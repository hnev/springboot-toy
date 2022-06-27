package com.hnev.toy.web;

import com.hnev.toy.domain.board.Board;
import com.hnev.toy.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String getBoardIndexPage(Model model,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "5") Integer size) {
        model.addAttribute("result", boardService.findAll(page, size));
        return "/board/index";
    }

    @GetMapping("/board/write")
    public String getBoardWritePage(Model model, Board.RequestDto requestDto) {

        if (requestDto.getId() != null) {
            model.addAttribute("info", boardService.findById(requestDto.getId()));
        }

        return "/board/write";
    }

    @PostMapping("/board/save")
    public String save(Model model, Board.RequestDto requestDto) {
        String url = "/error/blank";

        if (boardService.save(requestDto) > 0) {
            url = "redirect:/board";
        }

        return url;
    }

    @PostMapping("/board/update")
    public String update(Model model, Board.RequestDto requestDto) {
        String url = "/error/blank";

        if (boardService.updateBoard(requestDto) > 0) {
            url = "redirect:/board";
        }

        return url;
    }

    @PostMapping("/board/delete")
    public String delete(Model model, Board.RequestDto requestDto) {
        boardService.deleteBoard(requestDto);
        return "redirect:/board";
    }
}
