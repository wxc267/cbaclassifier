

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class FileParser {

	public static List<AssociationRule> parseRule(String fileName) throws IOException
	{
		List<AssociationRule> ruleList=new ArrayList<AssociationRule>();
		BufferedReader reader=new BufferedReader(new FileReader(fileName));
		String line=reader.readLine();
		while(line!=null)
		{
			AssociationRule rule=new AssociationRule();
			String[] splitedLine=line.split(" ");
			rule.topicClass=splitedLine[0];
			String confidenceString=splitedLine[splitedLine.length-1];
			int end=confidenceString.length()-1;
			confidenceString=confidenceString.substring(0,end);
			double confidence=Double.parseDouble(confidenceString);
			rule.confidence=confidence;
			String supportString=splitedLine[splitedLine.length-2];
			end=supportString.length()-1;
			supportString=supportString.substring(1, end);
			double support=Double.parseDouble(supportString);
			rule.support=support;
			Set<String> keys=new HashSet<String>();
			for(int i=2;i<splitedLine.length-2;i++)
			{
				keys.add(splitedLine[i]);
			}
			rule.keywords=keys;
			ruleList.add(rule);
			line=reader.readLine();
		}
		reader.close();
		return ruleList;
	}
	public static List<Set<String>> putToSet(String fileName) throws IOException
	{
		List<Set<String>> result=new ArrayList<Set<String>>();
		BufferedReader reader=new BufferedReader(new FileReader(fileName));
		String line=reader.readLine();
		while(line!=null)
		{
			String[] words=line.split(" ");
			Set<String> set=new HashSet<String>();
			for(int i=0;i<words.length;i++)
			{
				set.add(words[i]);
			}
			result.add(set);
			line=reader.readLine();
		}
		reader.close();
		return result;
	}
}
