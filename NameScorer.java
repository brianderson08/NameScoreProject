import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Name{
	
	int baseScore = 0; //score before being multiplied by position within the list
	int adjustedScore = 0; //will be calculated after initialization
	String nameString;
	public static DecimalFormat df2 = new DecimalFormat( "#,###,###,###");
	
	public Name(){}
	public Name(String givenName){
		nameString = givenName;
		for(int i = 0; i < nameString.length(); i++){
			baseScore += Character.toLowerCase(nameString.charAt(i))-96; //offsets ASCII value. Converted letter to lowercase so the ASCII values are consistent and capitalization does not matter.
		}
	}
	public static void sortStringBubble(String x[])
    {
          int j;
          boolean flag = true;
          String temp;

          while (flag)
          {
                flag = false;
                for (j = 0;j < x.length - 1;j++)
                {
                        if(x[j].compareToIgnoreCase(x[j+1]) > 0)
                        { 
                                    temp = x[ j ];
                                    x[ j ] = x[ j+1];
                                    x[ j+1] = temp; 
                                    flag = true;
                         } 
                 } 
          } 
    } 
	
	public String getName(){
		return nameString;
	}
	public int getBaseScore(){
		return baseScore;
	}
	public void setAdjustedScore(int position){
		adjustedScore = (position) * baseScore;
	}
	public int getAdjustedScore(){
		return adjustedScore;
	}
}

public class NameScorer extends Name{
	
	public static void main(String[] args) {
		
		ArrayList<String> nameList = new ArrayList<String>();
		Scanner reader = null;
		int highestScoreIndex = 0;
		int lowestScoreIndex = 0;
		
		try{
			reader = new Scanner(new File("names.txt")); // opens names file
		}
		catch(IOException e){
			System.out.print(e);
		}
		while(reader.hasNext()){
			nameList.add(reader.next()); // populates nameList
		}
		String[] nameArray = nameList.toArray(new String[nameList.size()]); // used an ArrayList to allow for input without needing to know size of list, then converted to array once done populating said ArrayList
		
		System.out.println("\nReceived list of unsorted names: " + Arrays.toString(nameArray)); // print list of names to make sure it was interpreted correctly
		sortStringBubble(nameArray);
		System.out.println("Alphabetically sorted list of names: " + Arrays.toString(nameArray)); // print sorted list to make sure it was sorted correctly
		
		Name[] scoresList = new Name[nameArray.length];
		
		fillScoreList(scoresList, nameArray);
		adjustScores(scoresList);
		
		highestScoreIndex = findHighestScore(scoresList);
		lowestScoreIndex = findLowestScore(scoresList);
		printResults(nameArray, highestScoreIndex, lowestScoreIndex, scoresList);
		
	}
	private static void printResults(String[] givenNameArray, int givenHighestScoreIndex, int givenLowestScoreIndex, Name[] givenScoresList){
		System.out.println("\nThe highest scoring name is " + givenNameArray[givenHighestScoreIndex] + " (" + df2.format(givenScoresList[givenHighestScoreIndex].getAdjustedScore()) + " points) at position " + (givenHighestScoreIndex+1) + " out of " + givenNameArray.length + " names.");
		System.out.println("\nThe lowest scoring name is " + givenNameArray[givenLowestScoreIndex] + " (" + df2.format(givenScoresList[givenLowestScoreIndex].getAdjustedScore()) + " points) at position " + (givenLowestScoreIndex+1) + " out of " + givenNameArray.length + " names.");
		System.out.println("\nThe average name score is " + df2.format(getScoreAverage(givenScoresList)) + " points.");
		System.out.println("\nThe total score of all names is: " + df2.format(getTotalScore(givenScoresList)) + " points.");
	}
	private static void fillScoreList(Name[] givenScoresList, String[] givenNameArray){
		for(int i = 0; i < givenNameArray.length; i++){
			Name newName = new Name(givenNameArray[i]);
			givenScoresList[i] = newName;
		}
	}
	private static void adjustScores(Name[] givenScoresList){
		for(int i = 0; i < givenScoresList.length; i++){
			givenScoresList[i].setAdjustedScore(i+1); // the "plus one" is because positions are offset. the "0" position is actually the 1st, the "1" position is actually the 2nd, and so on
		}
	}
	private static int findHighestScore(Name[] givenScoresList){
		int highestScore = 0;
		int highestScoreIndex = 0;
		for(int i = 0; i < givenScoresList.length; i++){
			if(highestScore < givenScoresList[i].getAdjustedScore()){
				highestScore = givenScoresList[i].getAdjustedScore();
				highestScoreIndex = i;
			}
		}
		return highestScoreIndex;
	}
	private static int findLowestScore(Name[] givenScoresList){
		int lowestScore = givenScoresList[0].getAdjustedScore();
		int lowestScoreIndex = 0;
		
		for(int i = 1; i < givenScoresList.length; i++){
			if(lowestScore > givenScoresList[i].getAdjustedScore()){
				lowestScore = givenScoresList[i].getAdjustedScore();
				lowestScoreIndex = i;
			}
		}
		return lowestScoreIndex;
	}
	private static int getScoreAverage(Name[] givenScoresList){
		int totalScore = 0;
		for(int i = 0; i < givenScoresList.length; i++){
			totalScore += givenScoresList[i].getAdjustedScore();
		}
		return totalScore/givenScoresList.length;
	}
	private static int getTotalScore(Name[] givenScoresList){
		int totalScore = 0;
		for(int i = 0; i < givenScoresList.length; i++){
			totalScore += givenScoresList[i].getAdjustedScore();
		}
		return totalScore;
	}
}
