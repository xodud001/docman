package api.doc.docman.controller;


import api.doc.docman.annotation.TImer;
import api.doc.docman.domain.RestUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id, @RequestParam String name){
        return "get method" + ", id : " + id + ", name : " + name ;
    }

    @PostMapping("/post")
    public ResponseEntity post(@Valid @RequestBody RestUser user, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError)objectError;
                String message = objectError.getDefaultMessage();

                System.out.println("field : " + field.getField());
                System.out.println("message : " + message);
                sb.append("field : ").append(field.getField());
                sb.append("message : ").append(message);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @TImer
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(2000);
        return "delete method : " + id;
    }
}
