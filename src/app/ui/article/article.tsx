'use client'

import Image from "next/image"
import '@/app/ui/article/article.css'

interface Article {
  title: string;
  content: string;
  thumbnail_url: string;
  author: string;
  left_sympathy_title: string;
  right_sympathy_title: string;
  left_sympathy_count: number;
  right_sympathy_count: number;
}

export default function Article(
  {
    title, content, thumbnail_url, author,
    left_sympathy_title, left_sympathy_count,
    right_sympathy_title, right_sympathy_count
  }: Article
) {
  const leftPercent = (left_sympathy_count / (left_sympathy_count + right_sympathy_count)) * 100
  const rightPercent = (right_sympathy_count / (left_sympathy_count + right_sympathy_count)) * 100
  
  return (
    <div className="article">
      <Image 
        src={thumbnail_url}
        width={300}
        height={300}
        alt="demo image"
      />
      <div className="articleContent">
        <a href="" className="articleTitle">{title}</a>
        <p className="articleAuthor">{author}</p>
        <div className="vote">
          <div className="voteLeft" style={{ width: `${leftPercent}%` }}>{left_sympathy_title}</div>
          <div className="voteRight" style={{ width: `${rightPercent}%` }}>{right_sympathy_title}</div>
        </div>
      </div>
    </div>
  )
}