import axios from "axios";

const SERVER_API_URL = process.env.SERVER_URL

// api axios instance
export const app = axios.create({
    baseURL: SERVER_API_URL
})

// request 발생 시 적용할 내용
app.defaults.headers.post["Content-Type"] = "application/json"
app.defaults.headers.put["Content-Type"] = "application/json"

// Axios의 인터셉터를 사용하여 요청과 응답에 대한 설정을 지정(for 일관성)
app.interceptors.request.use((config) => {
    return config
}),
    (error) => {
        return Promise.reject(error)
    }

app.interceptors.response.use((response) => {
    return response
}),
    (error) => {
        return Promise.reject(error)
    }


// POST 및 PUT 요청의 기본 헤더를 설정