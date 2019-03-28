package i18n;

import java.util.ListResourceBundle;

public class MyClassBundle_jp extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "salary"   , new Double(95000.80) },
            { "hello", "今日は" },
            { "goodbye", "さようなら" },
    };

}
