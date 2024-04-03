'use client'

import '@/app/ui/onmatchup/matchupChats.css'
import { ScrollShadow, Input } from "@nextui-org/react"
import React, { useEffect, useState, useRef } from "react"
import axios from "axios"
import { Client } from "@stomp/stompjs"
import { isLoggedInAtom, userInfoAtom } from "@/store/authAtom";
import { useAtom } from "jotai";
import { SERVER_API_URL } from "@/utils/instance-axios";
import { demoChats } from '@/app/lib/placeholder-data'

export default function MatchupChats({ roomId }: {roomId: string}) {
  const [client, setClient] = useState<Client | null>(null)
  const [messages, setMessages] = useState<any[]>([])
  const [user, setUser] = useAtom<any>(userInfoAtom)
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
  const [content, setContent] = useState<string>('')
  const messagesEndRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    getMessages()

    const stomp = new Client({
      brokerURL: `wss://matchup.site/api/ws`,
    })
    setClient(stomp)

    stomp.activate()

    stomp.onConnect = () => {
      stomp.subscribe(`/topic/${roomId}`, () => {
        getMessages()
      })
    }
  }, [])

  useEffect(() => {
		scrollToBottom();
	}, [messages]);

  const scrollToBottom = () => {
		if (messagesEndRef.current) {
			messagesEndRef.current.scrollIntoView({ behavior: 'instant' });
		}
  };

  function getMessages() {
    axios({
      method: 'get',
      url: `${SERVER_API_URL}/api/chats/rooms/${roomId}`
    })
      .then((response) => {
        setMessages(response.data.list)
      })
  }

  function sendMessage() {
    if (client && client.connected) {
      client.publish({
        destination: `/app/chat/${roomId}`,
        body: JSON.stringify({
          userId: user.userId,
          name: user.riotAccount.summonerProfile.name,
          iconUrl: user.riotAccount.summonerProfile.iconUrl,
          content: content
        })
      })
      setContent('')
    }
  }

  return (
    <div>
      <ScrollShadow hideScrollBar className="chatbox" size={30}>
        {demoChats.map((chat, index) => {
          return(
            <div key={index}>
              <span style={{color: chat.name == "me" ? "#C89B3C" : "#A09B8C"}}>{chat.name.replaceAll('+', ' ')}</span>:
              <span> {chat.content}</span>
            </div>
          )
        })}
        <div ref={messagesEndRef} />
      </ScrollShadow>
      <Input 
        onValueChange={setContent} value={content} placeholder='내용을 입력해주세요' 
        onKeyDown={((event: any) => {
          if (event.key == 'Enter') {
            sendMessage()
          }
        })}
      />
    </div>
  )
}