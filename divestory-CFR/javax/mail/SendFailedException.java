/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.Address;
import javax.mail.MessagingException;

public class SendFailedException
extends MessagingException {
    private static final long serialVersionUID = -6457531621682372913L;
    protected transient Address[] invalid;
    protected transient Address[] validSent;
    protected transient Address[] validUnsent;

    public SendFailedException() {
    }

    public SendFailedException(String string2) {
        super(string2);
    }

    public SendFailedException(String string2, Exception exception) {
        super(string2, exception);
    }

    public SendFailedException(String string2, Exception exception, Address[] arraddress, Address[] arraddress2, Address[] arraddress3) {
        super(string2, exception);
        this.validSent = arraddress;
        this.validUnsent = arraddress2;
        this.invalid = arraddress3;
    }

    public Address[] getInvalidAddresses() {
        return this.invalid;
    }

    public Address[] getValidSentAddresses() {
        return this.validSent;
    }

    public Address[] getValidUnsentAddresses() {
        return this.validUnsent;
    }
}

