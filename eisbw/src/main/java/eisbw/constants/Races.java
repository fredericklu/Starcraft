package eisbw.constants;

import java.util.ArrayList;
import java.util.List;

public class Races {

  private static List<String> races;

  private Races() {
    //Static class.
  }
  
  /**
   * Get a list from all racenames
   * 
   * @return racenames.
   */
  public static List<String> getRaceList() {
    if (races == null) {
      races = new ArrayList<>(7);
      races.add("terran");
      races.add("protoss");
      races.add("zerg");
      races.add("random");
      races.add("randomtp");
      races.add("randomtz");
      races.add("randompz");
      races.add("test");
    }
    return races;
  }

}
