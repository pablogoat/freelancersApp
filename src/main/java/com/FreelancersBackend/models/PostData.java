package com.FreelancersBackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostData {
    private Integer id;
    private Integer user_id;
    private String title;
    private String text;
    private Boolean priority;
    private List<String> photos;
    private Date timestamp;
    private float marker_location_lng;
    private float marker_location_lat;

    public PostData(Post post){
        this.id = post.getId();
        this.user_id = post.getUser().getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.priority = post.getPriority();
        this.photos = post.getPhotos().stream()
                .map(Photo::getUrl)
                .collect(Collectors.toList());
        this.timestamp = post.getTimestamp();
        this.marker_location_lng = post.getMarker_location_lng();
        this.marker_location_lat = post.getMarker_location_lat();
    }

}
