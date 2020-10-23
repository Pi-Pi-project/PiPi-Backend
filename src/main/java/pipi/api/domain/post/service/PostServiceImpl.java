package pipi.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.post.domain.PostSkillset;
import pipi.api.domain.post.domain.repository.PostRepository;
import pipi.api.domain.post.domain.repository.PostSkillsetRepository;
import pipi.api.domain.post.dto.GetPostsResponse;
import pipi.api.domain.post.dto.PostWriteRequest;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.config.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostSkillsetRepository postSkillsetRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @SneakyThrows
    @Override
    public void writeOne(PostWriteRequest postWriteRequest) {
        System.out.println(authenticationFacade.getUserEmail());
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        String imageName;
        if (postWriteRequest.getImg() != null) {
            imageName = UUID.randomUUID().toString();
            postWriteRequest.getImg().transferTo(new File(imageDirPath, imageName));
        } else {
            imageName = "post.jpg";
        }

        Post post = postRepository.save(
                Post.builder()
                        .idea(postWriteRequest.getIdea())
                        .title(postWriteRequest.getTitle())
                        .category(postWriteRequest.getCategory())
                        .userEmail(authenticationFacade.getUserEmail())
                        .content(postWriteRequest.getContent())
                        .img(imageName)
                        .max(postWriteRequest.getMax())
                        .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                        .build()
        );
        
        if (postWriteRequest.getSkills() != null) {
            for (String skill : postWriteRequest.getSkills()) {
                postSkillsetRepository.save(
                        PostSkillset.builder()
                                .postId(post.getId())
                                .skill(skill)
                                .build()
                );
            }
        }
    }
}