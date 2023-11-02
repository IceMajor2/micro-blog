package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.postcategory.PostCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("category")
@NoArgsConstructor
@Data
public class Category {

    @Id
    private Long id;
    private String name;
    @MappedCollection(idColumn = "category_id", keyColumn = "post_id")
    private Set<PostCategory> posts = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }
}
