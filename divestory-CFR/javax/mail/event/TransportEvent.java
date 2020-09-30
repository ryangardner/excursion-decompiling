/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.event.MailEvent;
import javax.mail.event.TransportListener;

public class TransportEvent
extends MailEvent {
    public static final int MESSAGE_DELIVERED = 1;
    public static final int MESSAGE_NOT_DELIVERED = 2;
    public static final int MESSAGE_PARTIALLY_DELIVERED = 3;
    private static final long serialVersionUID = -4729852364684273073L;
    protected transient Address[] invalid;
    protected transient Message msg;
    protected int type;
    protected transient Address[] validSent;
    protected transient Address[] validUnsent;

    public TransportEvent(Transport transport, int n, Address[] arraddress, Address[] arraddress2, Address[] arraddress3, Message message) {
        super(transport);
        this.type = n;
        this.validSent = arraddress;
        this.validUnsent = arraddress2;
        this.invalid = arraddress3;
        this.msg = message;
    }

    @Override
    public void dispatch(Object object) {
        int n = this.type;
        if (n == 1) {
            ((TransportListener)object).messageDelivered(this);
            return;
        }
        if (n == 2) {
            ((TransportListener)object).messageNotDelivered(this);
            return;
        }
        ((TransportListener)object).messagePartiallyDelivered(this);
    }

    public Address[] getInvalidAddresses() {
        return this.invalid;
    }

    public Message getMessage() {
        return this.msg;
    }

    public int getType() {
        return this.type;
    }

    public Address[] getValidSentAddresses() {
        return this.validSent;
    }

    public Address[] getValidUnsentAddresses() {
        return this.validUnsent;
    }
}

