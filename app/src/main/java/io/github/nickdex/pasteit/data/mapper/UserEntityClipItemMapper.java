/*
 * Copyright © 2016 Nikhil Warke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nickdex.pasteit.data.mapper;

import io.github.nickdex.pasteit.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import io.github.nickdex.pasteit.domain.model.User;

/**
 * Mapper to convert {@link UserEntity} into {@link User} and vice versa.
 */
public class UserEntityClipItemMapper extends BaseMapper<UserEntity, User> {

    /**
     * Method which converts {@link User} to {@link UserEntity}.
     *
     * @param o2 {@link User} that contains some data.
     * @return {@link UserEntity} mapped with data from o2.
     * Default value is used when corresponding data is not found in o2.
     */
    @Override
    public UserEntity mapToFirst(User o2) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(o2.getId());
        userEntity.setName(o2.getName());
        userEntity.setEmail(o2.getEmail());
        return userEntity;
    }

    /**
     * Method which converts {@link UserEntity} to {@link User}.
     *
     * @param o1 {@link UserEntity} that contains some data.
     * @return {@link User} mapped with data from o1.
     * Default value is used when corresponding data is not found in o1.
     */
    @Override
    public User mapToSecond(UserEntity o1) {
        User user = new User();
        user.setId(o1.getId());
        user.setName(o1.getName());
        user.setEmail(o1.getEmail());
        return user;
    }
}
