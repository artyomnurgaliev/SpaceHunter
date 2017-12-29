package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Bodies.MoveableImage;
import com.mygdx.game.Extra.AssemblingScreenCoords;
import com.mygdx.game.Extra.ItemID;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

public class Menu implements Screen, InputProcessor, ItemID, AssemblingScreenCoords {

    private Stage stage;
    private Game game;

    private float oldX = 0, oldY = 0;
    private float currentX = 0, currentY = 0;
    private float lastX = 0, lastY = 0;
    private int lastXInTable = 0, lastYInTable = 0;
    private int delta_flag = NULL;

    private int[][] blockArr = new int[FIELD_WIDTH][FIELD_HEIGHT];
    private ArrayList<MoveableImage[]> blocks = new ArrayList<MoveableImage[]>();
    private MoveableImage[][] cells = new MoveableImage[FIELD_WIDTH][FIELD_HEIGHT];
    private Label[] labels = new Label[NUMBER_OF_ITEMS];

    private int currI = NULL, currJ = NULL;                                                         // Предмет, который мы сейчас перетаскиваем
    private boolean isImageDragging = false;                                                        // Перетаскиваем ли мы какой-нибудь из предметов

    private int[] inventory = setPrimaryInventory();                                                // Инвентарь игрока



    Menu(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                cells[i][j] = new MoveableImage(FIELD_DELTA_X + BLOCK_SIZE*i, FIELD_DELTA_Y + BLOCK_SIZE*j, BLOCK_SIZE, BLOCK_SIZE,0,"gray.png");
                stage.addActor(cells[i][j]);
                blockArr[i][j] = NULL;
            }
        }

        for (int itemsCount : inventory)
            blocks.add(new MoveableImage[itemsCount]);

        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < blocks.get(i).length; j++)
                blocks.get(i)[blocks.get(i).length - j - 1] = new MoveableImage(getPosX(i), getPosY(i), getWidth(i), getHeight(i), 0, getImageName(i));
            blocks.get(i)[0].setTouchable(true);
            stage.addActor(blocks.get(i)[0]);
        }

        BitmapFont itemsCountBF = new BitmapFont();
        itemsCountBF.getData().scale(2);
        Label.LabelStyle itemsCountLS = new Label.LabelStyle(itemsCountBF, Color.BLACK);

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label("x" + blocks.get(i).length, itemsCountLS);
            labels[i].setX((i < NUMBER_OF_ITEMS/2)? BLOCK_SIZE/5: SCREEN_WIDTH - BLOCK_SIZE*2/5);
            labels[i].setY(getPosY(i) + BLOCK_SIZE/5);
            stage.addActor(labels[i]);
        }

        TextButton button = new TextButton("Start!", MyGdxGame.Companion.getSkin());
        button.setWidth(SCREEN_WIDTH/4);
        button.setPosition(SCREEN_WIDTH - button.getWidth()*3/2, BLOCK_SIZE/2);
        button.addListener(new InputListener(){

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                int[][] arr = new int[FIELD_WIDTH][FIELD_HEIGHT];
                for (int i = 0; i < FIELD_WIDTH; i++)
                    for (int j = 0; j < FIELD_HEIGHT; j++)
                        arr[i][j] = blockArr[i][FIELD_HEIGHT - 1 - j];
                game.setScreen(new GameScreen(arr,arr));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(button);
    }



    private float getPosX(int i) {
        return (i < NUMBER_OF_ITEMS/2)? BLOCK_SIZE/2 : SCREEN_WIDTH - BLOCK_SIZE/2 - getWidth(i);
    }

    private float getPosY(int i) {
        return (i < NUMBER_OF_ITEMS/2)? BLOCK_SIZE*(i + 2)*3/2 - getHeight(i)/2: BLOCK_SIZE*(i - 2)*3/2 - getHeight(i)/2;
    }

    private float getWidth(int i) {
        switch(i) {
            case WOOD_GUN: return 770*BLOCK_SIZE/345;
            case STEEL_GUN: return 765*BLOCK_SIZE/345;
            case TURBINE: return 565*BLOCK_SIZE/345;
        }
        return BLOCK_SIZE;
    }

    private float getHeight(int i) {
        switch(i) {
            case WOOD_GUN: return 194*BLOCK_SIZE/345;
            case STEEL_GUN: return 315*BLOCK_SIZE/345;
        }
        return BLOCK_SIZE;
    }


    private void plus1ToLabel(int index) {
        labels[index].setText("x" + (Integer.parseInt(labels[index].getText().toString().substring(1)) + 1));
    }

    private void minus1FromLabel(int index) {
        labels[index].setText("x" + (Integer.parseInt(labels[index].getText().toString().substring(1)) - 1));
    }


    private String getImageName(int i) {
        switch(i) {
            case WOOD_BLOCK: return "woodblock.png";
            case STEEL_BLOCK: return "steelblock.png";
            case ENGINE: return "engine.png";
            case TURBINE: return "turbine.png";
            case HALF_WOOD_BLOCK: return "halfwoodblock.png";
            case HALF_STEEL_BLOCK: return "halfsteelblock.png";
            case WOOD_GUN: return "gun_1.png";
            case STEEL_GUN: return "gun_2.png";
        }
        return "";
    }

    private int[] setPrimaryInventory() {
        int[] inventory = new int[8];
        inventory[WOOD_BLOCK] = 8;
        inventory[STEEL_BLOCK] = 4;
        inventory[ENGINE] = 3;
        inventory[TURBINE] = 2;
        inventory[HALF_WOOD_BLOCK] = 4;
        inventory[HALF_STEEL_BLOCK] = 2;
        inventory[WOOD_GUN] = 1;
        inventory[STEEL_GUN] = 2;
        return inventory;
    }                                                       // Задаёт изначальное количество предметов для расстановки

    private void setCoordsFromCell(float x, float y, float relativeX, float relativeY) {
        float imgCenterX = BLOCK_SIZE/2, imgCenterY = BLOCK_SIZE/2;
        int ID = blocks.get(currI)[currJ].getNumber() % 10;
        int facing = blocks.get(currI)[currJ].getNumber() / 10 * 10;

        if (((ID == WOOD_GUN || ID == STEEL_GUN) && facing == RIGHT && relativeX > BLOCK_SIZE) || (ID == TURBINE && facing == LEFT && relativeX > 1.7 * BLOCK_SIZE)) {
            imgCenterX -= BLOCK_SIZE;
            delta_flag = LEFT;
        }
        if ((((ID == WOOD_GUN || ID == STEEL_GUN) && facing == UP) || (ID == TURBINE && facing == DOWN)) && relativeY > BLOCK_SIZE) {
            imgCenterY -= BLOCK_SIZE;
            delta_flag = DOWN;
        }
        if (((ID == WOOD_GUN || ID == STEEL_GUN) && facing == LEFT && relativeX < 0) || (ID == TURBINE && facing == RIGHT && relativeX < 0.7 * BLOCK_SIZE)) {
            imgCenterX += BLOCK_SIZE;
            delta_flag = RIGHT;
        }
        if ((((ID == WOOD_GUN || ID == STEEL_GUN) && facing == DOWN) || (ID == TURBINE && facing == UP)) && relativeY < 0) {
            imgCenterY += BLOCK_SIZE;
            delta_flag = UP;
        }

        blocks.get(currI)[currJ].setXY(x + imgCenterX - blocks.get(currI)[currJ].getOriginX(), y + imgCenterY - blocks.get(currI)[currJ].getOriginY());
    }



    private void initCurrentImage(int i, int j) {
        isImageDragging = true;
        currI = i;
        currJ = j;
    }                                               // Запоминает предмет, которое мы сейчас перетаскиваем

    private void resetCurrentImage() {
        isImageDragging = false;
        currI = NULL;
        currJ = NULL;
    }                                                          // Сбрасывает перетаскиваемый предмет


    private boolean setEndPosition(int x, int y) {
        float relativeX = x - blocks.get(currI)[currJ].getX();
        float relativeY = SCREEN_HEIGHT - y - blocks.get(currI)[currJ].getY();
        int i = (int) Math.floor((x - FIELD_DELTA_X) / BLOCK_SIZE);
        int j = FIELD_HEIGHT - 1 - (int) Math.floor((y - FIELD_DELTA_Y) / BLOCK_SIZE);

        if ((i >= 0) && (i < FIELD_WIDTH) && (j >= 0) && (j < FIELD_HEIGHT)) {
            if (blockArr[i][j] == NULL) {
                setCoordsFromCell(cells[i][j].getX(), cells[i][j].getY(), relativeX, relativeY);
                switch (delta_flag) {
                    case RIGHT: i++; break;
                    case UP: j++; break;
                    case LEFT: i--; break;
                    case DOWN: j--; break;
                }
                delta_flag = NULL;
                if ((i != -1) && (i != FIELD_WIDTH) && (j != -1) && (j != FIELD_HEIGHT)) {
                    if (!isImgOutOfBounds(i, j)) {
                        setImageOnTable(i, j);
                        return true;
                    } else {
                        returnImageBack();
                        return false;
                    }
                } else {
                    returnImageBack();
                    return false;
                }
            } else {
                returnImageBack();
                return false;
            }
        } else {
            blocks.get(currI)[currJ].setAngle(0);
            blocks.get(currI)[currJ].returnToStartPos();
            return false;
        }
    }

    private void safeRotate() {
        int x = blocks.get(currI)[currJ].getXinTable();
        int y = blocks.get(currI)[currJ].getYinTable();
        do
            blocks.get(currI)[currJ].flip90();
        while (isImgOutOfBounds(x, y));
    }

    private boolean isImgOutOfBounds(int x, int y) {
        int ID = blocks.get(currI)[currJ].getNumber() % 10;
        int facing = blocks.get(currI)[currJ].getNumber() / 10 * 10;
        return ((((ID == WOOD_GUN || ID == STEEL_GUN) && facing == LEFT) || (ID == TURBINE && facing == RIGHT)) && x == 0) ||
                ((((ID == WOOD_GUN || ID == STEEL_GUN) && facing == RIGHT) || (ID == TURBINE && facing == LEFT)) && x == FIELD_WIDTH - 1) ||
                ((((ID == WOOD_GUN || ID == STEEL_GUN) && facing == DOWN) || (ID == TURBINE && facing == UP)) && y == 0) ||
                ((((ID == WOOD_GUN || ID == STEEL_GUN) && facing == UP) || (ID == TURBINE && facing == DOWN)) && y == FIELD_HEIGHT - 1);
    }


    private void setImageOnTable(int i, int j) {
        blockArr[i][j] = blocks.get(currI)[currJ].getNumber();
        blocks.get(currI)[currJ].setXYinTable(i, j);
    }

    private void removeImageFromTable() {
        blockArr[blocks.get(currI)[currJ].getXinTable()][blocks.get(currI)[currJ].getYinTable()] = NULL;
        blocks.get(currI)[currJ].setXYinTable(NULL, NULL);
    }

    private void returnImageBack() {
        blocks.get(currI)[currJ].returnToStartPos(lastX, lastY);
        if (lastXInTable != NULL)
            setImageOnTable(lastXInTable, lastYInTable);
    }


    @Override
    public boolean keyDown (int keycode) {
            return true;
        }

    @Override
    public boolean keyUp (int keycode) {
            return true;
        }

    @Override
    public boolean keyTyped (char character) {
            return true;
        }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        for (int i = 0; i < blocks.size(); i++)
            for (int j = 0; j < blocks.get(i).length; j++)
                if (blocks.get(i)[j].contains(x, y) && blocks.get(i)[j].isTouchable() && !isImageDragging) {
                    initCurrentImage(i, j);
                    blocks.get(currI)[currJ].setMoving(true);
                    blocks.get(currI)[currJ].setAlreadyMoved(false);
                    lastX = blocks.get(currI)[currJ].getX();
                    lastY = blocks.get(currI)[currJ].getY();
                    if (blocks.get(currI)[currJ].getXinTable() != NULL) {
                        lastXInTable = blocks.get(currI)[currJ].getXinTable();
                        lastYInTable = blocks.get(currI)[currJ].getYinTable();
                        removeImageFromTable();
                    } else {
                        lastXInTable = NULL;
                        lastYInTable = NULL;
                        if (blocks.get(currI)[currJ].isInStartPos())
                            minus1FromLabel(i);
                    }
                }
        oldX = x;
        oldY = y;
        currentX = x;
        currentY = y;
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        if (isImageDragging) {
            if (!blocks.get(currI)[currJ].isAlreadyMoved()) {
                blocks.get(currI)[currJ].setXY(lastX, lastY);
                if (blocks.get(currI)[currJ].isInStartPos())
                    plus1ToLabel(currI);
                else {
                    setImageOnTable(lastXInTable, lastYInTable);
                    safeRotate();
                    blockArr[lastXInTable][lastYInTable] = blocks.get(currI)[currJ].getNumber();
                }
            } else if (setEndPosition(x, y)) {
                if (currJ < blocks.get(currI).length - 1) {
                    for (int h = currJ + 1; h < blocks.get(currI).length; h++) {
                        if (blocks.get(currI)[h].isInStartPos()) {
                            blocks.get(currI)[h].setTouchable(true);
                            stage.addActor(blocks.get(currI)[h]);
                            break;
                        }
                    }
                }
            } else {
                if (blocks.get(currI)[currJ].isInStartPos()) {
                    plus1ToLabel(currI);
                    for (int h = 0; h < blocks.get(currI).length; h++) {
                        if ((h != currJ) && (blocks.get(currI)[h].isInStartPos())) {
                            blocks.get(currI)[h].setTouchable(false);
                            blocks.get(currI)[h].remove();
                        }
                    }
                }
            }
            blocks.get(currI)[currJ].setMoving(false);
            resetCurrentImage();
        }
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        if (isImageDragging) {
            if ((Math.abs(x - currentX) > BLOCK_SIZE/8) || (Math.abs(y - currentY) > BLOCK_SIZE/8))
                blocks.get(currI)[currJ].setAlreadyMoved(true);
            blocks.get(currI)[currJ].setXY(blocks.get(currI)[currJ].getX() + x - oldX, blocks.get(currI)[currJ].getY() - y + oldY);
            oldX = x;
            oldY = y;
        }
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
            return true;
    }

    @Override
    public boolean scrolled (int amount) {
            return true;
        }



    @Override
    public void show() {
        InputMultiplexer m = new InputMultiplexer();
        m.addProcessor(this);
        m.addProcessor(stage);
        Gdx.input.setInputProcessor(m);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        stage.dispose();
    }

}
