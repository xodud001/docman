package api.doc.docman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/greeting")
    public String greeting(){
        return "hello";
    }
}
