package pipi.api.domain.post.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.dto.GetDetailPostResponse;
import pipi.api.domain.post.dto.GetPostsResponse;
import pipi.api.domain.post.dto.PostApplyRequest;
import pipi.api.domain.post.dto.PostWriteRequest;

import java.util.List;

public interface PostService {
    void writeOne(PostWriteRequest postWriteRequest);
    List<GetPostsResponse> getPosts(Pageable pageable);
    List<GetPostsResponse> getMyPosts(Pageable pageable);
    GetDetailPostResponse getOne(Long id);
    void applyOne(PostApplyRequest postApplyRequest);
    List<Apply> getApplyList(Long id);
}
