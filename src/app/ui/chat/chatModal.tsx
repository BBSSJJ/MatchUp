"use client"
import React, { useState, useEffect } from "react";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import { useAtom } from "jotai";
import { isRoomOpenAtom, roomIdAtom } from '@/store/chatAtom'
import DirectMessage from "./chat";
import { SERVER_API_URL } from "@/utils/instance-axios";
import useSWR, { mutate } from "swr";
import { youFetcher } from "./chatRoom";
import Link from "next/link";
import { createSession } from "@/app/lib/openvidu";


// 친구목록의 DM클릭 시 또는 채팅목록의 채팅방 클릭시(set roomId) 뜨는 모달 
export default function ChatModal ({isOpen, onOpenChange} :{
    isOpen:boolean;
    onOpenChange: () => void;
}) {
	const [scrollBehavior, setScrollBehavior] = React.useState<ModalProps["scrollBehavior"]>("inside");
    const [roomId, setRoomId] = useAtom<any>(roomIdAtom)

    const {data: chatRooms, error: chatRoomError, isLoading: chatRoomLoading } = useSWR(
        `${SERVER_API_URL}/api/chats/rooms`,
        youFetcher,
        {
            onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
              if (error.status === 401) return
        
            }, 
            revalidateOnFocus: false,
            revalidateOnMount: true,
			revalidateIfStale: true,
        },
    )
    
    return (
    <Modal
        className="z-30000"
        isOpen={isOpen}
        onOpenChange={onOpenChange}
        scrollBehavior={scrollBehavior}
        style={{ zIndex: 50000 }} 
    >
        <ModalContent>
        {(onClose) => (
            <>
            <ModalHeader className="flex flex-col gap-1">
                Chat
            </ModalHeader>
            <ModalBody>
                <DirectMessage roomId={roomId} />
            </ModalBody>
            <ModalFooter>
                <Button color="danger" variant="ghost" onPress={async () => {
                    const response = await fetch(`${SERVER_API_URL}/api/chats/rooms/${roomId}`, {
                        method: 'DELETE',
                        headers: {
                        'Content-Type': 'application/json'
                        },
                    })
                    onClose()
                    mutate(`${SERVER_API_URL}/api/chats/rooms`)
                }}>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="-6 -2 24 24" width="28" fill="currentColor"><path d="M2 0h8a2 2 0 0 1 2 2v16a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2zm0 2v16h8V2H2zm2 7h1a1 1 0 1 1 0 2H4a1 1 0 0 1 0-2z"></path></svg>
                    나가기
                </Button>
                <Link href={`/onmatchup/${roomId}`} onClick={() => createSession(roomId)}>
                    <Button color="primary" onPress={onClose}>
                        Match Up
                    </Button>
                </Link>
            </ModalFooter>
            </>
        )}
        </ModalContent>
    </Modal>
    )
}