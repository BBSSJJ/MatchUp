'use client'

import { Input, RadioGroup, Radio, Button } from "@nextui-org/react"
import { useState } from "react"
import { useRouter } from "next/navigation"
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import axios from "axios"
import { useAtom } from "jotai"

export default function Page() {
  const [snsId, setSnsId] = useState<string>('')
  const [snsType, setSnsType] = useState<string>('')
  const router = useRouter()
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
  const [user, setUser] = useAtom(userInfoAtom)

  async function dummyLogin(snsId: string, snsType: string) {
    const response = await fetch('https://matchup.site/api/users/login', {
      method:  'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      credentials: 'include', 
      body: JSON.stringify({ snsType, snsId }),
    })
    // console.log("로그인 요청 보냄")

    if(response.ok) {
      console.log(response)
      // console.log("로그인 요청에 대한 응답 : ", response)
      // 쿠키의 유저 정보 저장
      const userCookie = decodeURIComponent(document.cookie);
      // console.log(userCookie)
      const jsonString = userCookie.substring(5);
      console.log(jsonString)
      setUser(JSON.parse(jsonString));
      // 로그인 상태 로컬 스토리지에 저장
      setIsLoggedIn(true)
      // redirect
      router.push('/lobby')
      // window.location.href = 'http://70.12.246.67:3000/lobby'
        
    } else {
        console.log('로그인 실패')
    }
  }
  return (
    <div className="w-3/5 ml-10">
      <Input label="snsId" onValueChange={setSnsId}/>
      <br />
      <RadioGroup onValueChange={(key: string) => setSnsType(key)}>
        <Radio value="GOOGLE">GOOGLE</Radio>
        <Radio value="NAVER">NAVER</Radio>
        <Radio value="KAKAO">KAKAO</Radio>
      </RadioGroup>
      <br />
      <Button onPress={() => dummyLogin(snsId, snsType)}>Dummy Login</Button>
    </div>
  )
}