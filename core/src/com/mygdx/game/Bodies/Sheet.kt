package com.mygdx.game.Bodies

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.game.Extra.AssemblingScreenCoords
import com.mygdx.game.Extra.ItemID
import com.mygdx.game.MyGdxGame

class Sheet(name : String, player : Int): Table(){
    private var ship = Array(AssemblingScreenCoords.FIELD_WIDTH) { IntArray(AssemblingScreenCoords.FIELD_HEIGHT) }
    private var inventory = IntArray(ItemID.NUMBER_OF_ITEMS)
    private var prefSize = AssemblingScreenCoords.BLOCK_SIZE
    private var SheetName = ""
    private var player : Int = 0
    init{
        ship = getShip(name)
        inventory = getInventory(name)
        SheetName = name
        this.player = player
        val stageLayout = Table()
        background = TextureRegionDrawable(TextureRegion(Texture("background.png")))
        add(stageLayout.apply {
            debugAll()
            for (j in 0 until AssemblingScreenCoords.FIELD_HEIGHT){
                row().let {
                    for (i in 0 until AssemblingScreenCoords.FIELD_WIDTH) {
                        if (ship[i][j] != 1000) {
                            val img = Image(Texture(getString(ship[i][j]%10)))
                            img.width = getBWidth(ship[i][j])
                            img.height = getBHeight(ship[i][j])
                            val imgOX = if (ship[i][j]%10 == ItemID.TURBINE) img.width - prefSize/2 else prefSize/2
                            val imgOY = prefSize/2
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
            add(Label(SheetName, MyGdxGame.skin, "big-black")).fillY()
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
            "balcon" -> arrayOf(intArrayOf(0,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,3,2,3,1000),
                    intArrayOf(1000,1000,2,8,2,1000),
                    intArrayOf(1000,1000,1000,6,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000),
                    intArrayOf(1000,1000,1000,1000,1000,1000))
            else -> arrayOf(IntArray(0))
        }
    }

    private fun getInventory(name: String):IntArray{
        return when (name){
            "balcon" -> intArrayOf(8,4,0,0,4,2,0,2,0)
            else -> intArrayOf()
        }
    }

}