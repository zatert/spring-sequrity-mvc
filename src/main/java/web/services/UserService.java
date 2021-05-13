package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDaoImpl;
import web.model.Role;
import web.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserDaoImpl userDao;

    public User findByName (String name){
      //  User ud = userDao.findByName(name);
        return userDao.findByName(name);
    }
    public List<User> findAll () {return userDao.findAll();}
    @Override
   // @Transactional   // это ломает все
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        User user = findByName(str);
        if(user == null){
            throw new UsernameNotFoundException(String.format("User '&s' not found", str));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
            return roles.stream().map(r-> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
        }

}
