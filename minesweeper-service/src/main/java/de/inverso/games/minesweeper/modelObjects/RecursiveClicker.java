package de.inverso.games.minesweeper.modelObjects;

import java.util.ArrayList;
import java.util.List;

public class RecursiveClicker {

    private Board board;

    public RecursiveClicker(Board board) {
        this.board = board;
    }

    public List<Integer> recursiveClick(int cellNum) {

        List<Integer> clickedCells = new ArrayList<>();
        if( !(board.cellIsClicked(cellNum))) {
            board.setCellToClicked(cellNum);
            clickedCells.add(cellNum);

            if (board.getNumOfNeighboringMines(cellNum) == 0) {
                clickedCells.addAll(repeatForAllNeighbors(cellNum));
            }
        }
        return clickedCells;
    }

    private List<Integer> repeatForAllNeighbors(int cellNum) {

        List<Integer> neighbors = board.getNeighboringCells(cellNum);
        List<Integer> clickedCells = new ArrayList<>();

        for(int neighbor: neighbors){
            clickedCells.addAll(recursiveClick(neighbor));
        }
        return clickedCells;
    }
}
