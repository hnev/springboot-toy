package com.hnev.toy.service;

import com.hnev.toy.domain.board.Board;
import com.hnev.toy.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(Board.RequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    public HashMap<String, Object> findAll(Integer page, Integer size) {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        Page<Board> list = boardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")));

        resultMap.put("list", list.stream().map(Board.ResponseDto::new).collect(Collectors.toList()));
        resultMap.put("paging", list.getPageable());
        resultMap.put("totalCnt", list.getTotalElements());
        resultMap.put("totalPage", list.getTotalPages());

        return resultMap;
    }

    public Board.ResponseDto findById(Long id) {
        return new Board.ResponseDto(boardRepository.findById(id).get());
    }

    public int updateBoard(Board.RequestDto requestDto) {
        return boardRepository.updateBoard(requestDto);
    }

    public void deleteBoard(Board.RequestDto requestDto) {
        boardRepository.deleteById(requestDto.getId());
    }
}
