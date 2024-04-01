'use client'

import axios from "axios"
import { useEffect, useState } from "react"
import Link from "next/link"
import { createSession } from "@/app/lib/openvidu"
import { SERVER_API_URL } from "@/utils/instance-axios"

const URL = "https://matchup.site/openvidu/api"
const headers = { Authorization: "Basic T1BFTlZJRFVBUFA6TWF0Y2hVcA==" }

export default function Page() {
  const [myRooms, setMyRooms] = useState<any>([])

  useEffect(() => {
    getRooms()
  })


  function getRooms() {
    axios({
      method: 'get',
      url: `${SERVER_API_URL}/api/chats/rooms`
    })
    .then((response: any) => {
      setMyRooms(response.data)
    })
  }

  function goSession() {
  }



  return (
    <div>
      <button onClick={getRooms}>채팅방 목록 가져오기</button>
      <hr />
      {myRooms.map((room: any) => (
        <div key={room.roomId}>
          <button onClick={() => {createSession(room.roomId)}}>
            <Link href={`/onmatchup/${room.roomId}`}>{room.roomId}</Link>
          </button>
          <hr />
        </div>
      ))}
    </div>
  )
}