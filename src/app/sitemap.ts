import { MetadataRoute } from 'next'

//matchup.site/sitemap.xml 페이지를 만들어주는 함수
export default function sitemap(): MetadataRoute.Sitemap {
  return [
    {
      url: 'https://matchup.site',
      lastModified: new Date(),
      changeFrequency: 'yearly',
      priority: 0.5,
    },
    {
      url: 'https://matchup.site/lobby',
      lastModified: new Date(),
      changeFrequency: 'daily',
      priority: 1,
    },
    {
      url: 'https://matchup.site/article',
      lastModified: new Date(),
      changeFrequency: 'daily',
      priority: 0.8,
    },
  ]
}