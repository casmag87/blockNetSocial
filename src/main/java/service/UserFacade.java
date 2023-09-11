package service;

import DTO.PostDTO;
import DTO.UserDTO;
import entities.Post;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;


public class UserFacade {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("puTest");
    private static UserFacade instance;

    private UserFacade() {
    }


    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }



    public User createUser (User user) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }

    }

    public static PostDTO createUserPost(PostDTO postDTO, String username) {

        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class,username);
        Post post = new Post(postDTO);
        try {
            em.getTransaction().begin();
            post.setUser(user);
            em.persist(post);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PostDTO(post);
    }

    public List<PostDTO> getUserPost(String name) {
        EntityManager em = emf.createEntityManager();

        List<Post> postList;
        try {
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.user.userName = :name", Post.class)
                    .setParameter("name", name);

            postList = query.getResultList();

            if (postList.isEmpty()) {
                throw new WebApplicationException("No posts found for the user.", 204);
            }
        } finally {
            em.close();
        }

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList) {
            PostDTO postDTO = new PostDTO(post.getPostID(), post.getBody(), post.getCreationDate());
            postDTOList.add(postDTO);
        }

        return postDTOList;
    }

    public PostDTO deleteByID(Long ID) {
        EntityManager em = emf.createEntityManager();

        Post post = em.find(Post.class, ID);

        if (post == null) {
            em.close(); // Close EntityManager if the entity is not found
            throw new WebApplicationException("No post with that ID", 204);
        }

        try {
            em.getTransaction().begin();

            // Step 1: Disassociate the child entity from the parent entity
            User user = post.getUser();
            if (user != null) {
                user.getPostList().remove(post);
                post.setUser(null);
            }

            // Step 2: Remove the child entity
            em.remove(post);

            em.getTransaction().commit();
            return new PostDTO(post);
        } finally {
            em.close();
        }
    }

}