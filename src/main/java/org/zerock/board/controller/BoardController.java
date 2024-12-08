package org.zerock.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.service.BoardService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/register")
    public void register(){
        log.info("regiser get...");
    }


    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);
        //새로 추가된 엔티티의 번호
        Long bno = boardService.register(dto);

        log.info("BNO: " + bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/modify")
    public String modify(Model model, @RequestParam("bno") Long bno){
        Optional<Board> board = boardService.getBoardByBno(bno);
        board.ifPresent(dto -> model.addAttribute("dto", dto));
        return "board/modify";
    }

    @GetMapping("/list")
    public void list(Model model){
        List<Board> boardList = boardService.getBoardList();
        model.addAttribute("boards", boardList);
    }

    @PostMapping("/modify")
    public String modifyPost(BoardDTO dto){
        boardService.modify(dto);
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(BoardDTO dto){
        boardService.remove(dto.getBno());
        return "redirect:/board/list";
    }
}
