package vm_lab;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static vm_lab.Matrix.sol;

public class SecondLab {

    public static void main(String[] args) throws IOException {

        int SIZE;
        double[] xSolution;
        double[] B;
        double[] xHilbertSolution;
        double[] BHilbert;

        String IER0 = "IER:=0\n";
        String X = "X(good): ";
        String XH = "X(Hilbert): ";
        String IER1 = "IER:=1 DET(good) = 0 \n";
        String IER1H = "IER:=1 DET(Hilbert) = 0 \n";

        Scanner sc = new Scanner(new File("C:\\Users\\gg\\Downloads\\mo_lab-master\\mo_lab-master\\src\\files2lab\\input.txt"));
        SIZE = Integer.parseInt(sc.nextLine());

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream
                (new File("C:\\Users\\gg\\Downloads\\mo_lab-master\\mo_lab-master\\src\\files2lab\\output.txt")));



        //хорошо обусловленная матрица. рандомная генерация.
        double[][] matrix = Matrix.generate(SIZE);
        if(Matrix.determinant(matrix) == 0) {
            try{
                bos.write(IER1.getBytes());
            }
            catch (Exception e) {
                bos.write(e.getMessage().getBytes());
            }/* finally {
                bos.close();
            }*/
        }
    else {
            B = Matrix.generateB(matrix, SIZE);
            Matrix.gaussFirst(matrix, B, SIZE);

            Matrix.gaussFirstIMatrix(matrix, B, SIZE);
            Matrix.print(Matrix.gaussFirstIMatrix(matrix, B, SIZE));
            xSolution = Matrix.Solution(matrix, B, SIZE);
            System.out.println("X(good) = ");
            Matrix.printB(xSolution);

            //вывод полученных данных в файл

            try{
                String sol = sol(xSolution);
                bos.write(IER0.getBytes());
                bos.write(X.getBytes());
                bos.write(sol.getBytes());
            }
            catch (Exception e) {
                bos.write(e.getMessage().getBytes());
            } /*finally {
                bos.close();
            }*/
        }



        //плохо обусловенная матрица Гильберта
        double[][] matrixGilbert = Matrix.generateHilbert(SIZE);
        if(Matrix.determinant(matrixGilbert) == 0){
            try{
                bos.write(IER1H.getBytes());
            }
            catch (Exception e) {
                bos.write(e.getMessage().getBytes());
            } finally {
                bos.close();
            }
        }

    else{
        BHilbert = Matrix.generateB(matrixGilbert, SIZE);
        Matrix.gaussFirst(matrixGilbert, BHilbert, SIZE);
        Matrix.gaussFirstIMatrix(matrixGilbert, BHilbert, SIZE);
        xHilbertSolution = Matrix.Solution(matrixGilbert, BHilbert, SIZE);
        System.out.println("X(Hilbert) = ");
        Matrix.printB(xHilbertSolution);

            //вывод полученных данных в файл

            try{
                String sol = sol(xHilbertSolution);
                bos.write(IER0.getBytes());
                bos.write(XH.getBytes());
                bos.write(sol.getBytes());
            }
            catch (Exception e) {
                bos.write(e.getMessage().getBytes());
            } finally {
                bos.close();
            }
    }



        /*double determ = Matrix.determinant(matrix);

        String det = String.format("%.6f", determ);

        System.out.println("Определитель: " + det);

        System.out.println("Определитель: " + determ);

        Matrix.print(Matrix.gaussFirst(matrix, SIZE));

        System.out.println("Вектор свободных членов  ");
        Matrix.printB(B);

        Matrix.gaussFirstIMatrix(matrix, B, SIZE);

        Matrix.print(matrix);
        System.out.println("Вектор свободных членов  ");
        Matrix.printB(B);*/
    }
}
