import { Card, CardHeader, CardBody, CardFooter, User, Image, Divider } from "@nextui-org/react"

const demo: any = {
	high: [
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
	],
	similar: [
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
		{
			username: '피아노의자#KR1',
			iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png',
			mostChamps: [
				'Qiyana',
				'Warwick',
				'Ekko'
			],
			// `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`
			score: 4.3
		},
	]
}

export default function Recommendation(){
	return (
		<div>
			<h1 className="py-10 p-10">높은 듀오 승률</h1>
			<div className="flex justify-evenly">
				{demo.high.map((user: any, index: number) => (
					<Card key={index} isPressable>
						<CardHeader>
							<User
								avatarProps={{src: user.iconUrl}}
								description={`#${user.username.split('#')[1]}`}
								name={user.username.split('#')[0]}
							/>
						</CardHeader>
						<Divider/>
						<CardBody>
							<div className="flex">
								{user.mostChamps.map((champ: string) => (
									<Image 
										key={champ}
										alt={champ}
										src={`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`}
										width={50}
									/>
								))}
							</div>
						</CardBody>
						<Divider/>
						<CardFooter>
							<p>{user.score}</p>
						</CardFooter>
					</Card>
				))}
			</div>
			<h1 className="p-10">비슷한 플레이 스타일</h1>
			<div className="flex justify-evenly">
				{demo.similar.map((user: any, index: number) => (
					<Card key={index} isPressable>
						<CardHeader>
							<User
								avatarProps={{src: user.iconUrl}}
								description={`#${user.username.split('#')[1]}`}
								name={user.username.split('#')[0]}
							/>
						</CardHeader>
						<Divider/>
						<CardBody>
							<div className="flex">
								{user.mostChamps.map((champ: string) => (
									<Image 
										key={champ}
										alt={champ}
										src={`https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/${champ}.png`}
										width={50}
									/>
								))}
							</div>
						</CardBody>
						<Divider/>
						<CardFooter>
							<p>{user.score}</p>
						</CardFooter>
					</Card>
				))}
			</div>
		</div>
	)
}