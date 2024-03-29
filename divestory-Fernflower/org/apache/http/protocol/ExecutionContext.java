package org.apache.http.protocol;

public interface ExecutionContext {
   String HTTP_CONNECTION = "http.connection";
   String HTTP_PROXY_HOST = "http.proxy_host";
   String HTTP_REQUEST = "http.request";
   String HTTP_REQ_SENT = "http.request_sent";
   String HTTP_RESPONSE = "http.response";
   String HTTP_TARGET_HOST = "http.target_host";
}
