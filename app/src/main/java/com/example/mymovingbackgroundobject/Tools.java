package com.example.mymovingbackgroundobject;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;

public class Tools
{

    protected static void setShrinkAnimationOnView(final View target, int percents, final int durationInMillis)
    {
        final float finalPercent = (float) (100 - percents) / 100;
        target.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(target,
                                "scaleX", finalPercent);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(target,
                                "scaleY", finalPercent);
                        scaleDownX.setDuration(durationInMillis);
                        scaleDownY.setDuration(durationInMillis);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();
                        break;

                    case MotionEvent.ACTION_UP:
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(
                                target, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(
                                target, "scaleY", 1f);
                        scaleDownX2.setDuration(durationInMillis);
                        scaleDownY2.setDuration(durationInMillis);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);

                        scaleDown2.start();
                        break;
                }
                return false;
            }
        });
    }

}
