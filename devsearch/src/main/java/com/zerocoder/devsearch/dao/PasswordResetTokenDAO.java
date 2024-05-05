package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.PasswordResetToken;
import com.zerocoder.devsearch.entity.User;

public interface PasswordResetTokenDAO {
    void savePasswordResetToken(PasswordResetToken passwordResetToken);
    PasswordResetToken getPasswordResetTokenByToken(String token);
    void deletePasswordResetToken(Long id);
    void deletePasswordResetTokenByUser(User user);
}