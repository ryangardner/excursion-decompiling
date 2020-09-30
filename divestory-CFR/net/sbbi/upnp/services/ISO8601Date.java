/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class ISO8601Date {
    private static boolean check(StringTokenizer object, String string2) throws NumberFormatException {
        try {
            if (((StringTokenizer)object).nextToken().equals(string2)) {
                return true;
            }
            object = new StringBuilder("Missing [");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("]");
            NumberFormatException numberFormatException = new NumberFormatException(((StringBuilder)object).toString());
            throw numberFormatException;
        }
        catch (NoSuchElementException noSuchElementException) {
            return false;
        }
    }

    private static Calendar getCalendar(String charSequence) throws NumberFormatException {
        int n;
        int n2;
        StringTokenizer stringTokenizer;
        Object object;
        block15 : {
            block14 : {
                block11 : {
                    block12 : {
                        block13 : {
                            n = ((String)charSequence).indexOf(58) != -1 ? 1 : 0;
                            n2 = ((String)charSequence).indexOf(45) == -1 && (((String)charSequence).length() != 4 || n != 0) ? 0 : 1;
                            object = charSequence;
                            if (n != 0) {
                                object = charSequence;
                                if (n2 == 0) {
                                    object = charSequence;
                                    if (!((String)charSequence).toUpperCase().startsWith("T")) {
                                        object = new StringBuilder("T");
                                        ((StringBuilder)object).append((String)charSequence);
                                        object = ((StringBuilder)object).toString();
                                    }
                                }
                            }
                            stringTokenizer = new StringTokenizer((String)object, "-T:.+Z", true);
                            object = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
                            ((Calendar)object).clear();
                            if (n2 != 0) {
                                if (!stringTokenizer.hasMoreTokens()) return object;
                                ((Calendar)object).set(1, Integer.parseInt(stringTokenizer.nextToken()));
                                if (!ISO8601Date.check(stringTokenizer, "-")) return object;
                                if (!stringTokenizer.hasMoreTokens()) return object;
                                ((Calendar)object).set(2, Integer.parseInt(stringTokenizer.nextToken()) - 1);
                                if (!ISO8601Date.check(stringTokenizer, "-")) return object;
                                if (!stringTokenizer.hasMoreTokens()) return object;
                                ((Calendar)object).set(5, Integer.parseInt(stringTokenizer.nextToken()));
                            }
                            if (!ISO8601Date.check(stringTokenizer, "T") || !stringTokenizer.hasMoreTokens()) break block11;
                            ((Calendar)object).set(11, Integer.parseInt(stringTokenizer.nextToken()));
                            if (!ISO8601Date.check(stringTokenizer, ":") || !stringTokenizer.hasMoreTokens()) break block12;
                            ((Calendar)object).set(12, Integer.parseInt(stringTokenizer.nextToken()));
                            if (!stringTokenizer.hasMoreTokens()) {
                                return object;
                            }
                            charSequence = stringTokenizer.nextToken();
                            if (!((String)charSequence).equals(":")) break block13;
                            if (!stringTokenizer.hasMoreTokens()) throw new NumberFormatException("No secondes specified");
                            ((Calendar)object).set(13, Integer.parseInt(stringTokenizer.nextToken()));
                            if (!stringTokenizer.hasMoreTokens()) {
                                return object;
                            }
                            charSequence = stringTokenizer.nextToken();
                            if (((String)charSequence).equals(".")) break block14;
                            ((Calendar)object).set(14, 0);
                            break block15;
                        }
                        ((Calendar)object).set(13, 0);
                        ((Calendar)object).set(14, 0);
                        break block15;
                    }
                    ((Calendar)object).set(12, 0);
                    ((Calendar)object).set(13, 0);
                    ((Calendar)object).set(14, 0);
                    return object;
                }
                ((Calendar)object).set(11, 0);
                ((Calendar)object).set(12, 0);
                ((Calendar)object).set(13, 0);
                ((Calendar)object).set(14, 0);
                return object;
            }
            charSequence = stringTokenizer.nextToken();
            do {
                if (((String)charSequence).length() >= 3) {
                    ((Calendar)object).set(14, Integer.parseInt(((String)charSequence).substring(0, 3)));
                    if (!stringTokenizer.hasMoreTokens()) {
                        return object;
                    }
                    charSequence = stringTokenizer.nextToken();
                    break;
                }
                charSequence = new StringBuilder(String.valueOf(charSequence));
                ((StringBuilder)charSequence).append("0");
                charSequence = ((StringBuilder)charSequence).toString();
            } while (true);
        }
        if (((String)charSequence).equals("Z")) return object;
        if (!((String)charSequence).equals("+")) {
            if (!((String)charSequence).equals("-")) throw new NumberFormatException("only Z, + or - allowed");
        }
        boolean bl = ((String)charSequence).equals("+");
        if (!stringTokenizer.hasMoreTokens()) throw new NumberFormatException("Missing hour field");
        n2 = Integer.parseInt(stringTokenizer.nextToken());
        if (!ISO8601Date.check(stringTokenizer, ":")) throw new NumberFormatException("Missing minute field");
        if (!stringTokenizer.hasMoreTokens()) throw new NumberFormatException("Missing minute field");
        n = Integer.parseInt(stringTokenizer.nextToken());
        if (bl) {
            ((Calendar)object).add(10, -n2);
            ((Calendar)object).add(12, -n);
            return object;
        }
        ((Calendar)object).add(10, n2);
        ((Calendar)object).add(12, n);
        return object;
    }

    public static String getIsoDate(Date comparable) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime((Date)comparable);
        comparable = new StringBuffer();
        ((StringBuffer)comparable).append(gregorianCalendar.get(1));
        ((StringBuffer)comparable).append("-");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(2) + 1));
        ((StringBuffer)comparable).append("-");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(5)));
        return ((StringBuffer)comparable).toString();
    }

    public static String getIsoDateTime(Date comparable) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime((Date)comparable);
        comparable = new StringBuffer();
        ((StringBuffer)comparable).append(gregorianCalendar.get(1));
        ((StringBuffer)comparable).append("-");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(2) + 1));
        ((StringBuffer)comparable).append("-");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(5)));
        ((StringBuffer)comparable).append("T");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(11)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(12)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(13)));
        ((StringBuffer)comparable).append(".");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(14) / 10));
        return ((StringBuffer)comparable).toString();
    }

    public static String getIsoDateTimeZone(Date comparable) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gregorianCalendar.setTime((Date)comparable);
        comparable = new StringBuffer();
        ((StringBuffer)comparable).append(gregorianCalendar.get(1));
        ((StringBuffer)comparable).append("-");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(2) + 1));
        ((StringBuffer)comparable).append("-");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(5)));
        ((StringBuffer)comparable).append("T");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(11)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(12)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(13)));
        ((StringBuffer)comparable).append(".");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(14) / 10));
        ((StringBuffer)comparable).append("Z");
        return ((StringBuffer)comparable).toString();
    }

    public static String getIsoTime(Date comparable) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime((Date)comparable);
        comparable = new StringBuffer();
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(11)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(12)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(13)));
        ((StringBuffer)comparable).append(".");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(14) / 10));
        return ((StringBuffer)comparable).toString();
    }

    public static String getIsoTimeZone(Date comparable) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gregorianCalendar.setTime((Date)comparable);
        comparable = new StringBuffer();
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(11)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(12)));
        ((StringBuffer)comparable).append(":");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(13)));
        ((StringBuffer)comparable).append(".");
        ((StringBuffer)comparable).append(ISO8601Date.twoDigit(gregorianCalendar.get(14) / 10));
        ((StringBuffer)comparable).append("Z");
        return ((StringBuffer)comparable).toString();
    }

    public static Date parse(String string2) throws NumberFormatException {
        return ISO8601Date.getCalendar(string2).getTime();
    }

    private static String twoDigit(int n) {
        if (n < 0) return String.valueOf(n);
        if (n >= 10) return String.valueOf(n);
        StringBuilder stringBuilder = new StringBuilder("0");
        stringBuilder.append(String.valueOf(n));
        return stringBuilder.toString();
    }
}

