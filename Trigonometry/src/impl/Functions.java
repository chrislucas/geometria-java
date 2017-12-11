package impl;

/**
 * O circulo trigonometrico tem raio 1
 * */

import static java.lang.Math.*;

public class Functions {

    public static class Point2Df {
        double x, y;
        public Point2Df(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Vector {
        Point2Df origin, dest, originCoord;
        public Vector(Point2Df origin, Point2Df dest) {
            this.origin = origin;
            this.dest = dest;
            this.originCoord = new Point2Df(0, 0);
        }

        public Point2Df getOriginCoord() {
            return originCoord;
        }
    }

    public static final double PI = Math.PI;
    public static final int hpa = 180;

    public static double fromDegreeToRadian(double degree) {
        return PI * degree / hpa;
    }

    public static double fromRandianToDegree(double radian) {
        return radian * hpa / PI;
    }

    /**
     *
     *  No circulo trigometrico o eixo das ordenadas e o eixo dos senos(Y)
     *  a imagem da funcao seno esta em -1.0 <= sen(a) <= 1.0 ela eh uma funcao impar
     *  onde sen(-a) == -sen(a)
     *
     *  Dado um ponto P na circuferencia, a projecao desse ponto no eixo dos senos achamos
     *  um ponto E. A distancia entre a origem(0, 0) e o ponto E e o que chamamos de seno()
     *
     * Funcao seno eh periodica em 2PI, uma vez que ela se repete apos dar uma volta no ciruclo
     * */
    public static double fnSin(double degree) { return sin(fromDegreeToRadian(degree)); }

    /**
     *
     * O eixo das abscissas eh o eixo da funcao cosseno.
     *
     *
     * Dado um ponto P na circunferencia, a projecao desse ponto no eixo dos cossenos achamos
     * o ponto E. A distancia entre a origem e o ponto E e o que chamamos de cosseno()
     *
     * Funcao cosseno eh periodica em 2PI, uma vez que ela se repete apos dar uma volta no ciruclo
     * Cosseno eh uma funcao par, onde cos(a) == cos(-a) seu grafico eh simetrico em relacao ao eixo
     * das ordenadas
     *
     * */
    public static double fnCos(double degree) { return cos(fromDegreeToRadian(degree)); };

    /**
     * os valores da funcao tangente estao na reta que passa tangente ao circulo trigonometrico.
     * A partir do ponto de origem O traçamos um segmento de reta ate um ponto P no ciruclo
     * e extendemos esse segmento a partir do ponto P ate um ponto F (se existir) na reta tangente
     * ao ciruclo, onde o eixo da tangente.
     *
     * A partir de um ponto A no final eixo das abscissas A(1, 0) indo ate o ponto F no eixo das tangentes
     * temos a medida da tangente no segmento AF
     *
     * Assim
     *
     * alfa=0 -> P=A -> F=A tg tg(alfa) = 0
     * alfa=pi/2 -> P=B -> F=nao existe tg tg(alfa) = nao existe
     * alfa=pi -> P=A' -> F=A tg tg(alfa) = 0
     * alfa=3pi/2 -> P=B' -> F=nao existe tg tg(alfa) = nao existe
     * alfa=2pi -> P=A -> F=A tg tg(alfa) = 0
     *
     * A funcao tangente quando esta em PI/2 ou 3PI/2 nao possui valor uma vez que
     * o ponto P esta paralelo ao eixo da tangente.
     *
     * Olhando os valores no sentido horario, os valores no 4 quadrante sao negativos
     * uma vez que eles se estao na parte negativa do eixo das ordenadas, e ainda estao decrescendo
     *
     *  A funcao eh monotona crescente, ou seja cresce em todo o seu dominio (olhando no sentido antihorario
     *  do circulo)
     *
     *  A funcao eh periodica em PI
     *
     *  Como o eixo das tangentes e paralelo ao circulo trigonometrico, a funcao pode assumir qualquer valor
     *  real
     *
     * OBS: Por isso que tangente de 90 graus nao existe pois quando um ponto P esta na reta
     * dos senos(a 90 graus) esse ponto esta na reta paralela a reta tangente ao circulo
     *
     * */
    public static double fnTan(double degree) { return tan(fromDegreeToRadian(degree)); }


    /**
     * O eixo das cotangentes passa tangete ao circulo trigonometrico e esta paralelo ao eixo
     * dos cossenos (abscissa)
     * */
    public static double fnCotg(double degree) {
        return 1.0/tan(fromDegreeToRadian(degree));
    }

    public static void main(String[] args) {

    }

}
