package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @Test
    public void cellsAreAllUnclicked() {
        board = new Board(8, 10);
        int clickedCells = 0;

        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if (board.cellIsClicked(cellNum)){
                    clickedCells++;
            }
        }
        assertEquals(0, clickedCells);
    }

    @Test
    public void numberOfPlacedMinesIsEqualToVariable() {
        board = new Board(8, 10);
        int minesOnBoard = 0;

        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if (board.cellIsAMine(cellNum)){
                    minesOnBoard++;
            }
        }
        assertEquals(board.getNumberOfMines(), minesOnBoard);
    }

    @Test
    public void flaggingCellMakesItFlagged() {
        board = new Board(8, 10);

        board.setCellToFlagged(44);
        assertTrue(board.cellIsFlagged(44));
    }

    @Test
    public void unflaggingFlaggedCellMakesItUnclicked(){
        board = new Board(8, 10);

        board.setCellToFlagged(33);
        board.setFlaggedCellToUnflagged(33);
        assertFalse(board.cellIsFlagged(33));
    }

    @Test
    public void cellCoordinatesGivesCorrectCell(){
        board = new Board(8, 10);

        assertArrayEquals(new int[]{0, 0}, (board.getCellCoordinates(0)));
        assertArrayEquals(new int[]{4, 5}, (board.getCellCoordinates(37)));
        assertArrayEquals(new int[]{7, 7}, (board.getCellCoordinates(63)));
    }

    @Test
    public void getCellByCoordinatesCorrect(){
        board = new Board(8, 10);

        assertEquals(0, board.getCellByCoordinates(0,0));
        assertEquals(63, board.getCellByCoordinates(7,7));
        assertEquals(42, board.getCellByCoordinates(5, 2));
    }

    @Test
    public void getNeighboringCellsCorrect(){
        board = new Board(8, 10);

        assertArrayEquals(new Integer[]{1, 8, 9}, board.getNeighboringCells(0).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{54, 55, 62}, board.getNeighboringCells(63).toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{27, 28, 29, 35, 37, 43, 44, 45}, board.getNeighboringCells(36).toArray(new Integer[0]));
    }
}
