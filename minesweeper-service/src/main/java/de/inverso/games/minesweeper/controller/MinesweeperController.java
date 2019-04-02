package de.inverso.games.minesweeper.controller;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Cell;
import de.inverso.games.minesweeper.modelObjects.Coordinates;
import de.inverso.games.minesweeper.modelObjects.Response;
import de.inverso.games.minesweeper.services.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Endpoints für Minesweeper-Service.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/minesweeper-service")
public class MinesweeperController {

    private BoardService boardService;
    private Board board;

    public MinesweeperController(BoardService boardService){
        this.boardService = boardService;
    }

    @ResponseBody
    @PostMapping(value = "/start", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> startMinesweeper(@RequestBody @Valid Board board) {
        this.board = board;
        board.initialize();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping(value = "/click", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> clickAndSendResult(@RequestBody @Valid Coordinates coordinates) {
        Response response;
        List<Integer> clickedCells;

        clickedCells = boardService.clickOnCell(board, board.getCellByNum(coordinates.getCellNum()));
        response = new Response(board, clickedCells, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/flagChange", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> flagCell(@RequestBody @Valid Coordinates coordinates) {
        Response response;

        boardService.flagChange(board, board.getCellByNum(coordinates.getCellNum()));
        response = new Response(board, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
