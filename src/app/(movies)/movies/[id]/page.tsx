import MovieVideos from "@/app/ui/movie-videos";
import MovieInfo from "@/app/ui/movie-info";
import { Suspense } from "react";

// 원래 이 페이지에서 movieInfo와 movieVideo를 모두 fetch함
// 현재는 MovieVideos, MovieInfo 컴포넌트로 대체 - async 컴포넌트임
// Suspense 컴포넌트의 fallback prop은 컴포넌트의 비동기 작업이 끝나 프로미스를
// 반환할 때까지 render할 메세지를 지정 

export default async function MovieDetail({
    params: { id },
  }: {
    params: { id: string };
  }) {
    return (
			<div>
				<h3>사용자에게 즉시 표시됩니다!</h3>
				<h1>
					<h3>영화 비디오</h3>
					<Suspense fallback={<h1>Loading movie info</h1>}>
						<MovieVideos id={id} />
					</Suspense>
					<h3>영화 정보</h3>
					<Suspense fallback={<h1>Loading movie videos</h1>}>
						<MovieInfo id={id} />
					</Suspense>
				</h1>
			</div>
		) 
}