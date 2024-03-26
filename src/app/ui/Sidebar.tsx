import React from "react";
import styles from "./Sidebar.module.css";
import {Button, Avatar} from "@nextui-org/react";

const SideBar: React.FC = () => {

	return (
		<div style={{
			position: 'fixed', 
			right: 0, 
			top: '4rem',
			bottom: 0, 
			width: '280px',
			color: 'white',
			backgroundColor: '#161A1E', 
			overflowY: 'auto',
			padding: '20px', 
			zIndex: 1000,
			boxShadow: '0 0 0.5px 0.15px #36c4be',
		}}>
			<p className={styles.title}>Friends</p>
			<div className="flex flex-col gap-4 items-center">
				<Avatar isBordered color="default" src="https://i.pravatar.cc/150?u=a042581f4e29026024d" />
				<Avatar isBordered color="primary" src="https://i.pravatar.cc/150?u=a04258a2462d826712d" />
				<Avatar isBordered color="secondary" src="https://i.pravatar.cc/150?u=a042581f4e29026704d" />
				<Avatar isBordered color="success" src="https://i.pravatar.cc/150?u=a04258114e29026302d" />
				<Avatar isBordered color="warning" src="https://i.pravatar.cc/150?u=a04258114e29026702d" />
				<Avatar isBordered color="danger" src="https://i.pravatar.cc/150?u=a04258114e29026708c" />
			</div>
			<Button className={styles.button} color="primary" variant="solid">Chats</Button>
		</div>
	);
};

export default SideBar;
