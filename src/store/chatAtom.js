import { atom } from 'jotai'
import { atomWithStorage } from 'jotai/utils';

export const isRoomOpenAtom = atom(false)
export const roomIdAtom = atomWithStorage("roomId","")