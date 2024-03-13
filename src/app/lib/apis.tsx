
import { RIOT_API_KEY } from "../ui/summoner-info";
import useSWR from "swr";

export function getMatchDetail (id :string)  {
    const RIOT_API_URL :string = `https://asia.api.riotgames.com/lol/match/v5/matches/${id}`
    const corsProxyUrl = 'https://cors-anywhere.herokuapp.com/';

    const fetcher = (RIOT_API_URL :string) => fetch(RIOT_API_URL,
        {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        }
        ).then(res => res.json())

    const { data, error, isLoading } = useSWR(`${corsProxyUrl}${RIOT_API_URL}`, fetcher);

    return {
        data: data,
        isLoading,
        isError: error
    }
}
