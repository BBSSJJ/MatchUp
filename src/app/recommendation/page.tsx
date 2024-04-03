'use client'

import { Card, CardHeader, CardBody, CardFooter, User, Image, Divider } from "@nextui-org/react"
import axios, { AxiosResponse } from "axios"
import { useState } from "react"
import { SERVER_API_URL } from "@/utils/instance-axios"
import { userInfoAtom } from "@/store/authAtom"
import { useAtom } from "jotai"
import { useSearchParams } from "next/navigation"

type Recommendation = {
  profile: number
	name: string
	tag: string
	tier: string
	rank: string
	wins: number
	losses: number
}

export default function Page(){
	const [enjoyingList, setEnjoyingList] = useState<Recommendation[]>([{
		profile: 1,
		name: "loading",
		tag: "loading",
		tier: "loading",
		rank: "loading",
		wins: 1,
		losses: 1,
	}])
	const [winnigList, setWinningList] = useState<Recommendation[]>([{
		profile: 1,
		name: "loading",
		tag: "loading",
		tier: "loading",
		rank: "loading",
		wins: 1,
		losses: 1,
	}])
	const [userInfo, setUserInfo] = useAtom<any>(userInfoAtom)
	const p = useSearchParams()
	const my_lane = p.get('myLine')
	const partner_lane = p.get('searchingLine')
	const mic = p.get('useMic')

	useState(() => {
		axios({
			method: 'get',
			url: `${SERVER_API_URL}/api/recommends/enjoying/${userInfo.userId}`,
			params: {my_lane, partner_lane, mic}
		})
			.then((response: AxiosResponse) => {
				setEnjoyingList(response.data.slice(0, 5))
			})
		
	axios({
		method: 'get',
		url: `${SERVER_API_URL}/api/recommends/winning/${userInfo.userId}`,
		params: {my_lane, partner_lane, mic}
	})
		.then((response: AxiosResponse) => {
			setWinningList(response.data.slice(0, 5))
		})
	})

	return (
		<div>
			<h1 className="py-10 p-10">높은 듀오 승률</h1>
			<div className="flex justify-evenly">
				{winnigList.length !== 0 ? (
					winnigList.map((user: Recommendation, index: number) => (
						<Card key={index} isPressable>
							<CardHeader>
								<User
									avatarProps={{src: `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/${user.profile}.png`}}
									description={`#${user.tag}`}
									name={user.name}
								/>
							</CardHeader>
							<Divider/>
							<CardBody>
							<div className="flex justify-around">
								<Image
									width={30}
									alt="tierIcon"
									src={user.tier !== "GRANDMASTER" ? `/Emblems/${user.tier.slice(0, 1)}.png` : '/Emblems/GM.png'}
								/>
								<p className="text-lg">{user.tier} {user.rank}</p>
							</div>
							</CardBody>
							<Divider/>
							<CardFooter>
							<div className="flex justify-between w-44">
								<div 
									className="bg-blue-700 flex items-center rounded-l-lg" 
									style={{
										width: `${(user.wins * 100) / (user.wins + user.losses)}%`,
										paddingLeft: "5%"
									}}
								>
									<p className="text-sm">{user.wins}승</p>
								</div>
								<div 
									className="bg-red-700 flex items-center flex-row-reverse rounded-r-lg" 
									style={{
										width: `${(user.losses * 100) / (user.wins + user.losses)}%`,
										paddingRight: "5%"
									}}
								>
									<p className="text-sm">{user.losses}패</p>
								</div>
							</div>
							</CardFooter>
						</Card>
					))
				) : (
					<h1>언랭은 안받아요</h1>
				)}
				<h1 v-if={winnigList.length == 1}>분석중입니다</h1>
			</div>
			<h1 className="p-10">비슷한 플레이 스타일</h1>
			<div className="flex justify-evenly">
				{enjoyingList.length !== 0 ? (
					enjoyingList.map((user: Recommendation, index: number) => (
						<Card key={index} isPressable>
							<CardHeader>
								<User
									avatarProps={{src: `https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/${user.profile}.png`}}
									description={`#${user.tag}`}
									name={user.name}
								/>
							</CardHeader>
							<Divider/>
							<CardBody>
							<div className="flex justify-around">
								<Image
									width={30}
									alt="tierIcon"
									src={user.tier !== "GRANDMASTER" ? `/Emblems/${user.tier.slice(0, 1)}.png` : '/Emblems/GM.png'}
								/>
								<p className="text-lg">{user.tier} {user.rank}</p>
							</div>
							</CardBody>
							<Divider/>
							<CardFooter>
							<div className="flex justify-between w-44">
								<div 
									className="bg-blue-700 flex items-center rounded-l-lg" 
									style={{
										width: `${(user.wins * 100) / (user.wins + user.losses)}%`,
										paddingLeft: "5%"
									}}
								>
									<p className="text-sm">{user.wins}승</p>
								</div>
								<div 
									className="bg-red-700 flex items-center flex-row-reverse rounded-r-lg" 
									style={{
										width: `${(user.losses * 100) / (user.wins + user.losses)}%`,
										paddingRight: "5%"
									}}
								>
									<p className="text-sm">{user.losses}패</p>
								</div>
							</div>
							</CardFooter>
						</Card>
					))
				) : (
					<h1>언랭은 안받아요</h1>
				)}
				<h1 v-if={winnigList.length == 1}>분석중입니다</h1>
			</div>
		</div>
	)
}