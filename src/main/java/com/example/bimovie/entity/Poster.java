package com.example.bimovie.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
@Table(name="tbl_poster")
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ino;

    private String fname;

    //포스터 순번
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;//movie가 one, poster가 many

    public void setIdx(int idx){
        this.idx = idx;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }
}
