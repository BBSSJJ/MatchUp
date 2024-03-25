'use client'

import '@/app/ui/article/article.css'
import Link from 'next/link'
import { Select, SelectItem, Input, Button } from '@nextui-org/react'
import { useRouter } from 'next/navigation'

export default function Search() {
  const router = useRouter()
  const handleCreateArticle = () => {
    router.push('/article/create')
  }

  return (
    <div className='search'>
      <div className='searchInput'>
        <Select
          defaultSelectedKeys={["최신순"]}
          className='w-32'
          size='sm'
        >
          <SelectItem key="최신순" value="최신순">
            최신순
          </SelectItem>
          <SelectItem key="인기순" value="인기순">
            인기순
          </SelectItem>
        </Select>
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
        <Button className="searchButton m-2" variant="ghost">검색</Button>
      </div>
      <Button className="createButton" onClick={handleCreateArticle} variant="ghost">글쓰기</Button>
      {/* <Link href='/article/create' className='writeLink'></Link> */}
    </div>
  )
}