"use client"
import { Button } from '@nextui-org/react';
import {Input} from "@nextui-org/react";
import { useState } from 'react';
import styles from './riot-login.module.css';
import { SERVER_URL } from '@/app/ui/login-button';


// sns_type, sns_id, riot_id를 담아 POST요청
export default function RiotLoginForm() {
    const [riotId, setRiorId] = useState("");
    
    const handleSignIn = async () => {
        try {
            const response = await fetch(`${SERVER_URL}/api/users/signup`, {
                method:  'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ riotId }),
            })

            if(response.ok) {
                console.log(response)
            } else {
                console.log('회원가입 실패')
            }
        } catch(error) {
            console.log(error);
        }
    }

    return (
        <div>
            <p><span className='font-bold'>주의사항</span>"아이디는 대소문자를 구분하지 않습니다." "Riot Games 계정과 연동되어 있지 않은 아이디는 입력할 수 없습니다."</p>
            <form action="submit" className={styles.container}>
                <p><span className='font-bold'>개인정보 안내</span> "입력한 아이디는 Riot Games 서버에서 사용자 정보를 가져오는 용도로만 사용되며, 개인정보 보호 정책에 따라 안전하게 처리됩니다."</p>
                <Input
                    value={riotId}
                    label="Riot ID"
                    isClearable
                    onClear={() => setRiorId("")}
                    radius="lg"
                    classNames={{
                    label: "text-black/50 dark:text-white/90",
                    input: [
                        "bg-transparent",
                        "text-black/90 dark:text-white/90",
                        "placeholder:text-default-700/50 dark:placeholder:text-white/60",
                    ],
                    innerWrapper: "bg-transparent",
                    inputWrapper: [
                        "shadow-xl",
                        "bg-default-200/50",
                        "dark:bg-default/60",
                        "backdrop-blur-xl",
                        "backdrop-saturate-200",
                        "hover:bg-default-200/70",
                        "dark:hover:bg-default/70",
                        "group-data-[focused=true]:bg-default-200/50",
                        "dark:group-data-[focused=true]:bg-default/60",
                        "!cursor-text",
                    ],
                    }}
                    onValueChange={(value: string): void => { setRiorId(value) }}
                    placeholder="Type your Riot Id..."
                    
                />
                <Button className='w-[50px] my-6' onClick={handleSignIn}>가입하기</Button>
            </form>
        </div>
    )
}