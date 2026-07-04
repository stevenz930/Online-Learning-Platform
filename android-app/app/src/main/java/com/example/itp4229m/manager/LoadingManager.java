package com.example.itp4229m.manager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class LoadingManager {
    private ViewGroup rootLayout;
    private FrameLayout overlay;
    private ProgressBar progressBar;

    public LoadingManager(ViewGroup rootLayout, Context context) {
        this.rootLayout = rootLayout;

        overlay = new FrameLayout(context);
        overlay.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setBackgroundColor(Color.parseColor("#7FFFFFFF"));

        progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        FrameLayout.LayoutParams pbParams = new FrameLayout.LayoutParams(100, 100);
        pbParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(pbParams);

        overlay.addView(progressBar);
        overlay.setVisibility(View.GONE);
    }

    public void show() {
        if (overlay.getParent() == null) {
            rootLayout.addView(overlay);
        }
        overlay.setVisibility(View.VISIBLE);
    }

    public void hide() {
        overlay.setVisibility(View.GONE);
    }

    public void remove() {
        rootLayout.removeView(overlay);
    }
}

