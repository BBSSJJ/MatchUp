import { app } from "./instance-axios"

// 게시판 api
const article = "api/mz/articles"
const comment = "api/mz/comments"
const sympathies = "api/mz/sympathies"

// 전체 게시글 조회
function getArticleList(success, fail) {
    app.get(`${article}`).then(success).catch(fail)
}

// 단일 게시글 조회
// function getArticle(articleId, success, fail) {
//     app.get(`${article}/${articleId}`).then(success).catch(fail)
// }

// 게시글 작성
function postArticle(params, success, fail) {
    app.post(`${article}`, JSON.stringify(params)).then(success).catch(fail)
}

// 게시글 수정
function updateArticle(articleId, params, success, fail) {
    app.put(`${article}/${articleId}`, JSON.stringify(params)).then(success).catch(fail)
}

// 게시글 삭제
function deleteArticle(articleId, success, fail) {
    app.delete(`${article}/${articleId}`).then(success).catch(fail)

}

// 전체 댓글 조회
function getComments(articleId, success, fail) {
    app.get(`${comment}/articles/${articleId}`).then(success).catch(fail)
}

// 댓글 작성
function addComment(articleId, success, fail) {
    app.post(`${comment}/articles/${articleId}`).then(success).catch(fail)
}

// 댓글 수정
function updateComment(articleId, success, fail) {
    app.post(`${comment}/${articleId}`).then(success).catch(fail)
}

// 댓글 삭제
function deleteComment(articleId, success, fail) {
    app.delete(`${comment}/${articleId}`).then(success).catch(fail)
}

// 공감 등록 or 수정 or 삭제
function empathize(articleId, success, fail) {
    app.put(`${sympathies}/${articleId}`).then(success).catch(fail)
}

// 유저 api
const user = "/users"

// 유저 정보 조회
function getUser(success, fail) {
    app.get(`${user}`).then(success).catch(fail)
}

// 회원가입
function signIn(params, success, fail) {
    app.post(`${user}/regist`, JSON.stringify(params), {withCredentials: true}).then(success).catch(fail)
}

//로그인
function logIn(params, success, fail) {
    app.post(`${user}/regist/login`, JSON.stringify(params), {withCredentials: true}).then(success).catch(fail)
}

// 로그아웃


// 친구 기능 api
const friend = "/friends"

// 친구 조회
function getFriends(success, fail) {
    app.get(`${friend}`).then(success).catch(fail)
}

// 친구 맺기
function makeFriends(params, success, fail) {
    app.post(`${friend}`, JSON.stringify(params)).then(success).catch(fail)
}

// 친구 삭제
function deleteFriend(friendId, success, fail) {
    app.delete(`${friend}/${friendId}`).then(success).catch(fail)
}

export { getArticleList, postArticle, updateArticle, deleteArticle, getComments, addComment, updateComment, deleteComment, empathize, getUser, signIn, getFriends, makeFriends, deleteFriend }