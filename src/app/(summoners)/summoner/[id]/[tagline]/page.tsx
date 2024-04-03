import SummonerInfo from "@/app/ui/summoner-info";
import styles from "./styles.module.css";

// 유저 아이디를 검색하면 나오는 페이지 
export default async function SummonerDetail({
  params: { id, tagline },
}: {
  params: { id :string, tagline :string };
}) {
  return (
    <div className={styles.container}>
      {/* <h3>{id} Information</h3> */}
      <SummonerInfo id={id} tagline={tagline}/>
    </div>
  )
}