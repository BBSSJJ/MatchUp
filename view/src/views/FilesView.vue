<script setup>
import axios from 'axios'
import { ref, onBeforeMount } from 'vue'
const FILESERVER_BASE_URL = 'https://matchup.site/file'
const FILESERVER_PORT = 8001
const files = ref([])
const baseUrl = `${FILESERVER_BASE_URL}:${FILESERVER_PORT}/uploads/`

const deleteFile = (file) => {
  console.log(file)
  if (confirm('파일을 삭제하시겠습니까?'))
    // 파일 업로드
    axios
      .delete(`${FILESERVER_BASE_URL}:${FILESERVER_PORT}/uploads/${file}`)
      .then((success) => console.log(success))
      .then(() => alert('파일 삭제 완료'))
      .then(() => window.location.reload())
      .catch((fail) => {
        alert('파일 삭제 실패')
        console.log(fail)
      })
}

const getCopy = (fileId) => {
  const range = new Range()
  range.setStart(document.getElementById(fileId), 0)
  range.setEnd(document.getElementById(fileId), 2)

  window.getSelection().removeAllRanges()
  const selRange = window.getSelection()
  selRange.addRange(range)
  document.execCommand('copy')
  window.getSelection().removeAllRanges()

  alert('복사 완료')
}

onBeforeMount(async () => {
  // 파일 서버 응답여부 확인
  axios
    .get(`${FILESERVER_BASE_URL}:${FILESERVER_PORT}/`)
    .then((success) => console.log(success))
    .catch(() => alert('파일 서버가 응답하지 않습니다. 관리자에게 문의하세요.'))

  // 파일 서버 응답확인 후 axios 보내기
  await axios
    .get(`${FILESERVER_BASE_URL}:${FILESERVER_PORT}/uploads`)
    .then((success) => (files.value = success.data.files))
    .catch((fail) => console.log(fail))
})
</script>
<template>
  <!-- 회의록 목록 -->
  <h1 class="mt-3 mb-3 text-center">파일 목록 | File List</h1>
  <hr />
  <div class="container">
    <ul class="list-group list-group-flush">
      <template v-for="(file, index) in files" :key="file">
        <li class="d-flex justify-content-between align-items-center">
          <a :href="`${baseUrl}${file}`" :id="`file-${index + 1}`">
            <span class="hidden">{{ baseUrl }}</span>
            {{ file }}
          </a>
          <div class="p-2">
            <button class="btn btn-primary p-2 pt-0 pb-1 ms-2 me-3" @click="getCopy(`file-${index + 1}`)">
              <svg
                xmlns="www.w3.org/2000/svg"
                width="12"
                height="12"
                fill="currentColor"
                class="bi bi-copy"
                viewBox="0 0 16 16"
              >
                <path
                  fill-rule="evenodd"
                  d="M4 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 5a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1h1v1a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h1v1z"
                />
              </svg>
            </button>
            <button class="btn btn-danger p-1 pt-0 pd-0" @click="deleteFile(file)">
              <svg
                xmlns="www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-trash"
                viewBox="0 0 16 16"
              >
                <path
                  d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"
                />
                <path
                  d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"
                />
              </svg>
            </button>
          </div>
        </li>
      </template>
      <h6 v-if="files.length === 0">표시할 항목이 없습니다.</h6>
    </ul>
  </div>
</template>
<style scoped>
.container {
  max-width: 40vw;
}
.hidden {
  overflow: hidden;
  position: absolute;
  border: 0;
  width: 1px;
  height: 1px;
  clip: rect(1px, 1px, 1px, 1px);
}
</style>
