package de.inverso.games.minesweeper.modelObjects;

import java.util.List;

public class RecursiveClicker {

    private Board board;

    public RecursiveClicker(Board board) {
        this.board = board;
    }

    public void recursiveClick(int cellNum) {

        if( !(board.cellIsClicked(cellNum))) {
            board.setCellToClicked(cellNum);

            if (board.getNumOfNeighboringMines(cellNum) == 0) {

                List<Integer> neighbors = board.getNeighboringCells(cellNum);

                for(int neighbor: neighbors){
                    recursiveClick(neighbor);
                }
            }
        }
    }
}
