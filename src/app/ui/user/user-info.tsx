"use client"
import {Card, CardFooter, Image, Button, Badge} from "@nextui-org/react";
import styles from "./user-info.module.css"
import { useAtom, useAtomValue } from "jotai";
import { isLoggedInAtom, userInfoAtom } from "@/store/authAtom";
import { SERVER_API_URL } from "@/utils/instance-axios";
import useSWR from "swr";


export interface UserData {
	tier?: string;
	win?: number;
	lose?: number;
} 

interface UserProfileProps {
	userId: string;
}

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
	// const userdata = data ?? { tier: 'Default', win: 0, lose: 0 };
	// const victory_rate = typeof data.win === 'number' && typeof data.lose === 'number' ? data.win / (data.win + data.lose) : ""
	const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
	const userInfo = useAtomValue<any>(userInfoAtom)
	
	// 유저 데이터 가져오기
	const {data: user, error: userError, isLoading: userLoading } = useSWR(
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
				return response.json()
			} catch(error) {
				console.error(error)
			}
		}
	}

	const recentChampions = ['Irelia', 'Ahri', 'Zeri']
	const positions = ['/positionIcons/all.png','/positionIcons/bottom.png', '/positionIcons/jungle.png', '/positionIcons/mid.png', './positionIcons/support.png', '/positionIcons/top.png' ]
	if (userLoading) {
		return <h1>loading...</h1>
	}

	return (
		<div className={styles.container}>
			<div className={styles.item1}>
			<Badge content={user.riotAccount.summonerProfile.level} color="primary" className="w-[50px] h-[30px]" >
				<Card
					isFooterBlurred
					radius="lg"
					className="border-none h-[250px] w-[250px]"
				>
					<Image
						alt="Woman listing to music"
						className="object-center h-[250px] w-[250px]"
						src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Viego.png"
						
					/>
					<CardFooter className="justify-between before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[calc(100%_-_8px)] shadow-small ml-1 z-10">
						<p className="text-tiny text-white/80">{user.riotAccount.summonerProfile.name}</p>
						{userInfo.userId !== userId && (
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
					</CardFooter>
				</Card>
			</Badge>
				
			</div>
			
			<div className={styles.item2}>
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
				<div className="h-[250px] w-[600px]">
					<p>전적 정보</p>
					{/* <p>{data.win} / {data.lose}</p> */}
					<p>승률 :  %</p>
					<p>티어 : {user.riotAccount.tier}</p>
					<p>최근 사용한 챔피언</p>
					<div>
					{recentChampions.map((champion, index) => {
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
					
					<div className="flex flex-col">
						<p>선호 포지션</p>
						{positions.map((pos, index) => {
							return (
								<div className="flex">
									<Image src={pos}/>
									<span></span>
								</div>
							)
						})
					}
					</div>
					
				</div>
			</div>
		</div>
	)
}