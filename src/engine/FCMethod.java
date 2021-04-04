package engine;

import java.util.ArrayList;

public class FCMethod extends SearchMethod {
	
	private final ArrayList<Clause> hClauses;
	private final ArrayList<String> basicFacts;
	private final String askStatement;

	private final ArrayList<String> usedVariables = new ArrayList<String>();

	public FCMethod(final String ask, final KnowledgeBase kb) {
		super(ask, kb);
		setShortMethodName("FC");
		setFullMethodName("Forward Chaining Search");

		hClauses = kb.getClauses();
		basicFacts = kb.getFacts();
		askStatement = ask;
	}

	@Override
	public String printResult() {
		String print = "";

		// check if the search succeeded or failed
		if (Solve() == true) {
			//if the search succeeded, print YES:
			print = "YES: ";

			//print all the facts that are used
			for (int i = 0; i < usedVariables.size(); i++) {
				print += (usedVariables.get(i));

				if (i < usedVariables.size() - 1) {
					print += ", ";
				}
			}
		} else {
			// else output "NO: Not enough information to prove (goal)."
			print = "NO: Not enough information to prove " + askStatement + ".";
		}
		return print;
	}

	@Override
	public boolean Solve() {
		boolean result = false;

		//solve all facts until nothing is left in the array
		while (basicFacts.size() > 0) {

			//take the first fact in the list to analyse
			String currentVar = basicFacts.remove(0);
			
			//add to a list of used facts since we will delete the facts used from basicFacts
			usedVariables.add(currentVar);
			 
			//return true if current fact is the goal fact
			if (currentVar.compareTo(askStatement) == 0)
			{
				result = true;
				break;
			}

			//check if the current fact contains in any horn clause given
			for ( int i = 0; i < hClauses.size(); i++ )
			{
				for ( int j = 0; j < hClauses.get(i).leftVarSize(); j++ )
				{
					if ( currentVar.equals( hClauses.get(i).getLeftVarAtIndex(j) ) )
					{
						hClauses.get(i).thisVarProved(currentVar);
					}
				}
			}
			
			//add new facts to the fact list and remove it from the clause list
			for ( int i = 0; i < hClauses.size(); i++ )
			{
				//if this is a fact
				if ( hClauses.get(i).isFact() == true )
				{
					//add to fact list
					basicFacts.add(hClauses.get(i).getRightVar());
					
					//this is now no longer a clause. remove it
					hClauses.remove(i);
				}
			}
		}

		//if the loop ended and no solution found, the search failed. 
		//return false
		return result;
	}
}
