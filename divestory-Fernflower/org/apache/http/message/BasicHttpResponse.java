package org.apache.http.message;

import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;

public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {
   private HttpEntity entity;
   private Locale locale;
   private ReasonPhraseCatalog reasonCatalog;
   private StatusLine statusline;

   public BasicHttpResponse(ProtocolVersion var1, int var2, String var3) {
      this(new BasicStatusLine(var1, var2, var3), (ReasonPhraseCatalog)null, (Locale)null);
   }

   public BasicHttpResponse(StatusLine var1) {
      this(var1, (ReasonPhraseCatalog)null, (Locale)null);
   }

   public BasicHttpResponse(StatusLine var1, ReasonPhraseCatalog var2, Locale var3) {
      if (var1 != null) {
         this.statusline = var1;
         this.reasonCatalog = var2;
         if (var3 == null) {
            var3 = Locale.getDefault();
         }

         this.locale = var3;
      } else {
         throw new IllegalArgumentException("Status line may not be null.");
      }
   }

   public HttpEntity getEntity() {
      return this.entity;
   }

   public Locale getLocale() {
      return this.locale;
   }

   public ProtocolVersion getProtocolVersion() {
      return this.statusline.getProtocolVersion();
   }

   protected String getReason(int var1) {
      ReasonPhraseCatalog var2 = this.reasonCatalog;
      String var3;
      if (var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getReason(var1, this.locale);
      }

      return var3;
   }

   public StatusLine getStatusLine() {
      return this.statusline;
   }

   public void setEntity(HttpEntity var1) {
      this.entity = var1;
   }

   public void setLocale(Locale var1) {
      if (var1 != null) {
         this.locale = var1;
         int var2 = this.statusline.getStatusCode();
         this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), var2, this.getReason(var2));
      } else {
         throw new IllegalArgumentException("Locale may not be null.");
      }
   }

   public void setReasonPhrase(String var1) {
      if (var1 == null || var1.indexOf(10) < 0 && var1.indexOf(13) < 0) {
         this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), this.statusline.getStatusCode(), var1);
      } else {
         throw new IllegalArgumentException("Line break in reason phrase.");
      }
   }

   public void setStatusCode(int var1) {
      this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), var1, this.getReason(var1));
   }

   public void setStatusLine(ProtocolVersion var1, int var2) {
      this.statusline = new BasicStatusLine(var1, var2, this.getReason(var2));
   }

   public void setStatusLine(ProtocolVersion var1, int var2, String var3) {
      this.statusline = new BasicStatusLine(var1, var2, var3);
   }

   public void setStatusLine(StatusLine var1) {
      if (var1 != null) {
         this.statusline = var1;
      } else {
         throw new IllegalArgumentException("Status line may not be null");
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.statusline);
      var1.append(" ");
      var1.append(this.headergroup);
      return var1.toString();
   }
}
