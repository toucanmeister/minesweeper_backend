package de.inverso.games.minesweeper.controller;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Player;

import java.util.List;

class Response {

    private boolean alive;
    private boolean winner;
    private int[] numOfNeighborMines;
    private int[] clickedCells;
    private int[] mines;
    private boolean okay;

    public Response(Player player, Board board, List<Integer> clickedCells, boolean okay) {
        setClickedCells(clickedCells);
        setNumOfNeighborMines(board);
        setAlive(player.isAlive());
        setWinner(player.isWinner());
        setStatus(okay);

        if(!player.isAlive()){
            setMinesFromBoard(board);
        }
    }

    public Response(Player player, boolean status) {
        setWinner(player.isWinner());
        setAlive(player.isAlive());
        setStatus(status);
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

    public int[] getMines(){
        return mines;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isWinner() {
        return winner;
    }

    public int[] getClickedCells() {
        return clickedCells;
    }

    public int[] getNumOfNeighborMines() {
        return numOfNeighborMines;
    }

    public void setStatus(boolean okay) {
        this.okay = okay;
    }

    public boolean isOkay(){
        return this.okay;
    }
}
