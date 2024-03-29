"use client"
import { usePathname, useRouter } from "next/navigation"
import { useEffect } from "react"
import { SERVER_API_URL } from "@/utils/instance-axios"
import { atom, useAtom } from 'jotai'
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import { atomWithStorage } from 'jotai/utils';

const SERVER_URL = SERVER_API_URL

// 소셜 로그인 시 이미 가입한 유저인 경우 -> 
const HiddenLogin = ( ) => {
    const path = usePathname()
    const tokens = path.split('/')
    const snsId = tokens[3]
    const snsType = tokens[2]
    const router = useRouter()
    const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
    const [user, setUser] = useAtom(userInfoAtom)

    const handleSignIn = async () => {
        try {
            // console.log("이미 가입한 유저", snsId)
            if(isNaN(parseInt(snsId, 10))) {
                return
            }
            const response = await fetch(`${SERVER_URL}/api/users/login`, {
                method:  'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', 
                body: JSON.stringify({ snsType, snsId }),
            })
            // console.log("로그인 요청 보냄")

            if(response.ok) {
                console.log("로그인 성공")
                // console.log("로그인 요청에 대한 응답 : ", response)
                // 쿠키의 유저 정보 저장
                const userCookie = decodeURIComponent(document.cookie);
                // console.log(userCookie)
                const jsonString = userCookie.substring(5);
                setUser(JSON.parse(jsonString));
                console.log(jsonString)
                // 로그인 상태 로컬 스토리지에 저장
                setIsLoggedIn(true)
                // redirect
                router.push('/lobby')
                // window.location.href = 'http://70.12.246.67:3000/lobby'
                
            } else {
                console.log('로그인 실패')
            }
        } catch(error) {
            console.log(error);
        }
    }
    handleSignIn() // 이 함수가 2번 호출되는 듯
    // useEffect(() => {
        
    // }, [])
    
    return null
}

export default HiddenLogin