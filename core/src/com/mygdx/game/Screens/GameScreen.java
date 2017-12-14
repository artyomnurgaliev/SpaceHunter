package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by artum on 12.12.2017.
 */

public class GameScreen implements Screen, InputProcessor {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    static final float SCALE = 0.005f;

    private int maxwidthofship =6;
    private int maxheightofship =5;


    TextureAtlas textureAtlas;
    SpriteBatch batch;
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    OrthographicCamera camera;
    ExtendViewport viewport;

    World world;
    Box2DDebugRenderer debugRenderer;
    PhysicsShapeCache physicsBodies;
    float accumulator = 0;
    Body upground;
    Body downground;
    Body leftground;
    Body rightground;
    static final int COUNT = 60;

    Body[][] Bodies = new Body[maxwidthofship][maxheightofship];
    String[][] namesofBodies = new String[maxwidthofship][maxheightofship];
    int [][] player1ship = new int[maxwidthofship][maxheightofship];

    int firstplayerturbine1_I;
    int firstplayerturbine1_J;
    int firstplayerturbine2_I;
    int firstplayerturbine2_J;


    int [][] player2ship = new int[maxwidthofship][maxheightofship];

    Body[] meteorBodies = new Body[COUNT];
    String[] meteornames = new String[COUNT];


    public GameScreen(
            //int [][] player1ship, int [][] player2ship
            ){
        //this.player1ship = player1ship;
        //this.player2ship = player2ship;
        player1ship[0] = new int[]{0, 0, 0, 0, 0};
        player1ship[1] = new int[]{0, 6, 1, 6, 0};
        player1ship[2] = new int[]{0, 7, 1, 7, 0};
        player1ship[3] = new int[]{0, 5, 4, 5, 0};
        player1ship[4] = new int[]{0, 0, 0, 0, 0};
        player1ship[5] = new int[]{0, 0, 0, 0, 0};
        //player1ship[0] = new int[]{1, 1, 1, 1, 1};
        //player1ship[1] = new int[]{1, 1, 1, 1, 1};
        //player1ship[2] = new int[]{1, 1, 1, 1, 1};
        //player1ship[3] = new int[]{1, 1, 1, 1, 1};
        //player1ship[4] = new int[]{1, 1, 1, 1, 1};
        //player1ship[5] = new int[]{1, 1, 1, 1, 1};

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(50, 50, camera);

        textureAtlas = new TextureAtlas("sprites.txt");
        addSprites();

        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        physicsBodies = new PhysicsShapeCache("physics.xml");
        generate();
        generateMeteors();

        debugRenderer = new Box2DDebugRenderer();
    }
    private void addSprites() {
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);

            float width = sprite.getWidth() * SCALE;
            float height = sprite.getHeight() * SCALE;

            sprite.setSize(width, height);
            sprite.setOrigin(0, 0);

            sprites.put(region.name, sprite);
        }
    }

    private void generate() {
        String[] blockNames = new String[]{"steelblock","halfwoodblock","halfsteelblock","gunone","guntwo", "turbine","engine","woodblock"};

        int k=0;
        for (int j = 0; j < maxheightofship; j++) {
            for (int i = 0; i < maxwidthofship; i++) {
                if (player1ship[i][j] == 0) {
                    String name = blockNames[0];

                    float x = (float) (-100);
                    float y = (float) (0);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }

                if (player1ship[i][j] == 1) {
                    String name = blockNames[0];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 2) {
                    String name = blockNames[1];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 3) {
                    String name = blockNames[2];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 4) {
                    String name = blockNames[3];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7 +1.1)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 5) {
                    String name = blockNames[4];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 6) {
                    if (k==1){
                        firstplayerturbine2_I = i;
                        firstplayerturbine2_J = j;
                    }
                    if (k==0){
                        firstplayerturbine1_I = i;
                        firstplayerturbine1_J = j;
                        k+=1;
                    }
                    String name = blockNames[5];

                    float x = (float) ((10+i*7 - 4.5)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 7) {
                    String name = blockNames[6];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
                if (player1ship[i][j] == 8) {
                    String name = blockNames[7];

                    float x = (float) ((10+i*7)*SCALE/0.02);
                    float y = (float) ((40 -j*7)*SCALE/0.02);

                    namesofBodies[i][j] = name;
                    Bodies[i][j]=createBody(name, x, y, 0);
                }
            }
        }

        WeldJointDef jointDef = new WeldJointDef();

        for (int j = 0; j < maxheightofship; j++) {
            for (int i = 1; i < maxwidthofship; i++) {
                if (player1ship[i][j]!= 0&&player1ship[i-1][j]!=0) {

                    jointDef.initialize(Bodies[i][j],Bodies[i-1][j], new Vector2((float)((10 + 7+7 +3.5)*SCALE/0.02),(float)((40 - 7 -3.5)*SCALE/0.02)));
                    world.createJoint(jointDef);
                }
            }
        }
        for (int i = 0; i < maxwidthofship; i++) {
            for (int j = 1; j < maxheightofship; j++) {
                if (player1ship[i][j] != 0&&player1ship[i][j-1]!=0) {
                    jointDef.initialize(Bodies[i][j],Bodies[i][j-1], new Vector2((float)((10 + 7+7 +3.5)*SCALE/0.02),(float)((40 - 7 -3.5)*SCALE/0.02)));
                    world.createJoint(jointDef);
                    world.createJoint(jointDef);
                }
            }
        }

    }

    private void generateMeteors() {
        String[] meteorNames = new String[]{"meteorthree", "meteorfour", "meteorfive","meteortwo","meteortwo1","meteortwo2","meteortwo3","meteortwo4",
                "meteorone","meteorone1","meteorone2","meteorone3"};

        Random random = new Random();

        for (int i = 0; i < meteorBodies.length; i++) {
            String name = meteorNames[random.nextInt(meteorNames.length)];

            float x = (random.nextFloat() * 150)*(float)( SCALE/0.01);
            float y = (random.nextFloat() * 80)*(float)( SCALE/0.01);
            if ((x<50*SCALE/0.02)&&(y<50*SCALE/0.01)){
                x +=50*(float)(SCALE/0.01);
                y +=50*(float)(SCALE/0.01);
            }
            meteornames[i] = name;
            meteorBodies[i] = createBody(name, x, y, 0);
            meteorBodies[i].setLinearVelocity(new Vector2((random.nextFloat()-0.5f)*20,(random.nextFloat()-0.5f)*20));
        }
    }

    private Body createBody(String name, float x, float y, float rotation) {
        Body body = physicsBodies.createBody(name, world, SCALE, SCALE);
        body.setTransform(x, y, rotation);
        return body;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stepWorld();

        batch.begin();
        drawSprite("background_red_space",0,0,camera.viewportWidth,camera.viewportHeight,0);

        for (int i = 0; i < maxwidthofship; i++) {
            for (int j = 0; j < maxheightofship; j++) {

                Body body = Bodies[i][j];
                String name = namesofBodies[i][j];

                Vector2 position = body.getPosition();
                float degrees = (float) Math.toDegrees(body.getAngle());
                drawSprite(name, position.x, position.y, degrees);

            }
        }
        for (int i = 0; i < meteorBodies.length; i++) {
            Body body = meteorBodies[i];
            String name = meteornames[i];

            Vector2 position = body.getPosition();
            float degrees = (float) Math.toDegrees(body.getAngle());
            drawSprite(name, position.x, position.y, degrees);
        }


        batch.end();

        // uncomment to show the polygons
        debugRenderer.render(world, camera.combined);

    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    private void drawSprite(String name, float x, float y, float degrees) {
        Sprite sprite = sprites.get(name);
        sprite.setPosition(x, y);
        sprite.setRotation(degrees);
        sprite.draw(batch);
    }
    private void drawSprite(String name, float x, float y,float width, float height, float degrees){
        Sprite sprite = sprites.get(name);
        sprite.setPosition(x, y);
        sprite.setRotation(degrees);
        sprite.setSize(width,height);
        sprite.draw(batch);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        batch.setProjectionMatrix(camera.combined);

        createGround();
    }

    private void createGround() {
        if (upground != null) {world.destroyBody(upground);}
        if (downground != null) {world.destroyBody(downground);}
        if (leftground != null) {world.destroyBody(leftground);}
        if (rightground != null) {world.destroyBody(rightground);}

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1;
        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef.friction = 1;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(camera.viewportWidth, 1);
        fixtureDef.shape = shape;

        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(1, camera.viewportHeight);
        fixtureDef1.shape = shape1;

        upground = world.createBody(bodyDef);
        upground.createFixture(fixtureDef);
        upground.setTransform(0, camera.viewportHeight, 0);

        downground = world.createBody(bodyDef);
        downground.createFixture(fixtureDef);
        downground.setTransform(0, 0, 0);

        leftground = world.createBody(bodyDef);
        leftground.createFixture(fixtureDef1);
        leftground.setTransform(0, 0, 0);

        rightground = world.createBody(bodyDef);
        rightground.createFixture(fixtureDef1);
        rightground.setTransform(camera.viewportWidth, 0, 0);

        shape.dispose();
        shape1.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        sprites.clear();
        world.dispose();
        debugRenderer.dispose();
    }





    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Body body1  = Bodies[firstplayerturbine1_I][firstplayerturbine1_J];
        float cos1 = (float) Math.cos(body1.getAngle());
        float sin1 = (float) Math.sin(body1.getAngle());
        Body body2 = Bodies[firstplayerturbine2_I][firstplayerturbine2_J];
        float cos2 = (float) Math.cos(body2.getAngle());
        float sin2 = (float) Math.sin(body2.getAngle());
        if (screenY<Gdx.graphics.getHeight()/2){
            Bodies[firstplayerturbine1_I][firstplayerturbine1_J].applyForceToCenter(20000*cos1,20000*sin1,true);}
        if (screenY>Gdx.graphics.getHeight()/2){
            Bodies[firstplayerturbine2_I][firstplayerturbine2_J].applyForceToCenter(20000*cos2,20000*sin2,true);}
        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}