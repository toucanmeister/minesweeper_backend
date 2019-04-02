package de.inverso.games.minesweeper.services;

import java.util.ArrayList;
import java.util.List;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Cell;
import de.inverso.games.minesweeper.modelObjects.RecursiveClicker;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    public BoardService(){
    }

    /**
     * Klickt auf die gewählte Zelle und führt rekursiven Klick aus.
     * @param board Board, auf dem gespielt wird.
     * @param cell Zelle, die geklickt werden soll.
     * @return Alle geklickten Zellen.
     */
    public List<Integer> clickOnCell(Board board, Cell cell) {

        if (!board.cellIsFlagged(cell)) {
            return clickAndCheckWin(board, cell);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Integer> clickAndCheckWin(Board board, Cell cell) {

        if (board.cellIsAMine(cell)) {
            board.setPlayerAlive(false);
            return new ArrayList<>();
        } else {
            RecursiveClicker clicker = new RecursiveClicker(board);
            clicker.recursiveClick(cell);
            checkIfPlayerWins(board);
            return board.getClickedCells();
        }
    }

    /**
     * Flagt die gewählte Zelle.
     * @param board Board, auf dem gespielt wird.
     * @param cell Zelle, die geflagt werden soll.
     */
    public void flagChange(Board board, Cell cell) {

        if (board.cellIsFlagged(cell)) {
            board.setFlaggedCellToUnflagged(cell);
            checkIfPlayerWins(board);
        } else {
            board.setCellToFlagged(cell);
            checkIfPlayerWins(board);
        }
    }

    private void checkIfPlayerWins(Board board) {

        if (board.allMinesAreFlagged() && board.allCellsAreClicked()) {
            board.setPlayerWinner(true);
        }
    }


}
