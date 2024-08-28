package kr.folio.qr.presentation.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrs")
public class QrController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to qr service";
    }
}
