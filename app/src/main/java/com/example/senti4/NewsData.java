package com.example.senti4;

import java.io.Serializable;

// 기사 api를 보면 글쓴이 등등 필요하지 않은 데이터를 모두 가져올 필요가 없으므로
// 이 Serializable을 구현한 클래스를 통해 필요한 제목, 본문, 이미지 만 받아오도록 한다.
public class NewsData implements Serializable {
    private String title;
    private String urlToImage;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
