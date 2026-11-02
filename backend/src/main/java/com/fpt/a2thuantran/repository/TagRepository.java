package com.fpt.a2thuantran.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.a2thuantran.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByTagNameIgnoreCase(String tagName);
}
