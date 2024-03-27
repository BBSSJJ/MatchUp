"use client"
import { useRouter } from "next/router";
import { usePathname } from "next/navigation";
import RiotLoginForm from '@/app/ui/login/riot-login';

const RiotLoginPage = () => {
    const path = usePathname();
    const tokens = path.split('/')
    console.log("tokens : ", tokens)
    const snsId = tokens[4]
    const snsType = tokens[3]
    return (
        <div>
            {/* <p>params : {snsId}, {snsType}</p> */}
            <RiotLoginForm snsType={snsType} snsId={snsId} />
        </div>
    )
}

export default RiotLoginPage;