package com.example.administrator.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView wv;
    private Button btn_tuijian, btn_shouye,btn_shujia,btn_fenlei;
    private NavigationView navigationView;
    private String url;

    private final int STOP_SPLASH = 0;
    private final int SPLASH_TIME = 3000;

    private LinearLayout splashLt;
    private String webViewUrl = "http://10.0.2.2:8080";
    //http://172.17.64.47:8080
    //http://10.0.2.2:8080
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_drawer,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(wv.canGoBack())
        {
            wv.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    private Handler splashHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_SPLASH:
                    splashLt.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.main1);

        splashLt = (LinearLayout) findViewById(R.id.splash_lt);

        Message msg = new Message();
        msg.what = STOP_SPLASH;

        // 注：这里必须用延迟发送消息的方法，否则ImageView不会显示出来
        splashHandler.sendMessageDelayed(msg, SPLASH_TIME);

        wv = (WebView)findViewById(R.id.sb_android);

        wv.getSettings().setDefaultTextEncodingName("utf-8");

        btn_tuijian = (Button)findViewById(R.id.btn_tuijian);
        btn_fenlei = (Button)findViewById(R.id.btn_fenlei);
        btn_shouye = (Button)findViewById(R.id.btn_shouye);
        btn_shujia = (Button)findViewById(R.id.btn_shujia);


        navigationView = (NavigationView)findViewById(R.id.navigationView);

        // navigationView menu点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });

        //展示开始界面
        wv.setWebViewClient(new WebViewClient());
        WebSettings webSettings2 = wv.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        wv.loadUrl(webViewUrl+"/E_read/user_index.action");

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //webview一系列相关设置：
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //这个是国外网站Stack Overflow推荐提升加载速度的方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        //设置自适应屏幕，两者合用
        wv.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        wv.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        wv.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        wv.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        wv.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件

        btn_tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.setWebViewClient(new WebViewClient());
                WebSettings webSettings2 = wv.getSettings();
                webSettings2.setJavaScriptEnabled(true);
                //wv.loadUrl("file:///android_asset/top.html");
                wv.loadUrl(webViewUrl+"/E_read/rank.jsp");
            }
        });
        btn_shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebSettings webSettings1 = wv.getSettings();
                webSettings1.setJavaScriptEnabled(true);
                //wv.loadUrl("file:///android_asset/showye.html");
                wv.loadUrl(webViewUrl+"/E_read/user_index.action");
            }
        });


        btn_fenlei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.setWebViewClient(new WebViewClient());
                WebSettings webSettings2 = wv.getSettings();
                webSettings2.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/type.jsp");
            }
        });

        btn_shujia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.setWebViewClient(new WebViewClient());
                WebSettings webSettings2 = wv.getSettings();
                webSettings2.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/user_showSubscribeList");
            }
        });
    }

    public void  selectDrawerItem(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.navigation_item1:
                //历史纪录
                WebSettings webSettings1 = wv.getSettings();
                webSettings1.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/user_history");
                //Toast.makeText(MainActivity.this,"1",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_item2:
                //个人信息
                WebSettings webSettings2 = wv.getSettings();
                webSettings2.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/user_showInfo");
                //Toast.makeText(MainActivity.this,"2",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_item3:
                //订阅记录
                WebSettings webSettings3 = wv.getSettings();
                webSettings3.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/user_showSubscribeList");
                //Toast.makeText(MainActivity.this,"3",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_item4:
                //退出登录
                WebSettings webSettings4 = wv.getSettings();
                webSettings4.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/user_logout");
                //Toast.makeText(MainActivity.this,"4",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_sub_item1:
                //登录
                WebSettings webSettings6 = wv.getSettings();
                webSettings6.setJavaScriptEnabled(true);
                wv.loadUrl(webViewUrl+"/E_read/login.jsp");
                //Toast.makeText(MainActivity.this,"5",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_sub_item2:
                //帮助
                Toast.makeText(MainActivity.this,"请在登陆后再查看我的书架",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_sub_item3:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                break;
            default:
                break;
        }
        menuItem.setChecked(true);
    }
}
