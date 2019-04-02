package de.inverso.games.minesweeper.modelObjects;

import java.util.List;

public class RecursiveClicker {

    private Board board;

    public RecursiveClicker(Board board) {
        this.board = board;
    }

    /**
     * Klickt auf alle Zellen um den Ursprung herum, die keine
     * Minen um sich herum haben.
     * @param cell Urspungszelle
     */
    public void recursiveClick(Cell cell) {

        if (!(board.cellIsClicked(cell))) {
            board.setCellToClicked(cell);

            if (board.getNumOfNeighboringMines(cell) == 0) {

                List<Cell> neighbors = board.getNeighboringCells(cell);

                for (Cell neighbor: neighbors) {
                    recursiveClick(neighbor);
                }
            }
        }
    }
}
