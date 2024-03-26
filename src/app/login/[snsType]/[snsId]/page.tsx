"use client"

import { usePathname, useRouter } from "next/navigation"
import { useEffect } from "react"

// 소셜 로그인 시 이미 가입한 유저인 경우 -> 
const HiddenLogin = ( ) => {
    const path = usePathname()
    const tokens = path.split('/')
    const snsId = tokens[3]
    const snsType = tokens[2]
    const router = useRouter()
    const handleSignIn = async () => {
        try {
            const response = await fetch("http://70.12.247.47:9000/api/users/login", {
                method:  'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', 
                body: JSON.stringify({ snsType, snsId }),
            })

            if(response.ok) {
                console.log("로그인 요청에 대한 응답 : ", response)
                // redirect 주소
                router.push('/lobby')
                // window.location.href = 'http://70.12.246.67:3000/lobby'
                
            } else {
                console.log('로그인 실패')
            }
        } catch(error) {
            console.log(error);
        }
    }
    handleSignIn()
    // useEffect(() => {
        
    // }, [])
    
    return null
}

export default HiddenLogin