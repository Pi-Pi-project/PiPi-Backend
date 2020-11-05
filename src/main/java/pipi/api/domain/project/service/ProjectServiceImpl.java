package pipi.api.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.post.domain.enums.Accept;
import pipi.api.domain.post.domain.repository.ApplyRepository;
import pipi.api.domain.post.domain.repository.PostRepository;
import pipi.api.domain.post.exception.PostNotFoundException;
import pipi.api.domain.project.domain.Member;
import pipi.api.domain.project.domain.Project;
import pipi.api.domain.project.domain.enums.MemberStatus;
import pipi.api.domain.project.domain.repository.MemberRepository;
import pipi.api.domain.project.domain.repository.ProjectRepository;
import pipi.api.domain.project.dto.CreateProjectRequest;
import pipi.api.domain.project.exception.TooManyMemberException;
import pipi.api.global.config.AuthenticationFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final PostRepository postRepository;
    private final ProjectRepository projectRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createProject(CreateProjectRequest createProjectRequest) {
        Long postId = createProjectRequest.getPostId();
        List<Apply> applyList = applyRepository.findAllByPostIdAndAccept(postId, Accept.ACCEPTED);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (applyList.size() != post.getMax() - 1) {
            throw new TooManyMemberException();
        }
        Project project = projectRepository.save(
                Project.builder()
                        .title(post.getTitle())
                        .build()
        );
        memberRepository.save(
                Member.builder()
                        .projectId(project.getId())
                        .userEmail(authenticationFacade.getUserEmail())
                        .status(MemberStatus.PM)
                        .build()
        );
        for (Apply apply : applyList) {
            memberRepository.save(
                    Member.builder()
                            .projectId(project.getId())
                            .userEmail(apply.getUserEmail())
                            .status(MemberStatus.MEMBER)
                            .build()
            );
        }
    }
}
