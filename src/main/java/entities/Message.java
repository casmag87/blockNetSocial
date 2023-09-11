package entities;

import jakarta.persistence.*;

@Entity(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content",nullable = false,length = 255,unique = false)
    private String content;

    @Column(name = "isRead",nullable = false,unique = false)
    private boolean isRead;

    @OneToOne
    private User user;

    public Message() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
