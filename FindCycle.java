import java.util.ArrayList;

public class FindCycle 
{
    private boolean[] marked;             		// marked[vertex] = has vertex been marked?
    private dedge[] edgeTo;        				// edgeTo[vertex] = previous edge on path to vertex
    private boolean[] onStack;            	    // onStack[vertex] = is vertex on the stack?
    private ArrayList<dedge> cycle;    		    // holds directed cycle if one exists

    
    // check for directed cycles within the graph
    public FindCycle(CreateGraph graph) 
    {
        marked  = new boolean[graph.getVertexTotal()];
        onStack = new boolean[graph.getVertexTotal()];
        edgeTo  = new dedge[graph.getVertexTotal()];
        
        for (int v = 0; v < graph.getVertexTotal(); v++)
        {
            if (!marked[v])
            {
            	depthFirstSearch(graph, v);
            }
        }    
        
        assert check();
    }

    
    // depth first search to find a directed cycle
    private void depthFirstSearch(CreateGraph graph, int src) {
        onStack[src] = true;
        marked[src] = true;
        
        for (dedge edge : graph.adj(src)) 
        {
            int dest = edge.getDest();

            // end if directed cycle found
            if (cycle != null) 
            	return;

            // found new vertex, do recursion
            else if (!marked[dest]) 
            {
                edgeTo[dest] = edge;
                depthFirstSearch(graph, dest);
            }

            // trace back directed cycle
            else if (onStack[dest]) 
            {
                cycle = new ArrayList<dedge>();
                dedge tempEdge = edge;
                
                while (tempEdge.getSource() != dest) 
                {
                    cycle.add(tempEdge);
                    tempEdge = edgeTo[tempEdge.getSource()];
                }
                
                cycle.add(tempEdge);
                return;
            }
        }

        onStack[src] = false;
    }

    
    // returns true if the graph has a directed cycle, false otherwise
    public boolean hasCycle() 
    {
        return cycle != null;
    }

    
    // read directed cycles through for-each loops
    public Iterable<dedge> cycle() 
    {
        return cycle;
    }
    
    
    // check graph is either acyclic or has a directed cycle
    private boolean check() 
    {
        if (hasCycle()) 
        {
            dedge first = null, last = null;
            
            for (dedge edge : cycle()) 
            {
            	if (first == null) 
                	first = edge;
               	
            	if (last != null) 
                {
            		if (last.getDest() != edge.getSource()) 
                    {
                        System.err.printf("Error: Edges " + last + " and " + edge + " not incident. Not a cycle.\n");
                        return false;
                    }
                }
            	
                last = edge;
            }

            if (last.getDest() != first.getSource())
            {
                System.err.printf("Error: Edges " + last + " and " + first + " not incident. Not a cycle.\n");
                return false;
            }
        }
        
        return true;
    }
    
}