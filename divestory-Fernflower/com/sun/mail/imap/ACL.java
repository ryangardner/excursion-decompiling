package com.sun.mail.imap;

public class ACL implements Cloneable {
   private String name;
   private Rights rights;

   public ACL(String var1) {
      this.name = var1;
      this.rights = new Rights();
   }

   public ACL(String var1, Rights var2) {
      this.name = var1;
      this.rights = var2;
   }

   public Object clone() throws CloneNotSupportedException {
      ACL var1 = (ACL)super.clone();
      var1.rights = (Rights)this.rights.clone();
      return var1;
   }

   public String getName() {
      return this.name;
   }

   public Rights getRights() {
      return this.rights;
   }

   public void setRights(Rights var1) {
      this.rights = var1;
   }
}
