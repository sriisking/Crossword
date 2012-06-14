package com.tcs.crossword.gridbuilder;


public class Searchstr {

		public static void main(String[] args)
		{
			String text = "the current priorities in the list are not here";
			String key = "the";
			horse(text, text.length(), key, key.length());
		}
		
		private static void horse(String text, int textLength, String key, int keyLength)
		{
			boolean match = false;
			char [] keyArray, textArray;
			
		    int scan = 0;
		    int bad_char_skip[]; /* Officially called:*/
		    int origLen = textLength;

		    keyArray = key.toCharArray();
		    textArray = text.toCharArray();
		    
		    bad_char_skip = new int[256];
		    //PREPROCESSING
		    //This loop initializes the search table to the length
		    //of the pattern being searched
		    for (scan = 0; scan <= 255; scan = scan + 1)
		    {
		        bad_char_skip[scan] = keyLength;
		       // System.out.println("badchar: " + bad_char_skip[scan]);
		    }
		    /* C arrays have the first byte at [0], therefore:
		     * [nlen - 1] is the last byte of the array. */
		    int last = keyLength - 1;

		    /* Then populate it with the analysis of the needle */
		    for (scan = 0; scan < keyLength; ++scan)
		    {
		        bad_char_skip[keyArray[scan]] = last - scan;
		        System.out.println("last: " + last);
		        System.out.println("badchar: " +  bad_char_skip[keyArray[scan]]);
		    }


		    
		    /* ---- Do the matching ---- */

		    /* Search the haystack, while the needle can still be within it. */
		    while (textLength >= keyLength)
		    {
		    //	System.out.println("scan2: " + scan);
		        /* scan from the end of the needle */
		        for (scan = last; textArray[scan] == keyArray[scan]; scan = scan - 1)
		        {
		            if (scan == 0) /* If the first byte matches, we've found it. */
		            {
		                System.out.println("match found");
		               

		            }
		        }
		        
		        textLength    -= bad_char_skip[textArray[last]];
		        text += bad_char_skip[textArray[last]];
		    	
		    	
		    }
		    	
		}
}
