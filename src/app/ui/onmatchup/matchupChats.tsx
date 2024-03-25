'use client'

import { demoChats } from "@/app/lib/placeholder-data"
import '@/app/ui/onmatchup/matchupChats.css'
import { ScrollShadow } from "@nextui-org/react"
import React, { useEffect, useState } from "react"
import axios from "axios"
import SockJS from "sockjs-client"
import { Client } from "@stomp/stompjs"

export default function MatchupChats() {

  useEffect(() => {
    const sock = new SockJS("https://j10a405.p.ssafy.io/api/ws")
    const client  = new Client({
      webSocketFactory() {
        sock
      },
      // brokerURL: "wss://j10a405.p.ssafy.io/api/ws",
      // debug: (str) => {
      //   console.log(str)
      // },
      onConnect(frame) {
        console.log('connected: ' + frame)

        client.subscribe('/topic/65f9542382dd2c780b44d292', (message) => {
          console.log(message)
        })
      },
    })
    client.activate()
  }, [])
  
  const me = "배성준"
  const user = 1
  const roomId = '65f9542382dd2c780b44d292'

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