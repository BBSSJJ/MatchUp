'use client'

import { useEditor, EditorContent } from "@tiptap/react"
import StarterKit from "@tiptap/starter-kit"
// import { Editor } from '@tiptap/core'
import Document from '@tiptap/extension-document'
import Paragraph from '@tiptap/extension-paragraph'
import Text from '@tiptap/extension-text'

export default function Create() {
  const editor = useEditor({
    extensions: [
      // Document,
      // Paragraph,
      // Text,
      StarterKit,
    ],
    content: '<h1>글 작성을 위한 페이지입니다.</h1>',
    // autofocus: true,
    // editable: true,
    // injectCSS: false,
  })

  return (
    <EditorContent editor={editor} />
  )
}