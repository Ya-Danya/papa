package ru.masnaviev.university.example.some_cool_media_library;

import java.util.HashMap;

public class SimpleBlogClass implements BlogServiceInterface {

    @Override
    public HashMap<String, Article> popularVideos() {
        connectToServer("http://www.youtube.com");
        return getRandomVideos();
    }

    @Override
    public Article getVideo(String videoId) {
        connectToServer("http://www.youtube.com/" + videoId);
        return getSomeVideo(videoId);
    }

    private int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private void experienceNetworkLatency() {
        int randomLatency = random(5, 10);
        for (int i = 0; i < randomLatency; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void connectToServer(String server) {
        System.out.print("Connecting to " + server + "... ");
        experienceNetworkLatency();
        System.out.print("Connected!" + "\n");
    }

    private HashMap<String, Article> getRandomVideos() {
        System.out.print("Downloading populars... ");

        experienceNetworkLatency();
        HashMap<String, Article> hmap = new HashMap<String, Article>();
        hmap.put("catzzzzzzzzz", new Article("sadgahasgdas", "Catzzzz.avi"));
        hmap.put("mkafksangasj", new Article("mkafksangasj", "Dog play with ball.mp4"));
        hmap.put("dancesvideoo", new Article("asdfas3ffasd", "Dancing video.mpq"));
        hmap.put("dlsdk5jfslaf", new Article("dlsdk5jfslaf", "Barcelona vs RealM.mov"));
        hmap.put("3sdfgsd1j333", new Article("3sdfgsd1j333", "Programing lesson#1.avi"));

        System.out.print("Done!" + "\n");
        return hmap;
    }

    private Article getSomeVideo(String videoId) {
        System.out.print("Downloading video... ");

        experienceNetworkLatency();
        Article article = new Article(videoId, "Some video title");

        System.out.print("Done!" + "\n");
        return article;
    }

}
