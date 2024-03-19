"use client"

import { SessionProvider } from "next-auth/react"
import { ReactNode } from "react"
import { Session } from "next-auth";

type Props = {
    session?: Session | null;
    children: ReactNode;
};
    
export default function AuthSession({ session, children }: Props) {
    return <SessionProvider session={session}>{children}</SessionProvider>;
}