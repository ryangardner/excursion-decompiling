/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.AddressTerm;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.NotTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.RecipientTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SizeTerm;
import javax.mail.search.StringTerm;
import javax.mail.search.SubjectTerm;

class SearchSequence {
    private static Calendar cal;
    private static String[] monthTable;

    static {
        monthTable = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        cal = new GregorianCalendar();
    }

    SearchSequence() {
    }

    private static Argument and(AndTerm arrsearchTerm, String string2) throws SearchException, IOException {
        arrsearchTerm = arrsearchTerm.getTerms();
        Argument argument = SearchSequence.generateSequence(arrsearchTerm[0], string2);
        int n = 1;
        while (n < arrsearchTerm.length) {
            argument.append(SearchSequence.generateSequence(arrsearchTerm[n], string2));
            ++n;
        }
        return argument;
    }

    private static Argument body(BodyTerm bodyTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("BODY");
        argument.writeString(bodyTerm.getPattern(), string2);
        return argument;
    }

    private static Argument flag(FlagTerm object) throws SearchException {
        boolean bl = ((FlagTerm)object).getTestSet();
        Argument argument = new Argument();
        object = ((FlagTerm)object).getFlags();
        Flags.Flag[] arrflag = ((Flags)object).getSystemFlags();
        String[] arrstring = ((Flags)object).getUserFlags();
        if (arrflag.length == 0) {
            if (arrstring.length == 0) throw new SearchException("Invalid FlagTerm");
        }
        int n = 0;
        int n2 = 0;
        do {
            if (n2 >= arrflag.length) break;
            if (arrflag[n2] == Flags.Flag.DELETED) {
                object = bl ? "DELETED" : "UNDELETED";
                argument.writeAtom((String)object);
            } else if (arrflag[n2] == Flags.Flag.ANSWERED) {
                object = bl ? "ANSWERED" : "UNANSWERED";
                argument.writeAtom((String)object);
            } else if (arrflag[n2] == Flags.Flag.DRAFT) {
                object = bl ? "DRAFT" : "UNDRAFT";
                argument.writeAtom((String)object);
            } else if (arrflag[n2] == Flags.Flag.FLAGGED) {
                object = bl ? "FLAGGED" : "UNFLAGGED";
                argument.writeAtom((String)object);
            } else if (arrflag[n2] == Flags.Flag.RECENT) {
                object = bl ? "RECENT" : "OLD";
                argument.writeAtom((String)object);
            } else if (arrflag[n2] == Flags.Flag.SEEN) {
                object = bl ? "SEEN" : "UNSEEN";
                argument.writeAtom((String)object);
            }
            ++n2;
        } while (true);
        n2 = n;
        while (n2 < arrstring.length) {
            object = bl ? "KEYWORD" : "UNKEYWORD";
            argument.writeAtom((String)object);
            argument.writeAtom(arrstring[n2]);
            ++n2;
        }
        return argument;
    }

    private static Argument from(String string2, String string3) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("FROM");
        argument.writeString(string2, string3);
        return argument;
    }

    static Argument generateSequence(SearchTerm searchTerm, String string2) throws SearchException, IOException {
        if (searchTerm instanceof AndTerm) {
            return SearchSequence.and((AndTerm)searchTerm, string2);
        }
        if (searchTerm instanceof OrTerm) {
            return SearchSequence.or((OrTerm)searchTerm, string2);
        }
        if (searchTerm instanceof NotTerm) {
            return SearchSequence.not((NotTerm)searchTerm, string2);
        }
        if (searchTerm instanceof HeaderTerm) {
            return SearchSequence.header((HeaderTerm)searchTerm, string2);
        }
        if (searchTerm instanceof FlagTerm) {
            return SearchSequence.flag((FlagTerm)searchTerm);
        }
        if (searchTerm instanceof FromTerm) {
            return SearchSequence.from(((FromTerm)searchTerm).getAddress().toString(), string2);
        }
        if (searchTerm instanceof FromStringTerm) {
            return SearchSequence.from(((FromStringTerm)searchTerm).getPattern(), string2);
        }
        if (searchTerm instanceof RecipientTerm) {
            searchTerm = (RecipientTerm)searchTerm;
            return SearchSequence.recipient(((RecipientTerm)searchTerm).getRecipientType(), ((AddressTerm)searchTerm).getAddress().toString(), string2);
        }
        if (searchTerm instanceof RecipientStringTerm) {
            searchTerm = (RecipientStringTerm)searchTerm;
            return SearchSequence.recipient(((RecipientStringTerm)searchTerm).getRecipientType(), ((StringTerm)searchTerm).getPattern(), string2);
        }
        if (searchTerm instanceof SubjectTerm) {
            return SearchSequence.subject((SubjectTerm)searchTerm, string2);
        }
        if (searchTerm instanceof BodyTerm) {
            return SearchSequence.body((BodyTerm)searchTerm, string2);
        }
        if (searchTerm instanceof SizeTerm) {
            return SearchSequence.size((SizeTerm)searchTerm);
        }
        if (searchTerm instanceof SentDateTerm) {
            return SearchSequence.sentdate((SentDateTerm)searchTerm);
        }
        if (searchTerm instanceof ReceivedDateTerm) {
            return SearchSequence.receiveddate((ReceivedDateTerm)searchTerm);
        }
        if (!(searchTerm instanceof MessageIDTerm)) throw new SearchException("Search too complex");
        return SearchSequence.messageid((MessageIDTerm)searchTerm, string2);
    }

    private static Argument header(HeaderTerm headerTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("HEADER");
        argument.writeString(headerTerm.getHeaderName());
        argument.writeString(headerTerm.getPattern(), string2);
        return argument;
    }

    private static boolean isAscii(String string2) {
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            if (string2.charAt(n2) > '') {
                return false;
            }
            ++n2;
        }
        return true;
    }

    static boolean isAscii(SearchTerm arrsearchTerm) {
        boolean bl = arrsearchTerm instanceof AndTerm;
        if (!bl && !(arrsearchTerm instanceof OrTerm)) {
            if (arrsearchTerm instanceof NotTerm) {
                return SearchSequence.isAscii(((NotTerm)arrsearchTerm).getTerm());
            }
            if (arrsearchTerm instanceof StringTerm) {
                return SearchSequence.isAscii(((StringTerm)arrsearchTerm).getPattern());
            }
            if (!(arrsearchTerm instanceof AddressTerm)) return true;
            return SearchSequence.isAscii(((AddressTerm)arrsearchTerm).getAddress().toString());
        }
        arrsearchTerm = bl ? ((AndTerm)arrsearchTerm).getTerms() : ((OrTerm)arrsearchTerm).getTerms();
        int n = 0;
        while (n < arrsearchTerm.length) {
            if (!SearchSequence.isAscii(arrsearchTerm[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private static Argument messageid(MessageIDTerm messageIDTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("HEADER");
        argument.writeString("Message-ID");
        argument.writeString(messageIDTerm.getPattern(), string2);
        return argument;
    }

    private static Argument not(NotTerm searchTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("NOT");
        searchTerm = searchTerm.getTerm();
        if (!(searchTerm instanceof AndTerm) && !(searchTerm instanceof FlagTerm)) {
            argument.append(SearchSequence.generateSequence(searchTerm, string2));
            return argument;
        }
        argument.writeArgument(SearchSequence.generateSequence(searchTerm, string2));
        return argument;
    }

    private static Argument or(OrTerm object, String string2) throws SearchException, IOException {
        Object object2 = object.getTerms();
        object = object2;
        if (((SearchTerm[])object2).length > 2) {
            object = object2[0];
            int n = 1;
            do {
                if (n >= ((SearchTerm[])object2).length) {
                    object = ((OrTerm)object).getTerms();
                    break;
                }
                object = new OrTerm((SearchTerm)object, (SearchTerm)object2[n]);
                ++n;
            } while (true);
        }
        object2 = new Argument();
        if (((SearchTerm[])object).length > 1) {
            ((Argument)object2).writeAtom("OR");
        }
        if (!(object[0] instanceof AndTerm) && !(object[0] instanceof FlagTerm)) {
            ((Argument)object2).append(SearchSequence.generateSequence(object[0], string2));
        } else {
            ((Argument)object2).writeArgument(SearchSequence.generateSequence(object[0], string2));
        }
        if (((SearchTerm[])object).length <= 1) return object2;
        if (!(object[1] instanceof AndTerm) && !(object[1] instanceof FlagTerm)) {
            ((Argument)object2).append(SearchSequence.generateSequence(object[1], string2));
            return object2;
        }
        ((Argument)object2).writeArgument(SearchSequence.generateSequence(object[1], string2));
        return object2;
    }

    private static Argument receiveddate(DateTerm serializable) throws SearchException {
        Argument argument = new Argument();
        String string2 = SearchSequence.toIMAPDate(((DateTerm)serializable).getDate());
        switch (((DateTerm)serializable).getComparison()) {
            default: {
                throw new SearchException("Cannot handle Date Comparison");
            }
            case 6: {
                serializable = new StringBuilder("OR SINCE ");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" ON ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 5: {
                serializable = new StringBuilder("SINCE ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 4: {
                serializable = new StringBuilder("NOT ON ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 3: {
                serializable = new StringBuilder("ON ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 2: {
                serializable = new StringBuilder("BEFORE ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 1: 
        }
        serializable = new StringBuilder("OR BEFORE ");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(" ON ");
        ((StringBuilder)serializable).append(string2);
        argument.writeAtom(((StringBuilder)serializable).toString());
        return argument;
    }

    private static Argument recipient(Message.RecipientType recipientType, String string2, String string3) throws SearchException, IOException {
        Argument argument = new Argument();
        if (recipientType == Message.RecipientType.TO) {
            argument.writeAtom("TO");
        } else if (recipientType == Message.RecipientType.CC) {
            argument.writeAtom("CC");
        } else {
            if (recipientType != Message.RecipientType.BCC) throw new SearchException("Illegal Recipient type");
            argument.writeAtom("BCC");
        }
        argument.writeString(string2, string3);
        return argument;
    }

    private static Argument sentdate(DateTerm serializable) throws SearchException {
        Argument argument = new Argument();
        String string2 = SearchSequence.toIMAPDate(((DateTerm)serializable).getDate());
        switch (((DateTerm)serializable).getComparison()) {
            default: {
                throw new SearchException("Cannot handle Date Comparison");
            }
            case 6: {
                serializable = new StringBuilder("OR SENTSINCE ");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" SENTON ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 5: {
                serializable = new StringBuilder("SENTSINCE ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 4: {
                serializable = new StringBuilder("NOT SENTON ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 3: {
                serializable = new StringBuilder("SENTON ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 2: {
                serializable = new StringBuilder("SENTBEFORE ");
                ((StringBuilder)serializable).append(string2);
                argument.writeAtom(((StringBuilder)serializable).toString());
                return argument;
            }
            case 1: 
        }
        serializable = new StringBuilder("OR SENTBEFORE ");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(" SENTON ");
        ((StringBuilder)serializable).append(string2);
        argument.writeAtom(((StringBuilder)serializable).toString());
        return argument;
    }

    private static Argument size(SizeTerm sizeTerm) throws SearchException {
        Argument argument = new Argument();
        int n = sizeTerm.getComparison();
        if (n != 2) {
            if (n != 5) throw new SearchException("Cannot handle Comparison");
            argument.writeAtom("LARGER");
        } else {
            argument.writeAtom("SMALLER");
        }
        argument.writeNumber(sizeTerm.getNumber());
        return argument;
    }

    private static Argument subject(SubjectTerm subjectTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("SUBJECT");
        argument.writeString(subjectTerm.getPattern(), string2);
        return argument;
    }

    private static String toIMAPDate(Date date) {
        StringBuffer stringBuffer = new StringBuffer();
        cal.setTime(date);
        stringBuffer.append(cal.get(5));
        stringBuffer.append("-");
        stringBuffer.append(monthTable[cal.get(2)]);
        stringBuffer.append('-');
        stringBuffer.append(cal.get(1));
        return stringBuffer.toString();
    }
}

