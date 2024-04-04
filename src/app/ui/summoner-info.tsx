import MatchIds from "./match";
import Summoner from "./summoner";
import { User, Avatar } from "@nextui-org/react";

import styles from "./summoner-info.module.css";
import { SERVER_API_URL } from "@/utils/instance-axios";

// export const RIOT_API_KEY = "RGAPI-50d26fab-b452-417b-a70b-1d3b59e789fd";
export const RIOT_API_KEY :string = "RGAPI-9375852d-f530-4428-93b7-b70f6c0fcf51";
// export const RIOT_API_KEY :string = "RGAPI-50d26fab-b452-417b-a70b-1d3b59e789fd"

// 이 컴포넌트에서 api요청을 보내고 응답을 받음 
async function getSummoner(id :string, tagline :string) {
    try {
        const RIOT_API_URL = `${SERVER_API_URL}/api/statistics/summoners/records/riot-ids/${id}/tag-lines/${tagline}`
        const response = await fetch(`${RIOT_API_URL}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json', // JSON 형식으로 데이터를 보낼 것임을 명시
                } 
            }
        );

        if (!response.ok) {
            // console.log('response body: ', await response.json())
            // throw new Error('Failed to fetch `summoner data');
        }
        const data = await response.json()
        return data;
    } catch(error) {
        console.error('Error fetching summoner data:', error);
        throw error; 
    }
}

// 사용 안 함 
// async function getProfile (puuid :string) {
//     try {
//         const RIOT_API_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/"
//         const response = await fetch(`${RIOT_API_URL}/${puuid}`, {
//             headers: { "X-Riot-Token": RIOT_API_KEY }
//         })

//         if (!response.ok) {
//             throw new Error('Failed to fetch summoner profile data');
//         }

//         return response.json();
//     } catch (error) {
//         console.error('Error fetching summoner profile data:', error);
//         throw error
//     }
// }

export default async function SummonerInfo({ id, tagline }: { id: string, tagline :string }) {
    try {
        const summoner = await getSummoner(id, tagline); // 여기서 데이터 한 번에 받음
        // console.log("response: ", summoner)
        // const summonerProfile = await getProfile(summoner.puuid)
        const profileImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/${summoner?.summonerInfo?.profileIconId}.png`;
        return (
            <div className={styles.container}>
                <div className={styles.profileInfo}>
                    <Avatar
                        src={profileImgUrl}
                        className={styles.profile}
                    />
                    <div className={styles.profileText}>
                        <p className="text-bold color-[#DD135A]">{summoner.summonerInfo.name}</p>
                        {summoner.leagueInfo.tier !== "no league data" ? (
                            <p className="text-sm">solo rank : {summoner.leagueInfo.tier} {summoner.leagueInfo.rank} </p>
                        ) : (
                            <p className="text-sm">solo rank : Unranked</p>
                        )}
                    </div>
                </div>
                {/* <Summoner puuid={summoner.puuid} /> */}
                <MatchIds matchIds={summoner.matches} summonerId={summoner.summonerInfo.puuid} />
            </div>
        );    
    } catch (error) {
        console.error(error)
        return (
            <div>
                <p className="m-8">Summoner not found</p>
            </div>
        );
    }
}