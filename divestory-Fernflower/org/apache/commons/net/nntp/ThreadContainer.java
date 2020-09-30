package org.apache.commons.net.nntp;

class ThreadContainer {
   ThreadContainer child;
   ThreadContainer next;
   ThreadContainer parent;
   Threadable threadable;

   boolean findChild(ThreadContainer var1) {
      ThreadContainer var2 = this.child;
      if (var2 == null) {
         return false;
      } else {
         return var2 == var1 ? true : var2.findChild(var1);
      }
   }

   void flush() {
      if (this.parent != null && this.threadable == null) {
         StringBuilder var4 = new StringBuilder();
         var4.append("no threadable in ");
         var4.append(this.toString());
         throw new RuntimeException(var4.toString());
      } else {
         this.parent = null;
         Threadable var2 = this.threadable;
         ThreadContainer var1;
         Threadable var3;
         if (var2 != null) {
            var1 = this.child;
            if (var1 == null) {
               var3 = null;
            } else {
               var3 = var1.threadable;
            }

            var2.setChild(var3);
         }

         var1 = this.child;
         if (var1 != null) {
            var1.flush();
            this.child = null;
         }

         var2 = this.threadable;
         if (var2 != null) {
            var1 = this.next;
            if (var1 == null) {
               var3 = null;
            } else {
               var3 = var1.threadable;
            }

            var2.setNext(var3);
         }

         var1 = this.next;
         if (var1 != null) {
            var1.flush();
            this.next = null;
         }

         this.threadable = null;
      }
   }

   void reverseChildren() {
      ThreadContainer var1 = this.child;
      if (var1 != null) {
         ThreadContainer var2 = var1.next;

         ThreadContainer var3;
         ThreadContainer var4;
         for(var3 = null; var1 != null; var3 = var4) {
            var1.next = var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.next;
            }

            var4 = var1;
            var1 = var2;
            var2 = var3;
         }

         for(this.child = var3; var3 != null; var3 = var3.next) {
            var3.reverseChildren();
         }
      }

   }
}
