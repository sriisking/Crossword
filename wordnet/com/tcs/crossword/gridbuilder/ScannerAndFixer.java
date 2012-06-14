package com.tcs.crossword.gridbuilder;

import java.util.ArrayList;


public class ScannerAndFixer {



	public class ScanData {
		private int x = 0;
		private int y = 0;
		private int length = 0;
		boolean valid = false;
		private final int maxLength = 15;
		private final int minLength = 3;
		//Indicates if the length is less than the minimum allowed length
		private boolean isLessThanMin = false ;

		public ScanData(int x,int y,int length)
		{
			super();
			this.x=x;
			this.y=y;
			this.length=length;
			if(length  < minLength)
			{
				valid = false;
				isLessThanMin = true;
			}
			else if(length > maxLength)
			{
				valid = false;
				isLessThanMin = false;
			}
			else
			{
				valid =true;
			}

		}
		public boolean isSameLocation(int x,int y)
		{
			boolean result =false;

			if(x == this.x && y == this.y )
			{
				result =true;
			}

			return result;
		}
		public boolean equals(java.lang.Object data)
		{
			ScanData scanData = (ScanData)data;
			return isSameLocation(scanData.getX(),scanData.getY());
		}
		public int getMaxLength() {
			return maxLength;
		}
		public int getMinLength() {
			return minLength;
		}
		public boolean isValid() {
			return valid;
		}
		public int getLength() {
			return length;
		}
		public int[] getData()
		{
			return new int[] {x,y,length};
		}
		public String toString()
		{
			return x+","+y+","+" Len = "+ length;
		}
		public boolean isLessThanMin() {
			return isLessThanMin;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}

	}

	//	ScanData[] horizontalScan = null;
	ArrayList<ScanData> columnScan = new ArrayList<ScanData>();
	ArrayList<ScanData> rowScan = new ArrayList<ScanData>();
	private boolean input[][] =null;
	//Marks the valid and invalid boxes
	public boolean[][] marker=null;

	ScannerAndFixer(boolean[][] input)
	{
		this.input = input;
		this.marker = new boolean [input.length][input.length];
		for(int i = 0;i<input.length;i++)
		{
			for(int j = 0;j<input.length;j++)
			{
				marker[i][j]=true;
			}
		}
	}
	/**
	 * Rescans the boolean array and updates the columnScan and rowScan objects
	 */
	public void reScan()
	{
		columnScan =null;
		columnScan = new ArrayList<ScanData>();
		rowScan =null;
		rowScan = new ArrayList<ScanData>();
		scanData();
	}

	public void scanAndFix()
	{
		scanData();
		fixData();
	}

	public void scanData()
	{
		int noOfSq = input.length;


		int x=1,y =1;
		int previousX =1,previousY =1;
		int length =0;

		//Horizontal scan
		for(x=1;x<noOfSq+1;x++)
		{
			previousY =1;
			previousX = x;
			length = 0;
			for(y=1;y<noOfSq+1;y++)
			{
				if(input[x-1][y-1])
				{
					length++;
				}
				else
				{
					if(length>0)
					{
						ScanData colScan =new ScanData(previousX,previousY,length);
						columnScan.add(colScan);
					}
					length = 0;
					previousY = y+1;
				}
			}
			if(length>0)
			{
				columnScan.add(new ScanData(previousX,previousY,length));
			}
		}

		//Vertical scan
		for(y=1;y<noOfSq+1;y++)
		{
			previousX =1;
			previousY = y;
			length =0;
			for(x=1;x<noOfSq+1;x++)
			{
				if(input[x-1][y-1])
				{
					length++;
				}
				else
				{
					if(length>0)
					{
						ScanData rScan = new ScanData(previousX,previousY,length);
						rowScan.add(rScan);
					}
					length = 0;
					previousX =x+1;
				}
			}
			if(length>0)
			{
				rowScan.add(new ScanData(previousX,previousY,length));
			}

		}
		//check for index out of bound

	}

	private void fixData()
	{

		for(int iterations=1;iterations<3;iterations++ )
		{
			fixShorterRowsNCols();
			//			fixLongerRowsNCols();
		}

	}
	private void fixShorterRowsNCols()
	{
		//Scan for less length violation
		for(int i = 0; i<rowScan.size();i++)
		{
			ScanData data = rowScan.get(i);
			if(!data.isValid())
			{
				if(data.isLessThanMin())
				{
					if(this.columnScan.contains(data))
					{
						if(columnScan.get(this.columnScan.indexOf(data)).isLessThanMin())
						{
							if(data.getLength()==1)
							{
								input[data.getX()-1][data.getY()-1]=false;
								int otherY = this.input.length-data.getY();
								int otherX = this.input.length-data.getX();
								input[otherX][otherY]=false;
							}
							else
							{
								//Make all the boxes black
								for(int d=data.getX();d<data.getX()+data.getLength();d++)
								{
									if(isIsolatedBox(d, data.getY(),data.getMinLength()))
									{
										input[d-1][data.getY()-1] = false;
										int otherY = this.input.length-data.getY();
										int otherX = this.input.length-d;
										input[otherX][otherY] = false;

										reScan();

									}
								}
							}
						}
					}
				}
			}
		}

	}
	/**
	private void fixLongerRowsNCols()
	{
		//		Scan for more length violation
		for(int i = 0; i<rowScan.size();i++)
		{
			ScanData data = rowScan.get(i);
			if(!data.isValid())
			{
				//!data.isLessThanMin() means the length is more than valid length
				if(!data.isLessThanMin())
				{
					//check which box can be made black
					for(int j = data.getX()+data.getMinLength();j<=data.getX()+data.getLength();j++)
					{
						int newXForBlackBox =   j;
						int otherY = this.input.length-newXForBlackBox+1;
						int otherX = this.input.length-data.getY()+1;

						if(isValidLocation(newXForBlackBox, data.getY(), data.getMinLength()) )
						{
							input[newXForBlackBox-1][data.getY()-1] = false;

							//make the symmetrically opposite box also false
							input[otherX][otherY] = false;

							reScan();
							break;
						}
					}
				}
			}
		}

	}

	/**
	 * Checks if the specified location is valid to put a black box 
	 * @return
	 */
	private boolean isValidLocation(int x, int y,int minLength)
	{
		//check if the element exists in the input array
		try{
			@SuppressWarnings("unused")
			boolean dummy = this.input[x-1][y-1];
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		//Check minLength boxes on left
		int counter =0;
		minLength++;

		try
		{
			for(int a=x-1;a >= x-minLength;a--)
			{
				if(!input[a][y-1])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}
		if(!(counter > 1 && counter >= minLength))
		{
			return false;
		}


		//Check minLength boxes on right
		counter =0;
		try
		{
			for(int a=x-1;a <= x+minLength;a++)
			{
				if(!input[a][y-1])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}

		if(!(counter > 1 && counter >= minLength))
		{
			return false;
		}
		//Check minLength boxes on top
		counter =0;
		try
		{
			for(int a=y-1;a >= y-minLength;a--)
			{
				if(!input[x-1][a])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}
		if(!(counter > 1 && counter >= minLength))
		{
			return false;
		}

		//Check minLength boxes on bottom
		counter =0;
		try
		{
			for(int a=y-1;a <= y+minLength;a++)
			{
				if(!input[x-1][a])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}

		if(!(counter > 1 && counter >= minLength))
		{
			return false;
		}
		return true;
	}

	/**
	 * Return false only if there is space of more than MinLength on any side 
	 * @param x
	 * @param y
	 * @param minLength
	 * @return
	 */
	private boolean isIsolatedBox(int x, int y,int minLength)
	{
		boolean result =true;
		//Check minLength boxes on left
		int counter =0;
		int horizLength =0;
		int vertLength =0;

		try
		{
			for(int a=x-1;a >= x-1-minLength;a--)
			{
				if(!input[a][y-1])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}
		horizLength = counter;


		//Check minLength boxes on right
		counter =0;
		try
		{
			for(int a=x-1;a <= x-1+minLength;a++)
			{
				if(!input[a][y-1])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}

		horizLength = horizLength + counter -1;

		if(horizLength>minLength)
		{
			return false;
		}
		//Check minLength boxes on top
		counter =0;
		try
		{
			for(int a=y-1;a >= y-1-minLength;a--)
			{
				if(!input[x-1][a])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}
		vertLength = counter;

		//Check minLength boxes on bottom
		counter =0;
		try
		{
			for(int a=y-1;a <= y-1+minLength;a++)
			{
				if(!input[x-1][a])
				{
					break;
				}
				counter++;
			}
		}
		catch( java.lang.ArrayIndexOutOfBoundsException e)
		{
			//Do nothing
		}

		vertLength = vertLength + counter;
		if(vertLength>minLength)
		{
			return false;
		}
		return result;
	}

	public boolean[][] getInput() {
		return input;
	}

}
