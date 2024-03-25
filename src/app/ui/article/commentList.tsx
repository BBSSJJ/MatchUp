import React from 'react';

interface Writer {
  userId: number;
  role: string;
  riotAccount: {
    riotTokenValue: string;
    summonerProfile: any; // 여기에 SummonerProfile에 대한 타입을 정의해야 합니다.
    tier: string | null;
    leagueRank: string | null;
    leaguePoint: number | null;
  };
}

interface Comment {
  id: number;
  content: string;
  writer: Writer;
  childrenComments: Comment[];
  createdAt: string;
  updatedAt: string;
}


const Comment = ({ comment } : { comment :Comment}) => {
  return (
    <div className="comment">
      <div className="comment-content">{comment.content}</div>
      <div className="comment-writer">작성자: {comment.writer.userId}</div>
      <div className="comment-createdAt">작성일시: {comment.createdAt}</div>
      {comment.childrenComments && comment.childrenComments.length > 0 && (
        <div className="children-comments">
          {comment.childrenComments.map(childComment => (
            <Comment key={childComment.id} comment={childComment} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Comment;
