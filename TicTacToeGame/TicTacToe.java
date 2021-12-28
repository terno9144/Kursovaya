package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
// Extends говорит о том, что наш класс наследует класс JComponent
public class TicTacToe extends JComponent{
    public static final int FIELD_EMPTY = 0;// Пустое поле
    public static final int FIELD_X = 10;// Поле с крестиком
    public static final int FIELD_O = 200;// Поле с ноликом
    int[][]field;//Обяъвление массива игрового поля
    boolean isXturn;

    public TicTacToe(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);//Получение событий от мышки
        field = new int[3][3];//выделяет память под массив при создании компонента
        initGame();
    }
    public void initGame(){
        for (int i = 0; i<3; ++i){
            for(int j = 0; j<3; ++j){
                field[i][j] = FIELD_EMPTY;// Очищает массив, заполняя его 0
            }
        }
        isXturn = true;
    }
    @Override
    protected void processMouseEvent(MouseEvent mouseEvent){
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1){ //Проверяем, что нажата левая клавиша мыши
            int x = mouseEvent.getX();//Координата х клика
            int y = mouseEvent.getY();//Координата y клика
            //Переводим координаты в индексы ячейки в массиве field
            int i = (int) ((float)x/getWidth()*3);
            int j = (int) ((float)y/getHeight()*3);
            // Проверяем, что ячейка пуста
            if (field[i][j] == FIELD_EMPTY){
                //Провеяем чей сейчас ход, если Х - ставим крестик если О - ставим нолик
                field[i][j] = isXturn?FIELD_X:FIELD_O;
                isXturn = !isXturn;// Меняем флаг хода
                repaint();// Перерисовка компонента, вызов метода paintComponent()
                int res = checkState();
                if(res!=0){
                    // Победа Ноликов!
                    if(res == FIELD_O * 3){
                        JOptionPane.showMessageDialog(this, "Нолики выиграли!","Победа!", JOptionPane.INFORMATION_MESSAGE);
                        // Победа крестиков!
                    }else if(res == FIELD_X * 3){
                        JOptionPane.showMessageDialog(this, "Крестики выиграли!", "Победа!",JOptionPane.INFORMATION_MESSAGE);
                        // Ничья:(
                    }else{
                        JOptionPane.showMessageDialog(this, "Ничья!","Ничья!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    // Перезапуск игры
                    initGame();
                    //Перерисовка поля
                    repaint();
                }
            }
        }
    }
    void drawX(int i, int j, Graphics graphics){
        graphics.setColor(Color.RED);
        int dw = getWidth()/3;
        int dh = getHeight()/3;
        int x = i * dw;
        int y = j * dh;
        // Линия от верхнего левого угла в правый нижний
        graphics.drawLine(x,y,x+dw,y+dh);
        // Линия от левого нижнего угла  в правый верхний
        graphics.drawLine(x,y+dh,x+dw,y);
    }
    void drawO(int i, int j, Graphics graphics){
        graphics.setColor(Color.BLUE);
        int dw = getWidth()/3;
        int dh = getHeight()/3;
        int x = i * dw;
        int y = j * dh;
        // Рисуем овал, который не касается боковых стенок
        graphics.drawOval(x+5*dw/100, y, dw*9/10, dh);
    }
    void drawXO(Graphics graphics){
        for (int i = 0; i<3; ++i){
            for(int j = 0; j<3; ++j){
                //Если в данной ячейке крестик - рисуем его
                if (field[i][j] == FIELD_X){
                    drawX(i, j, graphics);
                    //А тут рисуем нолик
                }else if(field[i][j] == FIELD_O){
                    drawO(i, j, graphics);
                }
            }
        }
    }
    int checkState(){
        //Проверяем диагонали
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i<3;i++){
            //Сумма значений по диагонали от левого угла
            diag += field[i][i];
            //Сумма значений по диагонали от правого угла
            diag2 += field[i][2-i];
        }
        //Если по диагонали стоят одни крестики или одни нолики выходим из метода
        if(diag == FIELD_O*3||diag == FIELD_X * 3){return diag;}
        //Для другой диагонали
        if (diag2 == FIELD_O*3 || diag2 == FIELD_X * 3){return diag2;}
        int check_i, check_j;
        boolean hasEmpty = false;
        // Проверка всех рядов
        for (int i=0; i<3; i++){
            check_i = 0;
            check_j = 0;
            for(int j=0; j<3; j++){
                // Суммируем знаки в текущем ряду
                if(field[i][j] == 0){
                    hasEmpty = true;
                }
                check_i += field[i][j];
                check_j += field[j][i];
            }
            //Если выигрыш крестика или нолика то выходим
            if(check_i == FIELD_O * 3 || check_i == FIELD_X * 3){
                return check_i;
            }
            if(check_j == FIELD_O * 3 || check_j == FIELD_X * 3){
                return check_j;
            }
        }
        if(hasEmpty) return 0; else return -1;
    }
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        //Очистка холста
        graphics.clearRect(0,0,getWidth(),getHeight());
        //Сетка
        drawGrid(graphics);
        //Рисуем крестики нолики
        drawXO(graphics);
    }
    void drawGrid(Graphics graphics){
        int w = getWidth();//Ширина поля
        int h = getWidth();//Высота поля
        int dw = w/3;//Делим ширину на 3 и получаем ширину одной ячейки
        int dh = h/3;//Делим высоту на 3 и получаем высоту одной ячейки
        graphics.setColor(Color.BLACK);//Цвет сетки
        for(int i = 1; i<3; i++){//Данный цикл нарисует на две горизонтальные линии и вертикальные
            graphics.drawLine(0,dh*i,w,dh*i);
            graphics.drawLine(dw*i,0,dw*i,h);
        }
    }
}
