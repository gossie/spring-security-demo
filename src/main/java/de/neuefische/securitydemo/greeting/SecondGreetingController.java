package de.neuefische.securitydemo.greeting;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admingreet")
public class SecondGreetingController {

    @GetMapping
    public String greet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Na " + auth.getName() + ", du bist der Knaller!";
    }

}
