"use client"

import { SERVER_API_URL } from "@/utils/instance-axios";
import { useEffect } from "react";
import { getFirebaseToken } from "../../../firebase/firebaseConfig";


export default function FirebaseToken () {

    useEffect(() => {
        const requestPermission = () => {
          console.log('Requesting permission...');
          Notification.requestPermission().then((permission) => {
            if (permission === 'granted') {
              console.log('Notification permission granted.');
              // 퍼미션 허가 후 추가적으로 수행할 작업이 있다면 여기에 추가
            } else {
              console.log('Notification permission denied.');
            }
          })
          .then(async () => {
            const response = await fetch(`${SERVER_API_URL}/api/alarm`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({token: getFirebaseToken })
            })
    
            if (!response.ok) {
                console.error("클라이언트 토큰 등록 실패")
            }
            console.log(response)
          })
        };
    
        requestPermission();
      }, []);
    

    return (
        <></>
    )
}