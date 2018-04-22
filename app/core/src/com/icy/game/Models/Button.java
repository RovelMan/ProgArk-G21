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
                        IcyGame.getInstance().setScreen(JoinScreen.getInstance());
                        break;
                    case "CREATE":
                        IcyGame.getInstance().setScreen(CreateScreen.getInstance());
                        break;
                    case "HELP":
                        IcyGame.getInstance().setScreen(TutorialScreen.getInstance());
                        break;
                    case "BACK":
                        IcyGame.getInstance().setScreen(MenuScreen.getInstance());
                        break;
                    case "CREATELOBBY":
                        try {
                            Player.getInstance().setUsername(opt[0]);
                            Player.getInstance().setPlayerId(0);
                            Opponent.getInstance().setPlayerId(1);
                            Connection.getInstance().createLobby(opt[0], opt[1]);
                        } catch (Exception e) {
                            System.out.println("Could not create a lobby: " + e);
                        }
                        break;
                    case "JOINLOBBY":
                        try {
                            Player.getInstance().setUsername(opt[0]);
                            Player.getInstance().setPlayerId(1);
                            Opponent.getInstance().setPlayerId(0);
                            Connection.getInstance().joinLobby(opt[0], opt[1]);
                        } catch (Exception e) {
                            System.out.println("Could not join a game: " + e);
                        }
                        break;
                    case "REMATCH":
                        try {
                            Connection.getInstance().rematch(Connection.getInstance().getRoomName(),Player.getInstance().getPlayerId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        IcyGame.getInstance().setScreen(LobbyScreen.getInstance());
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
                        IcyGame.getInstance().setScreen(MenuScreen.getInstance());
                }
                return false;
            }
        });
    }
}
