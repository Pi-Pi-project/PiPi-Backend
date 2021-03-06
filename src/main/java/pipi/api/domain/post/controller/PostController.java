package pipi.api.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.post.dto.*;
import pipi.api.domain.post.service.PostService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
    public List<GetPostsResponse> getPosts(@PageableDefault(size = 10) @NotBlank Pageable page) {
        return postService.getPosts(page);
    }

    @GetMapping("/search")
    public List<GetPostsResponse> getSearchPosts(@RequestParam(value = "category") @NotBlank String category, @PageableDefault(size = 10) @NotBlank Pageable page) {
        return postService.getSearchPosts(category, page);
    }

    @GetMapping("/mine")
    public List<GetPostsResponse> getMyPosts(@PageableDefault(size = 10) @NotBlank Pageable page) {
        return postService.getMyPosts(page);
    }

    @GetMapping("/{id}")
    public GetDetailPostResponse getOne(@PathVariable Long id) {
        return postService.getOne(id);
    }

    @GetMapping("/apply")
    public List<GetPostsResponse> getAppliedPosts(@PageableDefault(size = 10) @NotBlank Pageable page) {
        return postService.getAppliedPosts(page);
    }

    @PostMapping("/apply")
    public void applyOne(@RequestBody @NotBlank PostApplyRequest postApplyRequest) {
        postService.applyOne(postApplyRequest);
    }

    @DeleteMapping("/apply")
    public void cancleApply(@RequestBody @NotBlank PostApplyRequest postApplyRequest) {
        postService.cancleApply(postApplyRequest);
    }

    @GetMapping("/apply/{id}")
    public List<GetApplyListResponse> getApplyList(@PathVariable Long id) {
        return postService.getApplyList(id);
    }

    @PutMapping("/apply/accept")
    public void acceptApply(@RequestBody @Valid AcceptApplyRequest acceptApplyRequest) {
        postService.acceptApply(acceptApplyRequest);
    }

    @PutMapping("/apply/deny")
    public void denyApply(@RequestBody @Valid AcceptApplyRequest acceptApplyRequest) {
        postService.denyApply(acceptApplyRequest);
    }
}
