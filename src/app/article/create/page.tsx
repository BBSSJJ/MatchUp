"use client"
import React from 'react';
import { useState, useEffect } from 'react';
import { Editor } from '@tinymce/tinymce-react';
import { Button } from '@nextui-org/react';

export default function CreateArticle() {
  const [editorContent, setEditorContent] = useState('');
 
  const handleEditorChange = (content, editor) => {
    setEditorContent(content);
  };

  const postContentToServer = async () => {
    try {
      const response = await fetch('/api/mz/articles', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ content: editorContent }),
      });
      console.log(JSON.stringify({ content: editorContent }));
      const data = await response.json();
      console.log("서버로부터의 응답", data);
    } catch (error) {
      console.log("작성한 글 내용", editorContent);
      console.error('Error posting content to server:', error);
    }
  };
  
  return (
    <div className='mx-[50px] relative'>
      <Button 
        onClick={postContentToServer}
        className='right-0'>
          작성하기
      </Button>
      <Editor
        apiKey='1gn4r9v0aqj7ukaoide2t623xjf6t226fbqtwcmwk153pm2v'
        init={{
          plugins: 'anchor autolink charmap codesample emoticons image link lists media searchreplace table visualblocks wordcount checklist mediaembed casechange fullscreen export formatpainter pageembed linkchecker a11ychecker permanentpen powerpaste advtable advcode editimage advtemplate mentions tinycomments tableofcontents footnotes mergetags typography inlinecss',
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
          file_picker_types: 'file image media',
          file_picker_callback: (cb, value, meta) => {
            const input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');

            input.addEventListener('change', (e) => {
              const file = e.target.files[0];
        
              const reader = new FileReader();
              reader.addEventListener('load', () => {
                const id = 'blobid' + (new Date()).getTime();
                const blobCache = tinymce.activeEditor.editorUpload.blobCache;
                const base64 = reader.result.split(',')[1];
                const blobInfo = blobCache.create(id, file, base64);
                blobCache.add(blobInfo);
        
                cb(blobInfo.blobUri(), { title: file.name });
              });
              reader.readAsDataURL(file);
            });
        
            input.click();
          },
          video_template_callback: (data) =>
            `<video width="${data.width}" height="${data.height}"${data.poster ? ` poster="${data.poster}"` : ''} controls="controls">\n` +
            `<source src="${data.source}"${data.sourcemime ? ` type="${data.sourcemime}"` : ''} />\n` +
            (data.altsource ? `<source src="${data.altsource}"${data.altsourcemime ? ` type="${data.altsourcemime}"` : ''} />\n` : '') +
            '</video>',
          mergetags_list: [
            { value: 'First.Name', title: 'First Name' },
            { value: 'Email', title: 'Email' },
          ],
          // ai_request: (request, respondWith) => respondWith.string(() => Promise.reject("See docs to implement AI Assistant")),
        }}
        onEditorChange={handleEditorChange}
      />
    </div>
  );
}