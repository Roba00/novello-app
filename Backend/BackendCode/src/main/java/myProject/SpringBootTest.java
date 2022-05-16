package myProject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootTest {
    @GetMapping("/test")
    public String index() {
        return "Hello there! I'm running.";
    }
}
