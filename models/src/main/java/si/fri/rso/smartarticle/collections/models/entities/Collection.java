package si.fri.rso.smartarticle.collections.models.entities;

import si.fri.rso.smartarticle.collections.models.dtos.Article;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity(name = "collection")
@NamedQueries(value =
        {
                @NamedQuery(name = "Collection.getAll", query = "SELECT c FROM collection c")
        })
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private Instant creation;

    private String accountId;

    @Transient
    private List<Article> articles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Instant getCreation() {
        return creation;
    }

    public void setCreation(Instant creation) {
        this.creation = creation;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}