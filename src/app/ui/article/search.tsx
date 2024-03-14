'use client'

import '@/app/ui/article/article.css'
import Link from 'next/link'
import { Select, SelectItem, Input } from '@nextui-org/react'

export default function Search() {
  return (
    <div className='search'>
      <Select
        defaultSelectedKeys={["최신순"]}
        className='w-24'
        size='sm'
      >
        <SelectItem key="최신순" value="최신순">
          최신순
        </SelectItem>
        <SelectItem key="인기순" value="인기순">
          인기순
        </SelectItem>
      </Select>
      <div className='searchInput'>
        <Select
          defaultSelectedKeys={["제목"]}
          className='w-32'
          size='sm'
        >
          <SelectItem key="제목" value="제목">
            제목
          </SelectItem>
          <SelectItem key="내용" value="내용">
            내용
          </SelectItem>
          <SelectItem key="제목+내용" value="제목+내용">
            제목+내용
          </SelectItem>
        </Select>
        <Input type="search" placeholder='검색어를 입력해 주세요.' className="w-60 ml-3" size='sm' />
        <button className="searchButton">검색</button>
      </div>
      <Link href='/article/create' className='writeLink'>글쓰기</Link>
    </div>
  )
}