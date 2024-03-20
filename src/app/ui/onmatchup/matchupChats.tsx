import { demoChats } from "@/app/lib/placeholder-data"
import '@/app/ui/onmatchup/matchupChats.css'
import { ScrollShadow } from "@nextui-org/react"

export default function MatchupChats() {
  const me = "me"

  return (
    // <div className="chatbox">
    //   {demoChats.list.map((chat) => {
    //     return(
    //       <div key=''>
    //         <span style={{color: chat.sender == me ? "#C89B3C" : "#A09B8C"}}>{chat.sender}</span>:
    //         <span> {chat.content}</span>
    //       </div>
    //     )
    //   })}
    // </div>
    <ScrollShadow className="chatbox">
      {demoChats.list.map((chat) => {
        return(
          <div key=''>
            <span style={{color: chat.sender == me ? "#C89B3C" : "#A09B8C"}}>{chat.sender}</span>:
            <span> {chat.content}</span>
          </div>
        )
      })}
    </ScrollShadow>
  )
}