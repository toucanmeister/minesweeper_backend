package de.inverso.games.minesweeper.modelObjects;

import lombok.Data;

@Data
class Cell {

    enum CellStatus {CLICKED, FLAGGED, UNCLICKED}
    private CellStatus status;
    private boolean isMine;
    private int cellNum;
    private int[] cellCoordinates;

     Cell(int cellNum, int[] cellCoordinates) {
        this.cellNum = cellNum;
        this.cellCoordinates = cellCoordinates;
        status = CellStatus.UNCLICKED;
        isMine = false;
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
