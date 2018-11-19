package com.base64.d;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllStudyGuideLinks {

    public static final String STUDY_GUIDE_PATH = "https://content.infinitecampus.com/sis/latest/study-guide/";



    public static final Set<String> ALL_STUDY_GUIDES = Stream.of(
            "academic-planner-system-set-up",
            "academic-planner-use-and-management-",
            "ad-hoc-filters-letters-and-data-viewer",
            "ad-hoc-functions-and-logical-expressions",
            "attendance",
            "behavior-admin-set-up",
            "behavior-data-management-and-reporting",
            "behavior-messages-and-letters",
            "calendar-rights-user-groups",
            "campus-instruction-part-1-the-fundamentals",
            "campus-instruction-part-2-grade-book-basics",
            "campus-instruction-part-3-advanced-grade-book-and-posting-grades",
            "campus-instruction-part-4-campus-learning",
            "census---new-personfamily-set-up",
            "census---personhousehold-maintenance",
            "census-reports",
            "flags-and-programs",
            "grade-submission-process",
            "grading-setup",
            "health-module-system-setup",
            "health-module-view-and-manage-student-health-information",
            "messenger-setup-contacts-and-reports",
            "messenger-for-the-end-user",
            "schedule-wizard-mass-schedule-students",
            "tool-rights-user-groups",
            "user-account-creation-maintenance-and-reporting",
            "walk-in-scheduler-complete-or-change-a-students-schedule"
            //combining first and second part of the url.
    ).map(studyGuideName -> STUDY_GUIDE_PATH + studyGuideName)
            .collect(Collectors.toSet());






}
