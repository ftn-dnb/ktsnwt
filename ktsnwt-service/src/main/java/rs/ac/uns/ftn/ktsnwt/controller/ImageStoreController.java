package rs.ac.uns.ftn.ktsnwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/events/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity changeEventImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id){
        imageStoreService.changeEventImage(file, id);
        return ResponseEntity.ok().build();
    }

}
