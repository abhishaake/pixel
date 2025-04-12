package com.av.pixel.service.impl;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.dto.UserCreditDTO;
import com.av.pixel.helper.UserCreditHelper;
import com.av.pixel.mapper.UserCreditMap;
import com.av.pixel.repository.UserCreditRepository;
import com.av.pixel.service.UserCreditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class UserCreditServiceImpl implements UserCreditService {

    UserCreditRepository userCreditRepository;
    UserCreditHelper userCreditHelper;

    @Override
    public UserCredit createNewUserCredit (User user) {

        UserCredit userCredit = userCreditRepository.findByUserCodeAndDeletedFalse(user.getCode()).orElse(null);

        if (Objects.nonNull(userCredit)) {
            return userCredit;
        }

        userCredit = new UserCredit()
                .setAvailable(userCreditHelper.getDefaultUserCredit())
                .setUserCode(user.getCode())
                .setUtilised(0D);

        return userCreditRepository.save(userCredit);
    }

    @Override
    public UserCredit createNewUserCredit (String userCode) {

        UserCredit userCredit = userCreditRepository.findByUserCodeAndDeletedFalse(userCode).orElse(null);

        if (Objects.nonNull(userCredit)) {
            return userCredit;
        }

        userCredit = new UserCredit()
                .setAvailable(userCreditHelper.getDefaultUserCredit())
                .setUserCode(userCode)
                .setUtilised(0D);

        return userCreditRepository.save(userCredit);
    }

    @Override
    public UserCreditDTO getUserCredit (User user) {
        UserCredit userCredit = userCreditRepository.findByUserCodeAndDeletedFalse(user.getCode()).orElse(null);

        return UserCreditMap.userCreditDTO(userCredit);
    }

    @Override
    public UserCreditDTO getUserCredit (String userCode) {
        UserCredit userCredit = userCreditRepository.findByUserCodeAndDeletedFalse(userCode).orElse(null);

        return UserCreditMap.userCreditDTO(userCredit);
    }

    @Override
    public UserCreditDTO updateUserCredit(String userCode, Double used) {
        UserCredit userCredit = userCreditRepository.findByUserCodeAndDeletedFalse(userCode).orElse(null);

        assert userCredit != null;
        Double available = userCredit.getAvailable();
        available -= used;
        userCredit.setAvailable(available);

        Double utilised = userCredit.getUtilised();
        utilised += used;
        userCredit.setUtilised(utilised);

        return UserCreditMap.userCreditDTO(userCreditRepository.save(userCredit));

    }
}
