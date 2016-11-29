

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainClassResult {
	private static String printList(Set<String> s)
	{
		String print="";
		for(String e:s)
		{
			print+=e;
			print+=",";
		}
		return print;
	}
	public static void printResult(List<Set<String>> predicted,List<Set<String>> actual) throws IOException
	{
		FileWriter resultWriter=new FileWriter("../result/predictResult.html");
		String begin="<html><head></head><body><h1>Test Data</h1><table border=\"1\">\n";
		String end="</table></body></html>\n";
		String firstColumnFormat="<tr><td>%s</td>\n";
		String format="<td>%s</td>\n";
		String lastColumnFormat="<td>%s</td></tr>\n";
		resultWriter.write(begin);
		resultWriter.write(String.format(firstColumnFormat,"id"));
		resultWriter.write(String.format(format, "actual topic"));
		resultWriter.write(String.format(lastColumnFormat,"predict topic"));
		for(int i=0;i<predicted.size();i++)
		{
			resultWriter.write(String.format(firstColumnFormat,i));

			resultWriter.write(String.format(format, printList(actual.get(i))));
			resultWriter.write(String.format(lastColumnFormat, printList(predicted.get(i))));
			resultWriter.write("</tr>\n");
		}
		resultWriter.write(end);
		resultWriter.close();
	}
	public static void main(String[] args) throws IOException
	{
		long build_start=System.currentTimeMillis();
		List<AssociationRule> rules=FileParser.parseRule("../result/ruleInput");
		List<Set<String>> testData=FileParser.putToSet("../result/testData");
		List<Set<String>> actual=FileParser.putToSet("../result/actual");
		List<Set<String>> predicted=new ArrayList<>();
		CBAClassifier cba=new CBAClassifier(rules);
		long build_end=System.currentTimeMillis();
		double build_time=(build_end-build_start)/1000.0;
		System.out.println("Time to build CBA classifier: "+build_time+"s.");
		long online_start=System.currentTimeMillis();
		for(int i=0;i<testData.size();i++)
		{
			predicted.add(cba.ClassifyData(testData.get(i),10));
		}
		long online_end=System.currentTimeMillis();
		double online_time=(online_end-online_start)/1000.0;
		System.out.println("Online Time: "+online_time+"s.");
		double accuracy=UtilClass.accuracyJaccard(predicted, actual);
		printResult(predicted,actual);
		System.out.println("The accuracy is:"+accuracy);
	}
}
