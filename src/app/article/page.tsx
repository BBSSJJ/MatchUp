import Search from "@/app/ui/article/search"
import Article from "@/app/ui/article/article"

export default function Page() {
  const demoArticles: any = [
    {
      id: 1,
      title: "이게 솔직히 내 잘못이냐?",
      content: "blah blah",
      thumbnail_url: "https://ddragon.leagueoflegends.com/cdn/img/champion/centered/Aatrox_0.jpg",
      author: "T1 티모석",
      left_sympathy_title: "정글 잘못",
      right_sympathy_title: "탑 잘못",
      left_sympathy_count: 7,
      right_sympathy_count: 3
    },
    {
      id: 2,
      title: "20만원 빵 내기 중임... 투표 좀",
      content: "blah blah",
      thumbnail_url: "https://ddragon.leagueoflegends.com/cdn/img/champion/centered/Janna_0.jpg",
      author: "Gen Sumin",
      left_sympathy_title: "숟가락 차이",
      right_sympathy_title: "도구 차이",
      left_sympathy_count: 3,
      right_sympathy_count: 7
    }
  ]

  return (
    <div>
      <Search />
      {demoArticles.map((demoArticle: any) => (
        <Article 
          key={demoArticle.id}
          title={demoArticle.title}
          content={demoArticle.content}
          thumbnail_url={demoArticle.thumbnail_url}
          author={demoArticle.author}
          left_sympathy_title={demoArticle.left_sympathy_title}
          right_sympathy_title={demoArticle.right_sympathy_title}
          left_sympathy_count={demoArticle.left_sympathy_count}
          right_sympathy_count={demoArticle.right_sympathy_count}
        />
      ))}
    </div>
  )
}