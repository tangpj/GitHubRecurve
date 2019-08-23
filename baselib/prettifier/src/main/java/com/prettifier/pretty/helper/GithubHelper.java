package com.prettifier.pretty.helper;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prettifier.pretty.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.tangpj.recurve.util.UiUtilsKt.getColorByAttr;

/**
 * Created by Kosh on 25 Dec 2016, 9:12 PM
 */

public class GithubHelper {

    @NonNull public static String generateContent(
            @NonNull Context context, @NonNull String source,
            boolean dark) {
        return mergeContent(context, getParsedHtml(source), false);
    }

    @NonNull private static String parseReadme(@NonNull String source, boolean isWiki) {
        return getParsedHtml(source);
    }

    @NonNull private static String getParsedHtml(@NonNull String source) {
        Document document = Jsoup.parse(source, "");
        Elements imageElements = document.getElementsByTag("img");
        if (imageElements != null && !imageElements.isEmpty()) {
            for (Element element : imageElements) {
                String src = element.attr("src");
                if (src != null && !(src.startsWith("http://") || src.startsWith("https://"))) {
                    String finalSrc;
                    finalSrc = "https://raw.githubusercontent.com/" +  src;
                    element.attr("src", finalSrc);
                }
            }
        }
        Elements linkElements = document.getElementsByTag("a");
        if (linkElements != null && !linkElements.isEmpty()) {
            for (Element element : linkElements) {
                String href = element.attr("href");
                if (href.startsWith("#") || href.startsWith("http://") || href.startsWith("https://") || href.startsWith("mailto:")) {
                    continue;
                }
                element.attr("href",  href);
            }
        }
        return document.html();
    }

//    @NonNull private static String getLinkBaseUrl(@NonNull String baseUrl) {
//        Uri uri = Uri.parse(baseUrl);
//        ArrayList<String> paths = new ArrayList<>(uri.getPathSegments());
//        StringBuilder builder = new StringBuilder();
//        boolean containsMaster = paths.size() > 3 && paths.get(2).equalsIgnoreCase("blob");
//        if (!containsMaster) {
//            builder.append("blob/master/");
//        }
//        for (String path : paths) {
//            if (!path.equalsIgnoreCase(uri.getLastPathSegment())) {
//                builder.append(path).append("/");
//            }
//        }
//        return builder.toString();
//    }

    @NonNull private static String mergeContent(@NonNull Context context, @NonNull String source, boolean dark) {
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\"/>" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"" + getStyle(dark) + "\">\n" +
                "\n" + getCodeStyle(context, dark) + "\n" +
                "    <script src=\"./intercept-hash.js\"></script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" + source +
                "\n<script src=\"./intercept-touch.js\"></script>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }

    @NonNull private static String getStyle(boolean dark) {
        return dark ? "./github_dark.css" : "./github.css";
    }

    @NonNull private static String getCodeStyle(@NonNull Context context, boolean isDark) {
        if (!isDark) return "";
        String primaryColor = getCodeBackgroundColor(context);
        String accentColor = "#" + Integer.toHexString(getColorByAttr(context, R.attr.colorAccent)).substring(2).toUpperCase();
        return "<style>\n" +
                "body .highlight pre, body pre {\n" +
                "background-color: " + primaryColor + " !important;\n" +
                "}\n" +
                "</style>";
    }

    @NonNull private static String getCodeBackgroundColor(@NonNull Context context) {
        if (false) {
            return "#" + Integer.toHexString(getColorByAttr(context, R.attr.colorPrimaryDark)).substring(2).toUpperCase();
        }
        return "#" + Integer.toHexString(getColorByAttr(context, R.attr.colorPrimary)).substring(2).toUpperCase();
    }
}