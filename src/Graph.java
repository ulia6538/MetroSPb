import java.util.ArrayList;
import java.util.List;

// V — тип вершин графа
// E — тип ребер
public abstract class Graph<V, E extends Edge> {

    private ArrayList<V> vertices = new ArrayList<>();
    protected ArrayList<ArrayList<E>> edges = new ArrayList<>();

    public Graph(List<V> vertices) {
        this.vertices.addAll(vertices);
        for (V vertex : vertices) {
            edges.add(new ArrayList<>());
        }
    }

    // Количество вершин
    public int getVertexCount() {
        return vertices.size();
    }

    // Поиск вершины по индексу
    public V vertexAt(int index) {
        return vertices.get(index);
    }

    // Поиск индекса вершины в графе
    public int indexOf(V vertex) {
        return vertices.indexOf(vertex);
    }

    //  Возвращает все ребра, связанные с вершиной, имеющей заданный индекс
    public List<E> edgesOf(int index) {
        return edges.get(index);
    }

}