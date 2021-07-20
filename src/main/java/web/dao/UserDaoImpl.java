package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.*;
import java.util.List;

@Component
public class UserDaoImpl {
    @PersistenceContext//(unitName = "emf")
    private EntityManager entityManager;
    @Transactional
    public void addUser(User user){
        entityManager.persist(user);
    }
    @Transactional(readOnly = true)
    public List<User> findAll(){
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional
    public void delete(Long id){
       Query q = entityManager.createQuery("delete from User where id=:id");//("delete u from User u where u.getId= :id", User.class);
            q.setParameter("id",id);
            int deleted = q.executeUpdate();
             System.out.println("Deleted: " + deleted + " user(s)");
         }
    @Transactional
    public void edit(User user) {//(Integer id, String name, String lastname, Integer age){
        entityManager.createQuery("UPDATE User u SET u.username =  :username,  u.password = :password, u.email = :email WHERE u.id = :id")
            .setParameter("username", user.getUsername())
            .setParameter("password", user.getPassword())
            .setParameter("email", user.getEmail())
            .setParameter("id", user.getId())
            .executeUpdate();
    }
    @Transactional
    public User getOne(Long id){
        TypedQuery<User> q = entityManager.createQuery(
        "select u from User u where u.id = :id", User.class
        );
        q.setParameter("id", id);
        return q.getSingleResult();
    }
    @Transactional
    public User findByName(String username){
        TypedQuery<User> q = entityManager.createQuery(
                "select u from User u where u.username = :username", User.class
        );
        q.setParameter("username", username);
        User user = q.getSingleResult();
        return q.getSingleResult();
    }


}