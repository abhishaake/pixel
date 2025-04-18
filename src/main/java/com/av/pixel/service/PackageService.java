package com.av.pixel.service;

public interface PackageService {

    void handleAdPayment (String userCode, String adIdentifier, String adTxnId, String timestamp);

    void handleGooglePayment (String userCode, String packageId);
}
