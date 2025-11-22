import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Введіть x: ");
            double x = sc.nextDouble();

            double b = 0.5;
            double a = 2.0;
            double z = 1.5;

            // Обчислення чисельника
            double numerator = Math.sqrt(Math.abs(x))
                    + Math.pow(Math.cos(x), 3)
                    + Math.pow(z, 4);

            // Перевірка для log(x)
            if (x <= 0) {
                throw new ArithmeticException("Помилка: log(x) визначений тільки для x > 0");
            }

            // Аргумент для asin
            double asinArg = b * x - a;
            if (asinArg < -1 || asinArg > 1) {
                throw new ArithmeticException("Помилка: аргумент asin повинен бути в межах [-1, 1]");
            }

            // Обчислення знаменника
            double denominator = Math.log(x) - Math.asin(asinArg);

            // Перевірка на нуль
            if (Math.abs(denominator) < 1e-9) {
                throw new ArithmeticException("Помилка: знаменник дорівнює нулю!");
            }

            // Результат
            double F = numerator / denominator;
            System.out.println("F = " + F);

        } catch (InputMismatchException e) {
            System.out.println("Помилка: введено не число!");
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Невідома помилка: " + e.getMessage());

        } finally {
            sc.close();
        }
    }
}
