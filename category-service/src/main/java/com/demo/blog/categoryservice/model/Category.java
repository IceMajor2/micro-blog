package com.demo.blog.categoryservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("category")
@NoArgsConstructor
@Data
public class Category {

    @Id
    private Long id;
    private String name;
//    @MappedCollection(idColumn = "category_id", keyColumn = "post_id")
//    private Set<PostCategory> posts = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }
}
