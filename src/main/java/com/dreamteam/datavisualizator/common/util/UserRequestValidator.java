package com.dreamteam.datavisualizator.common.util;

import com.dreamteam.datavisualizator.models.UserRequest;

public class UserRequestValidator {
    public static boolean isUserRequestValid(UserRequest userRequest){
        String firstName = userRequest.getFirstName();
        String lastName = userRequest.getLastName();
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        if (firstName == null||firstName.isEmpty()||firstName.toCharArray().length>20) return false;
        if (lastName == null||lastName.isEmpty()||lastName.toCharArray().length>20) return false;
        if (email == null||!email.matches("^.+@.+\\..+$")||email.length()>40) return false;
        if (password == null||password.length()<6||password.length()>20) return false;

        return true;
    }
}
