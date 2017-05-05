import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main 
{
	
    public static void main(String s[]) 
    {
       
    	BufferedReader br = null;
    	String[] currencies = new String[32];
    	try {
    		br = new BufferedReader(new FileReader("Currencies.txt"));
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	try {//reads currencies.txt to create array list currencies
    		int i = 0;
    		String line1 = br.readLine();	//skipping the first line is necessary
    		line1 = br.readLine();
    		
    		while(line1 != null)
			{
				currencies[i] = line1;
				System.out.println(currencies[i]);
				line1 = br.readLine();
				i++;
			}
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	CreateGraph graph = new CreateGraph(currencies.length);
    	System.out.println("Building Graph...");
    	
    	for(int i = 0; i < currencies.length; i++)
    	{
	    	try {
	        URL exch = new URL("http://api.fixer.io/latest?base="+(currencies[i]));
	        //reads online exchange rates for each currency in currencies array
	        //ex. {"base":"USD","date":"2017-04-28","rates":{"AUD":1.3384,"BGN":1.7894,"BRL":3.1764,"CAD":1.3645....
	        //exchange rates are from the European central bank and are updated once a day at around 4 PM Central time
	        //full info of API -> http://fixer.io/
	        URLConnection yc = exch.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	        
	        String inputLine;
	        String[] temp = null;
	        String[] tamp = null;
	        String[] tomp = null;
	        Float rate = null;
	        
	        while ((inputLine = in.readLine()) != null)
	        {
	        	temp = inputLine.split("\\{"); 			//splits currency value output by the { brackets
	        	temp[2] = temp[2].replaceAll("}}","");  //eliminates the }} at the end
	        	tamp = temp[2].split(","); 				//creates array of currency and value as individual strings 
	        		 
	        	for(int y = 0; y < tamp.length; y++)
	        	{
	        		tomp = tamp[y].split(":");
	        		rate = Float.parseFloat(tomp[1]); 	//and an array of their values
	        			
	        		if(i == y)
	        		{
	        			dedge e1 = new dedge(i, y , (float) -Math.log(1));
	        			graph.addEdge(e1);
	        			System.out.println(e1.toString());
	        				
	        			for(int z = y; z < 32; z++)
	        			{
	    	        		tomp = tamp[z].split(":");
	    	        		rate = Float.parseFloat(tomp[1]); //and an array of their values
	    	        		dedge e2 = new dedge(i, z+1 , (float) -Math.log(rate));
		        			graph.addEdge(e2);
		        			System.out.println(e2.toString());
	        			}
	        		}
	        			
	        		else
	        		{
	        			dedge edge = new dedge(i, y, (float) -Math.log(rate));
	        			graph.addEdge(edge);
	        			System.out.println(edge.toString());
	        				
	        			if(i == 31 && y == 30)
	        			{
	        				dedge e3 = new dedge(i, 31 , (float) -Math.log(1));
		        			graph.addEdge(edge);
		        			System.out.println(e3.toString());
	        			}
	        		}
	        	}
	    	}
	        
	        in.close();
	        
	    	} catch(Exception e) {
	            System.out.println(e);
	        }
    	}
    	
	    BellmanFord spt = new BellmanFord(graph, 0);
	    
	    if (spt.hasNegativeCycle()) 
	    {
	        double stake = 10000.0;
	        
	        for (dedge edge : spt.negativeCycle()) 
	        {
	        	System.out.printf("%10.5f %s ", stake, currencies[edge.getSource()]);
	            stake *= Math.exp(-edge.getWeight());
	            System.out.printf("= %10.5f %s\n", stake, currencies[edge.getDest()]);
	        }
	    }
	    
	    else 
	    {
	        System.out.println("No arbitrage opportunity");
	    }
    }
}