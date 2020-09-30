/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class UnixFTPEntryParser
extends ConfigurableFTPFileEntryParserImpl {
    static final String DEFAULT_DATE_FORMAT = "MMM d yyyy";
    static final String DEFAULT_RECENT_DATE_FORMAT = "MMM d HH:mm";
    public static final FTPClientConfig NUMERIC_DATE_CONFIG = new FTPClientConfig("UNIX", "yyyy-MM-dd HH:mm", null, null, null, null);
    static final String NUMERIC_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String REGEX = "([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*(\\d+)\\s+(?:(\\S+(?:\\s\\S+)*?)\\s+)?(?:(\\S+(?:\\s\\S+)*)\\s+)?(\\d+(?:,\\s*\\d+)?)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s(.*)";
    private final boolean trimLeadingSpaces;

    public UnixFTPEntryParser() {
        this(null);
    }

    public UnixFTPEntryParser(FTPClientConfig fTPClientConfig) {
        this(fTPClientConfig, false);
    }

    public UnixFTPEntryParser(FTPClientConfig fTPClientConfig, boolean bl) {
        super(REGEX);
        this.configure(fTPClientConfig);
        this.trimLeadingSpaces = bl;
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
        var4_4 = this.group(15);
        var5_5 = this.group(16);
        var6_6 = this.group(17);
        var7_7 = this.group(18);
        var1_1 = new StringBuilder();
        var1_1.append(this.group(19));
        var1_1.append(" ");
        var1_1.append(this.group(20));
        var8_8 = var1_1.toString();
        var9_9 = this.group(21);
        var1_1 = var9_9;
        if (this.trimLeadingSpaces) {
            var1_1 = var9_9.replaceFirst("^\\s+", "");
        }
        try {
            var2_2.setTimestamp(super.parseTimestamp(var8_8));
lbl23: // 2 sources:
            do {
                var10_13 = var3_3.charAt(0);
                if (var10_13 == 45) ** GOTO lbl-1000
                if (var10_13 == 108) ** GOTO lbl-1000
                break;
            } while (true);
        }
        catch (ParseException var9_11) {
            ** continue;
        }
        {
            switch (var10_13) {
                default: {
                    var10_13 = 3;
                    ** break;
                }
                case 101: lbl-1000: // 2 sources:
                {
                    var10_13 = 2;
                    ** break;
                }
                case 100: {
                    var10_13 = 1;
                    ** break;
                }
                case 98: 
                case 99: {
                    var10_13 = 0;
                    var11_14 = true;
                    break;
                }
                case 102: lbl-1000: // 2 sources:
                {
                    var10_13 = 0;
lbl45: // 4 sources:
                    var11_14 = false;
                }
            }
            var2_2.setType(var10_13);
            var13_16 = 4;
        }
        for (var12_15 = 0; var12_15 < 3; ++var12_15, var13_16 += 4) {
            var2_2.setPermission(var12_15, 0, this.group(var13_16).equals("-") ^ true);
            var2_2.setPermission(var12_15, 1, this.group(var13_16 + 1).equals("-") ^ true);
            var9_9 = this.group(var13_16 + 2);
            if (!var9_9.equals("-") && !Character.isUpperCase(var9_9.charAt(0))) {
                var2_2.setPermission(var12_15, 2, true);
                continue;
            }
            var2_2.setPermission(var12_15, 2, false);
        }
        if (!var11_14) {
            try {
                var2_2.setHardLinkCount(Integer.parseInt(var4_4));
            }
            catch (NumberFormatException var9_12) {}
        }
        var2_2.setUser(var5_5);
        var2_2.setGroup(var6_6);
        try {
            var2_2.setSize(Long.parseLong(var7_7));
        }
        catch (NumberFormatException var9_10) {
            // empty catch block
        }
        if (var10_13 != 2) {
            var2_2.setName((String)var1_1);
            return var2_2;
        }
        var10_13 = var1_1.indexOf(" -> ");
        if (var10_13 == -1) {
            var2_2.setName((String)var1_1);
            return var2_2;
        }
        var2_2.setName(var1_1.substring(0, var10_13));
        var2_2.setLink(var1_1.substring(var10_13 + 4));
        return var2_2;
    }

    @Override
    public List<String> preParse(List<String> list) {
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (!listIterator.next().matches("^total \\d+$")) continue;
            listIterator.remove();
        }
        return list;
    }
}

