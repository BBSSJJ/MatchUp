'use client'

import { OpenVidu, Session } from "openvidu-browser"
import axios from "axios"
import { useState, useRef } from "react"
import Image from "next/image"
import MatchupChats from "@/app/ui/onmatchup/matchupChats"
import Link from "next/link"
import UserVideoComponent from './UserVideoComponent';
import { Button } from "@nextui-org/react"
import { stopRecording } from "@/app/lib/openvidu"
import { userInfoAtom } from "@/store/authAtom"
import { useAtom } from "jotai"

const APPLICATION_SERVER_URL = 'https://matchup.site/openvidu/'
const headers = { Authorization: "Basic T1BFTlZJRFVBUFA6TWF0Y2hVcA==" }
// var OVScreen: OpenVidu
// var screen: Session
// var screensharing: boolean = false

export default function Page({ params }: { params: { sessionId: string }}) {
  const [openvidu, setOpenvidu] = useState<any>({
    session: undefined,
    publisher: undefined,
    subscribers: [],
    // screens: []
  });
  const [username, setUsername] = useState('Participant' + Math.floor(Math.random() * 100))
  const [ovSession, setOvSession] = useState<Session | undefined>(undefined)
  // const [recordingId, setRecordingId] = useState<string>('')

  const [userInfo, setUserInfo] = useAtom(userInfoAtom)
  const [onAudio, setOnAudio] = useState<boolean>(true)

  async function getSession(sessionId: string) {
    const response = await axios.get(APPLICATION_SERVER_URL + 'api/sessions/' + sessionId, {
      headers: headers,
    });
    return response.data.sessionId; // The sessionId
  }
  
  async function createToken(sessionId: string) {
    const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/connection', {}, {
      headers: headers,
    });
    setUsername(response.data.id)
    return response.data.token; // The token
  }

  async function getToken() {
    const sessionId = await getSession(params.sessionId);
    return await createToken(sessionId);
  }

  async function joinSession() {
    const OV = new OpenVidu()
    // OVScreen = new OpenVidu()

    const session = OV.initSession()
    // screen = OVScreen.initSession()

    session.on("streamCreated", (event) => {
      // if (event.stream.typeOfVideo == "CAMERA") {
        const subscriber = session.subscribe(event.stream, undefined);
        setOpenvidu((parameter: any) => ({
          ...parameter,
          subscribers: [...parameter.subscribers, subscriber]
        }))
      // }
    });

    // screen.on("streamCreated", (event) => {
    //   if (event.stream.typeOfVideo == "SCREEN") {
    //     const subscriberScreen = screen.subscribe(event.stream, 'container-screens');
    //     console.log(subscriberScreen)
    //     setOpenvidu((parameter: any) => ({
    //       ...parameter,
    //       screens: [...parameter.screens, subscriberScreen]
    //     }))
    //   }
    // });

    session.on("streamDestroyed", (event) => {
      setOpenvidu((parameter: any) => {
        const streamManager = event.stream.streamManager;
        console.log(streamManager)
        return {
          ...parameter,
          subscribers: parameter.subscribers.filter((sub: any) => sub !== streamManager),
        };
      });
    });

    session.on("exception", (exception) => {
      console.warn(exception);
    });

    const token = await getToken()

    session.connect(token, { clientData: username })
      .then(async () => {
        const publisher = await OV.initPublisherAsync(undefined, {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: '640x480', // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
          mirror: false, // Whether to mirror your local video or not
        })
        session.publish(publisher)
        setOvSession(session)
        setOpenvidu((parameter: any) => ({
          ...parameter,
          session: session,
          publisher: publisher
        }))
      })
      .catch((error) => {
        console.log('There was an error connecting to the session:', error.code, error.message);
      });
    
  }
    
  // async function publishScreenShare() {
  //   getToken().then((tokenScreen) => {
  //     screen.connect(tokenScreen, { clientData: username })
  //   })
  //   // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
  //   var publisherScreen = await OVScreen.initPublisherAsync(undefined, { videoSource: "screen" });
  
  //   // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
  //   publisherScreen.once('accessAllowed', (event) => {
  //     screensharing = true;
  //     // If the user closes the shared window or stops sharing it, unpublish the stream
  //     publisherScreen.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
  //       console.log('User pressed the "Stop sharing" button');
  //       screen.unpublish(publisherScreen);
  //       screensharing = false;
  //     });
  //     screen.publish(publisherScreen);
  //   });
  
  //   publisherScreen.on('videoElementCreated', function (event) {
  //     // appendUserData(event.element, screen.connection);
  //     event.element['muted'] = true;
  //   });
  
  //   publisherScreen.once('accessDenied', (event) => {
  //     console.error('Screen Share: Access Denied');
  //   });
  // }

  function leaveSession () {
    if (ovSession) {
      ovSession.disconnect()
      // screen.disconnect()
      // screensharing = false
      setOpenvidu((parameter: any) => ({
        ...parameter,
        session: undefined,
        publisher: undefined,
        subscribers: [],
        screens: []
      }))
    }
  }

  // async function startRecording(sessionId: string) {
  //   const response = await axios({
  //     method: 'post',
  //     url: `${APPLICATION_SERVER_URL}api/recordings/start`,
  //     headers,
  //     data: {
  //       session: sessionId
  //     }
  //   })
  //   window.alert('녹화 시작')
  //   return setRecordingId(response.data.id)
  // }

  function micChange() {
    setOnAudio(!onAudio)
    openvidu.publiser.publishAudio(onAudio)
  }
  

  return (
    <div>
      {openvidu.session ? (
        <div className="flex justify-between">
          {openvidu.publisher ? (
            <>
              {/* <p>{openvidu.publisher.stream.typeOfVideo}{JSON.parse(openvidu.publisher.stream.connection.data).clientData}</p> */}
              <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.8}}
              />
              <div className="p-0 w-0">
                <UserVideoComponent streamManager={openvidu.publisher} />
              </div>
            </>
          ) : (
            <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.2}}
              />
          )}
          <div className="w-screen">
            <MatchupChats roomId={params.sessionId} />
            <div className="flex justify-center pt-3">
              <Link href={'/lobby'} onClick={leaveSession}>
                <Button>나가기</Button>
              </Link>
              {/* <Button onPress={publishScreenShare}>화면 공유</Button>
              <Button color="danger" onPress={() => startRecording(params.sessionId)}>녹화하기</Button>
              <Button color="warning" onPress={() => stopRecording(recordingId)}>녹화중지</Button> */}
            </div>
            <div className="flex justify-center pt-3">
              <Button onPress={micChange}>{(onAudio ? (<svg xmlns="http://www.w3.org/2000/svg" viewBox="-50 -100 500 700"><path d="M96 96V256c0 53 43 96 96 96s96-43 96-96H208c-8.8 0-16-7.2-16-16s7.2-16 16-16h80V192H208c-8.8 0-16-7.2-16-16s7.2-16 16-16h80V128H208c-8.8 0-16-7.2-16-16s7.2-16 16-16h80c0-53-43-96-96-96S96 43 96 96zM320 240v16c0 70.7-57.3 128-128 128s-128-57.3-128-128V216c0-13.3-10.7-24-24-24s-24 10.7-24 24v40c0 89.1 66.2 162.7 152 174.4V464H120c-13.3 0-24 10.7-24 24s10.7 24 24 24h72 72c13.3 0 24-10.7 24-24s-10.7-24-24-24H216V430.4c85.8-11.7 152-85.3 152-174.4V216c0-13.3-10.7-24-24-24s-24 10.7-24 24v24z"/></svg>) : (<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 512"><path d="M38.8 5.1C28.4-3.1 13.3-1.2 5.1 9.2S-1.2 34.7 9.2 42.9l592 464c10.4 8.2 25.5 6.3 33.7-4.1s6.3-25.5-4.1-33.7L472.1 344.7c15.2-26 23.9-56.3 23.9-88.7V216c0-13.3-10.7-24-24-24s-24 10.7-24 24v24 16c0 21.2-5.1 41.1-14.2 58.7L416 300.8V256H358.9l-34.5-27c2.9-3.1 7-5 11.6-5h80V192H336c-8.8 0-16-7.2-16-16s7.2-16 16-16h80V128H336c-8.8 0-16-7.2-16-16s7.2-16 16-16h80c0-53-43-96-96-96s-96 43-96 96v54.3L38.8 5.1zM358.2 378.2C346.1 382 333.3 384 320 384c-70.7 0-128-57.3-128-128v-8.7L144.7 210c-.5 1.9-.7 3.9-.7 6v40c0 89.1 66.2 162.7 152 174.4V464H248c-13.3 0-24 10.7-24 24s10.7 24 24 24h72 72c13.3 0 24-10.7 24-24s-10.7-24-24-24H344V430.4c20.4-2.8 39.7-9.1 57.3-18.2l-43.1-33.9z"/></svg>))}</Button>
            </div>
          </div>
          {openvidu.subscribers.length !== 0 ? (openvidu.subscribers.map((sub: any) => (
            <>
              {/* <p>{sub.stream.typeOfVideo}{JSON.parse(sub.stream.connection.data).clientData}</p> */}
              <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Akali_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.8}}
              />
              <div className="p-0 w-0">
                <UserVideoComponent streamManager={sub} />
              </div>
            </>
          ))) : (
            <Image 
              src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Akali_0.jpg"
              alt="image"
              width={308}
              height={560}
              style={{opacity: 0.2}}
            />
          )}
          {/* {openvidu.screens.map((screen: any) => (
            <div key={screen.id}>
            <p>screen{JSON.parse(screen.stream.connection.data).clientData}</p>
            <div>
              <UserVideoComponent streamManager={screen} />
            </div>
          </div>
          ))} */}
        </div>
      ) : (
          <div className="flex justify-between">
            <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.3}}
              />
            <div className="w-screen">
              <MatchupChats roomId={params.sessionId} />
              <div className="flex justify-center pt-10">
                <Button onPress={joinSession} color="success">음성채팅 시작</Button>
              </div>
            </div>
            <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Akali_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.3}}
              />
          </div>
      )}
    </div>
  )
}