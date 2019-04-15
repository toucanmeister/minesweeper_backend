package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @Test
    void sizeIsCorrect() {
        board = new Board(8, 10);
        assertEquals(board.getSize(), 64);
        int count = 0;
        assertEquals(board.getCells().length, 64);
    }

    @Test
    void cellsAreAllUnclicked() {
        board = new Board(8, 10);
        int clickedCells = 0;

        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if (board.cellIsClicked(board.getCellByNum(cellNum))){
                    clickedCells++;
            }
        }
        assertEquals(0, clickedCells);
    }

    @Test
    void numberOfPlacedMinesIsEqualToVariable() {
        board = new Board(8, 10);
        int minesOnBoard = 0;

        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if (board.cellIsAMine(board.getCellByNum(cellNum))){
                    minesOnBoard++;
            }
        }
        assertEquals(board.getNumberOfMines(), minesOnBoard);
    }

    @Test
    void flaggingCellMakesItFlagged() {
        board = new Board(8, 10);

        board.setCellToFlagged(board.getCellByNum(44));
        assertTrue(board.cellIsFlagged(board.getCellByNum(44)));
    }

    @Test
    void unflaggingFlaggedCellMakesItUnclicked(){
        board = new Board(8, 10);

        board.setCellToFlagged(board.getCellByNum(33));
        board.setFlaggedCellToUnflagged(board.getCellByNum(33));
        assertFalse(board.cellIsFlagged(board.getCellByNum(33)));
    }

    @Test
    void cellCoordinatesGivesCorrectCell(){
        board = new Board(8, 10);

        assertArrayEquals(new int[]{0, 0}, (board.getCellCoordinates(0)));
        assertArrayEquals(new int[]{4, 5}, (board.getCellCoordinates(37)));
        assertArrayEquals(new int[]{7, 7}, (board.getCellCoordinates(63)));
    }

    @Test
    void getCellByCoordinatesCorrect(){
        board = new Board(8, 10);

        assertEquals(0, board.getCellByCoordinates(0,0).getCellNum());
        assertEquals(63, board.getCellByCoordinates(7,7).getCellNum());
        assertEquals(42, board.getCellByCoordinates(5, 2).getCellNum());
    }

    @Test
    void getNeighboringCellsCorrect(){
        board = new Board(8, 10);

        assertArrayEquals(new Integer[]{1, 8, 9}, board.getNeighboringCells(board.getCellByNum(0))
                .stream()
                .map(Cell::getCellNum)
                .toArray());
        assertArrayEquals(new Integer[]{54, 55, 62}, board.getNeighboringCells(board.getCellByNum(63))
                .stream()
                .map(Cell::getCellNum)
                .toArray());
        assertArrayEquals(new Integer[]{27, 28, 29, 35, 37, 43, 44, 45}, board.getNeighboringCells(board.getCellByNum(36))
                .stream()
                .map(Cell::getCellNum)
                .toArray());
    }
}
