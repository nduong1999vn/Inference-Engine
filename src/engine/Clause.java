package engine;

import java.util.LinkedList;

public class Clause {
  private LinkedList<String> leftVariables = new LinkedList<String>();
  private String rightVariable = new String();

  public Clause(String sentence) {
    leftVariables = new LinkedList<String>();

    //analyse the sentences and split them into variables
    HandleData(sentence);
  }
  
  public void HandleData(String sentence) {
        //split left and right variables
        String[] implicationSplit = sentence.split("=>");
    
        //split left variables
        String[] conjunctionSplit = implicationSplit[0].split("&");
        
        //add found left variables to the array
        for (int i = 0; i < conjunctionSplit.length; i++) {
          leftVariables.add(conjunctionSplit[i]);
        }
        
        //assign found right variable to rightVariable
        rightVariable = implicationSplit[1];
  }

  //get all left variables
  public LinkedList<String> getLeftVar() {
    return leftVariables;
  }
  
  //get a specific left variable 
  public String getLeftVarAtIndex(int i) {
    return leftVariables.get(i);
  }
  
  //return left variable count
  public int leftVarSize() {
    return leftVariables.size();
  }
  
  //remove proved variable on the left side. When all left variables are removed then right variable is now a fact!
  public void thisVarProved(String leftVar) {
    leftVariables.remove(leftVar);
  }

  //check if right variable is now a fact
  public boolean isFact() {
    if (leftVarSize() == 0)
    {
      return true;
    }
    else 
    {
      return false;
    }
  }
  
  //return right variable
  public String getRightVar() {
    return rightVariable;
  }
}
