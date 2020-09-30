/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookAUResultParser;
import com.google.zxing.client.result.AddressBookDoCoMoResultParser;
import com.google.zxing.client.result.BizcardResultParser;
import com.google.zxing.client.result.BookmarkDoCoMoResultParser;
import com.google.zxing.client.result.EmailAddressResultParser;
import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.google.zxing.client.result.ExpandedProductResultParser;
import com.google.zxing.client.result.GeoResultParser;
import com.google.zxing.client.result.ISBNResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductResultParser;
import com.google.zxing.client.result.SMSMMSResultParser;
import com.google.zxing.client.result.SMSTOMMSTOResultParser;
import com.google.zxing.client.result.SMTPResultParser;
import com.google.zxing.client.result.TelResultParser;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIResultParser;
import com.google.zxing.client.result.URLTOResultParser;
import com.google.zxing.client.result.VCardResultParser;
import com.google.zxing.client.result.VEventResultParser;
import com.google.zxing.client.result.VINResultParser;
import com.google.zxing.client.result.WifiResultParser;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ResultParser {
    private static final Pattern AMPERSAND;
    private static final String BYTE_ORDER_MARK = "\ufeff";
    private static final Pattern DIGITS;
    private static final Pattern EQUALS;
    private static final ResultParser[] PARSERS;

    static {
        PARSERS = new ResultParser[]{new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser()};
        DIGITS = Pattern.compile("\\d+");
        AMPERSAND = Pattern.compile("&");
        EQUALS = Pattern.compile("=");
    }

    private static void appendKeyValue(CharSequence charSequence, Map<String, String> map) {
        Object object = EQUALS.split(charSequence, 2);
        if (((String[])object).length != 2) return;
        charSequence = object[0];
        object = object[1];
        try {
            map.put((String)charSequence, ResultParser.urlDecode((String)object));
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    private static int countPrecedingBackslashes(CharSequence charSequence, int n) {
        --n;
        int n2 = 0;
        while (n >= 0) {
            if (charSequence.charAt(n) != '\\') return n2;
            ++n2;
            --n;
        }
        return n2;
    }

    protected static String getMassagedText(Result object) {
        String string2 = ((Result)object).getText();
        object = string2;
        if (!string2.startsWith(BYTE_ORDER_MARK)) return object;
        return string2.substring(1);
    }

    protected static boolean isStringOfDigits(CharSequence charSequence, int n) {
        if (charSequence == null) return false;
        if (n <= 0) return false;
        if (n != charSequence.length()) return false;
        if (!DIGITS.matcher(charSequence).matches()) return false;
        return true;
    }

    protected static boolean isSubstringOfDigits(CharSequence charSequence, int n, int n2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (charSequence == null) return bl2;
        if (n2 <= 0) {
            return bl;
        }
        bl2 = bl;
        if (charSequence.length() < (n2 += n)) return bl2;
        bl2 = bl;
        if (!DIGITS.matcher(charSequence.subSequence(n, n2)).matches()) return bl2;
        return true;
    }

    static String[] matchPrefixedField(String string2, String string3, char c, boolean bl) {
        int n = string3.length();
        ArrayList arrayList = null;
        int n2 = 0;
        block0 : do {
            if (n2 >= n || (n2 = string3.indexOf(string2, n2)) < 0) {
                if (arrayList == null) return null;
                if (!arrayList.isEmpty()) return arrayList.toArray(new String[arrayList.size()]);
                return null;
            }
            int n3 = n2 + string2.length();
            boolean bl2 = true;
            n2 = n3;
            do {
                if (!bl2) continue block0;
                if ((n2 = string3.indexOf(c, n2)) < 0) {
                    n2 = string3.length();
                } else {
                    if (ResultParser.countPrecedingBackslashes(string3, n2) % 2 != 0) {
                        ++n2;
                        continue;
                    }
                    ArrayList arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList(3);
                    }
                    String string4 = ResultParser.unescapeBackslash(string3.substring(n3, n2));
                    arrayList = string4;
                    if (bl) {
                        arrayList = string4.trim();
                    }
                    if (!((String)((Object)arrayList)).isEmpty()) {
                        arrayList2.add(arrayList);
                    }
                    ++n2;
                    arrayList = arrayList2;
                }
                bl2 = false;
            } while (true);
            break;
        } while (true);
    }

    static String matchSinglePrefixedField(String object, String string2, char c, boolean bl) {
        if ((object = ResultParser.matchPrefixedField((String)object, string2, c, bl)) != null) return object[0];
        return null;
    }

    protected static void maybeAppend(String string2, StringBuilder stringBuilder) {
        if (string2 == null) return;
        stringBuilder.append('\n');
        stringBuilder.append(string2);
    }

    protected static void maybeAppend(String[] arrstring, StringBuilder stringBuilder) {
        if (arrstring == null) return;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            stringBuilder.append('\n');
            stringBuilder.append(string2);
            ++n2;
        }
    }

    protected static String[] maybeWrap(String arrstring) {
        if (arrstring != null) return new String[]{arrstring};
        return null;
    }

    protected static int parseHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        int n = 97;
        if (c >= 'a' && c <= 'f') {
            return c - n + 10;
        }
        n = 65;
        if (c < 'A') return -1;
        if (c > 'F') return -1;
        return c - n + 10;
    }

    static Map<String, String> parseNameValuePairs(String arrstring) {
        int n = arrstring.indexOf(63);
        if (n < 0) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>(3);
        arrstring = AMPERSAND.split(arrstring.substring(n + 1));
        int n2 = arrstring.length;
        n = 0;
        while (n < n2) {
            ResultParser.appendKeyValue(arrstring[n], hashMap);
            ++n;
        }
        return hashMap;
    }

    public static ParsedResult parseResult(Result result) {
        ResultParser[] arrresultParser = PARSERS;
        int n = arrresultParser.length;
        int n2 = 0;
        while (n2 < n) {
            ParsedResult parsedResult = arrresultParser[n2].parse(result);
            if (parsedResult != null) {
                return parsedResult;
            }
            ++n2;
        }
        return new TextParsedResult(result.getText(), null);
    }

    protected static String unescapeBackslash(String string2) {
        int n = string2.indexOf(92);
        if (n < 0) {
            return string2;
        }
        int n2 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n2 - 1);
        stringBuilder.append(string2.toCharArray(), 0, n);
        boolean bl = false;
        while (n < n2) {
            char c = string2.charAt(n);
            if (!bl && c == '\\') {
                bl = true;
            } else {
                stringBuilder.append(c);
                bl = false;
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    static String urlDecode(String string2) {
        try {
            return URLDecoder.decode(string2, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalStateException(unsupportedEncodingException);
        }
    }

    public abstract ParsedResult parse(Result var1);
}

