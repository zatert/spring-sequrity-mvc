package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.dao.UserDaoImpl;
import web.model.User;

import java.util.List;

@Controller
//@RequestMapping("/users")
public class MainController {
    @Autowired
    private UserDaoImpl userDao;

    //@RequestMapping(value = "/users3", method = RequestMethod.GET)
    @ResponseBody
    public String getHello(Model model){
        return "Hello, i'm controller";
    }

    @GetMapping(value = "/users")
    public String allUsers(Model model){
        //List<User> list = userDao.all();
        model.addAttribute("list", userDao.all());
        return "users";
    }

    @PostMapping(value="/users")
    public String add(@RequestParam String name, @RequestParam String lastname, @RequestParam Integer age,
                        Model model){
    User user = new User(name, lastname, age);
    userDao.addUser(user);
    model.addAttribute("list", userDao.all());
        return "users";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteUser(@RequestParam Integer id, Model model){
        userDao.delete(id);
        System.out.println("User_Id : ");
        model.addAttribute("list", userDao.all());
        return "redirect:/users"; //"users";
    }
    @PostMapping(value="/edit")
    public String editUser(@RequestParam(required=false) String name, @RequestParam Integer id,
                           @RequestParam(required=false) String lastname, @RequestParam(required=false) Integer age, Model model){
        User user = userDao.getOne(id);
        if(name != null) {
            user.setName(name);
        }
        if(lastname != null) {
            user.setLastName(lastname);
        }
        if(age != null) {
            user.setAge(age);
        }
        userDao.edit(user);//(user.getName(), lastname, age);
        model.addAttribute("list", userDao.all());
        return "redirect:/users";
    }
}
