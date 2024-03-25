import React from "react";
import styles from "./Sidebar.module.css";
import {Button, Avatar} from "@nextui-org/react";

const SideBar: React.FC = () => {

	return (
		<div style={{
			position: 'fixed', // 고정 위치
			right: 0, // 오른쪽에 고정
			top: '4rem', // 상단에서 시작
			bottom: 0, // 하단까지 뻗음
			width: '280px',
			color: 'white',
			backgroundColor: '#333c44', // 배경색
			overflowY: 'auto', // 내용이 많아질 경우 스크롤
			padding: '20px', // 내부 패딩
			zIndex: 1000,
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
