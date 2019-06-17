package com.androidsample.interfaces;

public interface NetworkStatus {
    /**
     * Receive the network change status
     *
     * @param isOnline
     */
    void receiveNetworkStatus(Boolean isOnline);
}
