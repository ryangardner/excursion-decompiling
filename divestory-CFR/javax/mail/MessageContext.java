/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;

public class MessageContext {
    private Part part;

    public MessageContext(Part part) {
        this.part = part;
    }

    private static Message getMessage(Part object) throws MessagingException {
        while (object != null) {
            if (object instanceof Message) {
                return (Message)object;
            }
            if ((object = ((BodyPart)object).getParent()) == null) {
                return null;
            }
            object = ((Multipart)object).getParent();
        }
        return null;
    }

    public Message getMessage() {
        try {
            return MessageContext.getMessage(this.part);
        }
        catch (MessagingException messagingException) {
            return null;
        }
    }

    public Part getPart() {
        return this.part;
    }

    public Session getSession() {
        Message message = this.getMessage();
        if (message == null) return null;
        return message.session;
    }
}

