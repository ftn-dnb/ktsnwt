package rs.ac.uns.ftn.ktsnwt.service.imagestore;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.ktsnwt.exception.ApiRequestException;
import rs.ac.uns.ftn.ktsnwt.service.event.EventService;
import rs.ac.uns.ftn.ktsnwt.service.user.UserService;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageStoreService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    public void changeUserProfileImage(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imagePath = (String) result.get("url");
            userService.changeProfileImage(imagePath);
        } catch (IOException e) {
            throw new ApiRequestException("There was an error while uploading a profile image");
        }
    }

    public void changeEventImage(MultipartFile file, Long id){
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imagePath = (String) result.get("url");
            eventService.setNewEventImage(imagePath, id);
        } catch (IOException e){
            throw  new ApiRequestException("There was an error while uploading a event image");
        }
    }
}
