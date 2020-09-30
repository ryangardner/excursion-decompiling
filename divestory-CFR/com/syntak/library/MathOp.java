/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.syntak.library;

import java.util.Random;

public class MathOp {
    public static Matrix add(Matrix matrix, Matrix matrix2) throws IllegalDimensionException {
        if (matrix.getNcols() != matrix2.getNcols()) throw new IllegalDimensionException("Two matrices should be the same dimension.");
        if (matrix.getNrows() != matrix2.getNrows()) throw new IllegalDimensionException("Two matrices should be the same dimension.");
        Matrix matrix3 = new Matrix(matrix.getNrows(), matrix.getNcols());
        int n = 0;
        while (n < matrix.getNrows()) {
            for (int i = 0; i < matrix.getNcols(); ++i) {
                matrix3.setValueAt(n, i, matrix.getValueAt(n, i) + matrix2.getValueAt(n, i));
            }
            ++n;
        }
        return matrix3;
    }

    private static int changeSign(int n) {
        if (n % 2 != 0) return -1;
        return 1;
    }

    public static Matrix cofactor(Matrix matrix) throws NoSquareException {
        Matrix matrix2 = new Matrix(matrix.getNrows(), matrix.getNcols());
        int n = 0;
        while (n < matrix.getNrows()) {
            for (int i = 0; i < matrix.getNcols(); ++i) {
                matrix2.setValueAt(n, i, (double)(MathOp.changeSign(n) * MathOp.changeSign(i)) * MathOp.determinant(MathOp.createSubMatrix(matrix, n, i)));
            }
            ++n;
        }
        return matrix2;
    }

    public static Matrix createSubMatrix(Matrix matrix, int n, int n2) {
        Matrix matrix2 = new Matrix(matrix.getNrows() - 1, matrix.getNcols() - 1);
        int n3 = 0;
        int n4 = -1;
        while (n3 < matrix.getNrows()) {
            if (n3 != n) {
                int n5 = n4 + 1;
                int n6 = 0;
                int n7 = -1;
                do {
                    n4 = n5;
                    if (n6 >= matrix.getNcols()) break;
                    if (n6 != n2) {
                        matrix2.setValueAt(n5, ++n7, matrix.getValueAt(n3, n6));
                    }
                    ++n6;
                } while (true);
            }
            ++n3;
        }
        return matrix2;
    }

    public static double determinant(Matrix matrix) throws NoSquareException {
        if (!matrix.isSquare()) throw new NoSquareException("matrix need to be square.");
        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }
        if (matrix.size() == 2) {
            return matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1) - matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0);
        }
        double d = 0.0;
        int n = 0;
        while (n < matrix.getNcols()) {
            d += (double)MathOp.changeSign(n) * matrix.getValueAt(0, n) * MathOp.determinant(MathOp.createSubMatrix(matrix, 0, n));
            ++n;
        }
        return d;
    }

    public static Matrix inverse(Matrix matrix) throws NoSquareException {
        return MathOp.transpose(MathOp.cofactor(matrix)).multiplyByConstant(1.0 / MathOp.determinant(matrix));
    }

    public static boolean isInteger(double d) {
        if ((double)((int)d) != d) return false;
        return true;
    }

    public static boolean isInteger(float f) {
        if ((float)((int)f) != f) return false;
        return true;
    }

    public static float lengthFoot2Meter(float f) {
        return (float)((double)f * 0.3048);
    }

    public static float lengthMeter2Foot(float f) {
        return (float)((double)f / 0.3048);
    }

    public static int mod(int n, int n2) {
        int n3;
        n = n3 = n % n2;
        if (n3 >= 0) return n;
        return n3 + n2;
    }

    public static Matrix multiply(Matrix matrix, Matrix matrix2) {
        Matrix matrix3 = new Matrix(matrix.getNrows(), matrix2.getNcols());
        int n = 0;
        while (n < matrix3.getNrows()) {
            for (int i = 0; i < matrix3.getNcols(); ++i) {
                double d = 0.0;
                for (int j = 0; j < matrix.getNcols(); d += matrix.getValueAt((int)n, (int)j) * matrix2.getValueAt((int)j, (int)i), ++j) {
                }
                matrix3.setValueAt(n, i, d);
            }
            ++n;
        }
        return matrix3;
    }

    public static int randomInteger(int n, int n2) {
        return new Random().nextInt(n2 - n) + n;
    }

    public static float round_to_digits(float f, int n) {
        int n2 = 1;
        int n3 = 10;
        do {
            if (n2 >= n) {
                float f2;
                float f3 = n3;
                f = f2 = f * f3;
                if (!(f2 - (float)((int)f2) >= 0.5f)) return (float)((int)f) / f3;
                f = f2 + 1.0f;
                return (float)((int)f) / f3;
            }
            n3 *= 10;
            ++n2;
        } while (true);
    }

    public static double square(double d) {
        return d * d;
    }

    public static Matrix subtract(Matrix matrix, Matrix matrix2) throws IllegalDimensionException {
        return MathOp.add(matrix, matrix2.multiplyByConstant(-1.0));
    }

    public static float temperatureC2F(float f) {
        return (float)((double)f * 1.8 + 32.0);
    }

    public static float temperatureF2C(float f) {
        return (float)((double)(f - 32.0f) / 1.8);
    }

    public static Matrix transpose(Matrix matrix) {
        Matrix matrix2 = new Matrix(matrix.getNcols(), matrix.getNrows());
        int n = 0;
        while (n < matrix.getNrows()) {
            for (int i = 0; i < matrix.getNcols(); ++i) {
                matrix2.setValueAt(i, n, matrix.getValueAt(n, i));
            }
            ++n;
        }
        return matrix2;
    }

    public static float weightKg2Pound(float f) {
        return (float)((double)f / 0.454);
    }

    public static float weightPound2Kg(float f) {
        return (float)((double)f * 0.454);
    }

    public static class IllegalDimensionException
    extends Exception {
        public IllegalDimensionException() {
        }

        public IllegalDimensionException(String string2) {
            super(string2);
        }
    }

    public static class Matrix {
        private double[][] data;
        private int ncols;
        private int nrows;

        public Matrix(int n, int n2) {
            this.nrows = n;
            this.ncols = n2;
            this.data = new double[n][n2];
        }

        public Matrix(double[][] arrd) {
            this.data = arrd;
            this.nrows = arrd.length;
            this.ncols = arrd[0].length;
        }

        public int getNcols() {
            return this.ncols;
        }

        public int getNrows() {
            return this.nrows;
        }

        public double getValueAt(int n, int n2) {
            return this.data[n][n2];
        }

        public double[][] getValues() {
            return this.data;
        }

        public Matrix insertColumnWithValue1() {
            Matrix matrix = new Matrix(this.getNrows(), this.getNcols() + 1);
            int n = 0;
            while (n < matrix.getNrows()) {
                for (int i = 0; i < matrix.getNcols(); ++i) {
                    if (i == 0) {
                        matrix.setValueAt(n, i, 1.0);
                        continue;
                    }
                    matrix.setValueAt(n, i, this.getValueAt(n, i - 1));
                }
                ++n;
            }
            return matrix;
        }

        public boolean isSquare() {
            if (this.nrows != this.ncols) return false;
            return true;
        }

        public Matrix multiplyByConstant(double d) {
            Matrix matrix = new Matrix(this.nrows, this.ncols);
            int n = 0;
            while (n < this.nrows) {
                for (int i = 0; i < this.ncols; ++i) {
                    matrix.setValueAt(n, i, this.data[n][i] * d);
                }
                ++n;
            }
            return matrix;
        }

        public void setNcols(int n) {
            this.ncols = n;
        }

        public void setNrows(int n) {
            this.nrows = n;
        }

        public void setValueAt(int n, int n2, double d) {
            this.data[n][n2] = d;
        }

        public void setValues(double[][] arrd) {
            this.data = arrd;
        }

        public int size() {
            if (!this.isSquare()) return -1;
            return this.nrows;
        }
    }

    public static class NoSquareException
    extends Exception {
        public NoSquareException() {
        }

        public NoSquareException(String string2) {
            super(string2);
        }
    }

    public static class Vector3D {
        double x;
        double y;
        double z;

        public Vector3D() {
        }

        public Vector3D(double d, double d2, double d3) {
            this.x = d;
            this.y = d2;
            this.z = d3;
        }
    }

}

