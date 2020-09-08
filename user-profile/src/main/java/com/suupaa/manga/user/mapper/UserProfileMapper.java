package com.suupaa.manga.user.mapper;

import org.mapstruct.Mapper;

import com.suupaa.manga.user.dto.UserProfileTO;
import com.suupaa.manga.user.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileTO toUserProfileTO(UserProfile userProfile);

    UserProfile toUserProfileEntity(UserProfileTO userProfileTO);

}
