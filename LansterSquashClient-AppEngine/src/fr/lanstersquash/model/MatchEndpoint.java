package fr.lanstersquash.model;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import fr.lanstersquash.EMF;

@Api(name = "matchendpoint", namespace = @ApiNamespace(ownerDomain = "lanstersquash.fr", ownerName = "lanstersquash.fr", packagePath = "model"))
public class MatchEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listMatch")
	public CollectionResponse<Match> listMatch(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Match> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Match as Match");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (Match obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Match> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 *
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getMatch")
	public Match getMatch(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Match match = null;
		try {
			match = mgr.find(Match.class, id);
		} finally {
			mgr.close();
		}
		return match;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 *
	 * @param match
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertMatch")
	public Match insertMatch(Match match) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(match);
		} finally {
			mgr.close();
		}
		return match;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 *
	 * @param match
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateMatch")
	public Match updateMatch(Match match) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsMatch(match)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(match);
		} finally {
			mgr.close();
		}
		return match;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 *
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeMatch")
	public void removeMatch(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Match match = mgr.find(Match.class, id);
			mgr.remove(match);
		} finally {
			mgr.close();
		}
	}

	private boolean containsMatch(Match match) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Match item = mgr.find(Match.class, match.getKey());
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
