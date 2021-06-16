package pl.kowalskidawid.skishop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kowalskidawid.skishop.Response;
import pl.kowalskidawid.skishop.dto.*;
import pl.kowalskidawid.skishop.entity.Category;
import pl.kowalskidawid.skishop.entity.WebsiteError;
import pl.kowalskidawid.skishop.repository.CategoriesRepository;
import pl.kowalskidawid.skishop.repository.ProductsRepository;
import pl.kowalskidawid.skishop.repository.WebsiteErrorsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/categories")
@CrossOrigin
public class CategoriesController {
    private CategoriesRepository categoriesRepository;
    private ProductsRepository productsRepository;
    private Response response;
    private WebsiteErrorsRepository websiteErrorsRepository;
    @Autowired
    public CategoriesController(CategoriesRepository categoriesRepository, ProductsRepository productsRepository, WebsiteErrorsRepository websiteErrorsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
        this.response = new Response();
        this.websiteErrorsRepository = websiteErrorsRepository;
    }

    @GetMapping(path = "")
    public ResponseEntity<CategoriesDTO> categories() {
        CategoriesDTO dto = new CategoriesDTO(
                this.categoriesRepository.count(),
                this.categoriesToDto(this.categoriesRepository.findAll())
        );
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<CreateCategoryDTO> addCategory(@RequestBody Category category) {
        CreateCategoryDTO dto = new CreateCategoryDTO();
        try {
            Category savedCategory = this.categoriesRepository.save(category);
            dto.setCreated(true);
            dto.setResult(new CategoryDTO(
                    savedCategory.getId(), savedCategory.getCategoryName(), savedCategory.getThumbnailPath(), 0L
            ));
        } catch (Exception ex) {
            dto.setCreated(true);
            dto.setError(ex.getMessage());
        }

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{categoryId}")
    public Response editCategory(@PathVariable Integer categoryId, @RequestBody Category updatedCategory) {
        Category category = this.categoriesRepository.findById(categoryId).get();

        try {
            category.setCategoryName(updatedCategory.getCategoryName());
            category.setThumbnailPath(updatedCategory.getThumbnailPath());
            this.categoriesRepository.save(category);
            response.result = category;
            response.success = true;
        } catch (Exception ex) {
            response.success = false;
            response.addError(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<DeleteCategoryDTO> deleteCategory(@PathVariable Integer categoryId) {
        DeleteCategoryDTO dto = new DeleteCategoryDTO();
        try {
            this.categoriesRepository.deleteById(categoryId);
            dto.setDeleted(true);
        } catch (Exception ex) {
            dto.setDeleted(false);
            dto.setError("Coś poszło nie tak! Nie udało się usunąć kategorii");
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private List<CategoryDTO> categoriesToDto(List<Category> categories) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categories.forEach(category -> categoryDTOList.add(new CategoryDTO(
                category.getId(), category.getCategoryName(), category.getThumbnailPath(), this.productsRepository.countProductByCategoryId(category.getId())
        )));
        return categoryDTOList;
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable Integer categoryId) {
        try {
            Category category = this.categoriesRepository.getOne(categoryId);
            CategoryDTO categoryDTO = new CategoryDTO(
                  category.getId(), category.getCategoryName(), category.getThumbnailPath(), this.productsRepository.countProductByCategoryId(categoryId)
            );
//
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        } catch (Exception exception) {
            this.websiteErrorsRepository.save(new WebsiteError("CategoriesController", "getCategory", exception.getMessage(), null));
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, "not found category with id " + categoryId);
            return new ResponseEntity<>(exceptionDTO, HttpStatus.OK);
        }
    }
}
