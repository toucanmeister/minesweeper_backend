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

    /**
     * Initialisiert das gesamte Board.
     *
     * @param rowSize Anzahl der Zellen pro Seite
     * @param numOfMines Anzahl der Minen
     */
    public Board(int rowSize, int numOfMines) {
        numberOfRows = rowSize;
        this.numberOfMines = numOfMines;
        initialize();
    }

    public Board() {
    }

    /**
     * Erstellt und initialisiert Cells, platziert Minen.
     */
    public void initialize() {

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

        for (int cellNum = 0; cellNum < getSize(); cellNum++) {
            cells[cellNum] = new Cell(cellNum, createCellCoordinates(cellNum));
        }
        for (Cell cell :cells) {
            cell.addNeighbors(createNeighboringCellsList(cell));
        }
    }

    private void placeMinesRandomly() {

        boolean cellWithoutMineFound;
        for (int i = 0; i < numberOfMines; i++) {
            cellWithoutMineFound = false;
            while (!cellWithoutMineFound) {
                int chosenCell = ThreadLocalRandom.current().nextInt(0, getSize());
                if (!(cellIsAMine(getCellByNum(chosenCell)))) {
                    cells[chosenCell].setMine(true);
                    cellWithoutMineFound = true;
                }
            }
        }
    }

    int getNumOfNeighboringMines(Cell cell) {

        int mineCounter = 0;
        List<Cell> neighbors = getNeighboringCells(cell);

        for (Cell neighbor: neighbors) {
            if (neighbor.isMine()) {
                mineCounter++;
            }
        }

        return mineCounter;
    }

    private List<Cell> createNeighboringCellsList(Cell cell) {

        int[] originCoordinates = cell.getCellCoordinates();
        int originRow = originCoordinates[0];
        int originColumn = originCoordinates[1];
        List<Cell> neighbors = new ArrayList<>();

        // go through all adjacent cells (includes cells that aren't even on the board)
        for (int row = originRow - 1; row <= originRow + 1; row++) {
            for (int column = originColumn - 1; column <= originColumn + 1; column++) {

                // ignore self
                if (row == originRow && column == originColumn) {
                    continue;
                }

                if (coordinatesAreOnBoard(row, column)) {
                    neighbors.add(getCellByCoordinates(row, column));
                }
            }
        }
        return neighbors;
    }

    public List<Cell> getNeighboringCells(Cell cell) {
        return cell.getNeighbors();
    }

    private boolean coordinatesAreOnBoard(int row, int column) {

        if (row < 0 || row > numberOfRows - 1) {
            return false;
        } else {
            return column >= 0 && column <= numberOfRows - 1;
        }
    }


    private int[] createCellCoordinates(int cellNum) throws IndexOutOfBoundsException {

        int counter = 0;
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                if (counter == cellNum) {
                    return new int[]{row, column};
                }
                counter++;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Gibt Koordinaten der Zelle als int[2] zurück.
     * @param cellNum Zelle
     * @return int[2] = {Zeile, Spalte}
     */
    public int[] getCellCoordinates(int cellNum) {
        return cells[cellNum].getCellCoordinates();
    }

    /**
     * Gibt Zelle anhand von xy-Koordinaten zurück.
     * @param row Zeile
     * @param column Spalte
     * @return Gefundene Zelle
     * @throws IndexOutOfBoundsException Wenn nichts gefunden wurde
     */
    public Cell getCellByCoordinates(int row, int column) throws IndexOutOfBoundsException {

        for (Cell cell : cells) {
            if (Arrays.equals(cell.getCellCoordinates(), new int[]{row, column})) {
                return cell;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Fragt ab, ob alle Minen geflaggt sind.
     * @return true = ja, false = nein.
     */
    public boolean allMinesAreFlagged() {

        int flaggedMines = 0;
        for (Cell cell: cells) {
            if (cell.isFlagged() && cell.isMine()) {
                flaggedMines++;
            }
        }
        return flaggedMines == numberOfMines;
    }

    /**
     * Fragt ab, ob alle Zellen geklickt sind.
     * @return true = ja, false = nein.
     */
    public boolean allCellsAreClicked() {

        int clickedCells = 0;
        for (Cell cell: cells) {
            if (cell.isClicked()) {
                clickedCells++;
            }
        }
        return clickedCells == size - numberOfMines;
    }

    /**
     * Gibt Nummern alle je geklickten Zellen zurück.
     * @return Liste mit Nummern der Zellen.
     */
    public List<Integer> getClickedCells() {
        List<Integer> clickedCells = new ArrayList<>();
        for (Cell cell: cells) {
            if (cell.isClicked()) {
                clickedCells.add(cell.getCellNum());
            }
        }
        return clickedCells;
    }

    void setCellToClicked(Cell cell) {
        cell.setClicked();
    }

    public void setCellToFlagged(Cell cell) {
        cell.setFlagged();
    }

    public void setFlaggedCellToUnflagged(Cell cell) {
        cell.setUnflagged();
    }

    public boolean cellIsClicked(Cell cell) {
        return cell.isClicked();
    }

    public boolean cellIsAMine(Cell cell) {
        return cell.isMine();
    }

    public boolean cellIsFlagged(Cell cell) {
        return cell.isFlagged();
    }

    public int getSize() {
        return this.size;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    /**
     * Sucht nach Zelle mit bestimmter Nummer und gibt diese zurück.
     * @param cellNum Nummer der gesuchten Zelle.
     * @return Gefundenes Zellenobjekt.
     */
    public Cell getCellByNum(int cellNum) {
        for (Cell cell : cells) {
            if (cell.getCellNum() == cellNum) {
                return cell;
            }
        }

        throw new IndexOutOfBoundsException("No Cell found with cellnum " + cellNum);
    }
}
