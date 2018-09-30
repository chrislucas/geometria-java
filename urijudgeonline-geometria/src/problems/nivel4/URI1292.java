package problems.nivel4;


import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1292
 *
 *
 * Estudar melhor essa solucao
 * http://ken.duisenberg.com/potw/archive/arch98/981113sol.html
 * */
public class URI1292 {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    private static final NumberFormat numberFormat = new DecimalFormat("#.##########");


    public static double s1(double pSide) {
        return pSide * (Math.sin(108 * Math.PI / 180.0f) / Math.sin(63 * Math.PI / 180.0f));
    }

    public static void main(String[] args) {
        try {
            String in = null;
            while ( ( in = reader.readLine()) != null) {
                double pSide = Double.parseDouble(in);
                writer.printf("%s\n", numberFormat.format(s1(pSide)));
            }
        } catch (IOException ioex) {}
    }

/**
 * https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html
 * */
    public static void testDecimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        System.out.println(decimalFormat.format(1100000L));
        System.out.println(decimalFormat.format(1123450L));
    }
}
