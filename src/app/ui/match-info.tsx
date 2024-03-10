import { RIOT_API_KEY } from "./summoner-info"
import styles from "./match-info.module.css"

async function getMatchDetail(matchId: string) {
    const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/${matchId}`
    const response = await fetch(RIOT_API_URL,
        {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        });
    return response.json();
}

export default async function MatchDetail({ matchId }: {
    matchId: string
}) {
    const matchInfo = await getMatchDetail(matchId); // 
    const players = matchInfo.metadata?.participants; // array
    const data = matchInfo.info
    const gameDuration = data?.gameDuration
    const playerData = matchInfo.info?.participants
    const teamData = matchInfo.info.teams
    const result = teamData.map((team) => {
        if (team.win) {
            return 'win';
        } else {
            return 'lose';
        }
    })
    let index = 0;

    return (
        <div>
            <div className={styles.match}>
                <p>matchId : {matchId}</p>
                <p>gameDuration : {gameDuration}</p>
            </div>
            {/* {JSON.stringify(matchInfo)} */}
            <div>
                {
                    playerData.map((player: object) => {
                        const profileImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/${player.profileIcon}.png`
                        return (
                            <div key={index++}>
                                <p>{index}</p>
                                <img src={profileImgUrl} alt="profile" className={styles.profile} />
                                <p>Id : {player.riotIdGameName}</p>
                                <p>TagLine : {player.riotIdTagline}</p>
                                <p>summonerLevel : {player.summonerLevel}</p>
                                <p>summonerName : {player.summonerName}</p>
                                <p>Champion : {player.championName}</p>
                                <p>assists : {player.assists}</p>
                                <p>deaths : {player.deaths}</p>
                                <p>kills : {player.kills}</p>
                                <p>largest Multi Kill : {player.largestMultiKill}</p>
                                <p>pentaKills : {player.pentaKills}</p>
                                <p>Individual Position : {player.individualPosition}</p>
                                <p>lane : {player.lane}</p>
                                <p>role	: {player.role}</p>
                                <p>teamId : {player.teamId}</p>
                                <p>teamPosition : {player.teamPosition}</p>
                                {index === 4 ? <p>win: {result[0]}</p> : index === 9 ? <p>win : {result[1]}</p> : null}
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )

}