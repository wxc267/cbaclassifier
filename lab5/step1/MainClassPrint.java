

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainClassPrint {
	private static final int SPLIT=80;
	private static void printToFile(List<Map<String,Double>> tfidfList,List<Set<String>> topicList,int split) throws IOException
	{

		Set<String> topicSet=new HashSet<>();//in case of duplicated class labels
		FileWriter transactionWriter=new FileWriter("../step2/transcation");
		FileWriter classWriter=new FileWriter("../step2/class");
		classWriter.write("antecedent\n");
		
		for(int i=0;i<split;i++)
		{
			Set<String> keywords=tfidfList.get(i).keySet();
			Set<String> topic=topicList.get(i);
			for(String k : keywords)
			{
				transactionWriter.write(k);
				transactionWriter.write(" ");
			}
			for(String t : topic)
			{
				transactionWriter.write(t);
				transactionWriter.write(" ");
				if(!topicSet.contains(t)){
					classWriter.write(t);
					classWriter.write(" ");
					classWriter.write("consequent\n");
					topicSet.add(t);
				}
			}
			transactionWriter.write("\n");
		}
		transactionWriter.close();
		classWriter.close();
		FileWriter testDataWriter=new FileWriter("../step3/data/testData");
		FileWriter actualWriter=new FileWriter("../step3/data/actual");
		for(int i=split;i<tfidfList.size();i++)
		{
			Set<String> keywords=tfidfList.get(i).keySet();
			Set<String> topic=topicList.get(i);
			for(String k : keywords)
			{
				testDataWriter.write(k);
				testDataWriter.write(" ");
			}
			for(String t : topic)
			{
				actualWriter.write(t);
				actualWriter.write(" ");
			}
			testDataWriter.write("\n");
			actualWriter.write("\n");
		}
		testDataWriter.close();
		actualWriter.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		for(int i=1;i<22;i++){
			String fileName="./data/reut2-"+UtilClass.GetThreeDigitsNumber(i)+".sgm";
			List<ReuterDoc> reuters=FileParser.parse(fileName);
			reuterList.addAll(reuters);
		}
		long start=System.currentTimeMillis();
		WordCounter word=new WordCounter(reuterList);
		List<Map<String,Double>> tfidfList=word.getTFIDFValue();
		List<Set<String>> topicList=word.getActualTopic();
		int split_index=tfidfList.size()*SPLIT/100;
		printToFile(tfidfList,topicList,split_index);
		long end=System.currentTimeMillis();
		double time=(end-start)/1000.0;
		System.out.println("The time of data transformation is: "+time+"s.");
	}
}
