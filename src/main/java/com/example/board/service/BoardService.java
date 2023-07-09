package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글작성
    public void save(BoardDTO boardDTO) {
        // DTO -> Entity
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    // 글목록
    public Page<BoardDTO> findAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int size = 5;

        // Entity -> DTO
        Page<BoardEntity> boardEntityList = boardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
               /* map으로 받아야 Page의 메서드 기능 사용
                  글번호, 제목, 작성자, 조회수, 작성일 */
        Page<BoardDTO> boardDTOList = boardEntityList.map(board -> new BoardDTO(
                board.getId(),
                board.getWriter(),
                board.getTitle(),
                board.getHits(),
                board.getCreatedTime()
        ));
        
        return boardDTOList;
    }

    // 글조회
    public BoardDTO findById(Long id) {
        // Entity -> DTO
        Optional<BoardEntity> boardRepositoryById = boardRepository.findById(id);
        if (boardRepositoryById.isPresent()) {
            BoardEntity boardEntity = boardRepositoryById.get();
            BoardDTO boardDTO = BoardDTO.EntityToDTO(boardEntity);

            return boardDTO;
        } else {
            return null;
        }
    }

    // 조회수
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    // 글수정
    public BoardDTO update(BoardDTO boardDTO) {
        // DTO -> Entity
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);

        return findById(boardDTO.getId());
    }

    // 글삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // 검색목록
    public Page<BoardDTO> boardSearchList(String keyword, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int size = 5;

        Page<BoardEntity> byBoardTitleContaining =
                boardRepository.findByTitleContaining(keyword, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDTO> boardDTO =
                byBoardTitleContaining.map(boardEntity -> new BoardDTO(
                        boardEntity.getId(),
                        boardEntity.getWriter(),
                        boardEntity.getTitle(),
                        boardEntity.getHits(),
                        boardEntity.getCreatedTime()
                ));

        return boardDTO;
    }
}