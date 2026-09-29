package com.example.demo.mapper;

import com.example.demo.dto.response.CVResponse;
import com.example.demo.entity.CV;
import org.springframework.stereotype.Component;

@Component
public class CVMapper {
    public CVResponse toCVResponse(CV cv) {
        if (cv == null) {
            return null;
        }
        return CVResponse.builder()
                .id(cv.getId())
                .title(cv.getTitle())
                .fileUrl(cv.getFileUrl())
                .displayOrder(cv.getDisplayOrder())
                .isPrimary(cv.getIsPrimary())
                .build();
    }
}
