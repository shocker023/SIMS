package controller;

import java.util.TimerTask;

import javafx.application.Platform;
import model.Match;
import model.Player;
import model.Team;
import model.events.Assist;
import model.events.Block;
import model.events.DefensiveFoul;
import model.events.DefensiveRebound;
import model.events.OffensiveFoul;
import model.events.OffensiveRebound;
import model.events.OnePtShot;
import model.events.Steal;
import model.events.ThreePtShot;
import model.events.Turnover;
import model.events.TwoPtShot;
import view.controller.ViewController;

public class MainController {
	private ViewController view;
	private static MainController instance = null;
	private Match match;
	private String tempTeam, tempNumber, tempAction;
	private model.Timer t;
	private java.util.Timer timer;

	private MainController() {
		match = new Match();
		t = new model.Timer();
	}
	
	public void start() {
		t.start();
		timer = new java.util.Timer();
	    timer.schedule(new TimerTask(){
	        public void run(){
	        	Platform.runLater(() ->{
	        		updateTime();
	        	});
	        }
	    },0,  100 );
	}
	
	public void pause() {
		t.pause();
		timer.cancel();
	}
	
	public void updateTime() {
		t.updateTime();
		view.setTime(String.valueOf(t.getTime() / 60) + " : " + String.format("%02d", t.getTime() % 60));
	}

	public void setViewController(ViewController _vc) {
		view = _vc;
	}

	public static MainController getInstance() {
		if (instance == null)
			instance = new MainController();
		return instance;
	}

	public void shotOccured(String team, String playersNumber, String action) {
		tempTeam = team;
		tempNumber = playersNumber;
		tempAction = action;
	}
	
	public Player getPlayer(String team, int playersNumber) {
		return match.getTeam(team).getPlayer(playersNumber);
	}

	public void eventOccured(String team, String playersNumber, String action) {
		switch (action) {
		case "DEFENSIVEFOUL":
			match.addEvent(new DefensiveFoul(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "OFFENSIVEFOUL":
			match.addEvent(new OffensiveFoul(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "DEFENSIVEREBOUND":
			match.addEvent(new DefensiveRebound(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "OFFENSIVEREBOUND":
			match.addEvent(new OffensiveRebound(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "STEAL":
			match.addEvent(new Steal(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "ASSIST":
			match.addEvent(new Assist(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "TURNOVER":
			match.addEvent(new Turnover(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		case "BLOCK":
			match.addEvent(new Block(getPlayer(team, Integer.parseInt(playersNumber))));
			break;
		}
		System.out.println(team + playersNumber + action);
	}

	public void eventOccured(String scored, int x, int y) {
		switch (tempAction) {
		case "1 POINTSHOT":
			match.addEvent(new OnePtShot(getPlayer(tempTeam, Integer.parseInt(tempNumber)), scored, x, y));
			break;
		case "2 POINTSHOT":
			match.addEvent(new TwoPtShot(getPlayer(tempTeam, Integer.parseInt(tempNumber)), scored, x, y));
			break;
		case "3 POINTSHOT":
			match.addEvent(new ThreePtShot(getPlayer(tempTeam, Integer.parseInt(tempNumber)), scored, x, y));
			break;
		}
		System.out.println(tempTeam + tempNumber + "scored" + x + y);
	}

}
