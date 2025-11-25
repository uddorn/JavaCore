package classesAndObjectsPackages;

import classesAndObjectsPackages.math.ExpressionCalculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Введіть номер варіанту N: ");
            int N = sc.nextInt();

            double start = -10 - 2.5 * N;
            double end = 5 + 1.2 * N;
            double step = 0.5 + N / 20.0;

            //Використання конструктора без параметрів
            ExpressionCalculator calcDefault = new ExpressionCalculator();

            //Використання конструктора з параметрами
            ExpressionCalculator calcCustom = new ExpressionCalculator(1.0, 0.7, 2.0);

            System.out.println("\n=== Обчислення нестатичним методом (for, конструктор без параметрів) ===");
            for (double x = start; x <= end; x += step) {
                try {
                    double F = calcDefault.calculate(x);
                    System.out.printf("x = %.3f  ->  F = %.6f%n", x, F);
                } catch (ArithmeticException e) {
                    System.out.printf("x = %.3f  ->  %s%n", x, e.getMessage());
                }
            }

            System.out.println("\n=== Обчислення статичним методом (while, конструктор з параметрами) ===");
            double x = start;
            while (x <= end) {
                try {
                    double F = ExpressionCalculator.calculateStatic(
                            x,
                            calcCustom.getA(),
                            calcCustom.getB(),
                            calcCustom.getZ()
                    );
                    System.out.printf("x = %.3f  ->  F = %.6f%n", x, F);
                } catch (ArithmeticException e) {
                    System.out.printf("x = %.3f  ->  %s%n", x, e.getMessage());
                }
                x += step;
            }

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
