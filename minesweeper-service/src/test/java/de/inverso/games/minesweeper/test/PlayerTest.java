package de.inverso.games.minesweeper.test;

import de.inverso.games.minesweeper.modelObjects.Board;
import de.inverso.games.minesweeper.modelObjects.Player;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Board board;
    private Player player;

    @Test
    public void playerIsAlive(){
        board = new Board(7, 10);
        player = new Player(board);


        assertTrue(player.isAlive());
    }

    @Test
    public void playerIsNotWinner(){
        board = new Board(7, 10);
        player = new Player(board);

        assertFalse(player.isWinner());
    }

    @Test
    public void cellClickMakesCellClickedOrKillsPlayer() {
        board = new Board(7, 10);
        player = new Player(board);

        player.clickOnCell(2);

        if (board.cellIsAMine(2)) {
            assertFalse(player.isAlive());
        } else {
            assertTrue(board.cellIsClicked(2));
        }
    }

    @Test
    public void playerWinsIfMinesAreFlaggedAndCellsClicked(){
        board = new Board(7, 10);
        player = new Player(board);

        for(int cellNum=0; cellNum < board.getSize(); cellNum++){
            if(board.cellIsAMine(cellNum)){
                player.flagChange(cellNum);
            } else {
                player.clickOnCell(cellNum);
                }
        }
        assertTrue(player.isWinner());
    }
}
