package mx.itesm.dinospazio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fondo {
    private Sprite sprite;
    public Fondo(Texture textura){
        sprite = new Sprite(textura);
    }
    public void setSize (float x,float y){
        sprite.setSize(x,y);
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setAlfa(float alfa) {
        sprite.setAlpha(alfa);
    }
    public void setPosicion(float x,float y){
        sprite.setPosition(x, y);
    }
}
