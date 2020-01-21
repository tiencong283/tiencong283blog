package io.tiencong283.blog.validator;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryFormValidator implements Validator {
    private PostCategoryService categoryService;

    @Autowired
    public CategoryFormValidator(PostCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PostCategory.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PostCategory category = (PostCategory) o;
        String name = category.getName();
        String urlSlug = category.getUrlSlug();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty");
        if (categoryService.exists(name)) {  // category name is unique
            errors.reject("name", "category.name.duplicate");
        }
        if (urlSlug.length() > 0 && categoryService.exists(urlSlug)) { // category urlSlug is unique too
            errors.reject("name", "category.urlSlug.duplicate");
        }
    }
}
