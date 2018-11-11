package com.example.kyaw.firebasetutorial.ImageTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kyaw.firebasetutorial.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;


public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    PhotoView img;
    String url;


    static SlideshowDialogFragment newInstance(String url) {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        f.setArguments(bundle);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_preview, container, false);
        img=(PhotoView) v.findViewById(R.id.image_preview);
        Glide.with(this).load(url).into(img);
        return v;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url=getArguments().getString("url");

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }



    }

