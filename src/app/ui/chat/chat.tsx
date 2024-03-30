"use client"
import React, { useRef, useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import type { DraggableData, DraggableEvent } from 'react-draggable';
import Draggable from 'react-draggable';
import { Button, Modal } from 'antd';
import { SERVER_API_URL } from "@/utils/instance-axios";
import styles from "./chat.module.css"

// 개별 채팅방
export default function DirectMessage() {
    const [stompClient, setStompClient] = useState<Client | null>(null);
    const [messages, setMessages] = useState<string[]>([]);
    const [inputMessage, setInputMessage] = useState("");
    const WEBSOCKET_URL = "wss://matchup.site/api/ws";


    useEffect(() => {
        // STOMP 클라이언트 생성
        const connectStomp = async () => {
            const stomp = new Client({
                brokerURL: "wss://matchup.site/api/ws",
            });
    
            try {
                stomp.activate();
                console.log('STOMP connected');
            } catch (error) {
                console.error('Failed to activate STOMP:', error);
            }
        // 메시지 수신 핸들러 등록
        stomp.onConnect = () => {
            stomp.subscribe('/topic/chat', (message) => {
                const receivedMessage = message.body;
                setMessages((prevMessages) => [...prevMessages, receivedMessage]);
            });
        };

        setStompClient(stomp);

        // 컴포넌트 언마운트 시 연결 종료
        return () => {
            stomp.deactivate();
        };
    }}, []);

    const sendMessage = () => {
        if (!stompClient) return;

        // 입력한 메시지를 서버로 전송
        stompClient.publish({
            destination: '/app/chat',
            body: inputMessage,
        });

        // 메시지 입력 필드 초기화
        setInputMessage("");
    };

    return (
        <div className={styles.chatModal}>
           <div>
            {messages.map((message, index) => (
                <div key={index}>{message}</div>
            ))}
           </div>
           <input
                type="text"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
            />
        </div>
    )
}



