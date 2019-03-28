package de.inverso.games.minesweeper.modelObjects;

import lombok.Data;
import java.util.List;

@Data
public class Response {

    private boolean alive;
    private boolean winner;
    private int[] numOfNeighborMines;
    private int[] clickedCells;
    private int[] mines;
    private boolean okay;

    public Response(Board board, List<Integer> clickedCells, boolean okay) {
        setClickedCells(clickedCells);
        setNumOfNeighborMines(board);
        setAlive(board.isPlayerAlive());
        setWinner(board.isPlayerWinner());
        setOkay(okay);

        if(!board.isPlayerAlive()){
            setMinesFromBoard(board);
        }
    }

    public Response(Board board, boolean status) {
        setWinner(board.isPlayerWinner());
        setAlive(board.isPlayerAlive());
        setOkay(status);
    }

    private void setNumOfNeighborMines(Board board) {
        numOfNeighborMines = new int[clickedCells.length];
        for(int i=0; i < clickedCells.length; i++){
            numOfNeighborMines[i] = board.numOfNeighboringMines(clickedCells[i]);
        }
    }

    private void setClickedCells(List<Integer> clickedCells) {
        this.clickedCells = new int[clickedCells.size()];
        for(int i=0; i < clickedCells.size(); i++){
            this.clickedCells[i]=clickedCells.get(i);
        }
    }

    private void setMinesFromBoard(Board board){
        mines = new int[board.getNumberOfMines()];
        int mineNumber = 0;
        for(int cell=0; cell < board.getSize(); cell++){
            if(board.cellIsAMine(cell)){
                mines[mineNumber] = cell;
                mineNumber++;
            }
        }
    }
}
