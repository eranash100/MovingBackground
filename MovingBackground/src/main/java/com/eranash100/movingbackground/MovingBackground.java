package com.eranash100.movingbackground;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Random;

public class MovingBackground extends View
{
    private int image_source_id;
    private int numberOfImages;
    private int minImageScale;
    private int maxImageScale;
    private int minDurationInMillis;
    private int maxDurationInMillis;
    private int minDelayInMillis;
    private int maxDelayInMillis;

    private ImageSource[] imageSources;
    private MyAnimator[] animators;

    private Random rnd;

    private int screenWidth;
    private int screenHeight;
    private boolean isFirstTime = true;

    public MovingBackground(Context context)
    {
        super(context);
        init(null);
        //Eran
    }

    public MovingBackground(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public MovingBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MovingBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(@Nullable AttributeSet set)
    {
        this.rnd = new Random();
        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.MovingBackground);
        this.image_source_id = typedArray.getResourceId(R.styleable.MovingBackground_image_source, R.drawable.sample_image);
        this.numberOfImages = typedArray.getInteger(R.styleable.MovingBackground_number_of_images, 5);
        this.minImageScale = typedArray.getInteger(R.styleable.MovingBackground_minImageScale, 20);
        this.maxImageScale = typedArray.getInteger(R.styleable.MovingBackground_maxImageScale, 50);
        this.minDurationInMillis = typedArray.getInteger(R.styleable.MovingBackground_minDurationInMillis, 10000);
        this.maxDurationInMillis = typedArray.getInteger(R.styleable.MovingBackground_maxDurationInMillis, 25000);;
        this.minDelayInMillis = typedArray.getInteger(R.styleable.MovingBackground_minDelayInMillis, 3000);
        this.maxDelayInMillis = typedArray.getInteger(R.styleable.MovingBackground_maxDelayInMillis, 7000);


        this.imageSources = new ImageSource[this.numberOfImages];
        this.animators = new MyAnimator[this.numberOfImages];
        for (int i = 0; i < this.imageSources.length; i++)
        {
            this.imageSources[i] = new ImageSource(i, getResources(), this.image_source_id);

        }

//        resetImage(imageSources[0]);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if(this.isFirstTime)
        {
            this.isFirstTime = false;
            this.screenWidth = getWidth();
            this.screenHeight = getHeight();
//            resetImage(this.imageSources[0]);
            for (int i = 0; i< this.imageSources.length; i++)
            {
                resetImage(this.imageSources[i]);
            }
        }
//        this.imageSource.setPosition(getWidth() / 2, getHeight() / 2);
//        this.imageSource.setImageScale(0.95f);
//
//        canvas.drawBitmap(this.imageSource.getImageBitmap(), null, this.imageSource.getImageFrame(), null);
        for (int i = 0; i < this.imageSources.length; i++)
        {
            canvas.drawBitmap(this.imageSources[i].getImageBitmap(), null, this.imageSources[i].getImageFrame(), this.imageSources[i].getPaint());
        }
    }

    private void resetImage(final ImageSource imageSource)
    {
        int imageTag = imageSource.getTag();
        int minScale = this.minImageScale;
        int maxScale = this.maxImageScale;
        imageSource.setImageScale((float) (((double)(minScale + this.rnd.nextInt(maxScale - minScale + 1)))/100)); //Randomize the scale of the image
//        Log.d("TAG", "" + imageSource.getImageScale()); //Show to the log the scale of the image
        float startXPosition = this.rnd.nextInt(this.screenWidth); // Randomizing the start X position
        float startYPosition = 0 - imageSource.getImageHeight()/2 - 200; // Randomizing the start Y position
        imageSource.setStartPosition(startXPosition, startYPosition); // Setting the start X, Y values
        float endXPosition = startXPosition; // Randomizing the end X position
        float endYPosition = startYPosition + (this.screenHeight + imageSource.getImageHeight()*2) + 300; // Setting the end Y position
        imageSource.setEndPosition(endXPosition, endYPosition); // Setting the ens X, Y values

        //Create an animator to the image that randomize the duration , setting listeners and start the animation
        this.animators[imageTag] = new MyAnimator(this.rnd
                , imageSource
                , imageTag
                , this.minDurationInMillis
                , this.maxDurationInMillis
                , this.minDelayInMillis
                , this.maxDelayInMillis
                , new ValueAnimator.AnimatorUpdateListener()
                    {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator)
                        {
                            imageSource.setAnimationProgress(valueAnimator.getAnimatedFraction());
                            invalidate();
                        }
                    }
                , new Animator.AnimatorListener()
                    {
                        @Override
                        public void onAnimationStart(Animator animator)
                        {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator)
                        {
                            resetImage(imageSource);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator)
                        {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator)
                        {

                        }
                    } );
    }

    public void setImage_source_id(int image_source_id)
    {
        this.image_source_id = image_source_id;
    }

    public void setNumberOfImages(int numberOfImages)
    {
        this.numberOfImages = numberOfImages;
    }

    public void setMinImageScale(int minImageScale)
    {
        this.minImageScale = minImageScale;
    }

    public void setMaxImageScale(int maxImageScale)
    {
        this.maxImageScale = maxImageScale;
    }

    public void setMinDurationInMillis(int minDurationInMillis)
    {
        this.minDurationInMillis = minDurationInMillis;
    }

    public void setMaxDurationInMillis(int maxDurationInMillis)
    {
        this.maxDurationInMillis = maxDurationInMillis;
    }

    public void setMinDelayInMillis(int minDelayInMillis)
    {
        this.minDelayInMillis = minDelayInMillis;
    }

    public void setMaxDelayInMillis(int maxDelayInMillis)
    {
        this.maxDelayInMillis = maxDelayInMillis;
    }

    public void setFirstTime(boolean firstTime)
    {
        isFirstTime = firstTime;
    }

    public int getImage_source_id()
    {
        return image_source_id;
    }

    public int getNumberOfImages()
    {
        return numberOfImages;
    }

    public int getMinImageScale()
    {
        return minImageScale;
    }

    public int getMaxImageScale()
    {
        return maxImageScale;
    }

    public int getMinDurationInMillis()
    {
        return minDurationInMillis;
    }

    public int getMaxDurationInMillis()
    {
        return maxDurationInMillis;
    }

    public int getMinDelayInMillis()
    {
        return minDelayInMillis;
    }

    public int getMaxDelayInMillis()
    {
        return maxDelayInMillis;
    }

    public boolean isFirstTime()
    {
        return isFirstTime;
    }
}
