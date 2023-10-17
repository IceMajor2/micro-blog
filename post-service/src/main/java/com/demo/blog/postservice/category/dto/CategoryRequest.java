package com.demo.blog.postservice.category.dto;

import com.demo.blog.postservice.category.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryRequest {

    @NotBlank(message = "Name must be specified")
    private String name;

    public Category toModel() {
        return Category.builder().name(this.name).build();
    }
}
