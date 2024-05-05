package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.PasswordResetTokenDAO;
import com.zerocoder.devsearch.entity.PasswordResetToken;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenDAO passwordResetTokenDAO;
    @Override
    @Transactional
    public void savePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenDAO.savePasswordResetToken(passwordResetToken);
    }

    @Override
    public PasswordResetToken getPasswordResetTokenByToken(String token) {
        try
        {
            return passwordResetTokenDAO.getPasswordResetTokenByToken(token);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    @Transactional
    public void deletePasswordResetToken(Long id) {
        passwordResetTokenDAO.deletePasswordResetToken(id);
    }

    @Override
    @Transactional
    public void deletePasswordResetTokenByUser(User user) {
        passwordResetTokenDAO.deletePasswordResetTokenByUser(user);
    }

    @Override
    @Transactional
    public PasswordResetToken createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenDAO.savePasswordResetToken(myToken);
        return myToken;
    }

    @Override
    public String validatePasswordResetToken(long id, String token) {
        try
        {
            PasswordResetToken passToken = passwordResetTokenDAO.getPasswordResetTokenByToken(token);
            if ((passToken == null) || (passToken.getUser().getUserId() != id)) {
                return "invalidToken";
            }

            Calendar cal = Calendar.getInstance();
            if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                return "expired";
            }

            return "valid";
        }
        catch (Exception e) {
            return "invalidToken";
        }
    }
}
