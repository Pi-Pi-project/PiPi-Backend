package pipi.api.domain.project.service;

import lombok.RequiredArgsConstructor;
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
import pipi.api.domain.post.exception.PostNotFoundException;
import pipi.api.domain.project.domain.Calendar;
import pipi.api.domain.project.domain.Member;
import pipi.api.domain.project.domain.Project;
import pipi.api.domain.project.domain.enums.MemberStatus;
import pipi.api.domain.project.domain.enums.TodoStatus;
import pipi.api.domain.project.domain.repository.CalendarRepository;
import pipi.api.domain.project.domain.repository.MemberRepository;
import pipi.api.domain.project.domain.repository.ProjectRepository;
import pipi.api.domain.project.dto.CreateProjectRequest;
import pipi.api.domain.project.dto.CreateTodoRequest;
import pipi.api.domain.project.dto.GetMyProjectResponse;
import pipi.api.domain.project.exception.NotMyTodoException;
import pipi.api.domain.project.exception.TodoNotFoundException;
import pipi.api.domain.project.exception.TooManyMemberException;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.S3Service;
import pipi.api.global.config.security.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final PostRepository postRepository;
    private final ProjectRepository projectRepository;
    private final PostSkillsetRepository postSkillsetRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
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
        s3Service.delete(post.getImg());
        postRepository.deleteById(postId);
    }

    @Override
    public List<GetMyProjectResponse> getMyProject(Pageable pageable) {
        Page<Member> members = memberRepository.findAllByUserEmail(authenticationFacade.getUserEmail(), pageable);
        List<GetMyProjectResponse> projectResponseList = new ArrayList<>();
        for (Member member : members) {
            Post post = postRepository.findById(member.getProjectId())
                    .orElseThrow(PostNotFoundException::new);
            List<PostSkillset> skills = postSkillsetRepository.findAllByPostId(post.getId());
            User writer = userRepository.findByEmail(post.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            projectResponseList.add(
                    GetMyProjectResponse.builder()
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
        return projectResponseList;
    }

    @Override
    public void createTodo(CreateTodoRequest createTodoRequest) {
        calendarRepository.save(
                Calendar.builder()
                        .projectId(createTodoRequest.getProjectId())
                        .userEmail(authenticationFacade.getUserEmail())
                        .todo(createTodoRequest.getTodo())
                        .todoStatus(TodoStatus.WAITING)
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build()
        );
    }

    @Override
    public void successTodo(Long id) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(TodoNotFoundException::new);
        if (user.getEmail() != calendar.getUserEmail())
            throw new NotMyTodoException();
        calendarRepository.save(calendar.setStatus(TodoStatus.CHECK));
    }
}
