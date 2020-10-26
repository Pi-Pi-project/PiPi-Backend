package pipi.api.domain.post.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.post.dto.*;

import java.util.List;

public interface PostService {
    void writeOne(PostWriteRequest postWriteRequest);
    List<GetPostsResponse> getPosts(Pageable pageable);
    List<GetPostsResponse> getMyPosts(Pageable pageable);
    GetDetailPostResponse getOne(Long id);
    void applyOne(PostApplyRequest postApplyRequest);
    List<GetApplyListResponse> getApplyList(Long id);
    void acceptApply(AcceptApplyRequest acceptApplyRequest);
    void denyApply(AcceptApplyRequest acceptApplyRequest);
}
