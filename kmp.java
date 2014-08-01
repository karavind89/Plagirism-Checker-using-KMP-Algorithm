
/** 
 * Plagiarism Detector using KMP Algorithm(Linear Time String Matching Algorithm) implemented in JAVA
 * Using the degeneration property of the KMP, common patterns were identified and duplication is detected
 * It is Efficient for processing very large files because it minimizes the total number of comparisons of the pattern against the input string
 * 
 * ******************
 * Runtime Analysis:*
 * ******************
 * 
 * O(m) - It is to compute the prefix function values.
 * O(n) - It is to compare the pattern to the text.
 * Total Runtime : O(n + m)
 * 
 * Author : ARAVIND KRISHNAKUMAR
 * 
 * Date Submitted : 12/12/2013 
 * 
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class class1
{
	private int partial_match_length=0;
	int f=0;
	int temp=0;
	int length=100000;
	int index=-1;
	
	int Plag_Sent_Count=0; // Number of Sentences detected as plagiarism
		 
	String[] suf = new String[length]; //Array of Suffix values 
	String[] pre = new String[length]; //Array of Prefix values
	int[] partial_match_table=new int[length]; //Partial Match Table
	
	/** Module to convert the String to Characters */

	public Character[] tochararr(String s) 
	{
		if (s == null) 
		{
			return null;
		}
		Character[] array = new Character[s.length()];

		for (int i = 0; i < s.length(); i++) 
		{
			array[i] = new Character(s.charAt(i));
		}
		return array;
	}
	
	/** Module to Calculate the Prefix and Suffix values for the PATTERN file*/
	
	void presufcalc(Character a2[], int m) 
	{
		/** PREFIX CALCULATION */
		
		int j = 0;
		int count = 0;

		pre[j] = Character.toString(a2[j]);

		for (int i = 0, k3 = 1; i < m - 1; i++) 
		{
			if (m == 1)
				continue;
			pre[i + 1] = pre[i] + Character.toString(a2[k3]);
			k3++;
		}

		/******************************
		 * PRINTING THE PREFIX VALUES *
		 ******************************
		 *  
		 *  System.out.println("\nPrefix values are :"); 
		 *  
		 *  for(int i=0;i<m-1;i++) 
		 *  {
		 *  System.out.println(pre[i]); 
		 *  }
		 *  
		 */
		 
		
		/** SUFFIX CALCULATION */
		
		int j1 = 0;
		int k1 = m - 1;

		if (m - 1 >= 1)
			suf[j1] = Character.toString(a2[k1]);

		for (int i = 0; i < m - 2; i++) 
		{
			k1--;
			suf[i + 1] = Character.toString(a2[k1]) + suf[i];
		}
		
		/******************************
		 * PRINTING THE SUFFIX VALUES *
		 ******************************
		 * 
		 *   System.out.println("\nSuffix values are :"); 
		 *	  
		 *	  for(int i=0;i<m;i++) 
		 *	  {
		 *	  if(suf[i]!=null)
		 *		 System.out.println(suf[i]); 
		 *   }
		 *	  
		 *	 System.out.println("**************************************************");  
		 */ 
		 
		
		/** Variable count = Number of Similar values in Prefix and Suffix */
		
			for (int i = 0; i < m; i++)
			{
				for (j = 0; j < m; j++)
					{
						if (pre[i].equals(suf[j]))
							count++;
					}
				}
		
			if (count >= 1)
			{
			 /** Variable W is the PARTIAL MATCH LENGTH 
			  *  Variable a is the index for the Array*/
				partial_match_length++;
				index++;	
			}
			else 
			{
				partial_match_length = 0;
				index++;
			}
			
			/** Partial Match Table is constructed 
			 *  SKIP values are calculated */	
			partial_match_table[index]=partial_match_length;
				
			
	}
			
			/**
			 * This module builds the Partial Match Table for the Pattern file
			 * @param pattern_char - Character array of pattern file
			 */
	
			void findmatch(Character pattern_char[]) 
			{
				int k1 = pattern_char.length;
				boolean alreadyExecuted = false;
				int i, k;
				
				/**ITERATING THROUGH THE ALL THE VALUES AND 
					   BUILDING THE PARTIAL MATCH TABLE */
				
				for (k = 0; k < k1; k++) 
				{
					for (i = 0; i < k + 1; i++) 
					{
						if (!alreadyExecuted) 
						{
							presufcalc(pattern_char, k + 1);
							alreadyExecuted = true;
						}

					}
					alreadyExecuted = false;
				}
			}
			
			/**
			 * Comparing the Pattern file and the Test files to check the Plagirism factor
			 * 
			 * @param a3 is the pattern file
			 * @param a4 is the test file
			 */
			
			void kmp(Character[] testfile_char,Character[] pattern_char)
			{
				int n=testfile_char.length;
				int k=0;
				int c=0;
						
				/** Comparing the pattern and test files */
				
				for(int i=0,j=0;i<n;)
				{
					if(pattern_char[j].equals(testfile_char[i]))
					{
					/** k = Number of similar characters in pattern and test files*/
						k++;
					/** i and j are the counter values for iterating through Strings*/
						j++;
						i++;		
					}
				
					else
					{
						
					/** Skipping characters by using the Partial Match Table */
						
						if(k!=0)
						{
					/** Applying the formula 
					 *  partial_match_length - table[partial_match_length - 1]
					 *  where k = partial_match_length
					 *  	  k1 = temporary value 	 
					 */
						temp=partial_match_table[k-1];					
						f=k-temp;
						}
						else
						{
							f=1;
						}
						
						i=c+f;
						j=0;
						k=0;
						c=i;
					}
					
					if(k==pattern_char.length)
					{
			/*		System.out.println("String found");
					System.out.println("Position where it started "+((i-j)+1));
					System.out.println("Position where it ends "+i);
					System.out.println("\n");	*/
					
					/** Variable Plag_Sent_Count = Plagirised Sentence Count */
					Plag_Sent_Count++;
					j=0;
					k=0;
					c = i;
					}	
				}
				
			}
			
			/** Module to print the Plagiarism results 
			 *  1) Whether the test file is Plagiarised or not
			 *  2) Percentage of Plagiarism involved 
			 */
			
			void print(int d,String file,String file1)
			{
					int threshold_value=((Plag_Sent_Count*100)/d);
			
			if (threshold_value>=50) /**THRESHOLD VALUE IS 50 OVER HERE*/
			{
				System.out.println("Total number of sentences: "+d);
				System.out.println("Number of sentences matched: "+Plag_Sent_Count);
				System.out.println(file+" is "+threshold_value+" % Plagarised with "+file1);
				System.out.println("\n***************************************");
				Plag_Sent_Count=0;
				}
			
			else
			{
				System.out.println("Total number of sentences: "+d);
				System.out.println("Number of sentences matched: "+Plag_Sent_Count);	
				System.out.println(file+" is not Plagarised with "+file1);
				System.out.println("\n***************************************");
				Plag_Sent_Count=0;
			}
			}
	
}

class kmp
{
	public static void main(String args[]) throws FileNotFoundException
	{
		class1 c1=new class1();
		Character[] char_file1;
		Character[] char_pat;
		
		/**
		 * Test files and pattern files location
		 */
		
		Scanner in = new Scanner(System.in);
		 
	    System.out.println("Enter a Test file directory path : ");
		
		String test_file1=in.nextLine();
		
		Scanner in1 = new Scanner(System.in);
		System.out.println("Enter a pattern file directory path : ");
		
		String pattern_file=in1.nextLine();
				
		String str_file1 = "";
		String str_pat ="";
		
		
		FileReader f=new FileReader(test_file1);
			
	    BufferedReader fileReader = new BufferedReader(f);
	    
	    FileReader f1=new FileReader(pattern_file);
		
	    BufferedReader fileReader1 = new BufferedReader(f1);
	    
	    
	    try 
	    {
	        while (fileReader.ready()) 
	        {
	            str_file1 += (char) fileReader.read();
	        }

	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
	    
	    try 
	    {
	        while (fileReader1.ready()) 
	        {
	        	str_pat += (char) fileReader1.read();

	        }
	     } 
	     catch (IOException e) 
	     {   
	          e.printStackTrace();
	     }
	    
	    /**
	     * The String is split based on the Full stop (.) and stored in a string array
	     */
	    
	    String[] split_pat  = str_pat.split("\\.");
	    String[] split1 = str_file1.split("\\.");
	    
	    int d1=split1.length;
	    
	    /**
	     * String is converted into characters
	     */
	    
	    char_file1 = c1.tochararr(str_file1);
		
		/**
		 * Test Files are compared with the pattern files
		 */
	
		for(int i=0;i<split_pat.length;i++)
		{
			char_pat = c1.tochararr(split_pat[i]);	
			c1.findmatch(char_pat);
			
			c1.kmp(char_file1, char_pat);
		}
		c1.print(d1,test_file1,pattern_file);
		
		
	}
}

	