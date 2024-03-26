"use client"

import React,  { useState, MouseEvent } from 'react';
import styles from './login-button.module.css';
export const SERVER_URL = "http://70.12.247.47:9000";

const LoginButton = ({ snsType } : {snsType :string} ) => {
    const REDIRECT_URI = `http://70.12.247.47:9000/api/oauth2/${snsType}`
    
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
        <div className={buttonClassName} onClick={onClick}>
            <a href="#" >
                
            </a>
        </div>
    )
}

export default LoginButton;