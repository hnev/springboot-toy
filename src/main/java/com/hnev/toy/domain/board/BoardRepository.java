package com.hnev.toy.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    String updateBoardQuery = "update board " +
            "set title = :#{#requestDto.title}, " +
            "contents = :#{#requestDto.contents}, " +
            "use_yn = :#{#requestDto.useYn}, " +
            "modify_id = :#{#requestDto.modifyId}, " +
            "modify_time = NOW() " +
            "where id = :#{#requestDto.id}";
    @Transactional
    @Modifying
    @Query(value = updateBoardQuery, nativeQuery = true)
    public int updateBoard(@Param("requestDto") Board.RequestDto requestDto);
}
