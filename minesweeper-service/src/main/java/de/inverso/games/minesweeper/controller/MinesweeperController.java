package de.inverso.games.minesweeper.controller;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Endpoints für Minesweeper-Service.
 */
@RestController
@RequestMapping("/minesweeper-service")
public class MinesweeperController {

    private static class Coordinates{
        private int cellNum;

        public void setCellNum(int cellNum) {
            this.cellNum = cellNum;
        }

        int getCellNum() {
            return cellNum;
        }
    }

    private static class BoardData {
        private int rowSize;
        private int numOfMines;

        public int getRowSize() {
            return rowSize;
        }
        public int getNumOfMines() {
            return numOfMines;
        }
    }

    private Game minesweeper;


    @ResponseBody
    @RequestMapping(method = POST, value = "/start", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> startMinesweeper(@RequestBody BoardData boardData) {
        minesweeper = new Game(boardData.getRowSize(), boardData.getNumOfMines());
        return new ResponseEntity<>(minesweeper.board, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(method = POST, value = "/click", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> clickAndSendResult(@RequestBody Coordinates coordinates) {
        HttpStatus status = tryToClick(coordinates.getCellNum());
        Response response;
        List<Integer> clickedCells;

        if(status == HttpStatus.OK) {
            clickedCells = minesweeper.player.clickOnCell(coordinates.getCellNum());
            response = new Response(minesweeper.player, minesweeper.board, clickedCells);
        } else {
            response = new Response(minesweeper.player);
        }
        return new ResponseEntity<>(response, status);
    }

    @ResponseBody
    @RequestMapping(method = POST, value = "/flagChange", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> flagCell(@RequestBody Coordinates coordinates) {
        HttpStatus status = tryToFlag(coordinates.getCellNum());
        Response response;

        if(status == HttpStatus.OK) {
            minesweeper.player.flagChange(coordinates.getCellNum());
            response = new Response(minesweeper.player);
        } else {
            response = new Response(minesweeper.player);
        }
        return new ResponseEntity<>(response, status);
    }

    private HttpStatus tryToClick(int cellNum) {
        try {
            minesweeper.board.checkCellRange(cellNum);
            return HttpStatus.OK;
        } catch (IndexOutOfBoundsException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private HttpStatus tryToFlag(int cellNum) {
        try {
            minesweeper.board.checkCellRange(cellNum);
            return HttpStatus.OK;
        } catch (IndexOutOfBoundsException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
