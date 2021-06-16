package pl.kowalskidawid.skishop.entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.persistence.*;
import java.util.List;

@Entity(name = "categories")
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(name = "category_name", nullable = false, unique = true)
    public String categoryName;
    @Column(name = "thumbnail_path", unique = true)
    public String thumbnailPath;

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
