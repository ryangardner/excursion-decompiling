/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.matrix;

public final class Vector3 {
    public static final Vector3 UNIT_X;
    public static final Vector3 UNIT_Y;
    public static final Vector3 UNIT_Z;
    public static final Vector3 ZERO;
    public float x;
    public float y;
    public float z;

    static {
        ZERO = new Vector3(0.0f, 0.0f, 0.0f);
        UNIT_X = new Vector3(1.0f, 0.0f, 0.0f);
        UNIT_Y = new Vector3(0.0f, 1.0f, 0.0f);
        UNIT_Z = new Vector3(0.0f, 0.0f, 1.0f);
    }

    public Vector3() {
    }

    public Vector3(float f, float f2, float f3) {
        this.set(f, f2, f3);
    }

    public Vector3(Vector3 vector3) {
        this.set(vector3);
    }

    public Vector3(float[] arrf) {
        this.set(arrf[0], arrf[1], arrf[2]);
    }

    public final void add(float f, float f2, float f3) {
        this.x += f;
        this.y += f2;
        this.z += f3;
    }

    public final void add(Vector3 vector3) {
        this.x += vector3.x;
        this.y += vector3.y;
        this.z += vector3.z;
    }

    public final Vector3 cross(Vector3 vector3) {
        float f = this.y;
        float f2 = vector3.z;
        float f3 = this.z;
        float f4 = vector3.y;
        float f5 = vector3.x;
        float f6 = this.x;
        return new Vector3(f * f2 - f3 * f4, f3 * f5 - f2 * f6, f6 * f4 - f * f5);
    }

    public final float distance2(Vector3 vector3) {
        float f = this.x - vector3.x;
        float f2 = this.y - vector3.y;
        float f3 = this.z - vector3.z;
        return f * f + f2 * f2 + f3 * f3;
    }

    public final void divide(float f) {
        if (f == 0.0f) return;
        this.x /= f;
        this.y /= f;
        this.z /= f;
    }

    public final float dot(Vector3 vector3) {
        return this.x * vector3.x + this.y * vector3.y + this.z * vector3.z;
    }

    public final float length() {
        return (float)Math.sqrt(this.length2());
    }

    public final float length2() {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.z;
        return f * f + f2 * f2 + f3 * f3;
    }

    public final void multiply(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public final void multiply(Vector3 vector3) {
        this.x *= vector3.x;
        this.y *= vector3.y;
        this.z *= vector3.z;
    }

    public final float normalize() {
        float f = this.length();
        if (f == 0.0f) return f;
        this.x /= f;
        this.y /= f;
        this.z /= f;
        return f;
    }

    public final boolean pointsInSameDirection(Vector3 vector3) {
        if (!(this.dot(vector3) > 0.0f)) return false;
        return true;
    }

    public final void set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public final void set(Vector3 vector3) {
        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;
    }

    public final void subtract(Vector3 vector3) {
        this.x -= vector3.x;
        this.y -= vector3.y;
        this.z -= vector3.z;
    }

    public final void subtractMultiple(Vector3 vector3, float f) {
        this.x -= vector3.x * f;
        this.y -= vector3.y * f;
        this.z -= vector3.z * f;
    }

    public final void zero() {
        this.set(0.0f, 0.0f, 0.0f);
    }
}

