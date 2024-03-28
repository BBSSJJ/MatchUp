import type { InferGetServerSidePropsType, GetServerSideProps, NextPage, GetServerSidePropsContext } from 'next'
import '@/app/ui/article/article.css'
import Comment from "@/app/ui/article/commentList";
import { Button } from '@nextui-org/react';
import { useRouter } from 'next/navigation';
import { motion } from 'framer-motion';
import { comments2 } from '@/app/ui/article/dummyData';
import { AxiosResponse, AxiosError } from 'axios';
import { ParsedUrlQuery } from 'querystring';
import { SERVER_API_URL } from '@/utils/instance-axios';
import ArticlePage from '@/app/ui/article/article';
import ArticleWrapper from '@/app/ui/article/article-wrapper';

// 동적 라우팅 게시글 페이지
interface ArticlePageProps {
  articleInfoResponse?: Article;
  comments?: Comment[];
}

interface Article {
  title?: string;
  author?: string;
  createdAt?: string;
  views?: number;
  content?: string;
  rightSympathyTitle?: string;
  leftSympathyTitle?: string;
  rightSympathyCount?: number;
  leftSympathyCount?: number;
}


// 게시글 가져오기
// export async function getArticle(id :number) {
//   try {
//     const response = await fetch(`${SERVER_API_URL}/api/mz/articles/${id}`)

//     if (!response.ok) {
//       throw new Error('게시글 정보 가져오기 실패')
//     }
//     console.log(response)
//     return response.json()
//   } catch(error) {
//     console.error(error)
//     throw error
//   }
// }

// 댓글 가져오기
// async function getComments(id :number) {
//   try {
//     const response = await fetch(`${SERVER_API_URL}/api/mz/comments/articles/${id}`)

//     if (!response.ok) {
//       throw new Error('댓글 정보 가져오기 실패')
//     }

//     return response.json()
//   } catch(error) {
//     console.error(error)
//     throw error
//   }
// }


// 개별 게시글 상세 내용을 보여주는 컴포넌트
export default async function Page({
  params: { id },
}: {
  params: { id: number };
}) {

  return(
    <div>
      <ArticleWrapper id={id} />
    </div>
  )
}

