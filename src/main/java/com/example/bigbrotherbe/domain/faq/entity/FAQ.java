package com.example.bigbrotherbe.domain.faq.entity;

import com.example.bigbrotherbe.domain.BaseTimeEntity;
import com.example.bigbrotherbe.global.file.entity.File;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class FAQ extends BaseTimeEntity {
    @Id                                                 //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB에서 값 자동 생성
    @Column(name = "faq_id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, name = "faq_title")
    private String title;

    @Column(nullable = false, name = "faq_content", columnDefinition = "TEXT") // 긴 문자열
    private String content;

    @Column
    private Long affiliationId;

    @OneToMany
    @JoinColumn(name = "faq_id")
    private List<File> files;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
