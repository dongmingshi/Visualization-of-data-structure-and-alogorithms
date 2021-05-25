package Demo.Graph;
import Demo.Data;

import java.util.ArrayList;

public class GraphData extends Data {

    public int n;
    public boolean directed;
    public ArrayList<Vertex> vertexList;
    public int[][] edges;
    public boolean[] isVisited;
    public ArrayList<Vertex> visitedVertex;
    public ArrayList<int[]> visitedEdge;
    public boolean isPath[][];
    public int[] parent;
    public boolean isWeighted;
    public String op;
    public int cost;
    public int umin = -1, vmin = -1;

    public GraphData(ArrayList<Vertex> vertices, boolean directed, String op, boolean isWeighted) {

        n = vertices.size();
        this.directed = directed;
        this.vertexList = new ArrayList<>();
        edges = new int[n][n];
        isVisited = new boolean[n];
        visitedVertex = new ArrayList<>();
        visitedEdge = new ArrayList<>();
        isPath = new boolean[n][n];
        parent = new int[n];
        this.isWeighted = isWeighted;
        this.op = op;

        for(Vertex v : vertices)
            if(!isWeighted)
                addVertexToGraph(v, 1);
            else {
                int randomWeight = (int) (Math.random() * 20 + 1);
                addVertexToGraph(v, randomWeight);
            }

         for(int i = 0; i < n; i++)
             parent[i] = i;

    }

    public Vertex getVertex(int index) {
        for(Vertex v : vertexList)
            if(v.index == index)
                return v;
        return null;
    }

    public void addVertexToGraph(Vertex v, int weight) {
        if(vertexList.isEmpty()) {
            vertexList.add(v);
            v.index = 0;
        } else {
            int index = vertexList.size();
            v.index = index;
            int[] randomIndex = new int[2];
            for(int i = 0; i < randomIndex.length; i++)
                randomIndex[i] = (int) (Math.random() * vertexList.size());
            for(int i = 0; i < randomIndex.length; i++) {
                edges[index][randomIndex[i]] = weight;
                if (!directed)
                    edges[randomIndex[i]][index] = weight;
            }
            vertexList.add(v);
        }
    }

    public int getIndex(int val) {
        for(Vertex vertex: vertexList) {
            if(vertex.value == val)
                return vertex.index;
        }
        return -1;
    }

    public ArrayList<Integer> getNeighbors(int index) { //index
        ArrayList<Integer> neighbors = new ArrayList<>();
        for(int i = 0; i < n; i++)
            if(edges[index][i] != 0)
                neighbors.add(i);
        return neighbors;
    }

    public int numOfEdges() {
        int m = 0;
        if(!directed) {
            for(int i = 0; i < n; i++)
                for(int j = i + 1; j < n; j++)
                    if(edges[i][j] != 0)
                        m++;

        }
        return m;
    }

    static class Edge implements Comparable{
        Vertex start;
        Vertex end;
        int weight;

        public Edge(Vertex start, Vertex end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Object o) {
            return this.weight - ((Edge) o).weight;
        }
    }
}
