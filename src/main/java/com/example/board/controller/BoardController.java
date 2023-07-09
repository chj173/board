package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // 게시글 리스트
    @GetMapping(value = {"/", "/board"})
    public String board(Model model, @PageableDefault(page = 1) Pageable pageable, String keyword) {

        Page<BoardDTO> boardDTOList;

        if (keyword == null) {
            boardDTOList = boardService.findAll(pageable);
        } else {
            boardDTOList = boardService.boardSearchList(keyword, pageable);
        }

        int showTotalPage = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / showTotalPage))) - 1) * showTotalPage + 1;
        int endPage = Math.min(startPage + showTotalPage - 1, boardDTOList.getTotalPages());

        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board";
    }

    // 글작성
    @GetMapping("/write")
    public String saveForm() {
        return "board_write";
    }

    @PostMapping("/write")
    public String boardSave(@ModelAttribute BoardDTO boardDTO) {
        boardService.save(boardDTO);
        return "redirect:/";
    }

    // 글조회
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model,
                         @PageableDefault(page = 1) Pageable pageable) {

        BoardDTO boardDTO = boardService.findById(id);

        // 조회수 증가
        boardService.updateHits(id);
        // 댓글 목록
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        model.addAttribute("boardDetail", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("commentList", commentDTOList);

        return "board_detail";
    }

    // 글수정
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "board_update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO update = boardService.update(boardDTO);
        model.addAttribute("boardDetail", update);
        return "board_detail";
    }

    // 글삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/";
    }
}
