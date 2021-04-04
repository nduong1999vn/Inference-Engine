package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TTMethod extends SearchMethod {
	
	private final ArrayList<Clause> hClauses;
	private final ArrayList<String> basicFacts;
	private final String askStatement;

	private final ArrayList<String> varList = new ArrayList<String>();
	private final int y; // number of columns
	private final int x; // number of rows
	private final boolean[][] table;
	private final boolean[] rowValue;
	private final int[][] leftVar;
	private final int[] fact;
	private final int[] rightVar;
	private final boolean[] askStatementValue;
	private int askLocation = 0;

	public TTMethod(final String ask, final KnowledgeBase kb) {
		super(ask, kb);

		setShortMethodName("TT");
		setFullMethodName("Truth Table Search");

		hClauses = kb.getClauses();
		basicFacts = kb.getFacts();
		askStatement = ask;

		leftVar = new int[hClauses.size()][2];
		rightVar = new int[hClauses.size()];
		fact = new int[basicFacts.size()];

		//initialize a list of variables 
		initializeVarList();
		//method to remove duplicate values in the array
		RemoveArrayDuplicate(varList);

		//number of column = number of variables 
		y = varList.size();

		//number of rows = 2 ^ (number of variables)
		x = (int) Math.pow(2, (varList.size()));

		//initialize truth table parameter
		table = new boolean[x][y];
		
		//assign all possible truth table values to the parameter
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				final int v = i & 1 << y - 1 - j;

				table[i][j] = (v == 0 ? true : false);
			}
		}

		// a final column that determines the result of each row
		rowValue = new boolean[x];

		//default row value = true
		for (int i = 0; i < x; i++) {
			rowValue[i] = true;
		}

		//set the index location for all variables
		FactsLocation();
		LeftVariablesLocation();
		RightVariablesLocation();
		AskStatementLocation();

		//set value for all ask statements
		askStatementValue = new boolean[x];
		for (int i = 0; i < x; i++) {
			askStatementValue[i] = table[i][askLocation];		
		}
	}

	@Override
	public String printResult() {
		String print = "";

		if (Solve() == true) {
			print = "YES: " + CountTrueRows();
		}
		else {
			print = "NO: Not enough information to prove " + askStatement + ".";
		}

		return print;
	}

	@Override
	public boolean Solve() {
		//check if asked statement if false
		//if yes then the row is false
		CheckGoalFactValue();

		//check if any fact is false
		//if yes then the row is false
		CheckFactsValue();


		// now check the value of every variables in each clauses for every row
		for (int i = 0; i < x; i++) {
			if (rowValue[i] == true) { //default row value is true. so we only check true row values
				for (int j = 0; j < leftVar.length; j++) {
					if (hClauses.get(j).leftVarSize() == 1) {
						if ((table[i][leftVar[j][0]] == true) && (table[i][rightVar[j]] == false)) {
							rowValue[i] = false;
						}
					} else {
						if ((table[i][leftVar[j][0]] == true) && (table[i][leftVar[j][1]] == true) && (table[i][rightVar[j]] == false)) {
							rowValue[i] = false;
						}
					}
				}
			}
		}

		//check if the seach has succeeded or failed
		boolean result = CheckFinalResult();

		return result;
	}

	// add every variables from every clauses into a list	
	public void initializeVarList() {
		
		for (int i = 0; i < hClauses.size(); i++) {
			//add right variables
			varList.add(hClauses.get(i).getRightVar());

			//add left variables
			if (hClauses.get(i).leftVarSize() == 1) {
				varList.add(hClauses.get(i).getLeftVarAtIndex(0));
			}
			else {
				varList.add(hClauses.get(i).getLeftVarAtIndex(0));
				varList.add(hClauses.get(i).getLeftVarAtIndex(1));
			}
		}
	}
	//assign location of the facts to the fact param
	public void FactsLocation()
	{
		for ( int i = 0; i < basicFacts.size(); i++ ) {
			for ( int j = 0; j < varList.size(); j++ ) {
				if ( basicFacts.get(i).compareTo( varList.get( j )) == 0 ) {
					fact[i] = j;
				}
			}
		}
	}

	public void AskStatementLocation()
	{
		for ( int j = 0; j < varList.size(); j++ ) {
			//assign location of the ask statement to the askLocation param
			if (askStatement.compareTo(varList.get(j )) == 0 ) {
				askLocation = j;
			}	
		}
	}

	//assign location of the left variables of clauses to the leftVar param
	public void LeftVariablesLocation()
	{
		for ( int i = 0; i < hClauses.size(); i++) {
			for (int j = 0; j < varList.size(); j++) {
				for ( int z = 0; z < hClauses.get(i).leftVarSize( ); z++ ) {
					if (hClauses.get(i).getLeftVarAtIndex(z).compareTo(varList.get(j)) == 0) {
						leftVar[i][z] = j;
					}
				}
			}
		}
	}

	//assign location of the right variables of clauses to the rightVar param
	public void RightVariablesLocation()
	{
		for ( int i = 0; i < hClauses.size(); i++) {
			for (int j = 0; j < varList.size(); j++) {
				if ( hClauses.get(i).getRightVar().equals(varList.get(j))) {
					rightVar[i] = j;
				}
			}
		}
	}

	//method to count numbers of True rows to output
	public int CountTrueRows() {
		int c = 0;

		for (int i = 0; i < x; i++) {
			if (rowValue[i]) {
				c++;
			}
		}

		return c;
	}

	//method to remove similar values in an array
	public ArrayList<String> RemoveArrayDuplicate(ArrayList<String> array) {
		final Set<String> set = new HashSet<String>();

		for (final String num : array) {
		set.add(num);
		}
		array.clear();
		array.addAll(set);

		return array;
	}

	public void CheckGoalFactValue() { 
		for (int i = 0; i < x; i++) {
			if (rowValue[i]) { //default row value is true. so we only check true row values
				// if the ask value is false so the row is also false
				if (askStatementValue[i] == false) {
					rowValue[i] = false;
				} 
			}
		}
	}

	public void CheckFactsValue() { 
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < fact.length; j++) {
				if (rowValue[i]) { //default row value is true. so we only check true row values
					if (askStatementValue[i] == true) {
						rowValue[i] = table[i][fact[j]];
					}
				}
			}
		}
	}

	public boolean CheckFinalResult() {
		boolean result = true;
		for (int i = 0; i < x; i++) {
			//if any row is true but its ask statement is false => search failed
			if (askStatementValue[i] == false && rowValue[i] == true) {
				result = false;
			}
		}
		return result;
	}
}