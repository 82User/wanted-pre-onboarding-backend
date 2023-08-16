package wanted.preonboarding.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.preonboarding.entity.Board;
import wanted.preonboarding.entity.User;
import wanted.preonboarding.service.BoardServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardServiceImpl boardService;

    ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/test")
    public String test(){
        return "test OK";
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board){

        Board createdBoard = boardService.createBoard(board);

        return new ResponseEntity<>(createdBoard, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping()
    public HttpEntity<Page<Board>> findAllBoardList(Pageable pageable){

        Page<Board> boardList = boardService.getBoardListPage(pageable);

        return new HttpEntity<>(boardList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> findOneBoard(@PathVariable Long id){
        Board oneBoard = boardService.findOneBoard(id);

        return ResponseEntity.ok(oneBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> editBoard(@PathVariable Long id,
                                           HttpEntity<Board> board){

        Board editBoard = board.getBody();

        Board editedBoard = boardService.editBoard(id, editBoard);

        return new ResponseEntity<>(editedBoard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long id,
                                             @RequestBody User user) throws JsonProcessingException {


        Board deletedBoard = boardService.deleteBoard(id, user);

        return new ResponseEntity<>(deletedBoard, HttpStatus.OK);
    }
}
