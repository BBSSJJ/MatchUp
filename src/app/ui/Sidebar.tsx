const SideBar: React.FC = () => {
	return (
		<div style={{
			position: 'fixed', // 고정 위치
			right: 0, // 오른쪽에 고정
			top: '4rem', // 상단에서 시작
			bottom: 0, // 하단까지 뻗음
			width: '20%',
			color: 'white',
			backgroundColor: '#161A1E', // 배경색
			overflowY: 'auto', // 내용이 많아질 경우 스크롤
			padding: '20px', // 내부 패딩
			zIndex: 1000,
		}}>
			<p>친구목록</p>
			<button>채팅목록</button>
		</div>
	);
};

export default SideBar;
