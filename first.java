package vm_lab;

import java.io.*;
import java.util.Scanner;

import static java.lang.Math.abs;

public class FirstLab {
    public static String right = "EIR:=0\n";
    public static String divZero = "EIR:=1 Деление на ноль\n";
    public static boolean divZeroFlag = false;
    public static String outOfN = "EIR:=2 Превышено число итераций\n";
    public static boolean outOfFlag = false;
    public static int counter;

    public static double f1(double x){
        return x*x*x-2*x+5;
    }

    public static double df1(double x){
        return 3*x*x-2;
    }
    
    public static double f2(double x){
        return x * x + 4 * x - 3;
    }

    public static double df2(double x){
        return 2 * x + 4;
    }

    public static double f3(double x){
        return 2 * x * x * x * x * x - 2 * x * x * x + 2;
    }

    public static double df3(double x){
        return 10 * x * x * x * x - 6 * x * x;
    }

    public static double newton(double EPS, double x, int N) throws IOException{
        double xn = 0;
        int k = 0;

        while(true){
            k++;
            if(k > N) {outOfFlag = true; break;}
            counter = k;
            if (df1(x) == 0) {
                divZeroFlag = true;
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\gg\\Downloads\\mo_lab-master\\mo_lab-master\\src\\output")));
                bos.write(divZero.getBytes());
                bos.close();
                throw new ArithmeticException("IER: Division by 0");
            }
            xn = x - (f1(x)/df1(x));
            if(abs(xn - x) <= EPS) break;
            x = xn;
        }
        return xn;
    }

    public static void main(String[] args) throws IOException {
        double EPS, x;
        int N;

        Scanner sc = new Scanner(new File("C:\\Users\\gg\\Downloads\\mo_lab-master\\mo_lab-master\\src\\input"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\gg\\Downloads\\mo_lab-master\\mo_lab-master\\src\\output")));

        EPS = Double.parseDouble(sc.nextLine());
        x = Double.parseDouble(sc.nextLine());
        N = Integer.parseInt(sc.nextLine());

        System.out.println("ESP = " + EPS);
        System.out.println("~X = " + x);
        System.out.println("Количество итераций = " + N);

        try {
            if(outOfFlag == true) bos.write(outOfN.getBytes());
            else{
                double oX = newton(EPS, x, N);
                System.out.println(oX);
                System.out.println(counter);
                int c = counter;
                String str = "x: " + oX + "\n";
                String str2 = "Количество итераций: " + c + "\n";
                bos.write(right.getBytes());
                bos.write(str2.getBytes());
                bos.write(str.getBytes());

            }
        }
        catch (Exception e) {
            bos.write(e.getMessage().getBytes());
        } finally {
            bos.close();
        }
    }

}
