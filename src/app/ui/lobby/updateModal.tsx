import { Modal, ModalContent, ModalHeader, ModalBody, Listbox, ListboxItem, Image, Input, ModalFooter, Button } from "@nextui-org/react"
import { ListboxWrapper } from "./ListboxWrapper"
import { useEffect, useState, useMemo } from "react";

export default function CreateModal({isOpen, onOpenChange, client, chat, userDetail}: {
  isOpen: boolean, 
  onOpenChange: () => void, 
  client: any, 
  chat: any, 
  userDetail: any
}) {
  const [myPosition, setMyPosition] = useState(new Set([chat.line]))
  const [searchingPosition, setSearchingPosition] = useState(new Set([chat.wishLine]))
  const [memo, setMemo] = useState(chat.content)

  function editMessage(chat: any) {
    if (client && client.connected) {
      client.publish({
        destination: '/app/recruit',
        body: JSON.stringify({
          method: 'update',
          objectId: chat.objectId,
          userId: chat.userId,
          name: chat.name,
          iconUrl: chat.iconUrl,
          tier: chat.tier,
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
                    defaultSelectedKeys={myPosition}
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
                    defaultSelectedKeys={searchingPosition}
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
              <Button color="primary" onPress={() => editMessage(chat)} onClick={onClose}>
                작성하기
              </Button>
            </ModalFooter>
          </>
        )}
      </ModalContent>
    </Modal>
  )
}