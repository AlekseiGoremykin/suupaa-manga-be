package com.suupaa.manga.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suupaa.manga.content.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
