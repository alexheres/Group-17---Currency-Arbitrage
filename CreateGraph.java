import java.util.ArrayList;

public class CreateGraph 
{
	private int numOfVertex;        		
    private ArrayList<dedge>[] adjList;
    
    
	@SuppressWarnings("unchecked")
	public CreateGraph(int vertices) 
	{
        if (vertices < 0)
        	throw new IllegalArgumentException("Number of vertices must be positive!");
        
        this.numOfVertex = vertices;
        adjList = (ArrayList<dedge>[]) new ArrayList[vertices];
        
        // keep track of incident vertices via adjacency list 
        for (int v = 0; v < vertices; v++)
            adjList[v] = new ArrayList<dedge>();
    }

	
    public void addEdge(dedge edge) 
    {
    	int v = edge.getSource();
    	
    	// add to beginning of ArrayList
        adjList[v].add(0, edge); 		
    }

    
    public int getVertexTotal() 
    {
        return numOfVertex;
    }

    
    public Iterable<dedge> adj(int v) 
    {
        return adjList[v];
    }
    
}  