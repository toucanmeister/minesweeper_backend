package de.inverso.games.minesweeper.services;

import java.util.ArrayList;
import java.util.List;

import de.inverso.games.minesweeper.modelObjects.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    public BoardService(){
    }

    public List<Integer> clickOnCell(Board board, int cellNum) {

        if (!board.cellIsFlagged(cellNum)) {
            return clickAndCheckWin(board, cellNum);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Integer> clickAndCheckWin(Board board, int cellNum) {

        if (board.cellIsAMine(cellNum)) {
            board.setPlayerAlive(false);
            return new ArrayList<>();
        } else {
            List<Integer> clickedCells = new ArrayList<>(recursiveClick(board, cellNum));
            checkIfPlayerWins(board);
            return clickedCells;
        }
    }

    private List<Integer> recursiveClick(Board board, int cellNum) {

        List<Integer> clickedCells = new ArrayList<>();
        if( !(board.cellIsClicked(cellNum))) {
            board.setCellToClicked(cellNum);
            clickedCells.add(cellNum);

            if (board.numOfNeighboringMines(cellNum) == 0) {
                clickedCells.addAll(repeatForAllNeighbors(board, cellNum));
            }
        }
        return clickedCells;
    }

    private List<Integer> repeatForAllNeighbors(Board board, int cellNum) {

        List<Integer> neighbors = board.getNeighboringCells(cellNum);
        List<Integer> clickedCells = new ArrayList<>();

        for(int neighbor: neighbors){
            clickedCells.addAll(recursiveClick(board, neighbor));
        }
        return clickedCells;
    }

    public void flagChange(Board board, int cellNum){

        if(board.cellIsFlagged(cellNum)) {
            board.setFlaggedCellToUnflagged(cellNum);
            checkIfPlayerWins(board);
        } else {
            board.setCellToFlagged(cellNum);
            checkIfPlayerWins(board);
        }
    }

    private void checkIfPlayerWins(Board board){

        if(board.allMinesAreFlagged() && board.allCellsAreClicked()){
            board.setPlayerWinner(true);
        }
    }
}
