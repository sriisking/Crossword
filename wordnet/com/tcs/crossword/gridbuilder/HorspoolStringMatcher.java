package com.tcs.crossword.gridbuilder;

public class HorspoolStringMatcher
{
    private static char[] p;       // pattern, text
	private static char[] t;
    private static int m;          // pattern length, text length
	private static int n;
    private static int alphabetsize=256;
    private static int[] occ;         // occurence function
    private static String matches;    // string of match positions
    private static char[] showmatches;// char array that shows matches
    private static String name="Horspool";

    public HorspoolStringMatcher()
    {
        occ=new int[alphabetsize];
    }
    
//	public static void main(String[] args)
//	{
//		occ=new int[alphabetsize];
//		String text = "the current priorities in the list are not here";
//		String key = "the";
//		search(text,key);
//      System.out.println(text);
//      System.out.println(key);
//      System.out.println(showmatches);
//      System.out.println(getMatches());
//	}
       
    /** searches the text tt for the pattern pp
     */ 
    private static void search(String tt, String pp)
    {
        setText(tt);
        setPattern(pp);
        horspoolSearch();
    }

    /** sets the text
     */ 
    private static void setText(String tt)
    {
        n=tt.length();
        t=tt.toCharArray();
        initmatches();
    }
    
    /** sets the pattern
     */ 
    private static void setPattern(String pp)
    {
        m=pp.length();
        p=pp.toCharArray();
        horspoolInitocc();
    }

    /** initializes match positions and the array showmatches
     */ 
    private static void initmatches()
    {
        matches="";
        showmatches=new char[n];
        for (int i=0; i<n; i++)
            showmatches[i]=' ';
    }
    
    /** computation of the occurrence function
     */ 
    private static void horspoolInitocc()
    {
        int a, j;

        for (a=0; a<alphabetsize; a++)
            occ[a]=-1;

        for (j=0; j<m-1; j++)
        {
            a=p[j];
            occ[a]=j;
        }
    }

    /** searches the text for all occurences of the pattern
     */ 
    private static void horspoolSearch()
    {
        int i=0, j;
        while (i<=n-m)
        {
            j=m-1;
            while (j>=0 && p[j]==t[i+j])
                j--;
            if (j<0) report(i);
            i+=m-1;
            i-=occ[t[i]];
        }
    }

    /** reports a match
     */
    private static void report(int i)//instead set flag
    {
        matches+=i+" ";
        showmatches[i]='^';
    }

    /** returns the match positions after the search
     */ 
    public static String getMatches()
    {
        return matches;
    }

    // only for test purposes
    public static void main(String[] args)
    {
        HorspoolStringMatcher stm=new HorspoolStringMatcher();
        System.out.println(name);
        String tt, pp;
        tt="abcdabcd";
        pp="abc";
        stm.search(tt, pp);
        System.out.println(pp);
        System.out.println(tt);
        System.out.println(stm.showmatches);
        System.out.println(stm.getMatches());
    }
}    
