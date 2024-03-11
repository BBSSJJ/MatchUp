import '@/app/ui/article/article.css'
import Link from 'next/link'

export default function Search() {
  return (
    <div className='search'>
      <select name="orderBy" className="orderBy">
        <option value="최신순">최신순</option>
        <option value="인기순">인기순</option>
      </select>
      <div>
        <select name="searchBy" className="searchBy">
          <option value="제목">제목</option>
          <option value="내용">내용</option>
          <option value="제목+내용">제목+내용</option>
        </select>
        <input type="text" className='searchInput' placeholder="검색어를 입력해주세요." />
        <button className="searchButton">검색</button>
      </div>
      <Link href='/article/create' className='writeLink'>글쓰기</Link>
    </div>
  )
}