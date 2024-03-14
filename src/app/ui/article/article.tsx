import { ArticleForm } from "@/app/lib/definitions";
import '@/app/ui/article/article.css'

export default function Article({
  article
}: {
  article: ArticleForm
}
) {
  return (
    <div className="articleContainer">
      <h1 className="articleTitle">{article.title}</h1>
      <div className="articleInfo">
        <p>{article.author}</p>
        <p>조회수 {article.views}</p>
      </div>
      <hr />
      <p className="articleContent">{article.content}</p>
      <div className="articleVoteButtons">
        <button className="leftButton">
          {article.left_sympathy_title}
        </button>

        <button className="rightButton">
          {article.right_sympathy_title}
        </button>
      </div>
      <div className="articleVote">
        <div className="articleVoteLeft" style={{ width: `${article.left_sympathy_percent}%` }}>{article.left_sympathy_title}</div>
        <div className="articleVoteRight" style={{ width: `${article.right_sympathy_percent}%` }}>{article.right_sympathy_title}</div>
      </div>
</div>
  )
}