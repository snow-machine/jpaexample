package com.springinaction.jpaexample;

import static org.junit.Assert.*;
import static org.springframework.test.jdbc.SimpleJdbcTestUtils.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:persistence-context.xml",
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml"
})
@TransactionConfiguration(transactionManager="txMgr", defaultRollback=true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class SpitterDaoTest {

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SpitterDao dao;
	
	@After
	public void cleanup() {
		deleteFromTables(jdbcTemplate, "spitter");
	}

	@Test
	public void shouldCreateRowAndSetIds() {
		assertEquals(0, countRowsInTable(jdbcTemplate, "spitter"));
		insertSpitter("username", "password", "fullName");
		assertEquals(1, countRowsInTable(jdbcTemplate, "spitter"));
		insertSpitter("username", "password", "fullName");
		assertEquals(2, countRowsInTable(jdbcTemplate, "spitter"));
	}
	
	@Test
	public void shouldBeAbleToFindInsertedSpitter() {
		Spitter spitterIn = insertSpitter("username", "password", "fullName");
		Spitter spitterOut = dao.getSpitterById(spitterIn.getId());
		assertEquals(spitterIn, spitterOut);
	}
	
	private Spitter insertSpitter(String username, String password, String fullName) {
		Spitter spitter = new Spitter();
		spitter.setUsername(username);
		spitter.setPassword(password);
		spitter.setFullName(fullName);
		assertNull(spitter.getId());
		dao.addSpitter(spitter);
		assertNotNull(spitter.getId());
		return spitter;
	}
}
