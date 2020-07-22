package com.suupaa.manga.manga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "page")
@SequenceGenerator(name = "page_id_generator", sequenceName = "seq_page_pk", allocationSize = 1)
@Getter
@Setter
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_id_generator")
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "image_id")
    private Long imageId;

}
