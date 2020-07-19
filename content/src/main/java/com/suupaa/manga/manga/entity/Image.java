package com.suupaa.manga.manga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@SequenceGenerator(name = "image_id_generator", sequenceName = "seq_image_pk", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_generator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }
}
