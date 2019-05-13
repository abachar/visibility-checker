package consulting.crafters.visibility.checker;

import consulting.crafters.visibility.checker.aspect.HasVisibilityOn;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @HasVisibilityOn("'Name-' + #query.name")
    public String sayHello(GreetingQuery query) {
        return "I can say hello to " + query.getName();
    }
}
