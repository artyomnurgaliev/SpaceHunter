package io.spacehunters.game.Extra;

import com.badlogic.gdx.Gdx;

import static io.spacehunters.game.Extra.ItemID.NUMBER_OF_ITEMS;

public interface AssemblingScreenCoords {

    // Здесь хранятся координаты для вёрстки окна со сборкой корабля

    int SCREEN_HEIGHT = Gdx.graphics.getHeight();                                       // Высота экрана
    int SCREEN_WIDTH = Gdx.graphics.getWidth();                                         // Ширина экрана
    float SCREEN_HEIGHT_F = Gdx.graphics.getHeight();                                   // То же, но float очень логичная константа, Миша

    int FIELD_HEIGHT = 6;                                                               // Высота поля сборки (в блоках)
    int FIELD_WIDTH = 8;// Ширина поля сборки (в блоках)
    float halfsize = Math.min(SCREEN_HEIGHT_F,SCREEN_WIDTH/2f);
    float blocksize = halfsize/10;

    float BLOCK_SIZE = Math.min(Math.min(
            (float) (SCREEN_HEIGHT / (FIELD_HEIGHT + 2)),
            (float) (SCREEN_WIDTH / (FIELD_WIDTH + 5))),
            (float) (SCREEN_HEIGHT / (NUMBER_OF_ITEMS + 2)));                           // Размер одного блока
    float FIELD_DELTA_Y = (SCREEN_HEIGHT - BLOCK_SIZE * FIELD_HEIGHT) / 2;              // Вертикальный отступ поля сборки
    float FIELD_DELTA_X = (SCREEN_WIDTH - BLOCK_SIZE * FIELD_WIDTH) / 2;                // Горизонтальный отступ поля сборки

}
