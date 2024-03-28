import { atom } from 'jotai'
import { atomWithStorage } from 'jotai/utils';

// 로컬 스토리지에 초기값 저장
export const isLoggedInAtom = atomWithStorage('isLoggedIn', false)
export const userInfo = atomWithStorage('user', null)
export const testAtom = atomWithStorage('test', 'friday', 'sessionstorage')