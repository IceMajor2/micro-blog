package com.demo.blog.postservice.assertions;

import com.demo.blog.postservice.category.Category;
import org.assertj.core.api.Assertions;

public class PostAssertions extends Assertions {

    public static CategoryAssert assertThat(Category actual) {
        return new CategoryAssert(actual);
    }
}
