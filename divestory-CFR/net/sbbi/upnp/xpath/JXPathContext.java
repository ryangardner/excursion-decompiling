/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

public class JXPathContext {
    private Node pointer;
    private Node src;
    private final XPath xpath;

    public JXPathContext(Node node) {
        this.xpath = XPathFactory.newInstance().newXPath();
        this.src = node;
        this.pointer = node;
    }

    private JXPathContext(Node node, XPath xPath) {
        this.xpath = xPath;
        this.src = node;
        this.pointer = node;
    }

    public Boolean getBoolean(String object) throws XPathException {
        try {
            return (Boolean)this.xpath.evaluate((String)object, this.pointer, XPathConstants.BOOLEAN);
        }
        catch (Exception exception) {
            throw new XPathException(exception);
        }
    }

    public Double getNumber(String object) throws XPathException {
        try {
            return (Double)this.xpath.evaluate((String)object, this.pointer, XPathConstants.NUMBER);
        }
        catch (Exception exception) {
            throw new XPathException(exception);
        }
    }

    public Node getPointer(String string2) throws XPathException {
        try {
            Object object = (Node)this.xpath.evaluate(string2, this.pointer, XPathConstants.NODE);
            if (object != null) {
                return object;
            }
            object = new StringBuilder("Null Pointer - \"");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("\"");
            XPathException xPathException = new XPathException(((StringBuilder)object).toString());
            throw xPathException;
        }
        catch (Exception exception) {
            throw new XPathException(exception);
        }
    }

    public JXPathContext getRelativeContext(Node node) {
        return new JXPathContext(node, this.xpath);
    }

    public String getString(String string2) throws XPathException {
        try {
            return (String)this.xpath.evaluate(string2, this.pointer, XPathConstants.STRING);
        }
        catch (Exception exception) {
            throw new XPathException(exception);
        }
    }

    public void movePointer(Node node) {
        this.pointer = node;
    }

    public void resetPointer() {
        this.pointer = this.src;
    }
}

