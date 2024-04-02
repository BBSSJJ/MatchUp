"use client"
import { usePathname, useRouter } from "next/navigation"
import { useEffect } from "react"
import { SERVER_API_URL } from "@/utils/instance-axios"
import { atom, useAtom } from 'jotai'
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import Cookies from 'js-cookie'

const CLIENT_ID = process.env.NEXT_PUBLIC_CLIENT_ID
const RIOT_REDIRECT_URI = process.env.NEXT_PUBLIC_RIOT_REDIRECT_URI
const SERVER_URL = SERVER_API_URL

// 소셜 로그인 시 이미 가입한 유저인 경우 -> 
const HiddenLogin = ( ) => {
    const path = usePathname()
    const tokens = path.split('/')
    const snsId = tokens[3]
    const snsType = tokens[2]
    const tof = tokens[4]
    const router = useRouter()
    const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
    const [user, setUser] = useAtom(userInfoAtom)

    const handleSignIn = async () => {
        try {
            // console.log("이미 가입한 유저", snsId)
            if(isNaN(parseInt(snsId, 10))) {
                return
            }
            if (tof === 'true') { // RSO 인증이 필요
             
                window.location.href = "https://auth.riotgames.com/authorize?" +
                "client_id=" + CLIENT_ID +
                "&redirect_uri=" + RIOT_REDIRECT_URI +
                "&response_type=code&scope=openid+offline_access"  // RSO
            } else { 
                const response = await fetch(`${SERVER_URL}/api/users/login`, {
                    method:  'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include', 
                    body: JSON.stringify({ snsType, snsId }),
                })

                if(response.ok) {
                    console.log("로그인 성공")
                    // console.log("로그인 요청에 대한 응답 : ", response)
                    // 쿠키의 유저 정보 저장
                    const userCookie = Cookies.get('user')
    
                    if (userCookie) {
                        setUser(JSON.parse(userCookie))
                        setIsLoggedIn(true)
                        router.push('/lobby')
                    } else {
                        console.log("no user cookie")
                    }
                    
                } else {
                    console.log('로그인 실패')
                }
                router.push('/') // 기존 회원
            }
            
            // console.log("로그인 요청 보냄")
            
        } catch(error) {
            console.log(error);
        }
    }
   
    useEffect(() => {
        handleSignIn() 
    }, [])
    return null
}

export default HiddenLogin