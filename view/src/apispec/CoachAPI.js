export default {
  title: 'Coach API',
  apis: [
    {
      id: 'coach-1',
      spec: '3-1',
      method: 'GET',
      uri: '/coaches/categories?division1={대분류명}&division2={소분류명}',
      privilege: '0',
      description: '해당 분류 코치들의 정보를 받아온다.',
      request: {},
      response: {
        name: 'CoachesResponseDtos',
        success: {
          description: '해당 코치정보 리스트로 반환',
          code: '200',
          data: {
            dtos: 'List<CoachesResponseDtos>',
            CoachesResponseDto: {
              name: 'String',
              avgScore: 'Float',
              description: 'String',
              lessonInfo: 'String',
              lessonPrice: 'Integer'
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
      id: 'coach-2',
      spec: '3-2',
      method: 'GET',
      uri: '/coaches/portfolio/{longId}',
      privilege: '2',
      description: '코치는 본인의 포트폴리오를 조회한다.',
      request: {},
      response: {
        name: 'PortfolioResponseDto',
        success: {
          description: '권한 변경 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '서버 오류',
          code: '500',
          data: { message: 'String' }
        }
      }
    },

    {
      id: 'coach-3',
      spec: '3-2',
      method: 'PATCH',
      uri: '/coaches/portfolio/{longId}',
      privilege: '2',
      description: '코치는 본인의 포트폴리오를 수정한다.',
      request: {
        name: 'PortfolioRequestDto',
        data: {
          ids: 'Array'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '권한 변경 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청[400], 서버 오류[500]',
          code: '400, 500',
          data: { message: 'String' }
        }
      }
    }
  ]
}
