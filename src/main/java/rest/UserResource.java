package rest;

import DTO.PostDTO;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import entities.Post;
import entities.Role;
import entities.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import service.UserFacade;

import java.util.List;
@Path("info")
public class UserResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("puTest");
    private final UserFacade userFacade = UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {
        try (EntityManager em = EMF.createEntityManager()) {
            TypedQuery<User> query = em.createQuery("select u from User u", User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"" + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @POST
    @Path("signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String jsonString) {
        String username;
        String password;

        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        username = json.get("username").getAsString();
        password = json.get("password").getAsString();

        User user1 = new User(username, password);
        Role role = new Role("user");
        user1.addRole(role);
        User user = userFacade.createUser(user1);

        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", username);

        return Response.ok(GSON.toJson(responseJson)).build();
    }


    @POST
    @Path("createpost")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUserPost(String jsonString){
        String thisuser = securityContext.getUserPrincipal().getName();

        PostDTO postDTO = GSON.fromJson(jsonString, PostDTO.class);

        return Response.ok(GSON.toJson(UserFacade.createUserPost(postDTO,thisuser)),"application/json").build();
    }

    @GET
    @Path("posts")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPosts(){
        String loggedInUser = securityContext.getUserPrincipal().getName();

        return Response.ok(GSON.toJson(userFacade.getUserPost(loggedInUser)), "application/json").build();
    }

    @DELETE
    @Path("delete/{id}")
    @RolesAllowed("user")
    public Response deletePost(@PathParam("id") Long id){

        PostDTO postDTO = userFacade.deleteByID(id);

        return Response.ok(GSON.toJson(postDTO) ,"application/json").build();
    }

}