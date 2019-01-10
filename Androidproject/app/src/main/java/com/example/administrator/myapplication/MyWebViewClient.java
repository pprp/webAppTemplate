package com.example.administrator.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static android.R.attr.description;

/**
 * Created by Administrator on 2018/12/26.
 */

public class MyWebViewClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("com"))
        {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            switch(errorCode)
            {
                case 404:
                    view.loadUrl("file:///android_asset/error_handle.html");
                    break;
            }
        }

}
