#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
    float** matrix = new float[8];    //initializes nxn matrix to hold exchange rates
    for(int i = 0; i < 8; i++)
    {
        matrix[i] = new float[8];
    }

    ifstream file;
    file.open("CurrencyTable.txt");
    
    while (!file.eof())                //fills matrix with exchange rates from our txt file
    {
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                file >> matirx[i][j];
            }
        }
    }

    for (int = 0; i < 8; i++)
    {
        for (int j = 0; j < 8; j++)
        {
            cout << matrix[i][j] << " ";
        }
        cout << "\n";
    }
    
    system("pause");
    return 0;

}
