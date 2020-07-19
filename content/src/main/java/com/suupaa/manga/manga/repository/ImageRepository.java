package com.suupaa.manga.manga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suupaa.manga.manga.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
