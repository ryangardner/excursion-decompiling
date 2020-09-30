/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.SaslAuthenticator;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.Vector;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.ChoiceCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

public class IMAPSaslAuthenticator
implements SaslAuthenticator {
    private boolean debug;
    private String host;
    private String name;
    private PrintStream out;
    private IMAPProtocol pr;
    private Properties props;

    public IMAPSaslAuthenticator(IMAPProtocol iMAPProtocol, String string2, Properties properties, boolean bl, PrintStream printStream, String string3) {
        this.pr = iMAPProtocol;
        this.name = string2;
        this.props = properties;
        this.debug = bl;
        this.out = printStream;
        this.host = string3;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public boolean authenticate(String[] object, String object22, String object32, String object4, String object5) throws ProtocolException {
        IMAPProtocol iMAPProtocol = this.pr;
        synchronized (iMAPProtocol) {
            SaslClient saslClient;
            Object object6;
            CallbackHandler callbackHandler;
            OutputStream outputStream2;
            Vector<Object> vector;
            ByteArrayOutputStream byteArrayOutputStream;
            Object object7;
            int n;
            Object[] arrobject;
            block26 : {
                vector = new Vector<Object>();
                if (this.debug) {
                    this.out.print("IMAP SASL DEBUG: Mechanisms:");
                    n = 0;
                    do {
                        if (n >= ((Object)object).length) {
                            this.out.println();
                            break;
                        }
                        object7 = this.out;
                        callbackHandler = new StringBuilder(" ");
                        ((StringBuilder)((Object)callbackHandler)).append((String)object[n]);
                        ((PrintStream)object7).print(((StringBuilder)((Object)callbackHandler)).toString());
                        ++n;
                    } while (true);
                }
                callbackHandler = new CallbackHandler((String)((Object)saslClient), (String)((Object)byteArrayOutputStream), (String)arrobject){
                    private final /* synthetic */ String val$p0;
                    private final /* synthetic */ String val$r0;
                    private final /* synthetic */ String val$u0;
                    {
                        this.val$u0 = string2;
                        this.val$p0 = string3;
                        this.val$r0 = string4;
                    }

                    @Override
                    public void handle(Callback[] arrcallback) {
                        Serializable serializable;
                        Object object;
                        if (IMAPSaslAuthenticator.this.debug) {
                            object = IMAPSaslAuthenticator.this.out;
                            serializable = new StringBuilder("IMAP SASL DEBUG: callback length: ");
                            ((StringBuilder)serializable).append(arrcallback.length);
                            ((PrintStream)object).println(((StringBuilder)serializable).toString());
                        }
                        int n = 0;
                        while (n < arrcallback.length) {
                            if (IMAPSaslAuthenticator.this.debug) {
                                object = IMAPSaslAuthenticator.this.out;
                                serializable = new StringBuilder("IMAP SASL DEBUG: callback ");
                                ((StringBuilder)serializable).append(n);
                                ((StringBuilder)serializable).append(": ");
                                ((StringBuilder)serializable).append(arrcallback[n]);
                                ((PrintStream)object).println(((StringBuilder)serializable).toString());
                            }
                            if (arrcallback[n] instanceof NameCallback) {
                                ((NameCallback)arrcallback[n]).setName(this.val$u0);
                            } else if (arrcallback[n] instanceof PasswordCallback) {
                                ((PasswordCallback)arrcallback[n]).setPassword(this.val$p0.toCharArray());
                            } else if (arrcallback[n] instanceof RealmCallback) {
                                serializable = (RealmCallback)arrcallback[n];
                                object = this.val$r0;
                                if (object == null) {
                                    object = ((TextInputCallback)serializable).getDefaultText();
                                }
                                ((TextInputCallback)serializable).setText((String)object);
                            } else if (arrcallback[n] instanceof RealmChoiceCallback) {
                                object = (RealmChoiceCallback)arrcallback[n];
                                if (this.val$r0 == null) {
                                    ((ChoiceCallback)object).setSelectedIndex(((ChoiceCallback)object).getDefaultChoice());
                                } else {
                                    serializable = ((ChoiceCallback)object).getChoices();
                                    for (int i = 0; i < ((Serializable)serializable).length; ++i) {
                                        if (!((String)((Object)serializable[i])).equals(this.val$r0)) continue;
                                        ((ChoiceCallback)object).setSelectedIndex(i);
                                        break;
                                    }
                                }
                            }
                            ++n;
                        }
                        return;
                    }
                };
                try {
                    saslClient = Sasl.createSaslClient((String[])object, (String)object6, this.name, this.host, this.props, callbackHandler);
                    if (saslClient != null) break block26;
                    if (!this.debug) return false;
                    this.out.println("IMAP SASL DEBUG: No SASL support");
                }
                catch (SaslException object32) {
                    if (!this.debug) return false;
                    arrobject = this.out;
                    object = new StringBuilder("IMAP SASL DEBUG: Failed to create SASL client: ");
                    ((StringBuilder)object).append(object32);
                    arrobject.println(((StringBuilder)object).toString());
                    return false;
                }
                return false;
            }
            if (this.debug) {
                object = this.out;
                arrobject = new StringBuilder("IMAP SASL DEBUG: SASL client ");
                arrobject.append(saslClient.getMechanismName());
                ((PrintStream)object).println(arrobject.toString());
            }
            try {
                arrobject = this.pr;
                object = new StringBuilder("AUTHENTICATE ");
                ((StringBuilder)object).append(saslClient.getMechanismName());
                object7 = arrobject.writeCommand(((StringBuilder)object).toString(), null);
                outputStream2 = this.pr.getIMAPOutputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
            }
            catch (Exception object22) {
                if (!this.debug) return false;
                object6 = this.out;
                object = new StringBuilder("IMAP SASL DEBUG: AUTHENTICATE Exception: ");
                ((StringBuilder)object).append(object22);
                ((PrintStream)object6).println(((StringBuilder)object).toString());
                return false;
            }
            callbackHandler = new CallbackHandler[]{(byte)13, (byte)10};
            boolean bl = saslClient.getMechanismName().equals("XGWTRUSTEDAPP");
            object = null;
            n = 0;
            do {
                if (n != 0) {
                    if (!saslClient.isComplete() || (arrobject = (String)saslClient.getNegotiatedProperty("javax.security.sasl.qop")) == null || !arrobject.equalsIgnoreCase("auth-int") && !arrobject.equalsIgnoreCase("auth-conf")) break;
                    if (!this.debug) return false;
                    this.out.println("IMAP SASL DEBUG: Mechanism requires integrity or confidentiality");
                    return false;
                }
                try {
                    object = this.pr.readResponse();
                    if (((Response)object).isContinuation()) {
                        Appendable appendable;
                        arrobject = null;
                        if (!saslClient.isComplete()) {
                            object6 = ((Response)object).readByteArray().getNewBytes();
                            arrobject = object6;
                            if (((byte[])object6).length > 0) {
                                arrobject = BASE64DecoderStream.decode((byte[])object6);
                            }
                            if (this.debug) {
                                object6 = this.out;
                                appendable = new StringBuilder("IMAP SASL DEBUG: challenge: ");
                                ((StringBuilder)appendable).append(ASCIIUtility.toString((byte[])arrobject, 0, arrobject.length));
                                ((StringBuilder)appendable).append(" :");
                                ((PrintStream)object6).println(((StringBuilder)appendable).toString());
                            }
                            arrobject = saslClient.evaluateChallenge((byte[])arrobject);
                        }
                        if (arrobject == null) {
                            if (this.debug) {
                                this.out.println("IMAP SASL DEBUG: no response");
                            }
                            outputStream2.write((byte[])callbackHandler);
                            outputStream2.flush();
                            byteArrayOutputStream.reset();
                            continue;
                        }
                        if (this.debug) {
                            appendable = this.out;
                            object6 = new StringBuilder("IMAP SASL DEBUG: response: ");
                            ((StringBuilder)object6).append(ASCIIUtility.toString((byte[])arrobject, 0, arrobject.length));
                            ((StringBuilder)object6).append(" :");
                            ((PrintStream)appendable).println(((StringBuilder)object6).toString());
                        }
                        arrobject = BASE64EncoderStream.encode((byte[])arrobject);
                        if (bl) {
                            byteArrayOutputStream.write("XGWTRUSTEDAPP ".getBytes());
                        }
                        byteArrayOutputStream.write((byte[])arrobject);
                        byteArrayOutputStream.write((byte[])callbackHandler);
                        outputStream2.write(byteArrayOutputStream.toByteArray());
                        outputStream2.flush();
                        byteArrayOutputStream.reset();
                        continue;
                    }
                    if (!(((Response)object).isTagged() && ((Response)object).getTag().equals(object7) || ((Response)object).isBYE())) {
                        vector.addElement(object);
                        continue;
                    }
                }
                catch (Exception exception) {
                    if (this.debug) {
                        exception.printStackTrace();
                    }
                    object = Response.byeResponse(exception);
                }
                n = 1;
            } while (true);
            arrobject = new Response[vector.size()];
            vector.copyInto(arrobject);
            this.pr.notifyResponseHandlers((Response[])arrobject);
            this.pr.handleResult((Response)object);
            this.pr.setCapabilities((Response)object);
            return true;
        }
    }

}

