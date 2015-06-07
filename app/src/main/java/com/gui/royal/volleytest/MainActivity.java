package com.gui.royal.volleytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {


    private ImageView iv;
    private NetworkImageView networkImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getJSONVolley();
        loadImageVolley();
        NetWorkImageViewVolley();
    }

    public void init () {
        iv = (ImageView) findViewById(R.id.iv);
        networkImageView = (NetworkImageView) findViewById(R.id.networkiv);
    }


    //获取一个JSON字符串
    public void getJSONVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);//生成RequestQueue
        String JSONDataUrl = "http://www.weather.com.cn/adat/cityinfo/101190404.html";//Url地址
        //生成一个JSONObjectRequest请求对象
        //int method,请求方式Volley支持8种
        // java.lang.String url, 请求地址
        // org.json.JSONObject jsonRequest,
        // com.android.volley.Response.Listener<org.json.JSONObject> listener, 请求成功的监听器
        // com.android.volley.Response.ErrorListener errorListener 请求出错的监听器
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,JSONDataUrl, null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("response", jsonObject.toString());
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Error", "Error");
            }
        }
        );
        requestQueue.add(jsonObjectRequest);//将request添加到队列中
    }

    //异步加载图片
    public void loadImageVolley() {
        //图片地址
        String imageUrl = "http://inuyasha.manmankan.com/UploadFiles_6178/201008/20100826162703374.jpg";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //生成一个缓存对象
        final LruCache<String, Bitmap> lruCache = new LruCache<>(20);
        /**
         * 生成一个图片缓存对象，用于存储和读取图片
         */
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }

            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }
        };
        //ImageLoader用于从处理走缓存请求
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        //为ImageLoad生成监听器
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.home, R.drawable.home);
        imageLoader.get(imageUrl, listener);
    }

    public void NetWorkImageViewVolley() {
        String imageUrl ="http://inuyasha.manmankan.com/UploadFiles_6178/201008/20100826162703374.jpg";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final  LruCache<String , Bitmap> lruCache = new LruCache<>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }

            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        networkImageView.setTag("url");
        networkImageView.setImageUrl(imageUrl,imageLoader);
    }

}
