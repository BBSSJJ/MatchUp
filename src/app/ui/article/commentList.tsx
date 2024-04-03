import React from 'react';
import { useState } from 'react';
import { Reply } from '@/app/ui/article/article'
import { Button, Textarea } from '@nextui-org/react';
import styles from './commentList.module.css'
import { SERVER_API_URL } from '@/utils/instance-axios';
import useSWR, { mutate } from 'swr';
import { useAtomValue } from 'jotai';
import { isLoggedInAtom } from '@/store/authAtom';

interface Writer {
  userId?: number;
  role?: string;
  riotAccount?: {
    riotTokenValue?: string;
    summonerProfile?: any; // 여기에 SummonerProfile에 대한 타입을 정의해야 합니다.
    tier?: string | null;
    leagueRank?: string | null;
    leaguePoint?: number | null;
  };
}

// 개별 댓글 및 대댓글 
const Comment = ({ comment, articleId, parentId } : { comment :Reply, articleId: number, parentId: number}) => {
  const [toggle, setToggle] :[boolean, React.Dispatch<React.SetStateAction<boolean>>] = useState(true);
  const [replyContent, setReplyContent] = useState("")
  const isLoggedIn = useAtomValue(isLoggedInAtom)
  const { data , error } = useSWR(`${SERVER_API_URL}/api/mz/comments/articles/${articleId}`)
  const utcDate = new Date(comment.createdAt)
  utcDate.setHours(utcDate.getHours() + 8)
  const createdAtKST = utcDate.toISOString().replace("T", " ").slice(0, -5)

  // 대댓글 달기
  const handleReply = async () => {
    if(!isLoggedIn) {
      alert("댓글은 회원만 작성할 수 있습니다.")
      setReplyContent("")
      return
    }
    if(!replyContent.trim()){
      alert("내용을 입력하세요!")
      return
    }
    setToggle(prev => !prev)

    const response = await fetch(`${SERVER_API_URL}/api/mz/comments/articles/${articleId}`, 
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        content: replyContent,
        parentCommentId: parentId,
      })
    })

    if (response.ok) {
      setReplyContent("")
      mutate(`${SERVER_API_URL}/api/mz/comments/articles/${articleId}`)
    }
  }


  return (
    <div className={styles.comment}>
      <div className='flex justify-between'>
        <div className='flex flex-col w-[70%]'>
          <p className={styles.commentContent}>{comment.content}</p>
          <p className={styles.commentWriter}>{comment.writer.riotAccount.summonerProfile.name}</p>
          <p className={styles.commentCreatedAt}>작성일시: {createdAtKST}</p>
          <div className='flex'>
            <Textarea 
              className={`${toggle ? styles.hide : ''} mt-4`} 
              size='sm'
              placeholder="답글을 작성하세요"
              value={replyContent}
              onValueChange={(value:string):void => { setReplyContent(value)}} 
            />
            <Button className={`${toggle ? styles.hide : '' } mt-4`} size='sm' color='secondary' onClick={handleReply}>완료</Button>
          </div>
        </div>
        <Button 
          size='sm'
          variant='bordered'
          className={`${toggle ? '' : styles.hide } mt-4`} 
          onClick={() => setToggle(prev => !prev)}
        >답글 달기</Button>
      </div>
    
      {/* 대댓글 보여주기 */}
      {comment.childrenComments && comment.childrenComments.length > 0 && (
        <div className={styles.childrenComments}>
          {comment.childrenComments.map((childComment) => (
            <Comment key={childComment.id} comment={childComment} articleId={articleId} parentId={childComment.id}/>
          ))}
        </div>
      )}
    </div>
  );
};

export default Comment;
