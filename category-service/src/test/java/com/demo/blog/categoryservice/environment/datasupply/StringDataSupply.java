package com.demo.blog.categoryservice.environment.datasupply;

import java.util.stream.Stream;

public class StringDataSupply {

    static Stream<String> blankStrings() {
        return Stream.of(
                "\t \t \n",
                "       ",
                ""
        );
    }
}
