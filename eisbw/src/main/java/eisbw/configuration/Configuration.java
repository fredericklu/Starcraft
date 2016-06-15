package eisbw.configuration;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Danny & Harm - This class handles all the possible configurations.
 *
 */
public class Configuration {

  protected RaceString ownRace = null;
  protected RaceString enemyRace = new RaceString("random");
  protected String map = null;
  protected String scDir = null;
  protected String autoMenu = "OFF";
  protected BooleanString debug = new BooleanString("false");

  /**
   * The Configuration constructor.
   * 
   * @param parameters
   *          The given config parameters.
   * @throws TranslationException
   *           One of the config parameters is not valid.
   */
  public Configuration(Map<String, Parameter> parameters) throws TranslationException {
    parseParams(parameters);
    checkSanity();
  }

  private void parseParams(Map<String, Parameter> parameters) throws TranslationException {
    Translator translator = Translator.getInstance();
    for (Entry<String, Parameter> entry : parameters.entrySet()) {
      ParamEnum param = translator.translate2Java(new Identifier(entry.getKey()), ParamEnum.class);
      switch (param) {
        case DEBUG:
          setDebugMode(translator.translate2Java(entry.getValue(), BooleanString.class));
          break;
        case MAP:
          setMap(translator.translate2Java(entry.getValue(), String.class));
          break;
        case OWN_RACE:
          setOwnRace(translator.translate2Java(entry.getValue(), RaceString.class));
          break;
        case ENEMY_RACE:
          setEnemyRace(translator.translate2Java(entry.getValue(), RaceString.class));
          break;
        case SC_DIR:
          setScDir(translator.translate2Java(entry.getValue(), String.class));
          break;
        case AUTO_MENU:
          setAutoMenu(translator.translate2Java(entry.getValue(), String.class));
          break;
        default:
          // Unreachable clause.
          break;
      }
    }
  }

  private void setEnemyRace(RaceString race) {
    enemyRace = race;
  }

  private void checkSanity() {
    if (ownRace == null || map == null || scDir == null) {
      throw new IllegalStateException(
          "Map, Race " + "and starcraft directory have to be defined in the .mas2g file");
    }
  }

  private void setScDir(String dir) {
    this.scDir = dir;
  }

  private void setDebugMode(BooleanString debug) {
    this.debug = debug;
  }

  private void setMap(String map) {
    this.map = map;
  }

  private void setAutoMenu(String autoMenu) {
    this.autoMenu = autoMenu;
  }

  private void setOwnRace(RaceString race) {
    this.ownRace = race;
  }

  public BooleanString getDebugMode() {
    return debug;
  }

  public String getMap() {
    return map;
  }

  public RaceString getOwnRace() {
    return ownRace;
  }

  public RaceString getEnemyRace() {
    return enemyRace;
  }

  public String getScDir() {
    return scDir;
  }

  public String getAutoMenu() {
    return autoMenu;
  }
}
