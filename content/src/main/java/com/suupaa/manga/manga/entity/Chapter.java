package com.suupaa.manga.manga.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chapter")
@SequenceGenerator(name = "chapter_id_generator", sequenceName = "seq_chapter_pk", allocationSize = 1)
@Getter
@Setter
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chapter_id_generator")
    @Column(name = "id")
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "name")
    private String name;

    @Column(name = "manga_id")
    private Long mangaId;

    @JoinColumn(name = "chapter_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @OrderBy("number")
    private Set<Page> pages = new HashSet<>();

}
