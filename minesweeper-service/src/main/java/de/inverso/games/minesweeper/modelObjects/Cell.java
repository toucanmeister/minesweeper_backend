package de.inverso.games.minesweeper.modelObjects;

import de.inverso.games.minesweeper.enums.CellStatus;
import lombok.Data;

import java.util.List;

@Data
public class Cell {
    private CellStatus status;
    private boolean isMine;
    private int cellNum;
    private int[] cellCoordinates;
    private List<Cell> neighbors;

     Cell(int cellNum, int[] cellCoordinates) {
        this.cellNum = cellNum;
        this.cellCoordinates = cellCoordinates;
        status = CellStatus.UNCLICKED;
        isMine = false;
    }

    void addNeighbors(List<Cell> neighbors){
         this.neighbors = neighbors;
    }

    boolean isClicked() {
        return status == CellStatus.CLICKED;
    }

    boolean isFlagged() {
         return status == CellStatus.FLAGGED;
     }

    void setUnflagged() {
         this.status = CellStatus.UNCLICKED;
    }

    void setClicked() {
        this.status = CellStatus.CLICKED;
    }

    void setFlagged() {
        this.status = CellStatus.FLAGGED;
    }
}
