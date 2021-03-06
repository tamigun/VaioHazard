package org.dimigo.vaiohazard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.dimigo.library.FontGenerator;
import org.dimigo.vaiohazard.Object.MarioActor;
import org.dimigo.vaiohazard.Object.VaioActor;

/**
 * Created by juwoong on 15. 11. 10..
 */
public class LoadingScreen extends ScreenAdapter {
    VaioHazardGame currentGame;
    Stage stage;
    MarioActor mario;
    SpriteBatch batch;

    FontGenerator generater;

    float timer = 0;
    String loading;

    public LoadingScreen(VaioHazardGame game) {
        this.currentGame = game;
        init();

    }

    private void init() {
        stage = new Stage();
        batch = new SpriteBatch();

        loading = "Loading";

        mario = new MarioActor();
        mario.setPosition(0, 30);

        mario.twitch(VaioActor.FOREVER);

        generater = new FontGenerator();

        stage.addActor(mario);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        stage.act(delta);
        stage.draw();


        if(timer >= 1.0f) {
            loading += ".";
            timer = 0;
        }
        if(loading.length() >= 12) {
            loading = "Loading";
        }


        batch.begin();
        generater.drawBitmapFont(batch, 60, loading, Color.BLACK, 100, 100);
        generater.drawBitmapFont(batch, 30, "Developed By 박유택, 배주웅", Color.BLACK, 70, 40);
        batch.end();

        generater.releaseAll();
    }
}
