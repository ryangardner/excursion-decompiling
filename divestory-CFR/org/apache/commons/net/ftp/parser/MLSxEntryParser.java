/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileEntryParserImpl;

public class MLSxEntryParser
extends FTPFileEntryParserImpl {
    private static final MLSxEntryParser PARSER = new MLSxEntryParser();
    private static final HashMap<String, Integer> TYPE_TO_INT;
    private static int[] UNIX_GROUPS;
    private static int[][] UNIX_PERMS;

    static {
        int[] arrn = new HashMap<String, Integer>();
        TYPE_TO_INT = arrn;
        arrn.put("file", 0);
        int[] arrn2 = TYPE_TO_INT;
        arrn = 1;
        arrn2.put((String)"cdir", arrn);
        TYPE_TO_INT.put("pdir", (Integer)arrn);
        TYPE_TO_INT.put("dir", (Integer)arrn);
        UNIX_GROUPS = new int[]{0, 1, 2};
        arrn = new int[]{2};
        arrn2 = new int[]{0, 2};
        int[] arrn3 = new int[]{0, 1};
        UNIX_PERMS = new int[][]{new int[0], arrn, {1}, {2, 1}, {0}, arrn2, arrn3, {0, 1, 2}};
    }

    /*
     * Unable to fully structure code
     */
    private void doUnixPerms(FTPFile var1_1, String var2_2) {
        var2_2 = var2_2.toCharArray();
        var3_3 = var2_2.length;
        var4_4 = 0;
        while (var4_4 < var3_3) {
            var5_5 = var2_2[var4_4];
            if (var5_5 != 'a') {
                if (var5_5 != 'p') {
                    if (var5_5 != 'r') {
                        if (var5_5 != 'w') {
                            if (var5_5 != 'l') {
                                if (var5_5 != 'm') {
                                    switch (var5_5) {
                                        default: {
                                            ** break;
                                        }
                                        case 'e': {
                                            var1_1.setPermission(0, 0, true);
                                            ** break;
                                        }
                                        case 'd': {
                                            var1_1.setPermission(0, 1, true);
                                            ** break;
                                        }
                                        case 'c': 
                                    }
                                    var1_1.setPermission(0, 1, true);
                                    ** break;
lbl24: // 4 sources:
                                } else {
                                    var1_1.setPermission(0, 1, true);
                                }
                            } else {
                                var1_1.setPermission(0, 2, true);
                            }
                        } else {
                            var1_1.setPermission(0, 1, true);
                        }
                    } else {
                        var1_1.setPermission(0, 0, true);
                    }
                } else {
                    var1_1.setPermission(0, 1, true);
                }
            } else {
                var1_1.setPermission(0, 1, true);
            }
            ++var4_4;
        }
    }

    public static MLSxEntryParser getInstance() {
        return PARSER;
    }

    public static FTPFile parseEntry(String string2) {
        return PARSER.parseFTPEntry(string2);
    }

    public static Calendar parseGMTdateTime(String string2) {
        boolean bl;
        Cloneable cloneable;
        if (string2.contains(".")) {
            cloneable = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
            bl = true;
        } else {
            cloneable = new SimpleDateFormat("yyyyMMddHHmmss");
            bl = false;
        }
        Object object = TimeZone.getTimeZone("GMT");
        cloneable.setTimeZone((TimeZone)object);
        GregorianCalendar gregorianCalendar = new GregorianCalendar((TimeZone)object);
        object = new ParsePosition(0);
        cloneable.setLenient(false);
        cloneable = cloneable.parse(string2, (ParsePosition)object);
        if (((ParsePosition)object).getIndex() != string2.length()) {
            return null;
        }
        gregorianCalendar.setTime((Date)cloneable);
        if (bl) return gregorianCalendar;
        gregorianCalendar.clear(14);
        return gregorianCalendar;
    }

    @Override
    public FTPFile parseFTPEntry(String arrstring) {
        if (arrstring.startsWith(" ")) {
            if (arrstring.length() <= 1) return null;
            FTPFile fTPFile = new FTPFile();
            fTPFile.setRawListing((String)arrstring);
            fTPFile.setName(arrstring.substring(1));
            return fTPFile;
        }
        Object object = arrstring.split(" ", 2);
        if (((String[])object).length != 2) return null;
        if (object[1].length() == 0) {
            return null;
        }
        int[] arrn = object[0];
        if (!arrn.endsWith(";")) {
            return null;
        }
        FTPFile fTPFile = new FTPFile();
        fTPFile.setRawListing((String)arrstring);
        fTPFile.setName(object[1]);
        arrstring = arrn.split(";");
        boolean bl = object[0].toLowerCase(Locale.ENGLISH).contains("unix.mode=");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            block10 : {
                int n3;
                block18 : {
                    String string2;
                    block15 : {
                        block17 : {
                            block16 : {
                                block14 : {
                                    block13 : {
                                        block12 : {
                                            block11 : {
                                                object = arrstring[n2].split("=", -1);
                                                if (((String[])object).length != 2) {
                                                    return null;
                                                }
                                                arrn = object[0].toLowerCase(Locale.ENGLISH);
                                                if (((String)(object = object[1])).length() == 0) break block10;
                                                string2 = ((String)object).toLowerCase(Locale.ENGLISH);
                                                if (!"size".equals(arrn)) break block11;
                                                fTPFile.setSize(Long.parseLong((String)object));
                                                break block10;
                                            }
                                            if (!"sizd".equals(arrn)) break block12;
                                            fTPFile.setSize(Long.parseLong((String)object));
                                            break block10;
                                        }
                                        if (!"modify".equals(arrn)) break block13;
                                        if ((object = MLSxEntryParser.parseGMTdateTime((String)object)) == null) {
                                            return null;
                                        }
                                        fTPFile.setTimestamp((Calendar)object);
                                        break block10;
                                    }
                                    if (!"type".equals(arrn)) break block14;
                                    object = TYPE_TO_INT.get(string2);
                                    if (object == null) {
                                        fTPFile.setType(3);
                                    } else {
                                        fTPFile.setType((Integer)object);
                                    }
                                    break block10;
                                }
                                if (!arrn.startsWith("unix.")) break block15;
                                if (!"group".equals(arrn = arrn.substring(5).toLowerCase(Locale.ENGLISH))) break block16;
                                fTPFile.setGroup((String)object);
                                break block10;
                            }
                            if (!"owner".equals(arrn)) break block17;
                            fTPFile.setUser((String)object);
                            break block10;
                        }
                        if (!"mode".equals(arrn)) break block10;
                        n3 = ((String)object).length();
                        break block18;
                    }
                    if (bl || !"perm".equals(arrn)) break block10;
                    this.doUnixPerms(fTPFile, string2);
                    break block10;
                }
                for (int i = 0; i < 3; ++i) {
                    int n4 = ((String)object).charAt(n3 - 3 + i) - 48;
                    if (n4 < 0 || n4 > 7) continue;
                    for (int n5 : UNIX_PERMS[n4]) {
                        fTPFile.setPermission(UNIX_GROUPS[i], n5, true);
                    }
                }
            }
            ++n2;
        }
        return fTPFile;
    }
}

