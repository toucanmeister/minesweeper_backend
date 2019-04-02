package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.services.BoardService;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

@Ignore
public class BoardServiceTest {

    private Board board;
    private BoardService boardService;

    @Test
    public void playerIsAlive(){
        board = new Board(7, 10);

        assertTrue(board.isPlayerAlive());
    }

    @Test
    public void playerIsNotWinner(){
        board = new Board(7, 10);

        assertFalse(board.isPlayerWinner());
    }

    @Test
    public void cellClickMakesCellClickedOrKillsPlayer() {
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
    public void playerWinsIfMinesAreFlaggedAndCellsClicked(){
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
