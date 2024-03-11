import { Avatar, AvatarGroup, Button } from "@nextui-org/react";
import styles from "./match-info.module.css"

import { RIOT_API_KEY } from "../ui/summoner-info";

async function getMatchDetail(matchId: string) {
    const RIOT_API_URL = `https://asia.api.riotgames.com/lol/match/v5/matches/${matchId}`
    const response = await fetch(RIOT_API_URL,
        {
            headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
        });
    return response.json();
}


export default async function MatchDetail({ matchId, puuid }: {
    matchId: string,
    puuid: string,
}) {
    const matchInfo = await getMatchDetail(matchId); // 
    const players = matchInfo.metadata?.participants; // array
    const data = matchInfo.info
    const gameDuration = data?.gameDuration
    const playerData = matchInfo.info?.participants // 보통 10명
    const teamData = matchInfo.info?.teams
    const playerChampionImgs = playerData.map((item) => {
        return item.championName;
    }) // 챔피언 이름 
    const playerSummonerIds = playerData.map((item) => {
        return item.riotIdGameName;
    }) // 소환사명
    const result = teamData.map((team) => {
        if (team.win) {
            return 'win';
        } else {
            return 'lose';
        }
    })
    let index = 0;
    // let buttonToggle = false;

    return (
        <div className={styles.container}>
            <div className={styles.match}>
                <p>matchId : {matchId}</p>
                <div className={styles.playInfo}>
                    <p>gameDuration : {Math.trunc(gameDuration/60)}m {Math.trunc(((gameDuration/60)-Math.trunc(gameDuration/60))*60)}s</p>
                    <AvatarGroup>
                        {playerChampionImgs.slice(0, playerChampionImgs.length/2).map((url, index) => (
                            <Avatar
                            key={index}
                            size="md"
                            src={"https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/" + url + ".png"}
                            />
                        ))}
                    </AvatarGroup>
                    <Button color="primary" variant="shadow">
                        More
                    </Button> 
                </div>
                <div>
                    {
                        playerData.slice(0, playerChampionImgs.length/2).map((player: object, index: number) => {
                            const champImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/${player.championName}.png`
                            console.log(index);
                            return (
                                <div className={players[index] === puuid ? styles.thisUser : styles.playerInfo} key={index++}>
                                    <p>{index}</p>
                                    <div className={styles.playerProfile}>
                                        {/* <img src={profileImgUrl} alt="profile" className={styles.profile} /> */}
                                        <span>{player.riotIdGameName}</span>
                                        <span>#{player.riotIdTagline}</span>
                                        <span>{player.puuid}</span>
                                        <span>summonerLevel : {player.summonerLevel}</span>
                                        {player.summonerName !== "" ? <span>Prev.summonerName : {player.summonerName}</span> : null}
                                        <img src={champImgUrl} alt="champion" className={styles.profile} />
                                        <span>Champion : {player.championName}</span>
                                    </div>
                                    <div className={styles.playInfoDetail}>
                                        <span>KDA : {player.kills}/{player.deaths}/{player.assists}</span>
                                        <span>item : {player.item0} {player.item1} {player.item2} {player.item3} {player.item4} {player.item5} {player.item6}</span>
                                        <span>spell : {player.spell1Casts} {player.spell2Casts} {player.spell3Casts}</span>
                                        <span>largest Multi Kill : {player.largestMultiKill}</span>
                                        {/* <span>pentaKills : {player.pentaKills}</span> */}
                                        <span>Individual Position : {player.individualPosition}</span>
                                        <span>lane : {player.lane}</span>
                                        <span>role	: {player.role}</span>
                                        <span>teamId : {player.teamId}</span>
                                        <span>team Position : {player.teamPosition}</span>
                                    </div>
                                    {index === 4 ? <p>{result[0]}</p> : index === 9 ? <p>{result[1]}</p> : null}
                                </div>
                            )
                        })
                    }
                </div>
                
            </div>
            {/* {JSON.stringify(matchInfo)} */}
            <div>
                <AvatarGroup>
                    {playerChampionImgs.slice(playerChampionImgs.length/2, playerChampionImgs.length).map((url, index) => (
                        <Avatar
                        key={index}
                        size="md"
                        src={"https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/" + url + ".png"}
                        />
                    ))}
                </AvatarGroup>
                {
                    playerData.slice(playerChampionImgs.length/2, playerChampionImgs.length).map((player: object) => {
                        const profileImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/${player.profileIcon}.png`
                        const champImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/${player.championName}.png`
                        return (
                            <div className={players[index+players.length/2] === puuid ? styles.thisUser : styles.playerInfo} key={index++}>
                                <p>{index}</p>
                                <div className={styles.playerProfile}>
                                    {/* <img src={profileImgUrl} alt="profile" className={styles.profile} /> */}
                                    <span>{player.riotIdGameName}</span>
                                    <span>#{player.riotIdTagline}</span>
                                    <span>summonerLevel : {player.summonerLevel}</span>
                                    {player.summonerName !== "" ? <span>Prev.summonerName : {player.summonerName}</span> : null}
                                    <img src={champImgUrl} alt="champion" className={styles.profile} />
                                    <span>Champion : {player.championName}</span>
                                </div>
                                <div className={styles.playInfo}>
                                    <span>KDA : {player.kills}/{player.deaths}/{player.assists}</span>
                                    <span>largest Multi Kill : {player.largestMultiKill}</span>
                                    {/* <span>pentaKills : {player.pentaKills}</span> */}
                                    <span>Individual Position : {player.individualPosition}</span>
                                    <span>lane : {player.lane}</span>
                                    <span>role	: {player.role}</span>
                                    <span>teamId : {player.teamId}</span>
                                    <span>team Position : {player.teamPosition}</span>
                                </div>
                                {index === 4 ? <p>{result[0]}</p> : index === 9 ? <p>{result[1]}</p> : null}
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )

}