package com.example.bimovie.repository;

import com.example.bimovie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @EntityGraph(attributePaths = "posterList", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Movie m")
    Page<Movie> findAll2(Pageable pageable);

    //@Query("select m, p, count(p) from Movie m left join Poster p on p.movie = m group by p.movie")//가장 무난한 해결책... 영화 + 포스터 1개 + 포스터의 개수
    @Query("select m, p, count(p) from Movie m left join Poster p on p.movie = m where p.idx = 1 group by p.movie")//가장 무난한 해결책... 영화 + 포스터 1개 + 포스터의 개수
    Page<Object[]> findAll3(Pageable pageable);



}