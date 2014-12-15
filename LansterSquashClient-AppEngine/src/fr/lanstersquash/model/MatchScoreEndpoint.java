package fr.lanstersquash.model;

import fr.lanstersquash.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "matchscoreendpoint", namespace = @ApiNamespace(ownerDomain = "lanstersquash.fr", ownerName = "lanstersquash.fr", packagePath = "model"))
public class MatchScoreEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listMatchScore")
	public CollectionResponse<MatchScore> listMatchScore(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<MatchScore> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from MatchScore as MatchScore");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<MatchScore>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (MatchScore obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<MatchScore> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getMatchScore")
	public MatchScore getMatchScore(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		MatchScore matchscore = null;
		try {
			matchscore = mgr.find(MatchScore.class, id);
		} finally {
			mgr.close();
		}
		return matchscore;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param matchscore the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertMatchScore")
	public MatchScore insertMatchScore(MatchScore matchscore) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsMatchScore(matchscore)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(matchscore);
		} finally {
			mgr.close();
		}
		return matchscore;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param matchscore the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateMatchScore")
	public MatchScore updateMatchScore(MatchScore matchscore) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsMatchScore(matchscore)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(matchscore);
		} finally {
			mgr.close();
		}
		return matchscore;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeMatchScore")
	public void removeMatchScore(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			MatchScore matchscore = mgr.find(MatchScore.class, id);
			mgr.remove(matchscore);
		} finally {
			mgr.close();
		}
	}

	private boolean containsMatchScore(MatchScore matchscore) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			MatchScore item = mgr.find(MatchScore.class, matchscore.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
