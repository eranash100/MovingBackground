package com.example.mymovingbackgroundobject;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateInterpolator;

import java.util.Random;

public class MyAnimator
{
    private final int tag;
    private ValueAnimator valueAnimator;
    private final ImageSource target;

    public MyAnimator(Random rnd, ImageSource target, int tag, int minDurationInMillis, int maxDurationInMillis, int minDelayInMillis, int maxDelayInMillis, ValueAnimator.AnimatorUpdateListener updateListener, Animator.AnimatorListener listener)
    {
        this.tag = tag;
        this.target = target;
        this.valueAnimator = new ValueAnimator();
        int duration = minDurationInMillis + rnd.nextInt(maxDurationInMillis - minDurationInMillis + 1);
        this.valueAnimator.setDuration(duration);
        this.valueAnimator.setStartDelay(0);
        if(1 + rnd.nextInt(100) > 25) // 25% of Not Giving a Animation delay and 75% Yes
            this.valueAnimator.setStartDelay(minDelayInMillis + rnd.nextInt(maxDelayInMillis - minDelayInMillis + 1));
        this.valueAnimator.setInterpolator(new AccelerateInterpolator());
        this.valueAnimator.addUpdateListener(updateListener);
        this.valueAnimator.addListener(listener);

        this.valueAnimator.setFloatValues(0f, 1f);
        this.valueAnimator.start();
    }
}
