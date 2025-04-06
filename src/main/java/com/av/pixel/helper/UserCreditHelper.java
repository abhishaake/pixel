package com.av.pixel.helper;

import com.av.pixel.dao.UserCredit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCreditHelper {

    public UserCredit getDefaultUserCredit() {
        UserCredit userCredit = new UserCredit();
        userCredit.setAvailable(0L);
        userCredit.setUtilised(0L);
        return userCredit;
    }
}
