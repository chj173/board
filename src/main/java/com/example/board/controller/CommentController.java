package com.example.board.controller;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.CommentEntity;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 추가
    @PostMapping("/comment/save")
    public ResponseEntity commentWrite(@ModelAttribute CommentDTO commentDTO) {

        Long saveResult = commentService.save(commentDTO); // 반환 된 id값

        if (saveResult != null) {
            // 해당 게시글의 댓글을 다 가져옴
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
