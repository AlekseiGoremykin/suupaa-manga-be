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
@Table(name = "manga")
@SequenceGenerator(name = "manga_id_generator", sequenceName = "seq_manga_pk", allocationSize = 1)
@Getter
@Setter
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manga_id_generator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "genre")
    private String genre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "manga_id")
    @Fetch(FetchMode.JOIN)
    @OrderBy
    private Set<Chapter> chapters = new HashSet<>();
}
