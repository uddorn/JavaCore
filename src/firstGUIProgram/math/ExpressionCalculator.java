package firstGUIProgram.math;

public class ExpressionCalculator {
    private double a;
    private double b;
    private double z;

    // Конструткор
    public ExpressionCalculator() {
        this(2.0, 0.5, 1.5);
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

    // Нестатичний метод
    public double calculate(double x) {
        if (x <= 0) {
            throw new ArithmeticException("log(x) is defined only for x > 0");
        }
        double asinArg = b * x - a;
        if (asinArg < -1 || asinArg > 1) {
            throw new ArithmeticException("asin argument is out of bounds [-1, 1]");
        }
        double numerator = Math.sqrt(Math.abs(x)) + Math.pow(Math.cos(x), 3) + Math.pow(z, 4);
        double denominator = Math.log(x) - Math.asin(asinArg);
        if (Math.abs(denominator) < 1e-9) {
            throw new ArithmeticException("Denominator is zero");
        }
        return numerator / denominator;
    }

    // Статичний метод
    public static double calculateStatic(double x, double a, double b, double z) {
        if (x <= 0) {
            throw new ArithmeticException("log(x) is defined only for x > 0");
        }
        double asinArg = b * x - a;
        if (asinArg < -1 || asinArg > 1) {
            throw new ArithmeticException("asin argument is out of bounds [-1, 1]");
        }
        double numerator = Math.sqrt(Math.abs(x)) + Math.pow(Math.cos(x), 3) + Math.pow(z, 4);
        double denominator = Math.log(x) - Math.asin(asinArg);
        if (Math.abs(denominator) < 1e-9) {
            throw new ArithmeticException("Denominator is zero");
        }
        return numerator / denominator;
    }
}