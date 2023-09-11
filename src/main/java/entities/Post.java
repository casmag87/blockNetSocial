package entities;

import DTO.PostDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;



    @Column(name="body", length=250, nullable = false,unique = false )
    private String body;

    @Column(name="created", length=250, nullable = false,unique = false )
    private LocalDateTime creationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    User user;

    public Post() {
    }

    public Post(String body) {


        this.body = body;

    }

    public Post(Long postID, String body, LocalDateTime creationDate, User user) {


        this.body = body;
        this.creationDate = creationDate;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        // Set the creation date before persisting the entity
        this.creationDate = LocalDateTime.now();
    }

    public Post(PostDTO postDTO) {


        this.body= postDTO.getBody();
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(body, post.body) && Objects.equals(creationDate, post.creationDate) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, body, creationDate, user);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +

                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                ", user=" + user +
                '}';
    }
}
