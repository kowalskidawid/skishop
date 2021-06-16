package pl.kowalskidawid.skishop.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "product")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    public Integer id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description", nullable = false)
    public String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "product_id")
    public List<ProductProperty> properties;

    public Double price;

    @Column(name = "in_stock")
    private Integer inStock;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ProductProperty> properties) {
        this.properties = properties;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", properties=" + properties +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}
