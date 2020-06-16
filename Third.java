package vm_lab;

import static java.lang.Math.abs;

public class Third {

    //public static double A;//левый конец отрезка интегрирования
    public static double start;//правый конец отрезка интегрирования
    public static double finish;//начальное условие x0
    public static double Hmin;//минимальнй размер шага
    public static double Hmax;//максимальный размер шага
    public static boolean lastTime = false;
    public static double eps = 0.001;
    public static boolean isIntervalMin = false;
    public static double H = (finish - start) / 10;

    //функции для тестов
    public static double f1(double X, double Y) {  // 2 * X
    return 3 * X * X * X;
}

    public static double f2(double X, double Y) { // X * X
        return 2 * X;
    }

    public static double f3(double X, double Y) {  // 4 * X * X * X
        return 4 * 3 * X * X;
    }

    public static double f4(double X, double Y) { // 7 * X * X * X * X
        return 7 * 4 * X * X * X;
    }

    //для решения: метод Рунге-Кутте четвертого порядка
    //вычисляет приближенное значение y в точке x(n+1) = x(n) + H
    public static double fourthGrade(double X, double Y, double H) {
        double K1 = H * f1(X, Y);
        double K2 = H * f1(X + (1.0 / 2.0) * H, Y + (1.0 / 2.0) * K1);
        double K3 = H * f1(X + (1.0 / 2.0) * H, Y + (1.0 / 4.0) * K1 + (1.0 / 4.0) * K2);
        double K4 = H * f1(X + H, Y - K2 + 2 * K3);
        return Y + (1.0 / 6.0) * (K1 + 4.0 * K3 + K4);
    }

    //для уточнения решения: метод Рунге-Кутте пятого порядка
    //вычисляет более точное приближенное значение y в точке x(n+1) = x(n) + H
    static double fifthGrade(double X, double Y, double H) {
        double K1 = H * f1(X, Y);
        double K2 = H * f1(X + (1.0 / 2.0) * H, Y + (1.0 / 2.0) * K1);
        double K3 = H * f1(X + (1.0 / 2.0) * H, Y + (1.0 / 4.0) * K1 + (1.0 / 4.0) * K2);
        double K4 = H * f1(X + H, Y - K2 + 2 * K3);
        double K5 = H * f1(X + (2.0 / 3.0) * H,
                Y + (7.0 / 27.0) * K1 + (10.0 / 27.0) * K2 + (1.0 / 27.0) * K4);
        double K6 = H * f1(X + (1.0 / 5.0) * H,
                Y - (1.0 / 625.0) * (28 * K1 - 125 * K2 + 546 * K3 + 54 * K4 + 378 * K5));
        return Y + (1.0 / 336.0) * (14 * K1 + 35.0 * K4 + 162 * K5 + 125 * K6);
    }

    public static void calcRunge(double x0, double y0, double h) {

        double y1 = fourthGrade(x0, y0, h);
        //y1 = fifthGrade(x0, y0, h);

        double hn = 0;
        double he = (Hmax + Hmin)/2; //TODO: методичка страница 45

        if(he <= Hmin) hn = Hmin;
        if(he < Hmin && he <= Hmax) hn = he;
        if(he > Hmax) hn = Hmax;

        double x1 = x0 + hn;

        //проверим новую точку на близость к концу отрезка
        double endCheck = finish - x1;
        //if (endCheck < 2*Hmin) x1 = endCheck - 2 * Hmin; //если ближе чем на 2 минимальных шага, следующая точка -2 Hmin от финиша
        if (endCheck < Hmin) x1 = endCheck -  Hmin; //если ближе чем на мин шаг, то сл точка -Hmin от финиша
        if (endCheck > finish) {x1 = finish; lastTime = true;}

        calcKoshi(x1, y1, h);
    }

    public static void calcKoshi(double x0, double y0, double h) {
        double x1;
        double y1 = y0;
        //на случай, если x будет меньше стартовой точки,
        // а расстояние от финишной до первой с шагом меньше мин шага
        if (x0 < start) {
            if (finish - (x0 + H) < Hmin) {
                x1 = finish;
                h = x1 - x0;
            }
            else {
                x1 = x0 + h;
            }
            calcRunge(x0, y0, h);
        }
    }



    public static void main(String[] args){

        double B, A, C, h;


        /*if (B == C) {
            start = B;
            finish = A;
            h *= (-1);
        }
        else {
            start = A;
            finish = B;
        }
        */

    }
}

