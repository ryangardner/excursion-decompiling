package com.sun.mail.imap;

import javax.mail.FetchProfile;
import javax.mail.UIDFolder;

class IMAPMessage$1FetchProfileCondition implements Utility.Condition {
   private String[] hdrs = null;
   private boolean needBodyStructure = false;
   private boolean needEnvelope = false;
   private boolean needFlags = false;
   private boolean needHeaders = false;
   private boolean needSize = false;
   private boolean needUID = false;

   public IMAPMessage$1FetchProfileCondition(FetchProfile var1) {
      if (var1.contains(FetchProfile.Item.ENVELOPE)) {
         this.needEnvelope = true;
      }

      if (var1.contains(FetchProfile.Item.FLAGS)) {
         this.needFlags = true;
      }

      if (var1.contains(FetchProfile.Item.CONTENT_INFO)) {
         this.needBodyStructure = true;
      }

      if (var1.contains((FetchProfile.Item)UIDFolder.FetchProfileItem.UID)) {
         this.needUID = true;
      }

      if (var1.contains((FetchProfile.Item)IMAPFolder.FetchProfileItem.HEADERS)) {
         this.needHeaders = true;
      }

      if (var1.contains((FetchProfile.Item)IMAPFolder.FetchProfileItem.SIZE)) {
         this.needSize = true;
      }

      this.hdrs = var1.getHeaderNames();
   }

   public boolean test(IMAPMessage var1) {
      if (this.needEnvelope && IMAPMessage.access$0(var1) == null) {
         return true;
      } else if (this.needFlags && IMAPMessage.access$1(var1) == null) {
         return true;
      } else if (this.needBodyStructure && IMAPMessage.access$2(var1) == null) {
         return true;
      } else if (this.needUID && var1.getUID() == -1L) {
         return true;
      } else if (this.needHeaders && !IMAPMessage.access$3(var1)) {
         return true;
      } else if (this.needSize && IMAPMessage.access$4(var1) == -1) {
         return true;
      } else {
         int var2 = 0;

         while(true) {
            String[] var3 = this.hdrs;
            if (var2 >= var3.length) {
               return false;
            }

            if (!IMAPMessage.access$5(var1, var3[var2])) {
               return true;
            }

            ++var2;
         }
      }
   }
}
