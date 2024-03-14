"use client"

import React, { useState } from "react";
import { Navbar, NavbarBrand, NavbarContent, NavbarItem, Image, Input, User,  Badge } from "@nextui-org/react";
import { AcmeLogo } from "./AcmeLogo";
import { usePathname, useRouter } from "next/navigation";
import Link from "next/link";
import styles from "./Navbar.module.css"
import { SearchIcon } from "./SearchIcon";

export default function NavigationBar() {
  // react hook 사용
  const path = usePathname();
  const [keyword, setKeyword] = useState("");
  const router = useRouter();
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

  return (
    <Navbar isBordered className={styles.nav}>
      <NavbarBrand>
        <AcmeLogo />
        <p className="font-bold text-inherit">MatchUP</p>
      </NavbarBrand>
      <NavbarContent justify="start">
        <NavbarItem>
          <Link color="foreground" className={path === '/' ? styles.active : ""} href="/">
            Home
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link href="/article" className={path === '/article' ? styles.active : ""}>
            MatchUP Zone
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link href="/recommendation" className={path === '/recommendation' ? styles.active : ""}>
            Match
          </Link>
        </NavbarItem>
        <NavbarItem>
          <form action="submit" className={styles.form} onSubmit={(e) => handleSubmit(e)}>
            <Input
              isClearable
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
            <button type="button" onClick={handleClick} className={styles.button}>search</button>
          </form>
        </NavbarItem>
      </NavbarContent>
      <NavbarContent as="div" className="items-center" justify="end">
        <User
          name="Username"
          description="Lv.712"
          avatarProps={{
            src: "https://ddragon.leagueoflegends.com/cdn/14.5.1/img/champion/Leblanc.png"
          }}
        />
      </NavbarContent>
    </Navbar>
  );
}
