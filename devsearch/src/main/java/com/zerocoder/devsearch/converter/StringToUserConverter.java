package com.zerocoder.devsearch.converter;

import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserConverter implements Converter<String, User> {
    @Autowired
    private UserService userService;
    @Override
    public User convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return userService.getUserById(Long.parseLong(source));
    }
}
