/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Nullable;

public final class Status {
    public static final Status ABORTED;
    public static final Status ALREADY_EXISTS;
    public static final Status CANCELLED;
    public static final Status DATA_LOSS;
    public static final Status DEADLINE_EXCEEDED;
    public static final Status FAILED_PRECONDITION;
    public static final Status INTERNAL;
    public static final Status INVALID_ARGUMENT;
    public static final Status NOT_FOUND;
    public static final Status OK;
    public static final Status OUT_OF_RANGE;
    public static final Status PERMISSION_DENIED;
    public static final Status RESOURCE_EXHAUSTED;
    private static final List<Status> STATUS_LIST;
    public static final Status UNAUTHENTICATED;
    public static final Status UNAVAILABLE;
    public static final Status UNIMPLEMENTED;
    public static final Status UNKNOWN;
    private final CanonicalCode canonicalCode;
    @Nullable
    private final String description;

    static {
        STATUS_LIST = Status.buildStatusList();
        OK = CanonicalCode.OK.toStatus();
        CANCELLED = CanonicalCode.CANCELLED.toStatus();
        UNKNOWN = CanonicalCode.UNKNOWN.toStatus();
        INVALID_ARGUMENT = CanonicalCode.INVALID_ARGUMENT.toStatus();
        DEADLINE_EXCEEDED = CanonicalCode.DEADLINE_EXCEEDED.toStatus();
        NOT_FOUND = CanonicalCode.NOT_FOUND.toStatus();
        ALREADY_EXISTS = CanonicalCode.ALREADY_EXISTS.toStatus();
        PERMISSION_DENIED = CanonicalCode.PERMISSION_DENIED.toStatus();
        UNAUTHENTICATED = CanonicalCode.UNAUTHENTICATED.toStatus();
        RESOURCE_EXHAUSTED = CanonicalCode.RESOURCE_EXHAUSTED.toStatus();
        FAILED_PRECONDITION = CanonicalCode.FAILED_PRECONDITION.toStatus();
        ABORTED = CanonicalCode.ABORTED.toStatus();
        OUT_OF_RANGE = CanonicalCode.OUT_OF_RANGE.toStatus();
        UNIMPLEMENTED = CanonicalCode.UNIMPLEMENTED.toStatus();
        INTERNAL = CanonicalCode.INTERNAL.toStatus();
        UNAVAILABLE = CanonicalCode.UNAVAILABLE.toStatus();
        DATA_LOSS = CanonicalCode.DATA_LOSS.toStatus();
    }

    private Status(CanonicalCode canonicalCode, @Nullable String string2) {
        this.canonicalCode = Utils.checkNotNull(canonicalCode, "canonicalCode");
        this.description = string2;
    }

    private static List<Status> buildStatusList() {
        Serializable serializable = new TreeMap<Integer, Status>();
        CanonicalCode[] arrcanonicalCode = CanonicalCode.values();
        int n = arrcanonicalCode.length;
        int n2 = 0;
        while (n2 < n) {
            CanonicalCode canonicalCode = arrcanonicalCode[n2];
            Status status = ((TreeMap)serializable).put(canonicalCode.value(), new Status(canonicalCode, null));
            if (status != null) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Code value duplication between ");
                ((StringBuilder)serializable).append(status.getCanonicalCode().name());
                ((StringBuilder)serializable).append(" & ");
                ((StringBuilder)serializable).append(canonicalCode.name());
                throw new IllegalStateException(((StringBuilder)serializable).toString());
            }
            ++n2;
        }
        return Collections.unmodifiableList(new ArrayList(((TreeMap)serializable).values()));
    }

    public boolean equals(@Nullable Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Status)) {
            return false;
        }
        object = (Status)object;
        if (this.canonicalCode != ((Status)object).canonicalCode) return false;
        if (!Utils.equalsObjects(this.description, ((Status)object).description)) return false;
        return bl;
    }

    public CanonicalCode getCanonicalCode() {
        return this.canonicalCode;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.canonicalCode, this.description});
    }

    public boolean isOk() {
        if (CanonicalCode.OK != this.canonicalCode) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Status{canonicalCode=");
        stringBuilder.append((Object)this.canonicalCode);
        stringBuilder.append(", description=");
        stringBuilder.append(this.description);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public Status withDescription(@Nullable String string2) {
        if (!Utils.equalsObjects(this.description, string2)) return new Status(this.canonicalCode, string2);
        return this;
    }

    public static final class CanonicalCode
    extends Enum<CanonicalCode> {
        private static final /* synthetic */ CanonicalCode[] $VALUES;
        public static final /* enum */ CanonicalCode ABORTED;
        public static final /* enum */ CanonicalCode ALREADY_EXISTS;
        public static final /* enum */ CanonicalCode CANCELLED;
        public static final /* enum */ CanonicalCode DATA_LOSS;
        public static final /* enum */ CanonicalCode DEADLINE_EXCEEDED;
        public static final /* enum */ CanonicalCode FAILED_PRECONDITION;
        public static final /* enum */ CanonicalCode INTERNAL;
        public static final /* enum */ CanonicalCode INVALID_ARGUMENT;
        public static final /* enum */ CanonicalCode NOT_FOUND;
        public static final /* enum */ CanonicalCode OK;
        public static final /* enum */ CanonicalCode OUT_OF_RANGE;
        public static final /* enum */ CanonicalCode PERMISSION_DENIED;
        public static final /* enum */ CanonicalCode RESOURCE_EXHAUSTED;
        public static final /* enum */ CanonicalCode UNAUTHENTICATED;
        public static final /* enum */ CanonicalCode UNAVAILABLE;
        public static final /* enum */ CanonicalCode UNIMPLEMENTED;
        public static final /* enum */ CanonicalCode UNKNOWN;
        private final int value;

        static {
            CanonicalCode canonicalCode;
            OK = new CanonicalCode(0);
            CANCELLED = new CanonicalCode(1);
            UNKNOWN = new CanonicalCode(2);
            INVALID_ARGUMENT = new CanonicalCode(3);
            DEADLINE_EXCEEDED = new CanonicalCode(4);
            NOT_FOUND = new CanonicalCode(5);
            ALREADY_EXISTS = new CanonicalCode(6);
            PERMISSION_DENIED = new CanonicalCode(7);
            RESOURCE_EXHAUSTED = new CanonicalCode(8);
            FAILED_PRECONDITION = new CanonicalCode(9);
            ABORTED = new CanonicalCode(10);
            OUT_OF_RANGE = new CanonicalCode(11);
            UNIMPLEMENTED = new CanonicalCode(12);
            INTERNAL = new CanonicalCode(13);
            UNAVAILABLE = new CanonicalCode(14);
            DATA_LOSS = new CanonicalCode(15);
            UNAUTHENTICATED = canonicalCode = new CanonicalCode(16);
            $VALUES = new CanonicalCode[]{OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS, canonicalCode};
        }

        private CanonicalCode(int n2) {
            this.value = n2;
        }

        public static CanonicalCode valueOf(String string2) {
            return Enum.valueOf(CanonicalCode.class, string2);
        }

        public static CanonicalCode[] values() {
            return (CanonicalCode[])$VALUES.clone();
        }

        public Status toStatus() {
            return (Status)STATUS_LIST.get(this.value);
        }

        public int value() {
            return this.value;
        }
    }

}

