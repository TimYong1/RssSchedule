/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sx.trackdispatch.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * Create by xiao
 */
public class CommonBindingAdapter {

    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    public static void imageUrl(ImageView view, String url, Drawable placeHolder) {
        try {
            if(!TextUtils.isEmpty(url)){
                Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @BindingAdapter(value = {"touchListener"}, requireAll = false)
    public static void touchListener(View view,View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
    }

    @BindingAdapter(value = {"imageUrlLocal"}, requireAll = false)
    public static void imageUrlLocal(ImageView view, int res) {
        view.setImageResource(res);
    }

    @BindingAdapter(value = {"viewVisible"}, requireAll = false)
    public static void viewVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"textColor"}, requireAll = false)
    public static void setTextColor(TextView textView, int textColorRes) {
        textView.setTextColor(textView.getContext().getColor(textColorRes));
    }

    @BindingAdapter(value = {"selected"}, requireAll = false)
    public static void selected(View view, boolean select) {
        view.setSelected(select);
    }

    @BindingAdapter(value = {"localBg"}, requireAll = false)
    public static void localBg(View view, int bg) {
        view.setBackgroundResource(bg);
    }
}
