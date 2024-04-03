import UserProfile from "@/app/ui/user/user-info"
import styles from "./styles.module.css"
import { RIOT_API_KEY } from "@/app/ui/summoner-info";
import { UserData } from "@/app/ui/user/user-info";
import useSWR from "swr";
import { SERVER_API_URL } from "@/utils/instance-axios";

const userFetcher = async (url:string) => {
    const response = await fetch(url); // 서버로부터 데이터 가져오기
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    return response.json(); // JSON 형식으로 변환하여 반환
}

export default async function UserPage({
  params: { id },
}: {
  params: { id :string, };
}) {
	// useEffect(() => {
	// 	const FetchImg = async () => {
	// 		const {data: records,  error: recordsError, isLoading: recordsLoading } = useSWR(
	// 			`${SERVER_API_URL}/api/statistics/summoners/details/users/${id}`,
	// 			userFetcher,
	// 			{
	// 				onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
	// 				if (error.status === 401) return
	// 				if (error.status === 500) return 'unranked'
	// 				}, 
	// 				revalidateOnFocus: false,
	// 				revalidateOnMount: true,
	// 				revalidateIfStale: true,
	// 			},
	// 		)
	// 		const container = document.querySelector('.container') as HTMLElement
	// 		const newImageUrl = `https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${records?.latestChampion}_0.jpg`
	// 		if (container) {
	// 		  container.style.backgroundImage = `url('${newImageUrl}')`
	// 		}
	// 	}
	// 	FetchImg()
	// }, [])
	

	return (
		<>
			<UserProfile userId={id}/>
		</>
		// <div className={styles.container}>
		// 	{/* <h3>User Page :{id}</h3> */}
		// </div>
	)
}

// function useEffect(arg0: () => void, arg1: never[]) {
// 	throw new Error("Function not implemented.");
// }
   