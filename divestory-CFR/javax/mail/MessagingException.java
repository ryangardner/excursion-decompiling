/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

public class MessagingException
extends Exception {
    private static final long serialVersionUID = -7569192289819959253L;
    private Exception next;

    public MessagingException() {
        this.initCause(null);
    }

    public MessagingException(String string2) {
        super(string2);
        this.initCause(null);
    }

    public MessagingException(String string2, Exception exception) {
        super(string2);
        this.next = exception;
        this.initCause(null);
    }

    private final String superToString() {
        return super.toString();
    }

    @Override
    public Throwable getCause() {
        synchronized (this) {
            return this.next;
        }
    }

    public Exception getNextException() {
        synchronized (this) {
            return this.next;
        }
    }

    public boolean setNextException(Exception exception) {
        synchronized (this) {
            Exception exception2 = this;
            while (exception2 instanceof MessagingException && exception2.next != null) {
                exception2 = exception2.next;
            }
            if (!(exception2 instanceof MessagingException)) return false;
            exception2.next = exception;
            return true;
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            String string2 = super.toString();
            Exception exception = this.next;
            if (exception == null) {
                return string2;
            }
            Object object = string2;
            if (string2 == null) {
                object = "";
            }
            StringBuffer stringBuffer = new StringBuffer((String)object);
            object = exception;
            do {
                if (object == null) {
                    object = stringBuffer.toString();
                    return object;
                }
                stringBuffer.append(";\n  nested exception is:\n\t");
                if (object instanceof MessagingException) {
                    object = (MessagingException)object;
                    stringBuffer.append(MessagingException.super.superToString());
                    object = ((MessagingException)object).next;
                    continue;
                }
                stringBuffer.append(((Throwable)object).toString());
                object = null;
            } while (true);
        }
    }
}

