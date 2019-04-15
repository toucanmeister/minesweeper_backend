package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.services.BoardService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {

    private Board board;
    private BoardService boardService;

    @Test
    void playerIsAlive(){
        board = new Board(7, 10);

        assertTrue(board.isPlayerAlive());
    }

    @Test
    void playerIsNotWinner(){
        board = new Board(7, 10);

        assertFalse(board.isPlayerWinner());
    }

    @Test
    void cellClickMakesCellClickedOrKillsPlayer() {
        board = new Board(7, 10);
        boardService = new BoardService();

        boardService.clickOnCell(board, board.getCellByNum(2));

        if (board.cellIsAMine(board.getCellByNum(2))) {
            assertFalse(board.isPlayerAlive());
        } else {
            assertTrue(board.cellIsClicked(board.getCellByNum(2)));
        }
    }

    @Test
    void playerWinsIfMinesAreFlaggedAndCellsClicked(){
        board = new Board(7, 10);
        boardService = new BoardService();

        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if(board.cellIsAMine(board.getCellByNum(cellNum))){
                boardService.flagChange(board, board.getCellByNum(cellNum));
            } else {
                boardService.clickOnCell(board, board.getCellByNum(cellNum));
            }
        }
        assertTrue(board.isPlayerWinner());
    }
}
