"use client"
import React, { useRef, useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import type { DraggableData, DraggableEvent } from 'react-draggable';
import Draggable from 'react-draggable';
import { Button, Modal } from 'antd';
import { SERVER_API_URL } from "@/utils/instance-axios";
import styles from "./chat.module.css"
import { useAtom } from 'jotai';
import { userInfoAtom } from '@/store/authAtom';

// 개별 채팅방
export default function DirectMessage({roomId} : {roomId :string}) {
    const [stompClient, setStompClient] = useState<Client | null>(null);
    const [messages, setMessages] = useState<string[]>([]); // 기존 메시지
    const [inputMessage, setInputMessage] = useState(""); // 입력 메시지
    const WEBSOCKET_URL = "wss://matchup.site/api/ws";
    const [userInfo, setUserInfo] = useAtom<any>(userInfoAtom)


    useEffect(() => {
        // STOMP 클라이언트 생성
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
                        const receivedMessage = message.body;
                        setMessages((prevMessages) => [...prevMessages, receivedMessage]);
                    });
                };

                setStompClient(stomp);

            } catch (error) {
                console.error('Failed to activate STOMP:', error);
            }

            connectStomp() // 함수 호출
            


        // 컴포넌트 언마운트 시 연결 종료
        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }}, []);

    const sendMessage = () => {
        if (!stompClient) return;

        if(!inputMessage.trim()) {
            alert('메시지를 입력하세요')
            return
        }

        const messageObject = {
            userId: userInfo.userId,
            name: userInfo.name,
            iconUrl: userInfo.iconUrl,
            content: inputMessage,
            timestamp: new Date().toISOString(), // Replace with the appropriate date-time format
          };

        // 입력한 메시지를 서버로 전송
        // {}에 어떤 내용이 들어가야하는지?
        stompClient.publish({
            destination: `/app/chat/${roomId}`, 
            body: JSON.stringify(messageObject),
        });

        // 메시지 입력 필드 초기화
        setInputMessage("");
    };

    return (
        <div className={styles.chatModal}>
           <div className='h-[92%]'>
            {messages.map((message, index) => (
                <div key={index}>{message}</div>
            ))}
           </div>
           <div className='flex h-[8%]'>
           <input
                className='w-[80%] h-[100%]'
                type="text"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
            />
            <button 
                className='w-[20%] h-[100%]'  
                style={{ backgroundColor: 'red' }}
                onClick={sendMessage}
            >
                보내기
            </button>
           </div>
        </div>
    )
}



