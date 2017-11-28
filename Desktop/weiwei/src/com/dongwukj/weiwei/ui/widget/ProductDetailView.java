//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.dongwukj.weiwei.R;
//import com.achievo.vipshop.view.DialogListener;
//import com.achievo.vipshop.view.DialogViewer;
//import com.achievo.vipshop.view.interfaces.CartAnimation;

public class ProductDetailView extends RelativeLayout implements OnTouchListener, AnimationListener {
   // CartAnimation cartAnimation;
    private Context context;
    private String couponGouTips;
    //private DialogListener dialogListener;
    protected AnimationSet mCloseAnimationSet;
    private LinearLayout mDetailLayout;
    boolean mIsAnimating = false;
    protected AnimationSet mOpenAnimationSet;

    public ProductDetailView(Context var1) {
        super(var1);
        this.context = var1;
    }

    public ProductDetailView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.context = var1;
    }

    private void showConponDialog() {
        this.postDelayed(new Runnable() {
            public void run() {
//                (new DialogViewer(ProductDetailView.this.context, ProductDetailView.this.couponGouTips, "我知道了", "去购物车", ProductDetailView.this.dialogListener)).show();
//                ProductDetailView.this.couponGouTips = null;
            }
        }, 0L);
    }

    public void dismissDialog() {
        if(this.mIsAnimating) {
            ;
        }

    }

    protected void dispatchDraw(Canvas var1) {
        super.dispatchDraw(var1);
//        if(this.cartAnimation != null && this.cartAnimation.isDrawingAnimation(var1)) {
//            this.onFinish();
//        }

    }

    public Animation getCloseAnimation() {
        if(this.mCloseAnimationSet == null) {
            this.mCloseAnimationSet = new AnimationSet(true);
            AlphaAnimation var1 = new AlphaAnimation(1.0F, 0.0F);
            this.mCloseAnimationSet.addAnimation(var1);
            this.mCloseAnimationSet.setDuration(500L);
            this.mCloseAnimationSet.setAnimationListener(this);
        }

        return this.mCloseAnimationSet;
    }

    public Animation getOpenAnimation() {
        if(this.mOpenAnimationSet == null) {
            this.mOpenAnimationSet = new AnimationSet(true);
            AlphaAnimation var1 = new AlphaAnimation(0.0F, 1.0F);
            new ScaleAnimation(1.0F, 1.0F, 0.3F, 1.0F, 1, 0.0F, 1, 1.0F);
            this.mOpenAnimationSet.addAnimation(var1);
            this.mOpenAnimationSet.setFillAfter(true);
            this.mOpenAnimationSet.setDuration(0L);
            this.mOpenAnimationSet.setAnimationListener(this);
        }

        return this.mOpenAnimationSet;
    }

    public void onAnimationEnd(Animation var1) {
        this.mIsAnimating = false;
    }

    public void onAnimationRepeat(Animation var1) {
    }

    public void onAnimationStart(Animation var1) {
        this.mIsAnimating = true;
    }

    protected void onFinish() {
        if(this.couponGouTips != null) {
            this.showConponDialog();
        } else {
            this.showDialog();
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDetailLayout = (LinearLayout)this.findViewById(R.id.detail_framework);
    }

    public boolean onTouch(View var1, MotionEvent var2) {
        if(var1 == this.mDetailLayout && this.mDetailLayout.getVisibility() == VISIBLE) {
            this.dismissDialog();
            return true;
        } else {
            return false;
        }
    }

    public void recycle() {
//        if(this.cartAnimation != null) {
//            this.cartAnimation.close();
//            this.cartAnimation = null;
//        }

    }

//    public void setCouponGou(String var1, DialogListener var2) {
//        this.couponGouTips = var1;
//        this.dialogListener = var2;
//    }

    public void showDialog() {
        if(this.mIsAnimating) {
            ;
        }

    }

//    public void startCartAnimation(CartAnimation var1) {
//        this.cartAnimation = var1;
//        var1.start();
//    }

    public void stopAllShow() {
//        if(this.mIsAnimating) {
//            if(this.cartAnimation != null) {
//                this.cartAnimation.stopAllShow();
//            }
//
//            this.mIsAnimating = false;
//        }

    }
}
