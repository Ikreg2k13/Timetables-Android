package mobile.ikreg.com.mytestapplication.util;

public class ParseHelper {

    public ParseHelper() {

    }

    public static String parseLongToString(int parsedStringLength, long longToParse) {
        String parsedString = Long.toString(longToParse);
        String s = "";
        int nullCount = parsedStringLength - parsedString.length();

        for(int i = 0; i < nullCount; i++) {
            s = s + "0";
        }

        return s + parsedString;
    }
}
