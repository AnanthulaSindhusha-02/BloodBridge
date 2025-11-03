package com.bloodbridge;

import java.sql.Timestamp;

public class BloodRequest {
    private int requestId;
    private int recipientId;
    private int donorId;
    private String status;
    private Timestamp requestTime;

    public BloodRequest(int requestId, int recipientId, int donorId, String status, Timestamp requestTime) {
        this.requestId = requestId;
        this.recipientId = recipientId;
        this.donorId = donorId;
        this.status = status;
        this.requestTime = requestTime;
    }

    public int getRequestId() { return requestId; }
    public int getRecipientId() { return recipientId; }
    public int getDonorId() { return donorId; }
    public String getStatus() { return status; }
    public Timestamp getRequestTime() { return requestTime; }

    @Override
    public String toString() {
        return requestId + " | recipientId:" + recipientId + " | donorId:" + donorId + " | " + status + " | " + requestTime;
    }
}
