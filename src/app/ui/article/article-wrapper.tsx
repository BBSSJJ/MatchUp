import ArticlePage from "./article"
import { SERVER_API_URL } from '@/utils/instance-axios';


// 게시글 가져오기
export async function getArticle(id :number) {
  try {
    const response = await fetch(`${SERVER_API_URL}/api/mz/articles/${id}`)

    if (!response.ok) {
      throw new Error('게시글 정보 가져오기 실패')
    }
    console.log(response)
    return response.json()
  } catch(error) {
    console.error(error)
    throw error
  }
}

// 댓글 가져오기
export async function getComments(id :number) {
  try {
    const response = await fetch(`${SERVER_API_URL}/api/mz/comments/articles/${id}`)

    if (!response.ok) {
      throw new Error('댓글 정보 가져오기 실패')
    }

    return response.json()
  } catch(error) {
    console.error(error)
    throw error
  }
}


export default async function ArticleWrapper({id}:{id:number}) {
    const fetchedArticle = await getArticle(id)
    const fetchedComments = await getComments(id)
    return (
        <div>
            <ArticlePage id={id} />
        </div>
    )
}