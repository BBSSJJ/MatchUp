"use client"
import type { InferGetServerSidePropsType, GetServerSideProps, NextPage } from 'next'
import '@/app/ui/article/article.css'
import Comment from "./commentList";
import { Button, Textarea } from '@nextui-org/react';
import { useRouter } from 'next/navigation';
import { motion } from 'framer-motion';
import { comments2 } from './dummyData';
import { updateArticle, deleteArticle, getComments, addComment, updateComment, deleteComment, empathize } from "@/utils/api"
import { SERVER_API_URL } from '@/utils/instance-axios';
import { useState } from 'react';
import { getArticle } from '@/app/article/[id]/page';

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
  list: Reply[];
}



// 개별 게시글 상세 내용을 보여주는 컴포넌트 - props: 2번 게시글, 2번 게시글의 댓글 모음

const ArticlePage = ({article, comments, id} :{article:Article, comments: CommentList, id :number}) => {
  const router = useRouter()
  const [voteCount, setVoteCount] = useState<{ left: number; right: number }>({ left: article.leftSympathies.length || 0, right: article.leftSympathies.length || 0 })
  // 투표기능 
  const totalVotes = (voteCount.left === 0 || voteCount.right === 0) ? 1 : voteCount.left + voteCount.right;
  let leftVotesPercentage
  let rightVotesPercentage
  if (voteCount.left === 0 && voteCount.right === 0) {
    leftVotesPercentage = 50;
    rightVotesPercentage = 50;
  } else if (voteCount.left === 0) {
    leftVotesPercentage = 0
    rightVotesPercentage = 100;
  } else if (voteCount.right === 0) {
    leftVotesPercentage = 100
    rightVotesPercentage = 0;
  } else {
    leftVotesPercentage = (voteCount.left / totalVotes) * 100;
    rightVotesPercentage = (voteCount.right / totalVotes) * 100;
  }

  const handleVote = async (lor :string) => {
    const response = await fetch(`${SERVER_API_URL}/api/mz/sympathies/${id}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ sympathyType: lor })
    })

    if (response.ok) {
      const newData = await getArticle(id)
      setVoteCount(
        { right: newData.rightSympathies.length , left: newData.leftSympathies.length }
      ) 
    }
  }

  return (
    <div>
      <Button
        size='sm'
        color='secondary'
        className='ml-[10%]'
        onClick={() => {router.back()}}
      >
        뒤로가기
      </Button>
      <div className="articleContainer tilt-in-fwd-tr">
        <div>
          <h1 className="articleTitle">{article.title}</h1>
          <div className="articleInfo">
            <p>{article.author!.riotAccount?.summonerProfile?.name}</p>
            <p>조회수 : {article.views}</p>
          </div>
          <hr />
          <p className='mt-3 text-right'>{article.createdAt}</p>
          <p className="articleContent">{article.content}</p>
          <div dangerouslySetInnerHTML={{ __html: article.content }} />
          <div className="articleVoteButtons">
            <Button className="leftButton" variant="shadow" onClick={() => handleVote("left")}>
              {article.leftSympathyTitle}
            </Button>

            <Button className="rightButton" variant="shadow" onClick={() => handleVote("right")}>
              {article.rightSympathyTitle}
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
              exit={{ width: "0%", opacity: 0 }}
            >
              {article.leftSympathies.length}
            </motion.div>
            <motion.div 
              className="articleVoteRight" 
              style={{ width: `${rightVotesPercentage}%` }}
              initial={{ width: '0%', opacity: 0 }}
              animate={{ width: `${rightVotesPercentage}%`, opacity: 1 }}
              transition={{ duration: 0.55}}
              exit={{ width: "0%", opacity: 0 }}
            >
              {article.rightSympathies.length}
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
          />
          <Button
            color='secondary'
            size='sm'
          >
            댓글쓰기
          </Button>
        </div>
       
        {comments.list && comments.list.length > 0 && comments.list.map((comment)=> (
          <Comment key={comment.id} comment={comment} />
        ))}
      </div>
    </div> 
  )
}
  

export default ArticlePage