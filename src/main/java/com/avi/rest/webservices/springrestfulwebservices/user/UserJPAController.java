package com.avi.rest.webservices.springrestfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException("id-" + id);

        // "all-users", SERVER_PATH + "/users"
        // retrieveAllUsers
        Resource<User> resource = new Resource<User>(user.get());

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUser());

        resource.add(linkTo.withRel("all-users"));

        // HATEOAS

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        User savedUser = userRepository.save(user);
        // alternate
        //ResponseEntity.created(new URI("/users/" + savedUser.getId())).body(savedUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> getAllPostByUserId(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("id " + id);
        }
        return optionalUser.get().getPosts();
    }

    @PostMapping("/users/{id}/post")
    public ResponseEntity<User> createUser(@PathVariable int id, @RequestBody Post post) throws URISyntaxException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("id " + id);
        }
        User user = optionalUser.get();
        post.setUser(user);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
