package eisbw.configuration;

/**
 * @author Danny & Harm - The parameter enums.
 *
 */
public enum ParamEnum {
  MAP("map"), OWN_RACE("own_race"), DEBUG("debug"), SC_DIR("starcraft_location"), AUTO_MENU(
      "auto_menu"), ENEMY_RACE("enemy_race");

  private String parameter;

  private ParamEnum(String name) {
    parameter = name;
  }

  public String getParam() {
    return parameter;
  }
}
