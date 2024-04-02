import { atom } from 'jotai'
import { atomWithStorage, createJSONStorage } from 'jotai/utils';


// 로컬 스토리지에 초기값 저장
export const isLoggedInAtom = atomWithStorage('isLoggedIn', false)
export const userInfoAtom = atomWithStorage('user', null)


// 세션 스토리지 사용시
const storage = createJSONStorage(() => sessionStorage)
const someAtom = atomWithStorage('some', true, storage)