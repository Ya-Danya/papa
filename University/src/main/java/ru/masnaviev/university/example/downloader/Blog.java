package ru.masnaviev.university.example.downloader;



import ru.masnaviev.university.example.some_cool_media_library.Article;
import ru.masnaviev.university.example.some_cool_media_library.BlogServiceInterface;

import java.util.HashMap;

public class Blog {
    private BlogServiceInterface api;

    public Blog(BlogServiceInterface api) {
        this.api = api;
    }

    public void renderVideoPage(String videoId) {
        Article article = api.getVideo(videoId);
        System.out.println("\n-------------------------------");
        System.out.println("Video page (imagine fancy HTML)");
        System.out.println("ID: " + article.id);
        System.out.println("Title: " + article.title);
        System.out.println("Video: " + article.data);
        System.out.println("-------------------------------\n");
    }

    public void renderPopularVideos() {
        HashMap<String, Article> list = api.popularVideos();
        System.out.println("\n-------------------------------");
        System.out.println("Most popular videos on YouTube (imagine fancy HTML)");
        for (Article article : list.values()) {
            System.out.println("ID: " + article.id + " / Title: " + article.title);
        }
        System.out.println("-------------------------------\n");
    }
}
