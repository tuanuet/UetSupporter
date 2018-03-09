package vnu.uet.tuan.uetsupporter.Model.Response;

/**
 * Created by FRAMGIA\vu.minh.tuan on 09/03/2018.
 */

public class DataSync {
    private String[] announcements;
    private String[] news;

    public DataSync(String[] announcements, String[] news) {
        this.announcements = announcements;
        this.news = news;
    }

    public DataSync() {
    }

    public String[] getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(String[] announcements) {
        this.announcements = announcements;
    }

    public String[] getNews() {
        return news;
    }

    public void setNews(String[] news) {
        this.news = news;
    }
}
