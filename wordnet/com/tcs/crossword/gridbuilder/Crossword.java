package com.tcs.crossword.gridbuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Crossword extends JFrame
{
	private static final long serialVersionUID = 8247054561576646490L;
	private boolean values[][] = null;
	private int[][] numberTags =null;
	private String answerset = null;
	private String cluedown =null;
	private String clueacross =null;

	private int crosswordLength =0;
	private JFrame mainFrame = null;
	private int crosswordWidth =0;
	private int crosswordHeight =0;

	public Crossword(int crosswordLength, boolean values[][], int numbers[][], StringBuffer answerset, StringBuffer clueacross, StringBuffer cluedown)
	{
		mainFrame = new JFrame();
		setTitle("CROSSWORD");
		setResizable(false);
		this.values = values;
		this.crosswordLength = crosswordLength;
		numberTags = numbers;
		this.answerset = new String(answerset);
		this.clueacross = new String(clueacross);
		this.cluedown = new String(cluedown);
	}

	public void createAndShowUI()
	{
		BorderLayout border = new BorderLayout(20, 0);
		setLayout(border);
		add(getCrosswordPanel(), "Center");
		add(getHintsPanel(), "East");
		setDefaultCloseOperation(2);
		pack();
		show();
	}

	private Component getHintsPanel()
	{
		JPanel hintsPanel = new JPanel();
		hintsPanel.setLayout(new BoxLayout(hintsPanel, 1));
		JTextArea across = new JTextArea(clueacross);
		across.setEditable(false);
		across.setLineWrap(true);
		hintsPanel.add(across);
		JTextArea down = new JTextArea(cluedown);
		down.setEditable(false);
		down.setLineWrap(true);
		hintsPanel.add(down);
		JTextArea answer = new JTextArea(answerset);
		answer.setEditable(false);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		hintsPanel.add(answer);
		hintsPanel.setBorder(BorderFactory.createBevelBorder(0));
		JScrollPane scrollpane = new JScrollPane(hintsPanel);
		scrollpane.setVerticalScrollBarPolicy(20);
		scrollpane.setPreferredSize(new Dimension(350, crosswordHeight));
		return scrollpane;
	}

	private JPanel getCrosswordPanel()
	{
		JPanel panel = new JPanel(new SpringLayout());
		for(int i = 0; i < crosswordLength * crosswordLength; i++)
		{
			int x = i % crosswordLength;
			int y = i / crosswordLength;
			CrosswordBox textField = new CrosswordBox("", x, y);
			if(!values[x][y])
			{
				textField.setEditable(false);
				textField.setEnabled(false);
				textField.setBackground(Color.BLACK);
			} else
				if(numberTags[x][y] != 0)
					textField.setNumber(numberTags[x][y]);
			panel.add(textField);
		}

		crosswordWidth = crosswordLength * 38;
		crosswordHeight = crosswordLength * 38;
		SpringUtilities.makeCompactGrid(panel, crosswordLength, crosswordLength, 5, 5, 0, 0);
		panel.setPreferredSize(new Dimension(crosswordWidth, crosswordHeight));
		return panel;
	}




	private class CrosswordBox extends JTextField
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1742725768005689207L;
		int xIndex =0;
		int yIndex =0;
		String number ="";
		CrosswordBox(String a,int xIndex,int yIndex)
		{
			super(a);
			this.xIndex =xIndex;
			this.yIndex =yIndex;
			JTextFieldLimit j = new JTextFieldLimit();
			this.setDocument(j);
			this.setHorizontalAlignment(CENTER);
			this.setFont(new Font("Arial",Font.CENTER_BASELINE,14));
		}
		public void paint(Graphics g)  
		{  
			if(this.isEnabled())
			{

//				setOpaque(false);  

//				//Image that will be set as JTextField background  
//				//I suggest, image that will be use is blur...  
//				//If image is locate at other location, you must put it's full location address.  
//				//For example : C:\\Documents and Settings\\evergreen\\Desktop\\textFieldBackgroundImage.jpg  
//				ImageIcon ii=new ImageIcon("C:\\Users\\admin\\Desktop\\numbers\\1.jpg");  
//				Image i=ii.getImage();  
//				g.drawImage(i,0,0,this);  
				super.paint(g);  
				Font f = new Font("Dialog", Font.HANGING_BASELINE, 9);
				g.setFont(f);
				g.drawString(this.number, 1, 10);
			}
			else
			{
				super.paint(g); 

			}
		}
		public void setNumber(int number) {
			if(number <0)
			{
				this.number = "";
			}
			else
			{
				this.number = Integer.toString(number);
			}
			this.repaint();
		}

	}
	class JTextFieldLimit extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6759692507980565992L;
		private int limit=1;
		JTextFieldLimit() {
			super();
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			if(!Character.isLetter(str.charAt(0)))
			{
				str="";
			}
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str.toUpperCase(), attr);
			}


		}
	}
	//censorWords array contains words that are not allowed
	private boolean censor(String word)
	{
		String[] censorWords={""};
		for(int i=0;i<censorWords.length;i++)
		{
			if(word.contains(censorWords[i]))
			{
				return false;
			}
		}
		return true;
	}
}
