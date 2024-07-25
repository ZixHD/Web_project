package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.user;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;

import java.util.List;

public interface UserRepository {

    //Za login
    public List<User> allUsers();
    public List<User> allUserPagination(Integer page, Integer pageSize);
    public User findUser(String username);
    public User addUser(User user);
    public User editUser(Integer id, User user);
    public User getUser(Integer id);
    public User activateUser(Integer id, boolean active);

}
