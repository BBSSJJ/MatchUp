interface SummonerProps {
    puuid: string;
}

// 임시 컴포넌트
export default function Summoner({ puuid }: SummonerProps) {
    return (
        <div>
            <p>{puuid}</p>
        </div>
    )
}