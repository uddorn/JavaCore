package classesAndObjectsPackages.math;

public class ExpressionCalculator {
    private double a;
    private double b;
    private double z;

    // Конструктор за замовчуванням
    public ExpressionCalculator() {
        this(0.5, 2, 1);
        //this.b = 0.5;
        //this.a = 2.0;
        //this.z = 1.5;
    }

    // Конструктор з параметрами
    public ExpressionCalculator(double a, double b, double z) {
        this.a = a;
        this.b = b;
        this.z = z;
    }

    public double getA() { return a; }
    public void setA(double a) { this.a = a; }

    public double getB() { return b; }
    public void setB(double b) { this.b = b; }

    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }

    // Нестатичний метод обчислення
    public double calculate(double x) {
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

        return numerator / denominator;
    }

    // Статичний метод обчислення
    public static double calculateStatic(double x, double a, double b, double z) {
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

        return numerator / denominator;
    }
}
