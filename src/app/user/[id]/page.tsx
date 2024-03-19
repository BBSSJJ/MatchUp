import UserProfile from "@/app/ui/user/user-info"
import styles from "./styles.module.css"
import { RIOT_API_KEY } from "@/app/ui/summoner-info";

const encryptedSummonerId = 'ALCPx8GgXfWuiYEmZvQzzuuTeINIORAsA3FA_SuuOf-fpw';
async function GetTier(encryptedSummonerId :string) {
	try {
		// Make a fetch request
		const response = await fetch(`https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/${encryptedSummonerId}`,
		{
            headers : { "X-Riot-Token": RIOT_API_KEY }
        });
		
		
		// Check if response is successful
		if (!response.ok) {
			throw new Error('Failed to fetch data');
		}

		// Parse response data
		const data = await response.json();
		const userData =  {
			tier : data.tier,
			win: data.wins,
			lose : data.losses,
		}
		return userData;
		} catch (error) {
			// Handle errors
			console.log(error);
		}
}


export default async function UserPage({
  params: { id },
}: {
  params: { id :string, };
}) {
	
	const UserData = await GetTier(encryptedSummonerId)
	console.log(UserData, 'Userdata');

	return (
		<div className={styles.container}>
			<h3>User Page :{id}</h3>
			<UserProfile data= {UserData}/>
		</div>
	)
}
   