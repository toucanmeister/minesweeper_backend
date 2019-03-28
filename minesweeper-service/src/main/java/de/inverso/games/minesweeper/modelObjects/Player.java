package de.inverso.games.minesweeper.modelObjects;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private boolean alive;
    private boolean winner;
    private Board board;

    public Player(Board board) {

        this.board = board;
        alive = true;
        winner = false;
    }

    public List<Integer> clickOnCell(int cellNum) {

        if (!board.cellIsFlagged(cellNum)) {
            return clickAndCheckWin(cellNum);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Integer> clickAndCheckWin(int cellNum) {

        if (board.cellIsAMine(cellNum)) {
            alive = false;
            return new ArrayList<>();
        } else {
            List<Integer> clickedCells = new ArrayList<>(recursiveClick(cellNum));
            checkIfPlayerWins();
            return clickedCells;
        }
    }

    private List<Integer> recursiveClick(int cellNum) {

        List<Integer> clickedCells = new ArrayList<>();
        if( !(board.cellIsClicked(cellNum))) {
            board.setCellToClicked(cellNum);
            clickedCells.add(cellNum);

            if (board.numOfNeighboringMines(cellNum) == 0) {
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

    public void flagChange(int cellNum){

        if(board.cellIsFlagged(cellNum)) {
            board.setFlaggedCellToUnflagged(cellNum);
            checkIfPlayerWins();
        } else {
            board.setCellToFlagged(cellNum);
            checkIfPlayerWins();
        }
    }

    private void checkIfPlayerWins(){

        if(board.allMinesAreFlagged() && board.allCellsAreClicked()){
            winner = true;
        }
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
        return alive;
    }
}
