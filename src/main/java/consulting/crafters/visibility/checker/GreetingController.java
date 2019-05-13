package consulting.crafters.visibility.checker;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GreetingController {
    private final GreetingService greetingService;

    @GetMapping("/greetings")
    @PreAuthorize("isAuthenticated() && hasVisibilityOn('Name-' + #name)")
    public String greeting(@RequestParam("name") String name) {
        return greetingService.sayHello(new GreetingQuery(name));
    }
}