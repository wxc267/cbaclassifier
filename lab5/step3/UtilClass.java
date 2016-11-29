



import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilClass {

	/**
	 * calculate the accuracy of the predicted topics by using Jaccard Similarity
	 * @param predictedList
	 * @param actualList
	 * @return
	 */
	public static double accuracyJaccard(List<Set<String>> predictedList,List<Set<String>> actualList)
	{
		double result=0;
		for(int i=0;i<predictedList.size();i++)
		{
			double Jaccard=0;
			Set<String> predictedSet=predictedList.get(i);
			Set<String> acutalSet=actualList.get(i);
			Set<String> union=new HashSet<>(predictedSet);
			Set<String> intersect=new HashSet<>(predictedSet);
			union.addAll(acutalSet);
			intersect.retainAll(acutalSet);
			Jaccard=intersect.size()*1.0/union.size();
			result+=Jaccard;
		}
		result=result/predictedList.size();
		return result;
	}
}
