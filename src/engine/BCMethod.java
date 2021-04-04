package engine;

import java.util.ArrayList;


public class BCMethod extends SearchMethod {
	
	private ArrayList<Clause> hClauses;
	private ArrayList<String> basicFacts;
	private String askStatement;

	private ArrayList<String> usedVariables = new ArrayList<String>();
	private ArrayList<String> frontier = new ArrayList<String>();
	
	public BCMethod(String ask, KnowledgeBase kb) {
    super(ask,kb);
		setShortMethodName("BC");
		setFullMethodName("Backward Chaining Search");	

	    hClauses = kb.getClauses();
		basicFacts = kb.getFacts();
		askStatement = ask;
		//start the search from the goal fact
		frontier.add(askStatement);
	}
	
	@Override
	public String printResult()
	{
		String print = "";
		
		// check if the search succeeded or failed
		if (Solve() == true)
		{
			// if so, output YES:
			print = "YES: ";
			
			// as well as each fact that is discovered
			for ( int i = usedVariables.size() - 1; i >= 0; i-- )
			{ 
				if (i != 0) {
					print += (usedVariables.get(i));
					print += ", ";
				}
				else if (i == 0)
				{
					print += (usedVariables.get(i));
				}
			}
		}
		else
		{
			// else output "NO: (Query) could not be proven"
			print = "NO: Not enough information to prove " + askStatement + ".";
		}
		return print;
	}

	@Override
	public boolean Solve(){
		boolean result = true;

		// while there are still queries to be proven
		while ( frontier.size() > 0 )
		{
			// take the first query off of queries to be checked 
			String currentVar = frontier.remove(0);

			// add to used variable list which will be used to output to the terminal
			usedVariables.add(currentVar);

			boolean isFact = false;
			boolean isClause = false;

			//check if the current query is a fact
			for ( int i = 0; i < basicFacts.size(); i++ )
			{
				if (currentVar.compareTo(basicFacts.get(i)) == 0)
				{
					isFact = true;
				}
			}
			//if not, check if it is a clause
			if (isFact == false) {

				for ( int i = 0; i < hClauses.size(); i++ )
				{
					if (currentVar.compareTo(hClauses.get(i).getRightVar()) == 0)
					{
						isClause = true;

						for ( int j = 0; j < hClauses.get(i).leftVarSize(); j++ )
						{
							frontier.add( hClauses.get(i).getLeftVarAtIndex(j) );
						}
					}
				}

				//BUG FIX: remove solved variables in the queue to avoid duplication
				for ( int i = 0; i < usedVariables.size(); i++ )
				{
					for ( int j = 0; j < frontier.size(); j++ )
					{
						if ( usedVariables.get(i).compareTo(frontier.get(j)) == 0 )
						{
							frontier.remove(j);
						}
					}
				}

				if (isClause == false)
				{
					// if still didn’t find a match then the search has failed 
					result = false;
					break;
				}
			}
		}
		// when frontier is empty, it means it has tracked back to the original which means the search
		return result;
	}
}