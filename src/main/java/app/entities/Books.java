package app.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "release")
    private int release;

    public Books(int id, String name, String author, int release) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.release = release;
    }

    public Books() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getRelease() {
        return release;
    }
    public void setRelease(int release) {
        this.release = release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books books)) return false;
        return getId() == books.getId() && getRelease() == books.getRelease() && getName().equals(books.getName()) && getAuthor().equals(books.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAuthor(), getRelease());
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", release=" + release +
                '}';
    }
}
