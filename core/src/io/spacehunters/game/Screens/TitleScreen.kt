package io.spacehunters.game.Screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.spacehunters.game.Extra.AssemblingScreenCoords.*
import io.spacehunters.game.MyGdxGame

class TitleScreen(private val game: Game) : Screen {

    private val stage: Stage = Stage(ScreenViewport())



    init {
        val table = Table()
        stage.addActor(table.apply {
            setFillParent(true)
            background = TextureRegionDrawable(TextureRegion(Texture("background.png")))

            val title = Label("Space Hunters", MyGdxGame.skin, "big-black")
            title.setAlignment(Align.center)
            title.style.fontColor = Color.WHITE
            title.y = (SCREEN_HEIGHT / 2).toFloat()
            title.width = SCREEN_WIDTH.toFloat()
            addActor(title)

            val playButton = TextButton("Start!", MyGdxGame.skin)
            playButton.width = (SCREEN_WIDTH / 2).toFloat()
            playButton.setPosition(SCREEN_WIDTH / 2 - playButton.width / 2, SCREEN_HEIGHT/3 - playButton.height / 2)
            playButton.addListener(object : InputListener() {

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    game.screen = ShipChoosingScreen(game)
                }

                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    return true
                }
            })
            addActor(playButton)
        })


    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        stage.dispose()
    }
}
