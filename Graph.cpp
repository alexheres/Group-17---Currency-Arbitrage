#include "Graph.h"

Graph::Graph(int v)
{
	this->numOfVertex = v;
	array = new MasterNode[v];
	for (int i = 0; i < v; i++)
		array[i].head = NULL;
}

Node* Graph::createNode(int dest, float weight)
{
	Node* newNode = new Node;
	newNode->dest = dest;
	newNode->edgeWeight = weight;
	newNode->next = NULL;
	return newNode;
}

void Graph::addEdge(int src, int dest, float weight)
{
	Node* node = createNode(dest, weight);
	node->next = array[src].head;
	array[src].head = node;
	node = createNode(src, weight);
	node->next = array[dest].head;
	array[dest].head = node;
}

void Graph::printGraph()
{
	for (int v = 0; v < numOfVertex; v++)
	{
		Node* theNode = array[v].head;
		cout << "\nVertex #" << v << " is adjacent to: \n";
		while (theNode)
		{
			cout << "Vertex " << theNode->dest << "(edge Weight = " << theNode->edgeWeight << "), ";
			theNode = theNode->next;
		}
		cout << endl;
	}
}