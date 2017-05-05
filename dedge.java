public class dedge 
{
	private final int src;
    private final int dest;
    private final float weight;
    
    public dedge(int i, int y, float weight)
    {
        this.src = i;
        this.dest = y;
        this.weight = weight;
    }
    
    
    public int getSource() 
    {
        return src;
    }
    
    
    public int getDest() 
    {
        return dest;
    }
    
    
    public float getWeight() 
    {
        return weight;
    }
    
    
    public String toString() 
    {
        return src + "->" + dest + " "+ weight;	//String.format("%5.2f", weight);
    }
    
}

