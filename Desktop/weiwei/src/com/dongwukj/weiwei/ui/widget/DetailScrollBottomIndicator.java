//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dongwukj.weiwei.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dongwukj.weiwei.R;

public class DetailScrollBottomIndicator extends RelativeLayout {
    int BASE_PADDING;
    Drawable down;
    boolean overThreshold = false;
    boolean released = false;
    TextView txt;
    Drawable up;

    public DetailScrollBottomIndicator(Context var1) {
        super(var1);
        this.init();
    }

    public DetailScrollBottomIndicator(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.init();
    }

    public DetailScrollBottomIndicator(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.init();
    }

    private void init() {
        this.BASE_PADDING = this.getResources().getDimensionPixelSize(R.dimen.detail_indicator_base_padding);
        LayoutInflater.from(this.getContext()).inflate(R.layout.detail_bottom_indicator, this, true);
        this.txt = (TextView)this.findViewById(R.id.txt);
        this.down = this.getContext().getResources().getDrawable(R.drawable.itemdetail_pull_down);
        this.up = this.getContext().getResources().getDrawable(R.drawable.itemdetail_pull_up);
        int var2 =24;
        this.down.setBounds(0, 0, var2, var2);
        this.up.setBounds(0, 0, var2, var2);
        this.onReleased();
    }

    public void onPulled(double var1) {
        boolean var3;
        if(var1 >= 0.20000000298023224D) {
            var3 = true;
        } else {
            var3 = false;
        }

        if(this.released || var3 ^ this.overThreshold) {
            if(var3) {
                this.overThreshold = true;
                this.txt.setCompoundDrawables((Drawable)null, (Drawable)null, this.down, (Drawable)null);
                this.txt.setText(R.string.bottom_indicator_release);
            } else {
                this.overThreshold = false;
                this.txt.setCompoundDrawables((Drawable)null, (Drawable)null, this.up, (Drawable)null);
                this.txt.setText(R.string.bottom_indicator_pull);
            }
        }

        this.released = false;
    }

    public void onReleased() {
        if(!this.released) {
            this.txt.setCompoundDrawables((Drawable)null, (Drawable)null, this.up, (Drawable)null);
            this.txt.setText(R.string.bottom_indicator_pull);
            this.released = true;
        }

    }
}
