'use client'

import Image from "next/image"
import MatchupChats from "../ui/onmatchup/matchupChats"
import axios from "axios"
import { useState } from "react"
import Link from "next/link"
import { createSession } from "@/app/lib/openvidu"

const URL = "https://matchup.site/openvidu/api"
const headers = { Authorization: "Basic T1BFTlZJRFVBUFA6TWF0Y2hVcA==" }

export default function Page() {
  const [sessions, setSessions] = useState<string[]>([])
  axios({
    method: 'get',
    url: `${URL}/sessions`,
    headers
  })
  .then((response) => {
    const nowSessions = response.data.content.map((session: any) => session.sessionId)
    setSessions(nowSessions)
  })

  return (
    <div>
      <button onClick={createSession}>세션 생성하기(임시)</button>
      <hr />
      {sessions.map((session) => (
        <div key={session}>
          <Link href={`/onmatchup/${session}`}>{session}</Link>
          <hr />
        </div>
      ))}
    </div>
  )
}