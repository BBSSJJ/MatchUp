import "../../styles/global.css";
import "./globals.css";
import type { Metadata } from "next";
import { Providers } from "./providers/providers";
import { AntdRegistry } from '@ant-design/nextjs-registry';
import NavigationBar from "./ui/Navbar";
import SideBar from "./ui/Sidebar";
import { tektur } from "./ui/fonts";
import { Provider as JotaiProvider } from 'jotai'
// import SockJS from "sockjs-client";

interface NewMetadata {
  title: string;
  description: string[];
}

export const metadata: NewMetadata = {
  title: "MATCHUP",
  description: [
    "MATCHUP은 빅데이터 분석을 기반으로 롤(League of Legend)를 함께 플레이할 유저를 추천합니다. 추천된 유저와 친구가 되어 실시간 음성 채팅, 화면 공유 등 다양한 기능을 이용할 수 있습니다. 또한 이용자는 영상이나 사진으로 플레이 상황을 공유하며 의견을 나눌 수 있습니다. 듀오와의 게임 전적 데이터도 쉽게 관리할 수 있습니다.",
    "MATCHUP recommends users to play League of Legends together based on big data analysis. Users recommended to each other can become friends and enjoy various features such as real-time voice chat, screen sharing, and more. Additionally, users can share gameplay situations and exchange opinions using videos or photos. Managing gaming records with duos is also made easy."
  ]
};

export default function RootLayout({
  children, // 페이지 또는 nested layout
}: Readonly<{
  children: React.ReactNode;
}>) {
  // const sock = new SockJS("http://localhost:3000")
  // sock.onopen = function() {
  //   console.log('open');
  //   sock.send('test');
  // };

  // sock.onmessage = function(e) {
  //   console.log('message', e.data);
  //   sock.close();
  // };

  // sock.onclose = function() {
  //   console.log('close');
  // };

  return (
    <html lang="en" className="dark">
      <body className={tektur.className}>
        <JotaiProvider>
          <Providers>
            <AntdRegistry>
              <NavigationBar />
              <div className="layout-container">
                <SideBar />
                <div className="main-content">
                  {children}
                </div>
              </div>
            </AntdRegistry>
          </Providers>
        </JotaiProvider>
      </body>
    </html>
  );
}
