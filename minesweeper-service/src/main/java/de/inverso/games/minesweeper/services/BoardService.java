package de.inverso.games.minesweeper.services;

import java.util.ArrayList;
import java.util.List;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.RecursiveClicker;
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
            RecursiveClicker Clicker = new RecursiveClicker(board);
            List<Integer> clickedCells = new ArrayList<>(Clicker.recursiveClick(cellNum));
            checkIfPlayerWins(board);
            return clickedCells;
        }
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
