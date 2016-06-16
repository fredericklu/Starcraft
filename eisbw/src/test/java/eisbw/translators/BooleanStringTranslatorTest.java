package eisbw.translators;

import static org.junit.Assert.assertEquals;

import eis.eis2java.exception.TranslationException;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eisbw.configuration.BooleanString;

import org.junit.Before;
import org.junit.Test;

public class BooleanStringTranslatorTest {
  private BooleanStringTranslator translator;

  @Before
  public void start() {
    translator = new BooleanStringTranslator();
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
    assertEquals(new BooleanString("false").getData(),
        translator.translate(new Identifier("false")).getData());
    assertEquals(new BooleanString("true").getData(),
        translator.translate(new Identifier("true")).getData());
  }

  @Test
  public void translatesTo_test() {
    assertEquals(BooleanString.class, translator.translatesTo());
  }

}
