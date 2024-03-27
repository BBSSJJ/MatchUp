'use client'

import { OpenVidu } from 'openvidu-browser';

import axios from 'axios';
import React, { Component } from 'react';
import './App.css';
import Image from 'next/image';
import MatchupChats from '../ui/onmatchup/matchupChats';

const APPLICATION_SERVER_URL = 'https://matchup.site/openvidu/'
const headers = { Authorization: "Basic T1BFTlZJRFVBUFA6TWF0Y2hVcA==" }

class App extends Component {
	constructor(props) {
		super(props);

		// These properties are in the state's component in order to re-render the HTML whenever their values change
		this.state = {
			mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
			session: undefined,
			mainStreamManager: undefined,  // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
			publisher: undefined,
			subscribers: [],
		};

		this.joinSession = this.joinSession.bind(this);
		this.leaveSession = this.leaveSession.bind(this);
		this.handleChangeSessionId = this.handleChangeSessionId.bind(this);
		this.handleChangeUserName = this.handleChangeUserName.bind(this);
		this.onbeforeunload = this.onbeforeunload.bind(this);
	}

	componentDidMount() {
		this.setupMicrophone()
		window.addEventListener('beforeunload', this.onbeforeunload);
	}

	componentWillUnmount() {
		window.removeEventListener('beforeunload', this.onbeforeunload);
	}

    onbeforeunload(event) {
		this.leaveSession();
	}

  handleChangeSessionId(e) {
    this.setState({
      mySessionId: e.target.value,
    });
  }

  handleChangeUserName(e) {
    this.setState({
      myUserName: e.target.value,
    });
  }


	deleteSubscriber(streamManager) {
		let subscribers = this.state.subscribers;
		let index = subscribers.indexOf(streamManager, 0);
		if (index > -1) {
			subscribers.splice(index, 1);
			this.setState({
				subscribers: subscribers,
			});
		}
	}

	setupMicrophone() {
		//마이크 사용 허가 요청
		navigator.mediaDevices.getUserMedia({ audio: true })
			.then(stream => {
				const audioContext = new AudioContext();
				const microphone = audioContext.createMediaStreamSource(stream);
				const analyser = audioContext.createAnalyser();
	
				analyser.fftSize = 256;
				const bufferLength = analyser.frequencyBinCount;
				const dataArray = new Uint8Array(bufferLength);
	
				microphone.connect(analyser);
	
				const volumeMeter = document.getElementById('volume-meter');
				if(!volumeMeter) {
					console.error("volume-meter을 찾을 수 없음!");
					// return;
				}
	
				const updateVolume = () => {
					analyser.getByteFrequencyData(dataArray);
					const average = dataArray.reduce((sum, value) => sum + value, 0) / bufferLength;
					// console.log('>>>>>>볼륨: '+average);
		
					// average 값으로 UI 업데이트 (예: CSS를 사용하여 높이 조절)
					// 수정된 부분: volumeMeter가 null이 아닌 경우에만 스타일 조작
					if(volumeMeter){
						volumeMeter.style.height = `${average}px`;
					}
				};
		
				setInterval(updateVolume, 100);
			})
			.catch(error => {
			console.error('마이크 접근 중 오류 발생:', error);
		});
	}

	joinSession() {
		// --- 1) Get an OpenVidu object ---

		this.OV = new OpenVidu();

		// --- 2) Init a session ---

		this.setState(
			{
				session: this.OV.initSession(),
			},
			() => {
				var mySession = this.state.session;

				// --- 3) Specify the actions when events take place in the session ---

				// On every new Stream received...
				mySession.on('streamCreated', (event) => {
					// Subscribe to the Stream to receive it. Second parameter is undefined
					// so OpenVidu doesn't create an HTML video by its own
					var subscriber = mySession.subscribe(event.stream, undefined);
					var subscribers = this.state.subscribers;
					subscribers.push(subscriber);

					// Update the state with the new subscribers
					this.setState({
							subscribers: subscribers,
					});
				});

				// On every Stream destroyed...
				mySession.on('streamDestroyed', (event) => {

						// Remove the stream from 'subscribers' array
						this.deleteSubscriber(event.stream.streamManager);
				});

				// On every asynchronous exception...
				mySession.on('exception', (exception) => {
						console.warn(exception);
				});

				// --- 4) Connect to the session with a valid user token ---

				// Get a token from the OpenVidu deployment
				this.getToken().then((token) => {
					// First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
					// 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
					mySession.connect(token, { clientData: this.state.myUserName })
						.then(async () => {

							// --- 5) Get your own camera stream ---

							// Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
							// element: we will manage it on our own) and with the desired properties
							let publisher = await this.OV.initPublisherAsync(undefined, {
								audioSource: undefined, // The source of audio. If undefined default microphone
								publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
							});

							// --- 6) Publish your stream ---

							mySession.publish(publisher);

							// Set the main video in the page to display our webcam and store our Publisher
							this.setState({
								mainStreamManager: publisher,
								publisher: publisher,
							});
						})
						.catch((error) => {
							console.log('There was an error connecting to the session:', error.code, error.message);
						});
				});
			},
		);
	}

	leaveSession() {

		// --- 7) Leave the session by calling 'disconnect' method over the Session object ---

		const mySession = this.state.session;

		if (mySession) {
			mySession.disconnect();
		}

		// Empty all properties...
		this.OV = null;
		this.setState({
			session: undefined,
			subscribers: [],
			mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
			mainStreamManager: undefined,
			publisher: undefined
		});
	}

	render() {
		const mySessionId = this.state.mySessionId;
		const myUserName = this.state.myUserName;

		return (
			<div className="container">
				{this.state.session === undefined ? (
					<div id="join">
						<div id="img-div">
							<Image 
								src="/images/openvidu_grey_bg_transp_cropped.png" 
								alt="OpenVidu logo"
								width={200}
								height={100}
							/>
						</div>
						<div id="join-dialog" className="jumbotron vertical-center">
							<h1> Join a video session </h1>
							<form className="form-group" onSubmit={this.joinSession}>
								<p>
									<label>Participant: </label>
									<input
										className="form-control"
										type="text"
										id="userName"
										value={myUserName}
										onChange={this.handleChangeUserName}
										required
									/>
								</p>
								<p>
									<label> Session: </label>
									<input
										className="form-control"
										type="text"
										id="sessionId"
										value={mySessionId}
										onChange={this.handleChangeSessionId}
										required
									/>
								</p>
								<p className="text-center">
									<input className="btn btn-lg btn-success" name="commit" type="submit" value="JOIN" />
								</p>
							</form>
						</div>
					</div>
				) : null}

				{this.state.session !== undefined ? (
					<div id="session">
						<div id="session-header">
							<h1 id="session-title">{mySessionId}</h1>
							<input
								className="btn btn-large btn-danger"
								type="button"
								id="buttonLeaveSession"
								onClick={this.leaveSession}
								value="Leave session"
							/>
						</div>
						<div className="flex justify-between">
							{/* {this.state.mainStreamManager !== undefined ? (
									<div id="main-video" className="col-md-6">
											<UserVideoComponent streamManager={this.state.mainStreamManager} />
									</div>
							) : null} */}
							{this.state.publisher !== undefined ? (
								// <div className="stream-container col-md-6 col-xs-6" onClick={() => this.handleMainVideoStream(this.state.publisher)}>
								//     <UserVideoComponent
								//         streamManager={this.state.publisher} />
								// </div>
								<Image 
									src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"
									alt="image"
									width={308}
									height={560}
									style={{opacity: 0.5}}
								/>
							) : null}
							<MatchupChats />
							{this.state.subscribers.map((sub, i) => (
								// <div key={sub.id} className="stream-container col-md-6 col-xs-6" onClick={() => this.handleMainVideoStream(sub)}>
								//     <span>divider</span>
								//     <span>{sub.id}</span>
								//     <UserVideoComponent streamManager={sub} />
								// </div>
								<Image 
									src="https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Akali_0.jpg"
									alt="image"
									width={308}
									height={560}
									style={{opacity: 0.5}}
									key={sub.id}
								/>
								))}
						</div>
					</div>
				) : null}
			</div>
		);
	}


    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    async getToken() {
        const sessionId = await this.createSession(this.state.mySessionId);
        return await this.createToken(sessionId);
    }

    async createSession(sessionId) {
        const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions', { customSessionId: sessionId }, {
            headers: headers,
        });
        return response.data.sessionId; // The sessionId
    }

    async createToken(sessionId) {
        const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/connection', {}, {
            headers: headers,
        });
        return response.data.token; // The token
    }
}

export default App;
