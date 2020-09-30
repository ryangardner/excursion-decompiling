/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegendRenderer
extends Renderer {
    protected List<LegendEntry> computedEntries = new ArrayList<LegendEntry>(16);
    protected Paint.FontMetrics legendFontMetrics = new Paint.FontMetrics();
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint;
    private Path mLineFormPath = new Path();

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.mLegend = legend;
        viewPortHandler = new Paint(1);
        this.mLegendLabelPaint = viewPortHandler;
        viewPortHandler.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Paint.Align.LEFT);
        viewPortHandler = new Paint(1);
        this.mLegendFormPaint = viewPortHandler;
        viewPortHandler.setStyle(Paint.Style.FILL);
    }

    public void computeLegend(ChartData<?> typeface) {
        block8 : {
            Object object = typeface;
            if (this.mLegend.isLegendCustom()) break block8;
            this.computedEntries.clear();
            int n = 0;
            do {
                block11 : {
                    block13 : {
                        List<Integer> list;
                        int n2;
                        Object t;
                        int n3;
                        block14 : {
                            block9 : {
                                block12 : {
                                    block10 : {
                                        IBarDataSet iBarDataSet;
                                        if (n >= typeface.getDataSetCount()) break block9;
                                        t = ((ChartData)object).getDataSetByIndex(n);
                                        list = t.getColors();
                                        n3 = t.getEntryCount();
                                        if (!(t instanceof IBarDataSet) || !(iBarDataSet = (IBarDataSet)t).isStacked()) break block10;
                                        String[] arrstring = iBarDataSet.getStackLabels();
                                        for (n2 = 0; n2 < list.size() && n2 < iBarDataSet.getStackSize(); ++n2) {
                                            this.computedEntries.add(new LegendEntry(arrstring[n2 % arrstring.length], t.getForm(), t.getFormSize(), t.getFormLineWidth(), t.getFormLineDashEffect(), list.get(n2)));
                                        }
                                        if (iBarDataSet.getLabel() != null) {
                                            this.computedEntries.add(new LegendEntry(t.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, null, 1122867));
                                        }
                                        break block11;
                                    }
                                    if (!(t instanceof IPieDataSet)) break block12;
                                    object = (IPieDataSet)t;
                                    for (n2 = 0; n2 < list.size() && n2 < n3; ++n2) {
                                        this.computedEntries.add(new LegendEntry(((PieEntry)object.getEntryForIndex(n2)).getLabel(), t.getForm(), t.getFormSize(), t.getFormLineWidth(), t.getFormLineDashEffect(), list.get(n2)));
                                    }
                                    if (object.getLabel() != null) {
                                        this.computedEntries.add(new LegendEntry(t.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, null, 1122867));
                                    }
                                    break block13;
                                }
                                if (!(t instanceof ICandleDataSet) || (object = (ICandleDataSet)t).getDecreasingColor() == 1122867) break block14;
                                n2 = object.getDecreasingColor();
                                n3 = object.getIncreasingColor();
                                this.computedEntries.add(new LegendEntry(null, t.getForm(), t.getFormSize(), t.getFormLineWidth(), t.getFormLineDashEffect(), n2));
                                this.computedEntries.add(new LegendEntry(t.getLabel(), t.getForm(), t.getFormSize(), t.getFormLineWidth(), t.getFormLineDashEffect(), n3));
                                break block13;
                            }
                            if (this.mLegend.getExtraEntries() != null) {
                                Collections.addAll(this.computedEntries, this.mLegend.getExtraEntries());
                            }
                            this.mLegend.setEntries(this.computedEntries);
                            break;
                        }
                        for (n2 = 0; n2 < list.size() && n2 < n3; ++n2) {
                            object = n2 < list.size() - 1 && n2 < n3 - 1 ? null : typeface.getDataSetByIndex(n).getLabel();
                            this.computedEntries.add(new LegendEntry((String)object, t.getForm(), t.getFormSize(), t.getFormLineWidth(), t.getFormLineDashEffect(), list.get(n2)));
                        }
                    }
                    object = typeface;
                }
                ++n;
            } while (true);
        }
        if ((typeface = this.mLegend.getTypeface()) != null) {
            this.mLegendLabelPaint.setTypeface(typeface);
        }
        this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
        this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
        this.mLegend.calculateDimensions(this.mLegendLabelPaint, this.mViewPortHandler);
    }

    protected void drawForm(Canvas canvas, float f, float f2, LegendEntry legendEntry, Legend legend) {
        Legend.LegendForm legendForm;
        if (legendEntry.formColor == 1122868) return;
        if (legendEntry.formColor == 1122867) return;
        if (legendEntry.formColor == 0) {
            return;
        }
        int n = canvas.save();
        Legend.LegendForm legendForm2 = legendForm = legendEntry.form;
        if (legendForm == Legend.LegendForm.DEFAULT) {
            legendForm2 = legend.getForm();
        }
        this.mLegendFormPaint.setColor(legendEntry.formColor);
        float f3 = Float.isNaN(legendEntry.formSize) ? legend.getFormSize() : legendEntry.formSize;
        float f4 = Utils.convertDpToPixel(f3);
        f3 = f4 / 2.0f;
        int n2 = 1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[legendForm2.ordinal()];
        if (n2 != 3 && n2 != 4) {
            if (n2 != 5) {
                if (n2 == 6) {
                    f3 = Float.isNaN(legendEntry.formLineWidth) ? legend.getFormLineWidth() : legendEntry.formLineWidth;
                    f3 = Utils.convertDpToPixel(f3);
                    legendEntry = legendEntry.formLineDashEffect == null ? legend.getFormLineDashEffect() : legendEntry.formLineDashEffect;
                    this.mLegendFormPaint.setStyle(Paint.Style.STROKE);
                    this.mLegendFormPaint.setStrokeWidth(f3);
                    this.mLegendFormPaint.setPathEffect((PathEffect)legendEntry);
                    this.mLineFormPath.reset();
                    this.mLineFormPath.moveTo(f, f2);
                    this.mLineFormPath.lineTo(f + f4, f2);
                    canvas.drawPath(this.mLineFormPath, this.mLegendFormPaint);
                }
            } else {
                this.mLegendFormPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(f, f2 - f3, f + f4, f2 + f3, this.mLegendFormPaint);
            }
        } else {
            this.mLegendFormPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(f + f3, f2, f3, this.mLegendFormPaint);
        }
        canvas.restoreToCount(n);
    }

    protected void drawLabel(Canvas canvas, float f, float f2, String string2) {
        canvas.drawText(string2, f, f2, this.mLegendLabelPaint);
    }

    public Paint getFormPaint() {
        return this.mLegendFormPaint;
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    public void renderLegend(Canvas canvas) {
        float f;
        float f2;
        Legend.LegendHorizontalAlignment legendHorizontalAlignment;
        float f3;
        float f4;
        Object object;
        List<Boolean> list;
        float f5;
        float f6;
        float f7;
        float f8;
        List<FSize> list2;
        float f9;
        Legend.LegendDirection legendDirection;
        Object object2;
        LegendEntry[] arrlegendEntry;
        int n;
        float f10;
        float f11;
        block44 : {
            block43 : {
                block42 : {
                    if (!this.mLegend.isEnabled()) {
                        return;
                    }
                    object = this.mLegend.getTypeface();
                    if (object != null) {
                        this.mLegendLabelPaint.setTypeface((Typeface)object);
                    }
                    this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
                    this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
                    f11 = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
                    f9 = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
                    f3 = f11 - (float)Utils.calcTextHeight(this.mLegendLabelPaint, "ABC") / 2.0f;
                    arrlegendEntry = this.mLegend.getEntries();
                    f8 = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
                    f2 = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
                    object = this.mLegend.getOrientation();
                    legendHorizontalAlignment = this.mLegend.getHorizontalAlignment();
                    object2 = this.mLegend.getVerticalAlignment();
                    legendDirection = this.mLegend.getDirection();
                    f6 = Utils.convertDpToPixel(this.mLegend.getFormSize());
                    f = Utils.convertDpToPixel(this.mLegend.getStackSpace());
                    f7 = this.mLegend.getYOffset();
                    f4 = this.mLegend.getXOffset();
                    n = 1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[legendHorizontalAlignment.ordinal()];
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                f4 = 0.0f;
                            } else {
                                f5 = object == Legend.LegendOrientation.VERTICAL ? this.mViewPortHandler.getChartWidth() / 2.0f : this.mViewPortHandler.contentLeft() + this.mViewPortHandler.contentWidth() / 2.0f;
                                f10 = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? f4 : -f4;
                                f5 += f10;
                                if (object == Legend.LegendOrientation.VERTICAL) {
                                    double d = f5;
                                    double d2 = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? (double)(-this.mLegend.mNeededWidth) / 2.0 + (double)f4 : (double)this.mLegend.mNeededWidth / 2.0 - (double)f4;
                                    f4 = (float)(d + d2);
                                } else {
                                    f4 = f5;
                                }
                            }
                        } else {
                            f5 = object == Legend.LegendOrientation.VERTICAL ? this.mViewPortHandler.getChartWidth() : this.mViewPortHandler.contentRight();
                            f5 -= f4;
                            f4 = f5;
                            if (legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT) {
                                f4 = f5 - this.mLegend.mNeededWidth;
                            }
                        }
                    } else {
                        if (object != Legend.LegendOrientation.VERTICAL) {
                            f4 += this.mViewPortHandler.contentLeft();
                        }
                        if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                            f4 = this.mLegend.mNeededWidth + f4;
                        }
                    }
                    n = 1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[((Enum)object).ordinal()];
                    if (n == 1) break block42;
                    if (n != 2) {
                        return;
                    }
                    n = 1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[object2.ordinal()];
                    if (n != 1) {
                        if (n != 2) {
                            f5 = n != 3 ? 0.0f : this.mViewPortHandler.getChartHeight() / 2.0f - this.mLegend.mNeededHeight / 2.0f + this.mLegend.getYOffset();
                        } else {
                            f5 = legendHorizontalAlignment == Legend.LegendHorizontalAlignment.CENTER ? this.mViewPortHandler.getChartHeight() : this.mViewPortHandler.contentBottom();
                            f5 -= this.mLegend.mNeededHeight + f7;
                        }
                    } else {
                        f5 = legendHorizontalAlignment == Legend.LegendHorizontalAlignment.CENTER ? 0.0f : this.mViewPortHandler.contentTop();
                        f5 += f7;
                    }
                    break block43;
                }
                object = this.mLegend.getCalculatedLineSizes();
                list2 = this.mLegend.getCalculatedLabelSizes();
                list = this.mLegend.getCalculatedLabelBreakPoints();
                n = 1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[object2.ordinal()];
                f5 = f7;
                if (n != 1) {
                    f5 = n != 2 ? (n != 3 ? 0.0f : f7 + (this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0f) : this.mViewPortHandler.getChartHeight() - f7 - this.mLegend.mNeededHeight;
                }
                break block44;
            }
            f2 = f5;
            boolean bl = false;
            n = 0;
            float f12 = 0.0f;
            f5 = f;
            f10 = f3;
            f = f2;
            object = legendDirection;
            while (n < arrlegendEntry.length) {
                LegendEntry legendEntry = arrlegendEntry[n];
                boolean bl2 = legendEntry.form != Legend.LegendForm.NONE;
                f3 = Float.isNaN(legendEntry.formSize) ? f6 : Utils.convertDpToPixel(legendEntry.formSize);
                if (bl2) {
                    f2 = object == Legend.LegendDirection.LEFT_TO_RIGHT ? f4 + f12 : f4 - (f3 - f12);
                    f7 = f2;
                    this.drawForm(canvas, f7, f + f10, legendEntry, this.mLegend);
                    f2 = f7;
                    if (object == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        f2 = f7 + f3;
                    }
                } else {
                    f2 = f4;
                }
                f7 = f5;
                if (legendEntry.label != null) {
                    if (bl2 && !bl) {
                        f5 = object == Legend.LegendDirection.LEFT_TO_RIGHT ? f8 : -f8;
                        f5 = f2 + f5;
                    } else {
                        f5 = f2;
                        if (bl) {
                            f5 = f4;
                        }
                    }
                    f2 = f5;
                    if (object == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f2 = f5 - (float)Utils.calcTextWidth(this.mLegendLabelPaint, legendEntry.label);
                    }
                    if (!bl) {
                        this.drawLabel(canvas, f2, f + f11, legendEntry.label);
                    } else {
                        this.drawLabel(canvas, f2, (f += f11 + f9) + f11, legendEntry.label);
                    }
                    f += f11 + f9;
                    f2 = 0.0f;
                } else {
                    f2 = f12 + (f3 + f7);
                    bl = true;
                }
                ++n;
                f5 = f7;
                f12 = f2;
            }
            return;
        }
        int n2 = arrlegendEntry.length;
        f7 = f4;
        n = 0;
        int n3 = 0;
        f10 = f;
        f = f4;
        while (n < n2) {
            object2 = arrlegendEntry[n];
            boolean bl = ((LegendEntry)object2).form != Legend.LegendForm.NONE;
            float f13 = Float.isNaN(((LegendEntry)object2).formSize) ? f6 : Utils.convertDpToPixel(((LegendEntry)object2).formSize);
            if (n < list.size() && list.get(n).booleanValue()) {
                f5 += f11 + f9;
                f4 = f;
            } else {
                f4 = f7;
            }
            if (f4 == f && legendHorizontalAlignment == Legend.LegendHorizontalAlignment.CENTER && n3 < object.size()) {
                f7 = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? ((FSize)object.get((int)n3)).width : -((FSize)object.get((int)n3)).width;
                f4 += f7 / 2.0f;
                ++n3;
            }
            boolean bl3 = ((LegendEntry)object2).label == null;
            if (bl) {
                f7 = f4;
                if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                    f7 = f4 - f13;
                }
                this.drawForm(canvas, f7, f5 + f3, (LegendEntry)object2, this.mLegend);
                f4 = legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? f7 + f13 : f7;
            }
            if (!bl3) {
                f7 = f4;
                if (bl) {
                    f7 = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? -f8 : f8;
                    f7 = f4 + f7;
                }
                f4 = f7;
                if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                    f4 = f7 - list2.get((int)n).width;
                }
                this.drawLabel(canvas, f4, f5 + f11, ((LegendEntry)object2).label);
                f7 = f4;
                if (legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT) {
                    f7 = f4 + list2.get((int)n).width;
                }
                f4 = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? -f2 : f2;
                f4 = f7 + f4;
            } else {
                f7 = legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT ? -f10 : f10;
                f4 += f7;
            }
            ++n;
            f7 = f4;
        }
    }

}

