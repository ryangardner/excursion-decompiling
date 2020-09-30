/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.text.DateFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FTPClientConfig {
    private static final Map<String, Object> LANGUAGE_CODE_MAP;
    public static final String SYST_AS400 = "AS/400";
    public static final String SYST_L8 = "TYPE: L8";
    public static final String SYST_MACOS_PETER = "MACOS PETER";
    public static final String SYST_MVS = "MVS";
    public static final String SYST_NETWARE = "NETWARE";
    public static final String SYST_NT = "WINDOWS";
    public static final String SYST_OS2 = "OS/2";
    public static final String SYST_OS400 = "OS/400";
    public static final String SYST_UNIX = "UNIX";
    public static final String SYST_UNIX_TRIM_LEADING = "UNIX_LTRIM";
    public static final String SYST_VMS = "VMS";
    private String defaultDateFormatStr = null;
    private boolean lenientFutureDates = true;
    private String recentDateFormatStr = null;
    private boolean saveUnparseableEntries = false;
    private String serverLanguageCode = null;
    private final String serverSystemKey;
    private String serverTimeZoneId = null;
    private String shortMonthNames = null;

    static {
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        LANGUAGE_CODE_MAP = treeMap;
        treeMap.put("en", Locale.ENGLISH);
        LANGUAGE_CODE_MAP.put("de", Locale.GERMAN);
        LANGUAGE_CODE_MAP.put("it", Locale.ITALIAN);
        LANGUAGE_CODE_MAP.put("es", new Locale("es", "", ""));
        LANGUAGE_CODE_MAP.put("pt", new Locale("pt", "", ""));
        LANGUAGE_CODE_MAP.put("da", new Locale("da", "", ""));
        LANGUAGE_CODE_MAP.put("sv", new Locale("sv", "", ""));
        LANGUAGE_CODE_MAP.put("no", new Locale("no", "", ""));
        LANGUAGE_CODE_MAP.put("nl", new Locale("nl", "", ""));
        LANGUAGE_CODE_MAP.put("ro", new Locale("ro", "", ""));
        LANGUAGE_CODE_MAP.put("sq", new Locale("sq", "", ""));
        LANGUAGE_CODE_MAP.put("sh", new Locale("sh", "", ""));
        LANGUAGE_CODE_MAP.put("sk", new Locale("sk", "", ""));
        LANGUAGE_CODE_MAP.put("sl", new Locale("sl", "", ""));
        LANGUAGE_CODE_MAP.put("fr", "jan|f\u00e9v|mar|avr|mai|jun|jui|ao\u00fb|sep|oct|nov|d\u00e9c");
    }

    public FTPClientConfig() {
        this(SYST_UNIX);
    }

    public FTPClientConfig(String string2) {
        this.serverSystemKey = string2;
    }

    public FTPClientConfig(String string2, String string3, String string4, String string5, String string6, String string7) {
        this(string2);
        this.defaultDateFormatStr = string3;
        this.recentDateFormatStr = string4;
        this.serverLanguageCode = string5;
        this.shortMonthNames = string6;
        this.serverTimeZoneId = string7;
    }

    public FTPClientConfig(String string2, String string3, String string4, String string5, String string6, String string7, boolean bl, boolean bl2) {
        this(string2);
        this.defaultDateFormatStr = string3;
        this.lenientFutureDates = bl;
        this.recentDateFormatStr = string4;
        this.saveUnparseableEntries = bl2;
        this.serverLanguageCode = string5;
        this.shortMonthNames = string6;
        this.serverTimeZoneId = string7;
    }

    FTPClientConfig(String string2, FTPClientConfig fTPClientConfig) {
        this.serverSystemKey = string2;
        this.defaultDateFormatStr = fTPClientConfig.defaultDateFormatStr;
        this.lenientFutureDates = fTPClientConfig.lenientFutureDates;
        this.recentDateFormatStr = fTPClientConfig.recentDateFormatStr;
        this.saveUnparseableEntries = fTPClientConfig.saveUnparseableEntries;
        this.serverLanguageCode = fTPClientConfig.serverLanguageCode;
        this.serverTimeZoneId = fTPClientConfig.serverTimeZoneId;
        this.shortMonthNames = fTPClientConfig.shortMonthNames;
    }

    public static DateFormatSymbols getDateFormatSymbols(String arrstring) {
        arrstring = FTPClientConfig.splitShortMonthString((String)arrstring);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.US);
        dateFormatSymbols.setShortMonths(arrstring);
        return dateFormatSymbols;
    }

    public static Collection<String> getSupportedLanguageCodes() {
        return LANGUAGE_CODE_MAP.keySet();
    }

    public static DateFormatSymbols lookupDateFormatSymbols(String object) {
        if ((object = LANGUAGE_CODE_MAP.get(object)) == null) return new DateFormatSymbols(Locale.US);
        if (object instanceof Locale) {
            return new DateFormatSymbols((Locale)object);
        }
        if (!(object instanceof String)) return new DateFormatSymbols(Locale.US);
        return FTPClientConfig.getDateFormatSymbols((String)object);
    }

    private static String[] splitShortMonthString(String arrstring) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)arrstring, "|");
        if (12 != stringTokenizer.countTokens()) throw new IllegalArgumentException("expecting a pipe-delimited string containing 12 tokens");
        arrstring = new String[13];
        int n = 0;
        do {
            if (!stringTokenizer.hasMoreTokens()) {
                arrstring[n] = "";
                return arrstring;
            }
            arrstring[n] = stringTokenizer.nextToken();
            ++n;
        } while (true);
    }

    public String getDefaultDateFormatStr() {
        return this.defaultDateFormatStr;
    }

    public String getRecentDateFormatStr() {
        return this.recentDateFormatStr;
    }

    public String getServerLanguageCode() {
        return this.serverLanguageCode;
    }

    public String getServerSystemKey() {
        return this.serverSystemKey;
    }

    public String getServerTimeZoneId() {
        return this.serverTimeZoneId;
    }

    public String getShortMonthNames() {
        return this.shortMonthNames;
    }

    public boolean getUnparseableEntries() {
        return this.saveUnparseableEntries;
    }

    public boolean isLenientFutureDates() {
        return this.lenientFutureDates;
    }

    public void setDefaultDateFormatStr(String string2) {
        this.defaultDateFormatStr = string2;
    }

    public void setLenientFutureDates(boolean bl) {
        this.lenientFutureDates = bl;
    }

    public void setRecentDateFormatStr(String string2) {
        this.recentDateFormatStr = string2;
    }

    public void setServerLanguageCode(String string2) {
        this.serverLanguageCode = string2;
    }

    public void setServerTimeZoneId(String string2) {
        this.serverTimeZoneId = string2;
    }

    public void setShortMonthNames(String string2) {
        this.shortMonthNames = string2;
    }

    public void setUnparseableEntries(boolean bl) {
        this.saveUnparseableEntries = bl;
    }
}

