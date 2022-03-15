package de.neuefische.securitydemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api/greet")
@RequiredArgsConstructor
public class GreetingController {

    @GetMapping
    public String greet(Principal principal) {
        return "Moin " + principal.getName();
    }

}
