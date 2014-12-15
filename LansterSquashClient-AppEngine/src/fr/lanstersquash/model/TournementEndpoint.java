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

@Api(name = "tournementendpoint", namespace = @ApiNamespace(ownerDomain = "lanstersquash.fr", ownerName = "lanstersquash.fr", packagePath = "model"))
public class TournementEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTournement")
	public CollectionResponse<Tournement> listTournement(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Tournement> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from Tournement as Tournement");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Tournement>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Tournement obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Tournement> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTournement")
	public Tournement getTournement(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Tournement tournement = null;
		try {
			tournement = mgr.find(Tournement.class, id);
		} finally {
			mgr.close();
		}
		return tournement;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param tournement the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertTournement")
	public Tournement insertTournement(Tournement tournement) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsTournement(tournement)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(tournement);
		} finally {
			mgr.close();
		}
		return tournement;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param tournement the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTournement")
	public Tournement updateTournement(Tournement tournement) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsTournement(tournement)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(tournement);
		} finally {
			mgr.close();
		}
		return tournement;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTournement")
	public void removeTournement(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Tournement tournement = mgr.find(Tournement.class, id);
			mgr.remove(tournement);
		} finally {
			mgr.close();
		}
	}

	private boolean containsTournement(Tournement tournement) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Tournement item = mgr.find(Tournement.class, tournement.getKey());
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
