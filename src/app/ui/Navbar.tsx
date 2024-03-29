"use client"
import React, { useState, MouseEvent } from "react";
import { Switch, Navbar, NavbarBrand, NavbarContent, NavbarItem, Image, Input, User, Select, SelectItem, Badge, Button, Popover, PopoverTrigger, PopoverContent } from "@nextui-org/react";
import { usePathname, useRouter } from "next/navigation";
import Link from "next/link";
import styles from "./Navbar.module.css"
import { SearchIcon } from "./SearchIcon";
import { motion } from "framer-motion"
import { atom, useAtom } from 'jotai'
import { isLoggedInAtom, userInfoAtom } from '@/store/authAtom'
import { SERVER_API_URL } from "@/utils/instance-axios";

interface User {
  userId: number;
  role: string;
  riotAccount: {
    id: string;
    summonerProfile: {
      name: string;
      tag: string;
      iconUrl: string;
      level: number;
    };
    tier: string;
    leagueRank: string;
    leaguePoint: number;
  };
}

export default function NavigationBar() {
  // react hook 사용
  const path = usePathname();
  const [keyword, setKeyword] = useState("");
  const router = useRouter();
  
  // 로그인 상태 확인
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)
  const [user, setUser] = useAtom<any>(userInfoAtom)
  // 로그아웃 버튼 클릭
  const handleLogout = async () => {
    const response = await fetch(`${SERVER_API_URL}/api/users/logout`)
    
    if(response.ok) {
      setIsLoggedIn(false)
      // setUser(null)
      console.log("로그아웃 완료")
    }
  }

  const handleLogin = () => {
    router.push('/login');
  }

  const handleClick = () => {
    router.push(`/summoner/${keyword}`)
  }
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    router.push(`/summoner/${keyword}`)
  }

  const handleClear = (event: React.ChangeEvent<HTMLInputElement>) => {
    event.target.value = "";
  }

  const [myLine, setMyLine] = useState<string>('')
  const [searchingLine, setSearchingLine] = useState<string>('')
  const [useMic, setUseMic] = useState<boolean>(true)
 
  return (
    <Navbar isBordered className={styles.nav}>
      <NavbarBrand className="mr-4">
        <Link href="/lobby" className="font-bold text-inherit">
          <div className="flex items-center">
            <Image
              className="w-[40px] h-[40px]"
              src="/logo.png"
            />
            <span>MatchUP</span>
          </div>
        </Link>
      </NavbarBrand>
      <NavbarContent justify="start">
        <NavbarItem>
          <Link color="foreground" className={path === '/champion' ? styles.active : ""} href="/champion">
            Champion
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link href="/article" className={path === '/article' ? styles.active : ""}>
            MatchUP Zone
          </Link>
        </NavbarItem>
        <Popover placement="bottom" offset={10}>
          <PopoverTrigger>
            <NavbarItem>
              <a className={path === '/recommendation' ? styles.active : ""}>
                Match
              </a>
            </NavbarItem>
          </PopoverTrigger>
          {/* 추천 버튼을 누르면 먼저 뜨는 모달창 */}
          <PopoverContent className="w-[250px]">
            {(titleProps) => (
              <div className="px-1 py-2 w-full">
                <p className="text-small font-bold text-foreground" {...titleProps}>
                  추천에 참고할 정보를 입력해주세요
                </p>
                <br />
                <div className="mt-2 flex flex-col gap-2 w-full">
                  <div className="flex">
                    <p className="ml-2 mr-10 text-base">마이크</p>
                    <Switch defaultSelected onChange={() => setUseMic(!useMic)} />
                  </div>
                  <Select label="내 라인" size="sm" onChange={(e) => setMyLine(e.target.value)}>
                    <SelectItem key="top" value="top" 
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/top.png`}/>
                      }>탑</SelectItem>
                    <SelectItem key="jungle" value="jungle"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/jungle.png`}/>
                      }>정글</SelectItem>
                    <SelectItem key="mid" value="mid"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/mid.png`}/>
                      }>미드</SelectItem>
                    <SelectItem key="bottom" value="bottom"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/bottom.png`}/>
                      }>원딜</SelectItem>
                    <SelectItem key="support" value="support"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/support.png`}/>
                      }>서포터</SelectItem>
                  </Select>
                  <Select label="상대 라인" size="sm" onChange={(e) => setSearchingLine(e.target.value)}>
                    <SelectItem key="top" value="top" 
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/top.png`}/>
                      }>탑</SelectItem>
                    <SelectItem key="jungle" value="jungle"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/jungle.png`}/>
                      }>정글</SelectItem>
                    <SelectItem key="mid" value="mid"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/mid.png`}/>
                      }>미드</SelectItem>
                    <SelectItem key="bottom" value="bottom"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/bottom.png`}/>
                      }>원딜</SelectItem>
                    <SelectItem key="support" value="support"
                      startContent={
                        <Image width={20} alt="top" src={`/positionIcons/support.png`}/>
                      }>서포터</SelectItem>
                  </Select>
                  <br />
                  <Link href="/recommendation">
                    <Button color="primary" className="text-base font-bold w-full">
                      매칭하기!
                    </Button>
                  </Link>
                </div>
              </div>
            )}
          </PopoverContent>
        </Popover>
        <NavbarItem>
          <form action="submit" className={styles.form} onSubmit={(e) => handleSubmit(e)}>
            <Input
              isClearable
              isDisabled
              value={keyword}
              onClear={() => setKeyword("")}
              placeholder="enter player name"
              variant="bordered"
              className={styles.input}
              startContent={
                <SearchIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
              }
              onValueChange={(value: string): void => { setKeyword(value) }}
            />
            <Button 
              type="button"
              isDisabled
              onClick={handleClick} 
              className={styles.button}
            >
              search
            </Button>
          </form>
        </NavbarItem>
      </NavbarContent>
      <NavbarContent as="div" className="items-center" justify="end">
        {
          isLoggedIn ?
          <>
            {/* 로그인 상태일 때 보이는 유저 프로필 */}
            <Link href={`/user/${user.userId}`}>
              <User
                name={user.riotAccount.summonerProfile.name.replaceAll('+', ' ')}
                description={`Lv.${user.riotAccount.summonerProfile.level}`}
                avatarProps={{
                  src: `${user.riotAccount.summonerProfile.iconUrl}`
                }}
              />
            </Link>
            <Button 
              onClick={handleLogout}
            >
              Log Out
            </Button>
          </>  
          : <Button
            onClick={handleLogin}
          >
          Sign In / Log In
          </Button>
        } 
      </NavbarContent>
    </Navbar>
  );
}
