package pipi.api.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pipi.api.domain.chat.domain.ChatMember;
import pipi.api.domain.chat.domain.Room;
import pipi.api.domain.chat.domain.repository.ChatMemberRepository;
import pipi.api.domain.chat.domain.repository.RoomRepository;
import pipi.api.domain.post.domain.Apply;
import pipi.api.domain.post.domain.Post;
import pipi.api.domain.post.domain.PostSkillset;
import pipi.api.domain.post.domain.enums.Accept;
import pipi.api.domain.post.domain.repository.ApplyRepository;
import pipi.api.domain.post.domain.repository.PostRepository;
import pipi.api.domain.post.domain.repository.PostSkillsetRepository;
import pipi.api.domain.post.exception.PostNotFoundException;
import pipi.api.domain.project.domain.Approval;
import pipi.api.domain.project.domain.Calendar;
import pipi.api.domain.project.domain.Member;
import pipi.api.domain.project.domain.Project;
import pipi.api.domain.project.domain.enums.ApprovalStatus;
import pipi.api.domain.project.domain.enums.MemberStatus;
import pipi.api.domain.project.domain.enums.TodoStatus;
import pipi.api.domain.project.domain.repository.ApprovalRepository;
import pipi.api.domain.project.domain.repository.CalendarRepository;
import pipi.api.domain.project.domain.repository.MemberRepository;
import pipi.api.domain.project.domain.repository.ProjectRepository;
import pipi.api.domain.project.dto.*;
import pipi.api.domain.project.exception.*;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.global.S3Service;
import pipi.api.global.config.security.AuthenticationFacade;
import pipi.api.global.error.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final PostRepository postRepository;
    private final ProjectRepository projectRepository;
    private final PostSkillsetRepository postSkillsetRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ApprovalRepository approvalRepository;
    private final ChatMemberRepository chatMemberRepository;
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
                        .approval(ApprovalStatus.WORKING)
                        .build()
        );
        memberRepository.save(
                Member.builder()
                        .projectId(project.getId())
                        .userEmail(authenticationFacade.getUserEmail())
                        .status(MemberStatus.PM)
                        .build()
        );
        Room room = roomRepository.save(
                Room.builder()
                        .title(post.getTitle())
                        .build()
        );
        chatMemberRepository.save(
                ChatMember.builder()
                        .roomId(room.getId())
                        .userEmail(authenticationFacade.getUserEmail())
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
            chatMemberRepository.save(
                    ChatMember.builder()
                            .roomId(room.getId())
                            .userEmail(apply.getUserEmail())
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
            Project project = projectRepository.findById(member.getProjectId())
                    .orElseThrow(ProjectNotFoundException::new);
            List<PostSkillset> skills = postSkillsetRepository.findAllByPostId(project.getId());
            projectResponseList.add(
                    GetMyProjectResponse.builder()
                            .id(project.getId())
                            .title(project.getTitle())
                            .img(project.getImg())
                            .idea(project.getIdea())
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
                        .date(createTodoRequest.getDate())
                        .build()
        );
    }

    @Override
    public List<GetTodoResponse> getTodo(Long id, String date) {
        List<Calendar> todos = calendarRepository.findAllByIdAndDate(id, date);
        List<GetTodoResponse> getTodoResponses = new ArrayList<>();
        for (Calendar todo : todos) {
            User writer = userRepository.findByEmail(todo.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            getTodoResponses.add(
                    GetTodoResponse.builder()
                            .id(todo.getId())
                            .nickname(writer.getNickname())
                            .date(todo.getDate())
                            .todo(todo.getTodo())
                            .todoStatus(todo.getTodoStatus())
                            .build()
            );
        }

        return getTodoResponses;
    }

    @Override
    public void successTodo(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(TodoNotFoundException::new);
        calendarRepository.save(calendar.setStatus(TodoStatus.CHECK));
    }

    @Override
    public void finishProject(FinishProjectRequest finishProjectRequest) {
        Project project = projectRepository.findById(finishProjectRequest.getId())
                .orElseThrow(ProjectNotFoundException::new);
        Member member = memberRepository.findByUserEmailAndProjectId(authenticationFacade.getUserEmail(), finishProjectRequest.getId());
        if (member.getStatus() != MemberStatus.PM)
            throw new NotProjectManagerException();
        projectRepository.save(project.setApproval(ApprovalStatus.WAITING));
        approvalRepository.save(
                Approval.builder()
                        .projectId(finishProjectRequest.getId())
                        .giturl(finishProjectRequest.getGiturl())
                        .introduce(finishProjectRequest.getIntroduce())
                        .build()
        );
    }
}
