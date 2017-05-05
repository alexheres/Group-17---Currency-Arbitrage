import java.util.LinkedList;
import java.util.ArrayList;

public class BellmanFord 
{
    private double[] distTo;                // distTo[vertex] = distance  of shortest source->vertex path
    private dedge[] edgeTo;         		// edgeTo[vertex] = last edge on shortest source->vertex path
    private boolean[] onQueue;              // onQueue[vertex] = is vertex currently on the queue?
    private LinkedList<Integer> queue;      // queue of vertices to relax
    private int cost;                       // number of calls to relax()
    private Iterable<dedge> cycle;          // negative cycle (or null if no such cycle)

    
    public BellmanFord(CreateGraph graph, int src) 
    {
        distTo  = new double[graph.getVertexTotal()];
        edgeTo  = new dedge[graph.getVertexTotal()];
        onQueue = new boolean[graph.getVertexTotal()];
        
        for (int v = 0; v < graph.getVertexTotal(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
     
        distTo[src] = 0.0;

        // Bellman Ford algorithm
        queue = new LinkedList<Integer>();
        queue.add(src);
        onQueue[src] = true;
        
        while (!queue.isEmpty() && !hasNegativeCycle()) 
        {
            int v = queue.remove();
            onQueue[v] = false;
            relax(graph, v);
        }
        
        assert check(graph, src);
    }

    
    // relax source and change queued endpoints
    private void relax(CreateGraph graph, int src) 
    {
    	for (dedge edge : graph.adj(src)) 
        {
            int dest = edge.getDest();
            
            if (distTo[dest] > distTo[src] + edge.getWeight())
            {
                distTo[dest] = distTo[src] + edge.getWeight();
                edgeTo[dest] = edge;
                
                if (!onQueue[dest]) 
                {
                    queue.add(dest);
                    onQueue[dest] = true;
                }
            }
            
            if (cost++ % graph.getVertexTotal() == 0) 
            {
                findNegativeCycle();
                
                if (hasNegativeCycle()) 
                	// found a negative cycle
                	return;  
            }
        }
    }

    
    public boolean hasNegativeCycle() 
    {
        return cycle != null;
    }

    
    public Iterable<dedge> negativeCycle() 
    {
        return cycle;
    }

    
    // find negative cycle by finding a cycle in predecessor graph
    private void findNegativeCycle() 
    {
        int vertices = edgeTo.length;
        CreateGraph spt = new CreateGraph(vertices);
        
        for (int v = 0; v < vertices; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);

        FindCycle finder = new FindCycle(spt);
        cycle = finder.cycle();
    }

    
    public double distTo(int src) 
    {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists!");
        
        return distTo[src];
    }

    
    public boolean hasPathTo(int v) 
    {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    
    public Iterable<dedge> pathTo(int v) 
    {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists!");
        
        if (!hasPathTo(v)) 
        	return null;
        	
        ArrayList<dedge> path = new ArrayList<dedge>();
        
        for (dedge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.getSource()]) 
            path.add(edge);
        
        return path;
    }
    
    
    private boolean check(CreateGraph graph, int src) 
    {

        // has a negative cycle
        if (hasNegativeCycle()) 
        {
            double weight = 0.0;
            
            for (dedge edge : negativeCycle()) 
                weight += edge.getWeight();
            
            if (weight >= 0.0) 
            {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        }

        // no negative cycle reachable from source
        else 
        {
            if (distTo[src] != 0.0 || edgeTo[src] != null) 
            {
                System.err.println("distanceTo[src] and edgeTo[src] inconsistent");
                return false;
            }
            
            for (int v = 0; v < graph.getVertexTotal(); v++)
            {
                if (v == src) 
                	continue;
                
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) 
                {
                    System.err.println("distTo[v] and edgeTo[v] inconsistent");
                    return false;
                }
            }

            for (int v = 0; v < graph.getVertexTotal(); v++) 
            {
                for (dedge edge : graph.adj(v)) 
                {
                    int dest = edge.getDest();
                    
                    if (distTo[v] + edge.getWeight() < distTo[dest]) 
                    {
                        System.err.println("error: edge " + edge + " not relaxed");
                        return false;
                    }
                }
            }

            for (int w = 0; w < graph.getVertexTotal(); w++) {
                
            	if (edgeTo[w] == null) 
                	continue;
                
                dedge tempEdge = edgeTo[w];
                int v = tempEdge.getSource();
                
                if (w != tempEdge.getDest()) 
                	return false;
                
                if (distTo[v] + tempEdge.getWeight() != distTo[w]) 
                {
                    System.err.println("error: edge " + tempEdge + " on shortest path not tight");
                    return false;
                }
            }
        }

        System.out.println("Satisfies optimality conditions.\n");
        return true;
    }
}