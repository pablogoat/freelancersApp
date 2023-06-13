package com.FreelancersBackend.api.posts;

import com.FreelancersBackend.dao.PostsRepository;
import com.FreelancersBackend.dao.UserRepository;
import com.FreelancersBackend.models.Post;
import com.FreelancersBackend.models.PostData;
import com.FreelancersBackend.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    private final SessionFactory sessionFactory;
    public Integer createNew(NewPostRequest request, String email) {
        var post = Post.builder()
                .title(request.getTitle())
                .text(request.getText())
                .marker_location_lat(request.getMarker_location_lat())
                .marker_location_lng(request.getMarker_location_lng())
                .user(userRepository.findByEmail(email).get())
                .timestamp(new Date())
                .build();

        postsRepository.save(post);
        return 1;
    }

    public List<PostData> getAllPosts() {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String query = "select p from Post p";

            List<PostData> posts = session.createQuery(query, Post.class).list().stream()
                    .map(PostData::new)
                    .collect(Collectors.toList());

            session.getTransaction().commit();
            return posts;
        }


    }

    public List<PostData> getAllUserPosts(String email) {
        return userRepository.findByEmail(email).get().getPosts().stream()
                .map(PostData::new)
                .collect(Collectors.toList());
    }

    public List<PostData> getPost(String title) {
        return postsRepository.findByTitle(title).stream()
                .map(PostData::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public String updatePost(Integer id, NewPostRequest request, String email) {
        Optional<Post> post = postsRepository.findById(id);

        if(post.isEmpty()){
            return "Post does not exist";
        } else if (!post.get().getUser().getEmail().equals(email)) {
            return "User is not the owner of the post";
        }

        post.get().setTitle(request.getTitle());
        post.get().setText(request.getText());
        post.get().setMarker_location_lat(request.getMarker_location_lat());
        post.get().setMarker_location_lng(request.getMarker_location_lng());

        return "Post updated";
    }

    @Transactional
    public String deletePost(Integer id, String email) {

        Optional<Post> post = postsRepository.findById(id);

        if(post.isEmpty()){
            return "Post does not exist";
        } else if (!post.get().getUser().getEmail().equals(email)) {
            return "User is not the owner of the post";
        }

        postsRepository.delete(post.get());

        return "Post deleted";
    }
}
