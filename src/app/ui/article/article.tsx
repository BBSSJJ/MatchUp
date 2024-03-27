"use client"
import type { InferGetServerSidePropsType, GetServerSideProps, NextPage } from 'next'
import '@/app/ui/article/article.css'
import Comment from "./commentList";
import { Button } from '@nextui-org/react';
import { useRouter } from 'next/navigation';
import { motion } from 'framer-motion';
import { comments2 } from './dummyData';

interface ArticlePageProps {
  title?: string;
  author?: string;
  createdAt?: string;
  views?: number;
  content?: string;
  rightSympathyTitle?: string;
  leftSympathyTitle?: string;
  rightSympathyCount?: number;
  leftSympathyCount?: number;
  comments?: Comment[];
}


// 개별 게시글 상세 내용을 보여주는 컴포넌트

const ArticlePage :NextPage<ArticlePageProps> = (props) => {
  const { title,
          author,
          createdAt, 
          views, 
          content, 
          comments, 
          leftSympathyTitle, 
          rightSympathyTitle,
          leftSympathyCount,
          rightSympathyCount,
        } = props
    
  const router = useRouter()

  console.log("댓글목록: ", comments)
  return (
    <div>
      <Button
        onClick={() => {router.back()}}
      >
        뒤로가기
      </Button>
      <div className="articleContainer">
        <div>
          <h1 className="articleTitle">{title}글 제목</h1>
          <div className="articleInfo">
            <p>{author}작성자 이름</p>
            <p>조회수 {views}123</p>
          </div>
          <hr />
          <p>{createdAt}2024-01-02</p>
          <p className="articleContent">{content}글내용 blah blah</p>
          <div className="articleVoteButtons">
            {/* 버튼 클릭은 한 유저당 한 번으로 제한 */}
            <Button className="leftButton" variant="shadow">
              {leftSympathyTitle}내 잘못
            </Button>

            <Button className="rightButton" variant="shadow">
              {rightSympathyTitle}상대 잘못
            </Button>
          </div>
          {/* 투표현황 */}
          <div className="articleVote">
            <motion.div 
              className="articleVoteLeft" 
              style={{ width: "25%" }}
              initial={{ width: '0%', opacity: 0 }}
              animate={{ width: '25%', opacity: 1 }}
              transition={{ duration: 0.55}}
              exit={{ width: "0%", opacity: 0 }}
            >
              25%
            </motion.div>
            <motion.div 
              className="articleVoteRight" 
              style={{ width: "75%" }}
              initial={{ width: '0%', opacity: 0 }}
              animate={{ width: '75%', opacity: 1 }}
              transition={{ duration: 0.55}}
              exit={{ width: "0%", opacity: 0 }}
            >
              75%
            </motion.div>
            {/* <div className="articleVoteLeft" style={{ width: `${leftSympathyCount}%` }}></div>
            <div className="articleVoteRight" style={{ width: `${rightSympathyCount}%` }}></div> */}
          </div>
          <div className="comments">
          {comments && comments.map((comment)=> (
            <Comment key={comment.id} comment={comment} />
          ))}
          </div>
        </div>
      </div>
    </div> 
  )
} 
  

export const getServerSideProps: GetServerSideProps<ArticlePageProps> = async () => {
  // const GET_ARTICLE_URL = "url"
  // const GET_COMMENT_URL = "url2"
  // const articleInfoResponse = await fetch(GET_ARTICLE_URL);
  // const commentsResponse = await fetch(GET_COMMENT_URL);
  // const articleInfo = await articleInfoResponse.json()
  // const comments = await commentsResponse.json();
  const comments = comments2
  console.log(comments)
  return {
    props: {
      // title: articleInfo.title,
      // author: articleInfo.author.userId,
      // createdAt: articleInfo.createdAt,
      // views: articleInfo.views,
      // content: articleInfo.content,
      // leftSympathyTitle: articleInfo.left_sympathy_title,
      // rightSympathyTitle: articleInfo.right_sympathy_title,
      // leftSympathyCount: articleInfo.leftSympathies.length,
      // rightSympathyCount: articleInfo.rightSympathies.length,
      comments : comments,
      title: "",
      author: "",
      createdAt: "articleInfo.createdAt",
      views: 11,
      content: "articleInfo.content",
      leftSympathyTitle: "articleInfo.left_sympathy_title",
      rightSympathyTitle: "articleInfo.right_sympathy_title",
      leftSympathyCount: 11,
      rightSympathyCount: 12,
    },
  }
}

export default ArticlePage