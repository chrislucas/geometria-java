package solution.algorithm;

import solution.entity.Location;

import java.util.*;

public class GroupLocationByDistanceAndTime implements Group {

    private LinkedHashMap<Location, LinkedHashSet<Location>> graph;
    private int limitSizeSet;

    private ArrayList<Location> allLocations;

    public GroupLocationByDistanceAndTime(List<Location> locations) {
        allLocations = new ArrayList<>(locations);
        graph = new LinkedHashMap<>();
        int qReference = 0;
        for (Location location : locations) {
            if (location.isReference()) {
                qReference++;
                graph.put(location, new LinkedHashSet<>());
            }
        }
        int size = locations.size();
        // Quantidade maxima que uma localizacao de referencia deve receber
        // removemos da conta os pontos que sao definidos como referencia
        this.limitSizeSet = ((size-qReference) / qReference) + ((size-qReference) % qReference);
    }

    public LinkedHashMap<Location, LinkedHashSet<Location>> getGraph() {
        return graph;
    }

    private final Location.CompareByDistanceAndTime compareLocations = new Location.CompareByDistanceAndTime();

    private Location getReference(Location origin, Location reference) {
        /**
         * Um loop atraves da lista de pontos de referencia ligados a origem.
         * Cada ponto de origem tem uma lista de pontos de referencia com a informacao
         * de distancia e tempo de trajeto
         **/
        for (Location ref : origin.getReferences()) {
            // cada origem tem informacao de distancia e tempo para todos os pontos
            // de referencia, mas queremos recuperar os dados de ponto de referencia especifico
            if (reference.getId() == ref.getId())
                return ref;
        }
        return null;
    }

    /**
     * Location currentBestReferenceLocation, atual ponto de referencia mais proximo e que leva menos
     * tempo de trajeto a partir da localizacao de origem
     *
     * Location currentOrigin: localizacao de origem de um determinado recurso
     *
     * O metodo abaixo prioriza o primeiro que entrou num conjunto
     * */
    private void join2(Location currentBestReferenceLocation, Location currentOrigin) {
        /**
         * Se um ponto de referencia estiver saturado
         * */
        if (graph.get(currentBestReferenceLocation).size() == limitSizeSet) {
            // indicar que tal localizacao de referencia ja foi tentado
            currentBestReferenceLocation.defineVisited();
            // vamos escolher outro ponto de referencia
            PriorityQueue<Location> references = new PriorityQueue<>(currentOrigin.getReferences());
            // remover da lista os pontos de referencia ja visitados
            references.removeIf(Location::isVisited);
            // o metodo abaixo eh mais eficiente pois a referencia visitada a primeira na fila de prioridade
            /**
             * se visitamos todos os pontos de referencia vamos conectar o ponto
             * de origem a melhor referencia porem marcar o ponto de origem como
             * um ponto de sobrecarga indicando que tal ponto sobregarrega um localizacao
             * de referencia
             * */
            if (references.isEmpty()) {
                currentOrigin.defineOverloading();
                graph.get(currentBestReferenceLocation).add(currentOrigin);
            }
            /**
             * Senao, facamos a seguinte pergunta: O que é mais viável ? remover
             * uma das conexoes de 'localizacao de origem' da 'localizacao referencial'
             * atual (leia-se corrente durante a execucao do algoritmo) e adicionar a
             * 'localizacao de origem' atual ou tentar conectar
             * */
            else {
                /**
                 * Recuperar a proxima localizacao de referencia mais adequada para
                 * a localizacao de origem corrente
                 **/
                Location nextBestLocationReference = references.peek();

                /**
                 * recuperar a lista de 'localizacoes de origem' ja adicionadas a
                 * 'localizacao de referencia' atual
                 **/
                Location [] originsAdded = new Location[graph.get(currentBestReferenceLocation).size()];
                graph.get(currentBestReferenceLocation).toArray(originsAdded);

                // Marcar a 'localizacao de origem' na posicao 0 como a melhor escolha para uma possivel remocao
                Location bestOriginLocationChoosed = originsAdded[0];
                // dados do trajeto entre a origem escolhida e a proxima melhor localizacao de referencia
                Location bestLocationReferenceChoosed = getReference(bestOriginLocationChoosed, nextBestLocationReference);

                // Loop sobre todas as posicoes de origem ja adicionadas para descobrir qual
                // esta mais proxima da proxima localizacao de referencia (comparando distancia e tempo de deslocamento)
                for (int i = 1; i < originsAdded.length ; i++) {
                    // dados do trajeto entre a i-esima origem e a proxima melhor localizacao de referencia
                    Location ref = getReference(originsAdded[i], nextBestLocationReference);
                    // teste para ver qual localizacao de origem esta mais proxima do proximo ponto de referencia
                    if (compareLocations.compare(ref, bestLocationReferenceChoosed) < 0) {
                        bestOriginLocationChoosed = originsAdded[i];
                        bestLocationReferenceChoosed = ref; //new Location(ref, ref.getDistance(), ref.getTime());
                    }
                }

                //boolean removed = false;
                /**
                 * Se dentre as 'localizacoes de origem' ja adicionadas tivermos uma cuja distancia/tempo
                 * para a proxima 'localizacao de referencia' for menor do que distancia entre a 'origem' atual
                 * e essa mesam localizacao de referencia
                 * */
                if (compareLocations.compare(bestLocationReferenceChoosed, nextBestLocationReference) < 0) {
                    // removemos a conexao entre o ponto de origem anteriormente escolhido
                    graph.get(currentBestReferenceLocation).remove(bestOriginLocationChoosed);
                    // adicionamos a nova conexao
                    graph.get(currentBestReferenceLocation).add(currentOrigin);
                    // vamos tentar adicionar a localizacao de origem removida a localizacao de referencia
                    // que foi definida como a mais adequada (menor distancia/tempo)
                    join2(nextBestLocationReference, bestOriginLocationChoosed);
                    //removed = true;
                }
                else {
                    /**
                     * Se a localizacao de origem escolhida para remocao for mais distante do novo ponto
                     * de referencia, vamos tentar adicionar o ponto origem atual ao proximo ponto de referencia
                     * considerado o mais adequado
                     * */
                    join2(nextBestLocationReference, currentOrigin);
                }
            }
        }
        else {
            currentBestReferenceLocation.defineVisited();
            graph.get(currentBestReferenceLocation).add(currentOrigin);
        }
    }

    /**
     * O metodo abaixo ira adicionar ao conjunto de pontos de origem os pontos com a menor distancia
     * ao ponto referencia
     * */
    private void join(Location currentBestReferenceLocation, Location currentOriginLocation) {
        if (graph.get(currentBestReferenceLocation).size() == limitSizeSet) {
            // indicar que tal localizacao de referencia ja foi tentado
            getReference(currentOriginLocation, currentBestReferenceLocation).defineVisited();
            // vamos escolher outro ponto de referencia
            PriorityQueue<Location> referencesLocationCurrentOrigin = new PriorityQueue<>(currentOriginLocation.getReferences());
            // remover da lista os pontos de referencia ja visitados
            referencesLocationCurrentOrigin.removeIf(Location::isVisited);
            // o metodo abaixo eh mais eficiente pois a referencia visitada a primeira na fila de prioridade
            /**
             * Se nao encontrarmos a solucao otima, adicione o ponto de origem ao ponto de referencia
             * ideal, mesmo que ele esteja saturado. Porem marque-o como um ponto de sobrecarga
             * */
            if (referencesLocationCurrentOrigin.isEmpty()) {
                currentOriginLocation.defineOverloading();
                graph.get(currentBestReferenceLocation).add(currentOriginLocation);
            }
            else {
                /**
                 * recuperar a lista de 'localizacoes de origem' ja adicionadas a
                 * 'localizacao de referencia' atual
                 **/
                Location [] originsAdded = new Location[graph.get(currentBestReferenceLocation).size()];
                graph.get(currentBestReferenceLocation).toArray(originsAdded);

                /**
                 * Recuperar a proxima localizacao de referencia mais adequada para
                 * a localizacao de origem corrente
                 **/
                Location nextBestLocationReference = referencesLocationCurrentOrigin.peek();
                /**
                 * Lista de localizacoes de 'origem' que ja foram conectadas a uma localizacao de 'referencia' candidatas a serem
                 * removidas para a adicao de uma localizacao de 'origem' mais proxima ou com menos tempo de trajeto
                 * */
                List<Location> candidatesToRemove = new ArrayList<>();
                // Loop sobre todas as posicoes de origem ja adicionadas para descobrir
                // quais estao mais distante do ponto de referencia que eles ja estao conectados
                // do que o ponto de origem que estamos tentando inserir
                for (int i = 0; i < originsAdded.length ; i++) {
                    /**
                     * recuperar os valores de distancia/tempo entre um ponto de origem adiciona a um ponto de referencia
                     * */
                    Location bestReferenceLocationAdded = getReference(originsAdded[i], currentBestReferenceLocation);
                    /**
                     * Verificar se a distancia/tempo entre a localicao de origem atual eh menor do que alguma distancia/tempo
                     * das localizacoes de origem ja adicionadas
                     * */
                    if (compareLocations.compare(currentBestReferenceLocation, bestReferenceLocationAdded) < 0) {
                        // TODO ATENCAO: Temos um ponto mais proximo que pode ser adicionado ao ponto de referencia
                        candidatesToRemove.add(originsAdded[i]);
                    }
                }

                /**
                 * Se tivermos pelo menos uma localizacao que foi adicionada cuja distancia/tempo for maior
                 * do que a da localizaca que estamos avaliando, vamos verificar quais destas localizacoes
                 * esta mais perto da proxima 'localizacao de referencia' viavel.
                 * */
                if (candidatesToRemove.size() > 0) {
                    // escolher o ponto de origem ja adicionado como o ideal para posteriormente remove-lo
                    Location originLocationChosenToRemove1 = candidatesToRemove.get(0);

                    // dados de distancia/tempo da localizacao de referencia baseado na proxima localizacao de referencia ideal em relacao
                    // a localizacao de origem corrente
                    Location nextBestReference1 = getReference(originLocationChosenToRemove1, nextBestLocationReference);

                    // Loop sobre as localizacoes de origem candidatas a serem removidas
                    // queremos descobrir qual delas esta mais proxima da proxima localizacao de referencia ideal
                    for (int i = 1; i < candidatesToRemove.size() ; i++) {
                        Location originLocationChosenToRemove2 = candidatesToRemove.get(i);
                        Location ref1 = originLocationChosenToRemove1.getReferences().peek();
                        Location ref2 = originLocationChosenToRemove2.getReferences().peek();
                        // Verificar qual dos pontos de origem que queremos remover esta mais proximo da referencia
                        // que estamos avaliando
                        if (compareLocations.compare(ref1, ref2) < 0) {
                            // se o ponto de origem 1 estiver mais proximo do ponto de refenencia do que o ponto de origem 2
                            // verificar se o ponto de origem 1 esta mais distante (ou a mesma distancia) do proximo ponto de referencia do que o ponto 2
                            // Pois assim compensa remover o ponto de origem mais distante da referencia atual e mais proximod da proxima referencia ideal

                            // melhor proximo ponto de referencia do i-esimo ponto de origem na lista dos escolhidos a exculsao
                            Location nextBestReference2 = getReference(originLocationChosenToRemove2, nextBestLocationReference);
                            if (compareLocations.compare(nextBestReference1, nextBestReference2) >= 0) {
                                nextBestReference1 = nextBestReference2;
                                originLocationChosenToRemove1 = originLocationChosenToRemove2;
                            }
                        }
                    }

                    /**
                     * Se tivermos um candidato que tem um custo maior de distante/tempo do 'ponto de referencia atual'
                     * e menor distancia/tempo da 'proxima localizacao de referencia ideal', removeremos a localizacao
                     * escolhida como a ideal da localizacao de referencia atual, adicionaremos a localizacao de origem
                     * atual e tentaremos realocar a localizacao de origem removida na proxima localizacao de referencia
                     * considerada a n-esima mais ideal
                     * */
                    if (compareLocations.compare(nextBestReference1, nextBestLocationReference) < 0) {
                        // remover a conexao entre um a localizacao de origem anteriomente conecatada da
                        // localizacao de referencia
                        graph.get(currentBestReferenceLocation).remove(originLocationChosenToRemove1);
                        // conecatar a localizacao de origem corrente a localizacao de refenrecia
                        graph.get(currentBestReferenceLocation).add(currentOriginLocation);
                        // tentar
                        join(nextBestLocationReference, originLocationChosenToRemove1);
                    }
                    else {
                        join(nextBestLocationReference, currentOriginLocation);
                    }
                }
                // se nao tiver nenhum candidado para remover, tentar adicionar o ponto de origem corrente ao
                // proximo ponto de referencia ideal
                else {
                    join(nextBestLocationReference, currentOriginLocation);
                }
            }
        }
        else {
            /**
             * Como lidamos com um algoritmo recursivo que busca alocar pontos a uma distancia/tempo
             * otima, por vezes o argumento currentBestReferenceLocation nao eh o valor otimo
             * para a localizacao de origem que esta sendo avaliada. Assim precisamos testar
             * se a localizacao de referencia (bestReferenceLocation, removida da fila de prioridade) ideal para a localizaca
             * eh maior ou igual a localizacao de referencia (currentBestReferenceLocation) passada por argumento
             * desse metodo recursivo
             * */
            PriorityQueue<Location> references = new PriorityQueue<>(currentOriginLocation.getReferences());
            // remover os pontos ja visitados
            references.removeIf(Location::isVisited);
            // se todos os pontos ja foram visitados, adicione a localizacao de origem a melhor localizacao de referencia
            if (references.isEmpty()) {
                Location bestReferenceLocation = currentOriginLocation.getReferences().peek();
                bestReferenceLocation.defineOverloading(); // marque como uma adicao que sobrecarrega a localizaco de referencia
                graph.get(bestReferenceLocation).add(currentOriginLocation);
            }
            else {
                // senao recupere a localizacao de referencia ideal
                Location bestReferenceLocation = references.peek();
                // compare-a com a localizacao de referencia considerada ideal que foi passara como arugmento
                // se ela for realmente a ideal, conecte as duas localizacoes
                // so precisamos comparar o ID das localizacoes de referencia para saber se sao as mesmas
                // uma vez que a distancia/tempo podem variar de origem para origem
                if (bestReferenceLocation.equals(currentBestReferenceLocation)) {
                    // verificar se a localizacao de referencia nao esta saturada
                    if (graph.get(currentBestReferenceLocation).size() == limitSizeSet) {
                        // se sim, vamos resolver esse problema de forma recursiva
                        join(currentBestReferenceLocation, currentOriginLocation);
                    }
                    else {
                        currentBestReferenceLocation.defineVisited();
                        graph.get(currentBestReferenceLocation).add(currentOriginLocation);
                    }
                }
                else {
                    // senao tente conectar a localizacao de referencia ideal coletada da fila de prioridade
                    join(bestReferenceLocation, currentOriginLocation);
                }
            }
        }
    }

    private void join() {
        for (Location location : allLocations) {
            if (!location.isReference()) {
                join(location.getReferences().peek(), location);
            }
        }
    }

    @Override
    public Group apply() {
        join();
        return this;
    }

    @Override
    public Location chooseOneOption(List<Location> locations) {
        return null;
    }
}
