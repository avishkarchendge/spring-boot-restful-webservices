package com.avi.rest.webservices.springrestfulwebservices.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
