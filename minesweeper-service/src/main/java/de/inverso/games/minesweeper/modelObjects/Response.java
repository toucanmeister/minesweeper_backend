package de.inverso.games.minesweeper.modelObjects;

import lombok.Data;
import java.util.List;

@Data
public class Response {

    private boolean alive;
    private boolean winner;
    private int[][] clickedCellsWithMineCount;
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

        for(int i = 0; i < clickedCellsWithMineCount[1].length; i++){
            clickedCellsWithMineCount[1][i] = board.getNumOfNeighboringMines(board.getCellByNum(clickedCellsWithMineCount[0][i]));
        }
    }

    private void setClickedCells(List<Integer> clickedCells) {
        this.clickedCellsWithMineCount = new int[2][clickedCells.size()];
        for(int i=0; i < clickedCells.size(); i++){
            this.clickedCellsWithMineCount[0][i]=clickedCells.get(i);
        }
    }

    private void setMinesFromBoard(Board board){
        mines = new int[board.getNumberOfMines()];
        int mineNumber = 0;
        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if(board.cellIsAMine(board.getCellByNum(cellNum))){
                mines[mineNumber] = cellNum;
                mineNumber++;
            }
        }
    }
}
