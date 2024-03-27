'use client'

import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell, User, Image, Textarea, button } from "@nextui-org/react"
import { Client } from "@stomp/stompjs";
import axios from "axios";
import { useEffect, useState } from "react";

export default function LobbyChat() {
  const [client, setClient] = useState<Client | null>(null)
  const [messages, setMessages] = useState<any[]>([])

  useEffect(() => {
    const stomp = new Client({
      brokerURL: "wss://matchup.site/api/ws",
    })
    setClient(stomp)

    stomp.activate()

    stomp.onConnect = () => {
      console.log('connected')
      stomp.subscribe('/topic/recruit', (message) => {
        // const parsedMessage = JSON.parse(message.body)
        console.log(message.body)
        // setMessages((prev: any) => [...prev, parsedMessage])
      })
    }

    axios({
      method: 'get',
      url: 'https://matchup.site/api/recruits'
    })
      .then((response) => {
        setMessages(response.data.list)
      })
  }, [])

  const columns = [
    {name: "소환사", uid: "summoner"},
    {name: "주 포지션", uid: "myPosition"},
    {name: "티어", uid: "tier"},
    {name: "찾는 포지션", uid: "searchingPosition"},
    {name: "승률", uid: "winningRate"},
    {name: "KDA", uid: "KDA"},
    {name: "메모", uid: "memo"},
    {name: "삭제", uid: "delete"}
  ];

  const renderCell = (chat: any, columnKey: any) => {
    switch (columnKey) {
      case "summoner":
        return (
          <User
            avatarProps={{src: chat.iconUrl}}
            description={chat.name.split(' ')[1]}
            name={chat.name.split(' ')[0]}
          />
        )
      case "myPosition":
        return (
          <Image 
            width={30}
            alt="positionIcon"
            src={`/positionIcons/${chat.line}.png`}
          />
        )
      case "tier":
        return (
          <p className="text-lg">{chat.tier}</p>
        )
      case "searchingPosition":
        return (
          <Image 
            width={30}
            alt="positionIcon"
            src={`/positionIcons/${chat.wishLine}.png`}
          />
        )
      case "winningRate":
        return (
          <div className="flex justify-between w-40">
            <div 
              className="bg-blue-700 flex items-center rounded-l-lg" 
              style={{
                width: `${(chat.win * 100) / (chat.win + chat.lose)}%`,
                paddingLeft: "5%"
              }}
            >
              {chat.win}승
            </div>
            <div 
              className="bg-red-700 flex items-center flex-row-reverse rounded-r-lg" 
              style={{
                width: `${(chat.lose * 100) / (chat.win + chat.lose)}%`,
                paddingRight: "5%"
              }}
            >
              {chat.lose}패
            </div>
          </div>
        )
      case "KDA":
        return (
          <div>
            <p className="text-xs">{chat.kill} / {chat.death} / {chat.assist}</p>
            <p className="text-base">{((chat.kill + chat.assist) / chat.death).toFixed(2)}</p>
          </div>
        )
      case "memo":
        return (
          <Textarea 
            isReadOnly
            defaultValue={chat.content}
            size="sm"
            className="w-40"
          />
        )
      case "delete":
        return (
          <button>
            삭제
          </button>
        )
    }
  }

  function sendMessage() {
    if (client && client.connected) {
      client.publish({
        destination: '/app/recruit',
        body: JSON.stringify({
          method: 'create',
          userId: 1,
          name: 'check #KR1',
          iconUrl: 'https://ddragon.leagueoflegends.com/cdn/14.5.1/img/profileicon/1614.png',
          tier: 'P1',
          line: 'top',
          wishLine: 'jungle',
          gameType: 'solo rank',
          content: '부캐 듀오 구합니다.',
          win: 64,
          lose: 74,
          kill: 2.1,
          death: 3.4,
          assist: 13.8,
        })
      })
    }
  }

  function deleteMessage(id: string) {
    if (client && client.connected) {
      client.publish({
        destination: '/app/recruit',
        body: JSON.stringify({
          method: 'delete',
          objectId: id,
        })
      })
    }
  }



  return (
    <div>
      <button>Send</button>
      {messages ? (
        <div>
          <Table aria-label="Chat Table">
            <TableHeader columns={columns}>
              {(column) => (
                <TableColumn key={column.uid} align="center" className="text-base">
                  {column.name}
                </TableColumn>
              )}
            </TableHeader>
            <TableBody items={messages}>
              {(chat) => (
                <TableRow key={chat.objectId} >
                  {(columnKey) => <TableCell>{renderCell(chat, columnKey)}</TableCell>}
                </TableRow>
              )}
            </TableBody>
          </Table>
        </div>
      ) : null}
    </div>
  )
}