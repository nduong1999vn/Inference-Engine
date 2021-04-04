package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class KnowledgeBase {
  private ArrayList<Clause> hornClauses = new ArrayList<Clause>();
  private ArrayList<String> basicFacts = new ArrayList<String>();
  
  public KnowledgeBase(BufferedReader inputFile) throws IOException
  { 
    //analyse the TELL statements 
    HandleData(inputFile);
  }
  
  //analyse the TELL statements 
  public void HandleData(BufferedReader caseContent) throws IOException {

      //discard the first line (TELL string)
      caseContent.readLine();
      
      //assign the TELL statements to a parameter
      String tellSentences = caseContent.readLine();
      
      //remove all spaces between characters because they are not in any order 
      tellSentences = tellSentences.replaceAll("\\s", "");
      
      //split each sentences by the semicolon
      String[] sentenceSplit = tellSentences.split(";");

      for (int i = 0; i < sentenceSplit.length; i++) {
        if (sentenceSplit[i].contains("=>") == false) //if this is a Horn clause
        {
          basicFacts.add(sentenceSplit[i]);
        } 
        else //if not then this is a fact
        {
          hornClauses.add(new Clause(sentenceSplit[i]));
        }
      }
    } 
  
  //return an array of the horn clauses in the KB
  public ArrayList<Clause> getClauses() {
    return hornClauses;
  }
  
  //return an array of facts in the KB
  public ArrayList<String> getFacts() {
    return basicFacts;
  }

}
