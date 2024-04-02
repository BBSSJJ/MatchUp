"use client"
import React, { useState } from "react";
import styles from "./Sidebar.module.css";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import ChatRoom from "./chat/chatRoom";
import DirectMessage from "./chat/chat";
import { useAtom, useAtomValue } from "jotai";
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import { isRoomOpenAtom } from '@/store/chatAtom'
import Friends from "./chat/friends";
import { SERVER_API_URL } from "@/utils/instance-axios";
import useSWR, { mutate } from "swr";


interface Chat {
    roomId: string;
    participants: number[]; 
    cnt: number; 
}

const chatFetcher = async (url:string) => {
    const response = await fetch(url); // 서버로부터 데이터 가져오기
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    return response.json(); // JSON 형식으로 변환하여 반환
};


const SideBar: React.FC = () => {
	const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
	const {isOpen, onOpen, onOpenChange} = useDisclosure();
	const [isRoomOpen, setIsRoomOpen] = useAtom(isRoomOpenAtom)
	const [chatOrFreiend, setChatOrFreiend] = useState(true) // 기본값은 친구목록 보여주기
	const [friendMode, setFriendMode] = useState('FRIEND') // 친구목록 중에서도 기존 친구목록 보여주기
	const userInfo = useAtomValue<any>(userInfoAtom) // read-only-atom
	

	// 친구, 채팅 토글
	const handleToggle = (category :string) => {
		if (category === 'f') {
			setChatOrFreiend(true)
		} else {
			setChatOrFreiend(false)
			mutate(`${SERVER_API_URL}/api/chats/rooms`)
		}
	}

	// 친구 버튼 토글(기존친구/내가요청보낸/나에게요청한)
	const handleFriendToggle = (category :string) => {
		setFriendMode(category)
	}

	// 채팅목록 가져오기
	const {data: chatRooms, error: chatRoomError, isLoading: chatRoomLoading } = useSWR(
        isLoggedIn ? `${SERVER_API_URL}/api/chats/rooms` : null,
        chatFetcher,
        {
            onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
              if (error.status === 401) return
        
            }, 
            revalidateOnFocus: false,
            revalidateOnMount: true,
			revalidateIfStale: true,
        },
    )

	if (chatRoomLoading) {
		return <h1>Loading...</h1>
	}

	return (
		<div className={styles.sidebar}>
			{!isLoggedIn && <p>먼저 로그인하고 서비스를 이용하세요</p>}
			{/* 로그인 하면 보이는 기능 */}
			{ isLoggedIn && 
				<div className="flex flex-col">
					{/* 토글버튼 */}
					<div className="my-3 flex">
						<Button className="h-[30px] min-w-0 mr-3" color="warning" radius="full" variant="shadow" onPress={()=> handleToggle('f')}>Duo</Button>
						<Button className="h-[30px] min-w-0" color="warning" radius="full" variant="shadow" onPress={()=> handleToggle('c')}>Chat</Button>
					</div>
					{/* 친구목록 */}
					<div className={chatOrFreiend ? "" : styles.hide}>
						<p className={styles.title}>Friends</p>
						<Button className="w-auto h-[25px] min-w-0 bg-[#10D7A0] mr-2" variant="shadow" onPress={() => handleFriendToggle('FRIEND')}>Duo</Button>
						<Button className="w-auto h-[25px] min-w-0 bg-[#10D7A0] mr-2" variant="shadow" onPress={() => handleFriendToggle('SENT')}>Sent</Button>
						<Button className="w-auto h-[25px] min-w-0 bg-[#10D7A0]" variant="shadow" onPress={() => handleFriendToggle('RECEIVED')}>Requested</Button>

						<div className="flex flex-col gap-4 items-center z-20000">
							<Friends mode={friendMode}/>
						</div>
					</div>
					{/* 채팅목록창 */}
					<div className={chatOrFreiend ? styles.hide : "" }>
						{/* <Button className={styles.chatButton} color="primary" variant="solid" onPress={onOpen}>Chats</Button> */}
						{/* 채팅목록 */}
						<div className={styles.chats}>
							{chatRooms?.list?.map((chat :Chat) => {
								return (
									<ChatRoom key={chat.roomId} chatId={chat.roomId} badge={chat.cnt} you={chat.participants.filter(member => member !== userInfo.userId)}/>
								)
							})
							}
						</div>
					</div>
				</div>
			}
		</div>
	);
};

export default SideBar;
