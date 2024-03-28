'use client'

import { demoChats } from "@/app/lib/placeholder-data"
import '@/app/ui/onmatchup/matchupChats.css'
import { ScrollShadow } from "@nextui-org/react"
import React, { useEffect, useState } from "react"
import axios from "axios"
import { Client } from "@stomp/stompjs"

export default function MatchupChats({ roomId }: {roomId: number}) {
  const [client, setClient] = useState<Client | null>(null)
  const [messages, setMessages] = useState<any[]>([])

  useEffect(() => {
    getMessages()
    getRooms()

    const stomp = new Client({
      brokerURL: "wss://matchup.site/api/ws",
    })
    setClient(stomp)

    stomp.activate()

    stomp.onConnect = () => {
      stomp.subscribe(`/topic/${roomId}`, () => {
        // getMessages()
      })
    }
  }, [])

  function getMessages() {
    axios({
      method: 'get',
      url: `https://matchup.site/api/chats/rooms/${roomId}`
    })
      .then((response) => {
        setMessages(response.data.list)
      })
  }

  function getRooms() {
    axios({
      method: 'get',
      url: `https://matchup.site/api/chats/rooms`
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((error) => {
        console.log(error)
      })
  }
  
  const me = "me"
  const user = 1

  const [chats, setChats] = useState([{sender: '', content: ''}])

  // axios({
  //   method: 'get',
  //   url: `https://j10a405.p.ssafy.io/api/chats/users/${user}/rooms/${roomId}`
  // })
  //   .then((response) => {
  //     setChats(response.data.list)
  //   })

  return (
    <ScrollShadow className="chatbox">
      {demoChats.map((chat) => {
        return(
          <div key=''>
            <span style={{color: chat.sender == me ? "#C89B3C" : "#A09B8C"}}>{chat.sender}</span>:
            <span> {chat.content}</span>
          </div>
        )
      })}
    </ScrollShadow>
  )
}