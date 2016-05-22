package eisbw.configuration;

import static org.junit.Assert.assertEquals;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eisbw.translators.ParamEnumTranslator;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationTest {

  @Before
  public void start() {
    Translator.getInstance().registerParameter2JavaTranslator(new ParamEnumTranslator());
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
    parameters.put("debug", new Identifier("debug"));
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception4_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("race", new Identifier("race"));
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void exception5_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("starcraft_location", new Identifier("scdir"));
    new Configuration(parameters);
  }
  
  @Test(expected = IllegalStateException.class)  
  public void noException_test() throws NoTranslatorException, TranslationException {
    Map<String,Parameter> parameters = new HashMap<>();
    parameters.put("debug", new Identifier("debug"));
    parameters.put("race", new Identifier("race"));
    parameters.put("map", new Identifier("map"));
    parameters.put("starcraft_location", new Identifier("scdir"));
    Configuration config = new Configuration(parameters);
    assertEquals("debug",config.getDebugMode());
    assertEquals("race",config.getRace());
    assertEquals("map",config.getMap());
    assertEquals("scdir",config.getScDir());
  }

}
