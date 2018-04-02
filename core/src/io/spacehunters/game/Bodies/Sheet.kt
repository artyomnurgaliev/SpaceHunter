package io.spacehunters.game.Bodies

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import io.spacehunters.game.Extra.AssemblingScreenCoords
import io.spacehunters.game.Extra.AssemblingScreenCoords.blocksize
import io.spacehunters.game.Extra.FontID
import io.spacehunters.game.Extra.ItemID
import io.spacehunters.game.MyGdxGame

class Sheet(name : String, player : Int): Table(){
    var ship = Array(AssemblingScreenCoords.FIELD_WIDTH) { IntArray(AssemblingScreenCoords.FIELD_HEIGHT) }
    var inventory = IntArray(ItemID.NUMBER_OF_ITEMS)
    private var prefSize = blocksize/2
    private var SheetName = ""
    private var player : Int = 0
    private var backuntouched : TextureRegionDrawable
    private var backtouched : TextureRegionDrawable
    init{
        ship = getShip(name)
        val a = Math.random()*3
        var b = 0
        if (a<1) b = 1
        if ((a>=1)&&(a<2)) b = 2
        if ((a>=2)&&(a<=3)) b = 3
        backuntouched =  TextureRegionDrawable(TextureRegion(Texture("sheet${b}.png")))
        backtouched = TextureRegionDrawable(TextureRegion(Texture("sheet${b}${player}.png")))
        inventory = getInventory(name)
        SheetName = name
        this.player = player
        val stageLayout = Table()
        val actor = Actor()
        actor.setBounds(0f,0f, prefSize*8, prefSize*7.5f)
        addActor(actor)
        background = backuntouched
        add(stageLayout.apply {
            for (j in 0 until AssemblingScreenCoords.FIELD_HEIGHT){
                row().let {
                    for (i in 0 until AssemblingScreenCoords.FIELD_WIDTH) {
                        if (ship[i][j] != 1000) {
                            val img = Image(Texture(getString(ship[i][j]%10)))
                            img.width = getBWidth(ship[i][j])
                            img.height = getBHeight(ship[i][j])
                            val imgOX = if (ship[i][j]%10 == ItemID.TURBINE) img.width - prefSize/2 else prefSize/2
                            val imgOY = img.height/2
                            img.setOrigin(imgOX, imgOY)
                            img.setPosition(i*prefSize+prefSize/2 - imgOX, j*prefSize+prefSize/2 - imgOY)
                            var id = ship[i][j]
                            while (id>=10) {
                                img.rotateBy(90.toFloat())
                                id-=10
                            }
                            addActor(img)
                        }
                    }
                }

            }
        }).height(prefSize*6f).width(prefSize*8f)
        row().let{
            val sheetFont = GameFont(FontID.JURA_DEMIBOLD, 30, Color.BROWN)
            add(Label(SheetName, sheetFont.getLS())).height(prefSize*1.3f)

        }

    }


    private fun getBWidth(i: Int): Float {
        return when (i%10) {
            ItemID.STEEL_GUN -> 765 * prefSize / 345
            ItemID.WOOD_GUN -> 770 * prefSize / 345
            ItemID.TURBINE -> 565 * prefSize / 345
            else -> prefSize
        }
    }

    fun changeBacktoTouched(){
        background = backtouched
    }

    fun changeBacktoUntouched(){
        background = backuntouched
    }

    private fun getBHeight(i: Int): Float {
        return when (i%10) {
            ItemID.STEEL_GUN -> 315 * prefSize / 345
            ItemID.WOOD_GUN -> 194 * prefSize / 345
            else -> prefSize
        }
    }
    private fun getString(texture: Int): String {
        return when (texture) {
            ItemID.WOOD_BLOCK -> "woodblock.png"
            ItemID.STEEL_BLOCK -> "steelblock.png"
            ItemID.ENGINE -> "engine.png"
            ItemID.TURBINE -> "turbine.png"
            ItemID.HALF_WOOD_BLOCK -> "halfwoodblock.png"
            ItemID.HALF_STEEL_BLOCK -> "halfsteelblock.png"
            ItemID.STEEL_GUN -> "gun_2.png"
            ItemID.WOOD_GUN -> "gun_1.png"
            ItemID.EYE -> "eye${player}.png"
            else -> ""
        }
    }

    private fun getShip(name: String):Array<IntArray>{
        return when (name){
            "balcon" -> arrayOf(intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,3,2,3,1000),
                    intArrayOf(1000,1000,2,8,2,1000),
                    intArrayOf(1000,1000,1000,6,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000))
            "dron" -> arrayOf(intArrayOf(0,1,0,0,4,1000),
                    intArrayOf(1,8,1,0,4,1000),
                    intArrayOf(0,1,0,5,1000,1000),
                    intArrayOf(0,0,5,1000,1000,1000),
                    intArrayOf(4,4,1000,3,2,3),
                    intArrayOf(1000,1000,1000,2,6,2),
                    intArrayOf(1000,1000,1000,7,1000,7),
                    intArrayOf(1000,1000,1000,1000,1000,1000))
            "vampire" -> arrayOf(intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,3,2,3,1000,1000),
                    intArrayOf(24,2,8,2,14,1000),
                    intArrayOf(7,0,6,0,7,1000),
                    intArrayOf(1000,0,1000,0,1000,1000),
                    intArrayOf(1000,35,1000,5,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000))
            "yasik" -> arrayOf(intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,2,15,1000,1000,1000),
                    intArrayOf(1000,13,1,17,1000,1000),
                    intArrayOf(1000,2,8,1,16,1000),
                    intArrayOf(1000,13,1,17,1000,1000),
                    intArrayOf(1000,2,5,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000))
            "FH and ESB"  -> arrayOf(intArrayOf(0,0,1000,1000,1000,1000),
            intArrayOf(0,0,35,3,3,5),
            intArrayOf(0,0,1000,2,2,1000),
            intArrayOf(0,0,1000,1,1,1000),
            intArrayOf(8,4,1000,1,1,1000),
            intArrayOf(7,1000,1000,6,2,1000),
            intArrayOf(1000,1000,1000,1000,7,1000),
            intArrayOf(1000,1000,1000,1000,1000,1000))


            else -> arrayOf(IntArray(0))
        }
    }

    private fun getInventory(name: String):IntArray{
        return when (name){
            "balcon" -> intArrayOf(8,4,0,0,4,2,0,2,0)
            "dron" -> intArrayOf(0,0,0,0,0,0,0,0,0)
            "vampire" -> intArrayOf(4,4,0,0,2,0,0,0,0)
            "yasik" -> intArrayOf(8,1,0,0,4,0,0,0,0)
            "FH and ESB" -> intArrayOf(0,0,0,0,3,0,0,0,0)
            else -> intArrayOf()
        }
    }

}