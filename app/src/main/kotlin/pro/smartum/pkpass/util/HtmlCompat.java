package pro.smartum.pkpass.util;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;

public class HtmlCompat {

    public HtmlCompat() {}

    public static Spanned fromHtml(String source) {
        return VERSION.SDK_INT >= VERSION_CODES.N ? Html.fromHtml(source, 0) : Html.fromHtml(source);
    }

    public static Spanned fromHtml(String source, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        return VERSION.SDK_INT >= VERSION_CODES.N ? Html.fromHtml(source, 0, imageGetter, tagHandler) : Html.fromHtml(source, imageGetter, tagHandler);
    }
}
