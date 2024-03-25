"use client"
import type { InferGetServerSidePropsType, GetServerSideProps, NextPage } from 'next'
import '@/app/ui/article/article.css'
import Comment from "./commentList";
import { Button } from '@nextui-org/react';

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
 

  return (
    <div className="articleContainer">
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
        <Button className="leftButton">
          {leftSympathyTitle}내 잘못
        </Button>

        <Button className="rightButton">
          {rightSympathyTitle}상대 잘못
        </Button>
      </div>
      <div className="articleVote">
        <div className="articleVoteLeft" style={{ width: "25%" }}>25%</div>
        <div className="articleVoteRight" style={{ width: "75%" }}>75%</div>
        {/* <div className="articleVoteLeft" style={{ width: `${leftSympathyCount}%` }}></div>
        <div className="articleVoteRight" style={{ width: `${rightSympathyCount}%` }}></div> */}
      </div>
      <div className="comments">
      {comments && comments.map((comment)=> (
        <Comment key={comment.id} comment={comment} />
      ))}
      </div>
    </div>
    
  )
} 
  

export const getServerSideProps: GetServerSideProps<ArticlePageProps> = async () => {
  const GET_ARTICLE_URL = "url"
  const GET_COMMENT_URL = "url2"
  const articleInfoResponse = await fetch(GET_ARTICLE_URL);
  const commentsResponse = await fetch(GET_COMMENT_URL);
  const articleInfo = await articleInfoResponse.json()
  const comments = await commentsResponse.json();
  return {
    props: {
      title: articleInfo.title,
      author: articleInfo.author.userId,
      createdAt: articleInfo.createdAt,
      views: articleInfo.views,
      content: articleInfo.content,
      leftSympathyTitle: articleInfo.left_sympathy_title,
      rightSympathyTitle: articleInfo.right_sympathy_title,
      leftSympathyCount: articleInfo.leftSympathies.length,
      rightSympathyCount: articleInfo.rightSympathies.length,
      comments : comments,
    },
  }
}

export default ArticlePage