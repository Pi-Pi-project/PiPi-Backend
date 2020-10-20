package pipi.api.domain.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pipi.api.domain.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
