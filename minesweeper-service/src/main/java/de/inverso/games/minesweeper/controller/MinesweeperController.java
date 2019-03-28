package de.inverso.games.minesweeper.controller;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Player;
import de.inverso.games.minesweeper.modelObjects.Coordinates;
import de.inverso.games.minesweeper.modelObjects.Response;
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

    private Player player;
    private Board board;

    @ResponseBody
    @PostMapping(value = "/start", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Board> startMinesweeper(@RequestBody @Valid Board board) {
        this.board = board;
        board.initialize();
        this.player = new Player(board);
        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(method = POST, value = "/click", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> clickAndSendResult(@RequestBody @Valid Coordinates coordinates) {
        boolean requestOkay = tryToClick(coordinates.getCellNum());
        Response response;
        List<Integer> clickedCells;

        if(requestOkay) {
            clickedCells = player.clickOnCell(coordinates.getCellNum());
            response = new Response(player, board, clickedCells, requestOkay);
        } else {
            response = new Response(player, requestOkay);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = POST, value = "/flagChange", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> flagCell(@RequestBody @Valid Coordinates coordinates) {
        boolean requestOkay = tryToFlag(coordinates.getCellNum());
        Response response;

        if(requestOkay) {
            player.flagChange(coordinates.getCellNum());
            response = new Response(player, requestOkay);
        } else {
            response = new Response(player, requestOkay);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean tryToClick(int cellNum) {
        try {
            board.checkCellRange(cellNum);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean tryToFlag(int cellNum) {
        try {
            board.checkCellRange(cellNum);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

}
