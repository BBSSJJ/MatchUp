import { Modal, ModalContent, ModalHeader, ModalBody, Listbox, ListboxItem, Image, Input, ModalFooter, Button } from "@nextui-org/react"
import { ListboxWrapper } from "./ListboxWrapper"
import { useEffect, useState, useMemo } from "react";

export default function CreateModal({isOpen, onOpenChange, client, user, userDetail}: {
  isOpen: boolean, 
  onOpenChange: () => void, 
  client: any, 
  user: any, 
  userDetail: any
}) {
  const [myPosition, setMyPosition] = useState(new Set([""]))
  const [searchingPosition, setSearchingPosition] = useState(new Set([""]))
  const [memo, setMemo] = useState('')

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

  const selectedMyPosition = useMemo(
    () => Array.from(myPosition).join(", "),
    [myPosition]
  )

  const selectedSearchingPosition = useMemo(
    () => Array.from(searchingPosition).join(", "),
    [searchingPosition]
  )
  return (
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
  )
}