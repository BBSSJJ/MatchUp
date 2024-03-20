import Image from "next/image";
import LoginButton from "../ui/login-button";
import AuthSession from "../providers/session-provider";

export default function LoginPage() {
    return (
        <AuthSession>
            <div>
                <li>
                    <LoginButton snsType="kakao" />
                </li>
                <li>
                    <LoginButton snsType="naver" />
                </li>
                <li>
                    <LoginButton snsType="google" />
                </li>
            </div>
        </AuthSession>
    
    );
}