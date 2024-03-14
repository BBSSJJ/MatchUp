import Image from "next/image"
import '@/app/ui/article/article.css'
import { ArticleForm } from "@/app/lib/definitions"
import Link from "next/link"
import { demoArticles } from "@/app/lib/placeholder-data"

export default function Articles() {
  
  return (
    <div>
      {demoArticles?.map((demoArticle) => (
        <div className="article" key={demoArticle.id}>
          <Image 
            src={demoArticle.thumbnail_url}
            width={300}
            height={300}
            alt="demo image"
          />
          <div className="articleListContent">
            <Link href={`/article/${demoArticle.id}`} className="articleListTitle">{demoArticle.title}</Link>
            <p className="articleListAuthor">{demoArticle.author}</p>
            <div className="vote">
              <div className="voteLeft" style={{ width: `${demoArticle.left_sympathy_percent}%` }}>{demoArticle.left_sympathy_title}</div>
              <div className="voteRight" style={{ width: `${demoArticle.right_sympathy_percent}%` }}>{demoArticle.right_sympathy_title}</div>
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}