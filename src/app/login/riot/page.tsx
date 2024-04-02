"use client"
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import { SERVER_API_URL } from '@/utils/instance-axios'
import { useAtom } from 'jotai'
import Cookies from 'js-cookie'
import { useRouter, usePathname, useSearchParams } from "next/navigation"
import { useEffect } from 'react'


const RSO = () => {
    const router = useRouter()
    const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
    const [user, setUser] = useAtom(userInfoAtom)

    const path = usePathname()
    const searchParams = useSearchParams()
    const riotCode = searchParams.get('code') // 회원가입 요청 시 전해줄 변수

    const handleSignIn = async () => {
        try {
            // 라이엇 게임즈 인증 성공 시 이 페이지로 옴, 서버에 로그인 요청
            const response = await fetch(`${SERVER_API_URL}/api/users/regist`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', 
                body: JSON.stringify({ riotCode : riotCode?.replaceAll('=', '') })
            })
            // 서버에서 성공 응답 받은 경우, 쿠키에 담긴 유저 정보 저장
            if (response.ok) {
                const userCookie= Cookies.get('user')

                if (userCookie) {
                    setUser(JSON.parse(userCookie))
                    setIsLoggedIn(true)
                    window.location.href = `${SERVER_API_URL}`
                } else {
                    console.log('no user cookie')
                }
            } else {
                console.log('회원가입 실패')
            }
            // 로그인 상태 로컬 스토리지에 저장
        } catch (error) {
            console.error(error)
        }
    }

    useEffect(() => {
        handleSignIn()
    }, [])

    return null
}

export default RSO