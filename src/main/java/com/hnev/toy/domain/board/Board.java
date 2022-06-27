package com.hnev.toy.domain.board;

import com.hnev.toy.domain.BaseTimeEntity;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity(name = "board")
public class Board extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    private String contents;
    private String useYn;
    private Long registerId;
    private Long modifyId;

    @Builder
    public Board(Long id, String title, String contents, String useYn, Long registerId, Long modifyId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.useYn = useYn;
        this.registerId = registerId;
        this.modifyId = modifyId;
    }

    @Getter
    @Setter
    public static class RequestDto {
        private Long id;
        private String title;
        private String contents;
        private String useYn;
        private Long registerId;
        private Long modifyId;

        public Board toEntity() {
            return Board.builder()
                    .title(title)
                    .contents(contents)
                    .useYn(useYn)
                    .registerId(registerId)
                    .modifyId(modifyId)
                    .build();
        }
    }

    @Getter
    public static class ResponseDto {
        private Long id;
        private String title;
        private String contents;
        private String useYn;
        private Long registerId;
        private String registerTime;
        private Long modifyId;
        private String modifyTime;

        public ResponseDto(Board board) {
            this.id = board.id;
            this.title = board.title;
            this.contents = board.contents;
            this.useYn = board.useYn;
            this.registerId = board.getRegisterId();
            this.registerTime = board.toStringDateTime(board.getRegisterTime());
            this.modifyId = board.getModifyId();
            this.modifyTime = board.toStringDateTime(board.getModifyTime());
        }
    }
}
