#ifndef GRAPH_H
#define GRAPH_H

#include <iostream>
using namespace std;

struct Node
{
	int dest;
	float edgeWeight;

	struct Node* next;
};

struct MasterNode
{
	struct Node* head;
};

class Graph
{
public:

	Graph(int v);
	Node* createNode(int dest, float weight);
	void addEdge(int src, int dest, float weight);
	void printGraph();

private:

	int numOfVertex;
	struct MasterNode* array;
};

#endif