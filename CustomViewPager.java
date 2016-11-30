import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SAI on 20/11/16.
 */

public class CustomViewPager extends ViewPager {


    private boolean autoScrollEnable = true;
    private boolean enableOnlyOneRoundOfScrolling = false;
    private boolean disableFingerScroll = false;

    private Timer timer;

    private int timerDelay = 2500; // (ms) delay of the timer for getting the viewpager to scroll to next position

    private int pageScrollDuration = 800; // (ms) duration of pager to scroll to next page

    private PagerAdapter adapter;

    private Handler handler = new Handler();


    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImagesViewPagerAttrs, 0, 0);


        enableOnlyOneRoundOfScrolling = a.getBoolean(R.styleable.ImagesViewPagerAttrs_enable_only_one_round_of_scrolling, false);

        if (enableOnlyOneRoundOfScrolling) {

            autoScrollEnable = true;

        } else {

            autoScrollEnable = a.getBoolean(R.styleable.ImagesViewPagerAttrs_auto_scroll, false);

        }

        disableFingerScroll = a.getBoolean(R.styleable.ImagesViewPagerAttrs_disable_finger_scroll,false);


        a.recycle();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == adapter.getCount() - 1 && enableOnlyOneRoundOfScrolling) {
                    //stop timer
                    setTimer(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setMyScroller();

    }


    public void setTimerDelay(int delayInMilliSec) {
        if (delayInMilliSec > pageScrollDuration) { // min delay must be 801ms, because scroller delay is 800ms
            timerDelay = delayInMilliSec;
            setTimer(true);
        }
    }


    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == View.VISIBLE) { // on resume is called

            setTimer(true);

        } else { // on pause called

            setTimer(false);

        }

    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        this.adapter = adapter;

        if (timer == null) {
            setTimer(true);
        }

    }

    public void setTimer(boolean start) {

        if (!autoScrollEnable) {
            return;
        }

        if (adapter == null || adapter.getCount() < 2) {
            return;
        }

        if (start) {

            if (timer != null)
                return;

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                if (getCurrentItem() < adapter.getCount() - 1) {
                                    setCurrentItem(getCurrentItem() + 1); // move to next item of viewpager
                                } else {
                                    setCurrentItem(0); // move to first item of viewpager
                                }
                            }

                        }
                    });
                }
            }, timerDelay, timerDelay);

        } else {

            if (timer != null) {
                timer.purge(); //removes all the cancelled tasks
                timer.cancel();
                timer = null;
            }
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (disableFingerScroll)
            return false;


        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
                setTimer(true);
                break;
            case MotionEvent.ACTION_DOWN:
                setTimer(false);
                break;
            case MotionEvent.ACTION_UP:
                setTimer(true);
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (disableFingerScroll)
            return false;

        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
                setTimer(true);
                break;
            case MotionEvent.ACTION_DOWN:
                setTimer(false);
                break;
            case MotionEvent.ACTION_UP:
                setTimer(true);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    // for controlling scroll speed
    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new CustomViewPager.MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, pageScrollDuration);
        }
    }

}
