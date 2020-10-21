package pipi.api.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pipi.api.domain.post.dto.PostWriteRequest;
import pipi.api.domain.post.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public void writeOne(@ModelAttribute @Valid PostWriteRequest postWriteRequest) {
        postService.writeOne(postWriteRequest);
    }
}
