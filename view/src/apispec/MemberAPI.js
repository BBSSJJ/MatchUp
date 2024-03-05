export default {
  title: 'Member API',
  apis: [
    {
      id: 'member-1',
      spec: '1-2',
      method: 'POST',
      uri: '/members/login',
      privilege: '0',
      description: '해당 api는 더이상 사용하지 않음(deprecated)',
      request: {
        name: '',
        data: {}
      },
      response: {
        name: '',
        success: {
          description: '',
          code: '',
          data: {}
        },
        fail: {
          description: '',
          code: '',
          data: {}
        }
      }
    },
    {
      id: 'member-2',
      spec: '1-3',
      method: 'POST',
      uri: '/members',
      privilege: '0',
      description: '회원가입 요청 시 해당 정보를 DB에 저장한다.',
      request: {
        name: 'MemberRegistRequestDto',
        data: {
          stringId: 'String(max : 20)',
          pw: 'String(max : 30)',
          name: 'String(max : 50)',
          nick: 'String(max : 10)',
          email: 'String(max : 50)'
        }
      },
      response: {
        name: 'MemberRegistResponseDto',
        success: {
          description: '회원가입 성공',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 회원가입 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-3',
      spec: '1-4',
      method: 'PATCH',
      uri: '/members/passwords',
      privilege: '0',
      description:
        '비밀번호 찾기(change password) 요청 시 해당 아이디와 이메일이 유효한지 확인하고, 비밀번호 변경 후 이메일로 임시 비밀번호를 발송한다.',
      request: {
        name: 'changePasswordRequestDto',
        data: {
          stringId: 'String(max : 20)',
          email: 'String(max : 50)'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '비밀번호 변경 및 임시 비밀번호 발송 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 비밀번호 찾기 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-4',
      spec: '1-5',
      method: 'POST',
      uri: '/members/privileges/elevations',
      privilege: '1',
      description: '회원정보 권한 상승 요청 시, 유효한 요청인지 확인 후 권한 상승 목록에 추가한다.',
      request: {
        name: 'ElevationRequestDto',
        data: {
          longId: 'Long',
          htmlDocs: 'String'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '권한 상승목록에 추가 완료',
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
      id: 'member-5',
      spec: '1-6',
      method: 'GET',
      uri: '/members/{longId}',
      privilege: '1',
      description: '기본 회원정보 조회',
      request: {},
      response: {
        name: 'MemberInfoResponseDto',
        success: {
          description: '기본 회원정보 데이터 전송',
          code: '200',
          data: { stringId: 'String', name: 'String', nick: 'String', email: 'String' }
        },
        fail: {
          description: '[400]잘못된 요청, [403]권한 오류',
          code: '400, 403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-6',
      spec: '1-6',
      method: 'PATCH',
      uri: '/members/{longId}',
      privilege: '1',
      description: '회원정보 수정 시 입력한 비밀번호를 검증한 후, 회원정보 변경',
      request: {
        name: 'MemberInfoChangeRequestDto',
        data: {
          pw: 'String',
          nick: 'String',
          email: 'String'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '기본 회원정보 수정 완료',
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
      id: 'member-7',
      spec: '1-7',
      method: 'GET',
      uri: '/members/{longId}/profiles',
      privilege: '1',
      description: '코미나 코치의 프로필 글과 사진에 대한 조회결과를 전송한다.',
      request: {},
      response: {
        name: 'ProfileResponseDto',
        success: {
          description: '프로필 글 조회결과 전송',
          code: '200',
          data: {
            profileText: 'String',
            profileImageUrl: 'String'
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
      id: 'member-8',
      spec: '1-7',
      method: 'POST',
      uri: '/members/{longId}/profiles/texts',
      privilege: '1',
      description: '코치나 코미가 프로필 글을 등록한다.(기존 글 덮어쓰기)',
      request: {
        name: 'ProfileTextRequestDto',
        data: {
          profileText: 'String'
        }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '프로필 글 등록 완료',
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
      id: 'member-9',
      spec: '1-7',
      method: 'POST',
      uri: '/members/{longId}/profiles/images',
      privilege: '1',
      description: '코치나 코미가 프로필 사진을 등록한다.(기존에 사진이 있으면 삭제)',
      request: {
        name: 'ProfileImageRequestDto',
        data: { type: 'FormData' }
      },
      response: {
        name: 'MessageDto',
        success: {
          description: '프로필 사진 등록 완료',
          code: '200',
          data: { profileImageUrl: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-10',
      spec: '1-7',
      method: 'PATCH',
      uri: '',
      privilege: '1',
      description: '해당 api는 더이상 사용하지 않음(deprecated)',
      request: {
        name: 'ProfileTextRequestDto',
        data: {
          profileText: 'String'
        }
      },
      response: {
        name: '',
        success: {
          description: '',
          code: '',
          data: { message: '' }
        },
        fail: {
          description: '',
          code: '',
          data: { message: '' }
        }
      }
    },
    {
      id: 'member-11',
      spec: '1-7',
      method: 'PATCH',
      uri: '',
      privilege: '1',
      description: '해당 api는 더이상 사용하지 않음(deprecated)',
      request: {
        name: 'ProfileImageRequestDto',
        data: { type: 'FormData' }
      },
      response: {
        name: '',
        success: {
          description: '',
          code: '',
          data: { message: '' }
        },
        fail: {
          description: '',
          code: '',
          data: { message: '' }
        }
      }
    },
    {
      id: 'member-12',
      spec: '1-7',
      method: 'DELETE',
      uri: '/members/{longId}/profiles/images',
      privilege: '1',
      description: '코치나 코미가 프로필 사진을 삭제한다.',
      request: {},
      response: {
        name: 'MessageDto',
        success: {
          description: '프로필 삭제완료',
          code: '200',
          data: { message: '프로필 삭제완료' }
        },
        fail: {
          description: '서버 오류',
          code: '500',
          data: { message: 'Internal Server Error' }
        }
      }
    },
    {
      id: 'member-13',
      spec: '1-6',
      method: 'DELETE',
      uri: '/members/{longId}',
      privilege: '1',
      description: '회원을 탈퇴한다.',
      request: {},
      response: {
        name: 'MessageDto',
        success: {
          description: '회원탈퇴 완료',
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
      id: 'member-14',
      spec: '1-3',
      method: 'POST',
      uri: '/members/duplicate/id',
      privilege: '0',
      description: '회원 ID를 중복검사한다.',
      request: {
        name: 'MemberDuplicateRequestDto',
        data: { stringId: 'String' }
      },
      response: {
        name: 'MemberDuplicateResponseDto',
        success: {
          description: '사용 가능한 아이디',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청(중복)',
          code: '409',
          data: { message: 'String' }
        }
      }
    }
  ]
}
