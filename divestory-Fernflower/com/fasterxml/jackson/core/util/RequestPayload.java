package com.fasterxml.jackson.core.util;

import java.io.IOException;
import java.io.Serializable;

public class RequestPayload implements Serializable {
   private static final long serialVersionUID = 1L;
   protected String _charset;
   protected byte[] _payloadAsBytes;
   protected CharSequence _payloadAsText;

   public RequestPayload(CharSequence var1) {
      if (var1 != null) {
         this._payloadAsText = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public RequestPayload(byte[] var1, String var2) {
      if (var1 == null) {
         throw new IllegalArgumentException();
      } else {
         String var3;
         label14: {
            this._payloadAsBytes = var1;
            if (var2 != null) {
               var3 = var2;
               if (!var2.isEmpty()) {
                  break label14;
               }
            }

            var3 = "UTF-8";
         }

         this._charset = var3;
      }
   }

   public Object getRawPayload() {
      byte[] var1 = this._payloadAsBytes;
      return var1 != null ? var1 : this._payloadAsText;
   }

   public String toString() {
      if (this._payloadAsBytes != null) {
         try {
            String var1 = new String(this._payloadAsBytes, this._charset);
            return var1;
         } catch (IOException var2) {
            throw new RuntimeException(var2);
         }
      } else {
         return this._payloadAsText.toString();
      }
   }
}
