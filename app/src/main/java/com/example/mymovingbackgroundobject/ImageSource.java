package com.example.mymovingbackgroundobject;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageSource
{
    private int tag;

    private Bitmap imageBitmap;
//    private boolean lockImageRatio;
//    private float imageRatio; // Width / Height
    private RectF imageFrame;
    private Paint paint;



    private float imageXPosition;
    private float imageYPosition;
    private float imageScale;

    private float imageEndXPosition;
    private float imageEndYPosition;

    private float imageStartXPosition;
    private float imageStartYPosition;

    public ImageSource(int tag, Resources res, int imageId, PorterDuff.Mode blendMode)
    {
        this.tag = tag;
        this.imageBitmap = BitmapFactory.decodeResource(res, imageId);
        this.imageFrame = new RectF();
        this.paint = new Paint();
        if(blendMode != null && blendMode != PorterDuff.Mode.CLEAR)
            this.paint.setXfermode(new PorterDuffXfermode(blendMode));
        this.paint.setAlpha(100);
        updateImageFrame();
//        if(lockImageRatio)
//            this.imageRatio = this.imageBitmap.getWidth() / imageBitmap.getHeight();
        this.imageXPosition = 0f;
        this.imageYPosition = 0f;
        this.imageScale = 1f;
    }

    public void setPosition(float newXPosition, float newYPosition)
    {
        this.imageXPosition = newXPosition;
        this.imageYPosition = newYPosition;
        updateImageFrame();
    }

    public void setEndPosition(float endXPosition, float endYPosition)
    {
        this.imageEndXPosition = endXPosition;
        this.imageEndYPosition = endYPosition;
    }

    public void setStartPosition(float startXPosition, float startYPosition)
    {
        setPosition(startXPosition, startYPosition);
        this.imageStartXPosition = startXPosition;
        this.imageStartYPosition = startYPosition;
    }

    public void setAnimationProgress(float progress)
    {
        setPosition(this.imageStartXPosition
                , this.imageStartYPosition + this.imageEndYPosition * progress);
    }

    public void setImageScale(float newImageScale)
    {
        this.imageScale = newImageScale;
        updateImageFrame();
    }

    private void updateImageFrame()
    {
        this.imageFrame.left = imageXPosition - (this.imageBitmap.getWidth() * this.imageScale) / 2;
        this.imageFrame.top = imageYPosition - (this.imageBitmap.getHeight() * this.imageScale) / 2;
        this.imageFrame.right = imageXPosition + (this.imageBitmap.getWidth() * this.imageScale) / 2;
        this.imageFrame.bottom = imageYPosition + (this.imageBitmap.getHeight() * this.imageScale) / 2;
    }

    public float getImageWidth()
    {
        return this.imageBitmap.getWidth() * this.imageScale;
    }

    public float getImageHeight()
    {
        return this.imageBitmap.getHeight() * this.imageScale;
    }

    public Bitmap getImageBitmap()
    {
        return this.imageBitmap;
    }

    public RectF getImageFrame()
    {
        return this.imageFrame;
    }

    public Paint getPaint()
    {
        return this.paint;
    }

    public float getImageScale()
    {
        return this.imageScale;
    }

    public int getTag()
    {
        return this.tag;
    }
}
