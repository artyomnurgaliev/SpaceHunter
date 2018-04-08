package io.spacehunters.game.Screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.spacehunters.game.Bodies.Sheet
import io.spacehunters.game.Extra.AssemblingScreenCoords
import io.spacehunters.game.Extra.AssemblingScreenCoords.*
import io.spacehunters.game.Extra.ItemID
import io.spacehunters.game.Extra.ItemID.NULL
import io.spacehunters.game.Extra.ItemID.NUMBER_OF_ITEMS
import io.spacehunters.game.MyGdxGame


class ShipChoosingScreen(private val game: Game) : Screen {

    private val stage: Stage = Stage(ScreenViewport())
    var p1_ship = setPrimaryShip()
    var p2_ship = setPrimaryShip()
    var p1_inventory = setPrimaryInventory()
    var p2_inventory = setPrimaryInventory()

    fun getMe() = this

    init {
        val container = Table()

        stage.addActor(container.apply {
            setFillParent(true)
            background = TextureRegionDrawable(TextureRegion(Texture("background1.png")))

        })
        val up = TextureRegionDrawable(TextureRegion(Texture("buttonunpressed.png")))
        val down = TextureRegionDrawable(TextureRegion(Texture("buttonpressed.png")))

        val table1 = Table()
        table1.width = (halfsize - blocksize/2)
        table1.height = (SCREEN_HEIGHT_F - blocksize)
        table1.setPosition(blocksize/4f, blocksize/2f)
        table1.apply{
            row().let{
                val connectToHost = ImageButton.ImageButtonStyle()
                connectToHost.up = up
                connectToHost.over = up
                connectToHost.down = down

                val b = ImageButton(connectToHost)
                b.addListener(object : InputListener() {
                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        table1.remove()
                    }

                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                        return true
                    }
                })
                add(b).width(blocksize).height(blocksize).right()
            }
            row().let{
                val table = Table()
                var pane = ScrollPane(table,MyGdxGame.skin)
                pane.setFadeScrollBars(true)
                table.apply{
                    defaults().width(blocksize*4f).height(blocksize*3.75f).padRight(blocksize/2).padBottom(blocksize/4)
                    val sheetArr = arrayOf(arrayOf(Sheet("balcon",1), Sheet("dron",1)),
                            arrayOf(Sheet("vampire",1), Sheet("yasik",1)),
                            arrayOf(Sheet("FH and ESB",1), Sheet("balcon",1)),
                            arrayOf(Sheet("balcon",1), Sheet("balcon",1)))

                    for (i in 0 until sheetArr.size){
                        row().let{
                            for (j in 0 until sheetArr[0].size) {
                                sheetArr[i][j].addListener(object : InputListener() {
                                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                                    }

                                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                        for (k in 0 until sheetArr.size){
                                            for (m in 0 until sheetArr[0].size){
                                                if ((i==k)&&(j==m)){
                                                    sheetArr[k][m].changeBacktoTouched()
                                                    p1_ship = sheetArr[k][m].ship
                                                    p1_inventory = sheetArr[k][m].inventory
                                                }
                                                else {
                                                    sheetArr[k][m].changeBacktoUntouched()
                                                }
                                            }
                                        }

                                        return true
                                    }
                                })
                                add(sheetArr[i][j])
                            }
                        }
                    }
                }
                add(pane).fill()
            }
        }


        val table2 = Table()
        table2.width = (halfsize - blocksize/2)
        table2.height = (SCREEN_HEIGHT_F - blocksize)
        table2.setPosition(SCREEN_WIDTH-table2.width-blocksize/4f, blocksize/2f)
        table2.apply{
            row().let{
                val connectToHost = ImageButton.ImageButtonStyle()
                connectToHost.up = up
                connectToHost.over = up
                connectToHost.down = down

                val b = ImageButton(connectToHost)
                b.addListener(object : InputListener() {
                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        table2.remove()
                    }

                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                        return true
                    }
                })
                add(b).width(blocksize).height(blocksize).right()
            }
            row().let{
                val table = Table()
                var pane = ScrollPane(table, MyGdxGame.skin)
                table.apply{
                    defaults().width(blocksize*4f).height(blocksize*3.75f).padRight(blocksize/2).padBottom(blocksize/4)
                    val sheetArr = arrayOf(arrayOf(Sheet("balcon",2), Sheet("dron",2)),
                            arrayOf(Sheet("vampire",2), Sheet("yasik",2)),
                            arrayOf(Sheet("FH and ESB",2), Sheet("balcon",2 )),
                            arrayOf(Sheet("balcon",2), Sheet("balcon",2 )))

                    for (i in 0 until sheetArr.size){
                        row().let{
                            for (j in 0 until sheetArr[0].size) {
                                sheetArr[i][j].addListener(object : InputListener() {
                                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                                    }

                                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                                        for (k in 0 until sheetArr.size){
                                            for (m in 0 until sheetArr[0].size){
                                                if ((i==k)&&(j==m)){
                                                    sheetArr[k][m].changeBacktoTouched()
                                                    p2_ship = sheetArr[k][m].ship
                                                    p2_inventory = sheetArr[k][m].inventory
                                                }
                                                else {
                                                    sheetArr[k][m].changeBacktoUntouched()
                                                }
                                            }
                                        }

                                        return true
                                    }
                                })
                                add(sheetArr[i][j])
                            }
                        }
                    }
                }
                add(pane).fill()
            }
        }



        val blueplanet = Image(Texture("blueplanet.png"))
        blueplanet.height = halfsize - 2* BLOCK_SIZE
        blueplanet.width = blueplanet.height*1088/1000
        blueplanet.setPosition(BLOCK_SIZE/4, SCREEN_HEIGHT/2- blueplanet.height/2)
        blueplanet.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                val Fx  = 374*blueplanet.width/1088
                val Fy =  876*blueplanet.height/1000
                val radius = 120*blueplanet.height/1000
                val Sx = 120*blueplanet.width/1088
                val Sy = 368*blueplanet.height/1000

                if (Math.sqrt(((x-Fx)*(x-Fx)+(y-Fy)*(y-Fy)).toDouble())<=radius){
                    game.screen = Menu(game, getMe(), 1, p1_ship, p1_inventory)
                }

                if (Math.sqrt(((x-Sx)*(x-Sx)+(y-Sy)*(y-Sy)).toDouble())<=radius){
                    container.addActor(table1)
                }

            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        val redplanet = Image(Texture("redplanet.png"))
        redplanet.height = halfsize - 2* BLOCK_SIZE
        redplanet.width = redplanet.height*1080/1000
        redplanet.setPosition(SCREEN_WIDTH - redplanet.width - BLOCK_SIZE/4, SCREEN_HEIGHT/2- redplanet.height/2)
        redplanet.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                val Fx  = 712*redplanet.width/1088
                val Fy =  872*redplanet.height/1000
                val radius = 120*redplanet.height/1000
                val Sx = 964*redplanet.width/1088
                val Sy = 360*redplanet.height/1000
                if (Math.sqrt(((x-Fx)*(x-Fx)+(y-Fy)*(y-Fy)).toDouble())<=radius){
                    game.screen = Menu(game,getMe(), 2, p2_ship, p2_inventory)
                }
                if (Math.sqrt(((x-Sx)*(x-Sx)+(y-Sy)*(y-Sy)).toDouble())<=radius){
                    container.addActor(table2)
                }
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        container.addActor(blueplanet)
        container.addActor(redplanet)

        /*var sheet = Sheet("balcon",1)
        container.add(sheet).fill().width(BLOCK_SIZE*8).height(BLOCK_SIZE*6.5f)*/
        val title = Label("Create your ship and press 'Start'", MyGdxGame.skin, "big-black")
        title.setAlignment(Align.center)
        title.style.fontColor = Color.WHITE
        title.y = (SCREEN_HEIGHT / 5*4).toFloat()
        title.width = SCREEN_WIDTH.toFloat()
        container.addActor(title)
        val playButton = TextButton("Start!", MyGdxGame.skin)

        playButton.width = (SCREEN_WIDTH / 2).toFloat()
        playButton.setPosition(SCREEN_WIDTH/2-playButton.width/2, SCREEN_HEIGHT/2-playButton.height/2)

        playButton.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = GameScreen(getMe(), game,p1_ship, p2_ship)

                for ( i in 0 until p1_inventory.size){
                    System.out.print("${p1_inventory[i]},")
                }
                for ( i in 0 until p1_ship.size){
                    System.out.println()
                    for ( j in 0 until p1_ship[0].size){
                        System.out.print("${p1_ship[i][j]},")
                    }
                }

            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        container.addActor(playButton)



        /*val table = Table()
        val button1 = TextButton( "<-", MyGdxGame.skin)
        val button2 = TextButton( "model+${1}", MyGdxGame.skin)
        button2.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {

            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

                return true
            }
        })
        val button3 = TextButton( "->", MyGdxGame.skin)
        table.add(button1).width(300.toFloat())
        table.add(button2).width(300.toFloat())
        table.add(button3).width(300.toFloat())
        table.width = (SCREEN_WIDTH/2).toFloat()
        table.setPosition(0.toFloat(), SCREEN_HEIGHT_F*3/4)
        container.addActor(table)*/

    }

    private fun shipRotate(ship: Array<IntArray>): Array<IntArray> {
        val res = Array(ship.size) { IntArray(ship[0].size) }
        var ID: Int

        for (i in ship.indices)
            for (j in 0 until ship[0].size) {
                res[ship.size - i - 1][ship[0].size - j - 1] = ship[i][j]
                ID = ship[i][j] % 10
                if (ID == ItemID.HALF_WOOD_BLOCK || ID == ItemID.HALF_STEEL_BLOCK || ID == ItemID.TURBINE || ID == ItemID.WOOD_GUN || ID == ItemID.STEEL_GUN) {
                    when (ship[i][j] / 10 * 10) {
                        ItemID.RIGHT -> res[ship.size - i - 1][ship[0].size - j - 1] += ItemID.LEFT - ItemID.RIGHT
                        ItemID.UP -> res[ship.size - i - 1][ship[0].size - j - 1] += ItemID.DOWN - ItemID.UP
                        ItemID.LEFT -> res[ship.size - i - 1][ship[0].size - j - 1] += ItemID.RIGHT - ItemID.LEFT
                        ItemID.DOWN -> res[ship.size - i - 1][ship[0].size - j - 1] += ItemID.UP - ItemID.DOWN
                    }
                }
            }

        return res
    }

    private fun shipFlipHorizontal(ship: Array<IntArray>): Array<IntArray> {
        val res = Array(ship.size) { IntArray(ship[0].size) }
        for (i in res.indices) {
            for (j in 0 until res[0].size) {
                res[i][j] = ship[i][AssemblingScreenCoords.FIELD_HEIGHT - 1 - j]
            }
        }
        return res
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



    private fun setPrimaryShip(): Array<IntArray> {                                                 // Задаёт изначальный вариант корабля (т.е. пустое поле)
        val resShip = Array(FIELD_WIDTH) { IntArray(FIELD_HEIGHT) }
        for (i in 0 until FIELD_WIDTH) {
            for (j in 0 until FIELD_HEIGHT) {
                resShip[i][j] = NULL
            }
        }
        return resShip
    }                                             // Задаёт изначальный корабль (т.е. пустое поле)
    private fun setPrimaryInventory(): IntArray {
        val inventory = IntArray(NUMBER_OF_ITEMS)
        for (i in 0 until ItemID.NUMBER_OF_ITEMS)
            inventory[i] = ItemID.ITEMS_MAX_CNT[i]
        return inventory
    }                                               // Задаёт изначальное количество предметов для расстановки

    fun setShipData(ship: Array<IntArray>, player: Int, inventory: IntArray) {
        when (player) {
            1 -> {
                p1_ship = ship
                p1_inventory = inventory
            }
            2 -> {
                p2_ship = ship
                p2_inventory = inventory
            }
        }
    }

}