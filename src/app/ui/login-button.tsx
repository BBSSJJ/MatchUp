"use client"

import React,  { useState, MouseEvent } from 'react';
import styles from './login-button.module.css';
export const SERVER_URL = process.env.SERVER_URL;

const LoginButton = ({ snsType } : {snsType :string} ) => {
    const REDIRECT_URI = `${SERVER_URL}/api/oauth2/${snsType}`

    const onClick = async () => {
        try {
            window.location.href = REDIRECT_URI;
         
        } catch(error) {
            console.log(error);
        }
    }
    return (
        <div className={styles.button}>
            <a href="#" onClick={onClick}>login</a>
        </div>
    )
}

export default LoginButton;