import { demoArticles } from "@/app/lib/placeholder-data"
import { ArticleForm } from "@/app/lib/definitions"
import Article from "@/app/ui/article/article"

export default function Page({ params }: { params: { id: number }}) {
  const id = params.id

  // 이 페이지에서 보여줄 게시글
  const article = demoArticles.filter((demoArticle) => demoArticle.id == id)[0]

  // Article은 article.tsx 
  // serversideprops를 사용하므로 부모가 전달해줄 필요 없음
  return (
    <div>
      <Article /> 
    </div>
  )
}