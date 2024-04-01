"use client"
import React, { useState, useEffect } from "react";
import useSWR, { mutate } from 'swr';
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import { useAtom } from "jotai";
import { isRoomOpenAtom, roomIdAtom } from '@/store/chatAtom'
import DirectMessage from "./chat";
import ChatModal from "./chatModal";
import { SERVER_API_URL } from "@/utils/instance-axios";

const youFetcher =async (url:string) => {
    const response = await fetch(url); // 서버로부터 데이터 가져오기
    if (!response.ok) {
        throw new Error('Failed to fetch data');
    }

    return response.json();
}

// 채팅목록에서 보이는 개별 채팅방 - 여기서 DM누르면 모달창이 뜸
export default function ChatRoom({chatId, badge, you} :{ chatId :string; badge :number; you :number[] }) {
    const [isRoomOpen, setIsRoomOpen] = useAtom(isRoomOpenAtom)
    const {isOpen, onOpen, onOpenChange} = useDisclosure();
	const [scrollBehavior, setScrollBehavior] = React.useState<ModalProps["scrollBehavior"]>("inside");
    const [roomId, setRoomId] = useAtom(roomIdAtom)

    const handleChat = () => {
        setRoomId(chatId)
        onOpen()
    }

    // 채팅 상대방 정보 가져오기
    const {data: partner , error: partnerError, isLoading: partnerLoading } = useSWR(
        `${SERVER_API_URL}/api/users/${you}`,
        youFetcher,
        {
          onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
            if (error.status === 401) return
      
          }, 
          revalidateOnFocus: false,
          revalidateOnMount: true,
        }
      )

    // 채팅 내용 가져오기
    const {data: chat , error: chatError, isLoading: chatLoading } = useSWR(
        `${SERVER_API_URL}/api/chats/rooms/${chatId}`,
        youFetcher,
        {
          onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
            if (error.status === 401) return
      
          }, 
          revalidateOnFocus: false,
          revalidateOnMount: true,
        }
      )
    
    // console.log("partner data : ", partner)
    // console.log("chat data:", chat)
    if (partnerLoading) {
        return <h1>chat list is loading...</h1>
    }

    if (chatLoading) {
        return <h1>chat is loading...</h1>
    }

    return (
        <div className="z-20000">
            <Card className="max-w-[340px]">
                <CardHeader className="justify-between">
                    <div className="flex gap-5">
                    <Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
                    <div className="flex flex-col gap-1 items-start justify-center">
                        <h4>{partner?.riotAccount.summonerProfile.name}</h4>
                        <h4 className="text-[7px]">{chatId}</h4>
                        {/* <h4 className="text-small font-semibold leading-none text-default-600">소환사명</h4> */}
                        <h5 className="text-tiny tracking-tight text-default-400">{chat.list?.at(-1).content}</h5>
                    </div>
                    </div>
                    <Button
                        className={"bg-transparent text-foreground border-default-200"}
                        color="primary"
                        radius="full"
                        size="sm"
                        variant={"bordered"}
                        onPress={handleChat}
                    >
                    DM
                    </Button>
                    {/* 개별 채팅방 모달  */}
					<ChatModal isOpen={isOpen} onOpenChange={onOpenChange}/>
                </CardHeader>
            </Card>
        </div>
    )
}