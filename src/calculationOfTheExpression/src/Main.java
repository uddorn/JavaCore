import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введіть x: ");
        double x = sc.nextDouble();

        double b = 0.5;
        double a = 2.0;
        double z = 1.5;

        double numerator = Math.sqrt(Math.abs(x)) + Math.pow(Math.cos(x), 3) + Math.pow(z, 4);

        double denominator = Math.log(x) - Math.asin(b * x - a);

        if (Math.abs(denominator) < 1e-9) {
            System.out.println("Помилка: знаменник дорівнює нулю!");
        } else {
            double F = numerator / denominator;
            System.out.println("F = " + F);
        }

        sc.close();
    }
}