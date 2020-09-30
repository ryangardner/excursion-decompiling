/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class MacOsPeterFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    static final String DEFAULT_DATE_FORMAT = "MMM d yyyy";
    static final String DEFAULT_RECENT_DATE_FORMAT = "MMM d HH:mm";
    private static final String REGEX = "([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s+((folder\\s+)|((\\d+)\\s+(\\d+)\\s+))(\\d+)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)";

    public MacOsPeterFTPEntryParser() {
        this(null);
    }

    public MacOsPeterFTPEntryParser(FTPClientConfig fTPClientConfig) {
        super(REGEX);
        this.configure(fTPClientConfig);
    }

    @Override
    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig("UNIX", DEFAULT_DATE_FORMAT, DEFAULT_RECENT_DATE_FORMAT, null, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public FTPFile parseFTPEntry(String var1_1) {
        var2_2 = new FTPFile();
        var2_2.setRawListing((String)var1_1);
        if (this.matches((String)var1_1) == false) return null;
        var3_3 = this.group(1);
        var4_5 = this.group(20);
        var1_1 = new StringBuilder();
        var1_1.append(this.group(21));
        var1_1.append(" ");
        var1_1.append(this.group(22));
        var5_7 = var1_1.toString();
        var1_1 = this.group(23);
        var6_9 = this.group(24);
        try {
            var2_2.setTimestamp(super.parseTimestamp(var5_7));
lbl18: // 2 sources:
            do {
                var7_10 = var3_3.charAt(0);
                if (var7_10 == 45) ** GOTO lbl-1000
                if (var7_10 == 108) ** GOTO lbl-1000
                break;
            } while (true);
        }
        catch (ParseException var5_8) {
            ** continue;
        }
        {
            switch (var7_10) {
                default: {
                    var7_10 = 3;
                    ** break;
                }
                case 101: lbl-1000: // 2 sources:
                {
                    var7_10 = 2;
                    ** break;
                }
                case 100: {
                    var7_10 = 1;
                    ** break;
                }
                case 98: 
                case 99: {
                    var7_10 = 0;
                    var8_11 = true;
                    break;
                }
                case 102: lbl-1000: // 2 sources:
                {
                    var7_10 = 0;
lbl40: // 4 sources:
                    var8_11 = false;
                }
            }
            var2_2.setType(var7_10);
            var10_13 = 4;
        }
        for (var9_12 = 0; var9_12 < 3; ++var9_12, var10_13 += 4) {
            var2_2.setPermission(var9_12, 0, this.group(var10_13).equals("-") ^ true);
            var2_2.setPermission(var9_12, 1, this.group(var10_13 + 1).equals("-") ^ true);
            var3_3 = this.group(var10_13 + 2);
            if (!var3_3.equals("-") && !Character.isUpperCase(var3_3.charAt(0))) {
                var2_2.setPermission(var9_12, 2, true);
                continue;
            }
            var2_2.setPermission(var9_12, 2, false);
        }
        if (!var8_11) {
            try {
                var2_2.setHardLinkCount(Integer.parseInt("0"));
            }
            catch (NumberFormatException var3_4) {}
        }
        var2_2.setUser(null);
        var2_2.setGroup(null);
        try {
            var2_2.setSize(Long.parseLong((String)var4_5));
        }
        catch (NumberFormatException var4_6) {
            // empty catch block
        }
        if (var6_9 == null) {
            var2_2.setName((String)var1_1);
            return var2_2;
        }
        var4_5 = new StringBuilder();
        var4_5.append((String)var1_1);
        var4_5.append(var6_9);
        var1_1 = var4_5.toString();
        if (var7_10 != 2) {
            var2_2.setName((String)var1_1);
            return var2_2;
        }
        var7_10 = var1_1.indexOf(" -> ");
        if (var7_10 == -1) {
            var2_2.setName((String)var1_1);
            return var2_2;
        }
        var2_2.setName(var1_1.substring(0, var7_10));
        var2_2.setLink(var1_1.substring(var7_10 + 4));
        return var2_2;
    }
}

