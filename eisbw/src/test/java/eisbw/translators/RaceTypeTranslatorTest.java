package eisbw.translators;

import static org.junit.Assert.assertEquals;

import eis.eis2java.exception.TranslationException;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import jnibwapi.types.RaceType;
import jnibwapi.types.RaceType.RaceTypes;

import org.junit.Before;
import org.junit.Test;

public class RaceTypeTranslatorTest {

  private RaceTypeTranslator translator;

  @Before
  public void start() {
    translator = new RaceTypeTranslator();
  }

  @Test(expected = TranslationException.class)  
  public void translateException_test() throws TranslationException {
    translator.translate(new Numeral(0));
  }

  @Test(expected = TranslationException.class)  
  public void translateExceptionNotFound_test() throws TranslationException {
    translator.translate(new Identifier("notFound"));
  }

  @Test
  public void translate_test() throws TranslationException {
    assertEquals(RaceTypes.Terran ,translator.translate(new Identifier("terran")));
    assertEquals(RaceTypes.Zerg ,translator.translate(new Identifier("zerg")));
    assertEquals(RaceTypes.Protoss ,translator.translate(new Identifier("protoss")));
  }

  @Test
  public void translatesTo_test() {
    assertEquals(RaceType.class, translator.translatesTo());
  }

}
