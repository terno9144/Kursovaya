package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Запускаем игру...");
        JFrame window = new JFrame("TictacToe");//Главное окно
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Добавляет кнопку Х для закрытия окна
        window.setSize(402,425);//размер окна
        window.setLayout(new BorderLayout());//менеджер компановки
        window.setLocationRelativeTo(null);//Открывает окно по центру экрана
        window.setVisible(true);//Видимость окна
        TicTacToe game = new TicTacToe();//Создаем объект нашего класса
        window.add(game);
        System.out.println("Конец...");
    }
}
