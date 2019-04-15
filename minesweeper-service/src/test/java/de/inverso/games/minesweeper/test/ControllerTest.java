package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.services.BoardService;
import de.inverso.games.minesweeper.controller.MinesweeperController;
import de.inverso.games.minesweeper.modelObjects.ChosenCellNum;
import de.inverso.games.minesweeper.modelObjects.Response;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


class ControllerTest {

    @Test
    void startShouldInitializeBoardAndPlayer() {
        Board board = new Board();
        board.setNumberOfRows(8);
        board.setNumberOfMines(10);

        MinesweeperController controller = new MinesweeperController(new BoardService());

        controller.startMinesweeper(board);

        Class c = controller.getClass();

        try {
            c.getDeclaredField("board");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        try {
            c.getDeclaredField("boardService");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void clickingResponseShouldBeOkay() {
        Board board = new Board();
        board.setNumberOfRows(8);
        board.setNumberOfMines(10);

        MinesweeperController controller = new MinesweeperController(new BoardService());
        controller.startMinesweeper(board);

        ChosenCellNum targetCell = new ChosenCellNum();
        targetCell.setCellNum(5);
        ResponseEntity<Response> responseEntity = controller.clickAndSendResult(targetCell);
        Response response = responseEntity.getBody();

        assertFalse(response.isWinner());
        response.isAlive();
        response.getMines();
        response.getClickedCellsWithMineCount();
        response.isOkay();
    }
}
