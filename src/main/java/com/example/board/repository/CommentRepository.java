package com.example.board.repository;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // SELECT * FROM comment WHERE board_id=? ORDER BY id DESC;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
