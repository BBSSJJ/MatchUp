export default {
  title: 'Like API',
  apis: [
    {
      id: 'like-1',
      spec: '8-1',
      method: 'POST',
      uri: '/coaches/categories?division1={대분류명}&division2={소분류명}',
      privilege: '1',
      description: '코미가 코치나 코칭을 찜콩한다.',
      request: {
        name: 'LikeRequestDto',
        data: { id: 'Integer' }
      },
      response: {
        name: 'LikeResponseDto',
        success: {
          description: '찜콩 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'like-2',
      spec: '8-2',
      method: 'GET',
      uri: '/coaches/categories?division1={대분류명}&division2={소분류명}',
      privilege: '1',
      description: '코미가 찜콩한 수강리스트를 조회한다.',
      request: {},
      response: {
        name: 'LikeListResponseDtos',
        success: {
          description: '찜콩 리스트 조회 성공',
          code: '200',
          data: {
            dtos: 'List<CoachesResponseDtos>',
            LikeListResponseDtos: {
              likeId: 'Integer',
              coameId: 'String',
              coachId: 'String',
              coachingId: 'String',
              coachingName: 'String'
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
      id: 'like-3',
      spec: '8-3',
      method: 'GET',
      uri: '/coaches/categories?division1={대분류명}&division2={소분류명}',
      privilege: '2',
      description: '코미가 찜콩한 코치에게 알림을 보낸다',
      request: {},
      response: {
        name: 'LikeNotificationResponseDtos',
        success: {
          description: '해당 코치에게 알림 전달 성공',
          code: '200',
          data: {
            likeId: 'Integer',
            coameId: 'String',
            coachId: 'String',
            coachingId: 'String',
            coachingName: 'String'
          }
        },
        fail: {
          description: '알림 실패',
          code: '403',
          data: { message: 'String' }
        }
      }
    }
  ]
}
