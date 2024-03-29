'use client'

import { 
  Table, TableHeader, TableColumn, TableBody, TableRow, TableCell, 
  User, Image, Textarea, Button, ButtonGroup, Listbox, ListboxItem,
  Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, 
  useDisclosure, Input
} from "@nextui-org/react"
import { Client } from "@stomp/stompjs";
import axios from "axios";
import { useEffect, useState, useMemo } from "react";
import { ListboxWrapper } from "./ListboxWrapper";
import { isLoggedInAtom, userInfoAtom } from "@/store/authAtom";
import { useAtom } from "jotai";

export default function LobbyChat() {
  const [client, setClient] = useState<Client | null>(null)
  const [messages, setMessages] = useState<any[]>([])
  const [myPosition, setMyPosition] = useState(new Set([""]))
  const [searchingPosition, setSearchingPosition] = useState(new Set([""]))
  const [memo, setMemo] = useState('')
  var {isOpen, onOpen, onOpenChange} = useDisclosure()
  const [user, setUser] = useAtom<any>(userInfoAtom)
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)

  useEffect(() => {
    getRecruits()

    const stomp = new Client({
      brokerURL: "wss://matchup.site/api/ws",
    })
    setClient(stomp)

    stomp.activate()

    stomp.onConnect = () => {
      stomp.subscribe('/topic/recruit', () => {
        getRecruits()
      })
    }

  }, [])

  function getRecruits() {
    axios({
      method: 'get',
      url: 'https://matchup.site/api/recruits'
    })
      .then((response) => {
        setMessages(response.data.list.reverse())
      })
  }

  // if (messages.length === 0) {
  //   getRecruits()
  // }

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
            description={`#${chat.name.split('#')[1]}`}
            name={chat.name.split('#')[0]}
          />
        )
      case "myPosition":
        return (
          <div className="pl-4">
            <Image 
              width={30}
              alt="positionIcon"
              src={`/positionIcons/${chat.line}.png`}
            />
          </div>
        )
      case "tier":
        return (
          <div className="flex justify-around">
            <Image
              width={30}
              alt="tierIcon"
              src={`/Emblems/${chat.tier.slice(0, -1)}.png`}
            />
            <p className="text-lg">{chat.tier !== 'nundefined' ? chat.tier : "UR"}</p>
          </div>
        )
      case "searchingPosition":
        return (
          <div className="pl-6">
            <Image 
              width={30}
              alt="positionIcon"
              src={`/positionIcons/${chat.wishLine}.png`}
            />
          </div>
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
          <ButtonGroup size="sm">
            <Button color="warning" isDisabled className="text-sm text-white">수정</Button>
            <Button color="danger" className="text-sm" onPress={() => deleteMessage(chat.objectId)}>삭제</Button>
          </ButtonGroup>
        )
    }
  }

  const demoUserInfo = {
    "userId":6,
    "role":"ROLE_USER",
    "riotAccount":{
      "id":"azZhIpYEutVjtlT7il9eUX1Gjv_UcgLeukxNSiBtpafNHA",
      "summonerProfile":{
        "name":"피아노의자",
        "tag":"KR1",
        "iconUrl":"https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/5.png",
        "level":9
      },
      "tier":"no+league+data",
      "leagueRank":"no+league+data",
      "leaguePoint":0
    }
  }

  const rankToNumber: any = {
    'I': '1',
    'II': '2',
    'III': '3',
    'IV': '4'
  }

  function sendMessage() {
    if (client && client.connected) {
      client.publish({
        destination: '/app/recruit',
        body: JSON.stringify({
          method: 'create',
          userId: user.userId,
          name: user.riotAccount.summonerProfile.name + '#' + user.riotAccount.summonerProfile.tag,
          iconUrl: user.riotAccount.summonerProfile.iconUrl,
          tier: user.riotAccount.tier.slice(0, 1) + rankToNumber[user.riotAccount.leagueRank],
          line: selectedMyPosition,
          wishLine: selectedSearchingPosition,
          gameType: 'solo rank',
          content: memo,
          win: 64, //최근 20판 승리?
          lose: 74, //최근 20판 패배?
          kill: 2.1, //최근 20판 킬?
          death: 3.4, //최근 20판 데스?
          assist: 13.8, //최근 20판 어시스트?
        })
      })
      setMyPosition(new Set(['']))
      setSearchingPosition(new Set(['']))
      setMemo('')
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

  const selectedMyPosition = useMemo(
    () => Array.from(myPosition).join(", "),
    [myPosition]
  )

  const selectedSearchingPosition = useMemo(
    () => Array.from(searchingPosition).join(", "),
    [searchingPosition]
  )

  return (
    <div>
      <Button onPress={() => {isLoggedIn ? onOpen() : window.alert("로그인이 필요합니다.")}}>작성하기</Button>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange} size="xl">
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">정보 입력</ModalHeader>
              <ModalBody>
                <div className="flex gap-2">
                  <ListboxWrapper>
                    <p className="text-small text-default-600 text-center">주 포지션</p>
                    <br />
                    <Listbox 
                      aria-label="myPosition"
                      variant="flat"
                      selectionMode="single"
                      selectedKeys={myPosition}
                      onSelectionChange={(keys: any) => setMyPosition(keys)}
                    >
                      <ListboxItem 
                        key="top" 
                        startContent={
                          <Image 
                            width={20}
                            alt="top"
                            src={`/positionIcons/top.png`}
                          />
                        }>탑</ListboxItem>
                      <ListboxItem 
                        key="jungle" 
                        startContent={
                          <Image 
                            width={20}
                            alt="jungle"
                            src={`/positionIcons/jungle.png`}
                          />
                        }>정글</ListboxItem>
                      <ListboxItem 
                        key="mid" 
                        startContent={
                          <Image 
                            width={20}
                            alt="mid"
                            src={`/positionIcons/mid.png`}
                          />
                        }>미드</ListboxItem>
                      <ListboxItem 
                        key="bottom" 
                        startContent={
                          <Image 
                            width={20}
                            alt="bottom"
                            src={`/positionIcons/bottom.png`}
                          />
                        }>원딜</ListboxItem>
                      <ListboxItem 
                        key="support" 
                        startContent={
                          <Image 
                            width={20}
                            alt="support"
                            src={`/positionIcons/support.png`}
                          />
                        }>서포터</ListboxItem>
                    </Listbox>
                  </ListboxWrapper>
                  <ListboxWrapper>
                    <p className="text-small text-default-600 text-center">찾는 포지션</p>
                    <br />
                    <Listbox 
                      aria-label="searchingPosition"
                      variant="flat"
                      selectionMode="single"
                      selectedKeys={searchingPosition}
                      onSelectionChange={(keys: any) => setSearchingPosition(keys)}
                    >
                      <ListboxItem 
                        key="top" 
                        startContent={
                          <Image 
                            width={20}
                            alt="top"
                            src={`/positionIcons/top.png`}
                          />
                        }>탑</ListboxItem>
                      <ListboxItem 
                        key="jungle" 
                        startContent={
                          <Image 
                            width={20}
                            alt="jungle"
                            src={`/positionIcons/jungle.png`}
                          />
                        }>정글</ListboxItem>
                      <ListboxItem 
                        key="mid" 
                        startContent={
                          <Image 
                            width={20}
                            alt="mid"
                            src={`/positionIcons/mid.png`}
                          />
                        }>미드</ListboxItem>
                      <ListboxItem 
                        key="bottom" 
                        startContent={
                          <Image 
                            width={20}
                            alt="bottom"
                            src={`/positionIcons/bottom.png`}
                          />
                        }>원딜</ListboxItem>
                      <ListboxItem 
                        key="support" 
                        startContent={
                          <Image 
                            width={20}
                            alt="support"
                            src={`/positionIcons/support.png`}
                          />
                        }>서포터</ListboxItem>
                    </Listbox>
                  </ListboxWrapper>
                </div>
                <Input label="메모" placeholder="메모를 적어주세요." onValueChange={setMemo}/>
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="flat" className="text-white" onPress={onClose}>
                  닫기
                </Button>
                <Button color="primary" onPress={sendMessage} onClick={onClose}>
                  작성하기
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
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
            <TableBody items={messages} emptyContent={"작성된 글이 없습니다."}>
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