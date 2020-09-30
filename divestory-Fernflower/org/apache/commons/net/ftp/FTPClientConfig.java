package org.apache.commons.net.ftp;

import java.text.DateFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
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
   private String defaultDateFormatStr;
   private boolean lenientFutureDates;
   private String recentDateFormatStr;
   private boolean saveUnparseableEntries;
   private String serverLanguageCode;
   private final String serverSystemKey;
   private String serverTimeZoneId;
   private String shortMonthNames;

   static {
      TreeMap var0 = new TreeMap();
      LANGUAGE_CODE_MAP = var0;
      var0.put("en", Locale.ENGLISH);
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
      LANGUAGE_CODE_MAP.put("fr", "jan|fév|mar|avr|mai|jun|jui|aoû|sep|oct|nov|déc");
   }

   public FTPClientConfig() {
      this("UNIX");
   }

   public FTPClientConfig(String var1) {
      this.defaultDateFormatStr = null;
      this.recentDateFormatStr = null;
      this.lenientFutureDates = true;
      this.serverLanguageCode = null;
      this.shortMonthNames = null;
      this.serverTimeZoneId = null;
      this.saveUnparseableEntries = false;
      this.serverSystemKey = var1;
   }

   public FTPClientConfig(String var1, String var2, String var3, String var4, String var5, String var6) {
      this(var1);
      this.defaultDateFormatStr = var2;
      this.recentDateFormatStr = var3;
      this.serverLanguageCode = var4;
      this.shortMonthNames = var5;
      this.serverTimeZoneId = var6;
   }

   public FTPClientConfig(String var1, String var2, String var3, String var4, String var5, String var6, boolean var7, boolean var8) {
      this(var1);
      this.defaultDateFormatStr = var2;
      this.lenientFutureDates = var7;
      this.recentDateFormatStr = var3;
      this.saveUnparseableEntries = var8;
      this.serverLanguageCode = var4;
      this.shortMonthNames = var5;
      this.serverTimeZoneId = var6;
   }

   FTPClientConfig(String var1, FTPClientConfig var2) {
      this.defaultDateFormatStr = null;
      this.recentDateFormatStr = null;
      this.lenientFutureDates = true;
      this.serverLanguageCode = null;
      this.shortMonthNames = null;
      this.serverTimeZoneId = null;
      this.saveUnparseableEntries = false;
      this.serverSystemKey = var1;
      this.defaultDateFormatStr = var2.defaultDateFormatStr;
      this.lenientFutureDates = var2.lenientFutureDates;
      this.recentDateFormatStr = var2.recentDateFormatStr;
      this.saveUnparseableEntries = var2.saveUnparseableEntries;
      this.serverLanguageCode = var2.serverLanguageCode;
      this.serverTimeZoneId = var2.serverTimeZoneId;
      this.shortMonthNames = var2.shortMonthNames;
   }

   public static DateFormatSymbols getDateFormatSymbols(String var0) {
      String[] var2 = splitShortMonthString(var0);
      DateFormatSymbols var1 = new DateFormatSymbols(Locale.US);
      var1.setShortMonths(var2);
      return var1;
   }

   public static Collection<String> getSupportedLanguageCodes() {
      return LANGUAGE_CODE_MAP.keySet();
   }

   public static DateFormatSymbols lookupDateFormatSymbols(String var0) {
      Object var1 = LANGUAGE_CODE_MAP.get(var0);
      if (var1 != null) {
         if (var1 instanceof Locale) {
            return new DateFormatSymbols((Locale)var1);
         }

         if (var1 instanceof String) {
            return getDateFormatSymbols((String)var1);
         }
      }

      return new DateFormatSymbols(Locale.US);
   }

   private static String[] splitShortMonthString(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0, "|");
      if (12 != var1.countTokens()) {
         throw new IllegalArgumentException("expecting a pipe-delimited string containing 12 tokens");
      } else {
         String[] var3 = new String[13];

         int var2;
         for(var2 = 0; var1.hasMoreTokens(); ++var2) {
            var3[var2] = var1.nextToken();
         }

         var3[var2] = "";
         return var3;
      }
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

   public void setDefaultDateFormatStr(String var1) {
      this.defaultDateFormatStr = var1;
   }

   public void setLenientFutureDates(boolean var1) {
      this.lenientFutureDates = var1;
   }

   public void setRecentDateFormatStr(String var1) {
      this.recentDateFormatStr = var1;
   }

   public void setServerLanguageCode(String var1) {
      this.serverLanguageCode = var1;
   }

   public void setServerTimeZoneId(String var1) {
      this.serverTimeZoneId = var1;
   }

   public void setShortMonthNames(String var1) {
      this.shortMonthNames = var1;
   }

   public void setUnparseableEntries(boolean var1) {
      this.saveUnparseableEntries = var1;
   }
}
