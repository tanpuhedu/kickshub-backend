package com.tanpuh.kickshub.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {
    Cloudinary cloudinary;

    public String upload(MultipartFile file, String folderName) {
        try {
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", folderName));
            String publicId = uploadedFile.get("public_id").toString();
            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
