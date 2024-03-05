export default {
  title: 'Review API',
  apis: [
    {
      id: 'review-1',
      spec: '9-1',
      method: 'POST',
      uri: '/review',
      privilege: 1,
      description: '코미는 코치에 대한 리뷰를 작성한다.',
      request: {
        name: 'ReviewCoachRegistRequestDto',
        data: {
          coameId: 'String',
          coachId: 'String',
          comment: 'String',
          score: 'Integer'
        }
      },
      response: {
        name: 'ResponseMessage',
        success: {
          description: '해당 리뷰 등록 요청 유효함',
          code: 200,
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 리뷰 등록 요청',
          code: 403,
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'review-2',
      spec: '9-1',
      method: 'POST',
      uri: '/review/coaching',
      privilege: 1,
      description: '코미는 코칭에 대한 리뷰를 작성한다.',
      request: {
        name: 'ReviewCoachingRegistRequestDto',
        data: {
          coameId: 'String',
          coachingId: 'String',
          comment: 'String',
          score: 'Integer'
        }
      },
      response: {
        name: 'responseMessage',
        success: {
          description: '해당 리뷰 등록 요청 유효함',
          code: 200,
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 리뷰 등록 요청',
          code: 403,
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'review-3',
      spec: '9-2',
      method: 'PUT',
      uri: '/review/{reviewId}',
      privilege: 1,
      description: '코미는 본인의 리뷰를 수정할 수 있다.',
      request: {
        name: 'ReviewComiUpdateRequestDto',
        data: {
          comment: 'String',
          score: 'Integer'
        }
      },
      response: {
        name: 'ResponseMessage',
        success: {
          description: '해당 리뷰 수정 요청 유효함',
          code: 200,
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 리뷰 수정 요청',
          code: 403,
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'review-4',
      spec: '9-2',
      method: 'DELETE',
      uri: '/review/{reviewId}',
      privilege: 1,
      description: '코미는 본인의 리뷰를 삭제할 수 있다.',
      request: {},
      response: {
        name: 'ResponseMessage',
        success: {
          description: '해당 리뷰 삭제 요청 유효함',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 리뷰 삭제 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'review-5',
      spec: '9-3',
      method: 'GET',
      uri: '/review/coach/{coachId}',
      privilege: 1,
      description: '코치에 대한 리뷰를 조회한다.',
      request: {},
      response: {
        name: 'CoachReviewResponseDto',
        success: {
          description: '코치 리뷰 조회 성공',
          code: 200,
          data: {
            reviewId: 'String',
            coameId: 'String',
            comment: 'String',
            score: 'Integer'
          }
        },
        fail: {
          description: '코치 리뷰 조회 실패',
          code: 404,
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'review-6',
      spec: '9-3',
      method: 'GET',
      uri: '/review/coaching/{coachingId}',
      privilege: 1,
      description: '코칭에 대한 리뷰를 조회한다.',
      request: {},
      response: {
        name: 'CoachingReviewResponseDto',
        success: {
          description: '코칭 리뷰 조회 성공',
          code: 200,
          data: {
            reviewId: 'String',
            coameId: 'String',
            comment: 'String',
            score: 'Integer'
          }
        },
        fail: {
          description: '코칭 리뷰 조회 실패',
          code: 404,
          data: { message: 'String' }
        }
      }
    }
  ]
}
