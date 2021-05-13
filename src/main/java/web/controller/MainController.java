package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.dao.UserDaoImpl;
import web.model.User;
import web.services.UserService;

import java.util.List;

@Controller
//@RequestMapping("/users")
public class MainController {
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private UserService userService;
    //@RequestMapping(value = "/users3", method = RequestMethod.GET)
    @ResponseBody
    public String getHello(Model model){
        return "Hello, i'm controller";
    }

    @GetMapping(value = "/users")
    public String allUsers(Model model){
        //List<User> list = userDao.all();
        model.addAttribute("list", userService.findAll());
        return "users";
    }

//    @RequestMapping(value="/users")
//    public String add(@RequestParam String name, @RequestParam String lastname, @RequestParam String email,
//                      @RequestParam String password, Model model){
////    User user = new User(email, name, lastname, password);
////    userDao.addUser(user);
//      //  List<User> list = userDao.findAll();
//    model.addAttribute("list", userDao.findAll());
//        return "users";
//    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteUser(@RequestParam Integer id, Model model){
        userDao.delete(id);
        System.out.println("User_Id : ");
        model.addAttribute("list", userDao.findAll());
        return "redirect:/users"; //"users";
    }
    @PostMapping(value="/edit")
    public String editUser(@RequestParam(required=false) String name, @RequestParam Integer id,
                           @RequestParam(required=false) String lastname, @RequestParam(required=false)String email, Model model){
        User user = userDao.getOne(id);
        if(name != null) {
            user.setName(name);
        }
        if(lastname != null) {
            user.setLastName(lastname);
        }
        if(email != null) {
            user.setEmail(email);
        }
        userDao.edit(user);//(user.getName(), lastname, age);
        model.addAttribute("list", userDao.findAll());
        return "redirect:/users";
    }

    //-------------============Security=================-----------------
    @GetMapping(value = "/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/user")
    public String getUserPage() {
        return "user";
    }

    @GetMapping(value = "/hello")
    public String getHelloPage() {
        return "hello";
    }

}
