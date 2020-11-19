package pipi.api.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pipi.api.domain.admin.dto.GetApprovalProjectsResponse;
import pipi.api.domain.admin.dto.GetReportUsersResponse;
import pipi.api.domain.admin.exception.NotAdminException;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.project.domain.Approval;
import pipi.api.domain.project.domain.Project;
import pipi.api.domain.project.domain.repository.ApprovalRepository;
import pipi.api.domain.project.domain.repository.ProjectRepository;
import pipi.api.domain.project.exception.ProjectNotFoundException;
import pipi.api.domain.user.domain.Report;
import pipi.api.domain.user.domain.User;
import pipi.api.domain.user.domain.enums.Admin;
import pipi.api.domain.user.domain.repository.ReportRepository;
import pipi.api.domain.user.domain.repository.UserRepository;
import pipi.api.domain.user.dto.TokenResponse;
import pipi.api.global.config.security.JwtTokenProvider;
import pipi.api.global.error.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final ApprovalRepository approvalRepository;
    private final ProjectRepository projectRepository;
    private final ReportRepository reportRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${secret.prefix}")
    private String prefix;

    @Override
    public TokenResponse adminLogin(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .filter(u -> passwordEncoder.matches(userLoginRequest.getPassword(), u.getPassword()))
                .orElseThrow(UserNotFoundException::new);
        if (user.getAdmin() != Admin.ADMIN)
            throw new NotAdminException();
        return responseToken(userLoginRequest.getEmail());
    }

    @Override
    public List<GetApprovalProjectsResponse> getApprovalProjects(Pageable pageable) {
        List<GetApprovalProjectsResponse> getApprovalProjectsResponses = new ArrayList<>();
        Page<Approval> approvals = approvalRepository.findAll(pageable);
        for (Approval approval : approvals) {
            Project project = projectRepository.findById(approval.getProjectId())
                    .orElseThrow(ProjectNotFoundException::new);
            getApprovalProjectsResponses.add(
                    GetApprovalProjectsResponse.builder()
                            .projectId(project.getId())
                            .title(project.getTitle())
                            .coverImg(project.getImg())
                            .build()
            );
        }
        return getApprovalProjectsResponses;
    }

    @Override
    public List<GetReportUsersResponse> getReportUsers(Pageable pageable) {
        List<GetReportUsersResponse> getReportUsersResponses = new ArrayList<>();
        Page<Report> reports = reportRepository.findAll(pageable);
        for (Report report : reports) {
            User user = userRepository.findByEmail(report.getReportedEmail())
                    .orElseThrow(UserNotFoundException::new);
            getReportUsersResponses.add(
                    GetReportUsersResponse.builder()
                            .userEmail(user.getEmail())
                            .profileImg(user.getProfileImage())
                            .userNickname(user.getNickname())
                            .build()
            );
        }
        return getReportUsersResponses;
    }

    private TokenResponse responseToken(String email) {
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(email))
                .refreshToken(jwtTokenProvider.generateRefreshToken(email))
                .tokenType(prefix)
                .build();
    }
}
