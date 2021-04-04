package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Engine {

  public static void main(final String[] args) throws IOException {

    //read and store the file data in to a parameter
    BufferedReader caseContent = new BufferedReader(new FileReader(args[0]));
    //store the method used the a parameter
    String method = args[1];

    //analyse the TELL statements with KnowledgeBase class
    KnowledgeBase knowledge = new KnowledgeBase(caseContent);

    //discard the 3rd line of the file
    caseContent.readLine();

    //assign the ASK statement to a parameter
    String askSentence = caseContent.readLine();

    //initialize the search methods
    SearchMethod[] searchMethod = {
    		  new FCMethod(askSentence, knowledge),
    		  new BCMethod(askSentence, knowledge),
          new TTMethod(askSentence, knowledge)
      };
      
    //check which method is called
    if (searchMethod[0].getShortMethodName().compareTo(method) == 0) {
      System.out.println(searchMethod[0].getFullMethodName() + " result:");
      System.out.println(searchMethod[0].printResult());
    } 
    else if (searchMethod[1].getShortMethodName().compareTo(method) == 0) {
      System.out.println(searchMethod[1].getFullMethodName() + " result:");
      System.out.println(searchMethod[1].printResult());
    }
    else if (searchMethod[2].getShortMethodName().compareTo(method) == 0) {
      System.out.println(searchMethod[2].getFullMethodName() + " result:");
      System.out.println(searchMethod[2].printResult());
    }
    else {
      System.out.println("Method " + method + " not found. Select FC, BC or TT! (Case sensitive)");
      System.exit(2);
    }
  }

}
