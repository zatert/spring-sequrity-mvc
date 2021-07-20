package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.dao.UserDaoImpl;
import web.model.User;
import web.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @ResponseBody
    public String getHello(Model model){
        return "Hello, i'm controller";
    }

    @GetMapping(value = "/users")
    public String allUsers(Model model){
        model.addAttribute("list", userService.findAll());
        return "users";
    }
    @GetMapping("/newUserForm")
    public String newUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        userService.add(user);
        return "redirect:/users";
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user){
        userService.edit(user);
        //userRepo.save(user);
        return "redirect:/users";
    }
    @GetMapping("/formForUpdate")
    public String formForUpdate(@RequestParam Long id, Model model){
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        return "update_user";
    }
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id){
//        userRepo.deleteById(id);
        userService.delete(id);
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
    public String getUserPage(Model model, Principal principal) {
        User u = userService.findByName(principal.getName());
        model.addAttribute("user", u);
        return "user";
    }
    @GetMapping(value = "/hello")
    public String getHelloPage() {
        return "hello";
    }

}
