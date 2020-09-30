/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.io.PrintStream;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.mail.internet.MailDateParser;

public class MailDateFormat
extends SimpleDateFormat {
    private static Calendar cal;
    static boolean debug = false;
    private static final long serialVersionUID = -8148227605210628779L;
    private static TimeZone tz;

    static {
        tz = TimeZone.getTimeZone("GMT");
        cal = new GregorianCalendar(tz);
    }

    public MailDateFormat() {
        super("EEE, d MMM yyyy HH:mm:ss 'XXXXX' (z)", Locale.US);
    }

    private static Date ourUTC(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl) {
        synchronized (MailDateFormat.class) {
            cal.clear();
            cal.setLenient(bl);
            cal.set(1, n);
            cal.set(2, n2);
            cal.set(5, n3);
            cal.set(11, n4);
            cal.set(12, n5 + n7);
            cal.set(13, n6);
            Date date = cal.getTime();
            return date;
        }
    }

    private static Date parseDate(char[] arrc, ParsePosition parsePosition, boolean bl) {
        try {
            int n;
            Object object;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            block10 : {
                object = new MailDateParser(arrc);
                ((MailDateParser)object).skipUntilNumber();
                n = ((MailDateParser)object).parseNumber();
                if (!((MailDateParser)object).skipIfChar('-')) {
                    ((MailDateParser)object).skipWhiteSpace();
                }
                n2 = ((MailDateParser)object).parseMonth();
                if (!((MailDateParser)object).skipIfChar('-')) {
                    ((MailDateParser)object).skipWhiteSpace();
                }
                if ((n5 = ((MailDateParser)object).parseNumber()) < 50) {
                    n6 = n5 + 2000;
                } else {
                    n6 = n5;
                    if (n5 < 100) {
                        n6 = n5 + 1900;
                    }
                }
                ((MailDateParser)object).skipWhiteSpace();
                n3 = ((MailDateParser)object).parseNumber();
                ((MailDateParser)object).skipChar(':');
                n7 = ((MailDateParser)object).parseNumber();
                boolean bl2 = ((MailDateParser)object).skipIfChar(':');
                int n8 = 0;
                n5 = bl2 ? ((MailDateParser)object).parseNumber() : 0;
                try {
                    ((MailDateParser)object).skipWhiteSpace();
                    n4 = ((MailDateParser)object).parseTimeZone();
                }
                catch (ParseException parseException) {
                    n4 = n8;
                    if (!debug) break block10;
                    PrintStream printStream = System.out;
                    StringBuilder stringBuilder = new StringBuilder("No timezone? : '");
                    String string2 = new String(arrc);
                    stringBuilder.append(string2);
                    stringBuilder.append("'");
                    printStream.println(stringBuilder.toString());
                    n4 = n8;
                }
            }
            parsePosition.setIndex(((MailDateParser)object).getIndex());
            return MailDateFormat.ourUTC(n6, n2, n, n3, n7, n5, n4, bl);
        }
        catch (Exception exception) {
            if (debug) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder("Bad date: '");
                stringBuilder.append(new String(arrc));
                stringBuilder.append("'");
                printStream.println(stringBuilder.toString());
                exception.printStackTrace();
            }
            parsePosition.setIndex(1);
            return null;
        }
    }

    @Override
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        int n;
        int n2;
        int n3 = stringBuffer.length();
        super.format(date, stringBuffer, fieldPosition);
        n3 += 25;
        do {
            if (stringBuffer.charAt(n3) == 'X') {
                this.calendar.clear();
                this.calendar.setTime(date);
                n2 = this.calendar.get(15) + this.calendar.get(16);
                if (n2 < 0) {
                    n = n3 + 1;
                    stringBuffer.setCharAt(n3, '-');
                    n2 = -n2;
                    n3 = n;
                    break;
                }
                n = n3 + 1;
                stringBuffer.setCharAt(n3, '+');
                n3 = n;
                break;
            }
            ++n3;
        } while (true);
        n = n2 / 60 / 1000;
        n2 = n / 60;
        int n4 = n % 60;
        n = n3 + 1;
        stringBuffer.setCharAt(n3, Character.forDigit(n2 / 10, 10));
        n3 = n + 1;
        stringBuffer.setCharAt(n, Character.forDigit(n2 % 10, 10));
        stringBuffer.setCharAt(n3, Character.forDigit(n4 / 10, 10));
        stringBuffer.setCharAt(n3 + 1, Character.forDigit(n4 % 10, 10));
        return stringBuffer;
    }

    @Override
    public Date parse(String string2, ParsePosition parsePosition) {
        return MailDateFormat.parseDate(string2.toCharArray(), parsePosition, this.isLenient());
    }

    @Override
    public void setCalendar(Calendar calendar) {
        throw new RuntimeException("Method setCalendar() shouldn't be called");
    }

    @Override
    public void setNumberFormat(NumberFormat numberFormat) {
        throw new RuntimeException("Method setNumberFormat() shouldn't be called");
    }
}

