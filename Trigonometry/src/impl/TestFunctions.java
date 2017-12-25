package impl;

import static impl.Functions.*;

public class TestFunctions {

    private static void testTransformations() {
        for (int i = 0; i < 361 ; i++)
            System.out.printf("Graus: %d Radianos: %.5f\n", i, fromDegreeToRadian(i));
        System.out.println("");
        for (double i = 0; i<=PI*6 ; i+=PI/32)
            System.out.printf("Radianos: %.5f Graus: %.5f\n", i, fromRandianToDegree(i));
    }

    private static void testFnSin() {
        /*
        for(int i=0; i<=360; i++)
            System.out.printf("Seno de %d, %.5f\n", i, fnSin(i));
        System.out.println(Functions.fnSin(92));
        System.out.println(Functions.fnSin(88));
        System.out.println(Functions.fnSin(268));
        */
        for(double i=-1.0; i<=1.0; i+=.2)
            System.out.printf("Seno(%f) %.5f\n", i, fnSin(i));
    }

    private static void testFnCos() {
        //for (int i = 0; i <=360 ; i++)
            //System.out.printf("Cosseno de %d, %.5f\n", i, Functions.fnCos(i));

        for(double i=-1.0; i<=1.0; i+=.2)
            System.out.printf("Cosseno(%f) = %.5f\n", i, fnCos(i));

        //System.out.println(Functions.fnCos(30));
        //System.out.println(Functions.fnCos(330));
        //System.out.println(Functions.fnCos(30) == Functions.fnCos(-30));
    }

    private static void testFnTan() {
        //for (int i = 0; i <=360 ; i++)
            //System.out.printf("Tangente de %d, %.5f\n", i, Functions.fnTan(i));

        for(double x=0; x<=360; x+=5)
            System.out.printf("Tangente de %f, %.5f\n", x, fnTan(x));
    }

    public static void main(String[] args) {
        //testFnSin();
        //testFnCos();
        testFnTan();
    }
}
