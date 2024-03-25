<script setup>
import axios from 'axios'
import { ref, onBeforeMount } from 'vue'
const FILESERVER_BASE_URL = 'https://matchup.site/file'
const FILESERVER_PORT = 8001
const upload = async () => {
  const files = document.getElementById('uploadFile').files

  // exception : 파일 없을때
  if (files.length === 0) {
    alert('파일을 선택해주세요.')
    return
  }

  // 파일 업로드
  await axios
    .post(`${FILESERVER_BASE_URL}/uploads`, files)
    .then((success) => console.log(success))
    .then(() => alert('파일 업로드 완료'))
    .then(() => window.location.reload())
    .catch((fail) => {
      alert('파일 업로드 실패')
      console.log(fail)
    })
}

onBeforeMount(async () => {
  // 파일 서버 응답여부 확인
  axios
    .get(`${FILESERVER_BASE_URL}/`)
    .then((success) => console.log(success))
    .catch(() => alert('파일 서버가 응답하지 않습니다. 관리자에게 문의하세요.'))
})
</script>
<template>
  <div id="upload-container" class="upload-container">
    <div>
      <h1 class="mt-3 mb-3 text-center">파일 업로드 | File Upload</h1>
      <hr />

      <!-- 파일 추가버튼 -->
      <div class="container mt-5" style="max-width: 40vw">
        <form @submit.prevent="upload">
          <div class="input-group mb-3">
            <label class="input-group-text" for="uploadFile"><strong>등록</strong></label>
            <input type="file" class="form-control" id="uploadFile" multiple />
            <button type="submit" class="btn btn-primary">업로드</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<style scoped>
.upload-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
}
a {
  text-decoration: none;
}
</style>
