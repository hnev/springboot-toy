package com.hnev.toy.web;

import com.hnev.toy.domain.member.Member;
import com.hnev.toy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/join")
    public String getJoinPage() {
        return "/member/join";
    }

    @GetMapping("/member/login")
    public String getLoginPage(Model model,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/member/login";
    }

    @PostMapping("/member/save")
    public String save(Member.RequestDto requestDto) {
        String url = "/error/blank";

        if (memberService.save(requestDto) > 0) {
            url = "redirect:/member/login";
        }
        return url;
    }

    @PostMapping("/member/count-email")
    public String countByEmailAndDropYn(Model model, Member.RequestDto requestDto) {
        model.addAttribute("count", memberService.countByEmailAndDropYn(requestDto.getEmail(), requestDto.getDropYn()));
        return "jsonView";
    }
}