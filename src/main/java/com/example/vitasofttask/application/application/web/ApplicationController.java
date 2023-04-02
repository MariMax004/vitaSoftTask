package com.example.vitasofttask.application.application.web;

import com.example.vitasofttask.application.application.model.ApplicationDto;
import com.example.vitasofttask.application.application.service.ApplicationService;
import com.example.vitasofttask.common.Filter;
import com.example.vitasofttask.domain.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/applications")
    public void createApplication(@RequestBody ApplicationDto applicationDto){
        applicationService.createApplication(applicationDto);
    }
    @PutMapping("/applications")
    public String updateApplication(@RequestBody ApplicationDto applicationDto){
        return applicationService.updateApplication(applicationDto);

    }
    @PutMapping("/applications/{applicationId}")
    public String sendToModerator(@PathVariable Long applicationId){
        return applicationService.sendToModerator(applicationId);
    }
    @PutMapping("/applications/status")
    public String updateStatus(@RequestBody ApplicationDto applicationDto){
        return applicationService.updateStatus(applicationDto);

    }
    @GetMapping("/applications/{applicationId}")
    public ApplicationDto getApplicationByAppId(@PathVariable Long applicationId){
        return applicationService.getApplication(applicationId);
    }
    @PostMapping("/all/{userId}/applications")
    public Page<ApplicationDto> getAllUserApplications(@PathVariable Long userId, @RequestBody Filter filterParams) {
        return applicationService.getAllUserApplications(userId, filterParams);
    }

    @PostMapping("/all/applications")
    public Page<ApplicationDto> getAllApplication(@RequestBody Filter filterParams) {
        return applicationService.getAllApplicationsWithStatusSend(filterParams);
    }

}
