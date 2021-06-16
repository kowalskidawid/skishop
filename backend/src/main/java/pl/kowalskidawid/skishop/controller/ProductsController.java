package pl.kowalskidawid.skishop.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kowalskidawid.skishop.Response;
import pl.kowalskidawid.skishop.dto.*;
import pl.kowalskidawid.skishop.entity.Category;
import pl.kowalskidawid.skishop.entity.Product;
import pl.kowalskidawid.skishop.entity.ProductProperty;
import pl.kowalskidawid.skishop.entity.WebsiteError;
import pl.kowalskidawid.skishop.repository.CategoriesRepository;
import pl.kowalskidawid.skishop.repository.ProductPropertiesRepository;
import pl.kowalskidawid.skishop.repository.ProductsRepository;
import pl.kowalskidawid.skishop.repository.WebsiteErrorsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "api/products")
@CrossOrigin
public class ProductsController {
    private ProductsRepository productsRepository;
    private CategoriesRepository categoriesRepository;
    private WebsiteErrorsRepository websiteErrorsRepository;
    private ProductPropertiesRepository productPropertiesRepository;

    @Autowired
    public ProductsController(ProductsRepository productsRepository, CategoriesRepository categoriesRepository, WebsiteErrorsRepository websiteErrorsRepository, ProductPropertiesRepository productPropertiesRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.websiteErrorsRepository = websiteErrorsRepository;
        this.productPropertiesRepository = productPropertiesRepository;
    }

    @GetMapping(path = "/{currentPage}/{perPage}")
    public ResponseEntity<ProductsDTO> products(@PathVariable Long currentPage, @PathVariable Long perPage) {
        Long count = this.productsRepository.count();
        Long maxPage = (long) Math.ceil(count / perPage);
        ProductsDTO dto = new ProductsDTO(
                count,
                currentPage,
                maxPage,
                perPage,
                "",
                this.productsRepository.findAll().iterator()
        );

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(path = "/{categoryId}/{currentPage}/{perPage}")
    public ResponseEntity<ProductsInCategoryDTO> productsInCategory(@PathVariable Long currentPage, @PathVariable Long perPage, @PathVariable Integer categoryId) throws Exception {
        Long count = 120L;
        Long maxPage = (long) Math.ceil(count / perPage);
        ProductsInCategoryDTO dto = new ProductsInCategoryDTO(
                count,
                currentPage,
                maxPage,
                perPage,
                "",
                this.categoriesRepository.findById(categoryId).get(),
                this.productsRepository.findProductByCategoryId(categoryId)
        );

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<?> productById(@PathVariable Integer productId) throws Exception {
        ProductDTO dto = new ProductDTO();
        try {
            Product product = this.productsRepository.findById(productId).get();
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setCategory(this.categoryToDto(product.getCategory()));
            dto.setPrice(product.getPrice());
            dto.setId(productId);
            dto.setProperties(product.getProperties());
            dto.setInStock(product.getInStock());
//            dto.setRelatedProducts(this.getRelatedProducts(product));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            WebsiteError websiteError = new WebsiteError("ProductsController", "productById", exception.getClass() + " " + exception.getMessage(), null);
            this.websiteErrorsRepository.save(websiteError);
            ExceptionDTO exceptionDTO = new ExceptionDTO(404, "Product with id " + productId + " not found");
            return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            WebsiteError websiteError = new WebsiteError("ProductsController", "productById", exception.getClass() + " " + exception.getMessage(), null);
            this.websiteErrorsRepository.save(websiteError);
            ExceptionDTO exceptionDTO = new ExceptionDTO(500, "Something want wrong");
            return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Add product to category
     *
     * @param categoryId category id
     * @param product    product to add
     * @return
     */
    @PostMapping(path = "/{categoryId}")
    public ResponseEntity<CreateProductDTO> addProduct(@PathVariable Integer categoryId, @RequestBody Product product) {
        System.out.println(product.toString());
        CreateProductDTO dto = new CreateProductDTO();
        try {
            Category category = this.categoriesRepository.findById(categoryId).get();
            product.setCategory(category);
            this.productsRepository.save(product);
            product.getProperties().forEach(productProperty -> {
                this.productPropertiesRepository.save(productProperty);
            });
            dto.setCreated(true);
//            dto.setResult(new ProductDTO(product.getId(), product.getPrice(), product.getName(), product.getDescription(), categoryDTO, product.getProperties(), null, product.getInStock()));
        } catch (Exception exception) {
            dto.setError(exception.getMessage());
            dto.setCreated(true);
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    private CategoryDTO categoryToDto(Category category) {
        return new CategoryDTO(category.getId(),
                category.getCategoryName(),
                category.getThumbnailPath(),
                this.productsRepository.countProductByCategoryId(category.getId())
        );
    }

    private List<ProductDTO> getRelatedProducts(Product product) {
        Stream<Product> products = this.productsRepository.findProductByCategoryId(product.category.getId()).stream().limit(4);

        List<ProductDTO> productDTOList = new ArrayList<>();
//        products.forEach(relatedProduct -> productDTOList.add(new ProductDTO(
//                relatedProduct.getId(), relatedProduct.getPrice(), relatedProduct.getName(), relatedProduct.getDescription(), new CategoryDTO(1, "test", "test", 1L), null, null
//        )));
        return productDTOList;
    }

    @PutMapping(path = "/{productId}/{categoryId}")
    public ResponseEntity<CreateProductDTO> update(@PathVariable Integer productId, @PathVariable Integer categoryId, @RequestBody Product product) {
        CreateProductDTO dto = new CreateProductDTO();
        try {
            Product oldProduct = this.productsRepository.findById(productId).get();
            oldProduct.setPrice(product.getPrice());
            oldProduct.setName(product.getName());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setCategory(this.categoriesRepository.getOne(categoryId));
            oldProduct.setInStock(product.getInStock());
            this.productsRepository.save(oldProduct);
            dto.setCreated(true);
            CategoryDTO categoryDTO = new CategoryDTO(product.getCategory().getId(), product.getCategory().getCategoryName(), product.getCategory().getThumbnailPath(), null);
            dto.setResult(new ProductDTO(product.getId(), product.getPrice(), product.getName(), product.getDescription(), categoryDTO, product.getProperties(), null, product.getInStock()));
        } catch (Exception exception) {
            dto.setError(exception.getMessage());
            dto.setCreated(true);
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<?> delete(@PathVariable Integer productId) {
        try {
            this.productsRepository.deleteById(productId);
            return ResponseEntity.ok(new DeleteDTO(true));
        } catch (Exception exception) {
            return ResponseEntity.ok(new ExceptionDTO(404, "Product with id " + productId + " not found"));
        }
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable String query) {
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        searchResultDTO.setQuery(query);
        query = "%" + query + "%";
        List<ProductDTO> productsList = new ArrayList<>();
        List<CategoryDTO> categoriesList = new ArrayList<>();

        this.productsRepository.search(query).forEach(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setCategory(this.categoryToDto(product.getCategory()));
            dto.setPrice(product.getPrice());
            dto.setId(product.getId());
            dto.setProperties(product.getProperties());
            dto.setInStock(product.getInStock());
            productsList.add(dto);
        });
        searchResultDTO.setProducts(productsList);

        this.categoriesRepository.search(query).forEach(category -> {
            categoriesList.add(new CategoryDTO(category.getId(), category.getCategoryName(), null, this.productsRepository.countProductByCategoryId(category.getId())));
        });
        searchResultDTO.setCategories(categoriesList);
        searchResultDTO.setMatched(false);
        if (productsList.size() > 0 || categoriesList.size() > 0) {
            searchResultDTO.setMatched(true);
        }
        return ResponseEntity.ok(searchResultDTO);
    }
}