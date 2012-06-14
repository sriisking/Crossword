package com.tcs.crossword.gridbuilder;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import sun.font.CreatedFontTracker;

public class CrossowordGenerator
{
	private int length = 0;
	private int percentageBlackBoxes =0;
	private boolean boxes[][] = null;
	private ArrayList doneX =null;
	private static char crosswordchars[][] = null;
	private int noOfBlackBoxes = 0;
	private static char emptychar = '0';
	
	CrossowordGenerator(int crosswordSize)
	{
		length = 0;
		percentageBlackBoxes = 9;
		boxes = null;
		doneX = new ArrayList();
		noOfBlackBoxes = 0;
		length = crosswordSize;
		boxes = new boolean[length][length];
		crosswordchars = new char[length][length];

		for(int x = 0; x < length; x++)
		{
			for(int y = 0; y < length; y++)
				boxes[x][y] = true;

		}

		noOfBlackBoxes = (length * length * percentageBlackBoxes) / 100;
	}

	public static void main(String args[])
	{
		int length = 9;
		Crossword uiAfter =null;
		int counter =0;
		for(;;)
		{
			uiAfter = createCrossword(length);
			counter++;
			if(uiAfter!=null) break;
		}
		System.out.println(counter);
		uiAfter.createAndShowUI();
	}

	public boolean[][] getBoxes()
	{
		return boxes;
	}

	public void setBoxes(boolean boxes[][])
	{
		this.boxes = boxes;
	}

	private void generateBritishStyleCrossword()
	{
		for(int x = 0; x < length; x++)
		{
			for(int y = 0; y < length; y++)
				if((x + 1) % 2 == 0 && (y + 1) % 2 == 0)
					boxes[x][y] = false;

		}

		Random randomGenerator = new Random();
		int counter = 0;
		int yCoOrd = 0;
		int xCoOrd = 0;
		try
		{
			while(counter - 1 < noOfBlackBoxes) 
			{
				if(xCoOrd > length - 1)
					break;
				yCoOrd = randomGenerator.nextInt(length - xCoOrd);
				if(boxes[xCoOrd][yCoOrd])
				{
					boxes[xCoOrd][yCoOrd] = false;
					doneX.add(Integer.valueOf(xCoOrd));
					counter++;
					int otherX = length - xCoOrd - 1;
					int otherY = length - yCoOrd - 1;
					boxes[otherX][otherY] = false;
					doneX.add(Integer.valueOf(otherX));
					counter++;
					xCoOrd++;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void generateRandomNo()
	{
		Random randomGenerator = new Random();
		int counter = 0;
		int yCoOrd = 0;
		int xCoOrd = 0;
		try
		{
			while(counter - 1 < noOfBlackBoxes) 
			{
				if(xCoOrd > length - 1)
					break;
				yCoOrd = randomGenerator.nextInt(length - xCoOrd);
				if(boxes[xCoOrd][yCoOrd])
				{
					if(!doneX.contains(Integer.valueOf(xCoOrd)))
					{
						boxes[xCoOrd][yCoOrd] = false;
						doneX.add(Integer.valueOf(xCoOrd));
						System.out.println((new StringBuilder("number = ")).append(xCoOrd).append(",").append(yCoOrd).toString());
						counter++;
					}
					int otherX = length - yCoOrd - 1;
					int otherY = length - xCoOrd - 1;
					if(boxes[otherX][otherY] && !doneX.contains(Integer.valueOf(otherX)))
					{
						boxes[otherX][otherY] = false;
						doneX.add(Integer.valueOf(otherX));
						System.out.println((new StringBuilder("number = ")).append(otherX).append(",").append(otherY).toString());
						counter++;
					}
					xCoOrd++;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Crossword createCrossword(int length)
	{
		int numbers[][] = new int[length][length];
		CrossowordGenerator c = new CrossowordGenerator(length);
		c.generateBritishStyleCrossword();
		ScannerAndFixer scr = new ScannerAndFixer(c.getBoxes());
		scr.scanAndFix();
		boolean fixedBoxes[][] = scr.getInput();
		scr.reScan();
		boolean isset[][] = new boolean[length][length];
		int startwordcount = 0;
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < length; j++)
				crosswordchars[i][j] = emptychar;

		}

		StringBuffer answeracross = new StringBuffer("ACROSS: ");
		StringBuffer answerdown = new StringBuffer("DOWN: ");
		StringBuffer clueacross = new StringBuffer("ACROSS: ");
		StringBuffer cluedown = new StringBuffer("DOWN: ");
		
		for(int i = 1; i <= length; i++)
		{
			for(int j = 1; j <= length; j++)
			{
				for(int index = 0; index < scr.columnScan.size(); index++)
					if(((ScannerAndFixer.ScanData)scr.columnScan.get(index)).getLength() > 1 && ((ScannerAndFixer.ScanData)scr.columnScan.get(index)).getX() == j && ((ScannerAndFixer.ScanData)scr.columnScan.get(index)).getY() == i)
					{
						startwordcount++;
						numbers[j - 1][i - 1] = startwordcount;
						isset[j - 1][i - 1] = true;
						Hashtable ht_index = new Hashtable();
						for(int n = 0; n < ((ScannerAndFixer.ScanData)scr.columnScan.get(index)).getLength(); n++)
						{
							if(crosswordchars[j - 1][(i - 1) + n] != emptychar)
								ht_index.put(n+1, crosswordchars[j - 1][(i - 1) + n]);
						}

						FindWord findw = new FindWord();
						String s = findw.GetWord(((ScannerAndFixer.ScanData)scr.columnScan.get(index)).getLength(), ht_index);
						cluedown.append("\n");
						cluedown.append(startwordcount);
						cluedown.append(" ");
						cluedown.append(GenerateClue.getclue(s));
						answerdown.append(startwordcount);
						answerdown.append(" ");
						answerdown.append(s);
						answerdown.append(", ");
						for(int n = 0; n < ((ScannerAndFixer.ScanData)scr.columnScan.get(index)).getLength(); n++)
						{
							crosswordchars[j - 1][(i - 1) + n] = s.charAt(n);
						}
					}

				for(int index = 0; index < scr.rowScan.size(); index++)
					if(((ScannerAndFixer.ScanData)scr.rowScan.get(index)).getLength() > 1 && ((ScannerAndFixer.ScanData)scr.rowScan.get(index)).getX() == j && ((ScannerAndFixer.ScanData)scr.rowScan.get(index)).getY() == i)
					{
						if(!isset[j - 1][i - 1])
						{
							startwordcount++;
							numbers[j - 1][i - 1] = startwordcount;
						}
						Hashtable ht_index = new Hashtable();
						for(int n = 0; n < ((ScannerAndFixer.ScanData)scr.rowScan.get(index)).getLength(); n++)
						{
							if(crosswordchars[(j - 1) + n][i - 1] != emptychar)
								ht_index.put(n+1, crosswordchars[(j - 1) + n][i - 1]);
						}

						FindWord findw = new FindWord();
						String s = findw.GetWord(((ScannerAndFixer.ScanData)scr.rowScan.get(index)).getLength(), ht_index);
						if(s.length()<1){return null;}
						clueacross.append("\n");
						clueacross.append(startwordcount);
						clueacross.append(" ");
						clueacross.append(GenerateClue.getclue(s));
						answeracross.append(startwordcount);
						answeracross.append(" ");
						answeracross.append(s);
						answeracross.append(", ");
						for(int n = 0; n < ((ScannerAndFixer.ScanData)scr.rowScan.get(index)).getLength(); n++)
						{
							try{
							crosswordchars[(j - 1) + n][i - 1] = s.charAt(n);
							}
							catch(Exception e)
							{
								return null;
							}
						}
					}

			}

		}

		StringBuffer answerset = new StringBuffer("ANSWER: ");
		answerset.append("\n");
		answerset.append(answeracross);
		answerset.append("\n");
		answerset.append(answerdown);
		
		return new Crossword(length, fixedBoxes, numbers, answerset, clueacross, cluedown);
	}
}