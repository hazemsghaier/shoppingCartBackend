package com.shop.e_comerce.service.category;

import com.shop.e_comerce.exeptions.AlreadyExistExeption;
import com.shop.e_comerce.exeptions.ResourceNotFoundExeption;
import com.shop.e_comerce.model.Category;
import com.shop.e_comerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundExeption("the category dosen t exist"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c->!categoryRepository.existsByName(c.getName())).map(categoryRepository::save)
                .orElseThrow(()->new AlreadyExistExeption(category.getName()+"already exist"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map((oldCategory)->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()->new ResourceNotFoundExeption("the category dosen t exist"));
    }

    @Override
    public void deleteCategory(Long id) {
         categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
             throw new ResourceNotFoundExeption("the category dosen t exist");
         });
    }
}
