'use client'

import { 
  Table, TableHeader, TableColumn, TableBody, TableRow, TableCell, 
  User, Image, Textarea, Button, ButtonGroup, Listbox, ListboxItem,
  Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, 
  useDisclosure, Input, Select, SelectItem
} from "@nextui-org/react"
import { Client } from "@stomp/stompjs";
import axios from "axios";
import { useEffect, useState, useMemo } from "react";
import { ListboxWrapper } from "./ListboxWrapper";
import { isLoggedInAtom, userInfoAtom } from "@/store/authAtom";
import { useAtom } from "jotai";
import { getFirebaseToken } from "../../../../firebase/firebaseConfig";
import { SERVER_API_URL } from "@/utils/instance-axios";
import Link from "next/link";

export default function LobbyChat() {
  const [client, setClient] = useState<Client | null>(null)
  const [messages, setMessages] = useState<any[]>([])
  const [myPosition, setMyPosition] = useState(new Set([""]))
  const [searchingPosition, setSearchingPosition] = useState(new Set([""]))
  const [memo, setMemo] = useState('')
  var {isOpen, onOpen, onOpenChange} = useDisclosure()
  const [user, setUser] = useAtom<any>(userInfoAtom)
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
  const MAX_RETRY_COUNT = 3
  let retryCount = 0
  const [userDetail, setUserDetail] = useState<any>({})

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const requestPermission = () => {
        console.log('Requesting permission...');
        Notification.requestPermission().then((permission) => {
          if (permission === 'granted') {
            console.log('Notification permission granted.');
            // 퍼미션 허가 후 추가적으로 수행할 작업이 있다면 여기에 추가
          } else {
            console.log('Notification permission denied.');
          }
        })
        .then(async () => {
          if (isLoggedIn) { // 로그인 된 상태에만 요청을 보내도록
            const response = await fetch(`${SERVER_API_URL}/api/alarm`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({token: await getFirebaseToken() })
            })
    
            if (!response.ok) {
                console.error("클라이언트 토큰 등록 실패")
                if (retryCount < MAX_RETRY_COUNT) {
                  // console.log(`Retrying... (${retryCount + 1}/${MAX_RETRY_COUNT})`);
                  retryCount++;
                  requestPermission();
                } else {
                  console.log(response);
                }
            }
            console.log(response)
          }
        })
      };
      requestPermission();
    }
  
  }, []);

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
    
    getDetails()
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

  async function getDetails() {
    await axios({
      method: 'get',
      url: `https://matchup.site/api/statistics/summoners/details/users/${user.userId}`
    })
      .then((response) => {
        setUserDetail(response.data)
      })
  }

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
          <Link href={`https://matchup.site/user/${chat.userId}`}>
            <User
              avatarProps={{src: chat.iconUrl}}
              description={`#${chat.name.split('#')[1]}`.replaceAll('+', ' ')}
              name={chat.name.split('#')[0].replaceAll('+', ' ')}
            />
          </Link>
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
              className={(chat.win + chat.lose) ? "bg-blue-700 flex items-center rounded-l-lg" : "bg-slate-600 flex items-center rounded-l-lg"}
              style={{
                width: `${(chat.win + chat.lose) ? ((chat.win * 100) / (chat.win + chat.lose)) : 50}%`,
                paddingLeft: "5%"
              }}
            >
              {chat.win}승
            </div>
            <div 
              className={(chat.win + chat.lose) ? "bg-red-700 flex items-center flex-row-reverse rounded-r-lg" : "bg-slate-700 flex items-center flex-row-reverse rounded-r-lg"} 
              style={{
                width: `${(chat.win + chat.lose) ? ((chat.lose * 100) / (chat.win + chat.lose)) : 50}%`,
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
            <p className="text-base">{chat.death ? ((chat.kill + chat.assist) / chat.death).toFixed(2) : 0}</p>
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
          <Button color="danger" isDisabled={!isLoggedIn || user.userId !== chat.userId} className="text-sm" onPress={() => deleteMessage(chat.objectId)}>삭제</Button>
        )
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
          win: userDetail.win ? userDetail.win : 0, //최근 20판 승리?
          lose: userDetail.lose ? userDetail.lose : 0, //최근 20판 패배?
          kill: userDetail.killAvg ? userDetail.killAvg : 0, //최근 20판 킬?
          death: userDetail.deathAvg ? userDetail.deathAvg : 0, //최근 20판 데스?
          assist: userDetail.assistAvg ? userDetail.assistAvg : 0, //최근 20판 어시스트?
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
      <div className="flex justify-end p-5">
        <Button className="ml-10 font-bold" color="primary" onPress={() => {isLoggedIn ? onOpen() : window.alert("로그인이 필요합니다.")}}>글 작성하기</Button>
      </div>
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
