package i18n;

import java.util.ListResourceBundle;

public class MyClassBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "salary"   , new Double(10000.25) },
            { "hello", "back to default label 1" },
            { "goodbye", "back to default label 2" },
    };

}
