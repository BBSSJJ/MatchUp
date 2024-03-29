"use client"
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure} from "@nextui-org/react";
import { useAtom } from "jotai";
import { isRoomOpenAtom } from '@/store/chatAtom'

export default function ChatRoom() {
    const [isRoomOpen, setIsRoomOpen] = useAtom(isRoomOpenAtom)

    const openChatRoom = () => {
        setIsRoomOpen(prev => !prev)
    }
    return (
        <div>
            <Card className="max-w-[340px]">
                <CardHeader className="justify-between">
                    <div className="flex gap-5">
                    <Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
                    <div className="flex flex-col gap-1 items-start justify-center">
                        <h4 className="text-small font-semibold leading-none text-default-600">춘배</h4>
                        <h5 className="text-small tracking-tight text-default-400">안녕하세요...!</h5>
                    </div>
                    </div>
                    <Button
                    className={"bg-transparent text-foreground border-default-200"}
                    color="primary"
                    radius="full"
                    size="sm"
                    variant={"bordered"}
                    onPress={openChatRoom}
                    >
                    DM
                    </Button>
                </CardHeader>
            </Card>
        </div>
    )
}