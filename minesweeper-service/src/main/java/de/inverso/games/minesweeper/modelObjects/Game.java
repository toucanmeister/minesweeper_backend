package de.inverso.games.minesweeper.modelObjects;

public class Game {

    public Player player;
    public Board board;

    public Game(int rowSize, int numOfMines) {
        board = new Board(rowSize, numOfMines);
        player = new Player(board);
    }
}