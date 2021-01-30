package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	private ArrayList<String> getWords(String text){
		ArrayList<String> words = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[a-zA-Z.?!]+");
		Matcher m = tokSplitter.matcher(text);
		while (m.find()) {
			words.add(m.group());
		}
		return words;
	}
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText 
	 * @throws Exception */
	@Override
	public void train(String sourceText) 
	{
		if(sourceText.isEmpty()) { 
			System.out.println("emptyy");
			return ;
		
			}
		//System.out.println("here1");
		ArrayList<String> words =getWords(sourceText);
//		Pattern tokSplitter = Pattern.compile("[a-zA-Z]+");
//		Matcher m = tokSplitter.matcher(sourceText);
//		while (m.find()) {
//			words.add(m.group());
//		}
		//System.out.println("words : "+words.toString());

		starter=words.get(0);
		String prevWord=starter;
		boolean exist;
		for (int i=1;i<words.size();i++) {
			exist=false;
			for(int j=0;j<wordList.size();j++) {
				if(wordList.get(j).getWord().equals(prevWord) ) {
					exist=true;
					wordList.get(j).addNextWord(words.get(i));
				}
			}
			if(!exist) {
				ListNode newNode= new ListNode(prevWord);
				wordList.add(newNode);
				newNode.addNextWord(words.get(i));
			}
			prevWord=words.get(i);
		}
		exist=false;
		for(int j=0;j<wordList.size();j++) {
			if(wordList.get(j).getWord().equals(prevWord)) {
				exist=true;
				wordList.get(j).addNextWord(starter);			}
		}
		if(!exist) {
			ListNode newNode= new ListNode(prevWord);
			wordList.add(newNode);
			newNode.addNextWord(starter);
		}
			//wordList.get(wordList.size()-1).addNextWord(starter);
		//System.out.println(wordList.toString() );
		// TODO: Implement this method
		//	List<String>words= d1.getTokens();
		
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		
//		if(wordList.isEmpty()) { 
//				//System.out.println("emty ");
//				throw new  NullPointerException(" empty list cannot generate text ");
//				
//		}
		if(numWords==0||wordList.isEmpty())
			return "";
			String currentWord=wordList.get(0).getWord();
		String output="";
		output=output+currentWord;
		numWords--;
		while(numWords>0) {
			for (int i=0;i<wordList.size();i++)
			{
				if (wordList.get(i).getWord().equals(currentWord)) {
					String x=wordList.get(i).getRandomNextWord(rnGenerator);
					output=output+" "+x;
					currentWord=x;
					numWords--;
					break;
				}
			}
		}
	    // TODO: Implement this method
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
//		if(sourceText.isEmpty())
//			throw new RuntimeException("empty string");
		System.out.println(sourceText);
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
		// TODO: Implement this method.
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
//		String test1="hi there hi Leo";
//		gen.train(test1);
//		System.out.println(gen);
		
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		int x= generator.nextInt(nextWords.size());
		return nextWords.get(x);
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	   
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


