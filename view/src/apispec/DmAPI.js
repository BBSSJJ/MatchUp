export default {
  title: 'DM API',
  apis: [
    {
      id: 'dm-1',
      spec: '4-1',
      method: 'GET',
      uri: '/dm?id={아이디}',
      privilege: 1,
      description: '회원의 전체 DM방 목록을 불러온다',
      request: {
        
      },
      response: {
        name: 'DMListResponseDto',
        success: {
          description: 'DM 목록을 리스트로 반환',
          code: '200',
          data: {
            dtos: 'List<DMListResponseDto>',
            DMListResponseDto: {
              name: 'String',
              nickname: 'String',
              profileImage: 'String',
              lastMessage: 'String'
            }
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'dm-2',
      spec: '4-1',
      method: 'DELETE',
      uri: '/dm/delete',
      privilege: 1,
      description: '회원이 선택한 DM방을 삭제한다',
      request: {
        
      },
      response: {
        name: 'responseMessage',
        success: {
          description: '삭제 확인 메세지',
          code: '200',
          data: {
            message: 'String'
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'dm-3',
      spec: '4-1',
      method: 'GET',
      uri: '/dm/room?coameId={코미ID}&coachId={코치ID}',
      privilege: 1,
      description: '회원과 코치의 DM방을 불러온다',
      request: {
      
      },
      response: {
        name: 'DMInfoDto',
        success: {
          description: 'DM방의 모든 정보 반환 (DM방 존재하지 않으면 Null 반환)',
          code: '200',
          data: {
            chatRoomId: 'String',
            chats: 'List',
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'dm-4',
      spec: '4-1',
      method: 'POST',
      uri: '/dm/{DM방 ID}',
      privilege: 1,
      description: '회원이 DM을 전송한다',
      request: {
        
      },
      response: {
        name: 'responseMessage',
        success: {
          description: 'DM 전송',
          code: '200',
          data: {
            message: 'String'
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    }
  ]
}
