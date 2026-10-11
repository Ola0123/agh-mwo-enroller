package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id) {
		return connector.getSession().get(Meeting.class, id);
	}

	public void save(Meeting meeting) {
		Transaction transaction =
				connector.getSession().beginTransaction();

		try {
			connector.getSession().persist(meeting);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void update(Meeting meeting) {
		Transaction transaction =
				connector.getSession().beginTransaction();

		try {
			connector.getSession().merge(meeting);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void delete(Meeting meeting) {
		Transaction transaction =
				connector.getSession().beginTransaction();

		try {
			connector.getSession().remove(meeting);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

}
