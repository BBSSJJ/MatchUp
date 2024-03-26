"use client"

import React, { useState, MouseEvent } from "react";
import { Navbar, NavbarBrand, NavbarContent, NavbarItem, Image, Input, User, Select, SelectItem, Badge, Button, Popover, PopoverTrigger, PopoverContent } from "@nextui-org/react";
import { AcmeLogo } from "./AcmeLogo";
import { usePathname, useRouter } from "next/navigation";
import Link from "next/link";
import styles from "./Navbar.module.css"
import { SearchIcon } from "./SearchIcon";
import { motion } from "framer-motion"
import { atom, useAtom } from 'jotai'
import { isLoggedInAtom } from '@/store/authAtom'

export default function NavigationBar() {
  // react hook 사용
  const path = usePathname();
  const [keyword, setKeyword] = useState("");
  const router = useRouter();
  
  // 로그인 상태 확인
  const [isLoggedIn, setIsLoggedIn] = useAtom(isLoggedInAtom)

  const handleLogout = () => {
    setIsLoggedIn(false)
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
  const handleLogin = () => {
    router.push('/login');
  }

  return (
    <Navbar isBordered className={styles.nav}>
      <NavbarBrand>
        <Image 
          src="logo.png"
          width="40px"
          height="40px"
        />
        <Link href="/lobby" className="font-bold text-inherit">MatchUP</Link>
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
              <Link href="/recommendation" className={path === '/recommendation' ? styles.active : ""}>
                Match
              </Link>
            </NavbarItem>
          </PopoverTrigger>
          <PopoverContent className="w-[240px]">
            {(titleProps) => (
              <div className="px-1 py-2 w-full">
                <p className="text-small font-bold text-foreground" {...titleProps}>
                  추천에 참고할 정보를 추가로 입력해주세요
                </p>
                <div className="mt-2 flex flex-col gap-2 w-full">
                  <Input defaultValue="100%" label="Width" size="sm" variant="bordered" />
                  <Input defaultValue="300px" label="Max. width" size="sm" variant="bordered" />
                  <Input defaultValue="24px" label="Height" size="sm" variant="bordered" />
                  <Input defaultValue="30px" label="Max. height" size="sm" variant="bordered" />
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
            <User
              name="Username"
              description="Lv.712"
              avatarProps={{
                src: "https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png"
              }}
            />
            <Button 
              onClick={handleLogout}
            >
              Log Out
            </Button>
          </>  
          : <Button
            onClick={handleLogin}
          >
          Log In
          </Button>
        }
        
      </NavbarContent>
    </Navbar>
  );
}
