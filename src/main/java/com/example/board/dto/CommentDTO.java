package com.example.board.dto;

import com.example.board.entity.CommentEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private Timestamp commentCreatedTime;

    public static CommentDTO EntityToDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        /* 부모 Entity에서 id값 (글번호) 가져옴
           Service에 @Transactional 추가 */
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());

        return commentDTO;
    }
}
