package com.icy.game.Models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.icy.game.Controller.Connection;
import com.icy.game.IcyGame;
import com.icy.game.Views.CreateScreen;
import com.icy.game.Views.JoinScreen;
import com.icy.game.Views.LobbyScreen;
import com.icy.game.Views.MenuScreen;
import com.icy.game.Views.SettingsScreen;
import com.icy.game.Views.TutorialScreen;

import org.json.JSONException;

public class Button {

    public Image img;

    public Button(String type, String... opt) {
        switch (type) {
            case "CREATELOBBY":
                img = new Image(new Texture("Buttons/CREATE.png"));
                break;
            case "JOINLOBBY":
                img = new Image(new Texture("Buttons/JOIN.png"));
                break;
            default:
                img = new Image(new Texture("Buttons/" + type + ".png"));
                break;
        }

        img.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                IcyGame.getInstance().getScreen().dispose();
                System.out.println(type + " button pressed");
                switch (type) {
                    case "JOIN":
                        IcyGame.getInstance().setScreen(new JoinScreen());
                        break;
                    case "CREATE":
                        IcyGame.getInstance().setScreen(new CreateScreen());
                        break;
                    case "HELP":
                        IcyGame.getInstance().setScreen(new TutorialScreen());
                        break;
                    case "SETTINGS":
                        IcyGame.getInstance().setScreen(new SettingsScreen());
                        break;
                    case "BACK":
                        IcyGame.getInstance().setScreen(new MenuScreen());
                        break;
                    case "CREATELOBBY":
                        try {
                            Connection.getInstance().createLobby(opt[0], opt[1]);
                        } catch (Exception e) {
                            System.out.println("Could not create a lobby: " + e);
                        }
                        break;
                    case "JOINLOBBY":
                        try {
                            Connection.getInstance().joinLobby(opt[0], opt[1]);
                        } catch (Exception e) {
                            System.out.println("Could not join a game: " + e);
                        }
                        break;
                    case "REMATCH":
                        try {
                            Player.getInstance().resetProperties();
                            Opponent.getInstance().resetProperties();
                            Connection.getInstance().rematch(Player.getInstance().getPlayerId(), Player.getInstance().getUsername(), Opponent.getInstance().getUsername(), Connection.getInstance().getRoomName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        IcyGame.getInstance().setScreen(new LobbyScreen(Player.getInstance().getPlayerId(), Player.getInstance().getUsername(), Opponent.getInstance().getUsername(), Connection.getInstance().getRoomName()));
                        break;
                    case "QUIT":
                        try {
                            String roomname = Connection.getInstance().getRoomName();
                            Connection.getInstance().leaveLobby(Player.getInstance().getPlayerId(), Player.getInstance().getUsername(), roomname);
                            Player.getInstance().resetIdentity();
                            Player.getInstance().resetProperties();
                            Opponent.getInstance().resetIdentity();
                            Opponent.getInstance().resetProperties();
                            Connection.getInstance().gameOver(roomname);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        IcyGame.getInstance().setScreen(new MenuScreen());
                }
                return false;
            }
        });
    }
}
