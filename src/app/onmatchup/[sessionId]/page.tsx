'use client'

import { OpenVidu, Session } from "openvidu-browser"
import axios from "axios"
import { useState, useRef } from "react"
import Image from "next/image"
import MatchupChats from "@/app/ui/onmatchup/matchupChats"
import Link from "next/link"
import UserVideoComponent from './UserVideoComponent';
import { Button } from "@nextui-org/react"

const APPLICATION_SERVER_URL = 'https://matchup.site/openvidu/'
const headers = { Authorization: "Basic T1BFTlZJRFVBUFA6TWF0Y2hVcA==" }
var OVScreen: OpenVidu
var screen: Session
var screensharing: boolean = false

export default function Page({ params }: { params: { sessionId: string }}) {
  const [openvidu, setOpenvidu] = useState<any>({
    session: undefined,
    publisher: undefined,
    subscribers: [],
  });
  const [username, setUsername] = useState('Participant' + Math.floor(Math.random() * 100))
  const [ovSession, setOvSession] = useState<Session | undefined>(undefined)

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
    OVScreen = new OpenVidu()

    const session = OV.initSession()
    screen = OVScreen.initSession()

    session.on("streamCreated", (event) => {
      const subscriber = session.subscribe(event.stream, undefined);
      setOpenvidu((parameter: any) => ({
        ...parameter,
        subscribers: [...parameter.subscribers, subscriber]
      }))
    });

    screen.on("streamCreated", (event) => {
      const subscriberScreen = screen.subscribe(event.stream, undefined);
      setOpenvidu((parameter: any) => ({
        ...parameter,
        subscribers: [...parameter.subscribers, subscriberScreen]
      }))
    });

    session.on("streamDestroyed", (event) => {
      setOpenvidu((parameter: any) => {
        const streamManager = event.stream.streamManager;
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
        console.log('111')
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
        console.log(openvidu.publisher)
      })
      .catch((error) => {
        console.log('There was an error connecting to the session:', error.code, error.message);
      });
    
    // const tokenScreen = await getToken()

    // screen.connect(tokenScreen, { clientData: username })
    //   .then(() => {
    //     console.log("Session screen connected");
    //   })
    //   .catch((error) => {
    //     console.log('There was an error connecting to the session for screen share:', error.code, error.message)
    //   })

  }
    
  function publishScreenShare() {
    getToken().then((tokenScreen) => {
      console.log('222')
      screen.connect(tokenScreen, { clientData: username })
    })
    // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
    var publisherScreen = OVScreen.initPublisher("container-screens", { videoSource: "screen" });
  
    // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
    publisherScreen.once('accessAllowed', (event) => {
      screensharing = true;
      // If the user closes the shared window or stops sharing it, unpublish the stream
      publisherScreen.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
        console.log('User pressed the "Stop sharing" button');
        screen.unpublish(publisherScreen);
        screensharing = false;
      });
      screen.publish(publisherScreen);
    });
  
    publisherScreen.on('videoElementCreated', function (event) {
      // appendUserData(event.element, screen.connection);
      event.element['muted'] = true;
    });
  
    publisherScreen.once('accessDenied', (event) => {
      console.error('Screen Share: Access Denied');
    });
  }

  function leaveSession () {
    if (ovSession) {
      ovSession.disconnect()
      setOpenvidu((parameter: any) => ({
        ...parameter,
        session: undefined,
        publisher: undefined,
        subscribers: []
      }))
    }
  }
  

  return (
    <div>
      {openvidu.session ? (
        <div className="flex justify-between">
          {openvidu.publisher ? (
            <div>
              <p>{JSON.parse(openvidu.publisher.stream.connection.data).clientData}</p>
              <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.5}}
              />
              <div className="p-0 w-0">
                <UserVideoComponent streamManager={openvidu.publisher} />
              </div>
            </div>
          ) : null}
          <div className="w-screen">
            <MatchupChats roomId={params.sessionId} />
            <Link href={'/onmatchup'} onClick={leaveSession}>나가기</Link>
            <Button onPress={publishScreenShare}>Screen Share</Button>
          </div>
          {openvidu.subscribers.map((sub: any, index: number) => (
            <div key={sub.id}>
              <p>{JSON.parse(sub.stream.connection.data).clientData}</p>
              <Image 
                src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Akali_0.jpg"
                alt="image"
                width={308}
                height={560}
                style={{opacity: 0.5}}
              />
              <div className="p-0 w-0">
                <UserVideoComponent streamManager={sub} />
              </div>
            </div>
          ))}
        </div>
      ) : (
          <div>
            <MatchupChats roomId={params.sessionId} />
            <button onClick={joinSession}>시작하기</button>
          </div>
      )}
    </div>
  )
}