package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.controller.MinesweeperController;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void startShouldInitializeBoardAndPlayer(){
        Board board = new Board();
        board.setNumberOfRows(8);
        board.setNumberOfMines(10);

        MinesweeperController controller = new MinesweeperController();

        controller.startMinesweeper(board);

        Class c = controller.getClass();

        try {
            c.getDeclaredField("board");
        } catch(NoSuchFieldException e){
            throw new RuntimeException(e);
        }

        try {
            c.getDeclaredField("player");
        } catch(NoSuchFieldException e){
            throw new RuntimeException(e);
        }
    }
}
