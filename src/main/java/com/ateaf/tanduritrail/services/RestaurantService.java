package com.ateaf.tanduritrail.services;

import com.ateaf.tanduritrail.exception.ImageNotFoundException;
import com.ateaf.tanduritrail.exception.ImageUploadFailedException;
import com.ateaf.tanduritrail.exception.UserRoleNotFoundException;
import com.ateaf.tanduritrail.mapper.RestaurantMapper;
import com.ateaf.tanduritrail.modal.restaurant.Restaurant;
import com.ateaf.tanduritrail.modal.restaurant.RestaurantApproval;
import com.ateaf.tanduritrail.repository.RestaurantApprovalRepository;
import com.ateaf.tanduritrail.repository.RestaurantRepository;
import com.ateaf.tanduritrail.requestDto.UserRegisterRequest;
import com.ateaf.tanduritrail.requestDto.restaurant.RestaurantRegistrationRequest;
import com.ateaf.tanduritrail.requestDto.restaurant.UserRestaurantRegistrationRequest;
import com.ateaf.tanduritrail.responseDto.UserResponse;
import com.ateaf.tanduritrail.responseDto.restaurant.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    @Value("${upload.directory}")
    private String uploadDir;

    private final RestaurantRepository restaurantRepository;
    private final RestaurantApprovalRepository approvalRepository;
    private final RestaurantMapper mapper;
    private final UserService userService;

    @Transactional
    public Map<String, Object> registerNewRestaurant(UserRestaurantRegistrationRequest registrationRequest, MultipartFile image) {
        UserRegisterRequest userRequest = registrationRequest.user();
        RestaurantRegistrationRequest request = registrationRequest.restaurant();

        if(!Objects.equals(userRequest.roleName(), "SELLER"))
            throw new UserRoleNotFoundException("Incorrect role");

        String username = userService.registerUser(userRequest);  //  registering the User
        UserResponse user = userService.getUserByUsername(username);  // fetching user details
        String imagePath = handleImageUpload(image); // uploading the image
        Restaurant  restaurant = restaurantRepository.save(mapper.toRestaurant(request,user.id(),imagePath)); // registering restaurant

        RestaurantApproval approval = mapper.toRestaurantApproval(restaurant.getRestaurantID(),restaurant.getName(),user);
        approvalRepository.save(approval); // adding the restaurant to approval list

        Map<String,Object> response = new HashMap<>();
        response.put("restaurant_id",restaurant.getRestaurantID());
        response.put("restaurant_name", restaurant.getName());
        response.put("owner",username);

        return response;
    }

    public List<RestaurantResponse> getAllRestaurant() {
        Set<Integer> pendingApprovalIds = approvalRepository.findByIsApproved('N')
                                .stream()
                                .map(RestaurantApproval::getRestaurantId)
                                .collect(Collectors.toSet());

        return restaurantRepository.findAll()
                .stream()
                .filter( r -> r.getIsActive() == 'Y')
                .filter( r -> !pendingApprovalIds.contains(r.getRestaurantID()))
                .map(mapper::fromRestaurant)
                .collect(Collectors.toList());

    }
     //TODO: while fetching the restaurant details check if it approved or not (pass it in the response). (for seller login)
     // if not approved, an message will be shown like
     // "this Restaurant is pending for approval, once approved your restaurant will be visible to the customer"

    public String handleImageUpload(MultipartFile restaurantImage) {
        if (restaurantImage == null || restaurantImage.isEmpty()) {
            log.error("Received an empty or null file.");
            throw new IllegalArgumentException("File is empty or null");
        }

        String filename = restaurantImage.getOriginalFilename();
        if (filename == null || filename.trim().isEmpty()) {
            log.error("File has no valid name.");
            throw new IllegalArgumentException("File has no valid name");
        }
        log.info("Processing file: {}", filename);

        // Proceed with file upload logic
        String directoryPath = uploadDir + File.separator + "restaurant";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            log.error("Failed to create upload directory at {}", directoryPath);
            throw new ImageUploadFailedException("Unable to create upload directory");
        }

        String filePath = directoryPath + File.separator + filename;
        File destinationFile = new File(filePath);
        try {
            restaurantImage.transferTo(destinationFile);
        } catch (IOException e) {
            log.error("File transfer failed: {}", e.getMessage(), e);
            throw new ImageUploadFailedException("File upload failed");
        }

        log.info("File successfully uploaded to: {}", filePath);
        return filePath;
    }

    public Resource handleImageFetch(String imageName){
        //get the Image path based on the image name
        Path imagePath = Paths.get(uploadDir, "restaurant", imageName);
        Resource resource = new FileSystemResource(imagePath);

        if (!resource.exists()) {
            throw new ImageNotFoundException("Image not found: " + imageName);
        }
        return resource;
    }

}
