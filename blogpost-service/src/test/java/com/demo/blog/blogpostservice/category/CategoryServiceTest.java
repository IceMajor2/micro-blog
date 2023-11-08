package com.demo.blog.blogpostservice.category;

import com.demo.blog.blogpostservice.command.CommandFactory;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestClassOrder(ClassOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl SUT;

    @Mock
    private CommandFactory repository;
}
