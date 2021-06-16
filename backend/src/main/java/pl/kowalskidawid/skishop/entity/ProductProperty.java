package pl.kowalskidawid.skishop.entity;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_properties")
public class ProductProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(name = "property_name")
    public String propertyName;
    @Column(name = "property_value")
    public String propertyValue;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    public Product properties;

    public ProductProperty() {
    }

    public Integer getId() {
        return id;
    }

    public String getPropertyNam() {
        return propertyName;
    }

    public void setPropertyNam(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Product getProperties() {
        return properties;
    }

    public void setProperties(Product properties) {
        this.properties = properties;
    }
}
