import LobbyChat from "@/app/ui/lobby/lobbyChat"
import SockJS from "sockjs-client"
import { Client } from "@stomp/stompjs";

export default function Page() {
  // const sock = new SockJS("https://j10a405.p.ssafy.io/api/ws")

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
    <div>
      <LobbyChat />
    </div>
  )
}