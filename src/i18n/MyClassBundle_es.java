package i18n;

import java.util.ListResourceBundle;

public class MyClassBundle_es extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "salary"   , new Double(91000.70) },
            { "hello", "Hola" },
            { "goodbye", "adiós" },
    };
}