import { RIOT_API_KEY } from "../ui/summoner-info";


export default async function getMatchDetail(matchId: string) {
    const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/${matchId}`
    // const response = await fetch(RIOT_API_URL,
    //     {
    //         headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
    //     });
    // return response.json();
    try {
        const response = await fetch(RIOT_API_URL, {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` },
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch: ${response.status} ${response.statusText}`);
        }

        return response.json();
    } catch (error) {
        console.error("Fetch error:", error);
        throw error; // Rethrow the error to handle it further up the call stack.
    }
}