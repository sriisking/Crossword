package com.tcs.crossword.gridbuilder;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.io.*;
import java.util.*;

public class FindWord
{

    public FindWord()
    {
    }

    private void constructWordGazetta()
    {
        try
        {
            String strWordFile = "WordsForSrini.txt";
            File f = new File(strWordFile);
            if(f.exists())
                try
                {
                    BufferedReader in = new BufferedReader(new FileReader(strWordFile));
                    ht = new Hashtable();
                    String str;
                    while((str = in.readLine()) != null && str != "") 
                        if(!ht.containsKey(str))
                            ht.put(str, "");
                    in.close();
                }
                catch(IOException ioexception) { }
            else
                System.out.println("Words File Not Available");
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private Boolean validateLengthAndIndex(Hashtable ht_index, int leng)
    {
        Boolean bReturn = Boolean.valueOf(true);
        ArrayList lstKeys = new ArrayList();
        int str;
        for(Enumeration e = ht_index.keys(); e.hasMoreElements(); lstKeys.add(Integer.valueOf(str)))
            str = ((Integer)e.nextElement()).intValue();

        Collections.sort(lstKeys);
        str = ((Integer)lstKeys.get(lstKeys.size() - 1)).intValue();
        if(str > leng)
            bReturn = Boolean.valueOf(false);
        return bReturn;
    }

    public String GetWord(int leng, Hashtable ht_index)
    {
        constructWordGazetta();
        String strReturn = "";
        ArrayList lstWords = new ArrayList();
        String str_sd = "";
        int str_ht = 0;
        boolean bSatisfy = false;
        for(Enumeration e = ht.keys(); e.hasMoreElements();)
        {
            str_sd = (String)e.nextElement();
            if(str_sd.length() == leng)
            {
                if(ht_index.size() > 0)
                {
                    Enumeration e1 = ht_index.keys();
                    while(e1.hasMoreElements()) 
                    {
                        str_ht = ((Integer)e1.nextElement()).intValue();
                        char ch = ht_index.get(Integer.valueOf(str_ht)).toString().charAt(0);
                        if(str_sd.charAt(str_ht - 1) == ch)
                        {
                            bSatisfy = true;
                            continue;
                        }
                        bSatisfy = false;
                        break;
                    }
                } else
                {
                    lstWords.add(str_sd);
                }
                if(bSatisfy)
                    lstWords.add(str_sd);
            }
        }

        if(lstWords.size() > 0)
            strReturn = pickRandomWord(lstWords);
        return strReturn;
    }

    private String pickRandomWord(ArrayList lstWords)
    {
        Random rd = new Random();
        int i_Random = rd.nextInt(lstWords.size());
        return (String)lstWords.get(i_Random);
    }

    Hashtable ht;
}