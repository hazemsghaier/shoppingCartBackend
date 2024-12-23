package com.shop.e_comerce.service.image;

import com.shop.e_comerce.DTO.ImageDTO;
import com.shop.e_comerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> file, Long id);
    void updateImage(MultipartFile image, Long id);
}
