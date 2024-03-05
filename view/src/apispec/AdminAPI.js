export default {
  title: 'Admin API',
  apis: [
    {
      id: 'admin-1',
      spec: '2-1',
      method: 'GET',
      uri: '/admin/members',
      privilege: '3',
      description: '모든 회원정보를 받아온다.',
      request: {},
      response: {
        name: 'AdminMembersResponseDto',
        success: {
          description: '모든 회원정보 리스트로 반환',
          code: '200',
          data: {
            list: 'List<AdminMembersResponseDto>',
            stringId: 'String',
            priv: 'String',
            name: 'String',
            nick: 'String',
            email: 'String',
            cdate: 'LocalDateTime',
            mdate: 'LocalDateTime',
            status: 'String'
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
      id: 'admin-2',
      spec: '2-2',
      method: 'GET',
      uri: '/admin/privileges/elevations',
      privilege: '3',
      description: '권한 상승요청 목록을 조회한다.',
      request: {},
      response: {
        name: 'AdminElevationsResponseDto',
        success: {
          description: '권한 상승정보 리스트로 반환',
          code: '200',
          data: {
            list: 'List<AdminElevationsResponseDto>',
            id: 'Long(Member PK)',
            name: 'String',
            portfolio: 'String(Raw HTML)'
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '400(요청정보 오류), 403(권한 없음)',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'admin-3',
      spec: '2-3',
      method: 'PATCH',
      uri: '/admin/privileges/elevations',
      privilege: '3',
      description: '해당 아이디의 권한을 1에서 2로 상승시킨다.',
      request: {
        name: 'AdminElevateRequestDto',
        data: {
          id: 'Number(List) - Member PK'
        }
      },
      response: {
        name: 'AdminElevateResponseDto',
        success: {
          description: '권한 변경 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '400(요청정보 오류), 403(권한 없음)',
          data: { message: 'String' }
        }
      }
    }
  ]
}
