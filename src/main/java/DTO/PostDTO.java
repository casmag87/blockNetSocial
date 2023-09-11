package DTO;

import entities.Post;
import entities.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostDTO {

    private Long postID;

    private String topic;

    private String body;

    private LocalDateTime creationDate;

    User user;

    public PostDTO() {
    }

    public PostDTO(String topic, String body, LocalDateTime creationDate) {
        this.topic = topic;
        this.body = body;
        this.creationDate = creationDate;
    }

    public PostDTO(String topic, String body, LocalDateTime creationDate, User user) {

        this.topic = topic;
        this.body = body;
        this.creationDate = creationDate;
        this.user = user;
    }

    public PostDTO(Long postID, String body, LocalDateTime creationDate) {
        this.postID = postID;

        this.body = body;
        this.creationDate = creationDate;
    }

    public PostDTO(Post post){
        if (post.getPostID() != null){
            this.postID = post.getPostID();
        }

        this.body = post.getBody();
        this.creationDate = post.getCreationDate();



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

    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.time.LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "postID=" + postID +

                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                ", user=" + user +
                '}';
    }


}
