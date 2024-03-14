import MatchDetail from "./match-info";
import { RIOT_API_KEY } from "./summoner-info"

async function getMatch(puuid: string) {
    const typeOfMatch = 'ranked';
    const countOfMatch = '20';
    const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/${puuid}/ids?type=${typeOfMatch}&start=0&count=${countOfMatch}`
    const response = await fetch(RIOT_API_URL,
        {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        });
    return response.json();
}

async function getMatchDetail(matchId: string) {
    const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/${matchId}`
    const response = await fetch(RIOT_API_URL,
        {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        });
    return response.json();
}

export default async function MatchIds({ puuid }: {
    puuid: string
}) {
    const matchIds: string[] = await getMatch(puuid);
    console.log(matchIds, puuid);
    const matchArray = JSON.parse(JSON.stringify(matchIds)); // 20개의 최근 경기 아이디 배열
    const detailsPromises = matchIds.map(id => getMatchDetail(id));
    const matchDetails = await Promise.all(detailsPromises);
    // const dataArray = matchIds.map(async (id) => {
    //     return await getMatchDetail(id);
    // })
    console.log(matchDetails, "print");
    return (
        <div>
            {matchArray.map((item: string, index: number) => {
                return <MatchDetail key={index} matchId={item} puuid={puuid} data={matchDetails[index]} />
            })}
        </div>
    )
}