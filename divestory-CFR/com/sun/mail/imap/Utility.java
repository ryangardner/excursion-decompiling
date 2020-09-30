/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.UIDSet;
import java.util.Vector;
import javax.mail.Message;

public final class Utility {
    private Utility() {
    }

    public static MessageSet[] toMessageSet(Message[] arrobject, Condition condition) {
        Vector<Object> vector = new Vector<Object>(1);
        int n = 0;
        do {
            block6 : {
                Object object;
                int n2;
                block7 : {
                    block5 : {
                        block4 : {
                            if (n < arrobject.length) break block4;
                            if (vector.isEmpty()) {
                                return null;
                            }
                            break block5;
                        }
                        object = (IMAPMessage)arrobject[n];
                        if (((Message)object).isExpunged()) break block6;
                        n2 = ((IMAPMessage)object).getSequenceNumber();
                        if (condition != null && !condition.test((IMAPMessage)object)) break block6;
                        object = new MessageSet();
                        ((MessageSet)object).start = n2;
                        break block7;
                    }
                    arrobject = new MessageSet[vector.size()];
                    vector.copyInto(arrobject);
                    return arrobject;
                }
                while (++n < arrobject.length) {
                    IMAPMessage iMAPMessage = (IMAPMessage)arrobject[n];
                    if (iMAPMessage.isExpunged()) continue;
                    int n3 = iMAPMessage.getSequenceNumber();
                    if (condition != null && !condition.test(iMAPMessage)) continue;
                    if (n3 == n2 + 1) {
                        n2 = n3;
                        continue;
                    }
                    --n;
                    break;
                }
                ((MessageSet)object).end = n2;
                vector.addElement(object);
            }
            ++n;
        } while (true);
    }

    public static UIDSet[] toUIDSet(Message[] arrobject) {
        Vector<UIDSet> vector = new Vector<UIDSet>(1);
        int n = 0;
        do {
            block6 : {
                long l;
                UIDSet uIDSet;
                IMAPMessage iMAPMessage;
                block7 : {
                    block5 : {
                        block4 : {
                            if (n < arrobject.length) break block4;
                            if (vector.isEmpty()) {
                                return null;
                            }
                            break block5;
                        }
                        iMAPMessage = (IMAPMessage)arrobject[n];
                        if (iMAPMessage.isExpunged()) break block6;
                        l = iMAPMessage.getUID();
                        uIDSet = new UIDSet();
                        uIDSet.start = l;
                        break block7;
                    }
                    arrobject = new UIDSet[vector.size()];
                    vector.copyInto(arrobject);
                    return arrobject;
                }
                while (++n < arrobject.length) {
                    iMAPMessage = (IMAPMessage)arrobject[n];
                    if (iMAPMessage.isExpunged()) continue;
                    long l2 = iMAPMessage.getUID();
                    if (l2 == 1L + l) {
                        l = l2;
                        continue;
                    }
                    --n;
                    break;
                }
                uIDSet.end = l;
                vector.addElement(uIDSet);
            }
            ++n;
        } while (true);
    }

    public static interface Condition {
        public boolean test(IMAPMessage var1);
    }

}

