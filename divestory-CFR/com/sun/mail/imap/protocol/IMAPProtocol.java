/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.LiteralException;
import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.ACL;
import com.sun.mail.imap.AppendUID;
import com.sun.mail.imap.Rights;
import com.sun.mail.imap.protocol.BASE64MailboxEncoder;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.Namespaces;
import com.sun.mail.imap.protocol.RFC822DATA;
import com.sun.mail.imap.protocol.SaslAuthenticator;
import com.sun.mail.imap.protocol.SearchSequence;
import com.sun.mail.imap.protocol.Status;
import com.sun.mail.imap.protocol.UID;
import com.sun.mail.imap.protocol.UIDSet;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.mail.Flags;
import javax.mail.Quota;
import javax.mail.internet.MimeUtility;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;

public class IMAPProtocol
extends Protocol {
    private static final byte[] CRLF = new byte[]{13, 10};
    private static final byte[] DONE = new byte[]{68, 79, 78, 69, 13, 10};
    private boolean authenticated;
    private List authmechs;
    private ByteArray ba;
    private Map capabilities;
    private boolean connected;
    private String idleTag;
    private String name;
    private boolean rev1;
    private SaslAuthenticator saslAuthenticator;
    private String[] searchCharsets;

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    public IMAPProtocol(String arrstring, String string2, int n, boolean bl, PrintStream printStream, Properties properties, boolean bl2) throws IOException, ProtocolException {
        StringBuilder stringBuilder = new StringBuilder("mail.");
        stringBuilder.append((String)arrstring);
        super(string2, n, bl, printStream, properties, stringBuilder.toString(), bl2);
        this.connected = false;
        this.rev1 = false;
        this.capabilities = null;
        this.authmechs = null;
        try {
            this.name = arrstring;
            if (!false) {
                this.capability();
            }
            if (this.hasCapability("IMAP4rev1")) {
                this.rev1 = true;
            }
            arrstring = new String[2];
            this.searchCharsets = arrstring;
            arrstring[0] = "UTF-8";
            arrstring[1] = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
            this.connected = true;
            return;
        }
        finally {
            if (!true) {
                this.disconnect();
            }
        }
    }

    private void copy(String string2, String string3) throws ProtocolException {
        string3 = BASE64MailboxEncoder.encode(string3);
        Argument argument = new Argument();
        argument.writeAtom(string2);
        argument.writeString(string3);
        this.simpleCommand("COPY", argument);
    }

    private String createFlagList(Flags arrstring) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("(");
        Flags.Flag[] arrflag = arrstring.getSystemFlags();
        boolean bl = true;
        int n = 0;
        do {
            boolean bl2;
            block16 : {
                Object object;
                block11 : {
                    block15 : {
                        block14 : {
                            block13 : {
                                block12 : {
                                    block10 : {
                                        if (n >= arrflag.length) break;
                                        object = arrflag[n];
                                        if (object != Flags.Flag.ANSWERED) break block10;
                                        object = "\\Answered";
                                        break block11;
                                    }
                                    if (object != Flags.Flag.DELETED) break block12;
                                    object = "\\Deleted";
                                    break block11;
                                }
                                if (object != Flags.Flag.DRAFT) break block13;
                                object = "\\Draft";
                                break block11;
                            }
                            if (object != Flags.Flag.FLAGGED) break block14;
                            object = "\\Flagged";
                            break block11;
                        }
                        if (object != Flags.Flag.RECENT) break block15;
                        object = "\\Recent";
                        break block11;
                    }
                    bl2 = bl;
                    if (object != Flags.Flag.SEEN) break block16;
                    object = "\\Seen";
                }
                if (bl) {
                    bl = false;
                } else {
                    stringBuffer.append(' ');
                }
                stringBuffer.append((String)object);
                bl2 = bl;
            }
            ++n;
            bl = bl2;
        } while (true);
        arrstring = arrstring.getUserFlags();
        n = 0;
        do {
            if (n >= arrstring.length) {
                stringBuffer.append(")");
                return stringBuffer.toString();
            }
            if (bl) {
                bl = false;
            } else {
                stringBuffer.append(' ');
            }
            stringBuffer.append(arrstring[n]);
            ++n;
        } while (true);
    }

    private ListInfo[] doList(String string2, String arrobject, String arrlistInfo) throws ProtocolException {
        arrobject = BASE64MailboxEncoder.encode((String)arrobject);
        arrlistInfo = BASE64MailboxEncoder.encode((String)arrlistInfo);
        Object object = new Argument();
        ((Argument)object).writeString((String)arrobject);
        ((Argument)object).writeString((String)arrlistInfo);
        Response[] arrresponse = this.command(string2, (Argument)object);
        arrlistInfo = null;
        Response response = arrresponse[arrresponse.length - 1];
        arrobject = arrlistInfo;
        if (response.isOK()) {
            object = new Vector(1);
            int n = 0;
            int n2 = arrresponse.length;
            do {
                if (n >= n2) {
                    arrobject = arrlistInfo;
                    if (((Vector)object).size() <= 0) break;
                    arrobject = new ListInfo[((Vector)object).size()];
                    ((Vector)object).copyInto(arrobject);
                    break;
                }
                if (arrresponse[n] instanceof IMAPResponse && (arrobject = (IMAPResponse)arrresponse[n]).keyEquals(string2)) {
                    ((Vector)object).addElement(new ListInfo((IMAPResponse)arrobject));
                    arrresponse[n] = null;
                }
                ++n;
            } while (true);
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return arrobject;
    }

    private Response[] fetch(String string2, String string3, boolean bl) throws ProtocolException {
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder("UID FETCH ");
            stringBuilder.append(string2);
            stringBuilder.append(" (");
            stringBuilder.append(string3);
            stringBuilder.append(")");
            return this.command(stringBuilder.toString(), null);
        }
        StringBuilder stringBuilder = new StringBuilder("FETCH ");
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(string3);
        stringBuilder.append(")");
        return this.command(stringBuilder.toString(), null);
    }

    private AppendUID getAppendUID(Response response) {
        byte by;
        if (!response.isOK()) {
            return null;
        }
        while ((by = response.readByte()) > 0 && by != 91) {
        }
        if (by == 0) {
            return null;
        }
        if (response.readAtom().equalsIgnoreCase("APPENDUID")) return new AppendUID(response.readLong(), response.readLong());
        return null;
    }

    private int[] issueSearch(String object, SearchTerm object2, String object3) throws ProtocolException, SearchException, IOException {
        block6 : {
            Object object4 = object3 == null ? null : MimeUtility.javaCharset((String)object3);
            object2 = SearchSequence.generateSequence((SearchTerm)object2, (String)object4);
            ((Argument)object2).writeAtom((String)object);
            if (object3 == null) {
                object = this.command("SEARCH", (Argument)object2);
            } else {
                object = new StringBuilder("SEARCH CHARSET ");
                ((StringBuilder)object).append((String)object3);
                object = this.command(((StringBuilder)object).toString(), (Argument)object2);
            }
            object3 = object[((Object)object).length - 1];
            object2 = null;
            if (!((Response)object3).isOK()) break block6;
            object4 = new Vector();
            int n = ((Object)object).length;
            int n2 = 0;
            int n3 = 0;
            do {
                block9 : {
                    block10 : {
                        block8 : {
                            block7 : {
                                if (n3 < n) break block7;
                                n = ((Vector)object4).size();
                                object2 = new int[n];
                                break block8;
                            }
                            if (!(object[n3] instanceof IMAPResponse) || !((IMAPResponse)(object2 = (IMAPResponse)object[n3])).keyEquals("SEARCH")) break block9;
                            break block10;
                        }
                        for (n3 = n2; n3 < n; ++n3) {
                            object2[n3] = (Integer)((Vector)object4).elementAt(n3);
                        }
                        break;
                    }
                    do {
                        int n4;
                        if ((n4 = ((Response)object2).readNumber()) == -1) {
                            object[n3] = null;
                            break;
                        }
                        ((Vector)object4).addElement(new Integer(n4));
                    } while (true);
                }
                ++n3;
            } while (true);
        }
        this.notifyResponseHandlers((Response[])object);
        this.handleResult((Response)object3);
        return object2;
    }

    private Quota parseQuota(Response response) throws ParsingException {
        Quota quota = new Quota(response.readAtomString());
        response.skipSpaces();
        if (response.readByte() != 40) throw new ParsingException("parse error in QUOTA");
        Vector<Quota.Resource> vector = new Vector<Quota.Resource>();
        do {
            if (response.peekByte() == 41) {
                response.readByte();
                quota.resources = new Quota.Resource[vector.size()];
                vector.copyInto(quota.resources);
                return quota;
            }
            String string2 = response.readAtom();
            if (string2 == null) continue;
            vector.addElement(new Quota.Resource(string2, response.readLong(), response.readLong()));
        } while (true);
    }

    private int[] search(String string2, SearchTerm searchTerm) throws ProtocolException, SearchException {
        Object[] arrobject;
        if (SearchSequence.isAscii(searchTerm)) {
            try {
                return this.issueSearch(string2, searchTerm, null);
            }
            catch (IOException iOException) {}
        }
        int n = 0;
        while (n < (arrobject = this.searchCharsets).length) {
            if (arrobject[n] != null) {
                try {
                    return this.issueSearch(string2, searchTerm, arrobject[n]);
                }
                catch (SearchException searchException) {
                    throw searchException;
                }
                catch (ProtocolException protocolException) {
                    throw protocolException;
                }
                catch (CommandFailedException commandFailedException) {
                    this.searchCharsets[n] = null;
                }
                catch (IOException iOException) {}
            }
            ++n;
        }
        throw new SearchException("Search failed");
    }

    private void storeFlags(String arrresponse, Flags flags, boolean bl) throws ProtocolException {
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder("STORE ");
            stringBuilder.append((String)arrresponse);
            stringBuilder.append(" +FLAGS ");
            stringBuilder.append(this.createFlagList(flags));
            arrresponse = this.command(stringBuilder.toString(), null);
        } else {
            StringBuilder stringBuilder = new StringBuilder("STORE ");
            stringBuilder.append((String)arrresponse);
            stringBuilder.append(" -FLAGS ");
            stringBuilder.append(this.createFlagList(flags));
            arrresponse = this.command(stringBuilder.toString(), null);
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
    }

    public void append(String string2, Flags flags, Date date, Literal literal) throws ProtocolException {
        this.appenduid(string2, flags, date, literal, false);
    }

    public AppendUID appenduid(String string2, Flags flags, Date date, Literal literal) throws ProtocolException {
        return this.appenduid(string2, flags, date, literal, true);
    }

    public AppendUID appenduid(String arrresponse, Flags flags, Date date, Literal literal, boolean bl) throws ProtocolException {
        arrresponse = BASE64MailboxEncoder.encode((String)arrresponse);
        Argument argument = new Argument();
        argument.writeString((String)arrresponse);
        if (flags != null) {
            arrresponse = flags;
            if (flags.contains(Flags.Flag.RECENT)) {
                arrresponse = new Flags(flags);
                arrresponse.remove(Flags.Flag.RECENT);
            }
            argument.writeAtom(this.createFlagList((Flags)arrresponse));
        }
        if (date != null) {
            argument.writeString(INTERNALDATE.format(date));
        }
        argument.writeBytes(literal);
        arrresponse = this.command("APPEND", argument);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
        if (!bl) return null;
        return this.getAppendUID(arrresponse[arrresponse.length - 1]);
    }

    public void authlogin(String arrobject, String string2) throws ProtocolException {
        synchronized (this) {
            Response response;
            boolean bl;
            Object object;
            Vector<Response> vector = new Vector<Response>();
            Object[] arrobject2 = null;
            try {
                object = this.writeCommand("AUTHENTICATE LOGIN", null);
                bl = false;
                response = null;
                arrobject2 = object;
            }
            catch (Exception exception) {
                response = Response.byeResponse(exception);
                bl = true;
            }
            OutputStream outputStream2 = this.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream(byteArrayOutputStream, Integer.MAX_VALUE);
            boolean bl2 = true;
            boolean bl3 = bl;
            do {
                block15 : {
                    if (bl3) {
                        arrobject = new Response[vector.size()];
                        vector.copyInto(arrobject);
                        this.notifyResponseHandlers((Response[])arrobject);
                        this.handleResult(response);
                        this.setCapabilities(response);
                        this.authenticated = true;
                        return;
                    }
                    bl = bl2;
                    try {
                        response = this.readResponse();
                        bl = bl2;
                        if (response.isContinuation()) {
                            if (bl2) {
                                object = arrobject;
                                bl2 = false;
                            } else {
                                object = string2;
                            }
                            bl = bl2;
                            ((OutputStream)bASE64EncoderStream).write(ASCIIUtility.getBytes((String)object));
                            bl = bl2;
                            ((OutputStream)bASE64EncoderStream).flush();
                            bl = bl2;
                            byteArrayOutputStream.write(CRLF);
                            bl = bl2;
                            outputStream2.write(byteArrayOutputStream.toByteArray());
                            bl = bl2;
                            outputStream2.flush();
                            bl = bl2;
                            byteArrayOutputStream.reset();
                            continue;
                        }
                        bl = bl2;
                        if (response.isTagged()) {
                            bl = bl2;
                            if (response.getTag().equals(arrobject2)) break block15;
                        }
                        bl = bl2;
                        if (!response.isBYE()) {
                            bl = bl2;
                            vector.addElement(response);
                            continue;
                        }
                    }
                    catch (Exception exception) {
                        response = Response.byeResponse(exception);
                        bl2 = bl;
                    }
                }
                bl3 = true;
            } while (true);
        }
    }

    public void authplain(String arrobject, String string2, String string3) throws ProtocolException {
        synchronized (this) {
            Object object;
            Response response;
            Vector<Response> vector = new Vector<Response>();
            boolean bl = false;
            String string4 = null;
            try {
                object = this.writeCommand("AUTHENTICATE PLAIN", null);
                response = null;
                string4 = object;
            }
            catch (Exception exception) {
                response = Response.byeResponse(exception);
                bl = true;
            }
            OutputStream outputStream2 = this.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            object = new BASE64EncoderStream(byteArrayOutputStream, Integer.MAX_VALUE);
            do {
                if (bl) {
                    arrobject = new Response[vector.size()];
                    vector.copyInto(arrobject);
                    this.notifyResponseHandlers((Response[])arrobject);
                    this.handleResult(response);
                    this.setCapabilities(response);
                    this.authenticated = true;
                    return;
                }
                try {
                    response = this.readResponse();
                    if (response.isContinuation()) {
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(arrobject));
                        stringBuilder.append("\u0000");
                        stringBuilder.append(string2);
                        stringBuilder.append("\u0000");
                        stringBuilder.append(string3);
                        ((OutputStream)object).write(ASCIIUtility.getBytes(stringBuilder.toString()));
                        ((OutputStream)object).flush();
                        byteArrayOutputStream.write(CRLF);
                        outputStream2.write(byteArrayOutputStream.toByteArray());
                        outputStream2.flush();
                        byteArrayOutputStream.reset();
                        continue;
                    }
                    if (!(response.isTagged() && response.getTag().equals(string4) || response.isBYE())) {
                        vector.addElement(response);
                        continue;
                    }
                }
                catch (Exception exception) {
                    response = Response.byeResponse(exception);
                }
                bl = true;
            } while (true);
        }
    }

    public void capability() throws ProtocolException {
        Response[] arrresponse = this.command("CAPABILITY", null);
        if (!arrresponse[arrresponse.length - 1].isOK()) throw new ProtocolException(arrresponse[arrresponse.length - 1].toString());
        this.capabilities = new HashMap(10);
        this.authmechs = new ArrayList(5);
        int n = 0;
        int n2 = arrresponse.length;
        while (n < n2) {
            IMAPResponse iMAPResponse;
            if (arrresponse[n] instanceof IMAPResponse && (iMAPResponse = (IMAPResponse)arrresponse[n]).keyEquals("CAPABILITY")) {
                this.parseCapabilities(iMAPResponse);
            }
            ++n;
        }
        return;
    }

    public void check() throws ProtocolException {
        this.simpleCommand("CHECK", null);
    }

    public void close() throws ProtocolException {
        this.simpleCommand("CLOSE", null);
    }

    public void copy(int n, int n2, String string2) throws ProtocolException {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(String.valueOf(n)));
        stringBuilder.append(":");
        stringBuilder.append(String.valueOf(n2));
        this.copy(stringBuilder.toString(), string2);
    }

    public void copy(MessageSet[] arrmessageSet, String string2) throws ProtocolException {
        this.copy(MessageSet.toString(arrmessageSet), string2);
    }

    public void create(String object) throws ProtocolException {
        String string2 = BASE64MailboxEncoder.encode((String)object);
        object = new Argument();
        ((Argument)object).writeString(string2);
        this.simpleCommand("CREATE", (Argument)object);
    }

    public void delete(String object) throws ProtocolException {
        String string2 = BASE64MailboxEncoder.encode((String)object);
        object = new Argument();
        ((Argument)object).writeString(string2);
        this.simpleCommand("DELETE", (Argument)object);
    }

    public void deleteACL(String object, String arrresponse) throws ProtocolException {
        if (!this.hasCapability("ACL")) throw new BadCommandException("ACL not supported");
        String string2 = BASE64MailboxEncoder.encode((String)object);
        object = new Argument();
        ((Argument)object).writeString(string2);
        ((Argument)object).writeString((String)arrresponse);
        arrresponse = this.command("DELETEACL", (Argument)object);
        object = arrresponse[arrresponse.length - 1];
        this.notifyResponseHandlers(arrresponse);
        this.handleResult((Response)object);
    }

    @Override
    public void disconnect() {
        super.disconnect();
        this.authenticated = false;
    }

    public MailboxInfo examine(String arrresponse) throws ProtocolException {
        Object object = BASE64MailboxEncoder.encode((String)arrresponse);
        arrresponse = new Argument();
        arrresponse.writeString((String)object);
        arrresponse = this.command("EXAMINE", (Argument)arrresponse);
        object = new MailboxInfo(arrresponse);
        ((MailboxInfo)object).mode = 1;
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
        return object;
    }

    public void expunge() throws ProtocolException {
        this.simpleCommand("EXPUNGE", null);
    }

    public Response[] fetch(int n, int n2, String string2) throws ProtocolException {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(String.valueOf(n)));
        stringBuilder.append(":");
        stringBuilder.append(String.valueOf(n2));
        return this.fetch(stringBuilder.toString(), string2, false);
    }

    public Response[] fetch(int n, String string2) throws ProtocolException {
        return this.fetch(String.valueOf(n), string2, false);
    }

    public Response[] fetch(MessageSet[] arrmessageSet, String string2) throws ProtocolException {
        return this.fetch(MessageSet.toString(arrmessageSet), string2, false);
    }

    public BODY fetchBody(int n, String string2) throws ProtocolException {
        return this.fetchBody(n, string2, false);
    }

    public BODY fetchBody(int n, String string2, int n2, int n3) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, false, null);
    }

    public BODY fetchBody(int n, String string2, int n2, int n3, ByteArray byteArray) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, false, byteArray);
    }

    protected BODY fetchBody(int n, String arrresponse, int n2, int n3, boolean bl, ByteArray object) throws ProtocolException {
        this.ba = object;
        object = bl ? "BODY.PEEK[" : "BODY[";
        StringBuilder stringBuilder = new StringBuilder((String)object);
        object = "]<";
        if (arrresponse == null) {
            arrresponse = object;
        } else {
            arrresponse = new StringBuilder(String.valueOf(arrresponse));
            arrresponse.append("]<");
            arrresponse = arrresponse.toString();
        }
        stringBuilder.append((String)arrresponse);
        stringBuilder.append(String.valueOf(n2));
        stringBuilder.append(".");
        stringBuilder.append(String.valueOf(n3));
        stringBuilder.append(">");
        arrresponse = this.fetch(n, stringBuilder.toString());
        this.notifyResponseHandlers(arrresponse);
        object = arrresponse[arrresponse.length - 1];
        if (((Response)object).isOK()) {
            return (BODY)FetchResponse.getItem(arrresponse, n, BODY.class);
        }
        if (((Response)object).isNO()) {
            return null;
        }
        this.handleResult((Response)object);
        return null;
    }

    protected BODY fetchBody(int n, String charSequence, boolean bl) throws ProtocolException {
        CharSequence charSequence2 = "]";
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder("BODY.PEEK[");
            if (charSequence != null) {
                charSequence = new StringBuilder(String.valueOf(charSequence));
                ((StringBuilder)charSequence).append("]");
                charSequence2 = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence2);
            charSequence = this.fetch(n, stringBuilder.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder("BODY[");
            if (charSequence != null) {
                charSequence = new StringBuilder(String.valueOf(charSequence));
                ((StringBuilder)charSequence).append("]");
                charSequence2 = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence2);
            charSequence = this.fetch(n, stringBuilder.toString());
        }
        this.notifyResponseHandlers((Response[])charSequence);
        charSequence2 = charSequence[((CharSequence)charSequence).length - 1];
        if (((Response)((Object)charSequence2)).isOK()) {
            return (BODY)FetchResponse.getItem((Response[])charSequence, n, BODY.class);
        }
        if (((Response)((Object)charSequence2)).isNO()) {
            return null;
        }
        this.handleResult((Response)((Object)charSequence2));
        return null;
    }

    public BODYSTRUCTURE fetchBodyStructure(int n) throws ProtocolException {
        Response[] arrresponse = this.fetch(n, "BODYSTRUCTURE");
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[arrresponse.length - 1];
        if (response.isOK()) {
            return (BODYSTRUCTURE)FetchResponse.getItem(arrresponse, n, BODYSTRUCTURE.class);
        }
        if (response.isNO()) {
            return null;
        }
        this.handleResult(response);
        return null;
    }

    public Flags fetchFlags(int n) throws ProtocolException {
        Response[] arrresponse = this.fetch(n, "FLAGS");
        int n2 = arrresponse.length;
        Flags flags = null;
        for (int i = 0; i < n2; ++i) {
            Flags flags2 = flags;
            if (arrresponse[i] != null) {
                flags2 = flags;
                if (arrresponse[i] instanceof FetchResponse) {
                    if (((FetchResponse)arrresponse[i]).getNumber() != n) {
                        flags2 = flags;
                    } else {
                        flags2 = flags = (Flags)((Object)((FetchResponse)arrresponse[i]).getItem(Flags.class));
                        if (flags != null) {
                            arrresponse[i] = null;
                            break;
                        }
                    }
                }
            }
            flags = flags2;
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
        return flags;
    }

    public RFC822DATA fetchRFC822(int n, String arrresponse) throws ProtocolException {
        Object object;
        if (arrresponse == null) {
            arrresponse = "RFC822";
        } else {
            object = new StringBuilder("RFC822.");
            ((StringBuilder)object).append((String)arrresponse);
            arrresponse = ((StringBuilder)object).toString();
        }
        arrresponse = this.fetch(n, (String)arrresponse);
        this.notifyResponseHandlers(arrresponse);
        object = arrresponse[arrresponse.length - 1];
        if (((Response)object).isOK()) {
            return (RFC822DATA)FetchResponse.getItem(arrresponse, n, RFC822DATA.class);
        }
        if (((Response)object).isNO()) {
            return null;
        }
        this.handleResult((Response)object);
        return null;
    }

    public UID fetchSequenceNumber(long l) throws ProtocolException {
        Response[] arrresponse = this.fetch(String.valueOf(l), "UID", true);
        int n = arrresponse.length;
        UID uID = null;
        for (int i = 0; i < n; ++i) {
            UID uID2 = uID;
            if (arrresponse[i] != null) {
                if (!(arrresponse[i] instanceof FetchResponse)) {
                    uID2 = uID;
                } else {
                    uID2 = uID = (UID)((FetchResponse)arrresponse[i]).getItem(UID.class);
                    if (uID != null) {
                        if (uID.uid == l) break;
                        uID2 = null;
                    }
                }
            }
            uID = uID2;
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
        return uID;
    }

    public UID[] fetchSequenceNumbers(long l, long l2) throws ProtocolException {
        Object[] arrobject = new StringBuilder(String.valueOf(String.valueOf(l)));
        arrobject.append(":");
        Object object = l2 == -1L ? "*" : String.valueOf(l2);
        arrobject.append((String)object);
        arrobject = this.fetch(arrobject.toString(), "UID", true);
        object = new Vector();
        int n = 0;
        int n2 = arrobject.length;
        do {
            UID uID;
            if (n >= n2) {
                this.notifyResponseHandlers((Response[])arrobject);
                this.handleResult(arrobject[arrobject.length - 1]);
                arrobject = new UID[((Vector)object).size()];
                ((Vector)object).copyInto(arrobject);
                return arrobject;
            }
            if (arrobject[n] != null && arrobject[n] instanceof FetchResponse && (uID = (UID)((FetchResponse)arrobject[n]).getItem(UID.class)) != null) {
                ((Vector)object).addElement(uID);
            }
            ++n;
        } while (true);
    }

    public UID[] fetchSequenceNumbers(long[] object) throws ProtocolException {
        Object object2 = new StringBuffer();
        int n = 0;
        int n2 = 0;
        do {
            if (n2 >= ((long[])object).length) break;
            if (n2 > 0) {
                object2.append(",");
            }
            object2.append(String.valueOf(object[n2]));
            ++n2;
        } while (true);
        Response[] arrresponse = this.fetch(object2.toString(), "UID", true);
        object = new Vector();
        int n3 = arrresponse.length;
        n2 = n;
        do {
            if (n2 >= n3) {
                this.notifyResponseHandlers(arrresponse);
                this.handleResult(arrresponse[arrresponse.length - 1]);
                object2 = new UID[((Vector)object).size()];
                ((Vector)object).copyInto((Object[])object2);
                return object2;
            }
            if (arrresponse[n2] != null && arrresponse[n2] instanceof FetchResponse && (object2 = (UID)((FetchResponse)arrresponse[n2]).getItem(UID.class)) != null) {
                ((Vector)object).addElement(object2);
            }
            ++n2;
        } while (true);
    }

    public UID fetchUID(int n) throws ProtocolException {
        Response[] arrresponse = this.fetch(n, "UID");
        this.notifyResponseHandlers(arrresponse);
        Response response = arrresponse[arrresponse.length - 1];
        if (response.isOK()) {
            return (UID)FetchResponse.getItem(arrresponse, n, UID.class);
        }
        if (response.isNO()) {
            return null;
        }
        this.handleResult(response);
        return null;
    }

    public ACL[] getACL(String object) throws ProtocolException {
        if (!this.hasCapability("ACL")) throw new BadCommandException("ACL not supported");
        object = BASE64MailboxEncoder.encode((String)object);
        Object[] arrobject = new Argument();
        arrobject.writeString((String)object);
        arrobject = this.command("GETACL", (Argument)arrobject);
        Response response = arrobject[arrobject.length - 1];
        object = new Vector();
        if (response.isOK()) {
            int n = arrobject.length;
            block0 : for (int i = 0; i < n; ++i) {
                IMAPResponse iMAPResponse;
                if (!(arrobject[i] instanceof IMAPResponse) || !(iMAPResponse = (IMAPResponse)arrobject[i]).keyEquals("ACL")) continue;
                iMAPResponse.readAtomString();
                do {
                    String string2;
                    String string3;
                    if ((string3 = iMAPResponse.readAtomString()) == null || (string2 = iMAPResponse.readAtomString()) == null) {
                        arrobject[i] = null;
                        continue block0;
                    }
                    ((Vector)object).addElement(new ACL(string3, new Rights(string2)));
                } while (true);
            }
        }
        this.notifyResponseHandlers((Response[])arrobject);
        this.handleResult(response);
        arrobject = new ACL[((Vector)object).size()];
        ((Vector)object).copyInto(arrobject);
        return arrobject;
    }

    public Map getCapabilities() {
        return this.capabilities;
    }

    OutputStream getIMAPOutputStream() {
        return this.getOutputStream();
    }

    public Quota[] getQuota(String object) throws ProtocolException {
        if (!this.hasCapability("QUOTA")) throw new BadCommandException("QUOTA not supported");
        Object object2 = new Argument();
        ((Argument)object2).writeString((String)object);
        Response[] arrresponse = this.command("GETQUOTA", (Argument)object2);
        object = new Vector();
        Response response = arrresponse[arrresponse.length - 1];
        if (response.isOK()) {
            int n = arrresponse.length;
            for (int i = 0; i < n; ++i) {
                if (!(arrresponse[i] instanceof IMAPResponse) || !((IMAPResponse)(object2 = (IMAPResponse)arrresponse[i])).keyEquals("QUOTA")) continue;
                ((Vector)object).addElement(this.parseQuota((Response)object2));
                arrresponse[i] = null;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        object2 = new Quota[((Vector)object).size()];
        ((Vector)object).copyInto((Object[])object2);
        return object2;
    }

    public Quota[] getQuotaRoot(String enumeration) throws ProtocolException {
        int n;
        if (!this.hasCapability("QUOTA")) throw new BadCommandException("GETQUOTAROOT not supported");
        enumeration = BASE64MailboxEncoder.encode((String)((Object)enumeration));
        Object[] arrobject = new Argument();
        arrobject.writeString((String)((Object)enumeration));
        arrobject = this.command("GETQUOTAROOT", (Argument)arrobject);
        Response response = arrobject[arrobject.length - 1];
        enumeration = new Hashtable();
        boolean bl = response.isOK();
        int n2 = 0;
        if (bl) {
            int n3 = arrobject.length;
            n = 0;
            do {
                if (n >= n3) break;
                if (arrobject[n] instanceof IMAPResponse) {
                    Object object;
                    Object object2 = (IMAPResponse)arrobject[n];
                    if (!object2.keyEquals("QUOTAROOT")) {
                        if (object2.keyEquals("QUOTA")) {
                            object = this.parseQuota((Response)object2);
                            object2 = (Quota)((Hashtable)((Object)enumeration)).get(((Quota)object).quotaRoot);
                            if (object2 != null) {
                                object2 = object2.resources;
                            }
                            ((Hashtable)((Object)enumeration)).put(((Quota)object).quotaRoot, object);
                            arrobject[n] = null;
                        }
                    } else {
                        object2.readAtomString();
                        do {
                            if ((object = object2.readAtomString()) == null) {
                                arrobject[n] = null;
                                break;
                            }
                            ((Hashtable)((Object)enumeration)).put(object, new Quota((String)object));
                        } while (true);
                    }
                }
                ++n;
            } while (true);
        }
        this.notifyResponseHandlers((Response[])arrobject);
        this.handleResult(response);
        arrobject = new Quota[((Hashtable)((Object)enumeration)).size()];
        enumeration = ((Hashtable)((Object)enumeration)).elements();
        n = n2;
        while (enumeration.hasMoreElements()) {
            arrobject[n] = (Quota)enumeration.nextElement();
            ++n;
        }
        return arrobject;
    }

    @Override
    protected ByteArray getResponseBuffer() {
        ByteArray byteArray = this.ba;
        this.ba = null;
        return byteArray;
    }

    public boolean hasCapability(String string2) {
        return this.capabilities.containsKey(string2.toUpperCase(Locale.ENGLISH));
    }

    public void idleAbort() throws ProtocolException {
        OutputStream outputStream2 = this.getOutputStream();
        try {
            outputStream2.write(DONE);
            outputStream2.flush();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public void idleStart() throws ProtocolException {
        synchronized (this) {
            Response response;
            boolean bl = this.hasCapability("IDLE");
            if (!bl) {
                BadCommandException badCommandException = new BadCommandException("IDLE not supported");
                throw badCommandException;
            }
            try {
                this.idleTag = this.writeCommand("IDLE", null);
                response = this.readResponse();
            }
            catch (Exception exception) {
                response = Response.byeResponse(exception);
            }
            catch (LiteralException literalException) {
                response = literalException.getResponse();
            }
            if (response.isContinuation()) return;
            this.handleResult(response);
            return;
        }
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public boolean isREV1() {
        return this.rev1;
    }

    public ListInfo[] list(String string2, String string3) throws ProtocolException {
        return this.doList("LIST", string2, string3);
    }

    public Rights[] listRights(String object, String object2) throws ProtocolException {
        if (!this.hasCapability("ACL")) throw new BadCommandException("ACL not supported");
        object = BASE64MailboxEncoder.encode((String)object);
        Object object3 = new Argument();
        ((Argument)object3).writeString((String)object);
        ((Argument)object3).writeString((String)object2);
        Response[] arrresponse = this.command("LISTRIGHTS", (Argument)object3);
        object3 = arrresponse[arrresponse.length - 1];
        object = new Vector();
        if (((Response)object3).isOK()) {
            int n = arrresponse.length;
            block0 : for (int i = 0; i < n; ++i) {
                if (!(arrresponse[i] instanceof IMAPResponse) || !((IMAPResponse)(object2 = (IMAPResponse)arrresponse[i])).keyEquals("LISTRIGHTS")) continue;
                ((Response)object2).readAtomString();
                ((Response)object2).readAtomString();
                do {
                    String string2;
                    if ((string2 = ((Response)object2).readAtomString()) == null) {
                        arrresponse[i] = null;
                        continue block0;
                    }
                    ((Vector)object).addElement(new Rights(string2));
                } while (true);
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult((Response)object3);
        object2 = new Rights[((Vector)object).size()];
        ((Vector)object).copyInto((Object[])object2);
        return object2;
    }

    public void login(String arrresponse, String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString((String)arrresponse);
        argument.writeString(string2);
        arrresponse = this.command("LOGIN", argument);
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(arrresponse[arrresponse.length - 1]);
        this.setCapabilities(arrresponse[arrresponse.length - 1]);
        this.authenticated = true;
    }

    public void logout() throws ProtocolException {
        Response[] arrresponse = this.command("LOGOUT", null);
        this.authenticated = false;
        this.notifyResponseHandlers(arrresponse);
        this.disconnect();
    }

    public ListInfo[] lsub(String string2, String string3) throws ProtocolException {
        return this.doList("LSUB", string2, string3);
    }

    public Rights myRights(String object) throws ProtocolException {
        if (!this.hasCapability("ACL")) throw new BadCommandException("ACL not supported");
        object = BASE64MailboxEncoder.encode((String)object);
        Object object2 = new Argument();
        ((Argument)object2).writeString((String)object);
        Response[] arrresponse = this.command("MYRIGHTS", (Argument)object2);
        Response response = arrresponse[arrresponse.length - 1];
        boolean bl = response.isOK();
        object = null;
        if (bl) {
            int n = arrresponse.length;
            object = null;
            for (int i = 0; i < n; ++i) {
                if (!(arrresponse[i] instanceof IMAPResponse)) {
                    object2 = object;
                } else {
                    Object object3 = (IMAPResponse)arrresponse[i];
                    object2 = object;
                    if (((IMAPResponse)object3).keyEquals("MYRIGHTS")) {
                        ((Response)object3).readAtomString();
                        object3 = ((Response)object3).readAtomString();
                        object2 = object;
                        if (object == null) {
                            object2 = new Rights((String)object3);
                        }
                        arrresponse[i] = null;
                    }
                }
                object = object2;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return object;
    }

    public Namespaces namespace() throws ProtocolException {
        if (!this.hasCapability("NAMESPACE")) throw new BadCommandException("NAMESPACE not supported");
        Namespaces namespaces = null;
        Response[] arrresponse = this.command("NAMESPACE", null);
        Response response = arrresponse[arrresponse.length - 1];
        if (response.isOK()) {
            int n = arrresponse.length;
            namespaces = null;
            for (int i = 0; i < n; ++i) {
                Namespaces namespaces2;
                if (!(arrresponse[i] instanceof IMAPResponse)) {
                    namespaces2 = namespaces;
                } else {
                    IMAPResponse iMAPResponse = (IMAPResponse)arrresponse[i];
                    namespaces2 = namespaces;
                    if (iMAPResponse.keyEquals("NAMESPACE")) {
                        namespaces2 = namespaces;
                        if (namespaces == null) {
                            namespaces2 = new Namespaces(iMAPResponse);
                        }
                        arrresponse[i] = null;
                    }
                }
                namespaces = namespaces2;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult(response);
        return namespaces;
    }

    public void noop() throws ProtocolException {
        if (this.debug) {
            this.out.println("IMAP DEBUG: IMAPProtocol noop");
        }
        this.simpleCommand("NOOP", null);
    }

    protected void parseCapabilities(Response response) {
        String string2;
        while ((string2 = response.readAtom(']')) != null) {
            if (string2.length() == 0) {
                if (response.peekByte() == 93) {
                    return;
                }
                response.skipToken();
                continue;
            }
            this.capabilities.put(string2.toUpperCase(Locale.ENGLISH), string2);
            if (!string2.regionMatches(true, 0, "AUTH=", 0, 5)) continue;
            this.authmechs.add(string2.substring(5));
            if (!this.debug) continue;
            PrintStream printStream = this.out;
            StringBuilder stringBuilder = new StringBuilder("IMAP DEBUG: AUTH: ");
            stringBuilder.append(string2.substring(5));
            printStream.println(stringBuilder.toString());
        }
        return;
    }

    public BODY peekBody(int n, String string2) throws ProtocolException {
        return this.fetchBody(n, string2, true);
    }

    public BODY peekBody(int n, String string2, int n2, int n3) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, true, null);
    }

    public BODY peekBody(int n, String string2, int n2, int n3, ByteArray byteArray) throws ProtocolException {
        return this.fetchBody(n, string2, n2, n3, true, byteArray);
    }

    @Override
    protected void processGreeting(Response response) throws ProtocolException {
        super.processGreeting(response);
        if (response.isOK()) {
            this.setCapabilities(response);
            return;
        }
        if (!((IMAPResponse)response).keyEquals("PREAUTH")) throw new ConnectionException(this, response);
        this.authenticated = true;
        this.setCapabilities(response);
    }

    public boolean processIdleResponse(Response response) throws ProtocolException {
        boolean bl;
        this.notifyResponseHandlers(new Response[]{response});
        boolean bl2 = bl = response.isBYE();
        if (response.isTagged()) {
            bl2 = bl;
            if (response.getTag().equals(this.idleTag)) {
                bl2 = true;
            }
        }
        if (bl2) {
            this.idleTag = null;
        }
        this.handleResult(response);
        return bl2 ^ true;
    }

    public void proxyauth(String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(string2);
        this.simpleCommand("PROXYAUTH", argument);
    }

    public Response readIdleResponse() {
        synchronized (this) {
            Object object = this.idleTag;
            if (object == null) {
                return null;
            }
            try {
                object = this.readResponse();
            }
            catch (ProtocolException protocolException) {
                object = Response.byeResponse(protocolException);
            }
            catch (IOException iOException) {
                object = Response.byeResponse(iOException);
            }
            return object;
        }
    }

    @Override
    public Response readResponse() throws IOException, ProtocolException {
        return IMAPResponse.readResponse(this);
    }

    public void rename(String string2, String string3) throws ProtocolException {
        string2 = BASE64MailboxEncoder.encode(string2);
        string3 = BASE64MailboxEncoder.encode(string3);
        Argument argument = new Argument();
        argument.writeString(string2);
        argument.writeString(string3);
        this.simpleCommand("RENAME", argument);
    }

    public void sasllogin(String[] object, String charSequence, String object2, String string2, String string3) throws ProtocolException {
        Object object3 = this.saslAuthenticator;
        int n = 0;
        if (object3 == null) {
            try {
                Constructor<?> constructor = Class.forName("com.sun.mail.imap.protocol.IMAPSaslAuthenticator").getConstructor(IMAPProtocol.class, String.class, Properties.class, Boolean.TYPE, PrintStream.class, String.class);
                String string4 = this.name;
                Properties properties = this.props;
                object3 = this.debug ? Boolean.TRUE : Boolean.FALSE;
                this.saslAuthenticator = (SaslAuthenticator)constructor.newInstance(this, string4, properties, object3, this.out, this.host);
            }
            catch (Exception exception) {
                if (!this.debug) return;
                object2 = this.out;
                charSequence = new StringBuilder("IMAP DEBUG: Can't load SASL authenticator: ");
                ((StringBuilder)charSequence).append(exception);
                ((PrintStream)object2).println(((StringBuilder)charSequence).toString());
                return;
            }
        }
        if (object == null || ((String[])object).length <= 0) {
            object = this.authmechs;
        } else {
            object3 = new ArrayList(((Object)object).length);
            do {
                if (n >= ((Object)object).length) {
                    object = object3;
                    break;
                }
                if (this.authmechs.contains(object[n])) {
                    object3.add(object[n]);
                }
                ++n;
            } while (true);
        }
        object = object.toArray(new String[object.size()]);
        if (!this.saslAuthenticator.authenticate((String[])object, (String)charSequence, (String)object2, string2, string3)) return;
        this.authenticated = true;
    }

    public int[] search(SearchTerm searchTerm) throws ProtocolException, SearchException {
        return this.search("ALL", searchTerm);
    }

    public int[] search(MessageSet[] arrmessageSet, SearchTerm searchTerm) throws ProtocolException, SearchException {
        return this.search(MessageSet.toString(arrmessageSet), searchTerm);
    }

    public MailboxInfo select(String object) throws ProtocolException {
        object = BASE64MailboxEncoder.encode((String)object);
        Object object2 = new Argument();
        ((Argument)object2).writeString((String)object);
        object2 = this.command("SELECT", (Argument)object2);
        object = new MailboxInfo((Response[])object2);
        this.notifyResponseHandlers((Response[])object2);
        object2 = object2[((Response[])object2).length - 1];
        if (((Response)object2).isOK()) {
            ((MailboxInfo)object).mode = ((Response)object2).toString().indexOf("READ-ONLY") != -1 ? 1 : 2;
        }
        this.handleResult((Response)object2);
        return object;
    }

    public void setACL(String object, char c, ACL arrresponse) throws ProtocolException {
        Argument argument;
        block3 : {
            block2 : {
                if (!this.hasCapability("ACL")) throw new BadCommandException("ACL not supported");
                object = BASE64MailboxEncoder.encode((String)object);
                argument = new Argument();
                argument.writeString((String)object);
                argument.writeString(arrresponse.getName());
                arrresponse = arrresponse.getRights().toString();
                if (c == '+') break block2;
                object = arrresponse;
                if (c != '-') break block3;
            }
            object = new StringBuilder(String.valueOf(c));
            ((StringBuilder)object).append((String)arrresponse);
            object = ((StringBuilder)object).toString();
        }
        argument.writeString((String)object);
        arrresponse = this.command("SETACL", argument);
        object = arrresponse[arrresponse.length - 1];
        this.notifyResponseHandlers(arrresponse);
        this.handleResult((Response)object);
    }

    protected void setCapabilities(Response response) {
        byte by;
        while ((by = response.readByte()) > 0 && by != 91) {
        }
        if (by == 0) {
            return;
        }
        if (!response.readAtom().equalsIgnoreCase("CAPABILITY")) {
            return;
        }
        this.capabilities = new HashMap(10);
        this.authmechs = new ArrayList(5);
        this.parseCapabilities(response);
    }

    public void setQuota(Quota arrresponse) throws ProtocolException {
        if (!this.hasCapability("QUOTA")) throw new BadCommandException("QUOTA not supported");
        Object object = new Argument();
        ((Argument)object).writeString(arrresponse.quotaRoot);
        Argument argument = new Argument();
        if (arrresponse.resources != null) {
            for (int i = 0; i < arrresponse.resources.length; ++i) {
                argument.writeAtom(arrresponse.resources[i].name);
                argument.writeNumber(arrresponse.resources[i].limit);
            }
        }
        ((Argument)object).writeArgument(argument);
        arrresponse = this.command("SETQUOTA", (Argument)object);
        object = arrresponse[arrresponse.length - 1];
        this.notifyResponseHandlers(arrresponse);
        this.handleResult((Response)object);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public void startTLS() throws ProtocolException {
        try {
            super.startTLS("STARTTLS");
            return;
        }
        catch (Exception exception) {
            this.notifyResponseHandlers(new Response[]{Response.byeResponse(exception)});
            this.disconnect();
        }
        return;
        catch (ProtocolException protocolException) {
            throw protocolException;
        }
    }

    public Status status(String object, String[] arrstring) throws ProtocolException {
        Object object2;
        Response[] arrresponse;
        block11 : {
            if (!this.isREV1()) {
                if (!this.hasCapability("IMAP4SUNVERSION")) throw new BadCommandException("STATUS not supported");
            }
            object = BASE64MailboxEncoder.encode((String)object);
            arrresponse = new Argument();
            arrresponse.writeString((String)object);
            object2 = new Argument();
            object = arrstring;
            if (arrstring == null) {
                object = Status.standardItems;
            }
            int n = ((String[])object).length;
            int n2 = 0;
            int n3 = 0;
            do {
                if (n3 >= n) {
                    arrresponse.writeArgument((Argument)object2);
                    arrresponse = this.command("STATUS", (Argument)arrresponse);
                    object2 = arrresponse[arrresponse.length - 1];
                    boolean bl = ((Response)object2).isOK();
                    object = null;
                    if (bl) {
                        n = arrresponse.length;
                        object = null;
                        break;
                    }
                    break block11;
                }
                ((Argument)object2).writeAtom(object[n3]);
                ++n3;
            } while (true);
            for (n3 = n2; n3 < n; ++n3) {
                if (!(arrresponse[n3] instanceof IMAPResponse)) {
                    arrstring = object;
                } else {
                    IMAPResponse iMAPResponse = (IMAPResponse)arrresponse[n3];
                    arrstring = object;
                    if (iMAPResponse.keyEquals("STATUS")) {
                        if (object == null) {
                            object = new Status(iMAPResponse);
                        } else {
                            Status.add((Status)object, new Status(iMAPResponse));
                        }
                        arrresponse[n3] = null;
                        arrstring = object;
                    }
                }
                object = arrstring;
            }
        }
        this.notifyResponseHandlers(arrresponse);
        this.handleResult((Response)object2);
        return object;
    }

    public void storeFlags(int n, int n2, Flags flags, boolean bl) throws ProtocolException {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(String.valueOf(n)));
        stringBuilder.append(":");
        stringBuilder.append(String.valueOf(n2));
        this.storeFlags(stringBuilder.toString(), flags, bl);
    }

    public void storeFlags(int n, Flags flags, boolean bl) throws ProtocolException {
        this.storeFlags(String.valueOf(n), flags, bl);
    }

    public void storeFlags(MessageSet[] arrmessageSet, Flags flags, boolean bl) throws ProtocolException {
        this.storeFlags(MessageSet.toString(arrmessageSet), flags, bl);
    }

    public void subscribe(String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(BASE64MailboxEncoder.encode(string2));
        this.simpleCommand("SUBSCRIBE", argument);
    }

    @Override
    protected boolean supportsNonSyncLiterals() {
        return this.hasCapability("LITERAL+");
    }

    public void uidexpunge(UIDSet[] arruIDSet) throws ProtocolException {
        if (!this.hasCapability("UIDPLUS")) throw new BadCommandException("UID EXPUNGE not supported");
        StringBuilder stringBuilder = new StringBuilder("UID EXPUNGE ");
        stringBuilder.append(UIDSet.toString(arruIDSet));
        this.simpleCommand(stringBuilder.toString(), null);
    }

    public void unsubscribe(String string2) throws ProtocolException {
        Argument argument = new Argument();
        argument.writeString(BASE64MailboxEncoder.encode(string2));
        this.simpleCommand("UNSUBSCRIBE", argument);
    }
}

