package io.tiencong283.service;

import io.tiencong283.service.markup.MarkupController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class IndexController {
    @GetMapping(path = "/", produces = MediaTypes.HAL_JSON_VALUE)
    public RepresentationModel index() {
        RepresentationModel resource = new RepresentationModel();
        // markup relation
        resource.add(linkTo(methodOn(MarkupController.class).renderMarkup(MediaType.TEXT_MARKDOWN, ""))
                .withRel("markup"));
        return resource;
    }
}
