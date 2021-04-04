package engine;

public abstract class SearchMethod {
  private String shortName;
  private String fullName;
  
  public SearchMethod(String ask, KnowledgeBase kb) {

  }

  abstract public String printResult();
  
  abstract public boolean Solve();
  
  //get the shortform name of the search method
  public String getShortMethodName()
  {
    return shortName;
  }
  
  //set a shortform name to the search method
  protected void setShortMethodName(String sName)
  {
    shortName = sName;
  }
  
  //get the full name of the search method
  public String getFullMethodName()
  {
    return fullName;
  }
  
  //set a fullname to the search method
  protected void setFullMethodName(String fName)
  {
    fullName = fName;
  }
}