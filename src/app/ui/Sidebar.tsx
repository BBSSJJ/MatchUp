import React from "react";
import styles from "./Sidebar.module.css";
import {Card, CardHeader, CardBody, CardFooter, Avatar, Button} from "@nextui-org/react";

const SideBar: React.FC = () => {

	return (
		<div style={{
			position: 'fixed', 
			right: 0, 
			top: '4rem',
			bottom: 0, 
			width: '280px',
			height: "100vh",
			color: 'white',
			backgroundColor: '#161A1E', 
			overflowY: 'auto',
			padding: '20px', 
			zIndex: 1000,
			boxShadow: '0 0 0.5px 0.15px #36c4be',
		}}>
			<div>
				{/* 친구목록 */}
				<span className={styles.title}>Friends</span>
				<Button className="w-[10px] h-[20px]">toggle</Button>
				<div className="flex flex-col gap-4 items-center">
					<Card className="max-w-[340px]">
						<CardHeader className="justify-between">
							<div className="flex gap-5">
							<Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
							<div className="flex flex-col gap-1 items-start justify-center">
								<h4 className="text-small font-semibold leading-none text-default-600">Zoey Lang</h4>
								{/* <h5 className="text-small tracking-tight text-default-400">@zoeylang</h5> */}
							</div>
							</div>
							<Button
							className={"bg-transparent text-foreground border-default-200"}
							color="primary"
							radius="full"
							size="sm"
							variant={"bordered"}
							>
							match up
							</Button>
							<Button
							className={"bg-transparent text-foreground border-default-200"}
							color="primary"
							radius="full"
							size="sm"
							variant={"bordered"}
							>
							DM
							</Button>
						</CardHeader>
					</Card>
					<Card className="max-w-[340px]">
						<CardHeader className="justify-between">
							<div className="flex gap-5">
							<Avatar isBordered radius="full" size="md" src="https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png" />
							<div className="flex flex-col gap-1 items-start justify-center">
								<h4 className="text-small font-semibold leading-none text-default-600">Zoey Lang</h4>
								{/* <h5 className="text-small tracking-tight text-default-400">@zoeylang</h5> */}
							</div>
							</div>
							<Button
							className={"bg-transparent text-foreground border-default-200"}
							color="secondary"
							radius="full"
							size="sm"
							variant={"bordered"}
							>
							match up
							</Button>
							<Button
							className={"bg-transparent text-foreground border-default-200"}
							color="primary"
							radius="full"
							size="sm"
							variant={"bordered"}
							>
							DM
							</Button>
						</CardHeader>
					</Card>
				</div>
			</div>
			<div className="relative">
				<Button className={styles.chatButton} color="primary" variant="solid">Chats</Button>
			</div>
		</div>
	);
};

export default SideBar;
