"use client"
import React, { useState, useEffect } from "react";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import { useAtom } from "jotai";
import { isRoomOpenAtom } from '@/store/chatAtom'
import DirectMessage from "./chat";

// 채팅목록에서 보이는 개별 채팅방
export default function ChatRoom() {
    const [isRoomOpen, setIsRoomOpen] = useAtom(isRoomOpenAtom)
    const {isOpen, onOpen, onOpenChange} = useDisclosure();
	const [scrollBehavior, setScrollBehavior] = React.useState<ModalProps["scrollBehavior"]>("inside");
   

    return (
        <div>
            <Card className="max-w-[340px]">
                <CardHeader className="justify-between">
                    <div className="flex gap-5">
                    <Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
                    <div className="flex flex-col gap-1 items-start justify-center">
                        <h4 className="text-small font-semibold leading-none text-default-600">춘배</h4>
                        <h5 className="text-tiny tracking-tight text-default-400">안녕하세요...!</h5>
                    </div>
                    </div>
                    <Button
                    className={"bg-transparent text-foreground border-default-200"}
                    color="primary"
                    radius="full"
                    size="sm"
                    variant={"bordered"}
                    onPress={onOpen}
                    >
                    DM
                    </Button>
                    {/* 개별 채팅방 모달  */}
					<Modal
						isOpen={isOpen}
						onOpenChange={onOpenChange}
						scrollBehavior={scrollBehavior}
					>
						<ModalContent>
						{(onClose) => (
							<>
							<ModalHeader className="flex flex-col gap-1">
                                Chat
							</ModalHeader>
							<ModalBody>
								<DirectMessage />
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
                </CardHeader>
            </Card>
        </div>
    )
}