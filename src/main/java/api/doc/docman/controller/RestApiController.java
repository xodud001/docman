package api.doc.docman.controller;


import api.doc.docman.annotation.TImer;
import api.doc.docman.domain.RestUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id, @RequestParam String name){
        return "get method" + ", id : " + id + ", name : " + name ;
    }

    @PostMapping("/post")
    public RestUser post(@RequestBody RestUser user){
        return user;
    }


    @TImer
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(2000);
        return "delete method : " + id;
    }
}
