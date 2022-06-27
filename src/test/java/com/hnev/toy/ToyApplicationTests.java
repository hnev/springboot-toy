package com.hnev.toy;

import com.hnev.toy.domain.board.Board;
import com.hnev.toy.service.BoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToyApplicationTests {

	@Autowired
	private BoardService boardService;

	@Test
	public void save() {
		Board.RequestDto requestDto = new Board.RequestDto();
		requestDto.setTitle("제목");
		requestDto.setContents("내용");
		requestDto.setUseYn("Y");
		requestDto.setRegisterId(117L);
		requestDto.setModifyId(117L);

		Assertions.assertNotEquals(0, boardService.save(requestDto));
		Assertions.assertNotEquals(0, boardService.save(requestDto));
	}

	@Test
	public void findAll() {
		//Assertions.assertNotEquals(0, boardService.findAll().size());
	}

	@Test
	public void updateBoard() {
		Board.RequestDto requestDto = new Board.RequestDto();
		requestDto.setId(1L);
		requestDto.setTitle("제목 업데이트");
		requestDto.setContents("내용 업데이트");
		requestDto.setModifyId(322L);

		Assertions.assertEquals(1, boardService.updateBoard(requestDto));
	}

	@Test
	public void deleteBoard() {
		Board.RequestDto requestDto = new Board.RequestDto();
		requestDto.setId(2L);
		boardService.deleteBoard(requestDto);
	}
}
