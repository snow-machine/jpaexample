package com.springinaction.jpaexample;

import static java.lang.Math.min;
import static java.util.Collections.reverse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class SpitterServiceImpl implements SpitterService {

	@Autowired
	private SpitterDao spitterDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	// <start id="java_addSpittle" />
	public void saveSpittle(final Spittle spittle) {
		transactionTemplate.execute(new TransactionCallback<Void>() {
			public Void doInTransaction(TransactionStatus txStatus) {
				try {
					// spitterDao.saveSpittle(spittle);
				} catch (RuntimeException e) {
					txStatus.setRollbackOnly();
					throw e;
				}
				return null;
			}
		});
	}

	// <end id="java_addSpittle" />

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Spittle> getRecentSpittles(int count) {
		List<Spittle> recentSpittles = spitterDao.getRecentSpittle();

		reverse(recentSpittles);

		return recentSpittles.subList(0, min(49, recentSpittles.size()));
	}

	public void saveSpitter(final Spitter spitter) {
		transactionTemplate.execute(new TransactionCallback<Void>() {
			public Void doInTransaction(TransactionStatus txStatus) {
				try {
					if (spitter.getId() == null) {
						spitterDao.addSpitter(spitter);
					} else {
						spitterDao.saveSpitter(spitter);
					}					
				} catch (RuntimeException e) {
					txStatus.setRollbackOnly();
					throw e;					
				}
				return null;
			}
		});
	}

	public Spitter getSpitter(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Spitter getSpitter(String username) {
		return spitterDao.getSpitterByUsername(username);
	}

	public void startFollowing(Spitter follower, Spitter followee) {
		// TODO Auto-generated method stub

	}

	public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
		return spitterDao.getSpittlesForSpitter(spitter);
	}

	public void deleteSpittle(long id) {
		spitterDao.deleteSpittle(id);
	}

	public Spittle getSpittleById(long id) {
		return spitterDao.getSpittleById(id);
	}

	public List<Spittle> getSpittlesForSpitter(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Spitter> getAllSpitters() {
		return spitterDao.findAllSpitters();
	}
}
