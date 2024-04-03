import { initializeApp } from "firebase/app";
import { getMessaging, getToken } from "firebase/messaging";

const getFirebaseToken = async () => {

    const firebaseConfig = {
        apiKey: "AIzaSyDVjW-x8C0eQxaz9TT_atctuIzLQrVo3-c",
        authDomain: "matchup-c9f78.firebaseapp.com",
        projectId: "matchup-c9f78",
        storageBucket: "matchup-c9f78.appspot.com",
        messagingSenderId: "109793865216",
        appId: "1:109793865216:web:f9f19fff1628756073f2e5",
        measurementId: "G-NG2181NBLP"
    };
    
    // Initialize Firebase
    const app = initializeApp(firebaseConfig);
    const messaging = getMessaging(app);
    
    // 토큰을 얻는 로직
    try {
        const currentToken = await getToken(messaging, { vapidKey: "BP3-XwR7C4KGwYeY04Ue65rZFaw8VVH3hNZDZScOohjf-mvYtIEGMwbeE961pah6WWhE_Boc_JAB2CXJheeO05c" });
        if (currentToken) {
            // console.log("클라이언트 토큰 : ",currentToken); // 토큰 사용
            return currentToken
        } else {
            // console.log('No registration token available. Request permission to generate one.');
        }
    } catch (error) {
        // console.log('An error occurred while retrieving token. ', error);
    }
    // const getToken = (messaging, {vapidKey: "BP3-XwR7C4KGwYeY04Ue65rZFaw8VVH3hNZDZScOohjf-mvYtIEGMwbeE961pah6WWhE_Boc_JAB2CXJheeO05c"})
    //     .then((currentToken) => {
    //         if (currentToken) {
    //             console.log("클라이언트 토큰 : ",currentToken); // 토큰 사용
    //             return currentToken
    //         } else {
    //             console.log('No registration token available. Request permission to generate one.');
    //         }
    //     }).catch((err) => {
    //         console.log('An error occurred while retrieving token. ', err);
    // })

    // return getToken
}


export { getFirebaseToken }