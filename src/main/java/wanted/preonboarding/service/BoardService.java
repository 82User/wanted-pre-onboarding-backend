package wanted.preonboarding.service;

import wanted.preonboarding.entity.Board;
import wanted.preonboarding.entity.User;

import java.util.List;

public interface BoardService {

    // 1. 새로운 게시글 생성
    Board createBoard(Board board);

    // 2. 게시글 목록을 조회
    List<Board> getBoardList();

    // 3. 특정 게시글을 조회
    Board findOneBoard(Long id);

    // 4. 특정 게시글을 수정
    Board editBoard(Long id, Board editBoard);

    // 5. 특정 게시글을 삭제
    Board deleteBoard(Long id, User user);
}
