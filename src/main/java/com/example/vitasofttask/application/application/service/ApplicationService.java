package com.example.vitasofttask.application.application.service;

import com.example.vitasofttask.application.application.model.ApplicationDto;
import com.example.vitasofttask.common.Filter;
import com.example.vitasofttask.domain.Page;

public interface ApplicationService {
    void createApplication(ApplicationDto applicationDto);
    String sendToModerator(Long applicationId);
    ApplicationDto getApplication(Long id);
    Page<ApplicationDto> getAllApplicationsWithStatusSend(Filter filterParams);
    Page<ApplicationDto> getAllUserApplications(Long userId, Filter filterParams);
    String updateStatus(ApplicationDto applicationDto);
    String updateApplication (ApplicationDto applicationDto);

}
