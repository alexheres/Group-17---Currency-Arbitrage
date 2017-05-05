#include <fstream>
#include <vector>
#include "Graph.h"

int main()
{

	//creating a graph with 8 vertices
	Graph graph(8);

	//reading exchange rates into a vector from a file
	vector<float>currencies;
	float num;
	ifstream input;
	input.open("CurrencyList.txt");
	while (!input.eof())
	{
		input >> num;
		currencies.push_back(num);
	}
	input.close();

	//test printing exchange rates from vector
	for (int i = 0; i < currencies.size(); i++)
	{
		cout << "Test printing exchange rate " << i + 1 << ": " << currencies[i] << endl;
	}

	//adding edges between vertex 0 and every other vertex
	for (int i = 1, j = 0; i < 8; i++, j++)
	{
		graph.addEdge(0, i, currencies[j]);
	}
	
	//Edges are generally added like so:
	graph.addEdge(1, 2, 0.8957);
	graph.addEdge(5, 7, 0.9534);
	graph.addEdge(3, 1, 1.1124);
	//...following the format addEdge(sourceNode, destinationNode, Weight);

	//printing graph
	graph.printGraph();

	system("pause");

	return 0;
}
