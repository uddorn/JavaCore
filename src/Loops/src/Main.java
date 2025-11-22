import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Введення номера варіанту
            System.out.print("Введіть номер варіанту N: ");
            int N = sc.nextInt();

            // Обчислення меж і кроку
            double start = -10 - 2.5 * N;
            double end = 5 + 1.2 * N;
            double step = 0.5 + N / 20.0;

            double a = 2.0;
            double b = 0.5;
            double z = 1.5;

            System.out.println("=== Обчислення циклом for ===");
            for (double x = start; x <= end; x += step) {
                try {
                    double numerator = Math.sqrt(Math.abs(x))
                            + Math.pow(Math.cos(x), 3)
                            + Math.pow(z, 4);

                    // Перевірка log(x)
                    if (x <= 0) {
                        throw new ArithmeticException("log(x) визначений тільки для x > 0");
                    }

                    // Перевірка аргументу asin
                    double asinArg = b * x - a;
                    if (asinArg < -1 || asinArg > 1) {
                        throw new ArithmeticException("asin аргумент виходить за межі [-1, 1]");
                    }

                    double denominator = Math.log(x) - Math.asin(asinArg);

                    // Перевірка знаменника
                    if (Math.abs(denominator) < 1e-9) {
                        throw new ArithmeticException("Знаменник дорівнює нулю");
                    }

                    double F = numerator / denominator;
                    System.out.printf("x = %.3f  ->  F = %.6f%n", x, F);

                } catch (ArithmeticException e) {
                    System.out.printf("x = %.3f  ->  %s%n", x, e.getMessage());
                    continue; // перехід на наступну ітерацію
                }
            }

            System.out.println("\n=== Обчислення циклом while ===");
            double x = start;
            while (x <= end) {
                try {
                    double numerator = Math.sqrt(Math.abs(x))
                            + Math.pow(Math.cos(x), 3)
                            + Math.pow(z, 4);

                    if (x <= 0) {
                        throw new ArithmeticException("log(x) визначений тільки для x > 0");
                    }

                    double asinArg = b * x - a;
                    if (asinArg < -1 || asinArg > 1) {
                        throw new ArithmeticException("asin аргумент виходить за межі [-1, 1]");
                    }

                    double denominator = Math.log(x) - Math.asin(asinArg);
                    if (Math.abs(denominator) < 1e-9) {
                        throw new ArithmeticException("Знаменник дорівнює нулю");
                    }

                    double F = numerator / denominator;
                    System.out.printf("x = %.3f  ->  F = %.6f%n", x, F);

                } catch (ArithmeticException e) {
                    System.out.printf("x = %.3f  ->  %s%n", x, e.getMessage());
                }

                x += step;
            }

        } catch (InputMismatchException e) {
            System.out.println("Помилка: введено не число для N!");
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
