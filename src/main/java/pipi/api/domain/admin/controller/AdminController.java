package pipi.api.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pipi.api.domain.admin.dto.GetApprovalProjectsResponse;
import pipi.api.domain.admin.dto.GetDetailApprovalProjectResponse;
import pipi.api.domain.admin.dto.GetDetailUserReportResponse;
import pipi.api.domain.admin.dto.GetReportUsersResponse;
import pipi.api.domain.admin.service.AdminService;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.user.dto.TokenResponse;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/auth")
    public TokenResponse adminLogin(UserLoginRequest userLoginRequest) {
        return adminService.adminLogin(userLoginRequest);
    }

    @GetMapping("/project")
    public List<GetApprovalProjectsResponse> getApprovalProjects(@PageableDefault(size = 10) Pageable pageable) {
        return adminService.getApprovalProjects(pageable);
    }

    @GetMapping("/project")
    public GetDetailApprovalProjectResponse getDetailApprovalProject(@PathVariable Long id) {
        return adminService.getDetailApprovalProject(id);
    }

    @GetMapping("/report")
    public List<GetReportUsersResponse> getReportsUsers(@PageableDefault(size = 10) Pageable pageable) {
        return adminService.getReportUsers(pageable);
    }

    @GetMapping("/report")
    public GetDetailUserReportResponse getDetailUserReport(@PathVariable String reportedEmail, @PathVariable String reporterEmail) {
        return adminService.getDetailUserReport(reportedEmail, reporterEmail);
    }
}
