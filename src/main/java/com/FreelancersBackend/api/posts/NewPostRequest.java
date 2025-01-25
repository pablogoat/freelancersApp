package com.FreelancersBackend.api.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewPostRequest {
    private String title;
    private String text;
    private Boolean priority;
    private float marker_location_lng;
    private float marker_location_lat;
}
