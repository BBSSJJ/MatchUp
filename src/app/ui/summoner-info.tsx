import MatchIds from "./match";
import Summoner from "./summoner";
import { User, Avatar } from "@nextui-org/react";

import styles from "./summoner-info.module.css";

// export const RIOT_API_KEY = "RGAPI-50d26fab-b452-417b-a70b-1d3b59e789fd";
export const RIOT_API_KEY :string = "RGAPI-9375852d-f530-4428-93b7-b70f6c0fcf51";

// 이 컴포넌트에서 api요청을 보내고 응답을 받음 
async function getSummoner(id: string) {
    try {
        const RIOT_API_URL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id";
        const response = await fetch(`${RIOT_API_URL}/${id}/KR1`,
            {
                headers: { "X-Riot-Token": RIOT_API_KEY }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch summoner data');
        }

        return response.json();
    } catch(error) {
        console.error('Error fetching summoner data:', error);
        throw error; 
    }
}

export default async function SummonerInfo({ id }: { id: string }) {
    try {
        const summoner = await getSummoner(id);
        console.log("바뀐 api로 받아올 수 있는 정보: ", summoner)
        const profileImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/${summoner.profileIconId}.png`;
        return (
            <div className={styles.container}>
                <div className={styles.profileInfo}>
                    <Avatar
                        src={profileImgUrl}
                        className={styles.profile}
                    />
                    {/* <img src={profileImgUrl} alt="profile" className={styles.profile}/> */}
                    <div className={styles.profileText}>
                        <p>{summoner.name}</p>
                        <p>solo rank : {summoner.summonerLevel} </p>
                    </div>
                </div>
                {/* <Summoner puuid={summoner.puuid} /> */}
                <MatchIds puuid={summoner.puuid} />
            </div>
        );    
    } catch (error) {
        return (
            <div>
                <p>Summoner not found</p>
            </div>
        );
    }
}