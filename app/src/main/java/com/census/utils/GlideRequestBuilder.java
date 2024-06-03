package com.census.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

public class GlideRequestBuilder<T> {


    private Context context;
    private String url;
    private RequestOptions requestOptions;
    private float thumbnail;
    private RequestListener<T> requestListener;

    public static Object updateIfGoogleUrl(Context context, String url) {
        Object object = url;

        return object;
    }

    public GlideRequestBuilder<T> setRequestListener(RequestListener<T> requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public GlideRequestBuilder<T> setContext(Context context) {
        this.context = context;
        return this;
    }

    public GlideRequestBuilder<T> setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        return this;
    }

    public GlideRequestBuilder<T> setThumbnail(float thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    private Object getGlideUrl(Context context) {
        Object glideUrl = updateIfGoogleUrl(context, url);
        if (glideUrl instanceof GlideUrl)
            thumbnail = .1f;
        return glideUrl;
    }

    public GlideRequestBuilder<T> setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder<T> build() {
        RequestBuilder requestBuilder = Glide.with(context).load(getGlideUrl(context));
        return constructGlideBuilder(requestBuilder);
    }

    public RequestBuilder<T> buildAsBitmap() {
        RequestBuilder requestBuilder = Glide.with(context).asBitmap().load(getGlideUrl(context));
        return constructGlideBuilder(requestBuilder);
    }

    private RequestBuilder<T> constructGlideBuilder(RequestBuilder requestBuilder) {
        if (thumbnail != 0f)
            requestBuilder.thumbnail(thumbnail);
        if (requestOptions != null) {
            requestBuilder.apply(requestOptions);
        }
        if (requestListener != null) {
            requestBuilder.listener(requestListener);
        }

        return requestBuilder;
    }

}
