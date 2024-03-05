export default {
  title: 'Coaching API',
  apis: [
    {
      id: 'coaching-1',
      spec: '5-2',
      method: 'GET',
      uri: '/coaches/coachings',
      privilege: '3',
      description: '모든 코칭의 정보를 받아온다.',
      request: {},
      response: {
        name: 'AllCoachingsResponseDtos',
        success: {
          description: '코칭정보 리스트로 반환',
          code: '200',
          data: {
            list: 'List<AllCoachingsResponseDto>',
            title: 'String',
            avgScore: 'Float',
            likes: 'Integer',
            Coamelimit: 'Integer',
            currentCoame: 'Integer'
          }
        },
        fail: {
          description: '서버 오류',
          code: '500',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'coaching-2',
      spec: '5-1',
      method: 'POST',
      uri: '/coaches/coachings{longId(member)}',
      privilege: '2',
      description: '코치가 코칭을 개설한다.',
      request: {
        name: 'CreateCoachingRequestDto',
        data: {
          main: 'String',
          sub: 'String',
          name: 'String(max : 50)',
          summary: 'String(max : 100)',
          htmlDocs: 'String(max : 5000)'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '정상 개설완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청[400], 서버 오류[500]',
          code: '400, 500',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'coaching-3',
      spec: '5-2',
      method: 'GET',
      uri: '/coaches/coachings/{longId(member)}',
      privilege: '2',
      description: '코치가 자신의 코칭 정보를 조회한다.',
      request: {},
      response: {
        name: 'MessageDto',
        success: {
          description: '전체 코칭정보 리스트로 반환',
          code: '200',
          data: {
            list: 'List<CoachesCoachingsResponseDto>',
            main: 'String',
            sub: 'String',
            name: 'String(max : 50)',
            summary: 'String(max : 100)',
            htmlDocs: 'String(max : 5000)'
          }
        },
        fail: {
          description: '서버 오류',
          code: '500',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'coaching-4',
      spec: '5-2',
      method: 'PATCH',
      uri: '/coaches/coachings/{longId(member)}',
      privilege: '2',
      description: '코치가 자신의 코칭 정보를 수정한다.',
      request: {
        name: 'CoachingInfoChangeRequestDto',
        data: {
          main: 'String',
          sub: 'String',
          name: 'String(max : 50)',
          summary: 'String(max : 100)',
          htmlDocs: 'String(max : 5000)'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '정상 수정완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청[400], 서버 오류[500]',
          code: '400, 500',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'coaching-5',
      spec: '5-2',
      method: 'DELETE',
      uri: '/coaches/coachings/{id(coaching)}',
      privilege: '2',
      description: '코치가 자신의 코칭을 삭제한다.',
      request: {},
      response: {
        name: 'MessageDto',
        success: {
          description: '코칭 삭제 완료',
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
      id: 'coaching-6',
      spec: '5-3',
      method: 'DELEGETTE',
      uri: '/coachings/{id(coaching)}',
      privilege: '1',
      description: '코미가 코칭 신청을 한다.',
      request: {},
      response: {
        name: 'MessageDto',
        success: {
          description: '코칭 삭제 완료',
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
      id: 'coaching-7',
      spec: '5-4',
      method: 'GET',
      uri: '/coachings/{id(coaching)}/coames',
      privilege: '2',
      description: '코치가 해당 코칭을 수강하고 있는 수강생 목록을 조회한다.',
      request: {},
      response: {
        name: 'StudentResponseDto',
        success: {
          description: '수강생 목록 조회',
          code: '200',
          data: {
            list: 'List<CoachesCoachingsResponseDto>',
            stringId: 'String',
            name: 'String'
          }
        },
        fail: {
          description: '서버 오류',
          code: '500',
          data: { message: 'String' }
        }
      }
    }
  ]
}
