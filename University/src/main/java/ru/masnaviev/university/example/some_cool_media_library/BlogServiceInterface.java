package ru.masnaviev.university.example.some_cool_media_library;

import java.util.HashMap;

public interface BlogServiceInterface {
    HashMap<String, Article> popularVideos();

    Article getVideo(String videoId);
}
