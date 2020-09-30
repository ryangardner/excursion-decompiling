/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.contrib.http.util;

import io.opencensus.trace.Status;
import javax.annotation.Nullable;

public final class HttpTraceUtil {
    private static final Status STATUS_100 = Status.UNKNOWN.withDescription("Continue");
    private static final Status STATUS_101 = Status.UNKNOWN.withDescription("Switching Protocols");
    private static final Status STATUS_402 = Status.UNKNOWN.withDescription("Payment Required");
    private static final Status STATUS_405 = Status.UNKNOWN.withDescription("Method Not Allowed");
    private static final Status STATUS_406 = Status.UNKNOWN.withDescription("Not Acceptable");
    private static final Status STATUS_407 = Status.UNKNOWN.withDescription("Proxy Authentication Required");
    private static final Status STATUS_408 = Status.UNKNOWN.withDescription("Request Time-out");
    private static final Status STATUS_409 = Status.UNKNOWN.withDescription("Conflict");
    private static final Status STATUS_410 = Status.UNKNOWN.withDescription("Gone");
    private static final Status STATUS_411 = Status.UNKNOWN.withDescription("Length Required");
    private static final Status STATUS_412 = Status.UNKNOWN.withDescription("Precondition Failed");
    private static final Status STATUS_413 = Status.UNKNOWN.withDescription("Request Entity Too Large");
    private static final Status STATUS_414 = Status.UNKNOWN.withDescription("Request-URI Too Large");
    private static final Status STATUS_415 = Status.UNKNOWN.withDescription("Unsupported Media Type");
    private static final Status STATUS_416 = Status.UNKNOWN.withDescription("Requested range not satisfiable");
    private static final Status STATUS_417 = Status.UNKNOWN.withDescription("Expectation Failed");
    private static final Status STATUS_500 = Status.UNKNOWN.withDescription("Internal Server Error");
    private static final Status STATUS_502 = Status.UNKNOWN.withDescription("Bad Gateway");
    private static final Status STATUS_505 = Status.UNKNOWN.withDescription("HTTP Version not supported");

    private HttpTraceUtil() {
    }

    public static final Status parseResponseStatus(int n, @Nullable Throwable throwable) {
        String string2;
        if (throwable != null) {
            String string3;
            string2 = string3 = throwable.getMessage();
            if (string3 == null) {
                string2 = throwable.getClass().getSimpleName();
            }
        } else {
            string2 = null;
        }
        if (n == 0) {
            return Status.UNKNOWN.withDescription(string2);
        }
        if (n >= 200 && n < 400) {
            return Status.OK;
        }
        if (n == 100) return STATUS_100;
        if (n == 101) return STATUS_101;
        if (n == 429) return Status.RESOURCE_EXHAUSTED.withDescription(string2);
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        return Status.UNKNOWN.withDescription(string2);
                    }
                    case 505: {
                        return STATUS_505;
                    }
                    case 504: {
                        return Status.DEADLINE_EXCEEDED.withDescription(string2);
                    }
                    case 503: {
                        return Status.UNAVAILABLE.withDescription(string2);
                    }
                    case 502: {
                        return STATUS_502;
                    }
                    case 501: {
                        return Status.UNIMPLEMENTED.withDescription(string2);
                    }
                    case 500: 
                }
                return STATUS_500;
            }
            case 417: {
                return STATUS_417;
            }
            case 416: {
                return STATUS_416;
            }
            case 415: {
                return STATUS_415;
            }
            case 414: {
                return STATUS_414;
            }
            case 413: {
                return STATUS_413;
            }
            case 412: {
                return STATUS_412;
            }
            case 411: {
                return STATUS_411;
            }
            case 410: {
                return STATUS_410;
            }
            case 409: {
                return STATUS_409;
            }
            case 408: {
                return STATUS_408;
            }
            case 407: {
                return STATUS_407;
            }
            case 406: {
                return STATUS_406;
            }
            case 405: {
                return STATUS_405;
            }
            case 404: {
                return Status.NOT_FOUND.withDescription(string2);
            }
            case 403: {
                return Status.PERMISSION_DENIED.withDescription(string2);
            }
            case 402: {
                return STATUS_402;
            }
            case 401: {
                return Status.UNAUTHENTICATED.withDescription(string2);
            }
            case 400: 
        }
        return Status.INVALID_ARGUMENT.withDescription(string2);
    }
}

