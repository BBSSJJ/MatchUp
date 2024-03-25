<template>
  <div id="main">
    <h1 class="mt-3 mb-3 text-center">파일 업로드 서버 | File Upload Server</h1>
    <hr />

    <div class="body-container-flex">
      <div class="body-container">
        <h4 class="p-2">개요</h4>
        <ul>
          <h5 class="p-2"><li>A405조 파일 업로드를 위한 서버입니다.</li></h5>
        </ul>
        <hr />

        <h4 class="p-2">사용 기술</h4>
        <ul>
          <li class="p-1">Node.js: ^20.11.1</li>
          <li class="p-1">vue: ^3.3.11</li>
          <li class="p-1">axios : ^1.6.5</li>
          <li class="p-1">express: ^4.18.2</li>
          <li class="p-1">multer: ^1.4.4</li>
        </ul>
        <hr />

        <h4 class="p-2">사용방법</h4>
        <ol>
          <h5 class="p-2">
            <li>
              파일 업로드 시
              <h6>
                <ol>
                  <li class="pt-3">업로드 탭으로 이동합니다.</li>
                  <li class="pt-3">파일 선택 후, 필요한 파일을 업로드합니다.</li>
                </ol>
              </h6>
            </li>
          </h5>
          <h5 class="p-2">
            <li>
              파일 다운로드 및 삭제 시
              <h6>
                <ol>
                  <li class="pt-3">파일 목록 탭으로 이동합니다.</li>
                  <li class="pt-3">파일들을 확인하고 클릭해서 다운로드합니다.</li>
                  <li class="pt-3">필요시 휴지통 버튼을 눌러 해당 파일을 삭제할 수 있습니다.</li>
                </ol>
              </h6>
            </li>
          </h5>
          <h5 id="make-img-tag" class="p-2">
            <li>
              img 태그에 업로드한 사진 넣기
              <h6>
                <ol>
                  <li class="pt-3">업로드 탭으로 이동 후 원하는 파일을 업로드 합니다.</li>
                  <li class="pt-3">목록 탭에서 해당 파일 경로를 복사합니다. (복사버튼 클릭)</li>
                  <li class="pt-3">이미지 태그 src에 해당 파일 경로를 넣습니다.</li>
                  <p class="pt-3">
                    <code class="language-plaintext highlighter-rouge">&lt;img src="{파일경로 붙여넣기}"&gt;</code>
                  </p>
                </ol>
                <ul>
                  <li class="list-group-item form-text text-primary">
                    로컬에서는 잘 작동하는 것을 확인했는데, 나중에 서버 빌드하고 cors 이슈가 있을 수 있습니다. 오류
                    확인되시면 관리자에게 문의주세요.
                  </li>
                </ul>
              </h6>
            </li>
          </h5>
          <h5 class="p-2">
            <li>
              참고사항
              <h6>
                <ul>
                  <li class="pt-3">파일형식 : [파일종류]-[파일명]-[생성날짜].[확장자]</li>
                  <li class="pt-3">파일종류 : text, image, video, etc</li>
                  <ul>
                    <li class="pt-3">text : [.txt], [.md]</li>
                    <li class="pt-3">image : [.png], [.jpg], [.jpeg]</li>
                    <li class="pt-3">video : [.avi], [.mp4]</li>
                    <li class="pt-3">etc : 그외 모든 파일</li>
                  </ul>
                  <li class="pt-3">나중에 시간나면 검색 및 분류기능도 추가해보겠습니다.</li>
                </ul>
              </h6>
            </li>
          </h5>
        </ol>
        <hr />

        <h4 class="p-2">관리자</h4>
        <ul>
          <li class="p-1">남상엽 | spearoad15@gmail.com</li>
          <li class="p-1">
            <a class="btn btn-dark align-middle" href="https://github.com/leaf-nam">
              <div class="d-flex justify-content-center align-items-center">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-github"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27s1.36.09 2 .27c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.01 8.01 0 0 0 16 8c0-4.42-3.58-8-8-8"
                  />
                </svg>
              </div>
            </a>
          </li>
        </ul>
        <hr />
      </div>
    </div>
  </div>
</template>
<style scoped>
.body-container-flex {
  display: flex;
  justify-content: center;
}
.body-container {
  width: 60vw;
  max-width: 80vw;
}
</style>
