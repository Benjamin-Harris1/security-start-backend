package dat3.recipe.service;

import dat3.recipe.entity.Category;
import dat3.recipe.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        //Convert from list of Categories to DTO-type, list of Strings
        return categories.stream().map((c)->new String(c.getName())).toList();
    }

    // Post category
    public Category addCategory(String category) {
        Category newCategory = new Category(category);
        categoryRepository.save(newCategory);
        return newCategory;
    }

    public Category updateCategory(int id, String newName){
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(newName);
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategory(int id){
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(id);
    }
}

