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

export interface LobbyChatForm  {
  method: string | null;
  objectId: string;
  userId: string | null;
  name: string;
  iconUrl: string | null;
  tier: string;
  line: string;
  wishLine: string;
  gameType: string;
  content: string;
  win: number;
  lose: number;
  kill: number;
  death: number;
  assist: number;
  timestamp: string;
}