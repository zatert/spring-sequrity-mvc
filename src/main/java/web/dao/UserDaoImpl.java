package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
//import web.repository.UserRepo;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Component
//@Transactional(readOnly = true)
//@Service
public class UserDaoImpl {//implements UserDao{
//    @Autowired
//    UserRepo userRepo;
    //private EntityManagerFactory entityManagerFactory;
    @PersistenceContext//(unitName = "emf")
    private EntityManager entityManager;  // прилетит сюда из энтитиМенеджрФэктори
//    EntityManagerFactory emf =
//            Persistence.createEntityManagerFactory("web.model");
//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }
    @Transactional
    public void addUser(User user){
        entityManager.persist(user);
//        EntityManager em = getEntityManager();
//        em.getTransaction().begin();
//        em.persist(user);
//        em.getTransaction().commit();

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
//        return userRepo.findByUsername(username);
    }


}
// на которую выводятся все юзеры с возможностью добавлять, удалять и изменять юзера.