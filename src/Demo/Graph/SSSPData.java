package Demo.Graph;
import java.util.ArrayList;

public class SSSPData extends GraphData {
    int[] distance;
    public int source;
    public boolean end;

    public SSSPData(ArrayList<Vertex> vertices, boolean directed, String op, boolean isWeighted) {
        super(vertices, directed, op, isWeighted);

        distance = new int[n];

        for(int i = 0; i < n; i++)
            distance[i] = Integer.MAX_VALUE;


    }

    public ArrayList<Integer> getPath(int index) {
        ArrayList<Integer> path = new ArrayList<>();
        path(index, path);
        return path;
    }

    public void path(int index, ArrayList<Integer> path) {
        if(parent[index] != index) {
            path(parent[index],  path);
            path.add(parent[index]);
        }
    }
}
