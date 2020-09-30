/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.Part;

public abstract class Multipart {
    protected String contentType = "multipart/mixed";
    protected Part parent;
    protected Vector parts = new Vector();

    protected Multipart() {
    }

    public void addBodyPart(BodyPart bodyPart) throws MessagingException {
        synchronized (this) {
            if (this.parts == null) {
                Vector vector;
                this.parts = vector = new Vector();
            }
            this.parts.addElement(bodyPart);
            bodyPart.setParent(this);
            return;
        }
    }

    public void addBodyPart(BodyPart bodyPart, int n) throws MessagingException {
        synchronized (this) {
            if (this.parts == null) {
                Vector vector;
                this.parts = vector = new Vector();
            }
            this.parts.insertElementAt(bodyPart, n);
            bodyPart.setParent(this);
            return;
        }
    }

    public BodyPart getBodyPart(int n) throws MessagingException {
        synchronized (this) {
            if (this.parts != null) {
                return (BodyPart)this.parts.elementAt(n);
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException("No such BodyPart");
            throw indexOutOfBoundsException;
        }
    }

    public String getContentType() {
        return this.contentType;
    }

    public int getCount() throws MessagingException {
        synchronized (this) {
            Vector vector = this.parts;
            if (vector == null) {
                return 0;
            }
            int n = this.parts.size();
            return n;
        }
    }

    public Part getParent() {
        synchronized (this) {
            return this.parent;
        }
    }

    public void removeBodyPart(int n) throws MessagingException {
        synchronized (this) {
            if (this.parts != null) {
                BodyPart bodyPart = (BodyPart)this.parts.elementAt(n);
                this.parts.removeElementAt(n);
                bodyPart.setParent(null);
                return;
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException("No such BodyPart");
            throw indexOutOfBoundsException;
        }
    }

    public boolean removeBodyPart(BodyPart object) throws MessagingException {
        synchronized (this) {
            if (this.parts != null) {
                boolean bl = this.parts.removeElement(object);
                ((BodyPart)object).setParent(null);
                return bl;
            }
            object = new MessagingException("No such body part");
            throw object;
        }
    }

    protected void setMultipartDataSource(MultipartDataSource multipartDataSource) throws MessagingException {
        synchronized (this) {
            this.contentType = multipartDataSource.getContentType();
            int n = multipartDataSource.getCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return;
                }
                this.addBodyPart(multipartDataSource.getBodyPart(n2));
                ++n2;
                continue;
                break;
            } while (true);
        }
    }

    public void setParent(Part part) {
        synchronized (this) {
            this.parent = part;
            return;
        }
    }

    public abstract void writeTo(OutputStream var1) throws IOException, MessagingException;
}

