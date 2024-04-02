"use client"

import React,  { useState, MouseEvent } from 'react';
import styles from './login-button.module.css';
import { SERVER_API_URL } from "@/utils/instance-axios"
import { button, Image } from '@nextui-org/react';

const SERVER_URL = SERVER_API_URL

const LoginButton = ({ snsType } : {snsType :string} ) => {
    const REDIRECT_URI = `${SERVER_URL}/api/oauth2/${snsType}`
    
    const onClick = async () => {
        try {
            window.location.href = REDIRECT_URI;
         
        } catch(error) {
            console.error(error);
        }
    }

    const snsTypeClassMap: { [key: string]: string } = {
        kakao: styles.kakaoButton,
        naver: styles.naverButton,
        google: styles.googleButton,
    };

    const buttonClassName = snsTypeClassMap[snsType]

    return (
        <button className={buttonClassName} onClick={onClick}>
            <Image src="" alt="" />
        </button>
    )
}

export default LoginButton;