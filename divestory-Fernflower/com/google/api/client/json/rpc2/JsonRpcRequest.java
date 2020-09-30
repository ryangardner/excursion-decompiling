package com.google.api.client.json.rpc2;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public class JsonRpcRequest extends GenericData {
   @Key
   private Object id;
   @Key
   private final String jsonrpc = "2.0";
   @Key
   private String method;
   @Key
   private Object params;

   public JsonRpcRequest clone() {
      return (JsonRpcRequest)super.clone();
   }

   public Object getId() {
      return this.id;
   }

   public String getMethod() {
      return this.method;
   }

   public Object getParameters() {
      return this.params;
   }

   public String getVersion() {
      return "2.0";
   }

   public JsonRpcRequest set(String var1, Object var2) {
      return (JsonRpcRequest)super.set(var1, var2);
   }

   public void setId(Object var1) {
      this.id = var1;
   }

   public void setMethod(String var1) {
      this.method = var1;
   }

   public void setParameters(Object var1) {
      this.params = var1;
   }
}
