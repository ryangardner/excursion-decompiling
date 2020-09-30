/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.appcompat.widget;

class RtlSpacingHelper {
    public static final int UNDEFINED = Integer.MIN_VALUE;
    private int mEnd = Integer.MIN_VALUE;
    private int mExplicitLeft = 0;
    private int mExplicitRight = 0;
    private boolean mIsRelative = false;
    private boolean mIsRtl = false;
    private int mLeft = 0;
    private int mRight = 0;
    private int mStart = Integer.MIN_VALUE;

    RtlSpacingHelper() {
    }

    public int getEnd() {
        if (!this.mIsRtl) return this.mRight;
        return this.mLeft;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getStart() {
        if (!this.mIsRtl) return this.mLeft;
        return this.mRight;
    }

    public void setAbsolute(int n, int n2) {
        this.mIsRelative = false;
        if (n != Integer.MIN_VALUE) {
            this.mExplicitLeft = n;
            this.mLeft = n;
        }
        if (n2 == Integer.MIN_VALUE) return;
        this.mExplicitRight = n2;
        this.mRight = n2;
    }

    public void setDirection(boolean bl) {
        if (bl == this.mIsRtl) {
            return;
        }
        this.mIsRtl = bl;
        if (!this.mIsRelative) {
            this.mLeft = this.mExplicitLeft;
            this.mRight = this.mExplicitRight;
            return;
        }
        if (bl) {
            int n = this.mEnd;
            if (n == Integer.MIN_VALUE) {
                n = this.mExplicitLeft;
            }
            this.mLeft = n;
            n = this.mStart;
            if (n == Integer.MIN_VALUE) {
                n = this.mExplicitRight;
            }
            this.mRight = n;
            return;
        }
        int n = this.mStart;
        if (n == Integer.MIN_VALUE) {
            n = this.mExplicitLeft;
        }
        this.mLeft = n;
        n = this.mEnd;
        if (n == Integer.MIN_VALUE) {
            n = this.mExplicitRight;
        }
        this.mRight = n;
    }

    public void setRelative(int n, int n2) {
        this.mStart = n;
        this.mEnd = n2;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if (n2 != Integer.MIN_VALUE) {
                this.mLeft = n2;
            }
            if (n == Integer.MIN_VALUE) return;
            this.mRight = n;
            return;
        }
        if (n != Integer.MIN_VALUE) {
            this.mLeft = n;
        }
        if (n2 == Integer.MIN_VALUE) return;
        this.mRight = n2;
    }
}

