package com.base64.d;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class KnownFalsePositives {

    public static final String BLACK_LIST_PATH = "https://content.infinitecampus.com/sis/latest/";

    public static final Set<String> BLACK_LIST = Stream.of(
            "simulation/send-a-behavior-message-sc-01-28-01",
            "simulation/schedule-attendance-message-sc-01-24-02",
            "simulation/schedule-a-behavior-message-sc-01-27-02",
            "simulation/create-a-behavior-message-template-sc-01-27-01",
            "simulation/view-individual-student-attendance",
            "simulation/cancel-a-user-created-future-dated-message-sc-03-08-04",
            "simulation/generate-report-of-behavior-removals"
    ).map(blackList -> BLACK_LIST_PATH + blackList)
            .collect(Collectors.toSet());




//    public KnownFalsePositives(Set<String> knownFalsePositives) {
//        this.knownFalsePositives = knownFalsePositives;
//    }
//
//    public Set<String> getKnownFalsePositives() {
//        return knownFalsePositives;
//    }
//
//    public void setKnownFalsePositives(Set<String> knownFalsePositives) {
//        this.knownFalsePositives = knownFalsePositives;
//    }
}
