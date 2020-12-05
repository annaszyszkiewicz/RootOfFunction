package edu.ib;

/**
 * klasa licząca miejsca zerowe funkcji za pomocą róznych metod
 */
public class RootOfFunction {

    /**
     * metoda zwracająca miejsce zerowe funcji za pomocą bisekcji
     * @param xl lewy koniec przedziału
     * @param xu prawy koniec przedziału
     * @param epsilon1 błąd
     * @param function funkcja
     * @return obliczone miejsce zerowe lub informację o błędzie jako String
     */
    public String bisection(double xl, double xu, double epsilon1, Calculate function) {
        if (function.f(xl) * function.f(xu) < 0) {
            double epsilon2 = 0;
            double xr1 = (xl + xu) / 2;
            double xr2 = xl;
            int n = 1;

            do {
                if (function.f(xl) * function.f(xr1) < 0) {
                    xu = xr1;
                } else if (function.f(xr1) * function.f(xu) < 0) {
                    xl = xr1;
                } else {
                    break;
                }
                xr2 = xr1;
                xr1 = (xu + xl) / 2;
                epsilon2 = Math.abs((xr1 - xr2) / xr1);
                //System.out.println("Bisection method: " + "number = " + n + ", " + "xl = " + xl + ", " + "xu = " + xu + ", " + "x = " + xr1 + ", " + "błąd = " + epsilon2);
                n++;
            } while (epsilon1 <= epsilon2);
            return String.valueOf(xr1);
        }
        else if (function.f(xl) == 0){
            return String.valueOf(xl);
        }
        else if (function.f(xu) == 0){
            return String.valueOf(xu);
        }
        else return "Failed";
    }

    /**
     * metoda zwracająca miejsce zerowe funcji za pomocą fałszywej liniowości
     * @param xl lewy koniec przedziału
     * @param xu prawy koniec przedziału
     * @param epsilon1 błąd
     * @param function funkcja
     * @return obliczone miejsce zerowe lub informację o błędzie jako String
     */
    public String falsi(double xl, double xu, double epsilon1, Calculate function) {
        if (function.f(xl) * function.f(xu) < 0) {
            double epsilon2 = 0;
            double xr1;
            xr1 = xl - (function.f(xl) * (xu - xl) / (function.f(xu) - function.f(xl)));
            double xr2 = xr1;
            int n = 1;
            do {
                if (function.f(xr1) == 0) {
                    break;
                } else if (function.f(xr1) * function.f(xl) < 0) {
                    xu = xr1;
                } else xl = xr1;
                xr1 = xl - (function.f(xl) * (xu - xl) / (function.f(xu) - function.f(xl)));
                epsilon2 = Math.abs((xr1 - xr2) / xr1);
                //System.out.println("Method of false position: " + "number = " + n + ", " + "xl = " + xl + ", " + "xu = " + xu + ", " + "x = " + xr1 + ", " + "błąd = " + epsilon2);
                n++;
                xr2 = xr1;
            } while (epsilon1 <= epsilon2);
            return String.valueOf(xr1);
        } else return "Failed";
    }

    /**
     * metoda zwracająca miejsce zerowe funcji za pomocą punktu stałego
     * @param x0 punkt startowy
     * @param epsilon1 błąd
     * @param function funkcja
     * @return obliczone miejsce zerowe lub informację o błędzie jako String
     */
    public String fixedpoint(double x0, double epsilon1, Calculate function) {
        double epsilon2 = 0;
        double x1 = x0 + function.f(x0);
        double x2 = 0;
        int n = 1;
        do {
            x2 = x1;
            x1 = x1 + function.f(x1);
            //System.out.println("Fixed-point iteration: " + "number = " + n + ", " + "x = " + x1 + ", " + "błąd = " + epsilon2);
            epsilon2 = Math.abs((x1 - x2) / x1);
            n++;
        } while (epsilon2 > epsilon1);

        if (Double.POSITIVE_INFINITY == x1 || Double.NEGATIVE_INFINITY == x1) {
            return "Failed";
        } else return String.valueOf(x1);

    }

    /**
     * metoda zwracająca pochodną funkcji
     * @param function funkcja
     * @param x miejsce liczenia pochodnej
     * @return pochodną funkcji
     */
    private double derivative(Calculate function, double x) {
        return (function.f(x + Math.pow(10, -10) / 2) - function.f(x - Math.pow(10, -10) / 2)) / Math.pow(10, -10);
    }

    /**
     * metoda zwracająca miejsce zerowe funcji za pomocą stycznych
     * @param x0 początkowe przybliżenie pierwiastka
     * @param epsilon1 błąd
     * @param function funkcja
     * @return obliczone miejsce zerowe lub informację o błędzie jako String
     */
    public String newtonraphson(double x0, double epsilon1, Calculate function) {
        double epsilon2 = 0;
        double x1 = x0 - function.f(x0) / (1 + function.f(x0) * derivative(function, x0));
        double x2 = 0;
        int n = 1;
        do {
            x2 = x1;
            x1 = x1 - (function.f(x1) / (1 + function.f(x1) * derivative(function, x1)));
            //System.out.println("Newton-Raphson method: " + "number = " + n + ", " + "x = " + x1 + ", " + "błąd = " + epsilon2);
            epsilon2 = Math.abs((x1 - x2) / x1);
            n++;
        } while (epsilon2 > epsilon1);

        if (function.f(x1) > 0.1 || function.f(x1) < -0.1) {
            return "Failed";
        } else return String.valueOf(x1);
    }

    /**
     * metoda zwracająca miejsce zerowe funcji za pomocą siecznych
     * @param x0 wartośc przybliżenia pierwiastka
     * @param x1 wartość przybliżenia pierwiastka
     * @param epsilon1 błąd
     * @param function funkcja
     * @return obliczone miejsce zerowe lub informację o błędzie jako String
     */
    public String sieczna(double x0, double x1, double epsilon1, Calculate function) {
        double epsilon2 = 0;
        double xa = x0;
        double xb = x1;
        double x = x1 - function.f(x1) * (x0 - x1) / (function.f(x0) - function.f(x1));
        int n = 1;
        do {
            xa = xb;
            xb = x;
            x = xb - function.f(xb) * (xa - xb) / (function.f(xa) - function.f(xb));
            //System.out.println("Metoda siecznych: " + "number = " + n + ", " + "x = " + x  + ", " + "błąd = " + epsilon2);
            epsilon2 = Math.abs((x - xb) / x);
            n++;
        } while (epsilon2 > epsilon1);
        if (function.f(x) > 0.1 || function.f(x) < -0.1) {
            return "Failed";
        } else return String.valueOf(x);
    }
}
