'use client'

import React from "react";
import { useCallback } from "react";
import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell, User, Image, Textarea } from "@nextui-org/react"
import { demoLobbyChats } from "@/app/lib/placeholder-data"


export default function LobbyChat() {
  const columns = [
    {name: "소환사", uid: "summoner"},
    {name: "주 포지션", uid: "myPosition"},
    {name: "티어", uid: "tier"},
    {name: "찾는 포지션", uid: "searchingPosition"},
    {name: "승률", uid: "winningRate"},
    {name: "KDA", uid: "KDA"},
    {name: "메모", uid: "memo"},
  ];

  const renderCell = (chat: any, columnKey: any) => {
    switch (columnKey) {
      case "summoner":
        return (
          <User
            avatarProps={{src: chat.profileIcon}}
            description={chat.summonerTag}
            name={chat.summonerName}
          />
        )
      case "myPosition":
        return (
          <Image 
            width={30}
            alt="positionIcon"
            src={`/positionIcons/${chat.myPosition}.png`}
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
            src={`/positionIcons/${chat.searchingPosition}.png`}
          />
        )
      case "winningRate":
        return (
          <div className="flex justify-between w-40">
            <div 
              className="bg-blue-700 flex items-center rounded-l-lg" 
              style={{
                width: `${(chat.wins * 100) / (chat.wins + chat.loses)}%`,
                paddingLeft: "5%"
              }}
            >
              {chat.wins}승
            </div>
            <div 
              className="bg-red-700 flex items-center flex-row-reverse rounded-r-lg" 
              style={{
                width: `${(chat.loses * 100) / (chat.wins + chat.loses)}%`,
                paddingRight: "5%"
              }}
            >
              {chat.loses}패
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
            defaultValue={chat.memo}
            size="sm"
            className="w-40"
          />
        )
    }
  }

  return (
    <div>
      <Table aria-label="Chat Table">
        <TableHeader columns={columns}>
          {(column) => (
            <TableColumn key={column.uid} align="center" className="text-base">
              {column.name}
            </TableColumn>
          )}
        </TableHeader>
        <TableBody items={demoLobbyChats}>
          {(chat) => (
            <TableRow key={chat.id} >
              {(columnKey) => <TableCell>{renderCell(chat, columnKey)}</TableCell>}
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  )
}