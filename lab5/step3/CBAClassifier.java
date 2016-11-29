

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CBAClassifier {
	private List<AssociationRule> ruleList;
	public CBAClassifier(List<AssociationRule> ruleList)
	{
		this.ruleList=ruleList;
		Collections.sort(this.ruleList, new RuleComparator());
	}
	
	public Set<String> ClassifyData(Set<String> items,int numAcceptRules)
	{
		Set<String> topics=new HashSet<String>();
		int appliedRules=0;
		for(int i=0;i<ruleList.size()&&appliedRules<numAcceptRules;i++)
		{
			AssociationRule candidate_rule=ruleList.get(i);
			Set<String> candidate_key=candidate_rule.keywords;
			if(items.containsAll(candidate_key))
			{
				topics.add(candidate_rule.topicClass);
				appliedRules++;
			}
			
		}
		//if there is no class label for this test data, use the most confident one as its topic label.
		if(topics.size()==0)
		{
			topics.add(ruleList.get(0).topicClass);
		}
		return topics;
	}
	
	public class RuleComparator implements Comparator<AssociationRule>
	{

		@Override
		public int compare(AssociationRule rule1, AssociationRule rule2) {
			if(rule1.confidence>rule2.confidence)
			{
				return 1;
			}
			else if(rule1.confidence<rule2.confidence)
			{
				return -1;
			}else{
				if(rule1.support>rule2.support)
				{
					return 1;
				}else if(rule1.support<rule2.support)
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		}
		
	}
}
