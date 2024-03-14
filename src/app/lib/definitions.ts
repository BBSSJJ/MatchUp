export type ArticleForm = {
  id: number;
  title: string;
  content: string;
  author: string;
  video_url: string | null;
  thumbnail_url: string | null;
  views: number;
  left_sympathy_title: string;
  right_sympathy_title: string;
  left_sympathy_percent: number;
  right_sympathy_percent: number;
}