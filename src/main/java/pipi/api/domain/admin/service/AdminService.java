package pipi.api.domain.admin.service;

import org.springframework.data.domain.Pageable;
import pipi.api.domain.admin.dto.GetApprovalProjectsResponse;
import pipi.api.domain.admin.dto.GetDetailUserReportResponse;
import pipi.api.domain.admin.dto.GetReportUsersResponse;
import pipi.api.domain.auth.dto.UserLoginRequest;
import pipi.api.domain.user.dto.TokenResponse;

import java.util.List;

public interface AdminService {
    TokenResponse adminLogin(UserLoginRequest userLoginRequest);
    List<GetApprovalProjectsResponse> getApprovalProjects(Pageable pageable);
    List<GetReportUsersResponse> getReportUsers(Pageable pageable);
    GetDetailUserReportResponse getDetailUserReport(String reportedEmail, String reporterEmail);
}
