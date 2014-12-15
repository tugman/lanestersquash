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

@Api(name = "gameendpoint", namespace = @ApiNamespace(ownerDomain = "lanstersquash.fr", ownerName = "lanstersquash.fr", packagePath = "model"))
public class GameEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listGame")
	public CollectionResponse<Game> listGame(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Game> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Game as Game");
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
			for (Game obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Game> builder().setItems(execute)
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
	@ApiMethod(name = "getGame")
	public Game getGame(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Game game = null;
		try {
			game = mgr.find(Game.class, id);
		} finally {
			mgr.close();
		}
		return game;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 *
	 * @param game
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertGame")
	public Game insertGame(Game game) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(game);
		} finally {
			mgr.close();
		}
		return game;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 *
	 * @param game
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateGame")
	public Game updateGame(Game game) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsGame(game)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(game);
		} finally {
			mgr.close();
		}
		return game;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 *
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeGame")
	public void removeGame(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Game game = mgr.find(Game.class, id);
			mgr.remove(game);
		} finally {
			mgr.close();
		}
	}

	private boolean containsGame(Game game) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Game item = mgr.find(Game.class, game.getKey());
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
