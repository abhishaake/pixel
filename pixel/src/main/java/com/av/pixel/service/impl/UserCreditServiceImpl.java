package com.av.pixel.service.impl;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.helper.UserCreditHelper;
import com.av.pixel.repository.UserCreditRepository;
import com.av.pixel.service.UserCreditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserCreditServiceImpl implements UserCreditService {

    UserCreditRepository userCreditRepository;
    UserCreditHelper userCreditHelper;

    @Override
    public UserCredit createNewUserCredit (User user) {

        UserCredit userCredit = userCreditRepository.findByUserAndDeletedFalse(user);

        if (Objects.nonNull(userCredit)) {
            return userCredit;
        }

        userCredit = userCreditHelper.getDefaultUserCredit();
        userCredit.setUser(user);

        userCredit = userCreditRepository.save(userCredit);

        return userCredit;
    }
}
