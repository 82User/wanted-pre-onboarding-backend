package wanted.preonboarding.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wanted.preonboarding.entity.Board;
import wanted.preonboarding.entity.User;
import wanted.preonboarding.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Board createBoard(Board board) {

        return boardRepository.save(board);
    }

    @Override
    public List<Board> getBoardList() {

        return boardRepository.findAll();
    }

    public Page<Board> getBoardListPage(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

    @Override
    public Board findOneBoard(Long id) {
        Optional<Board> findBoard = boardRepository.findById(id);
        if(findBoard.isPresent()){
            return findBoard.get();
        } else {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
    }

    @Override
    public Board editBoard(Long id, Board editBoard) {

        Board findBoard = findOneBoard(id);

        if(editBoard.getUser().getId() != findBoard.getUser().getId()){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        } else if (findBoard == null){
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        Board editedBoard = Board.builder()
                .id(findBoard.getId())
                .title(editBoard.getTitle())
                .content(editBoard.getTitle())
                .createDate(findBoard.getCreateDate())
                .updateDate(LocalDateTime.now())
                .user(editBoard.getUser())
                .build();

        Board result = boardRepository.save(editedBoard);


        return result;
    }

    @Override
    public Board deleteBoard(Long id, User user) {

        Board findBoard = findOneBoard(id);

        if(findBoard.getUser().getId() != user.getId()){
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        } else if (findBoard == null){
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        boardRepository.delete(findBoard);

        return findBoard;
    }
}
