package de.inverso.games.minesweeper.modelObjects;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Board {

    private boolean playerAlive;
    private boolean playerWinner;
    private int size;
    private int numberOfRows;
    private int numberOfMines;
    private Cell[] cells;

    public Board(int rowSize, int numOfMines) {
        numberOfRows = rowSize;
        this.numberOfMines = numOfMines;
        initialize();
    }

    public Board() {
    }

    public void initialize(){

        playerAlive = true;
        playerWinner = false;
        size = numberOfRows * numberOfRows;
        initializeCells();
    }


    private void initializeCells() {

        cells = new Cell[getSize()];
        instantiateCells();
        placeMinesRandomly();
    }

    private void instantiateCells() {

        for(int cellNum=0; cellNum < getSize(); cellNum++){
            cells[cellNum] = new Cell(cellNum, createCellCoordinates(cellNum));
        }
        for(int cellNum = 0; cellNum < getSize(); cellNum++) {
            cells[cellNum].addNeighbors(createNeighboringCellsList(cellNum));
        }
    }

    private void placeMinesRandomly() {

        boolean cellWithoutMineFound;
        for(int i=0; i < numberOfMines; i++) {
            cellWithoutMineFound = false;
            while(!cellWithoutMineFound) {
                int chosenCell = ThreadLocalRandom.current().nextInt(0, getSize());
                if( !(cellIsAMine(chosenCell))) {
                    cells[chosenCell].setMine(true);
                    cellWithoutMineFound = true;
                }
            }
        }
    }

    int getNumOfNeighboringMines(int cellNum) {

        int mineCounter = 0;
        List<Integer> neighbors = getNeighboringCells(cellNum);

        for(int neighbor: neighbors){
            if(cellIsAMine(neighbor)) {
                mineCounter++;
            }
        }

        return mineCounter;
    }

    private List<Integer> createNeighboringCellsList(int cellNum) {

        int[] originCoordinates = getCellCoordinates(cellNum);
        int originRow = originCoordinates[0];
        int originColumn = originCoordinates[1];
        List<Integer> neighbors = new ArrayList<>();

        for (int row = originRow - 1; row <= originRow + 1; row++) {
            for (int column = originColumn - 1; column <= originColumn + 1; column++) {

                if (!(row == originRow && column == originColumn)) {
                    if (coordinatesAreOnBoard(row, column)) {
                        neighbors.add(getCellByCoordinates(row, column));
                    }
                }
            }
        }
        return neighbors;
    }

    public List<Integer> getNeighboringCells(int cellNum) {
        return cells[cellNum].getNeighbors();
    }

    private boolean coordinatesAreOnBoard(int row, int column) {

        if(row < 0 || row > numberOfRows-1){
            return false;
        } else return column >= 0 && column <= numberOfRows - 1;
    }


    private int[] createCellCoordinates(int cellNum) throws IndexOutOfBoundsException {

        int counter = 0;
        for(int row=0; row < numberOfRows; row++){
            for(int column=0; column < numberOfRows; column++){
                if(counter == cellNum){
                    return new int[]{row, column};
                }
                counter++;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public int[] getCellCoordinates(int cellNum){
        return cells[cellNum].getCellCoordinates();
    }

    public int getCellByCoordinates(int row, int column) throws IndexOutOfBoundsException{

        for(int x=0; x < size; x++) {
            if(Arrays.equals(cells[x].getCellCoordinates(), new int[]{row, column})){
                return x;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean allMinesAreFlagged(){

        int flaggedMines = 0;
        for(Cell cell: cells){
            if(cell.isFlagged() && cell.isMine()){
                    flaggedMines++;
            }
        }
        return flaggedMines == numberOfMines;
    }

    public boolean allCellsAreClicked(){

        int clickedCells = 0;
        for(Cell cell: cells){
            if(cell.isClicked()){
                clickedCells++;
            }
        }
        return clickedCells == size - numberOfMines;
    }

    public void checkCellRange(int cellNum) throws IndexOutOfBoundsException{

        if ((cellNum < 0) || (cellNum >= getSize())){
            throw new IndexOutOfBoundsException("Cell is out of range of the Board.");
        }
    }

    public List<Integer> getClickedCells(){
        List<Integer> clickedCells = new ArrayList<>();
        for(Cell cell: cells){
            if(cell.isClicked()){
                clickedCells.add(cell.getCellNum());
            }
        }
        return clickedCells;
    }

    void setCellToClicked(int cellNum) {
        cells[cellNum].setClicked();
    }

    public void setCellToFlagged(int cellNum) {
        cells[cellNum].setFlagged();
    }

    public void setFlaggedCellToUnflagged(int cellNum) {
        cells[cellNum].setUnflagged();
    }

    public boolean cellIsClicked(int cellNum) {
        return cells[cellNum].isClicked();
    }

    public boolean cellIsAMine(int cellNum) {
        return cells[cellNum].isMine();
    }

    public boolean cellIsFlagged(int cellNum) {
        return cells[cellNum].isFlagged();
    }

    public int getSize() {
        return this.size;
    }

    public int getNumberOfMines(){
        return numberOfMines;
    }

    public void setNumberOfRows(int numberOfRows){
        this.numberOfRows = numberOfRows;
    }

    public void setNumberOfMines(int numberOfMines){
        this.numberOfMines = numberOfMines;
    }
}
