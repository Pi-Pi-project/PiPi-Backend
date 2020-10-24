package pipi.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.post.domain.PostSkillset;
import pipi.api.domain.post.domain.repository.PostRepository;
import pipi.api.domain.post.domain.repository.PostSkillsetRepository;
import pipi.api.domain.post.dto.GetDetailPostResponse;
import pipi.api.domain.post.dto.GetPostsResponse;
import pipi.api.domain.post.dto.PostWriteRequest;
import pipi.api.domain.post.exception.PostNotFoundException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.config.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        userRepository.findByEmail(authenticationFacade.getUserEmail())
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

    @Override
    public List<GetPostsResponse> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllBy(pageable);
        List<GetPostsResponse> getPostsResponses = new ArrayList<>();
        for (Post post : posts) {
            User writer = userRepository.findByEmail(post.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            List<PostSkillset> skills = postSkillsetRepository.findAllByPostId(post.getId());
            getPostsResponses.add(
                    GetPostsResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .img(post.getImg())
                            .category(post.getCategory())
                            .idea(post.getIdea())
                            .postSkillsets(skills)
                            .userEmail(writer.getEmail())
                            .userImg(writer.getProfileImage())
                            .userNickname(writer.getNickname())
                            .createdAt(post.getCreatedAt())
                            .build()
            );
        }
        return getPostsResponses;
    }

    @Override
    public List<GetPostsResponse> getMyPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByUserEmail(authenticationFacade.getUserEmail(), pageable);
        List<GetPostsResponse> getMyPostsResponses = new ArrayList<>();
        for (Post post : posts) {
            User writer = userRepository.findByEmail(post.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            List<PostSkillset> skills = postSkillsetRepository.findAllByPostId(post.getId());
            getMyPostsResponses.add(
                    GetPostsResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .img(post.getImg())
                            .category(post.getCategory())
                            .idea(post.getIdea())
                            .postSkillsets(skills)
                            .userEmail(writer.getEmail())
                            .userImg(writer.getProfileImage())
                            .userNickname(writer.getNickname())
                            .createdAt(post.getCreatedAt())
                            .build()
            );
        }
        return getMyPostsResponses;
    }

    @Override
    public GetDetailPostResponse getOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        User writer = userRepository.findByEmail(post.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        List<PostSkillset> skills = postSkillsetRepository.findAllByPostId(post.getId());
        return GetDetailPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .img(post.getImg())
                .category(post.getCategory())
                .idea(post.getIdea())
                .content(post.getContent())
                .postSkillsets(skills)
                .userEmail(writer.getEmail())
                .userImg(writer.getProfileImage())
                .userNickname(writer.getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}