package pipi.api.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.post.dto.GetPostsResponse;
import pipi.api.domain.post.dto.PostWriteRequest;
import pipi.api.domain.post.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public void writeOne(@ModelAttribute @Valid PostWriteRequest postWriteRequest) {
        postService.writeOne(postWriteRequest);
    }

    @GetMapping
    public List<GetPostsResponse> getPosts(@PageableDefault(sort = {"createdAt"}, size = 10) Pageable page) {
        return postService.getPosts(page);
    }

    @GetMapping("/mine")
    public List<GetPostsResponse> getMyPosts(@PageableDefault(sort = {"createdAt"}, size = 10) Pageable page) {
        return postService.getMyPosts(page);
    }
}
