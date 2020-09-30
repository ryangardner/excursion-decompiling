/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.ColorMatrix
 *  android.graphics.ColorMatrixColorFilter
 *  android.graphics.Outline
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewOutlineProvider
 *  android.widget.ImageView
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

public class ImageFilterView
extends AppCompatImageView {
    private float mCrossfade = 0.0f;
    private ImageMatrix mImageMatrix = new ImageMatrix();
    LayerDrawable mLayer;
    Drawable[] mLayers;
    private boolean mOverlay = true;
    private Path mPath;
    RectF mRect;
    private float mRound = Float.NaN;
    private float mRoundPercent = 0.0f;
    ViewOutlineProvider mViewOutlineProvider;

    public ImageFilterView(Context context) {
        super(context);
        this.init(context, null);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private void init(Context context, AttributeSet arrdrawable) {
        if (arrdrawable == null) return;
        arrdrawable = this.getContext().obtainStyledAttributes((AttributeSet)arrdrawable, R.styleable.ImageFilterView);
        int n = arrdrawable.getIndexCount();
        context = arrdrawable.getDrawable(R.styleable.ImageFilterView_altSrc);
        int n2 = 0;
        do {
            if (n2 >= n) {
                arrdrawable.recycle();
                if (context == null) return;
                arrdrawable = new Drawable[2];
                this.mLayers = arrdrawable;
                arrdrawable[0] = this.getDrawable();
                this.mLayers[1] = context;
                context = new LayerDrawable(this.mLayers);
                this.mLayer = context;
                context.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0f));
                super.setImageDrawable((Drawable)this.mLayer);
                return;
            }
            int n3 = arrdrawable.getIndex(n2);
            if (n3 == R.styleable.ImageFilterView_crossfade) {
                this.mCrossfade = arrdrawable.getFloat(n3, 0.0f);
            } else if (n3 == R.styleable.ImageFilterView_warmth) {
                this.setWarmth(arrdrawable.getFloat(n3, 0.0f));
            } else if (n3 == R.styleable.ImageFilterView_saturation) {
                this.setSaturation(arrdrawable.getFloat(n3, 0.0f));
            } else if (n3 == R.styleable.ImageFilterView_contrast) {
                this.setContrast(arrdrawable.getFloat(n3, 0.0f));
            } else if (n3 == R.styleable.ImageFilterView_round) {
                this.setRound(arrdrawable.getDimension(n3, 0.0f));
            } else if (n3 == R.styleable.ImageFilterView_roundPercent) {
                this.setRoundPercent(arrdrawable.getFloat(n3, 0.0f));
            } else if (n3 == R.styleable.ImageFilterView_overlay) {
                this.setOverlay(arrdrawable.getBoolean(n3, this.mOverlay));
            }
            ++n2;
        } while (true);
    }

    private void setOverlay(boolean bl) {
        this.mOverlay = bl;
    }

    public void draw(Canvas canvas) {
        boolean bl;
        if (Build.VERSION.SDK_INT < 21 && this.mRoundPercent != 0.0f && this.mPath != null) {
            bl = true;
            canvas.save();
            canvas.clipPath(this.mPath);
        } else {
            bl = false;
        }
        super.draw(canvas);
        if (!bl) return;
        canvas.restore();
    }

    public float getBrightness() {
        return this.mImageMatrix.mBrightness;
    }

    public float getContrast() {
        return this.mImageMatrix.mContrast;
    }

    public float getCrossfade() {
        return this.mCrossfade;
    }

    public float getRound() {
        return this.mRound;
    }

    public float getRoundPercent() {
        return this.mRoundPercent;
    }

    public float getSaturation() {
        return this.mImageMatrix.mSaturation;
    }

    public float getWarmth() {
        return this.mImageMatrix.mWarmth;
    }

    public void setBrightness(float f) {
        this.mImageMatrix.mBrightness = f;
        this.mImageMatrix.updateMatrix(this);
    }

    public void setContrast(float f) {
        this.mImageMatrix.mContrast = f;
        this.mImageMatrix.updateMatrix(this);
    }

    public void setCrossfade(float f) {
        this.mCrossfade = f;
        if (this.mLayers == null) return;
        if (!this.mOverlay) {
            this.mLayer.getDrawable(0).setAlpha((int)((1.0f - this.mCrossfade) * 255.0f));
        }
        this.mLayer.getDrawable(1).setAlpha((int)(this.mCrossfade * 255.0f));
        super.setImageDrawable((Drawable)this.mLayer);
    }

    public void setRound(float f) {
        if (Float.isNaN(f)) {
            this.mRound = f;
            f = this.mRoundPercent;
            this.mRoundPercent = -1.0f;
            this.setRoundPercent(f);
            return;
        }
        boolean bl = this.mRound != f;
        this.mRound = f;
        if (f != 0.0f) {
            ViewOutlineProvider viewOutlineProvider;
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mViewOutlineProvider == null) {
                    this.mViewOutlineProvider = viewOutlineProvider = new ViewOutlineProvider(){

                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
                        }
                    };
                    this.setOutlineProvider(viewOutlineProvider);
                }
                this.setClipToOutline(true);
            }
            int n = this.getWidth();
            int n2 = this.getHeight();
            this.mRect.set(0.0f, 0.0f, (float)n, (float)n2);
            this.mPath.reset();
            viewOutlineProvider = this.mPath;
            RectF rectF = this.mRect;
            f = this.mRound;
            viewOutlineProvider.addRoundRect(rectF, f, f, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.setClipToOutline(false);
        }
        if (!bl) return;
        if (Build.VERSION.SDK_INT < 21) return;
        this.invalidateOutline();
    }

    public void setRoundPercent(float f) {
        boolean bl = this.mRoundPercent != f;
        this.mRoundPercent = f;
        if (f != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mViewOutlineProvider == null) {
                    ViewOutlineProvider viewOutlineProvider;
                    this.mViewOutlineProvider = viewOutlineProvider = new ViewOutlineProvider(){

                        public void getOutline(View view, Outline outline) {
                            int n = ImageFilterView.this.getWidth();
                            int n2 = ImageFilterView.this.getHeight();
                            outline.setRoundRect(0, 0, n, n2, (float)Math.min(n, n2) * ImageFilterView.this.mRoundPercent / 2.0f);
                        }
                    };
                    this.setOutlineProvider(viewOutlineProvider);
                }
                this.setClipToOutline(true);
            }
            int n = this.getWidth();
            int n2 = this.getHeight();
            f = (float)Math.min(n, n2) * this.mRoundPercent / 2.0f;
            this.mRect.set(0.0f, 0.0f, (float)n, (float)n2);
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, f, f, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.setClipToOutline(false);
        }
        if (!bl) return;
        if (Build.VERSION.SDK_INT < 21) return;
        this.invalidateOutline();
    }

    public void setSaturation(float f) {
        this.mImageMatrix.mSaturation = f;
        this.mImageMatrix.updateMatrix(this);
    }

    public void setWarmth(float f) {
        this.mImageMatrix.mWarmth = f;
        this.mImageMatrix.updateMatrix(this);
    }

    static class ImageMatrix {
        float[] m = new float[20];
        float mBrightness = 1.0f;
        ColorMatrix mColorMatrix = new ColorMatrix();
        float mContrast = 1.0f;
        float mSaturation = 1.0f;
        ColorMatrix mTmpColorMatrix = new ColorMatrix();
        float mWarmth = 1.0f;

        ImageMatrix() {
        }

        private void brightness(float f) {
            float[] arrf = this.m;
            arrf[0] = f;
            arrf[1] = 0.0f;
            arrf[2] = 0.0f;
            arrf[3] = 0.0f;
            arrf[4] = 0.0f;
            arrf[5] = 0.0f;
            arrf[6] = f;
            arrf[7] = 0.0f;
            arrf[8] = 0.0f;
            arrf[9] = 0.0f;
            arrf[10] = 0.0f;
            arrf[11] = 0.0f;
            arrf[12] = f;
            arrf[13] = 0.0f;
            arrf[14] = 0.0f;
            arrf[15] = 0.0f;
            arrf[16] = 0.0f;
            arrf[17] = 0.0f;
            arrf[18] = 1.0f;
            arrf[19] = 0.0f;
        }

        private void saturation(float f) {
            float f2 = 1.0f - f;
            float f3 = 0.2999f * f2;
            float f4 = 0.587f * f2;
            float[] arrf = this.m;
            arrf[0] = f3 + f;
            arrf[1] = f4;
            arrf[2] = f2 *= 0.114f;
            arrf[3] = 0.0f;
            arrf[4] = 0.0f;
            arrf[5] = f3;
            arrf[6] = f4 + f;
            arrf[7] = f2;
            arrf[8] = 0.0f;
            arrf[9] = 0.0f;
            arrf[10] = f3;
            arrf[11] = f4;
            arrf[12] = f2 + f;
            arrf[13] = 0.0f;
            arrf[14] = 0.0f;
            arrf[15] = 0.0f;
            arrf[16] = 0.0f;
            arrf[17] = 0.0f;
            arrf[18] = 1.0f;
            arrf[19] = 0.0f;
        }

        private void warmth(float f) {
            float f2;
            float f3 = f;
            if (f <= 0.0f) {
                f3 = 0.01f;
            }
            if ((f = 5000.0f / f3 / 100.0f) > 66.0f) {
                double d = f - 60.0f;
                f2 = (float)Math.pow(d, -0.13320475816726685) * 329.69873f;
                f3 = (float)Math.pow(d, 0.07551484555006027) * 288.12216f;
            } else {
                f3 = (float)Math.log(f) * 99.4708f - 161.11957f;
                f2 = 255.0f;
            }
            f = f < 66.0f ? (f > 19.0f ? (float)Math.log(f - 10.0f) * 138.51773f - 305.0448f : 0.0f) : 255.0f;
            f2 = Math.min(255.0f, Math.max(f2, 0.0f));
            f3 = Math.min(255.0f, Math.max(f3, 0.0f));
            f = Math.min(255.0f, Math.max(f, 0.0f));
            float f4 = (float)Math.log(50.0f);
            float f5 = (float)Math.log(40.0f);
            float f6 = Math.min(255.0f, Math.max(255.0f, 0.0f));
            f4 = Math.min(255.0f, Math.max(f4 * 99.4708f - 161.11957f, 0.0f));
            f5 = Math.min(255.0f, Math.max(f5 * 138.51773f - 305.0448f, 0.0f));
            float[] arrf = this.m;
            arrf[0] = f2 /= f6;
            arrf[1] = 0.0f;
            arrf[2] = 0.0f;
            arrf[3] = 0.0f;
            arrf[4] = 0.0f;
            arrf[5] = 0.0f;
            arrf[6] = f3 /= f4;
            arrf[7] = 0.0f;
            arrf[8] = 0.0f;
            arrf[9] = 0.0f;
            arrf[10] = 0.0f;
            arrf[11] = 0.0f;
            arrf[12] = f /= f5;
            arrf[13] = 0.0f;
            arrf[14] = 0.0f;
            arrf[15] = 0.0f;
            arrf[16] = 0.0f;
            arrf[17] = 0.0f;
            arrf[18] = 1.0f;
            arrf[19] = 0.0f;
        }

        void updateMatrix(ImageView imageView) {
            boolean bl;
            this.mColorMatrix.reset();
            float f = this.mSaturation;
            boolean bl2 = true;
            if (f != 1.0f) {
                this.saturation(f);
                this.mColorMatrix.set(this.m);
                bl = true;
            } else {
                bl = false;
            }
            f = this.mContrast;
            if (f != 1.0f) {
                this.mTmpColorMatrix.setScale(f, f, f, 1.0f);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
                bl = true;
            }
            if ((f = this.mWarmth) != 1.0f) {
                this.warmth(f);
                this.mTmpColorMatrix.set(this.m);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
                bl = true;
            }
            if ((f = this.mBrightness) != 1.0f) {
                this.brightness(f);
                this.mTmpColorMatrix.set(this.m);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
                bl = bl2;
            }
            if (bl) {
                imageView.setColorFilter((ColorFilter)new ColorMatrixColorFilter(this.mColorMatrix));
                return;
            }
            imageView.clearColorFilter();
        }
    }

}

