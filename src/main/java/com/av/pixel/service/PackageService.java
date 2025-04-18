package com.av.pixel.service;

public interface PackageService {

    void handleAdPayment (String userCode, String adIdentifier);

    void handleGooglePayment (String userCode, String packageId);
}
