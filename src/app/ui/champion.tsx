"use client"
import Link from "next/link";
import styles from "./champion.module.css"
import { useRouter } from "next/navigation";

interface ChampionProps {
    title: string;
    poster_path: string;
}

export default function Champion({ title, poster_path }: ChampionProps) {
    const router = useRouter();

    return (
        <div className={styles.champ}>
            <img src={poster_path} alt={title} />
            <p>{title}</p>
        </div>
    )
}