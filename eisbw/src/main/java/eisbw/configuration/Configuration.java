package eisbw.configuration;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.Map;
import java.util.Map.Entry;

public class Configuration {

  protected String race = null;
  protected String map = null;
  protected String scDir = null;
  protected String debug = "false";

  public Configuration(Map<String, Parameter> parameters)
      throws NoTranslatorException, TranslationException {
    parseParams(parameters);
    checkSanity();
  }

  private void parseParams(Map<String, Parameter> parameters)
      throws NoTranslatorException, TranslationException {
    Translator translator = Translator.getInstance();
    for (Entry<String, Parameter> entry : parameters.entrySet()) {
      ParamEnum param = translator.translate2Java(new Identifier(entry.getKey()), ParamEnum.class);
      switch (param) {
        case DEBUG:
          setDebugMode(translator.translate2Java(entry.getValue(), String.class));
          break;
        case MAP:
          setMap(translator.translate2Java(entry.getValue(), String.class));
          break;
        case RACE:
          setRace(translator.translate2Java(entry.getValue(), String.class));
          break;
        case SC_DIR:
          setScDir(translator.translate2Java(entry.getValue(), String.class));
          break;
        default:
          //Unreachable clause.
          break;
      }
    }
  }

  private void checkSanity() {
    if (race == null || map == null || scDir == null) {
      throw new IllegalStateException(
          "Map, Race " + "and starcraft directory have to be defined in the .mas2g file");
    }
  }

  private void setScDir(String dir) {
    this.scDir = dir;
  }

  private void setDebugMode(String debug) {
    this.debug = debug;
  }

  private void setMap(String map) {
    this.map = map;
  }

  private void setRace(String race) {
    this.race = race;
  }

  public String getDebugMode() {
    return debug;
  }

  public String getMap() {
    return map;
  }

  public String getRace() {
    return race;
  }

  public String getScDir() {
    return scDir;
  }
}
