package com.av.pixel.service;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.dto.UserCreditDTO;

public interface UserCreditService {

    UserCredit createNewUserCredit(User user);

    UserCredit createNewUserCredit(String userCode);

    UserCreditDTO getUserCredit(User user);

    UserCreditDTO getUserCredit(String userCode);

    UserCreditDTO debitUserCredit (String userCode, Double used, String orderType, String source);

    UserCreditDTO creditUserCredits (String userCode, Double credits, String orderType, String refId, String packageId, String source);
}
