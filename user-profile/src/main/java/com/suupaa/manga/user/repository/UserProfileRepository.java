package com.suupaa.manga.user.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.suupaa.manga.user.entity.UserProfile;

@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile, String> {

}
