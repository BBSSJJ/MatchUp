import Image from "next/image";
import Link from "next/link";
import Movie from "../ui/movie";
import styles from "./styles.module.css";

export const metadata = {
  title: "Home",
};

export const API_URL = "https://nomad-movies.nomadcoders.workers.dev/movies";

async function getMovies() {
  // await new Promise((resolve) => setTimeout(resolve, 1000));
  const response = await fetch(API_URL);
  const json = await response.json();
  return json;
}

// 여기가 메인페이지 - root segment 
export default async function HomePage() {
  const movies = await getMovies();
  return (
    <div>
      <div className={styles.container}>
        {movies.map((movie) => (
          <Movie
            key={movie.id}
            id={movie.id}
            poster_path={movie.poster_path}
            title={movie.title}
          />
        ))}
      </div>
    </div>

  );
}
