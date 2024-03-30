"use client"
import React, { useState, useEffect } from "react";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import { useAtom } from "jotai";
import { isRoomOpenAtom, roomIdAtom } from '@/store/chatAtom'
import DirectMessage from "./chat";


// 친구목록의 DM클릭 시 또는 채팅목록의 채팅방 클릭시(set roomId) 뜨는 모달 
export default function ChatModal ({isOpen, onOpenChange} :{
    isOpen:boolean;
    onOpenChange: () => void;
}) {
	const [scrollBehavior, setScrollBehavior] = React.useState<ModalProps["scrollBehavior"]>("inside");
    const [roomId, setRoomId] = useAtom<any>(roomIdAtom)

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
                <DirectMessage roomId={"3"} />
            </ModalBody>
            <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                Close
                </Button>
                <Button color="primary" onPress={onClose}>
                Action
                </Button>
            </ModalFooter>
            </>
        )}
        </ModalContent>
    </Modal>
    )
}