package com.base64.d;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;



import static org.junit.Assert.*;

public class StudyGuideCheckerTest {

    private StudyGuideChecker checker;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception{

        checker = new StudyGuideChecker();
        checker.checkStudyGuides();
    }


    @Test(expected = NoSuchFieldException.class)
    public void totalLinksGreaterThanZero() throws Exception{


        assertThat(checker.getTotalLinksFound(), is(not(0)));
    }
}