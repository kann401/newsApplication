package com.example.senti4;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
// 뉴스api키 : 642d73d3db9f4297ac0b2e06ff604cea

    /*기사 데이터 받아오기 단계 인덱스
       1단계. 화면이 로딩 되면 > 뉴스 정보를 받아온다.
       2단계. 정보 > 어댑터에 넘겨준다.
       3단계. 어댑터 > 화면에 셋팅한다. */

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"1", "2"};   //-- 10강 예제용

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_news 의 xml에는 리사이클러뷰 하나만 존재한다.
        setContentView(R.layout.activity_news);

        //1. 리사이클러뷰 연결
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // 리니어레이아웃 연결 후 장착
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 뉴스정보를 담고 뿌려줄 queue 객체를 요청파일로 만들어준다.
        // volley는 네트워크 통신을 하기 위해서 queue에 담아서 하나씩 빼준다. queue는 FIFO구조
        queue = Volley.newRequestQueue(this);
        getNews();  //getNews 메소드를 만들고 queue에 연결시켜주는 여기까지가 1단계

    }

        // 뉴스를 받아오는 메소드(Json형태)
    public void getNews() {

        // 사용할 뉴스api 사이트의 기사 url을 넣어준다.
        String url ="https://newsapi.org/v2/top-headlines?country=kr&apiKey=642d73d3db9f4297ac0b2e06ff604cea";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //response 문자열에 불과한 덩어리를 JSON으로 변환해주고,
                        // NewsData Class를 통해 필요한 것만 분류
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arrayArticles = jsonObject.getJSONArray("articles");

                            List<NewsData> news = new ArrayList<>();

                            for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                                JSONObject obj = arrayArticles.getJSONObject(i); //이게 하나의 기사 모든 정보

                                Log.d("NEWS", obj.toString());

                                NewsData newsData = new NewsData();
                                // getString을 쓰는건 가져오려는게 Key값이 아니라 Value이기 때문에 Value기준으로 데이터타입을 결정.
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setDescription(obj.getString("description"));
                                news.add(newsData);
                            }

                            // Adapter를 통해 리사이클러뷰로 넘겨주자
                            mAdapter = new MyAdapter(news, NewsActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
