import { Button } from '@nextui-org/react';
import {Input} from "@nextui-org/react";
import styles from './styles.module.css';
import { useState } from 'react';
import { SERVER_URL } from '@/app/ui/login-button';
import { headers } from 'next/headers'
import RiotLoginForm from '@/app/ui/login/riot-login';

// sns_type, sns_id, riot_id를 담아 POST요청
export default function riotLoginPage() {
    
    const headersList = headers();

    return (
        <div>
            <p>Verify Riot account</p>
            <RiotLoginForm />
        </div>
    )
}