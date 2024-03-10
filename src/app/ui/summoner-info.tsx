import MatchIds from "./match";
import Summoner from "./summoner";

export const RIOT_API_KEY = "RGAPI-50d26fab-b452-417b-a70b-1d3b59e789fd";

// 이 컴포넌트에서 api요청을 보내고 응답을 받음 
async function getSummoner(id: string) {
    const RIOT_API_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name";
    const response = await fetch(`${RIOT_API_URL}/${id}`,
        {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        });
    return response.json();
}

export default async function SummonerInfo({ id }: { id: string }) {
    const summoner = await getSummoner(id);
    const profileImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/${summoner.profileIconId}.png`;
    return (
        <div>
            <img src={profileImgUrl} alt="profile" />
            <p>player name : {summoner.name}</p>
            <p>solo rank :{summoner.summonerLevel} </p>
            {/* <Summoner puuid={summoner.puuid} /> */}
            <MatchIds puuid={summoner.puuid} />
        </div>
    );
}