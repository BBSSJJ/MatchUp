import MatchDetail from "./match-info";
import { RIOT_API_KEY } from "./summoner-info"

// 해당 puuid를 가진 유저의 솔랭 매치 아이디 가져오기
async function getMatch(puuid: string) {
    try {
        // const typeOfMatch = 'ranked';
        const countOfMatch = '10';
        const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/${puuid}/ids?queue=420&start=0&count=${countOfMatch}`
        const response = await fetch(RIOT_API_URL,
            {
                headers: { "X-Riot-Token": RIOT_API_KEY }
        });

        if(!response.ok) {
            throw new Error('Failed to fetch match data');
        }
        return response.json();
    } catch(error) {
        console.error('Error fetching match data:', error);
        throw error;
    }
}

async function getMatchDetail(matchId: string) {
    try {
        const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/${matchId}`
        const response = await fetch(RIOT_API_URL,
            {
                headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        });

        if(!response.ok) {
            throw new Error('Failed to fetch match detail data');
        }
        return response.json();
    } catch (error) {
        console.error('Error fetching match data:', error);
        throw error;
    }    
}

export default async function MatchIds({ puuid }: {
    puuid: string
}) {
    const matchIds: string[] = await getMatch(puuid);
    console.log("matchIds : ", matchIds, puuid);
    const matchArray = await JSON.parse(JSON.stringify(matchIds)); // 20개의 최근 경기 아이디 배열
    console.log("20개의 최근 경기 아이디 배열", matchArray)
    const detailsPromises = matchIds.map(id => getMatchDetail(id));
    const matchDetails = await Promise.all(detailsPromises);
    // const dataArray = matchIds.map(async (id) => {
    //     return await getMatchDetail(id);
    // })
    // console.log(matchDetails, "print");
    return (
        <div>
            {matchArray.map((item: string, index: number) => {
                return <MatchDetail key={index} puuid={puuid} data={matchDetails[index]} />
            })}
        </div>
    )
}