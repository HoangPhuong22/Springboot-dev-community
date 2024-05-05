package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.dao.PasswordResetTokenDAO;
import com.zerocoder.devsearch.entity.PasswordResetToken;
import com.zerocoder.devsearch.entity.User;

public interface PasswordResetTokenService {
    void savePasswordResetToken(PasswordResetToken passwordResetToken);
    PasswordResetToken getPasswordResetTokenByToken(String token);
    void deletePasswordResetToken(Long id);
    void deletePasswordResetTokenByUser(User user);
    PasswordResetToken createPasswordResetTokenForUser(User user, String token);
    String validatePasswordResetToken(long id, String token);
}
