package DTO;

import entities.Post;
import entities.User;

import java.util.List;
import java.util.Objects;

public class UserDTO {

    private String userName;
    private String password;

    private List<RoleDTO> roleList;
    private List<PostDTO> boatList;

    public UserDTO() {
    }

    public UserDTO(String userName, String password, List<RoleDTO> roleList, List<PostDTO> boatList) {
        this.userName = userName;
        this.password = password;
        this.roleList = roleList;
        this.boatList = boatList;
    }

    public UserDTO(User user, List<Post> postList){
        this.userName = user.getUserName();
        this.password = user.getUserPass();
    }

    public  UserDTO(User user){
        this.userName=user.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }

    public List<PostDTO> getBoatList() {
        return boatList;
    }

    public void setBoatList(List<PostDTO> boatList) {
        this.boatList = boatList;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roleList=" + roleList +
                ", boatList=" + boatList +
                '}';
    }


}
