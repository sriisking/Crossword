package com.tcs.crossword.gridbuilder;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import edu.smu.tspell.wordnet.*;
import java.io.PrintStream;
import java.util.ArrayList;


public class GenerateClue
{

//    public GenerateClue()
//    {
//    }

    public static void main(String args[])
    {
        int leng = 7;
        Hashtable ht_index = new Hashtable();
        FindWord findw = new FindWord();
        String s = findw.GetWord(leng, ht_index);
        StringBuffer wordclue = getclue(s);
        System.out.print(wordclue);
    }

    public static StringBuffer getclue(String s)
    {
        if(s.equals(""))
            return new StringBuffer("");
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset synsets[] = database.getSynsets(s);
        int code[] = new int[synsets.length];
        for(int i = 0; i < synsets.length; i++)
        {
            SynsetType sst = synsets[i].getType();
            code[i] = sst.getCode();
        }

        int randomvalue = getrandomval(synsets.length);
        String clue = "";
        if(code[randomvalue] == 1)
            clue = getClueNoun(synsets[randomvalue]);
        else
        if(code[randomvalue] == 2)
            clue = getClueVerb(synsets[randomvalue]);
        else
        if(code[randomvalue] == 3)
            clue = getClueAdjective(synsets[randomvalue]);
        else
        if(code[randomvalue] == 4)
            clue = getClueAdverb(synsets[randomvalue]);
        else
            clue = getClueAdjective(synsets[randomvalue]);
        return new StringBuffer((new StringBuilder(String.valueOf(clue))).append(" ").append("(").append(s.length()).append(")").toString());
    }

    private static int getrandomval(int max)
    {
        return (int)(Math.random() * (double)max);
    }

    private static String getClueNoun(Synset word)
    {
        String clue = "";
        NounSynset nounsynset = (NounSynset)word;
        ArrayList validclues = new ArrayList();
        if(nounsynset.getAntonyms(word.getWordForms()[0]).length != 0)
        {
            int index = getrandomval(nounsynset.getAntonyms(word.getWordForms()[0]).length);
            validclues.add((new StringBuilder("opposite of ")).append(nounsynset.getAntonyms(word.getWordForms()[0])[index].getWordForm()).toString());
        }
        if(nounsynset.getDefinition().length() != 0)
            validclues.add(nounsynset.getDefinition());
        if(nounsynset.getHypernyms().length != 0)
        {
            int index = getrandomval(nounsynset.getHypernyms().length);
            validclues.add((new StringBuilder("an instance of ")).append(nounsynset.getHypernyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getHyponyms().length != 0)
        {
            int index = getrandomval(nounsynset.getHyponyms().length);
            validclues.add((new StringBuilder("a special kind of this is ")).append(nounsynset.getHyponyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getMemberHolonyms().length != 0)
        {
            int index = getrandomval(nounsynset.getMemberHolonyms().length);
            validclues.add((new StringBuilder("has member ")).append(nounsynset.getMemberHolonyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getMemberMeronyms().length != 0)
        {
            int index = getrandomval(nounsynset.getMemberMeronyms().length);
            validclues.add((new StringBuilder("has member ")).append(nounsynset.getMemberMeronyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getPartHolonyms().length != 0)
        {
            int index = getrandomval(nounsynset.getPartHolonyms().length);
            validclues.add((new StringBuilder("part of ")).append(nounsynset.getPartHolonyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getPartMeronyms().length != 0)
        {
            int index = getrandomval(nounsynset.getPartMeronyms().length);
            validclues.add((new StringBuilder("has part ")).append(nounsynset.getPartMeronyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getRegionMembers().length != 0)
        {
            int index = getrandomval(nounsynset.getRegionMembers().length);
            validclues.add((new StringBuilder("is part of ")).append(nounsynset.getRegionMembers()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getRegions().length != 0)
        {
            int index = getrandomval(nounsynset.getRegions().length);
            validclues.add((new StringBuilder("belongs to ")).append(nounsynset.getRegions()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getSubstanceHolonyms().length != 0)
        {
            int index = getrandomval(nounsynset.getSubstanceHolonyms().length);
            validclues.add((new StringBuilder("part of ")).append(nounsynset.getSubstanceHolonyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getSubstanceMeronyms().length != 0)
        {
            int index = getrandomval(nounsynset.getSubstanceMeronyms().length);
            validclues.add((new StringBuilder("has substance ")).append(nounsynset.getSubstanceMeronyms()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getTopicMembers().length != 0)
        {
            int index = getrandomval(nounsynset.getTopicMembers().length);
            validclues.add((new StringBuilder("includes ")).append(nounsynset.getTopicMembers()[index].getWordForms()[0]).append(" as a domain term").toString());
        }
        if(nounsynset.getTopics().length != 0)
        {
            int index = getrandomval(nounsynset.getTopics().length);
            validclues.add((new StringBuilder("a domain term from ")).append(nounsynset.getTopics()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getUsageExamples().length != 0)
        {
            int index = getrandomval(nounsynset.getUsageExamples().length);
            validclues.add(nounsynset.getUsageExamples()[index]);
        }
        if(nounsynset.getUsageMembers().length != 0)
        {
            int index = getrandomval(nounsynset.getUsageMembers().length);
            validclues.add((new StringBuilder("a usage member is ")).append(nounsynset.getUsageMembers()[index].getWordForms()[0]).toString());
        }
        if(nounsynset.getUsages().length != 0)
        {
            int index = getrandomval(nounsynset.getUsages().length);
            validclues.add((new StringBuilder("this usage is ")).append(nounsynset.getUsages()[index].getWordForms()[0]).toString());
        }
        clue = validclues.get(getrandomval(validclues.size())).toString();
        return clue;
    }

    private static String getClueAdjective(Synset word)
    {
        String clue = "";
        AdjectiveSynset adjsynset = (AdjectiveSynset)word;
        ArrayList validclues = new ArrayList();
        if(adjsynset.getAntonyms(word.getWordForms()[0]).length != 0)
        {
            int index = getrandomval(adjsynset.getAntonyms(word.getWordForms()[0]).length);
            validclues.add((new StringBuilder("opposite of ")).append(adjsynset.getAntonyms(word.getWordForms()[0])[index].getWordForm()).toString());
        }
        if(adjsynset.getDefinition().length() != 0)
            validclues.add(adjsynset.getDefinition());
        if(adjsynset.getRegions().length != 0)
        {
            int index = getrandomval(adjsynset.getRegions().length);
            validclues.add((new StringBuilder("belongs to ")).append(adjsynset.getRegions()[index].getWordForms()[0]).toString());
        }
        if(adjsynset.getRelated().length != 0)
        {
            int index = getrandomval(adjsynset.getRelated().length);
            validclues.add((new StringBuilder("related to ")).append(adjsynset.getRelated()[index].getWordForms()[0]).toString());
        }
        if(adjsynset.getSimilar().length != 0)
        {
            int index = getrandomval(adjsynset.getSimilar().length);
            validclues.add(adjsynset.getSimilar()[index].getWordForms()[0]);
        }
        if(adjsynset.getTopics().length != 0)
        {
            int index = getrandomval(adjsynset.getTopics().length);
            validclues.add((new StringBuilder("a domain term from ")).append(adjsynset.getTopics()[index].getWordForms()[0]).toString());
        }
        if(adjsynset.getUsageExamples().length != 0)
        {
            int index = getrandomval(adjsynset.getUsageExamples().length);
            validclues.add(adjsynset.getUsageExamples()[index]);
        }
        if(adjsynset.getUsages().length != 0)
        {
            int index = getrandomval(adjsynset.getUsages().length);
            validclues.add((new StringBuilder("this usage is ")).append(adjsynset.getUsages()[index].getWordForms()[0]).toString());
        }
        clue = validclues.get(getrandomval(validclues.size())).toString();
        return clue;
    }

    private static String getClueAdverb(Synset word)
    {
        String clue = "";
        AdverbSynset advsynset = (AdverbSynset)word;
        ArrayList validclues = new ArrayList();
        if(advsynset.getAntonyms(word.getWordForms()[0]).length != 0)
        {
            int index = getrandomval(advsynset.getAntonyms(word.getWordForms()[0]).length);
            validclues.add((new StringBuilder("opposite of ")).append(advsynset.getAntonyms(word.getWordForms()[0])[index].getWordForm()).toString());
        }
        if(advsynset.getDefinition() != null)
            validclues.add(advsynset.getDefinition());
        if(advsynset.getRegions().length != 0)
        {
            int index = getrandomval(advsynset.getRegions().length);
            validclues.add((new StringBuilder("belongs to ")).append(advsynset.getRegions()[index].getWordForms()[0]).toString());
        }
        if(advsynset.getTopics().length != 0)
        {
            int index = getrandomval(advsynset.getTopics().length);
            validclues.add((new StringBuilder("a domain term from ")).append(advsynset.getTopics()[index].getWordForms()[0]).toString());
        }
        if(advsynset.getUsageExamples().length != 0)
        {
            int index = getrandomval(advsynset.getUsageExamples().length);
            validclues.add(advsynset.getUsageExamples()[index]);
        }
        if(advsynset.getUsages().length != 0)
        {
            int index = getrandomval(advsynset.getUsages().length);
            validclues.add((new StringBuilder("this usage is ")).append(advsynset.getUsages()[index].getWordForms()[0]).toString());
        }
        clue = validclues.get(getrandomval(validclues.size())).toString();
        return clue;
    }

    private static String getClueVerb(Synset word)
    {
        int randomvalue = 0;
        String clue = "";
        VerbSynset vrbsynset = (VerbSynset)word;
        ArrayList validclues = new ArrayList();
        if(vrbsynset.getPhrases(vrbsynset.getWordForms()[0]).length != 0)
        {
            int index = getrandomval(vrbsynset.getPhrases(vrbsynset.getWordForms()[0]).length);
            validclues.add(vrbsynset.getPhrases(vrbsynset.getWordForms()[0])[index].getWordForm());
        }
        if(vrbsynset.getAntonyms(vrbsynset.getWordForms()[0]).length != 0)
        {
            int index = getrandomval(vrbsynset.getAntonyms(vrbsynset.getWordForms()[0]).length);
            validclues.add((new StringBuilder("opposite of ")).append(vrbsynset.getAntonyms(vrbsynset.getWordForms()[0])[index].getWordForm()).toString());
        }
        if(vrbsynset.getDefinition().length() != 0)
            validclues.add(vrbsynset.getDefinition());
        if(vrbsynset.getUsages().length != 0)
        {
            int index = getrandomval(vrbsynset.getUsages().length);
            validclues.add((new StringBuilder("this usage is ")).append(vrbsynset.getUsages()[index].getWordForms()[0]).toString());
        }
        if(vrbsynset.getEntailments().length != 0)
        {
            int index = getrandomval(vrbsynset.getEntailments().length);
            validclues.add((new StringBuilder("in ")).append(vrbsynset.getEntailments()[index].getWordForms()[0]).toString());
        }
        if(vrbsynset.getUsageExamples().length != 0)
        {
            int index = getrandomval(vrbsynset.getUsageExamples().length);
            validclues.add(vrbsynset.getUsageExamples()[index]);
        }
        if(vrbsynset.getHypernyms().length != 0)
        {
            int index = getrandomval(vrbsynset.getHypernyms().length);
            validclues.add((new StringBuilder("an instance of ")).append(vrbsynset.getHypernyms()[index].getWordForms()[0]).toString());
        }
        if(vrbsynset.getRegions().length != 0)
        {
            int index = getrandomval(vrbsynset.getRegions().length);
            validclues.add((new StringBuilder("belongs to ")).append(vrbsynset.getRegions()[index].getWordForms()[0]).toString());
        }
        if(vrbsynset.getTopics().length != 0)
        {
            int index = getrandomval(vrbsynset.getTopics().length);
            validclues.add((new StringBuilder("a domain term from ")).append(vrbsynset.getTopics()[index].getWordForms()[0]).toString());
        }
        if(vrbsynset.getTroponyms().length != 0)
        {
            int index = getrandomval(vrbsynset.getTroponyms().length);
            validclues.add((new StringBuilder("a special kind of this is ")).append(vrbsynset.getTroponyms()[index].getWordForms()[0]).toString());
        }
        if(vrbsynset.getVerbGroup().length != 0)
        {
            int index = getrandomval(vrbsynset.getVerbGroup().length);
            validclues.add((new StringBuilder("similar to ")).append(vrbsynset.getVerbGroup()[index].getWordForms()[0]).toString());
        }
        clue = validclues.get(getrandomval(validclues.size())).toString();
        return clue;
    }
}