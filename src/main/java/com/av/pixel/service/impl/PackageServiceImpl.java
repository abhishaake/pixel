package com.av.pixel.service.impl;

import com.av.pixel.dao.Audit;
import com.av.pixel.dao.Packages;
import com.av.pixel.exception.Error;
import com.av.pixel.repository.AuditRepository;
import com.av.pixel.repository.PackageRepository;
import com.av.pixel.service.PackageService;
import com.av.pixel.service.UserCreditService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class PackageServiceImpl implements PackageService {

    PackageRepository packageRepository;
    UserCreditService userCreditService;

    AuditRepository auditRepository;

    @Override
    public void handleAdPayment (String userCode, String adIdentifier, String adTxnId, String timestamp) {
        if (StringUtils.isEmpty(userCode)) {
            throw new Error("user code empty");
        }
        adIdentifier = StringUtils.isEmpty(adIdentifier) ? "PIXEL_AD_DEFAULT" : adIdentifier;

        Packages packageInfo = packageRepository.getByPackageIdAndDeletedFalse(adIdentifier);

        if (Objects.isNull(packageInfo)) {
            packageInfo = packageRepository.getByPackageIdAndDeletedFalse("PIXEL_AD_DEFAULT");
            if (Objects.isNull(packageInfo)) {
                auditFailedTxn(userCode, adIdentifier, adTxnId, timestamp);
                throw new Error("Invalid package");
            }
        }

        userCreditService.creditUserCredits(userCode, packageInfo.getCredits(), "AD_CREDIT", null, packageInfo.getPackageId(), "AD");
    }

    @Override
    public void handleGooglePayment (String userCode, String packageId) {
        if (StringUtils.isEmpty(userCode)) {
            throw new Error("user code empty");
        }
        packageId = StringUtils.isEmpty(packageId) ? "PIXEL_GOOGLE_PAYMENT_DEFAULT" : packageId;

        Packages packageInfo = packageRepository.getByPackageIdAndDeletedFalse(packageId);

        if (Objects.isNull(packageInfo)) {
            packageInfo = packageRepository.getByPackageIdAndDeletedFalse("PIXEL_GOOGLE_PAYMENT_DEFAULT");
            if (Objects.isNull(packageInfo)) {
                auditFailedTxn(userCode, packageId, null, null);
                throw new Error("Invalid package");
            }
        }
        userCreditService.creditUserCredits(userCode, packageInfo.getCredits(), "PAYMENTS_CREDIT", null, packageInfo.getPackageId(), "GOOGLE_PAYMENTS");
    }

    private void auditFailedTxn (String userCode, String packageId, String adTxnId, String timestamp) {
        Audit audit = new Audit();
        audit.setUserCode(userCode);
        audit.setPackageId(packageId);
        audit.setAdTxnId(adTxnId);
        audit.setTimestamp(timestamp);
        auditRepository.save(audit);
    }
}
