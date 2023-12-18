import java.util.*;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> {

    public WeightedGraph(List<V> vertices) {
        super(vertices);
    }

    // Это ненаправленный граф, поэтому мы всегда
    // добавляем ребра в обоих направлениях
    public void addEdge(WeightedEdge edge) {
        edges.get(edge.from).add(edge);
        edges.get(edge.to).add(edge.reversed());
    }

    public void addEdge(int from, int to, float weight) {
        addEdge(new WeightedEdge(from, to, weight));
    }

    public void addEdge(V first, V second, float weight) {
        addEdge(indexOf(first), indexOf(second), weight);
    }

    public static double totalWeight(List<WeightedEdge> path) {
        return path.stream().mapToDouble(we -> we.weight).sum();
    }

    public List printWeightedPath(List<WeightedEdge> wp) {
        List<String> PATH = new ArrayList<>();
        for (WeightedEdge edge : wp) {
            PATH.add(vertexAt(edge.from) + " -> " + vertexAt(edge.to) + "\n");
        }
        PATH.add("\nВремя в пути " + (int)totalWeight(wp) + " минут");
        return PATH;
    }

    public static final class DijkstraNode implements Comparable<DijkstraNode> {
        public final int vertex;
        public final double distance;

        public DijkstraNode(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(DijkstraNode other) {
            Double mine = distance;
            Double theirs = other.distance;
            return mine.compareTo(theirs);
        }
    }

    public static final class DijkstraResult {
        public final double[] distances;
        public final Map<Integer, WeightedEdge> pathMap;

        public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {
            this.distances = distances;
            this.pathMap = pathMap;
        }
    }

    public DijkstraResult dijkstra(V root) {
        int first = indexOf(root); // находим начальный индекс
        // сначала расстояния неизвестны
        double[] distances = new double[getVertexCount()];
        distances[first] = 0; // корневая вершина равна 0
        boolean[] visited = new boolean[getVertexCount()];
        visited[first] = true;
        // как добраться до каждой вершины
        HashMap<Integer, WeightedEdge> pathMap = new HashMap<>();
        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>();
        pq.offer(new DijkstraNode(first, 0));

        while (!pq.isEmpty()) {
            int near = pq.poll().vertex; // исследовать ближайшую вершину
            double distNear = distances[near];
            // все ребра и вершины для данной вершины
            for (WeightedEdge we : edgesOf(near)) {
                // старое расстояние до этой вершины
                double distOld = distances[we.to];
                // новое расстояние до этой вершины
                double pathWeight = we.weight + distNear;
                // новая вершина или найден новый путь?
                if (!visited[we.to] || (distOld > pathWeight)) {
                    visited[we.to] = true;
                    // изменить расстояние до этой вершины
                    distances[we.to] = pathWeight;
                    // заменить ребро на более короткий путь к этой вершине
                    pathMap.put(we.to, we);
                    pq.offer(new DijkstraNode(we.to, pathWeight));
                }
            }
        }

        return new DijkstraResult(distances, pathMap);
    }

    // Принимает словарь ребер, позволяющих достичь каждого узла,
    // и возвращает список ребер от start до end
    public static List<WeightedEdge> pathMapToPath(int start, int end, Map<Integer, WeightedEdge> pathMap) {
        if (pathMap.size() == 0 || start == end) {
            return List.of();
        }

        LinkedList<WeightedEdge> path = new LinkedList<>();
        WeightedEdge edge = pathMap.get(end);
        path.add(edge);
        while (edge.from != start) {
            edge = pathMap.get(edge.from);
            path.add(edge);
        }
        Collections.reverse(path);
        return path;
    }
}