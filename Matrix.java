package vm_lab;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.Random;

import static java.lang.Math.abs;

public class Matrix {

    public static int maxVal = 1;
    public static int minVal = -1;

    public static double minSmall = 0.0;
    public static double maxSmall = 0.1;

    //погрешность
    public static double epsError(double[] X, int N){
        double eps = 0;
        for(int i = 0; i < N; i++)
            eps += abs(X[i] - 1);
        return eps/N;
    }

    //генерация хорошо обусловленной матрицы
    public static double[][] generate(int size) {
        double[][] matrix = new double[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(maxVal) + minVal + Math.random()*0.001;
            }
        }

        return matrix;
    }

    //генерация плохо обусловленной матрицы
    public static double[][] generateHilbert(int size) {
        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (double) 1/((i+1) + (j+1) - 1);
            }
        }

        return matrix;
    }

    //генерация правой части. получается из умножения основной матрицы на единичный вектор
    public static double[] generateB(double[][] matrix, int size) {
        double[] B = new double[size];

        for(int i = 0; i < size; i++ ) {
            double sum = 0;
            for (int j = 0; j < size; j++)
                sum += matrix[i][j];

            B[i] = sum;
        }
        return B;
    }

    //печать основной матрицы
    public static void print(double[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sb
                        .append(String.format("%7.5f", matrix[i][j]))
                        .append(" ");
            }

            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    //печать вектора свободных членов
    public static void printB(double[] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        for (int i = 0; i < matrix.length; i++) {
             {
                sb
                        .append(String.format("%7.5f", matrix[i]))
                        .append(" ");
            }

            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    //преобразование вектора свободных членов в строку

    public static String sol(double[] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        for (int i = 0; i < matrix.length; i++) {
            {
                sb
                        .append(String.format("%7.5f", matrix[i]))
                        .append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }


    //копирование одного массива в другой
    public static double[][] copy(double[][] matrix) {
        double[][] copy = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                copy[i][j] = matrix[i][j];
            }
        }

        return copy;
    }

    //реализация метода Гаусса

    //приведение матрицы к треугольному виду
    public static double[][] gaussFirst(double[][] matrix, double[] B, int n) {

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {

                double coeff = -matrix[j][i] / matrix[i][i];
                for (int col = i; col < n; col++){
                    matrix[j][col] += matrix[i][col] * coeff;
                }
                B[j] += B[i] * coeff;
            }
        }
        return matrix;
    }

    //главную диагональ делаем единичной
    public static double[][] gaussFirstIMatrix(double[][] a, double[] b, int n){
        for(int i = 0; i < n; i++){
            b[i] = b[i]/a[i][i];
            for(int j = i+1; j < n; j++){
                a[i][j] = a[i][j]/a[i][i];
            }
            a[i][i] = 1;
        }
        return a;
    }

    //обратный ход
    public static double[] Solution(double[][] matrix, double[] B, int n){

        double[] x = new double[n];

        for (int i = 0; i < n; i++) x[i] = 0;

        for (int k = n - 1; k >= 0; k--)
        {
            x[k] = B[k];
            for (int i = 0; i < k; i++)
                B[i] = B[i] - matrix[i][k] * x[k];
        }
        return x;
    }


    private static double[] findDivider(double[][] matrix, int position) {
        double[] result = new double[2];

        double maxNum = -20;
        int rowNum = position;
        for (int j = position; j < matrix.length; j++) {
            if (matrix[j][position] != 0 && matrix[j][position] > maxNum) {
                rowNum = j;
                maxNum = matrix[j][position];
            }
        }

        if (position != rowNum) {
            swapRows(matrix, position, rowNum);
            result[1] = 1;
        }
        result[0] = matrix[position][position];

        return result;
    }

    private static void swapRows(double[][] matrix, int firstRow, int secondRow) {
        for (int k = firstRow; k < matrix[0].length; k++) {
            double temp = matrix[secondRow][k];
            matrix[secondRow][k] = matrix[firstRow][k];
            matrix[firstRow][k] = temp;
        }
    }

    public static double determinant(double[][] matrix) {
        if (matrix.length != matrix[0].length) {
            throw new ArithmeticException("Different sizes");
        }

        double[][] copy = Matrix.copy(matrix);
        double accum = 1;

        for (int i = 0; i < copy.length - 1; i++) {
            double[] result = findDivider(copy, i);

            if (result[0] == 0) {
                return 0;
            } else if (result[1] == 1) {
                accum *= -1;
            }

            double divider = result[0];
            accum *= divider;

            for (int k = i; k < copy[0].length; k++) {
                copy[i][k] = copy[i][k] / divider;
            }

            for (int j = i; j < copy[0].length - 1; j++) {
                double dev = copy[j + 1][i];

                for (int k = i; k < copy[0].length; k++) {
                    copy[j + 1][k] = copy[j + 1][k] - (dev * copy[i][k]);
                }
            }
        }

        double result = 1;

        for (int i = 0; i < copy.length; i++) {
            result *= copy[i][i];
        }

        return result * accum;
    }




    public static double[][] reverseMatrix(double[][] matrix) {
        if (matrix.length != matrix[0].length) {
            throw new ArithmeticException("Wrong sizes");
        }

        double det = determinant(matrix);

        if (det == 0) {
            throw new ArithmeticException("Division by zero");
        }

        double reversedDeterminant = 1 / det;

        double[][] result = new double[matrix.length][matrix.length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = findAlgExt(matrix, i, j);
            }
        }

        return Matrix.transpose(multiplyByNumber(result, reversedDeterminant));
    }

    public static double[][] transpose(double[][] originMatrix) {
        double[][] result = new double[originMatrix.length][originMatrix[0].length];

        for (int i = 0; i < originMatrix.length; i++) {
            for (int j = 0; j < originMatrix[0].length; j++) {
                result[i][j] = originMatrix[j][i];
            }
        }

        return result;
    }

    public static double findAlgExt(double[][] matrix, int rowNum, int colNum) {
        int algSize = matrix.length - 1;
        double[][] algExtMatrix = new double[algSize][algSize];

        for (int i = 0; i < matrix.length; i++) {
            if (i == rowNum) {
                continue;
            }

            for (int j = 0; j < matrix[0].length; j++) {
                if (j == colNum) {
                    continue;
                }

                int k = i;
                int l = j;
                if (i > rowNum) {
                    k--;
                }

                if (j > colNum) {
                    l--;
                }

                algExtMatrix[k][l] = matrix[i][j];
            }
        }

        return Math.pow(-1, (rowNum + colNum + 2)) * determinant(algExtMatrix);
    }

    public static double[][] multiplyByNumber(double[][] matrix, double number) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] * number;
            }
        }

        return result;
    }



}
