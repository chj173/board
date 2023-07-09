package com.example.board.entity;

import com.example.board.dto.BoardDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "board")
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String writer;
    @Column
    private String title;
    @Column
    private String contents;
    @Column
    private int hits;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    
    // 글작성 Entity -> DTO 변환
    public static BoardEntity DTOtoEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setWriter(boardDTO.getWriter());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContents(boardDTO.getContents());
        boardEntity.setHits(0); // 0으로 셋팅

        return boardEntity;
    }

    // 글수정 Entity -> DTO 변환
    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setWriter(boardDTO.getWriter());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContents(boardDTO.getContents());
        boardEntity.setHits(boardDTO.getHits());

        return boardEntity;
    }
}
