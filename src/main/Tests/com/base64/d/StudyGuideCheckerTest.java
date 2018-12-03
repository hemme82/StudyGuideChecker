package com.base64.d;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudyGuideCheckerTest {

    private StudyGuideChecker checker;

    @Before
    public void setUp() {
        checker = new StudyGuideChecker();
    }


    @Test
    public void totalLinksGreaterThanZero() {
        checker.checkStudyGuides();



        //assertTrue(checker.getTotalLinksFound > 0);
    }
}