# Group-17---Currency-Arbitrage
As of 4/15/2017 the program: 
  - initializes an 8x8 matrix through the creation of a two dimensional array using double pointers
  - reads exchange rates (floating point numbers) from a text file and inserts them into our matrix
  - prints out the matrix with their newly assigned values

As of 4/25/2017 the program:
  - generates a simple weighted graph (there might be bugs associated with the edge weight assignments). Weights are read from a file.
  - no longer uses any of the previous matrix code. This is because of our decision in using a weighted graph in place of a matrix.

As of 5/5/2017 the program:
  - now uses Java instead of C++. This was mainly to utilize the ability to read exhange rate data from an online API.
  - can calculate arbitrage! This is done by constructing a complete directed graph, where every vertex is a currency and every directed edge between two vertices is an exchange rate. The program then searches the complete graph using depth first search to find cycles, and then uses the Bellman Ford algorithm to check if these cycles are negative cycles. If a negative cycle is found, exchanges are made based on this cycle and an arbitrage opportunity is found!
  - can tell if there is an arbitrage opportunity or not. 
