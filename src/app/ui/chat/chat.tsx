"use client"
import React, { useRef, useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import { SERVER_API_URL } from "@/utils/instance-axios";
import styles from "./chat.module.css"
import { Button, Image } from '@nextui-org/react';
import { useAtom } from 'jotai';
import { userInfoAtom } from '@/store/authAtom';

interface ChatMessages {
    list : ChatMessage[]
}
interface ChatMessage {
    userId: number;
    name: string;
    iconUrl: string;
    content: string;
    timestamp: string; // Assuming LocalDateTime is a string format
}
// 개별 채팅방
export default function DirectMessage({roomId} : {roomId :string}) {
    const [stompClient, setStompClient] = useState<Client | null>(null)
    const [messages, setMessages] = useState<ChatMessages>({"list":[]}) // 기존 메시지
    const [inputMessage, setInputMessage] = useState("") // 입력 메시지
	const messagesEndRef = useRef<HTMLDivElement>(null)
    const WEBSOCKET_URL = "wss://matchup.site/api/ws"
    const [userInfo, setUserInfo] = useAtom<any>(userInfoAtom)
    
    const fetchPreviousMessages = async () => {
        try {
            const response = await fetch(`${SERVER_API_URL}/api/chats/rooms/${roomId}`);
            if (!response.ok) {
                throw new Error('Failed to fetch previous messages');
            }
            const data = await response.json();
            setMessages(data)

            console.log("fetched messages :", messages)

            return data
        } catch (error) {
            console.error('Error fetching previous messages:', error);
        }
    };

    // 1. STOMP 클라이언트 생성
    const connectStomp = async () => {
        const stomp = new Client({
            brokerURL: WEBSOCKET_URL,
            reconnectDelay: 5000,
        });


        try {
            stomp.activate(); // 활성화
            console.log('STOMP connected');
            // 메시지 수신 핸들러 등록
            stomp.onConnect = () => {
                stomp.subscribe(`/topic/${roomId}`, (message) => {
                    const receivedMessage = JSON.parse(message.body)
                    console.log("receivedMessage:", receivedMessage)
                    setMessages((prevMessages) => ({
                        ...prevMessages,
                        list: [...prevMessages.list, receivedMessage],
                    }));
                });
            };

            setStompClient(stomp);

        } catch (error) {
            console.error('Failed to activate STOMP:', error);
        }
    }


    useEffect(() => {

        fetchPreviousMessages() // 기존 메시지 가져와서 보여주기
        connectStomp() // 함수 호출

        // 컴포넌트 언마운트 시 연결 종료
        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, []);

	useEffect(() => {
		scrollToBottom();
		// console.log('Messages 상태가 업데이트됨:', messages);
	}, [messages]);

	const scrollToBottom = () => {
		if (messagesEndRef.current) {
			messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
		}
    };

    const sendMessage = () => {
        // if (!stompClient) return;
        if (stompClient && stompClient.connected) {
            if(!inputMessage.trim()) {
                alert('메시지를 입력하세요')
                return
            }

            const messageObject = {
                userId: userInfo.userId,
                name: userInfo.riotAccount.summonerProfile.name.replaceAll('+', ' '),
                iconUrl: userInfo.riotAccount.summonerProfile.iconUrl,
                content: inputMessage,
                timestamp: new Date().toISOString(), 
            };
            console.log("userInfo atom: " ,userInfo)
            console.log("messageObject:", messageObject)

            // 입력한 메시지를 서버로 전송
           
            stompClient.publish({
                destination: `/app/chat/${roomId}`, 
                body: JSON.stringify(messageObject),
            });
            
             // 메시지 입력 필드 초기화
            setInputMessage("");
        }
    };

    return (
		<div>
			<div className={styles.chatModal}>
				<div className={styles.messages}>
					{/* message 표시 */}
					{messages.list.map((message, index) => (
						<div key={index}  className={`${styles.messageContainer}`}>
							<div className={`flex ${message.userId === userInfo.userId ? styles.myMessage : styles.otherMessage}`}>
								<Image src={message.iconUrl} width="20px" height="20px" />
								<span className='text-tiny'>{message.name}</span>
							</div>
							<div>
								<p className='text-tiny'>{message.timestamp}</p>
								<p className={`${styles.messageBubble} text-small ${message.userId === userInfo.userId ? styles.myMessage : styles.otherMessage}`}>{message.content}</p>
							</div>	
						</div>
					))}
					<div ref={messagesEndRef} />
				</div>
					
				</div>
				<div className={styles.input}>
				<input
					type="text"
					value={inputMessage}
					onChange={(e) => setInputMessage(e.target.value)}
					onKeyDown={(e) => {
						if (e.key === 'Enter') {
							sendMessage();
						}
					}}
				/>
				<button 
					className='w-[20%] h-[100%]'  
					style={{ backgroundColor: 'red' }}
					onClick={() => sendMessage()}
				>
					보내기
				</button>
			</div>
		</div>
        
    )
}



