'use client'

import React from "react";
import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell } from "@nextui-org/react"
import { demoLobbyChats } from "@/app/lib/placeholder-data"

export default function LobbyChat() {
  const columns = [
    {name: "소환사", uid: "summoner"},
    {name: "주 포지션", uid: "myPosition"},
    {name: "티어", uid: "tier"},
    {name: "찾는 포지션", uid: "searchingPosition"},
    {name: "승률", uid: "winningRate"},
    {name: "KDA", uid: "KDA"},
    {name: "티어", uid: "tier"},
  ]; 

  return (
    <div>
      <Table>
        <TableHeader columns={columns}>
          {(column) => (
            <TableColumn key={column.uid}>
              {column.name}
            </TableColumn>
          )}
        </TableHeader>
        <TableBody items={demoLobbyChats}>
          {(chat) => (
            <TableRow key={chat.id}>
              <TableCell>something</TableCell>
              <TableCell>something</TableCell>
              <TableCell>something</TableCell>
              <TableCell>something</TableCell>
              <TableCell>something</TableCell>
              <TableCell>something</TableCell>
              <TableCell>something</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  )
}