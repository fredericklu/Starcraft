package eisbw.configuration;

import static org.junit.Assert.assertEquals;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eisbw.translators.BooleanStringTranslator;
import eisbw.translators.ParamEnumTranslator;
import eisbw.translators.RaceStringTranslator;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationTest {

  /**
   * register Translators.
   */
  @Before
  public void start() {
    Translator.getInstance().registerParameter2JavaTranslator(new ParamEnumTranslator());
    Translator.getInstance().registerParameter2JavaTranslator(new BooleanStringTranslator());
    Translator.getInstance().registerParameter2JavaTranslator(new RaceStringTranslator());
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception2_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("map", new Identifier("map"));
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception3_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("debug", new Identifier("true"));
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception4_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("own_race", new Identifier("terran"));
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception5_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("starcraft_location", new Identifier("scdir"));
    new Configuration(parameters);
  }
  
  @Test
  public void noException_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("debug", new Identifier("true"));
    parameters.put("own_race", new Identifier("terran"));
    parameters.put("map", new Identifier("map"));
    parameters.put("starcraft_location", new Identifier("scdir"));
    Configuration config = new Configuration(parameters);
    assertEquals("true",config.getDebugMode().getData());
    assertEquals("terran",config.getOwnRace().getData());
    assertEquals("map",config.getMap());
    assertEquals("scdir",config.getScDir());
  }

}
