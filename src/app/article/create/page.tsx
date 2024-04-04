"use client"
import React, { useRef } from 'react';
import { useState } from 'react';
import { Editor } from '@tinymce/tinymce-react';
import { Button, Input } from '@nextui-org/react';
import { useRouter } from 'next/navigation';
import styles from './styles.module.css'
import { SERVER_API_URL } from "@/utils/instance-axios"

const SERVER_URL = SERVER_API_URL

interface VideoTemplateCallbackData {
  source: string;
  sourcemime?: string;
  altsource?: string;
  altsourcemime?: string;
  poster?: string;
  width: number;
  height: number;
}

interface PluginApi {
  pick: (settings: PluginPickerApiSettings) => Promise<PickerResult>;
  browse: (settings: PluginPickerApiSettings) => Promise<void>;
  upload: (settings: PluginUploadApiSettings) => Promise<UploadResult>;
}

interface PluginPickerApiSettings {
  filetypes?: string[];
}

interface PluginUploadApiSettings {
  path?: string;
  name: string;
  blob: Blob;
  onprogress?: (details: UploadProgress) => void;
  max_image_dimension?: number;
}

interface DriveFile {
  url: string;
  size: number;
  name: string;
  type: string;
  mdate: string;
}

interface PickerResult {
  files: DriveFile[];
}

interface UploadProgress {
  loaded: number;
  total: number;
}

interface UploadResult {
  file: DriveFile;
}

interface UploadResponse extends Response {
  fileName?: string;
}

export default function CreateArticle() {
  const [editorContent, setEditorContent] = useState('');
  const [articleTitle, setArticleTitle] = useState('')
  const [left, setLeft] = useState('')
  const [right, setRight] = useState('')
  const editorRef = useRef(null); 
  const router = useRouter()

  const handleEditorChange = (content :string, editor : any) => {
    setEditorContent(content);
  };

  const postContentToServer = async () => {
    try {
      if(articleTitle.trim() === '') {
        alert('제목을 입력하세요!')
        return
      } else if ( left.trim() === '' || right.trim() === '') {
        alert('투표 선택지를 입력하세요!')
        return
      }
      const response = await fetch(`${SERVER_URL}/api/mz/articles`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: articleTitle, 
          content: editorContent,
          leftSympathyTitle: left,
          rightSympathyTitle: right,
        }),
      })

      // console.log(JSON.stringify({
      //   title: articleTitle, 
      //   content: editorContent,
      //   leftSympathyTitle: left,
      //   rightSympathyTitle: right,
      // }));
      // const data = await response;

      // console.log("서버로부터의 응답", response.status)
      // 글 작성에 성공하면 게시글 페이지로
      router.push("/article")
    } catch (error) {
      // console.log("작성한 글 내용", editorContent);
      console.error('Error posting content to server:', error);
    }
  };

  const saveLocalImage = async (formData :FormData) :Promise<string | undefined> => {
    try {
      const response :UploadResponse = await fetch('https://matchup.site/file/uploads/' , {
        headers: { "Accept": "*/*" },
        method: 'POST',
        body: formData,
      })
      const responseData = await response.json()
      // console.log("json으로 변환한 응답 데이터", responseData)
      // const imageUrl = response.fileName
      // return imageUrl
      if (response.status === 200) {
        // console.log("200", responseData.fileName)
        const imageUrl = responseData.fileName
        return imageUrl
      }
    } catch (error) {
      console.error(error)
    }
  }
    
  
  return (
    <div className='mx-[50px] my-6 relative flex flex-col'>
      <Button
        className='w-[50px] ml-auto'
        size="sm"
        variant="ghost"
        onClick={() => {router.back()}
      }>
        뒤로가기
      </Button>
      <div className='flex item-center justify-center mb-5 leading-7 w-full'>
        
        <Input
          label="Title"
          labelPlacement="outside-left"
          placeholder='제목을 입력하세요.'
          className="min-w-[300px] items-center"
          // width="450px"
          onValueChange={(value: string): void => { setArticleTitle(value) }}
        />
        
      </div>
      <Editor
        apiKey='8aum2e2t4747gni5yj1ennpxe1uqw6mu6vzo5fck4socnjlt'
        ref={editorRef}
        init={{
          plugins: 'anchor autolink charmap emoticons image link lists media searchreplace table visualblocks wordcount checklist mediaembed casechange fullscreen export formatpainter pageembed linkchecker a11ychecker permanentpen powerpaste advtable advcode editimage advtemplate mentions tinycomments tableofcontents footnotes mergetags typography inlinecss',
          toolbar: 'undo redo fullscreen | blocks fontsize | forecolor backcolor | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
          height: 600,
          content_style: 'body { margin: 2rem 10%; }',
          // skin: 'snow',
          skin: "oxide-dark",
          content_css: "dark",
          icons: 'thin',
          tinycomments_mode: 'embedded',
          tinycomments_author: 'Author name',
          image_title: true,
          media_live_embeds: true,
          automatic_uploads: true,
          images_upload_url: 'https://matchup.site/file/uploads/',
          file_picker_types: 'file image media',
          video_template_callback: (data :VideoTemplateCallbackData) => {
            // console.log("여기가 문제인가:", data)
            const newUrl = 'https://matchup.site/file/uploads/' + data.source
            return `<video width="${data.width}" height="${data.height}"${data.poster ? ` poster="${data.poster}"` : ''} controls="controls">\n` +
            `<source src="${newUrl}"${data.sourcemime ? ` type="${data.sourcemime}"` : ''} />\n` +
            (data.altsource ? `<source src="${data.altsource}"${data.altsourcemime ? ` type="${data.altsourcemime}"` : ''} />\n` : '') +
            '</video>'
          },
          file_picker_callback: (cb, value, meta) => {
            const input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');
        
            let file :File | undefined;
            // 파일을 선택 이벤트 발생시 콜백
            input.addEventListener('change', (e) => {
              const input = e.target as HTMLInputElement
              if (input.files && input.files.length > 0) {
                // console.log("input tag에 파일을 담음")
                file = input.files[0]; // 선택한 파일에 접근
              }
              
              if(file) {
                // console.log("파일이 undefined가 아님", file)
                const reader = new FileReader(); 
                // base64로 인코딩된 파일 데이터를 가져와서 이미지 blob registry에 등록
                reader.addEventListener('load', async () => {
                  /*
                    Note: Now we need to register the blob in TinyMCEs image blob
                    registry. In the next release this part hopefully won't be
                    necessary, as we are looking to handle it internally.
                  */
                  // const id = 'blobid' + (new Date()).getTime();
                  // const blobCache =  tinymce.activeEditor.editorUpload.blobCache;
                  // const base64 = reader.result.split(',')[1];
                  // 파일 데이터와 함께 새로운 blob 정보를 생성하고,캐시에 추가
                  // const blobInfo = blobCache.create(id, file, base64);
                  // blobCache.add(blobInfo);
                  const formData :FormData = new FormData()
                  formData.append('image', file!)
                  // console.log("서버로 보내는 데이터", formData)
                  const imageUrl = await saveLocalImage(formData) 
                  console.log("imageurl", imageUrl)
                  // const imageUrl = "https://us.123rf.com/450wm/nuevoimg/nuevoimg2308/nuevoimg230819575/211219943-%ED%8C%8C%EB%9E%80%EC%83%89-%EB%B0%B0%EA%B2%BD%EC%97%90-%ED%8C%8C%EB%9E%80-%EB%88%88%EC%9D%84-%EA%B0%80%EC%A7%84-%ED%9D%B0-%EA%B3%A0%EC%96%91%EC%9D%B4%EC%9D%98-%EC%B4%88%EC%83%81%ED%99%94.jpg?ver=6"
                  // 이미지를 에디터에 추가, 첫번째 인자는 새로운 이미지의 URI, file.name은 이미지의 제목 
                  if (typeof imageUrl === 'string') {
                    const fileType = file?.type
                    // 배포 후 주소 변경할 것 
                    // console.log("cb함수호출")
                    const url = "https://matchup.site/file/uploads/"
                    if(fileType!.startsWith('image/')) {
                      cb(url + imageUrl, { title: file!.name });
                    } else {
                      cb(imageUrl, { title: file!.name });
                    }
                  }
                });
                // 지정된 파일의 내용을 Data URL로 읽어오는 비동기적인 작업을 수행
                // 파일의 읽기가 완료되면 load 이벤트가 발생하고 이후의 작업은 load 이벤트 핸들러 내부에서 수행
                reader.readAsDataURL(file);
              }
            });
        
            input.click();
          },
          
          mergetags_list: [
            { value: 'First.Name', title: 'First Name' },
            { value: 'Email', title: 'Email' },
          ],
        }}
       
        onEditorChange={handleEditorChange}
      />
      <div className={styles.vote}>
        <div className='flex justify-between items-center'>
          <p className='font-bold my-6'>투표 생성</p>
          <Button 
            variant="ghost"
            color='danger'
            onClick={postContentToServer}
          >
            작성하기
          </Button>
        </div>
        <div className='flex gap-[50px] justify-center'>
          <Input
            type="email"
            color="primary"
            label="선택 1"
            className="max-w-[320px]"
            onValueChange={(value: string): void => { setLeft(value) }}
          />
          <span className='leading-10'>VS</span>
          <Input
            type="email"
            color="danger"
            label="선택 2"
            className="max-w-[320px]"
            onValueChange={(value: string): void => { setRight(value) }}
          />
        </div>
      </div>
    </div>
  );
}