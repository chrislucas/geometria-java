package problems.nivel1;


import java.io.*;
import java.util.StringTokenizer;

/**
 * https://www.urionlinejudge.com.br/judge/pt/problems/view/2158
 * Truncated icosahedron
 * DONE
 * */

public class URI2158 {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    public static void main(String[] args) {
        try {
            String in = null;
            int counter = 1;
            while ( (in = reader.readLine()) != null ) {
                StringTokenizer tk = new StringTokenizer(in, " ");
                long pentagonalFaces = Long.parseLong(tk.nextToken());   //
                long hexagonalFaces = Long.parseLong(tk.nextToken());
                // formula de euler para poliedros
                // numero de faces + numero de vertices - numero de arestas = 2

                // solucao do problema
                // numero de arestas =  numero de faces + (diferenca entre vertices == vertices relevantes) - 2
                long edges = pentagonalFaces * 5 + hexagonalFaces * 6;  // numeros de arestas no total
                 edges /= 2;
                long vertex = edges - (pentagonalFaces + hexagonalFaces - 2);
                /**
                 * Nesse problema cada temos um ponto interessante: Cada pentagono é tocado por 3 hexagonos
                 * ou seja 3 arestas de 1 pentagono sao compartilhadas com 3 hexagonos diferentes
                 *
                 * n-faces de um pentagono * 5  = n-arestas-pentagono
                 * n-arestas-pentagono / 3 = n-hexagonos
                 * */
                writer.printf("Molecula #%d.:.\nPossui %d atomos e %d ligacoes\n\n", counter++, vertex, edges);
            }
        } catch (IOException ieox) { }
    }
}
