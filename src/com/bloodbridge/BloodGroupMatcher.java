package com.bloodbridge;

import java.util.*;

public class BloodGroupMatcher {

    private static final Map<String, List<String>> COMPATIBILITY = new HashMap<>();

    static {
        COMPATIBILITY.put("O-", Arrays.asList("O-","O+","A-","A+","B-","B+","AB-","AB+")); // universal donor
        COMPATIBILITY.put("O+", Arrays.asList("O+","A+","B+","AB+"));
        COMPATIBILITY.put("A-", Arrays.asList("A-","A+","AB-","AB+"));
        COMPATIBILITY.put("A+", Arrays.asList("A+","AB+"));
        COMPATIBILITY.put("B-", Arrays.asList("B-","B+","AB-","AB+"));
        COMPATIBILITY.put("B+", Arrays.asList("B+","AB+"));
        COMPATIBILITY.put("AB-", Arrays.asList("AB-","AB+"));
        COMPATIBILITY.put("AB+", Arrays.asList("AB+")); // universal recipient
    }

    public static boolean isCompatible(String donorBg, String recipientBg) {
        if (donorBg == null || recipientBg == null) return false;
        donorBg = donorBg.trim().toUpperCase();
        recipientBg = recipientBg.trim().toUpperCase();
        List<String> allowed = COMPATIBILITY.get(donorBg);
        return allowed != null && allowed.contains(recipientBg);
    }
}
