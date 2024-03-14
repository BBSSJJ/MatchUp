import { demoArticles } from "@/app/lib/placeholder-data"
import { ArticleForm } from "@/app/lib/definitions"
import Article from "@/app/ui/article/article"

export default function Page({ params }: { params: { id: number }}) {
  const id = params.id

  const article = demoArticles.filter((demoArticle) => demoArticle.id == id)[0]

  return (
    <div>
      <Article article={article} />
    </div>
  )
}