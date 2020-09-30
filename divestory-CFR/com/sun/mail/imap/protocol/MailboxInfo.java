/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.FLAGS;
import com.sun.mail.imap.protocol.IMAPResponse;
import javax.mail.Flags;

public class MailboxInfo {
    public Flags availableFlags = null;
    public int first = -1;
    public int mode;
    public Flags permanentFlags = null;
    public int recent = -1;
    public int total = -1;
    public long uidnext = -1L;
    public long uidvalidity = -1L;

    /*
     * Unable to fully structure code
     */
    public MailboxInfo(Response[] var1_1) throws ParsingException {
        super();
        var2_2 = 0;
        do {
            block6 : {
                block13 : {
                    block12 : {
                        block11 : {
                            block10 : {
                                block9 : {
                                    block8 : {
                                        block7 : {
                                            if (var2_2 >= var1_1.length) {
                                                if (this.permanentFlags != null) return;
                                                if (this.availableFlags == null) break;
                                                this.permanentFlags = new Flags(this.availableFlags);
                                                return;
                                            }
                                            if (var1_1[var2_2] == null || !(var1_1[var2_2] instanceof IMAPResponse)) break block6;
                                            var3_3 = (IMAPResponse)var1_1[var2_2];
                                            if (!var3_3.keyEquals("EXISTS")) break block7;
                                            this.total = var3_3.getNumber();
                                            var1_1[var2_2] = null;
                                            break block6;
                                        }
                                        if (!var3_3.keyEquals("RECENT")) break block8;
                                        this.recent = var3_3.getNumber();
                                        var1_1[var2_2] = null;
                                        break block6;
                                    }
                                    if (!var3_3.keyEquals("FLAGS")) break block9;
                                    this.availableFlags = new FLAGS(var3_3);
                                    var1_1[var2_2] = null;
                                    break block6;
                                }
                                if (!var3_3.isUnTagged() || !var3_3.isOK()) break block6;
                                var3_3.skipSpaces();
                                if (var3_3.readByte() == 91) break block10;
                                var3_3.reset();
                                break block6;
                            }
                            var4_4 = var3_3.readAtom();
                            if (!var4_4.equalsIgnoreCase("UNSEEN")) break block11;
                            this.first = var3_3.readNumber();
                            ** GOTO lbl54
                        }
                        if (!var4_4.equalsIgnoreCase("UIDVALIDITY")) break block12;
                        this.uidvalidity = var3_3.readLong();
                        ** GOTO lbl54
                    }
                    if (!var4_4.equalsIgnoreCase("PERMANENTFLAGS")) break block13;
                    this.permanentFlags = new FLAGS(var3_3);
                    ** GOTO lbl54
                }
                if (var4_4.equalsIgnoreCase("UIDNEXT")) {
                    this.uidnext = var3_3.readLong();
lbl54: // 4 sources:
                    var5_5 = true;
                } else {
                    var5_5 = false;
                }
                if (var5_5) {
                    var1_1[var2_2] = null;
                } else {
                    var3_3.reset();
                }
            }
            ++var2_2;
        } while (true);
        this.permanentFlags = new Flags();
    }
}

