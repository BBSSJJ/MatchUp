"use client"
import useSWR, { mutate } from 'swr';
import type { InferGetServerSidePropsType, GetServerSideProps, NextPage } from 'next'
import '@/app/ui/article/article.css'
import Comment from "./commentList";
import { Button, Textarea } from '@nextui-org/react';
import { useRouter } from 'next/navigation';
import { motion } from 'framer-motion';
import { SERVER_API_URL } from '@/utils/instance-axios';
import { useEffect, useState } from 'react';
import { getArticle, getComments } from './article-wrapper';
import { useAtomValue } from 'jotai';
import { isLoggedInAtom } from '@/store/authAtom';
import Link from 'next/link';

interface ArticleProps {
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

interface SummonerProfile {
  name: string;
  tag: string;
  iconUrl: string;
  level: number;
}

interface RiotAccount {
  id: string;
  summonerProfile: SummonerProfile;
  tier: string;
  leagueRank: string;
  leaguePoint: number;
}

interface Author {
  userId: number;
  role: string;
  riotAccount: RiotAccount;
}

interface Article {
  id: number;
  title: string;
  content: string;
  leftSympathyTitle: string;
  rightSympathyTitle: string;
  thumbnailUrl: string | null;
  views: number;
  author: Author;
  leftSympathies: any[]; // leftSympathies의 타입은 확인되지 않았습니다.
  rightSympathies: any[]; // rightSympathies의 타입은 확인되지 않았습니다.
  createdAt: string;
  updatedAt: string;
}

interface SummonerProfile {
  name: string;
  tag: string;
  iconUrl: string;
  level: number;
}

interface RiotAccount {
  id: string;
  summonerProfile: SummonerProfile;
  tier: string;
  leagueRank: string;
  leaguePoint: number;
}

interface Writer {
  userId: number;
  role: string;
  riotAccount: RiotAccount;
}

export interface Reply {
  id: number;
  content: string;
  writer: Writer;
  createdAt: string;
  updatedAt: string;
  childrenComments?: Reply[];
}

export interface CommentList {
  list?: Reply[];
}

// fetcher 정의
const articleFetcher = async (url:string) => {
  const response = await fetch(url); // 서버로부터 데이터 가져오기
  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }
  return response.json(); // JSON 형식으로 변환하여 반환
};
// const commentFetcher = getComments

// 개별 게시글 상세 내용을 보여주는 컴포넌트 - props: 2번 게시글, 2번 게시글의 댓글 모음

const ArticlePage = ({id} :{id :number}) => {
  const router = useRouter()
  // const [replies, setReplies] = useState<CommentList>(null)
  const [commentContent, setCommentContent] = useState("")
  const isLoggedIn = useAtomValue(isLoggedInAtom) 

  // 데이터 가져오기
  const {data: article, error: articleError, isLoading: articleLoading } = useSWR(
    `${SERVER_API_URL}/api/mz/articles/${id}`,
    articleFetcher,
    {
      onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
        if (error.status === 401) return
  
      }, 
      revalidateOnFocus: false,
      revalidateOnMount: true,
    }
    // { refreshInterval: 1000 }
  )

  const { data: comments, error: commentsError, isLoading: commentsLoading } = useSWR(
    `${SERVER_API_URL}/api/mz/comments/articles/${id}`,
    articleFetcher,
    {
      onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
        if (error.status === 401) return
  
      }, 
      revalidateOnFocus: false,
      revalidateOnMount: true,
    }
  )

  // 실제 득표수를 실시간으로 보여주기 위한 상태
  // const [voteCount, setVoteCount] = useState<{ left: number; right: number }>({ left: article?.leftSympathies.length || 0, right: article?.rightSympathies.length || 0 })


// 투표기능 
const totalVotes = (article?.leftSympathies.length || 0) + (article?.rightSympathies.length || 0);
let leftVotesPercentage;
let rightVotesPercentage;

if (totalVotes === 0) {
    leftVotesPercentage = 50;
    rightVotesPercentage = 50;
} else {
    leftVotesPercentage = (article?.leftSympathies.length / totalVotes) * 100;
    rightVotesPercentage = (article?.rightSympathies.length / totalVotes) * 100;
}


  // 투표 버튼 클릭 이벤트
  const handleVote = async (lor :string) => {
    const response = await fetch(`${SERVER_API_URL}/api/mz/sympathies/${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ sympathyType: lor })
    })

    if (response.ok) {
      // 이벤트 성공시에 알아서 업데이트 된 데이터베이스 상태 반영
      // mutate(`${SERVER_API_URL}/api/mz/comments/articles/${id}`)
      mutate(`${SERVER_API_URL}/api/mz/articles/${id}`)
      // const newData = await getArticle(id)
      // setVoteCount(
      //   { right: newData.rightSympathies.length , left: newData.leftSympathies.length }
      // ) 
    }
  }

  // 댓글 작성하기 
  const handleReply = async (parentId :number) => {

    if(!commentContent.trim()){
      alert("내용을 입력하세요!")
      return
    }
    // 비로그인 시 댓글 작성 불가
    if (!isLoggedIn) {
      alert("댓글은 회원만 작성할 수 있습니다.")
      setCommentContent("")
      return
    }
    const response = await fetch(`${SERVER_API_URL}/api/mz/comments/articles/${id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        content: commentContent,
        parentCommentId: parentId,
      })
    })

    if (response.ok) {
      setCommentContent("") // 댓글입력창 지우기
      // const newComments = await getComments(id)
      // console.log(newComments)
      mutate(`${SERVER_API_URL}/api/mz/comments/articles/${id}`)
    }
  }

  // const fetchData = async () => {
  //   const newComments = await getComments(id)     
  //   setReplies(newComments)
  //   console.log("업데이트 된 댓글", replies)
  // }

  // useEffect(() => {
  //   fetchData()
  // }, [])

  return (
    <div>
      <Button
        size='sm'
        color='secondary'
        className='ml-[10%] m-3'
        onClick={() => {router.back()}}
      >
        뒤로가기
      </Button>
      <div className="articleContainer">
        <div>
          <h1 className="articleTitle">{article?.title}</h1>
          <div className="articleInfo">
            <Link href={`/user/${article?.author.userId}`}><p>{article?.author!.riotAccount?.summonerProfile?.name}</p></Link>
            <p>조회수 : {article?.views}</p>
          </div>
          <hr />
          <p className='my-3 text-right'>{article?.createdAt}</p>
          {/* <p className="articleContent">{article.content}</p> */}
          <div dangerouslySetInnerHTML={{ __html: article?.content }} />
          <div className="articleVoteButtons">
            <Button className="leftButton" variant="shadow" onClick={() => handleVote("LEFT")}>
              {article?.leftSympathyTitle}
            </Button>

            <Button className="rightButton" variant="shadow" onClick={() => handleVote("RIGHT")}>
              {article?.rightSympathyTitle}
            </Button>
          </div>
          {/* 투표현황 */}
          <div className="articleVote">
            <motion.div 
              className="articleVoteLeft" 
              style={{ width: `${leftVotesPercentage}%` }}
              initial={{ width: '0%', opacity: 0 }}
              animate={{ width: `${leftVotesPercentage}%`, opacity: 1 }}
              transition={{ duration: 0.55}}
              // exit={{ width: "0%", opacity: 0 }}
            >
              {/* {article.leftSympathies.length} */}
              {article?.leftSympathies.length}
            </motion.div>
            <motion.div 
              className="articleVoteRight" 
              style={{ width: `${rightVotesPercentage}%` }}
              initial={{ width: '0%', opacity: 0 }}
              animate={{ width: `${rightVotesPercentage}%`, opacity: 1 }}
              transition={{ duration: 0.55}}
              // exit={{ width: "0%", opacity: 0 }}
            >
              {/* {article.rightSympathies.length} */}
              {article?.rightSympathies.length}
            </motion.div>
            {/* <div className="articleVoteLeft" style={{ width: `${leftSympathyCount}%` }}></div>
            <div className="articleVoteRight" style={{ width: `${rightSympathyCount}%` }}></div> */}
          </div>
          
          
        </div>
        
      </div>
      {/* 댓글 목록 */}
      <div className="flex flex-col items-center justify-between">
        <div className='flex items-center justify-center mx-4 w-[80%]'>
          <Textarea
            minRows={2}
            className='w-[100%] mx-4'
            size='md'
            variant="bordered"
            color='secondary'
            label="write a comment"
            placeholder="Enter your description"
            value={commentContent}
            onValueChange={(value:string):void => { setCommentContent(value)}}
          />
          <Button
            color='secondary'
            size='sm'
            onClick={() => handleReply(-1)}
          >
            댓글쓰기
          </Button>
        </div>
        {/* 댓글목록 */}
        <div className='w-[80%]'>
          {comments?.list && comments?.list.length > 0 && comments?.list.map((comment: Reply)=> (
            <Comment key={comment.id} comment={comment} articleId={id} parentId={comment.id} />
          ))}
        </div>
      </div>
    </div> 
  )
}
  

export default ArticlePage