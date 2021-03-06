package org.dimigo.vaiohazard.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import org.dimigo.vaiohazard.CustomActions;

/**
 * Created by YuTack on 2015-11-11.
 * in this game actors moves to vertical of horizontal line,
 * define several game animations
 */
public class VaioActor extends Actor {
    public enum MovingState {
        walking,
        walkingOver,
        wating
    }
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrame;
    private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    protected int FRAME_COLS, FRAME_ROWS;
    protected String image;
    private float stateTime;

    //dot per second
    protected int walkSpeed = 80; //default
    protected MovingState moveState = MovingState.wating;


    public VaioActor() {
        super();
        this.setOrigin(getWidth()/2, getHeight()/2);
    }

    public VaioActor(int walkSpeed) {
        super();
        this.walkSpeed = walkSpeed;
        this.setOrigin(getWidth()/2, getHeight()/2);
    }

    public VaioActor(int walkSpeed, String imgPath) {

    }
    //
    protected void setAnimation(String image, int cols, int rows) {
        setBounds(0,0,100,100);
        this.image = image;
        this.FRAME_COLS = cols;
        this.FRAME_ROWS = rows;

        walkSheet = new Texture(Gdx.files.internal("resources/Actor/" + image));
        TextureRegion[][] temp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        walkFrame = new TextureRegion[FRAME_COLS*FRAME_ROWS-1];

        int p = 0;
        for(int i=0; i<FRAME_ROWS; i++) {
            for(int j=0; j<FRAME_COLS; j++) {
                if(i==0 && j ==0) continue;
                walkFrame[p++] = temp[i][j];
            }
        }

        walkAnimation = new Animation(0.4f, walkFrame);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    public void notifyWalkingOver() {
        moveState = MovingState.walkingOver;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    //걸어가는 함수임. 방향 지정해 주면 알아서 갑니다
    public void walkTo(int dotX, int dotY, boolean goXFirst) {

        assert((moveState == MovingState.wating));

        MoveByAction toX = new MoveByAction();

        toX.setAmount(dotX - getX(), 0);
        toX.setDuration(Math.abs(dotX - getX()) / walkSpeed);

        MoveByAction toY = new MoveByAction();

        toY.setAmount(0, dotY - getY());
        toY.setDuration(Math.abs(dotY - getY()) / walkSpeed);

        SequenceAction seq = null;

        if(goXFirst)
            seq = new SequenceAction(toX, toY);
        else
            seq = new SequenceAction(toY, toX);

        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                notifyWalkingOver();
                return true;
            }
        });

        moveState = MovingState.walking;

        this.addAction(seq);
    }

    public static int FOREVER  = -1;

    //흔들흔들~
    public void twitch(int count) {

        this.setOrigin(getWidth()/2, getHeight()/2);

        RepeatAction repeat = new RepeatAction();
        repeat.setAction(CustomActions.twinkle());
        if(count > 0)
            repeat.setCount(count);
        else if(count == -1)
            repeat.setCount(RepeatAction.FOREVER);
        else
            assert true : "wtf";

        this.addAction(repeat);
    }

    /*public void walkAround(int x1, int y1, int x2, int y2) {

    }*/

}
