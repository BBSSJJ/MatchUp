import axios from "axios";
import { OpenVidu } from "openvidu-browser";

const URL = "https://matchup.site/openvidu/api"
const headers = { Authorization: "Basic T1BFTlZJRFVBUFA6TWF0Y2hVcA==" }

export async function createSession(sessionId: string) {
  const response = await axios({
    method: 'post',
    url: `${URL}/sessions`,
    headers,
    data: {
      customSessionId: sessionId
    }
  })
  return response.data
}

export async function createToken(sessionId: string) {
  const response = await axios({
    method: 'post',
    url: `${URL}/sessions/${sessionId}/connection`,
    headers
  })
  return response.data
}

export function getSession() {
  axios({
    method: 'get',
    url: `${URL}/sessions`,
    headers
  })
  .then((response) => {
    console.log(response.data)
    return response.data
  })
}

export function deleteSession() {
  axios({
    method: 'delete',
    url: `${URL}/sessions/{sessionId}`,
    headers
  })
    .then((response) => {
      console.log(response.data)
      return response.data
    })
    .catch((error) => {
      console.log(error)
    })
}

export function searchSession() {
  axios({
    method: 'get',
    url: `${URL}/sessions/{sessionId}`,
    headers
  })
    .then((response) => {
      console.log(response.data)
      return response.data
    })
    .catch((error) => {
      console.log(error)
    })
}
