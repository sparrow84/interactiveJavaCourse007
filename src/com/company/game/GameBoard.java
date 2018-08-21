package com.company.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;

    private Game game;

    static char nullSymbol = '\u0000';

    public GameBoard(Game currentGame) {
        this.game = currentGame;
        initField();
    }

    /**
     * Метод инициации и отрисовки игрового поля
     */
    private void initField() {
        // задаём основные настройки окна игры
        setBounds(cellSize * dimension, cellSize * dimension, 300, 300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // qwe
        setLayout(new BorderLayout());
//        setLayout(new GridLayout(dimension, dimension));


        JPanel controlPanel = new JPanel(); //  панель управления игрой
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension, 150);

        JPanel gameFieldPanel = new JPanel(); // Панель самой игры
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];

        // инициадизируем игравое поле
        for (int i = 0; i < (dimension * dimension); i++) {
            GameButton fieldButton = new GameButton(i, this);
            gameButtons[i] = fieldButton;
            // qwe
            gameFieldPanel.add(gameButtons[i]);
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Метод очистки поля и матрицы игры
     */
    void emptyField() {
        for (int i = 0; i< (dimension * dimension); i++) {
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = nullSymbol;
        }
    }

    Game getGame() {
        return game;
    }

    /**
     * Метод проверки доступности клетки для хода
     * @param x - по горизонтали
     * @param y - по вертикали
     * @return boolean
     */
    boolean isTurnable(int x, int y) {
        boolean result = false;

        if(gameField[y][x] == nullSymbol)
            result = true;

        return result;
    }

    /**
     * Обновление матрицы игры после хода
     * @param x - по горизонтали
     * @param y - по вертикали
     */
    void updateGameField(int x, int y) {
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    /**
     * Проверка победы
     * @return флаг победы
     */
    boolean checkWin() {
        boolean result = false;

        char plauerSumbol = getGame().getCurrentPlayer().getPlayerSign();

        if (checkWinDiagonals(plauerSumbol) || checkWinLines(plauerSumbol)) {
            result = true;
        }

        return result;
    }

    boolean isFull() {
        boolean result = true;

        A1:
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] == nullSymbol) {
                    result = false;
                    break A1;
                }
            }
        }
        return result;
    }

    /**
     * Проверка победы по столбцам и линиям
     * @param playerSymbol
     * @return флаг победы
     */
    private boolean checkWinLines(char playerSymbol) {
        boolean cols, rows, result;

        result = false;

        for (int col = 0; col< dimension; col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < dimension; row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }

            /*
            Это условие после каждой  проверкиколонки столбца
            позволяет остановить дальнейшее выполнение, без
            проверки всех остальных столбцов и сторк
            */
            if (cols || rows) {
                result = true;
                break;
            }

            if (result) break;
        }


        // qwe
        return result;
        //return false;
    }

    /**
     * Проверка выиграшных комбинаций на диагоналях
     * @param plauerSumbol
     * @return флаг победы
     */
    private boolean checkWinDiagonals(char plauerSumbol) {

        // zzz

        return false;
    }

    public GameButton getButton(int buttonIndex) {
        return gameButtons[buttonIndex];
    }
}
