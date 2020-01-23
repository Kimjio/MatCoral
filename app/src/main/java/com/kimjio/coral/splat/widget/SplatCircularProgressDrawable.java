package com.kimjio.coral.splat.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.kimjio.coral.R;

@SuppressLint("RestrictedApi")
public class SplatCircularProgressDrawable extends CircularProgressDrawable {

    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();

    private static final float CENTER_RADIUS = 7.5f;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_HEIGHT = 5;

    /**
     * This is the default set of colors that's used in spinner. {@link
     * #setColorSchemeColors(int...)} allows modifying colors.
     */
    private static final int[] COLORS = new int[]{
            Color.BLACK
    };

    /**
     * The value in the linear interpolator for animating the drawable at which
     * the color transition should start
     */
    private static final float COLOR_CHANGE_OFFSET = 0.75f;

    /**
     * The duration of a single progress spin in milliseconds.
     */
    private static final int ANIMATION_DURATION = 1332;

    /**
     * Full rotation that's done for the animation duration in degrees.
     */
    private static final float GROUP_FULL_ROTATION = 1080f / 5f;
    /**
     * Maximum length of the progress arc during the animation.
     */
    private static final float MAX_PROGRESS_ARC = .8f;
    /**
     * Minimum length of the progress arc during the animation.
     */
    private static final float MIN_PROGRESS_ARC = .01f;
    /**
     * Rotation applied to ring during the animation, to complete it to a full circle.
     */
    private static final float RING_ROTATION = 1f - (MAX_PROGRESS_ARC - MIN_PROGRESS_ARC);
    /**
     * The indicator ring, used to manage animation state.
     */
    private final Ring mRing;
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            float mRotationCount;
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            boolean mFinishing;
    /**
     * Canvas rotation in degrees.
     */
    private float mRotation;
    private Resources mResources;
    private Animator mAnimator;

    /**
     * @param context application context
     */
    SplatCircularProgressDrawable(@NonNull Context context) {
        super(context);
        mResources = Preconditions.checkNotNull(context).getResources();

        mRing = new Ring(mResources);
        mRing.setColors(COLORS);

        setSizeParameters();
        setupAnimators();
    }

    /**
     * Sets all parameters at once in dp.
     */
    private void setSizeParameters() {
        final Ring ring = mRing;
        final DisplayMetrics metrics = mResources.getDisplayMetrics();
        final float screenDensity = metrics.density;

        ring.setCenterRadius(CENTER_RADIUS * screenDensity);
        ring.setColorIndex(0);
        ring.setArrowDimensions((float) ARROW_WIDTH * screenDensity, (float) ARROW_HEIGHT * screenDensity);
    }

    @Deprecated
    @Override
    public void setStyle(int size) {
    }

    /**
     * Returns the stroke width for the progress spinner in pixels.
     *
     * @return stroke width in pixels
     */
    @Deprecated
    @Override
    public float getStrokeWidth() {
        return 2.5f;
    }

    /**
     * Sets the stroke width for the progress spinner in pixels.
     *
     * @param strokeWidth stroke width in pixels
     */
    @Override
    @Deprecated
    public void setStrokeWidth(float strokeWidth) {
    }

    /**
     * Returns the center radius for the progress spinner in pixels.
     *
     * @return center radius in pixels
     */
    @Override
    public float getCenterRadius() {
        return mRing.getCenterRadius();
    }

    /**
     * Sets the center radius for the progress spinner in pixels. If set to 0, this drawable will
     * fill the bounds when drawn.
     *
     * @param centerRadius center radius in pixels
     */
    @Override
    public void setCenterRadius(float centerRadius) {
        mRing.setCenterRadius(centerRadius);
        invalidateSelf();
    }

    /**
     * Returns the stroke cap of the progress spinner.
     *
     * @return stroke cap
     */
    @NonNull
    @Deprecated
    @Override
    public Paint.Cap getStrokeCap() {
        return mRing.getStrokeCap();
    }

    /**
     * Sets the stroke cap of the progress spinner. Default stroke cap is {@link Paint.Cap#SQUARE}.
     *
     * @param strokeCap stroke cap
     */
    @Deprecated
    @Override
    public void setStrokeCap(@NonNull Paint.Cap strokeCap) {
        mRing.setStrokeCap(strokeCap);
        invalidateSelf();
    }

    /**
     * Returns the arrow width in pixels.
     *
     * @return arrow width in pixels
     */
    @Deprecated
    @Override
    public float getArrowWidth() {
        return mRing.getArrowWidth();
    }

    /**
     * Returns the arrow height in pixels.
     *
     * @return arrow height in pixels
     */
    @Deprecated
    @Override
    public float getArrowHeight() {
        return mRing.getArrowHeight();
    }

    /**
     * Sets the dimensions of the arrow at the end of the spinner in pixels.
     *
     * @param width  width of the baseline of the arrow in pixels
     * @param height distance from tip of the arrow to its baseline in pixels
     */
    @Deprecated
    @Override
    public void setArrowDimensions(float width, float height) {
        mRing.setArrowDimensions(width, height);
        invalidateSelf();
    }

    /**
     * Returns {@code true} if the arrow at the end of the spinner is shown.
     *
     * @return {@code true} if the arrow is shown, {@code false} otherwise.
     */
    @Deprecated
    @Override
    public boolean getArrowEnabled() {
        return mRing.getShowArrow();
    }

    /**
     * Sets if the arrow at the end of the spinner should be shown.
     *
     * @param show {@code true} if the arrow should be drawn, {@code false} otherwise
     */
    @Deprecated
    @Override
    public void setArrowEnabled(boolean show) {
        mRing.setShowArrow(show);
        invalidateSelf();
    }

    /**
     * Returns the scale of the arrow at the end of the spinner.
     *
     * @return scale of the arrow
     */
    @Deprecated
    @Override
    public float getArrowScale() {
        return mRing.getArrowScale();
    }

    /**
     * Sets the scale of the arrow at the end of the spinner.
     *
     * @param scale scaling that will be applied to the arrow's both width and height when drawing.
     */
    @Deprecated
    @Override
    public void setArrowScale(float scale) {
        mRing.setArrowScale(scale);
        invalidateSelf();
    }

    /**
     * Returns the start trim for the progress spinner arc
     *
     * @return start trim from [0..1]
     */
    @Override
    public float getStartTrim() {
        return mRing.getStartTrim();
    }

    /**
     * Returns the end trim for the progress spinner arc
     *
     * @return end trim from [0..1]
     */
    @Override
    public float getEndTrim() {
        return mRing.getEndTrim();
    }

    /**
     * Sets the start and end trim for the progress spinner arc. 0 corresponds to the geometric
     * angle of 0 degrees (3 o'clock on a watch) and it increases clockwise, coming to a full circle
     * at 1.
     *
     * @param start starting position of the arc from [0..1]
     * @param end   ending position of the arc from [0..1]
     */
    @Override
    public void setStartEndTrim(float start, float end) {
        mRing.setStartTrim(start);
        mRing.setEndTrim(end);
        invalidateSelf();
    }

    /**
     * Returns the amount of rotation applied to the progress spinner.
     *
     * @return amount of rotation from [0..1]
     */
    @Override
    public float getProgressRotation() {
        return mRing.getRotation();
    }

    /**
     * Sets the amount of rotation to apply to the progress spinner.
     *
     * @param rotation rotation from [0..1]
     */
    @Override
    public void setProgressRotation(float rotation) {
        mRing.setRotation(rotation);
        invalidateSelf();
    }

    /**
     * Returns the background color of the circle drawn inside the drawable.
     *
     * @return an ARGB color
     */
    @Override
    public int getBackgroundColor() {
        return mRing.getBackgroundColor();
    }

    /**
     * Sets the background color of the circle inside the drawable. Calling {@link
     * #setAlpha(int)} does not affect the visibility background color, so it should be set
     * separately if it needs to be hidden or visible.
     *
     * @param color an ARGB color
     */
    @Override
    public void setBackgroundColor(int color) {
        mRing.setBackgroundColor(color);
        invalidateSelf();
    }

    /**
     * Returns the colors used in the progress animation
     *
     * @return list of ARGB colors
     */
    @NonNull
    @Override
    public int[] getColorSchemeColors() {
        return mRing.getColors();
    }

    /**
     * Sets the colors used in the progress animation from a color list. The first color will also
     * be the color to be used if animation is not started yet.
     *
     * @param colors list of ARGB colors to be used in the spinner
     */
    @Override
    public void setColorSchemeColors(@NonNull int... colors) {
        mRing.setColors(colors);
        mRing.setColorIndex(0);
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        mRing.draw(canvas, bounds);
        canvas.restore();
    }

    @Override
    public int getAlpha() {
        return mRing.getAlpha();
    }

    @Override
    public void setAlpha(int alpha) {
        mRing.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mRing.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @SuppressWarnings("unused")
    private float getRotation() {
        return mRotation;
    }

    private void setRotation(float rotation) {
        mRotation = rotation;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    /**
     * Starts the animation for the spinner.
     */
    @Override
    public void start() {
        mAnimator.cancel();
        mRing.storeOriginals();
        // Already showing some part of the ring
        if (mRing.getEndTrim() != mRing.getStartTrim()) {
            mFinishing = true;
            mAnimator.setDuration(ANIMATION_DURATION / 2);
            mAnimator.start();
        } else {
            mRing.setColorIndex(0);
            mRing.resetOriginals();
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.start();
        }
    }

    /**
     * Stops the animation for the spinner.
     */
    @Override
    public void stop() {
        mAnimator.cancel();
        setRotation(0);
        mRing.setShowArrow(false);
        mRing.setColorIndex(0);
        mRing.resetOriginals();
        invalidateSelf();
    }

    // Adapted from ArgbEvaluator.java
    private int evaluateColorChange(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24
                | (startR + (int) (fraction * (endR - startR))) << 16
                | (startG + (int) (fraction * (endG - startG))) << 8
                | (startB + (int) (fraction * (endB - startB)));
    }

    /**
     * Update the ring color if this is within the last 25% of the animation.
     * The new ring color will be a translation from the starting ring color to
     * the next color.
     */
    @SuppressWarnings("WeakerAccess") /* synthetic access */
    void updateRingColor(float interpolatedTime, Ring ring) {
        if (interpolatedTime > COLOR_CHANGE_OFFSET) {
            ring.setColor(evaluateColorChange((interpolatedTime - COLOR_CHANGE_OFFSET)
                            / (1f - COLOR_CHANGE_OFFSET), ring.getStartingColor(),
                    ring.getNextColor()));
        } else {
            ring.setColor(ring.getStartingColor());
        }
    }

    /**
     * Update the ring start and end trim if the animation is finishing (i.e. it started with
     * already visible progress, so needs to shrink back down before starting the spinner).
     */
    private void applyFinishTranslation(float interpolatedTime, Ring ring) {
        // shrink back down and complete a full rotation before
        // starting other circles
        // Rotation goes between [0..1].
        updateRingColor(interpolatedTime, ring);
        float targetRotation = (float) (Math.floor(ring.getStartingRotation() / MAX_PROGRESS_ARC)
                + 1f);
        final float startTrim = ring.getStartingStartTrim()
                + (ring.getStartingEndTrim() - MIN_PROGRESS_ARC - ring.getStartingStartTrim())
                * interpolatedTime;
        ring.setStartTrim(startTrim);
        ring.setEndTrim(ring.getStartingEndTrim());
        final float rotation = ring.getStartingRotation()
                + ((targetRotation - ring.getStartingRotation()) * interpolatedTime);
        ring.setRotation(rotation);
    }

    /**
     * Update the ring start and end trim according to current time of the animation.
     */
    @SuppressWarnings("WeakerAccess") /* synthetic access */
    void applyTransformation(float interpolatedTime, Ring ring, boolean lastFrame) {
        if (mFinishing) {
            applyFinishTranslation(interpolatedTime, ring);
            // Below condition is to work around a ValueAnimator issue where onAnimationRepeat is
            // called before last frame (1f).
        } else if (interpolatedTime != 1f || lastFrame) {
            final float startingRotation = ring.getStartingRotation();

            final float rotation = startingRotation + (RING_ROTATION * interpolatedTime);
            float groupRotation = GROUP_FULL_ROTATION * (interpolatedTime + mRotationCount);

            ring.setRotation(rotation);
            setRotation(groupRotation);
        }
    }

    private void setupAnimators() {
        final Ring ring = mRing;
        final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> {
            float interpolatedTime = (float) animation.getAnimatedValue();
            updateRingColor(interpolatedTime, ring);
            applyTransformation(interpolatedTime, ring, false);
            invalidateSelf();
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(LINEAR_INTERPOLATOR);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                mRotationCount = 0;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // do nothing
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // do nothing
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                applyTransformation(1f, ring, true);
                ring.storeOriginals();
                ring.goToNextColor();
                if (mFinishing) {
                    // finished closing the last ring from the swipe gesture; go
                    // into progress mode
                    mFinishing = false;
                    animator.cancel();
                    animator.setDuration(ANIMATION_DURATION);
                    animator.start();
                    ring.setShowArrow(false);
                } else {
                    mRotationCount = mRotationCount + 1;
                }
            }
        });
        mAnimator = animator;
    }

    /**
     * A private class to do all the drawing of CircularProgressDrawable, which includes background,
     * progress spinner and the arrow. This class is to separate drawing from animation.
     */
    private static class Ring {
        final RectF mTempBounds = new RectF();
        final Paint mPaint = new Paint();
        final Paint mArrowPaint = new Paint();
        final Paint mCirclePaint = new Paint();
        final Drawable mSplatRing;
        float mStartTrim = 0f;
        float mEndTrim = 0f;
        float mRotation = 0f;
        int[] mColors;
        // mColorIndex represents the offset into the available mColors that the
        // progress circle should currently display. As the progress circle is
        // animating, the mColorIndex moves by one to the next available color.
        int mColorIndex;
        float mStartingStartTrim;
        float mStartingEndTrim;
        float mStartingRotation;
        boolean mShowArrow;

        float mArrowScale = 1;
        float mRingCenterRadius;
        int mArrowWidth;
        int mArrowHeight;
        int mAlpha = 255;
        int mCurrentColor;

        Ring(Resources resources) {
            mPaint.setStrokeCap(Paint.Cap.SQUARE);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);

            mArrowPaint.setStyle(Paint.Style.FILL);
            mArrowPaint.setAntiAlias(true);

            mCirclePaint.setColor(Color.TRANSPARENT);
            mSplatRing = resources.getDrawable(R.drawable.ic_splat_refresh);
        }

        /**
         * Sets the dimensions of the arrowhead.
         *
         * @param width  width of the hypotenuse of the arrow head
         * @param height height of the arrow point
         */
        void setArrowDimensions(float width, float height) {
            mArrowWidth = (int) width;
            mArrowHeight = (int) height;
        }

        Paint.Cap getStrokeCap() {
            return mPaint.getStrokeCap();
        }

        void setStrokeCap(Paint.Cap strokeCap) {
            mPaint.setStrokeCap(strokeCap);
        }

        float getArrowWidth() {
            return mArrowWidth;
        }

        float getArrowHeight() {
            return mArrowHeight;
        }

        /**
         * Draw the progress spinner
         */
        void draw(Canvas c, Rect bounds) {
            final RectF arcBounds = mTempBounds;
            float arcRadius = mRingCenterRadius + 2.5f;
            if (mRingCenterRadius <= 0) {
                // If center radius is not set, fill the bounds
                arcRadius = Math.min(bounds.width(), bounds.height()) / 2f - Math.max(
                        (mArrowWidth * mArrowScale) / 2f, 2.5f);
            }
            arcBounds.set(bounds.centerX() - arcRadius,
                    bounds.centerY() - arcRadius,
                    bounds.centerX() + arcRadius,
                    bounds.centerY() + arcRadius);

            final float startAngle = (mStartTrim + mRotation) * 360;
            final float endAngle = (mEndTrim + mRotation) * 360;
            float sweepAngle = endAngle - startAngle;

            DrawableCompat.setTint(DrawableCompat.wrap(mSplatRing), mCurrentColor);
            mPaint.setColor(mCurrentColor);
            mPaint.setAlpha(mAlpha);

            // Draw the background first
            float inset = 2.5f; // Calculate inset to draw inside the arc
            arcBounds.inset(inset, inset); // Apply inset
            c.drawCircle(arcBounds.centerX(), arcBounds.centerY(), arcBounds.width() / 2f,
                    mCirclePaint);
            arcBounds.inset(-inset, -inset); // Revert the inset

            mSplatRing.setBounds(new Rect(Math.round(arcBounds.left), Math.round(arcBounds.top), Math.round(arcBounds.right), Math.round(arcBounds.bottom)));
            c.save();
            c.rotate(startAngle + sweepAngle, bounds.centerX(), bounds.centerY());
            mSplatRing.draw(c);
            c.restore();
        }

        int[] getColors() {
            return mColors;
        }

        /**
         * Sets the colors the progress spinner alternates between.
         *
         * @param colors array of ARGB colors. Must be non-{@code null}.
         */
        void setColors(@NonNull int[] colors) {
            mColors = colors;
            // if colors are reset, make sure to reset the color index as well
            setColorIndex(0);
        }

        /**
         * Sets the absolute color of the progress spinner. This is should only
         * be used when animating between current and next color when the
         * spinner is rotating.
         *
         * @param color an ARGB color
         */
        void setColor(int color) {
            mCurrentColor = color;
        }

        int getBackgroundColor() {
            return mCirclePaint.getColor();
        }

        /**
         * Sets the background color of the circle inside the spinner.
         */
        void setBackgroundColor(int color) {
            mCirclePaint.setColor(color);
        }

        /**
         * @param index index into the color array of the color to display in
         *              the progress spinner.
         */
        void setColorIndex(int index) {
            mColorIndex = index;
            mCurrentColor = mColors[mColorIndex];
        }

        /**
         * @return int describing the next color the progress spinner should use when drawing.
         */
        int getNextColor() {
            return mColors[getNextColorIndex()];
        }

        int getNextColorIndex() {
            return (mColorIndex + 1) % (mColors.length);
        }

        /**
         * Proceed to the next available ring color. This will automatically
         * wrap back to the beginning of colors.
         */
        void goToNextColor() {
            setColorIndex(getNextColorIndex());
        }

        void setColorFilter(ColorFilter filter) {
            mPaint.setColorFilter(filter);
            mSplatRing.setColorFilter(filter);
        }

        /**
         * @return current alpha of the progress spinner and arrowhead
         */
        int getAlpha() {
            return mAlpha;
        }

        /**
         * @param alpha alpha of the progress spinner and associated arrowhead.
         */
        void setAlpha(int alpha) {
            mAlpha = alpha;
        }

        float getStartTrim() {
            return mStartTrim;
        }

        void setStartTrim(float startTrim) {
            mStartTrim = startTrim;
        }

        float getStartingStartTrim() {
            return mStartingStartTrim;
        }

        float getStartingEndTrim() {
            return mStartingEndTrim;
        }

        int getStartingColor() {
            return mColors[mColorIndex];
        }

        float getEndTrim() {
            return mEndTrim;
        }

        void setEndTrim(float endTrim) {
            mEndTrim = endTrim;
        }

        float getRotation() {
            return mRotation;
        }

        void setRotation(float rotation) {
            mRotation = rotation;
        }

        float getCenterRadius() {
            return mRingCenterRadius;
        }

        /**
         * @param centerRadius inner radius in px of the circle the progress spinner arc traces
         */
        void setCenterRadius(float centerRadius) {
            mRingCenterRadius = centerRadius;
        }

        boolean getShowArrow() {
            return mShowArrow;
        }

        /**
         * @param show {@code true} if should show the arrow head on the progress spinner
         */
        void setShowArrow(boolean show) {
            if (mShowArrow != show) {
                mShowArrow = show;
            }
        }

        float getArrowScale() {
            return mArrowScale;
        }

        /**
         * @param scale scale of the arrowhead for the spinner
         */
        void setArrowScale(float scale) {
            if (scale != mArrowScale) {
                mArrowScale = scale;
            }
        }

        /**
         * @return The amount the progress spinner is currently rotated, between [0..1].
         */
        float getStartingRotation() {
            return mStartingRotation;
        }

        /**
         * If the start / end trim are offset to begin with, store them so that animation starts
         * from that offset.
         */
        void storeOriginals() {
            mStartingStartTrim = mStartTrim;
            mStartingEndTrim = mEndTrim;
            mStartingRotation = mRotation;
        }

        /**
         * Reset the progress spinner to default rotation, start and end angles.
         */
        void resetOriginals() {
            mStartingStartTrim = 0;
            mStartingEndTrim = 0;
            mStartingRotation = 0;
            setStartTrim(0);
            setEndTrim(0);
            setRotation(0);
        }
    }
}
