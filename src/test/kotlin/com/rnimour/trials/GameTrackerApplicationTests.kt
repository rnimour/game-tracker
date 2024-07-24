package com.rnimour.trials

import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GameTrackerApplicationTests {

    @Test
    fun contextLoads() = assertThat("If this test fails, something went seriously wrong", true)
}
