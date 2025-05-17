package ru.masnaviev.university.example.proxy;

import ru.masnaviev.university.example.some_cool_media_library.Article;
import ru.masnaviev.university.example.some_cool_media_library.SimpleBlogClass;
import ru.masnaviev.university.example.some_cool_media_library.BlogServiceInterface;

import java.util.HashMap;

public class BolgCacheProxy implements BlogServiceInterface {
    private BlogServiceInterface youtubeService;
    private HashMap<String, Article> cachePopular = new HashMap<String, Article>();
    private HashMap<String, Article> cacheAll = new HashMap<String, Article>();

    public BolgCacheProxy() {
        this.youtubeService = new SimpleBlogClass();
    }

    @Override
    public HashMap<String, Article> popularVideos() {
        if (cachePopular.isEmpty()) {
            cachePopular = youtubeService.popularVideos();
        } else {
            System.out.println("Retrieved list from cache.");
        }
        return cachePopular;
    }

    @Override
    public Article getVideo(String videoId) {
        Article article = cacheAll.get(videoId);
        if (article == null) {
            article = youtubeService.getVideo(videoId);
            cacheAll.put(videoId, article);
        } else {
            System.out.println("Retrieved video '" + videoId + "' from cache.");
        }
        return article;
    }

    public void reset() {
        cachePopular.clear();
        cacheAll.clear();
    }
}
