public class Edge {
    public final int from; // вершина, ОТКУДА начинается путь
    public final int to; // вершина, КУДА надо прийти

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Edge reversed() {
        return new Edge(from, to);
    }

    @Override
    public String toString() {
        return to + " -> " + from;
    }

}