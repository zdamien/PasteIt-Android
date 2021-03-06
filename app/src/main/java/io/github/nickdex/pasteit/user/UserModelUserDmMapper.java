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

package io.github.nickdex.pasteit.user;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.framework.data.manager.AuthManager;
import io.github.nickdex.pasteit.framework.domain.model.User;

/**
 * Mapper to convert a user model from presentation layer into a user in domain layer and vice versa.
 */
public class UserModelUserDmMapper extends BaseMapper<UserModel, User> {

    private AuthManager authManager;

    @Inject
    public UserModelUserDmMapper(AuthManager authManager) {
        this.authManager = authManager;
    }

    /**
     * Method which converts user to a user model.
     * Default value is used when corresponding data is not found in user.
     *
     * @param user A user that contains some data.
     * @return The UserModel mapped with data from user.
     */
    @Override
    public UserModel mapToFirst(User user) {
        if (user == null) return null;

        UserModel userModel = new UserModel();

        userModel.setPhotoUri(authManager.getPhotoUrl());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        return userModel;
    }

    /**
     * Method which converts userEntity to a user.
     * Default value is used when corresponding data is not found in userEntity.
     *
     * @param userModel A UserModel that contains some data.
     * @return The User mapped with data from userEntity.
     */
    @Override
    public User mapToSecond(UserModel userModel) {
        if (userModel == null) return null;

        User user = new User();

        user.setId(authManager.getCurrentUserId());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        return user;
    }
}
