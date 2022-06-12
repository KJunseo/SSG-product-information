package ssg.product_information.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.BeforeEach;

import ssg.product_information.common.DatabaseCleaner;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class IntegrationTest {

    @Autowired
    private DatabaseCleaner cleaner;

    @BeforeEach
    void init() {
        cleaner.execute();
    }
}
