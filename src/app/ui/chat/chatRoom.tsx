"use client"
import React, { useState, useEffect } from "react";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import { useAtom } from "jotai";
import { isRoomOpenAtom, roomIdAtom } from '@/store/chatAtom'
import DirectMessage from "./chat";
import ChatModal from "./chatModal";

// 채팅목록에서 보이는 개별 채팅방 - 여기서 DM누르면 모달창이 뜸
export default function ChatRoom({chatId, badge} :{ chatId :string; badge :number }) {
    const [isRoomOpen, setIsRoomOpen] = useAtom(isRoomOpenAtom)
    const {isOpen, onOpen, onOpenChange} = useDisclosure();
	const [scrollBehavior, setScrollBehavior] = React.useState<ModalProps["scrollBehavior"]>("inside");
    const [roomId, setRoomId] = useAtom<string>(roomIdAtom)

    const handleChat = () => {
        setRoomId(chatId)
        onOpen
    }

    return (
        <div className="z-20000">
            <Card className="max-w-[340px]">
                <CardHeader className="justify-between">
                    <div className="flex gap-5">
                    <Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
                    <div className="flex flex-col gap-1 items-start justify-center">
                        <h4 className="text-small font-semibold leading-none text-default-600">소환사 이름</h4>
                        <h5 className="text-tiny tracking-tight text-default-400">가장 최근 메시지</h5>
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