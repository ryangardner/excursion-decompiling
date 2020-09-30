/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

public class Rectangle {
    public int height;
    public int width;
    public int x;
    public int y;

    public boolean contains(int n, int n2) {
        int n3 = this.x;
        if (n < n3) return false;
        if (n >= n3 + this.width) return false;
        n = this.y;
        if (n2 < n) return false;
        if (n2 >= n + this.height) return false;
        return true;
    }

    public int getCenterX() {
        return (this.x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.y + this.height) / 2;
    }

    void grow(int n, int n2) {
        this.x -= n;
        this.y -= n2;
        this.width += n * 2;
        this.height += n2 * 2;
    }

    boolean intersects(Rectangle rectangle) {
        int n = this.x;
        int n2 = rectangle.x;
        if (n < n2) return false;
        if (n >= n2 + rectangle.width) return false;
        n = this.y;
        n2 = rectangle.y;
        if (n < n2) return false;
        if (n >= n2 + rectangle.height) return false;
        return true;
    }

    public void setBounds(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
    }
}

