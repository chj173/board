package com.example.board.repository;

import com.example.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "UPDATE BoardEntity b SET b.hits = b.hits + 1 WHERE b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<BoardEntity> findByTitleContaining(String keyword, Pageable pageable);
}
