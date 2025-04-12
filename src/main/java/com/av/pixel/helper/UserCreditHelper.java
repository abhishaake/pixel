package com.av.pixel.helper;

import com.av.pixel.dao.UserCredit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCreditHelper {

    public Double getDefaultUserCredit() {
        return 0D;
    }
}
