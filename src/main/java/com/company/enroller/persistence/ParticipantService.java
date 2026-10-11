package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll(
			String sortBy, String sortOrder, String key) {

		String hql = "FROM Participant WHERE login LIKE :key";

		if ("login".equalsIgnoreCase(sortBy)) {
			if ("DESC".equalsIgnoreCase(sortOrder)) {
				hql += " ORDER BY login DESC";
			} else {
				hql += " ORDER BY login ASC";
			}
		}

		Query<Participant> query = connector.getSession()
				.createQuery(hql, Participant.class);

		query.setParameter("key", "%" + key + "%");

		return query.list();
	}

	public Participant findByLogin(String login) {
		String hql = "FROM Participant WHERE login = :login";

		Query<Participant> query = connector.getSession()
				.createQuery(hql, Participant.class);

		query.setParameter("login", login);

		return query.uniqueResult();
	}

	public void save(Participant participant) {
		Transaction transaction =
				connector.getSession().beginTransaction();

		try {
			connector.getSession().persist(participant);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void delete(Participant participant) {
		Transaction transaction =
				connector.getSession().beginTransaction();

		try {
			connector.getSession().remove(participant);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void update(Participant participant) {
		Transaction transaction =
				connector.getSession().beginTransaction();

		try {
			connector.getSession().merge(participant);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

}
