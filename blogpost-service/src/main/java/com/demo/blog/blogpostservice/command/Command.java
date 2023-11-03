package com.demo.blog.blogpostservice.command;

@FunctionalInterface
public interface Command {

    Object execute();
}
