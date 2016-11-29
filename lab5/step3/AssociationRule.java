

import java.util.HashSet;
import java.util.Set;

public class AssociationRule {
	Set<String> keywords;
	String topicClass;
	double confidence;
	double support;
	public AssociationRule()
	{
		keywords=new HashSet<>();
	}
}
