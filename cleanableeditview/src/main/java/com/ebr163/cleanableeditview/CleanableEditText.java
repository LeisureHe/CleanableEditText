package com.ebr163.cleanableeditview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CleanableEditText extends AppCompatEditText {

    private Drawable drawableRight;
    private Drawable drawableLeft;
    private Drawable drawableTop;
    private Drawable drawableBottom;

    int actionX, actionY;

    public interface DrawableClickListener {
        enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}
        void onClick(DrawablePosition target);
    }

    private DrawableClickListener clickListener;

    public CleanableEditText(Context context) {
        super(context);
        initClickListener();
    }

    public CleanableEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initClickListener();
    }

    public CleanableEditText(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initClickListener();
    }

    private void initClickListener() {
        setDrawableClickListener(new DrawableClickListener() {
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        setText(null);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            drawableLeft = left;
        }
        if (right != null) {
            drawableRight = right;
        }
        if (top != null) {
            drawableTop = top;
        }
        if (bottom != null) {
            drawableBottom = bottom;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = (int) event.getX();
            actionY = (int) event.getY();
            if (drawableBottom != null
                    && drawableBottom.getBounds().contains(actionX, actionY)) {
                clickListener.onClick(DrawableClickListener.DrawablePosition.BOTTOM);
                return super.onTouchEvent(event);
            }

            if (drawableTop != null
                    && drawableTop.getBounds().contains(actionX, actionY)) {
                clickListener.onClick(DrawableClickListener.DrawablePosition.TOP);
                return super.onTouchEvent(event);
            }

            if (drawableLeft != null) {
                bounds = drawableLeft.getBounds();

                int x, y;
                int extraTapArea = (int) (13 * getResources().getDisplayMetrics().density + 0.5);

                x = actionX;
                y = actionY;

                if (!bounds.contains(actionX, actionY)) {
                    x =  actionX - extraTapArea;
                    y =  actionY - extraTapArea;

                    if (x <= 0)
                        x = actionX;

                    if (y <= 0)
                        y = actionY;

                    if (x < y) y = x;
                }

                if (bounds.contains(x, y) && clickListener != null) {
                    clickListener.onClick(DrawableClickListener.DrawablePosition.LEFT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
            }

            if (drawableRight != null) {
                bounds = drawableRight.getBounds();

                int x, y;
                int extraTapArea = 13;

                x = actionX + extraTapArea;
                y = actionY - extraTapArea;

                x = getWidth() - x;

                if (x <= 0) {
                    x += extraTapArea;
                }

                if (y <= 0)
                    y = actionY;

                if (bounds.contains(x, y) && clickListener != null) {
                    clickListener.onClick(DrawableClickListener.DrawablePosition.RIGHT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
                return super.onTouchEvent(event);
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableRight = null;
        drawableBottom = null;
        drawableLeft = null;
        drawableTop = null;
        super.finalize();
    }

    public void setDrawableClickListener(DrawableClickListener listener) {
        this.clickListener = listener;
    }
}

