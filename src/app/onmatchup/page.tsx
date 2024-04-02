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
  const [sessions, setSessions] = useState<string[]>([])

  useEffect(() => {
    getRooms()
    getSessions()
  }, [])

  function getSessions() {
    axios({
      method: 'get',
      url: `${URL}/sessions`,
      headers
    })
    .then((response) => {
      const nowSessions = response.data.content.map((session: any) => session.sessionId)
      setSessions(nowSessions)
    })
  
  }


  function getRooms() {
    axios({
      method: 'get',
      url: `${SERVER_API_URL}/api/chats/rooms`
    })
    .then((response: any) => {
      setMyRooms(response.data.list)
    })
  }

  function createSessionWOID() {
    axios({
      method: 'post',
      url: `${URL}/sessions`,
      headers,
      data: {
        recordingMode: 'ALWAYS'
      }
    })
      .then(() => {
        getSessions()
      })
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
      <button onClick={createSessionWOID}>세션 생성하기(임시)</button>
      {sessions.map((session) => (
        <div key={session}>
          <Link href={`/onmatchup/${session}`}>{session}</Link>
          <hr />
        </div>
      ))}
    </div>
  )
}