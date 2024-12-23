package com.shop.e_comerce.service.product;

import com.shop.e_comerce.DTO.ImageDTO;
import com.shop.e_comerce.DTO.ProductDto;
import com.shop.e_comerce.exeptions.ProductNotFoundExeption;
import com.shop.e_comerce.model.Category;
import com.shop.e_comerce.model.Image;
import com.shop.e_comerce.model.Product;
import com.shop.e_comerce.repository.CategoryRepository;
import com.shop.e_comerce.repository.ImageRepository;
import com.shop.e_comerce.repository.ProductRepository;
import com.shop.e_comerce.request.AddProductRequest;
import com.shop.e_comerce.request.UpdateProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceService implements IProductService {
    private  final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final  ModelMapper modelMapper;
    public ProductServiceService(ProductRepository productRepository, CategoryRepository categoryRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Product addProduct(AddProductRequest request) {
        //if the category is found
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(()->{
           Category newCategory=new Category();
           newCategory.setName(request.getCategory().getName());
           return categoryRepository.save(newCategory);
        });
        //if yes set it as the category
        request.setCategory(category);

        //if no save it as  new category then st it as product category that we will  add
        return productRepository.save(createProduct(request,category));
    }
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundExeption("the product id dose not exite"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()->{throw new ProductNotFoundExeption("the product id dose not exite");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId).map(existProduct->updateExistingProduct(existProduct,request)).map(productRepository::save).orElseThrow(() -> new ProductNotFoundExeption("the product id dose not exite"));
    }
    private Product updateExistingProduct(Product product,  UpdateProductRequest request){
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());
        Category category=categoryRepository.findByName(request.getCategory().getName());
        product.setCategory(category);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {

        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String category, String name) {
        return List.of();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {

        return productRepository.countByBrandAndName(name, brand);
    }
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        productDto.setDescription(product.getDescription());

        // Ajout manuel des images
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDtos = images.stream()
                .map(image -> new ImageDTO(image.getId(),image.getFileName() ,image.getDownloadUrl()))
                .toList();
        productDto.setImages(imageDtos);

        // Mapping direct de la catégorie
        if (product.getCategory() != null) {
            System.out.println(product.getCategory());
            productDto.setCategory(product.getCategory());
        }

        return productDto;
    }
}
