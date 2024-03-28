// // api.ts 파일에 데이터 가져오는 함수를 정의합니다.
// import { SERVER_API_URL } from "./instance-axios";

// export async function getArticle(id: number) {
//     try {
//       const response = await fetch(`${SERVER_API_URL}/api/mz/articles/${id}`);
  
//       if (!response.ok) {
//         throw new Error('게시글 정보 가져오기 실패');
//       }
  
//       return response.json();
//     } catch (error) {
//       console.error(error);
//       throw error;
//     }
// }

// export async function getComments(id: number) {
//     try {
//         const response = await fetch(`${SERVER_API_URL}/api/mz/comments/articles/${id}`);

//         if (!response.ok) {
//         throw new Error('댓글 정보 가져오기 실패');
//         }

//         return response.json();
//     } catch (error) {
//         console.error(error);
//         throw error;
//     }
// }
  