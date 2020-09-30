package org.apache.http.client.entity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;

public class UrlEncodedFormEntity extends StringEntity {
   public UrlEncodedFormEntity(List<? extends NameValuePair> var1) throws UnsupportedEncodingException {
      this(var1, "ISO-8859-1");
   }

   public UrlEncodedFormEntity(List<? extends NameValuePair> var1, String var2) throws UnsupportedEncodingException {
      super(URLEncodedUtils.format(var1, var2), var2);
      StringBuilder var3 = new StringBuilder();
      var3.append("application/x-www-form-urlencoded; charset=");
      if (var2 == null) {
         var2 = "ISO-8859-1";
      }

      var3.append(var2);
      this.setContentType(var3.toString());
   }
}
