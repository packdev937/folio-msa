package kr.folio.photo.presentation.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to photo service";
    }
}
