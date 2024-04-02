"use client"
import { Avatar, AvatarGroup, Button, Image } from "@nextui-org/react";
import styles from "./match-info.module.css"
import { useState, useEffect } from 'react';

interface Participant {
    championName: string;
    riotIdGameName: string;
    puuid: string;
    item0: number;
    item1: number;
    item2: number;
    item3: number;
    item4: number;
    item5: number;
    item6: number;
    riotIdTagline: string;
    summonerLevel: number;
    summonerName: string;
    kills: number;
    deaths: number;
    assists: number;
    largestMultiKill: number;
    individualPosition?: string;
    role?: string;
    teamPosition?: string;
    profileIcon: number;
}

interface Metadata {
    participants: string[];
}


interface Team {
    win: boolean;
}

interface Info {
    participants: Participant[];
    teams: Team[];
    gameDuration: number;
}

// api 요청을 보내서(summoner id, tagline) 받을 응답
export interface ResponseData {
    metadata: Metadata;
    info: Info;
}

// 나중에 팀별 정보는 컴포넌트로 따로 분리할 예정
export default function MatchDetail({ match, id } : {
    match :ResponseData;
    id :string // 해당 소환사 puuid
}) {
    const [toggle, setToggle] :[boolean, React.Dispatch<React.SetStateAction<boolean>>] = useState(true);

    // const datas :Info | undefined = data?.info;
    const playerData = match.metadata.participants // 함께한 플레이어 puuid 10개
    // const players = data?.metadata?.participants;
    const gameDuration = match.info.gameDuration // 경기 시간
    const teamData = match.info.teams // 승패 정보 
    // console.log("props로 받은 데이터 확인 : ", data)
    const playerDetail = match.info.participants
    const playerDetail1 = playerDetail.slice(0, playerDetail.length / 2)
    const playerDetail2 = playerDetail.slice(playerDetail.length / 2, playerDetail.length)
    const playerChampionImgs = playerDetail?.map((player) => {
        return player.championName;
    }) // 챔피언 이름 10개 수집 -> 이미지 가져옴
    // const playerSummonerIds = playerData?.map((item) => {
    //     return item.riotIdGameName;
    // }) // 소환사명
    console.log("teamData", teamData);
    const result = teamData?.map((team) => {
        if (team.win === true) {
            return 'win';
        } else {
            return 'lose';
        }
    }) // ['win', 'lose']
    console.log("result", result)
    const team1 = playerData?.slice(0, playerData.length / 2) // 전팀 5인 puuid
    const team2 = playerData?.slice(playerData.length / 2, playerData.length) // 후팀 5인 puuid
    const resultOfThisUser = team1.includes(id) ? result[0] : result[1] // 검색된 소환사의 해당 경기 결과
    console.log("전팀에 포함되는가?", team1.includes(id))
    console.log(team1, id)
    console.log(resultOfThisUser, "result");
    
    return (
        <main>
            <div className={styles.container}>
                <div className={styles.match}>
                    {/* <p>matchId : {matchId}</p> */}
                    <div className={styles.playInfo}>
                        <p className={resultOfThisUser === 'win' ? styles.winText : styles.loseText}>{resultOfThisUser}</p>
                        <p className={styles.duration}>{Math.trunc(gameDuration / 60)}m {Math.trunc(((gameDuration / 60) - Math.trunc(gameDuration / 60)) * 60)}s</p>
                        <AvatarGroup>
                            {playerChampionImgs && playerChampionImgs.length && playerChampionImgs.slice(0, playerChampionImgs.length / 2).map((url, index) => (
                                <Avatar
                                    key={index}
                                    size="md"
                                    src={"https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/" + url + ".png"}
                                />
                            ))}
                        </AvatarGroup>
                        VS
                        <AvatarGroup>
                        {playerChampionImgs && playerChampionImgs.length > 0 && playerChampionImgs.slice(playerChampionImgs.length / 2, playerChampionImgs.length).map((url: string, index: number) => (
                            <Avatar
                                key={index}
                                size="md"
                                src={"https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/" + url + ".png"}
                            />
                        ))}
                        </AvatarGroup>
                        <Button color="primary" variant="shadow" onClick={() => setToggle(prev => !prev)}>
                            {toggle ? 'More' : 'Close'}
                        </Button>
                    </div>
                    <div className={toggle ? styles.hide : ""}>
                        {
                            playerDetail1.map((player :Participant, index :number) => {
                                const champImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/${player.championName}.png`
                                const items = [player.item0, player.item1, player.item2, player.item3, player.item4, player.item5, player.item6];
                                return (
                                        <div className={`${team1[index] === id ? styles.thisUser : styles.playerInfo} ${result[0] === 'win' ? styles.win : styles.lose}`} key={index}>
                                        {/* <p>{index}</p> */}
                                        <div className={styles.playerProfile}>
                                            <Image
                                                width={50}
                                                src={champImgUrl}
                                                isZoomed
                                            />
                                            {/* <img src={profileImgUrl} alt="profile" className={styles.profile} /> */}
                                            <span>{player.riotIdGameName}</span>
                                            <span>#{player.riotIdTagline}</span>
                                            {/* <span>{player.puuid}</span> */}
                                            <span>Level : {player.summonerLevel}</span>
                                            {player.summonerName !== "" ? <span>Prev. {player.summonerName}</span> : null}
                                           
                                            {/* <img src={champImgUrl} alt="champion" className={styles.profile} /> */}
                                            <span>Champion : {player.championName}</span>
                                        </div>
                                        <div className={styles.playInfoDetail}>
                                            <div className={styles.playInfoKDA}>
                                                <span className={styles.kda}>KDA : {player.kills}/{player.deaths}/{player.assists}</span>
                                                {items.map((item, index) => {
                                                    return (
                                                        <Image 
                                                            src={'https://ddragon.leagueoflegends.com/cdn/14.5.1/img/item/' + item + '.png'}
                                                            key={index}
                                                            width={20}
                                                            height={20}
                                                            isBlurred
                                                            fallbackSrc="https://via.placeholder.com/300x200"
                                                        />
                                                        // <img className={styles.itemImg} key={index} src={'https://ddragon.leagueoflegends.com/cdn/14.5.1/img/item/' + item + '.png'} alt="item" />
                                                    )
                                                })}
                                            </div>
                                            <div className={styles.playInfoDetailText}>
                                                {/* <span>spell : {player.spell1Casts} {player.spell2Casts} {player.spell3Casts}</span> */}
                                                <span>largest Multi Kill : {player.largestMultiKill}</span>
                                                {/* <span>pentaKills : {player.pentaKills}</span> */}
                                                <span>Individual Position : {player.individualPosition}</span>
                                                {/* <span>lane : {player.lane}</span> */}
                                                <span>role	: {player.role}</span>
                                                {/* <span>teamId : {player.teamId}</span> */}
                                                <span>team Position : {player.teamPosition}</span>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
                <div className={toggle ? styles.hide : ""}>
                    {
                        playerDetail2.map((player :Participant, index :number) => {
                            const profileImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/${player.profileIcon}.png`
                            const champImgUrl = `https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/${player.championName}.png`
                            const items = [player.item0, player.item1, player.item2, player.item3, player.item4, player.item5, player.item6];
                            return (
                                <div className={`${team2[index] === id ? styles.thisUser : styles.playerInfo} ${result[1] === 'win' ? styles.win : styles.lose}`} key={index}>
                                    {/* <p>{index}</p> */}
                                    <div className={styles.playerProfile}>
                                        <Image
                                            width={50}
                                            src={champImgUrl}
                                            isZoomed
                                        />
                                        {/* <img src={profileImgUrl} alt="profile" className={styles.profile} /> */}
                                        <span>{player.riotIdGameName}</span>
                                        <span>#{player.riotIdTagline}</span>
                                        <span>Level : {player.summonerLevel}</span>
                                        {player.summonerName !== "" ? <span>Prev. {player.summonerName}</span> : null}
                                        {/* <img src={champImgUrl} alt="champion" className={styles.profile} /> */}
                                        <span>Champion : {player.championName}</span>
                                    </div>
                                    <div className={styles.playInfoDetail}>
                                        <div className={styles.playInfoKDA}>
                                            <span className={styles.kda}>KDA : {player.kills}/{player.deaths}/{player.assists}</span>
                                            {items.map((item, index) => {
                                                return (
                                                    <Image 
                                                        src={'https://ddragon.leagueoflegends.com/cdn/14.5.1/img/item/' + item + '.png'}
                                                        key={index}
                                                        width={20}
                                                        height={20}
                                                        isBlurred
                                                        fallbackSrc="https://via.placeholder.com/300x200"
                                                    />
                                                    // <img className={styles.itemImg} key={index} src={'https://ddragon.leagueoflegends.com/cdn/14.5.1/img/item/' + item + '.png'} alt="item" />
                                                )
                                            })}
                                        </div>
                                        <div className={styles.playInfoDetailText}>
                                            <p>largest Multi Kill : {player.largestMultiKill}</p>
                                            {/* <span>pentaKills : {player.pentaKills}</span> */}
                                            <span>Individual Position : {player.individualPosition}</span>
                                            {/* <span>lane : {player.lane}</span> */}
                                            <span>role	: {player.role}</span>
                                            {/* <span>teamId : {player.teamId}</span> */}
                                            <span>team Position : {player.teamPosition}</span>
                                        </div>
                                    </div>
                                </div>
                            )
                        })
                    }
                </div>
            </div>
        </main>
    )
}