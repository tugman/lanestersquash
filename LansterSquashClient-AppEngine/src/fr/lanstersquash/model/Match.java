package fr.lanstersquash.model;

import java.sql.Time;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.datanucleus.store.types.sco.simple.Date;

import com.google.appengine.api.datastore.Key;

@Entity
public class Match {
	/*
	 * Autogenerated primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Player player1;
	private Player player2;
	private Player arbitre;
	private Player marqueur;
	private Player winner;
	private int numberOfWinningSet = 2;
	private LinkedList<Game> games = new LinkedList<Game>();
	private Player startPlayer;
	private String matchStatus = "";
	private String matchName = "";
	private Date matchDate;
	private Long startTime;
	private Long stopTime;
	private String levelOfMatch;
	private Tournement tournement;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public int getNumberOfWinningSet() {
		return numberOfWinningSet;
	}

	public void setNumberOfWinningSet(int numberOfWinningSet) {
		this.numberOfWinningSet = numberOfWinningSet;
	}

	public Player getStartPlayer() {
		return startPlayer;
	}

	public void setStartPlayer(Player startPlayer) {
		this.startPlayer = startPlayer;
	}

	public Player getArbitre() {
		return arbitre;
	}

	public void setArbitre(Player arbitre) {
		this.arbitre = arbitre;
	}

	public Player getMarqueur() {
		return marqueur;
	}

	public void setMarqueur(Player marqueur) {
		this.marqueur = marqueur;
	}

	public LinkedList<Game> getGames() {
		return games;
	}

	public void setGames(LinkedList<Game> games) {
		this.games = games;
	}

	public String getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getStopTime() {
		return stopTime;
	}

	public void setStopTime(Long stopTime) {
		this.stopTime = stopTime;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public String getLevelOfMatch() {
		return levelOfMatch;
	}

	public void setLevelOfMatch(String levelOfMatch) {
		this.levelOfMatch = levelOfMatch;
	}

	public Tournement getTournement() {
		return tournement;
	}

	public void setTournement(Tournement tournement) {
		this.tournement = tournement;
	}

}