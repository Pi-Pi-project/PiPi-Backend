package pipi.api.domain.post.service;

import pipi.api.domain.post.dto.PostWriteRequest;

public interface PostService {
    void writeOne(PostWriteRequest postWriteRequest);
}
