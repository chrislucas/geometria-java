package solution.entity;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Location  {

    public static class CompareByDistanceAndTime implements Comparator<Location> {
        @Override
        public int compare(Location p, Location q) {
            if (p.distance == q.distance)
                return p.time - q.time;
            return p.distance - q.distance;
        }
    }

    /**
     * Um local do tipo referencial eh um local fixo numa região do globo para onde
     * queremos enviar algum tipo de recurso.
     *
     * Locais que nao sao referenciais serao denominados de locais de origem. Desses
     * locais partem os recursos que queremos enviar. Cada local de origem possui
     * informacoes sobre os locais de referencia existentes (Distancia e tempo)
     *
     * */
    final private int id;
    private int distance;           // em metros
    private int time;               // em milissegundos
    private boolean isReference;
    private boolean isVisited;
    private boolean isOverloading;

    private PriorityQueue<Location> references;
    // ponto de referencia vinculado ao ponto de origem
    private Location reference;

    // construtor para locais de referencia que serao vinculados a locais de origem
    public Location(Location reference, int distance, int time) {
        this.id = reference.getId();
        this.reference = reference;
        this.distance = distance;
        this.time = time;
        this.isReference = true;
    }

    public Location(int id, List<Location> references) {
        this.id = id;
        this.isReference = false;
        this.references = new PriorityQueue<>( new CompareByDistanceAndTime());
        this.references.addAll(references);
    }

    public Location(int id, boolean isReference) {
        this.id = id;
        this.isReference = isReference;
    }

    public boolean isReference() {
        return isReference;
    }

    public void defineVisited() {
        this.isVisited = true;
    }

    public void defineOverloading() {
        this.isOverloading = true;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public PriorityQueue<Location> getReferences() {
        return references;
    }

    public int getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        String debug;
        if (isReference) {
            debug = String.format("Ponto de Referencia - ID: %d", id);
        }
        else {
            StringBuilder stringReferences = new StringBuilder();
            for (Location reference : references) {
                stringReferences.append(
                        String.format("Destino: %d, Distancia: %d, Tempo: %d\n"
                        , reference.getId()
                        , reference.getDistance()
                        , reference.getTime())
                );
            }
            debug = String.format("Ponto de Origem - ID: %d\n%s"
                    , id
                    , stringReferences.toString()
            );
        }


        return debug;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Location) obj).getId();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
