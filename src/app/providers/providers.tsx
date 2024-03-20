'use client'

import { NextUIProvider } from '@nextui-org/react'
import { SessionProvider } from 'next-auth/react';
import { ReactNode } from 'react';
import { Session } from 'next-auth';

type ProvidersProps = {
    session?: Session | null;
    children: ReactNode;
};


export function Providers({ session, children }: ProvidersProps) {
    return (
        <NextUIProvider>
            {/* <SessionProvider session={session} > */}
                {children}
            {/* </SessionProvider> */}
        </NextUIProvider>
    );
}