package de.inverso.games.minesweeper.controller;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.chosenCellNum;
import de.inverso.games.minesweeper.modelObjects.Response;
import de.inverso.games.minesweeper.services.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Endpoints für Minesweeper-Service.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/minesweeper-service")
public class MinesweeperController {

    private BoardService boardService;
    private Board board;

    public MinesweeperController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * Nimmt Board entgegen, initialisiert es, gibt Erfolg zurück.
     * @param board Enthält nach Serialisierung nur numberOfRows und numberOfMines.
     * @return Gibt nur zurück, dass es erfolgreich ausgeführt wurde.
     */
    @ResponseBody
    @PostMapping(value = "/start", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> startMinesweeper(@RequestBody @Valid Board board) {
        this.board = board;
        board.initialize();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Nimmt geklickte Zelle entgegen, führt Klick aus und berichtet über den Klick, Sieg/Niederlage.
     * @param chosenCellNum Nummer der geklickten Zelle.
     * @return Gibt Response mit Anzeigeinformationen zurück, siehe Response.
     */
    @ResponseBody
    @PostMapping(value = "/click", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> clickAndSendResult(@RequestBody @Valid chosenCellNum chosenCellNum) {
        Response response;
        List<Integer> clickedCells;

        clickedCells = boardService.clickOnCell(board, board.getCellByNum(chosenCellNum.getCellNum()));
        response = new Response(board, clickedCells, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Nimmt geflagte Zelle entegen, führt Flag aus und berichtet über möglichen Sieg.
     * @param chosenCellNum Nummer der geflagten Zelle.
     * @return Response mit Info: Gewonnen (Ja/Nein).
     */
    @ResponseBody
    @PostMapping(value = "/flagChange", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> flagCell(@RequestBody @Valid chosenCellNum chosenCellNum) {
        Response response;

        boardService.flagChange(board, board.getCellByNum(chosenCellNum.getCellNum()));
        response = new Response(board, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
