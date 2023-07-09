package com.example.board.service;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 저장
    public Long save(CommentDTO commentDTO) {
        /* 부모 엔티티 id값 조회(글번호) */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            // DTO -> Entity
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    // 댓글 목록
    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        // 글번호로 조회 후 존재하는지
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            // board_id를 기준으로 내림차순 정렬
            List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

            // Entity리스트 받을 DTO리스트
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for(CommentEntity commentEntity: commentEntityList) {
                // Entity -> DTO
                commentDTOList.add(CommentDTO.EntityToDTO(commentEntity));
            }
            return commentDTOList;
        }
        return null;
    }
}
