package com.showcase.backend;

import com.showcase.backend.util.SpringProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(SpringProfiles.TEST)
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
