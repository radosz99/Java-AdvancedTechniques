package model.app.entities;

import java.util.Date;

public class Trip {
    private int id;
    private int authorId;
    private String name;
    private Date date;

    public int getId() {
        return id;
    }

    public Trip(int authorId, String name, Date date) {
        this.authorId = authorId;
        this.name = name;
        this.date = date;
    }

    public Trip() {
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", author_id=" + authorId +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    public Trip(int id, int authorId, String name, Date date) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int author_id) {
        this.authorId = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
