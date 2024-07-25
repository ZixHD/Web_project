package rs.raf.web_projekat_teodor_jakovljevic_rn9622.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.user.UserRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {


    @Inject
    UserRepository userRepository;

    public String login(String username, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(username);
        if (user == null || !user.getPassword().equals(hashedPassword) || !user.isActive()) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        // JWT-om mozete bezbedno poslati informacije na FE
        // Tako sto sve sto zelite da posaljete zapakujete u claims mapu
        System.out.println("user je: " + user.getName() + " " + user.getSurname() + " " + user.getEmail());
        return JWT.create()
                .withClaim("role", user.getRole())
                .withClaim("username", user.getName())
                .withClaim("surname", user.getSurname())
                .withClaim("email", user.getEmail())
                .withClaim("user_id",user.getId())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm);
    }

    public String getRole(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String role = jwt.getClaim("role").asString();

        return role;
    }
    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();
        String role = jwt.getClaim("role").asString();


        User user = this.userRepository.findUser(username);
        System.out.println(user.getEmail());
        if (user == null){
            return false;
        }

        return true;
    }

    public List<User> allUsers(){ return this.userRepository.allUsers(); }
    public List<User> allUserPagination(Integer page, Integer pageSize){ return this.userRepository.allUserPagination(page, pageSize); }
    public User addUser(User user){ return this.userRepository.addUser(user); }
    public User editUser(Integer id, User user){ return this.userRepository.editUser(id, user); }
    public User findUser(String email){ return this.userRepository.findUser(email); }
    public User getUser(Integer id){ return this.userRepository.getUser(id); }
    public User activateUser(Integer id, boolean active){ return  this.userRepository.activateUser(id, active); }


}
