"use client"
import useSWR, { mutate } from 'swr';
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure} from "@nextui-org/react";
import { SERVER_API_URL } from '@/utils/instance-axios';
import { useState } from 'react';
import { roomIdAtom } from '@/store/chatAtom';
import { useAtom } from 'jotai';
import { userInfoAtom } from '@/store/authAtom';
import ChatModal from './chatModal';


interface Friend {
    userId: number;
    role: string;
    riotAccount: {
      id: string;
      summonerProfile: {
        name: string;
        tag: string;
        iconUrl: string;
        level: number;
      };
      tier: string;
      leagueRank: string;
      leaguePoint: number;
    };
}

const friendFetcher = async (url:string) => {
    const response = await fetch(url); // 서버로부터 데이터 가져오기
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    return response.json(); // JSON 형식으로 변환하여 반환
};

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
        // if (Object.keys(response).length !== 0 && response.constructor === Object) {
        //     const data = await response.json()
        //     return data.roomId ; // string 
        // }
        return response.json()
    } catch (error) {
        console.error('Error checking chat room:', error);
        return  // 에러 발생 시에도 false 반환
    }
};


// 친구목록
export default function Friends({mode} :{mode :string}) {
    const {isOpen, onOpen, onOpenChange} = useDisclosure();
    const [shouldFetch, setShouldFetch] = useState(true)
    const [roomId, setRoomId] = useAtom(roomIdAtom)
    const [userInfo, setUserInfo] = useAtom<any>(userInfoAtom)

    // 친구 데이터 가져오기(F/S/R)
    const {data: friends, error: friendError, isLoading: friendLoading } = useSWR(
        `${SERVER_API_URL}/api/friends?friendStatus=${mode}`,
        friendFetcher,
        {
            onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
              if (error.status === 401) return
        
            }, 
            revalidateOnFocus: false,
            revalidateOnMount: true,
            revalidateIfStale: true,
        },
    )


    const reloadFriends = async () => {
        mutate(`${SERVER_API_URL}/api/friends?friendStatus=FRIEND`)
    }
    // 버튼 클릭시 수행 : void
    const handleChat = async (userId :number) => {
        await openChatRoom(userId)
        onOpen()
    }

    // 해당 유저와의 채팅방이 있다면 그 방의 번호를 roomId로 설정, 없다면 생성하고 생성된 방 번호를 roomId로 설정
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

    if (friendError) {
        // setShouldFetch(false)
        return (
            <>
                <Button onPress={reloadFriends}>새로고침</Button>
                <p>failed to load data</p>
            </>
        );
    }
    if (friendLoading) {
        return <h1>Loading...</h1>
    }

    return (
        <>
        {friends?.list?.length === 0 ? (
            <p>No Friends yet</p>
        ) : (friends?.list?.map((friend :Friend) => {
            return (
                <Card key={friend.userId} className="max-w-[340px]">
                    <CardHeader className="justify-between">
                        <div className="flex gap-5">
                            <Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
                            <div className="flex flex-col gap-1 items-start justify-center">
                                <h4 className="text-small font-semibold leading-none text-default-600">{friend.riotAccount.summonerProfile.name}</h4>
                                {/* <h5 className="text-small tracking-tight text-default-400">@zoeylang</h5> */}
                            </div>
                        </div>
                        <Button
                        className={"bg-transparent text-foreground border-default-200"}
                        color="primary"
                        radius="full"
                        size="sm"
                        variant={"bordered"}
                        >
                            match up
                        </Button>
                        <Button
                        className={"bg-transparent text-foreground border-default-200"}
                        color="primary"
                        radius="full"
                        size="sm"
                        variant={"bordered"}
                        onPress={() => handleChat(friend.userId)}
                        >
                            DM
                        </Button>
                        <ChatModal isOpen={isOpen} onOpenChange={onOpenChange}/>
                    </CardHeader>
                </Card>
            )
            }))} 
        </>
    )
}