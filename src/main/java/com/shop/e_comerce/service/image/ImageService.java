package com.shop.e_comerce.service.image;

import com.shop.e_comerce.DTO.ImageDTO;
import com.shop.e_comerce.exeptions.ResourceNotFoundExeption;
import com.shop.e_comerce.model.Image;
import com.shop.e_comerce.model.Product;
import com.shop.e_comerce.repository.ImageRepository;
import com.shop.e_comerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;
    public ImageService(ImageRepository imageRepository, IProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundExeption("the image dosen t exist"));
    }

    @Override
    public void deleteImageById(Long id) {
              imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()->{
                  throw new ResourceNotFoundExeption("the image dosen t exist");
              });
    }

    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
       Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDto = new ImageDTO();
                imageDto.setId(savedImage.getId());
                imageDto.setName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            }   catch(IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;

    }

    @Override
    public void updateImage(MultipartFile image, Long id) {
     Image imageToUpdate = getImageById(id);

        try {
            imageToUpdate.setFileName(image.getOriginalFilename());
            imageToUpdate.setImage(new SerialBlob(image.getBytes()));
            imageRepository.save(imageToUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
