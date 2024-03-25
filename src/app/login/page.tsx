import Image from "next/image";
import LoginButton from "../ui/login-button";
import styles from "./styles.module.css";

export default function LoginPage() {
    return (
        <div className={styles.container}>
            <p className="font-bold my-5">Join the MatchUp community to find fellow gamers and share epic gaming moments</p>
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
    );
}