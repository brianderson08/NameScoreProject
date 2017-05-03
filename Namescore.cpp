#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

int main()
{
	int finalTotalScore = 0;
	std::ifstream infile("names.txt");
	std::string line;
	int count = 1;
	while (std::getline(infile, line))
	{
	
		int totalScore = 0;
		int tempScore = 0;
		std::istringstream iss(line);
		string word;
		
		if (!(iss >> word)) { break; } 
		for (int i = 0; i<word.length(); i++) {
			int _char = word[i];
			tempScore += (_char - 64);
		}
		
		totalScore = tempScore*count;
		cout << "For " << word << " the score is: " << totalScore << endl;
		finalTotalScore += totalScore;
		
		count++;
	}
	cout << "Final score of all names : " << finalTotalScore << endl;
	system("pause");
	return 0;
}