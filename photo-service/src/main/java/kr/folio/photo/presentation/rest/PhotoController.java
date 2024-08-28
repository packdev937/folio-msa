package kr.folio.photo.presentation.rest;

import kr.folio.photo.persistence.entity.PhotoEntity;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PhotoController implements PhotoControllerDocs {

    @GetMapping("/health_check")
    public String welcome() {
        return "Welcome to photo service";
    }

    @Override
    public ResponseEntity<CreatePhotoResponse> createPhoto(
        String id,
        CreatePhotoRequest request) {
        return null;
    }
}
