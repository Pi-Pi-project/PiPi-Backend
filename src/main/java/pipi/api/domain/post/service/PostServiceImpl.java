package pipi.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.post.domain.PostSkillset;
import pipi.api.domain.post.domain.enums.Accept;
import pipi.api.domain.post.domain.repository.ApplyRepository;
import pipi.api.domain.post.domain.repository.PostRepository;
import pipi.api.domain.post.domain.repository.PostSkillsetRepository;
import pipi.api.domain.post.dto.*;
import pipi.api.domain.post.exception.PostNotFoundException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.UserSearchLog;
import pipi.api.domain.user.domain.UserViewLog;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.domain.repository.UserSearchLogRepository;
import pipi.api.domain.user.domain.repository.UserViewLogRepository;
import pipi.api.global.config.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import javax.transaction.Transactional;
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
    private final UserViewLogRepository userViewLogRepository;
    private final ApplyRepository applyRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserSearchLogRepository userSearchLogRepository;

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

    private boolean checkApplied(Long postId, String email) {
        if (applyRepository.findByPostIdAndUserEmail(postId, email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public GetDetailPostResponse getOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        User writer = userRepository.findByEmail(post.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        List<PostSkillset> skills = postSkillsetRepository.findAllByPostId(post.getId());
        userViewLogRepository.save(
                UserViewLog.builder()
                        .userEmail(authenticationFacade.getUserEmail())
                        .log(post.getCategory())
                        .postId(post.getId())
                        .build()
        );
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
                .max(post.getMax())
                .createdAt(post.getCreatedAt())
                .isApplied(checkApplied(post.getId(), authenticationFacade.getUserEmail()))
                .build();
    }

    @Override
    public void applyOne(PostApplyRequest postApplyRequest) {
        postRepository.findById(postApplyRequest.getId())
                .orElseThrow(PostNotFoundException::new);
        applyRepository.save(
                Apply.builder()
                        .postId(postApplyRequest.getId())
                        .userEmail(authenticationFacade.getUserEmail())
                        .accept(Accept.WAITING)
                        .build()
        );
    }

    @Override
    @Transactional
    public void cancleApply(PostApplyRequest postApplyRequest) {
        postRepository.findById(postApplyRequest.getId())
                .orElseThrow(PostNotFoundException::new);
        applyRepository.deleteByPostIdAndUserEmail(postApplyRequest.getId(), authenticationFacade.getUserEmail());
    }

    @Override
    public List<GetApplyListResponse> getApplyList(Long id) {
        List <GetApplyListResponse> getApplyLIstResponses = new ArrayList<>();
        List<Apply> applies = applyRepository.findAllByPostId(id);
        for (Apply apply : applies) {
            User applier = userRepository.findByEmail(apply.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            getApplyLIstResponses.add(
                GetApplyListResponse.builder()
                        .userEmail(applier.getEmail())
                        .userImg(applier.getProfileImage())
                        .userNickname(applier.getNickname())
                        .status(apply.getAccept())
                        .build()
            );
        }
        return getApplyLIstResponses;
    }

    @Override
    public void acceptApply(AcceptApplyRequest acceptApplyRequest) {
        Apply apply = applyRepository.findByPostIdAndUserEmail(acceptApplyRequest.getId(), acceptApplyRequest.getUserEmail());
        applyRepository.save(apply.setApply(Accept.ACCEPTED));
    }

    @Override
    public void denyApply(AcceptApplyRequest acceptApplyRequest) {
        Apply apply = applyRepository.findByPostIdAndUserEmail(acceptApplyRequest.getId(), acceptApplyRequest.getUserEmail());
        applyRepository.save(apply.setApply(Accept.DENIED));
    }

    @Override
    public List<GetPostsResponse> getSearchPosts(String category, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByCategory(category, pageable);
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
        userSearchLogRepository.save(
                UserSearchLog.builder()
                        .log(category)
                        .userEmail(authenticationFacade.getUserEmail())
                        .build()
        );
        return getPostsResponses;
    }

    @Override
    public List<GetPostsResponse> getAppliedPosts(Pageable pageable) {
        Page<Apply> applies = applyRepository.findAllByUserEmail(pageable, authenticationFacade.getUserEmail());
        List<GetPostsResponse> getPostsResponses = new ArrayList<>();
        for (Apply apply : applies) {
            Post post = postRepository.findById(apply.getPostId())
                    .orElseThrow(PostNotFoundException::new);
            User writer = userRepository.findByEmail(authenticationFacade.getUserEmail())
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
}