import Image from "next/image"
import axios from "axios"
import MatchupChats from "../ui/onmatchup/matchupChats"

export default async function Page() {

  return (
    <div className="flex justify-between">
      <Image 
        src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
        alt="image"
        width={308}
        height={560}
        style={{opacity: 0.5}}
      />
      <MatchupChats />
      <Image 
        src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Akali_0.jpg"
        alt="image"
        width={308}
        height={560}
        style={{opacity: 0.5}}
      />
    </div>
  )
}