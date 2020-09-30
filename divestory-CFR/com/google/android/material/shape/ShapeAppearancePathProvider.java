/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Path$Op
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapePath;

public class ShapeAppearancePathProvider {
    private final Path boundsPath = new Path();
    private final ShapePath[] cornerPaths = new ShapePath[4];
    private final Matrix[] cornerTransforms = new Matrix[4];
    private boolean edgeIntersectionCheckEnabled = true;
    private final Matrix[] edgeTransforms = new Matrix[4];
    private final Path overlappedEdgePath = new Path();
    private final PointF pointF = new PointF();
    private final float[] scratch = new float[2];
    private final float[] scratch2 = new float[2];
    private final ShapePath shapePath = new ShapePath();

    public ShapeAppearancePathProvider() {
        int n = 0;
        while (n < 4) {
            this.cornerPaths[n] = new ShapePath();
            this.cornerTransforms[n] = new Matrix();
            this.edgeTransforms[n] = new Matrix();
            ++n;
        }
    }

    private float angleOfEdge(int n) {
        return (n + 1) * 90;
    }

    private void appendCornerPath(ShapeAppearancePathSpec shapeAppearancePathSpec, int n) {
        this.scratch[0] = this.cornerPaths[n].getStartX();
        this.scratch[1] = this.cornerPaths[n].getStartY();
        this.cornerTransforms[n].mapPoints(this.scratch);
        if (n == 0) {
            Path path = shapeAppearancePathSpec.path;
            float[] arrf = this.scratch;
            path.moveTo(arrf[0], arrf[1]);
        } else {
            Path path = shapeAppearancePathSpec.path;
            float[] arrf = this.scratch;
            path.lineTo(arrf[0], arrf[1]);
        }
        this.cornerPaths[n].applyToPath(this.cornerTransforms[n], shapeAppearancePathSpec.path);
        if (shapeAppearancePathSpec.pathListener == null) return;
        shapeAppearancePathSpec.pathListener.onCornerPathCreated(this.cornerPaths[n], this.cornerTransforms[n], n);
    }

    private void appendEdgePath(ShapeAppearancePathSpec shapeAppearancePathSpec, int n) {
        int n2 = (n + 1) % 4;
        this.scratch[0] = this.cornerPaths[n].getEndX();
        this.scratch[1] = this.cornerPaths[n].getEndY();
        this.cornerTransforms[n].mapPoints(this.scratch);
        this.scratch2[0] = this.cornerPaths[n2].getStartX();
        this.scratch2[1] = this.cornerPaths[n2].getStartY();
        this.cornerTransforms[n2].mapPoints(this.scratch2);
        Object object = this.scratch;
        float f = object[0];
        Path path = this.scratch2;
        float f2 = Math.max((float)Math.hypot(f - path[0], object[1] - path[1]) - 0.001f, 0.0f);
        f = this.getEdgeCenterForIndex(shapeAppearancePathSpec.bounds, n);
        this.shapePath.reset(0.0f, 0.0f);
        object = this.getEdgeTreatmentForIndex(n, shapeAppearancePathSpec.shapeAppearanceModel);
        ((EdgeTreatment)object).getEdgePath(f2, f, shapeAppearancePathSpec.interpolation, this.shapePath);
        path = new Path();
        this.shapePath.applyToPath(this.edgeTransforms[n], path);
        if (this.edgeIntersectionCheckEnabled && Build.VERSION.SDK_INT >= 19 && (((EdgeTreatment)object).forceIntersection() || this.pathOverlapsCorner(path, n) || this.pathOverlapsCorner(path, n2))) {
            path.op(path, this.boundsPath, Path.Op.DIFFERENCE);
            this.scratch[0] = this.shapePath.getStartX();
            this.scratch[1] = this.shapePath.getStartY();
            this.edgeTransforms[n].mapPoints(this.scratch);
            path = this.overlappedEdgePath;
            object = this.scratch;
            path.moveTo((float)object[0], (float)object[1]);
            this.shapePath.applyToPath(this.edgeTransforms[n], this.overlappedEdgePath);
        } else {
            this.shapePath.applyToPath(this.edgeTransforms[n], shapeAppearancePathSpec.path);
        }
        if (shapeAppearancePathSpec.pathListener == null) return;
        shapeAppearancePathSpec.pathListener.onEdgePathCreated(this.shapePath, this.edgeTransforms[n], n);
    }

    private void getCoordinatesOfCorner(int n, RectF rectF, PointF pointF) {
        if (n == 1) {
            pointF.set(rectF.right, rectF.bottom);
            return;
        }
        if (n == 2) {
            pointF.set(rectF.left, rectF.bottom);
            return;
        }
        if (n != 3) {
            pointF.set(rectF.right, rectF.top);
            return;
        }
        pointF.set(rectF.left, rectF.top);
    }

    private CornerSize getCornerSizeForIndex(int n, ShapeAppearanceModel shapeAppearanceModel) {
        if (n == 1) return shapeAppearanceModel.getBottomRightCornerSize();
        if (n == 2) return shapeAppearanceModel.getBottomLeftCornerSize();
        if (n == 3) return shapeAppearanceModel.getTopLeftCornerSize();
        return shapeAppearanceModel.getTopRightCornerSize();
    }

    private CornerTreatment getCornerTreatmentForIndex(int n, ShapeAppearanceModel shapeAppearanceModel) {
        if (n == 1) return shapeAppearanceModel.getBottomRightCorner();
        if (n == 2) return shapeAppearanceModel.getBottomLeftCorner();
        if (n == 3) return shapeAppearanceModel.getTopLeftCorner();
        return shapeAppearanceModel.getTopRightCorner();
    }

    private float getEdgeCenterForIndex(RectF rectF, int n) {
        this.scratch[0] = this.cornerPaths[n].endX;
        this.scratch[1] = this.cornerPaths[n].endY;
        this.cornerTransforms[n].mapPoints(this.scratch);
        if (n == 1) return Math.abs(rectF.centerX() - this.scratch[0]);
        if (n == 3) return Math.abs(rectF.centerX() - this.scratch[0]);
        return Math.abs(rectF.centerY() - this.scratch[1]);
    }

    private EdgeTreatment getEdgeTreatmentForIndex(int n, ShapeAppearanceModel shapeAppearanceModel) {
        if (n == 1) return shapeAppearanceModel.getBottomEdge();
        if (n == 2) return shapeAppearanceModel.getLeftEdge();
        if (n == 3) return shapeAppearanceModel.getTopEdge();
        return shapeAppearanceModel.getRightEdge();
    }

    private boolean pathOverlapsCorner(Path path, int n) {
        Path path2 = new Path();
        this.cornerPaths[n].applyToPath(this.cornerTransforms[n], path2);
        RectF rectF = new RectF();
        boolean bl = true;
        path.computeBounds(rectF, true);
        path2.computeBounds(rectF, true);
        path.op(path2, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        boolean bl2 = bl;
        if (!rectF.isEmpty()) return bl2;
        if (!(rectF.width() > 1.0f)) return false;
        if (!(rectF.height() > 1.0f)) return false;
        return bl;
    }

    private void setCornerPathAndTransform(ShapeAppearancePathSpec shapeAppearancePathSpec, int n) {
        CornerSize cornerSize = this.getCornerSizeForIndex(n, shapeAppearancePathSpec.shapeAppearanceModel);
        this.getCornerTreatmentForIndex(n, shapeAppearancePathSpec.shapeAppearanceModel).getCornerPath(this.cornerPaths[n], 90.0f, shapeAppearancePathSpec.interpolation, shapeAppearancePathSpec.bounds, cornerSize);
        float f = this.angleOfEdge(n);
        this.cornerTransforms[n].reset();
        this.getCoordinatesOfCorner(n, shapeAppearancePathSpec.bounds, this.pointF);
        this.cornerTransforms[n].setTranslate(this.pointF.x, this.pointF.y);
        this.cornerTransforms[n].preRotate(f);
    }

    private void setEdgePathAndTransform(int n) {
        this.scratch[0] = this.cornerPaths[n].getEndX();
        this.scratch[1] = this.cornerPaths[n].getEndY();
        this.cornerTransforms[n].mapPoints(this.scratch);
        float f = this.angleOfEdge(n);
        this.edgeTransforms[n].reset();
        Matrix matrix = this.edgeTransforms[n];
        float[] arrf = this.scratch;
        matrix.setTranslate(arrf[0], arrf[1]);
        this.edgeTransforms[n].preRotate(f);
    }

    public void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, Path path) {
        this.calculatePath(shapeAppearanceModel, f, rectF, null, path);
    }

    public void calculatePath(ShapeAppearanceModel object, float f, RectF rectF, PathListener pathListener, Path path) {
        int n;
        path.rewind();
        this.overlappedEdgePath.rewind();
        this.boundsPath.rewind();
        this.boundsPath.addRect(rectF, Path.Direction.CW);
        object = new ShapeAppearancePathSpec((ShapeAppearanceModel)object, f, rectF, pathListener, path);
        int n2 = 0;
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= 4) break;
            this.setCornerPathAndTransform((ShapeAppearancePathSpec)object, n3);
            this.setEdgePathAndTransform(n3);
            ++n3;
        } while (true);
        do {
            if (n >= 4) {
                path.close();
                this.overlappedEdgePath.close();
                if (Build.VERSION.SDK_INT < 19) return;
                if (this.overlappedEdgePath.isEmpty()) return;
                path.op(this.overlappedEdgePath, Path.Op.UNION);
                return;
            }
            this.appendCornerPath((ShapeAppearancePathSpec)object, n);
            this.appendEdgePath((ShapeAppearancePathSpec)object, n);
            ++n;
        } while (true);
    }

    void setEdgeIntersectionCheckEnable(boolean bl) {
        this.edgeIntersectionCheckEnabled = bl;
    }

    public static interface PathListener {
        public void onCornerPathCreated(ShapePath var1, Matrix var2, int var3);

        public void onEdgePathCreated(ShapePath var1, Matrix var2, int var3);
    }

    static final class ShapeAppearancePathSpec {
        public final RectF bounds;
        public final float interpolation;
        public final Path path;
        public final PathListener pathListener;
        public final ShapeAppearanceModel shapeAppearanceModel;

        ShapeAppearancePathSpec(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, PathListener pathListener, Path path) {
            this.pathListener = pathListener;
            this.shapeAppearanceModel = shapeAppearanceModel;
            this.interpolation = f;
            this.bounds = rectF;
            this.path = path;
        }
    }

}

