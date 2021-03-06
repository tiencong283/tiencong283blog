package io.tiencong283.blog;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.model.WebUser;
import io.tiencong283.blog.model.WebUserRole;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class InitModel implements CommandLineRunner {
    private PostCategoryService postCategoryService;
    private UserService userService;

    @Autowired
    public InitModel(PostCategoryService postCategoryService, UserService userService) {
        this.postCategoryService = postCategoryService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // add categories
        postCategoryService.addCategory(new PostCategory("Reversing"));
        postCategoryService.addCategory(new PostCategory("Exploitation"));
        postCategoryService.addCategory(new PostCategory("Data mining"));

        // add users
        userService.addUser(new WebUser("tiencong283", "password", "tiencong283@outlook.com"));
        userService.addUser(new WebUser("admin", "password", "admin@outlook.com", WebUserRole.ROLE_ADMIN));
    }
}
