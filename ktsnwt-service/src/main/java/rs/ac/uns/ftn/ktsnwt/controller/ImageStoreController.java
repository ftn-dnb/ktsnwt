package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.ktsnwt.service.imagestore.ImageStoreService;

@RestController
@RequestMapping("/api/images")
public class ImageStoreController {

    @Autowired
    private ImageStoreService imageStoreService;

    @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity changeProfileImage(@RequestParam("file") MultipartFile file) {
        imageStoreService.changeUserProfileImage(file);
        return ResponseEntity.ok().build();
    }
}
