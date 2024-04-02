"use client"
import {Card, CardFooter, Image, Button, Badge, useDisclosure, Switch} from "@nextui-org/react";
import styles from "./user-info.module.css"
import { useAtom, useAtomValue } from "jotai";
import { isLoggedInAtom, userInfoAtom } from "@/store/authAtom";
import { SERVER_API_URL } from "@/utils/instance-axios";
import useSWR from "swr";
import ChatModal from "@/app/ui/chat/chatModal";
import { roomIdAtom } from "@/store/chatAtom";
import MicIcon from '@mui/icons-material/Mic';
import MicOffIcon from '@mui/icons-material/MicOff';
import { SiLeagueoflegends } from "react-icons/si";
import { useEffect, useState } from "react";

interface PlayerStats {
	rank: string;
	tier: string;
	winRate: number;
	latestChampion: string;
	top3Champions: string[];
	mostLane: string;
}

export interface UserData {
	tier?: string;
	win?: number;
	lose?: number;
} 

interface UserProfileProps {
	userId: string;
}
// 채팅방 생성
const createChatRoom = async (userId :number, myId :number) => {
    try {
        const response = await fetch(`${SERVER_API_URL}/api/chats/rooms`,
        {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json', // JSON 형식으로 데이터를 보낼 것임을 명시
            },
            body: JSON.stringify({ participants : [`${userId}`, `${myId}`] }),
        })

        if(!response.ok) {
            throw new Error('cannot create')
        }

        return response.json() // "created"
    } catch (error) {
        console.error(error)
        return null
    }
}

// 채팅방이 있는지 확인
const IsChatRoom = async (userId: number) => {
    try {
        // 이미 해당 유저와의 채팅방이 있는지 확인,
        const response = await fetch(`${SERVER_API_URL}/api/chats/users/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json', // JSON 형식으로 데이터를 보낼 것임을 명시
            },
        });
        const resData = await response.json()
        console.log("상대와의 채팅방 목록:", resData)
        return resData
    } catch (error) {
        console.error('Error checking chat room:', error);
        return  
    }
};

// 유저 정보 가져오기
const userFetcher = async (url:string) => {
    const response = await fetch(url); // 서버로부터 데이터 가져오기
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    return response.json(); // JSON 형식으로 변환하여 반환
};

// 프로필 페이지
export default function UserProfile({ userId } :UserProfileProps) {
	const keywords = ['트리플킬 장인', 'MVP', 'ACE', '슬로우 스타터', '불굴의 의지', '???']
	const {isOpen, onOpen, onOpenChange} = useDisclosure();

	// const userdata = data ?? { tier: 'Default', win: 0, lose: 0 };
	// const victory_rate = typeof data.win === 'number' && typeof data.lose === 'number' ? data.win / (data.win + data.lose) : ""
	const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
	const [roomId, setRoomId] = useAtom(roomIdAtom)
	const userInfo = useAtomValue<any>(userInfoAtom)
	const [onOff, setOnOff] = useState(false) // 마이크 사용여부
	const [trigger,setTrigger] = useState(false)
	const [isFriend, setIsFriend] = useState(false) // 친구여부
	
	// 마이크 세팅 가져오기
	const {data: mic,  error: micError, isLoading: micLoading } = useSWR(
		`${SERVER_API_URL}/api/users/settings`,
		userFetcher,
		{
			onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
			if (error.status === 401) return
		
			}, 
			revalidateOnFocus: false,
			revalidateOnMount: true,
			revalidateIfStale: true,
		},
	)

	// 전적 정보 가져오기 
	const {data: records,  error: recordsError, isLoading: recordsLoading } = useSWR(
		`${SERVER_API_URL}/api/statistics/summoners/details/users/${userId}`,
		userFetcher,
		{
			onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
			if (error.status === 401) return
			if (error.status === 500) return 'unranked'
			}, 
			revalidateOnFocus: false,
			revalidateOnMount: true,
			revalidateIfStale: true,
		},
	)

	// 친구목록
	const {data: friends, error: friendError, isLoading: friendLoading } = useSWR(
		`${SERVER_API_URL}/api/friends?friendStatus=FRIEND`,
		userFetcher,
		{
			onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
			  if (error.status === 401) return
		
			}, 
			revalidateOnFocus: false,
			revalidateOnMount: true,
			revalidateIfStale: true,
		},
	)

	// 최초 렌더링 시 로직
	useEffect(() => {
		// 마이크 상태 정보 가져오기
		const fetchMicStatus = async () => {
            try {
                const response = await fetch(`${SERVER_API_URL}/api/users/settings`,
				{
					method: 'GET',
					headers: {
						'Content-Type': 'application/json'
					},
				}
				);
                if (!response.ok) {
                    throw new Error('Failed to fetch mic status');
                }
                const data = await response.json();
                setOnOff(data.useMike); 
            } catch (error) {
                console.error('Error fetching mic status:', error);
            }
        };
		// 기존 친구인지 확인
		const isFriend = () => {
			const friendsList = friends?.list?.map((user :any) => user.userId)
			return friendsList?.includes(userId)
		}
		
        fetchMicStatus() // 마이크 사용여부 가져오기
		setIsFriend(isFriend)

	}, [])

	// 마이크 토글시마다 요청 보내기 
	useEffect(() => {
		const fetchMicStatus = async () => {
			const param = {
				'useMike': onOff
			}
            try {
                const response = await fetch(`${SERVER_API_URL}/api/users/settings`,
				{
					method: 'PATCH',
					headers: {
						'Content-Type': 'application/json'
					},
					body : JSON.stringify(param),
				}
				);
                if (!response.ok) {
                    throw new Error('Failed to patch mic status');
                }
                // const data = await response.json();
				return response
                // setOnOff(data.useMike); 
            } catch (error) {
                console.error('Error patching mic status:', error);
            }
        };
		fetchMicStatus()
	}, [])


	// 유저 데이터 가져오기
	const {data: user,  error: userError, isLoading: userLoading } = useSWR(
        `${SERVER_API_URL}/api/users/${userId}`,
        userFetcher,
        {
            onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
              if (error.status === 401) return
        
            }, 
            revalidateOnFocus: false,
            revalidateOnMount: true,
            revalidateIfStale: true,
        },
    )

	const openChatRoom = async (userId :number) => {
        try {
            let roomId = await IsChatRoom(userId) // 두 사람의 채팅방이 있는지 확인 
            console.log("기존의 roomID :", roomId)

            if('roomId' in roomId) { // res에 roomId 속성이 있는 경우
                console.log("이 채팅방 열기 : ",roomId)
                setRoomId(roomId.roomId)
            } else { // 없다면 생성 
                console.log('아직 채팅방 없음')
                await createChatRoom(userId, userInfo.userId)
                const createdRoom = await IsChatRoom(userId)
                const roomId = createdRoom.roomId
                setRoomId(roomId)
                console.log("채팅방 생성 :", roomId)
            }
        } catch (error) {
            console.error(error)
        }
    }

	// 친구 요청
	const handlefollow = async () => {
		if (!isLoggedIn) {
			alert("먼저 로그인해주세요")
			return
		} else {
			try {
				const response = await fetch(`${SERVER_API_URL}/api/friends/${userId}`,
				{
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
				})
				
				if(!response.ok) {
					console.error('친구요청 실패')
				}
				alert('친구요청 완료')
				return response
			} catch(error) {
				console.error(error)
			}
		}
	}
	
	// DM버튼 누를 경우 호출
	const handleChat = async (userId :number) => {
        await openChatRoom(userId)
        onOpen()
    }

	// 마이크 사용여부 토글 
	// const handleSwitch = async (event :React.ChangeEvent<HTMLInputElement>) => {
	// 	const useMIC = event.target.checked 

	// }
	
	if (userLoading || micLoading || recordsLoading || friendLoading ) {
		return <h1>loading...</h1>
	}


	// fetch 데이터로 대체할 부분 


	const positions = {
		'MIDDLE': '',
		'TOP': '',
		'BOTTOM': '',
		'JUNGLE': '', 
		'SUPPORTER': '',
		'ALL': ''
	}
	// ['/positionIcons/all.png','/positionIcons/bottom.png', '/positionIcons/jungle.png', '/positionIcons/mid.png', '/positionIcons/support.png', '/positionIcons/top.png' ]
	

	return (
		<div className={styles.container}>
			<div className={styles.item1}>
			<Badge content={user.riotAccount.summonerProfile.level} color="primary" variant="shadow" className="w-[50px] h-[30px]" >
				<Card
					isFooterBlurred
					radius="lg"
					className="border-none h-[250px] w-[250px]"
				>
					<Image
						alt="Lv- profile"
						className="object-center h-[250px] w-[250px]"
						src={records === 'unranked' ? "https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Yuumi.png" : `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/${records?.latestChampion}.png`}
						
					/>
					<CardFooter className="justify-between before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[calc(100%_-_8px)] shadow-small ml-1 z-10">
						<p className="text-tiny text-white/80">{user?.riotAccount.summonerProfile.name}</p>
						{userInfo.userId !== Number(userId) && (
							<div className="flex">
								{/* 친구가 아닌 경우에만 보여주기 */}
								{!isFriend && (
									<Button 
									className="text-tiny text-white bg-black/20" 
									variant="flat" 
									color="default" 
									radius="lg" 
									size="sm"
									onPress={handlefollow}
								>
									친구요청
								</Button>
								)}
								<Button
									variant="flat" 
									color="default" 
									radius="lg"  
									size="sm"
									onPress={() => handleChat(Number(userId))}
								>
									DM
								</Button>
							</div>
						)}
					</CardFooter>
				</Card>
				{/* DM누르면 뜨는 모달 */}
				<ChatModal isOpen={isOpen} onOpenChange={onOpenChange}/>
			</Badge>
				
			</div>
			
			<div className={styles.item2}>
				{/* <SiLeagueoflegends /> */}
				{/* 마이크 사용여부 토글 */}
				<Switch
					// defaultSelected
					isSelected={onOff}
					onChange={(event) => setOnOff(event.target.checked)}
					size="lg"
					color="secondary"
					thumbIcon={({ isSelected, className }) =>
						isSelected ? (
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="-3 -2 24 24" width="28" fill="currentColor"><path d="M9 2a3 3 0 0 0-3 3v6a3 3 0 0 0 6 0V5a3 3 0 0 0-3-3zm0-2a5 5 0 0 1 5 5v6a5 5 0 0 1-10 0V5a5 5 0 0 1 5-5zM0 11.03a1 1 0 1 1 2 0A6.97 6.97 0 0 0 8.97 18h.06A6.97 6.97 0 0 0 16 11.03a1 1 0 1 1 2 0A8.97 8.97 0 0 1 9.03 20h-.06A8.97 8.97 0 0 1 0 11.03z"></path></svg>
						) : (
							null
						)
					}
					>
					Microphone
				</Switch>
				{
					keywords.map((keyword :string, index :number) => {
						return (
						<Button key={index} radius="full" className="bg-gradient-to-tr from-green-500 to-blue-500 text-white shadow-lg m-1 h-[25px]">
							{keyword}
						</Button>)
					})
				}
			</div>
			<div className={styles.item3}>
				<div className="h-[250px] w-[650px]">
					<p className="text-bold">전적 정보</p>
					{records === 'unranked' ? (<p>랭크 게임을 더 하고 오세요</p>) : (
						<>s
							<div>
								<p>{records?.win} / {records?.lose}</p>
								<p>승률 : {records?.winRate}%</p>
								<p>Tier : {user.riotAccount.tier}</p>
								<p>Rank : {records?.rank}</p>
								<p>최근 사용한 챔피언</p>
								<div className="flex">
									{records?.top3Champions.map((champion :string, index :number) => {
										return (
											<Image 
												key={index}
												src={`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champion}.png`}
												width="50px"
												height="50px"
											/>
										)
									})}
								</div>
							</div>
							<div className="flex flex-col">
								<p>Main Position : {records?.mostLane}</p>
									{/* <div className="flex">
										<Image src={pos} width="20px" height="20px"/>
										<p className={styles.bar}><span className={styles.barContent}></span></p>
									</div> */}
							</div>
						</>
					)}
				</div>
			</div>
		</div>
	)
}