"use client"
import useSWR, { mutate } from 'swr';
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure} from "@nextui-org/react";
import { SERVER_API_URL } from '@/utils/instance-axios';
import { useState } from 'react';


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



// 친구목록
export default function Friends() {
    const [shouldFetch, setShouldFetch] = useState(true)
    // 친구 데이터 가져오기
    const {data: friends, error: friendError, isLoading: friendLoading } = useSWR(
        `${SERVER_API_URL}/api/friends?friendStatus=FRIEND`,
        friendFetcher,
        {
            onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
              if (error.status === 401) return
        
            }, 
            revalidateOnFocus: false,
            revalidateOnMount: true,
        },
    )


    const reloadFriends = async () => {
        mutate(`${SERVER_API_URL}/api/friends?friendStatus=FRIEND`)
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
    if(friendLoading) {

        return <h1>Loading...</h1>
    }


    return (
        <>
        
        {friends?.list?.map((friend :Friend) => {
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
                        >
                            DM
                        </Button>
                    </CardHeader>
                </Card>
            )
            })} 
        </>
    )
}