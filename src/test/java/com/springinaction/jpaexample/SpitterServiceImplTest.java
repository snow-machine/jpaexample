package com.springinaction.jpaexample;

import static org.junit.Assert.*;
import static org.springframework.test.jdbc.SimpleJdbcTestUtils.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:persistence-context.xml",
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml"
	})// 141231
public class SpitterServiceImplTest {
	@Autowired
	private SpitterService spitterService;
	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	private Spitter newSpitter;
	private Spitter oldSpitter;

	@Before
	public void setup() {

		newSpitter = new Spitter();
		newSpitter.setUsername("testuser");
		newSpitter.setPassword("password");
		newSpitter.setFullName("Michael McTest");

		oldSpitter = new Spitter();
		oldSpitter.setId(12345L);
		oldSpitter.setUsername("olduser");
		oldSpitter.setPassword("letmein");
		oldSpitter.setFullName("Bob O'Test");
	}

	@Test
	public void shouldAddSpitter() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "spitter"));
		spitterService.saveSpitter(newSpitter);
		spitterService.saveSpitter(oldSpitter);
		assertEquals(2, countRowsInTable(jdbcTemplate, "spitter"));
	}
}
