package com.example.senti4;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public TextView TextView_content;
        public SimpleDraweeView ImageView_title;

        //MyViewHolder의 생성자
        public MyViewHolder(View v) {
            super(v);
            // 뷰홀더 : 메모리 관리와 성능 향상을 위해 안드로이드가 제공하는 클래스
            // 그래서 자식클래스의 변수를 뷰홀더에 정의 한다.
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_content = v.findViewById(R.id.TextView_content);
            ImageView_title = v.findViewById(R.id.ImageView_title);
        }
    }

    // Provide a 알맞은 생성자 (depends on the kind of dataset)
    // 액티비티가 아닌 곳에서 Context를 쓰기 위해서는 액티비티에서 Context값을 가져와야한다.
    public MyAdapter(List<NewsData> myDataset, Context context) {
        //{"1","2"}  -- 예제용 데이터
        mDataset = myDataset;
        Fresco.initialize(context);

    }

    // Create new views (invoked by the layout manager)
    // row_news 는 한개의 뉴스를 의미한다. 따라서 v는 row_news의 가장 상위 레이아웃 Linear를 가르킨다.
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // 화면에 뿌려줄 데이터를 세팅하는 곳
        NewsData news = mDataset.get(position);
        holder.TextView_title.setText(news.getTitle());
        if (news.getDescription().equals("null")) {
            holder.TextView_content.setText("본문없음");
        } else {
            holder.TextView_content.setText(news.getDescription());
        }
        // 우리가 아는건 이미지의 주소이지, 이미지가 아니다. 따라서 이미지 주소로부터
        // 이미지를 받아오는데에 fresco를 사용한다.
        Uri uri = Uri.parse(news.getUrlToImage());
        holder.ImageView_title.setImageURI(uri);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}