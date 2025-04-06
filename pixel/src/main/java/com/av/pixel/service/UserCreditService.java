package com.av.pixel.service;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.dto.UserCreditDTO;

public interface UserCreditService {

    UserCredit createNewUserCredit(User user);
}
