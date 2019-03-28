package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.controller.MinesweeperController;
import de.inverso.games.minesweeper.modelObjects.Coordinates;
import de.inverso.games.minesweeper.modelObjects.Response;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;

public class ControllerTest {

    @Test
    public void startShouldInitializeBoardAndPlayer() {
        Board board = new Board();
        board.setNumberOfRows(8);
        board.setNumberOfMines(10);

        MinesweeperController controller = new MinesweeperController();

        controller.startMinesweeper(board);

        Class c = controller.getClass();

        try {
            c.getDeclaredField("board");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        try {
            c.getDeclaredField("player");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void clickingResponseShouldBeOkay() {
        Board board = new Board();
        board.setNumberOfRows(8);
        board.setNumberOfMines(10);

        MinesweeperController controller = new MinesweeperController();
        controller.startMinesweeper(board);

        Coordinates targetCell = new Coordinates();
        targetCell.setCellNum(5);
        ResponseEntity<Response> responseEntity = controller.clickAndSendResult(targetCell);
        Response response = responseEntity.getBody();

        assertFalse(response.isWinner());
        response.isAlive();
        response.getMines();
        response.getClickedCells();
        response.getNumOfNeighborMines();
        response.isOkay();
    }

    @Test
    public void badCoordinatesShouldSendBadRequest() {
        Board board = new Board();
        board.setNumberOfRows(8);
        board.setNumberOfMines(10);

        MinesweeperController controller = new MinesweeperController();
        controller.startMinesweeper(board);

        Coordinates targetCell = new Coordinates();
        targetCell.setCellNum(-1);
        ResponseEntity<Response> responseEntity = controller.clickAndSendResult(targetCell);
        Response response = responseEntity.getBody();

        assertFalse(response.isOkay());
    }
}
