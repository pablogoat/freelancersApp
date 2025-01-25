package com.FreelancersBackend.api.posts;

import com.FreelancersBackend.models.PaymentDto;
import com.FreelancersBackend.models.Post;
import com.FreelancersBackend.models.PostData;
import com.FreelancersBackend.service.PayPalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;
    private final PayPalService paymentService;

    @PostMapping
    public ResponseEntity<Integer> createNewPost(
            Authentication authentication,
            @RequestBody NewPostRequest request){
        return ResponseEntity.ok(postsService.createNew(
                request,
                authentication.getName()));
    }

    @PostMapping("/api/posts/{postId}/mark-priority")
    public ResponseEntity<Void> markPostAsPriority(@PathVariable Long postId, @RequestBody PaymentDto paymentDto) {
        boolean isPaymentValid = paymentService.verifyPayment(paymentDto.getPaymentId());

        if (isPaymentValid) {
            postsService.markAsPriority(Math.toIntExact(postId));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PostData>> getAllPosts(Authentication authentication) {
        return ResponseEntity.ok(postsService.getAllPosts());
    }

    @GetMapping(path = "/user")
    public ResponseEntity<List<PostData>> getAllUserPosts(Authentication authentication) {
        return ResponseEntity.ok(postsService.getAllUserPosts(authentication.getName()));
    }

    @GetMapping(path = "/{title}")
    public ResponseEntity<List<PostData>> getPost(@PathVariable("title") String title) {
        return ResponseEntity.ok(postsService.getPost(title));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updatePost(
            Authentication authentication,
            @RequestBody NewPostRequest request,
            @PathVariable("id") Integer id){
        return ResponseEntity.ok(postsService.updatePost(
                id,
                request,
                authentication.getName()
        ));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePost(
            Authentication authentication,
            @PathVariable("id") Integer id){
        return ResponseEntity.ok(postsService.deletePost(
                id,
                authentication.getName()
        ));
    }
}
