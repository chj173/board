package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private int hits;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    // 게시판에 보여줄 생성자
    public BoardDTO(Long id, String writer, String title, int hits, Timestamp createdTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.hits = hits;
        this.createdTime = createdTime;
    }

    // Entity -> DTO 변환
    public static BoardDTO EntityToDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setWriter(boardEntity.getWriter());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContents(boardEntity.getContents());
        boardDTO.setHits(boardEntity.getHits());
        boardDTO.setCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setUpdatedTime(boardEntity.getUpdatedTime());

        return boardDTO;
    }


}