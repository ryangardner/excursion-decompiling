/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;

public final class DuplicateTaskCompletionException
extends IllegalStateException {
    private DuplicateTaskCompletionException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public static IllegalStateException of(Task<?> object) {
        if (!((Task)object).isComplete()) {
            return new IllegalStateException("DuplicateTaskCompletionException can only be created from completed Task.");
        }
        Exception exception = ((Task)object).getException();
        if (exception != null) {
            object = "failure";
        } else if (((Task)object).isSuccessful()) {
            object = String.valueOf(((Task)object).getResult());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(object).length() + 7);
            stringBuilder.append("result ");
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
        } else {
            object = ((Task)object).isCanceled() ? "cancellation" : "unknown issue";
        }
        object = String.valueOf(object);
        if (((String)object).length() != 0) {
            object = "Complete with: ".concat((String)object);
            return new DuplicateTaskCompletionException((String)object, exception);
        }
        object = new String("Complete with: ");
        return new DuplicateTaskCompletionException((String)object, exception);
    }
}

