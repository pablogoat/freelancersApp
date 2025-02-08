package com.FreelancersBackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonBackReference
    private User user;
    private String title;
    private String text;
    private Boolean priority;
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Photo> photos;
    private Date timestamp;
    private float marker_location_lng;
    private float marker_location_lat;

}
