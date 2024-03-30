"use client"
import React, { useState } from "react";
import styles from "./Sidebar.module.css";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button, Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, useDisclosure, ModalProps} from "@nextui-org/react";
import ChatRoom from "./chat/chatRoom";
import DirectMessage from "./chat/chat";
import { useAtom } from "jotai";
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import { isRoomOpenAtom } from '@/store/chatAtom'
import Friends from "./chat/friends";

const SideBar: React.FC = () => {
	const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
	const {isOpen, onOpen, onOpenChange} = useDisclosure();
	const [isRoomOpen, setIsRoomOpen] = useAtom(isRoomOpenAtom)
	const [chatOrFreiend, setChatOrFreiend] = useState(true) // 기본값은 친구목록 보여주기
	const [scrollBehavior, setScrollBehavior] = React.useState<ModalProps["scrollBehavior"]>("inside");
	const handleToggle = (category :string) => {
		if (category === 'f') {
			setChatOrFreiend(true)
		} else {
			setChatOrFreiend(false)
		}
	}
	return (
		<div style={{
			position: 'fixed', 
			right: 0, 
			top: '4rem',
			bottom: 0, 
			width: '280px',
			height: "100vh",
			color: 'white',
			backgroundColor: '#161A1E', 
			overflowY: 'auto',
			padding: '20px', 
			zIndex: 1000,
			boxShadow: '0 0 0.5px 0.15px #36c4be',
		}}>
			{!isLoggedIn && <p>먼저 로그인하고 서비스를 이용하세요</p>}
			{/* 로그인 하면 보이는 기능 */}
			<div>
				{/* 토글버튼 */}
				<Button className="w-[50px] h-[30px] min-w-0" color="warning" onPress={()=> handleToggle('f')}>친구</Button>
				<Button className="w-[50px] h-[30px] min-w-0" color="warning" onPress={()=> handleToggle('c')}>채팅</Button>
				{/* 친구목록 */}
				<div className={chatOrFreiend ? "" : styles.hide}>
					<span className={styles.title}>Friends</span>
					<Button className="w-[10px] h-[15px] min-w-0" color="warning">friends</Button>
					<Button className="w-[10px] h-[15px] min-w-0" color="warning">sent</Button>
					<Button className="w-[10px] h-[15px] min-w-0" color="warning">requested</Button>

					<div className="flex flex-col gap-4 items-center z-20000">
						<p>회원가입하고 나와 맞는 듀오를 찾아보세요</p>
						<Friends />
					</div>
				</div>
				{/* 채팅목록창 */}
				<div className={chatOrFreiend ? styles.hide : "" }>
					{/* <Button className={styles.chatButton} color="primary" variant="solid" onPress={onOpen}>Chats</Button> */}
					{/* 채팅목록 */}
					<div>
						<ChatRoom />
						<ChatRoom />
						<ChatRoom />
						<ChatRoom />
					</div>
				</div>
			</div>
		</div>
	);
};

export default SideBar;
