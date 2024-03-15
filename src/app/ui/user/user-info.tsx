import {Card, CardFooter, Image, Button, Badge} from "@nextui-org/react";
import styles from "./user-info.module.css"


interface UserData {
	tier: string;
	win: number;
	lose: number;
}

export default function UserProfile(data :UserData) {
	const keywords = ['트리플킬 장인', 'MVP', 'ACE', '슬로우 스타터', '불굴의 의지', '???']
	console.log(data)
	return (
		<div className={styles.container}>
			<div className={styles.item1}>
			<Badge content={data.tier} color="primary" className="w-[50px] h-[30px]" >
				<Card
					isFooterBlurred
					radius="lg"
					className="border-none h-[250px] w-[250px]"
				>
					<Image
						alt="Woman listing to music"
						className="object-center h-[250px] w-[250px]"
						src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Viego.png"
						
					/>
					<CardFooter className="justify-between before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[calc(100%_-_8px)] shadow-small ml-1 z-10">
						<p className="text-tiny text-white/80">User Id</p>
						<Button className="text-tiny text-white bg-black/20" variant="flat" color="default" radius="lg" size="sm">
							친구요청
						</Button>
					</CardFooter>
				</Card>
			</Badge>
				
			</div>
			
			<div className={styles.item2}>
				{
					keywords.map((keyword :string, index :number) => {
						return (
						<Button key={index} radius="full" className="bg-gradient-to-tr from-green-500 to-blue-500 text-white shadow-lg m-1 h-[25px]">
							{keyword}
						</Button>)
					})
				}
			</div>
			<div className={styles.item3}>
				<p>전적 정보</p>
				<p>{data.win} / {data.lose}</p>
				<p>{ data.win / (data.win + data.lose) }%</p>
				<p>티어</p>
			</div>
		</div>
	)
}