package com.example.bimovie.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "posterList")
@Table(name="tbl_movie")
public class Movie extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mno;

    private String title;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "movie",//자신이 연관관계의 owner가 아님을 명시..
            cascade = CascadeType.ALL,//Movie 엔티티 저장할 때 Poster 객체들도 같이 지정...수정이나 삭제도 필요하므로 ALL
            orphanRemoval = true)//참조가 없는 하위 엔티티 객체는 삭제한다.
    private List<Poster> posterList = new ArrayList<>();

    public void addPoster(Poster poster){//포스터 추가
        poster.setIdx(this.posterList.size());
        poster.setMovie(this);
        posterList.add(poster);
    }

    public void removePoster(Long ino){//포스터 삭제
        Optional<Poster> result = posterList.stream().filter(p -> p.getIno() == ino).findFirst();

        result.ifPresent(p -> {
            p.setMovie(null);//참조 관계 안전하게 끊기..orphanRemoval = true에 의해 포스터 삭제됨
            posterList.remove(p);
        });

        changeIdx();//포스터 번호 변경
    }

    private void changeIdx() {
        for(int i = 0; i < posterList.size(); i++){
            posterList.get(i).setIdx(i);
        }
    }
}
