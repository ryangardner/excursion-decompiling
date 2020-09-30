package net.sbbi.upnp.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

public class JXPathContext {
   private Node pointer;
   private Node src;
   private final XPath xpath;

   public JXPathContext(Node var1) {
      this.xpath = XPathFactory.newInstance().newXPath();
      this.src = var1;
      this.pointer = var1;
   }

   private JXPathContext(Node var1, XPath var2) {
      this.xpath = var2;
      this.src = var1;
      this.pointer = var1;
   }

   public Boolean getBoolean(String var1) throws XPathException {
      try {
         Boolean var3 = (Boolean)this.xpath.evaluate(var1, this.pointer, XPathConstants.BOOLEAN);
         return var3;
      } catch (Exception var2) {
         throw new XPathException(var2);
      }
   }

   public Double getNumber(String var1) throws XPathException {
      try {
         Double var3 = (Double)this.xpath.evaluate(var1, this.pointer, XPathConstants.NUMBER);
         return var3;
      } catch (Exception var2) {
         throw new XPathException(var2);
      }
   }

   public Node getPointer(String param1) throws XPathException {
      // $FF: Couldn't be decompiled
   }

   public JXPathContext getRelativeContext(Node var1) {
      return new JXPathContext(var1, this.xpath);
   }

   public String getString(String var1) throws XPathException {
      try {
         var1 = (String)this.xpath.evaluate(var1, this.pointer, XPathConstants.STRING);
         return var1;
      } catch (Exception var2) {
         throw new XPathException(var2);
      }
   }

   public void movePointer(Node var1) {
      this.pointer = var1;
   }

   public void resetPointer() {
      this.pointer = this.src;
   }
}
