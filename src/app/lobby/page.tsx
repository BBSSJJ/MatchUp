import LobbyChat from "@/app/ui/lobby/lobbyChat"
import SockJS from "sockjs-client"
import { Client } from "@stomp/stompjs";

export default function Page() {
  const sock = new SockJS("https://matchup.site/api/ws")
  console.log(typeof sock)
  const client  = new Client({
    webSocketFactory() {
      sock
    },
    // brokerURL: "wss://matchup.site/api/ws",
    // debug: (str) => {
    //   console.log(str)
    // },
    onConnect(frame) {
      console.log('연결 성공' + frame)

      client.subscribe('/topic/recruit', (message) => {
        console.log(message)
      })
    },
  })
  client.activate()


  return (
    <div>
      <LobbyChat />
    </div>
  )
}