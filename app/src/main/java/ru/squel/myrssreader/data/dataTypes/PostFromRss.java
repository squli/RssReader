package ru.squel.myrssreader.data.dataTypes;

/**
 * Created by Саша on 03.07.2017.
 * /// для хранения каждой записи
 */
public class PostFromRss {
    private static final int countOfcharsInShortDescription = 350;

    /// Для каждой записи
    private String linkPost = "";
    private String titlePost = "";
    private String descriptionPost = "";

    public PostFromRss(String link, String title, String desc)
    {
        this.linkPost = link;
        this.titlePost = title;
        this.descriptionPost = desc;
    }

    public String toString()
    {
        return ("" + linkPost + " - " + titlePost + "\r\n" + descriptionPost);
    }

    public String getLink() {
        return linkPost;
    }
    public String getTitle() {
        return titlePost;
    }
    public String getDescription() {
        return descriptionPost;
    }
    public String getShortDescription() {
        if (descriptionPost.length() < countOfcharsInShortDescription)
            return  descriptionPost;
        else
            return  descriptionPost.substring(0, countOfcharsInShortDescription);
    }
}