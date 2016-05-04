package mx.itesm.dinospazio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Historia implements Screen {

    private final Plataforma plataforma;
    private Stage stage;
    private Viewport viewport;

    private GameState estadoJuego;



    //Cámara "escenas"
    private OrthographicCamera escenas;

    //Botones avanzar historia
    private OrthographicCamera HUD;

    //Boton Izquierda
    private Texture Izquierda;
    private Boton btnIzquierda;

    //Boton Derecha

    private Texture Derecha;
    private Boton btnDerecha;

    // Boton Skip
    private Texture Skip;
    private Boton btnSkip;


    //Textura fondo cuarto
    private Fondo fondoComic1;
    private Texture texturaComic1;

    private Fondo fondoComic2;
    private Texture texturaComic2;

    private Fondo fondoComic3;
    private Texture texturaComic3;

    private Fondo fondoComic4;
    private Texture texturaComic4;

    private Fondo fondoComic5;
    private Texture texturaComic5;

    private Fondo fondoComic6;
    private Texture texturaComic6;

    private Fondo fondoComic7;
    private Texture texturaComic7;



    private SpriteBatch batch;

    private int ImagesCounter;

    private int counter = 0;

    float time = 0;

    public Historia (Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public void show() {
    // Crea camara
        escenas = new OrthographicCamera(Plataforma.ANCHO_CAMARA,Plataforma.ALTO_CAMARA);
        escenas.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        escenas.update();

        viewport = new StretchViewport(Plataforma.ANCHO_CAMARA,Plataforma.ALTO_CAMARA, escenas);


        LoadResources();
        CreateObjects();



        estadoJuego = GameState.SCENE0;
        //estadoJuego = GameState.SCENE1;
        //estadoJuego = GameState.SCENE2;
        //estadoJuego = GameState.SCENE3;
        //estadoJuego = GameState.SCENE4;
       // estadoJuego = GameState.SCENE5;
        //estadoJuego = GameState.SCENE6;




        batch = new SpriteBatch();

    }

    public void LoadResources(){
        AssetManager assetManager = plataforma.getAssetManager();
        assetManager.load("comic1.png", Texture.class);
        assetManager.load("comic2.png", Texture.class);
        assetManager.load("comic3.png", Texture.class);
        assetManager.load("comic4.png", Texture.class);
        assetManager.load("comic5.png", Texture.class);
        assetManager.load("comic6.png", Texture.class);
        assetManager.load("comic7.png", Texture.class);
        assetManager.load("derecha.png", Texture.class);
        assetManager.load("izquierda.png", Texture.class);
        assetManager.load("Skip.png", Texture.class);
        assetManager.finishLoading();
    }

    public void CreateObjects(){
        AssetManager assetManager = plataforma.getAssetManager();
        texturaComic1 =assetManager.get("comic1.png");
        fondoComic1 = new Fondo(texturaComic1);
        fondoComic1.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        texturaComic2 =assetManager.get("comic2.png");
        fondoComic2 = new Fondo(texturaComic2);
        fondoComic2.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        texturaComic3 =assetManager.get("comic3.png");
        fondoComic3 = new Fondo(texturaComic3);
        fondoComic3.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        texturaComic4 =assetManager.get("comic4.png");
        fondoComic4 = new Fondo(texturaComic4);
        fondoComic4.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        texturaComic5 =assetManager.get("comic5.png");
        fondoComic5 = new Fondo(texturaComic5);
        fondoComic5.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        texturaComic6 =assetManager.get("comic6.png");
        fondoComic6 = new Fondo(texturaComic6);
        fondoComic6.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        texturaComic7 =assetManager.get("comic7.png");
        fondoComic7 = new Fondo(texturaComic7);
        fondoComic7.setSize(plataforma.ANCHO_CAMARA, plataforma.ALTO_CAMARA);

        //Botón Derecha
        Derecha = assetManager.get("derecha.png");
        btnDerecha = new Boton (Derecha);
        btnDerecha.setSize(100, 100);
        //btnRight.setPosicion(TAM_CELDA, 5 * TAM_CELDA);
        btnDerecha.setPosicion(1170, 10);
        btnDerecha.setAlfa(0.7f);

        //Botón Izquierda
        Izquierda = assetManager.get("izquierda.png");
        btnIzquierda = new Boton (Izquierda);
        btnIzquierda.setSize(100, 100);
        btnIzquierda.setPosicion(10, 10);
        btnIzquierda.setAlfa(0.7f);

        // Boton Skip
        Skip = assetManager.get("Skip.png");
        btnSkip = new Boton (Skip);
        btnSkip.setSize(100, 100);
        btnSkip.setPosicion(1170, 600);
        btnSkip.setAlfa(0.7f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(escenas.combined);

        if(estadoJuego == GameState.SCENE0) {
            leerScene0();
            leerSkip();
            //leerBack();
            batch.begin();
            fondoComic1.render(batch);
           // btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        }else
        if(estadoJuego == GameState.SCENE1){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            btnDerecha.setPosicion(1170, 10);
            leerScene1();
            leerSkip1();
            leerBack1();
            batch.begin();
            fondoComic2.render(batch);
            btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        }else
        if(estadoJuego == GameState.SCENE2){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            btnDerecha.setPosicion(1170, 10);
            leerScene2();
            leerSkip2();
            leerBack2();
            batch.begin();
            fondoComic3.render(batch);
            btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        } else
        if(estadoJuego == GameState.SCENE3){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            btnDerecha.setPosicion(1170, 10);
            leerScene3();
            leerSkip3();
            leerBack3();
            batch.begin();
            fondoComic4.render(batch);
            btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        } else
        if(estadoJuego == GameState.SCENE4){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            btnDerecha.setPosicion(1170, 10);
            leerScene4();
            leerSkip4();
            leerBack4();
            batch.begin();
            fondoComic5.render(batch);
            btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        } else
        if(estadoJuego == GameState.SCENE5){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            btnDerecha.setPosicion(1170,10);
            leerScene5();
            leerSkip5();
            leerBack5();
            batch.begin();
            fondoComic6.render(batch);
            btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        }else
        if(estadoJuego == GameState.SCENE6){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            btnDerecha.setPosicion(1170, 10);
            leerScene6();
            leerSkip6();
            leerBack6();
            batch.begin();
            fondoComic7.render(batch);
            btnIzquierda.render(batch);
            btnSkip.render(batch);
            btnDerecha.render(batch);
            batch.end();
        }



    }


    private void leerScene0() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene0(x, y) == true) {
                estadoJuego =GameState.SCENE1;
            }
        }
    }

    private boolean verificarScene0(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerScene1() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene1(x, y) == true) {
                estadoJuego =GameState.SCENE2;

            }
        }
    }

    private boolean verificarScene1(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerScene2() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene2(x, y) == true) {
                estadoJuego =GameState.SCENE3;
            }
        }
    }

    private boolean verificarScene2(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerScene3() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene3(x, y) == true) {
                estadoJuego =GameState.SCENE4;
            }
        }
    }

    private boolean verificarScene3(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerScene4() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene4(x, y) == true) {
                estadoJuego =GameState.SCENE5;
            }
        }
    }

    private boolean verificarScene4(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerScene5() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene5(x, y) == true) {
                estadoJuego =GameState.SCENE6;
            }
        }
    }

    private boolean verificarScene5(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerScene6() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarScene6(x, y) == true) {
                plataforma.setScreen(new PantallaCargando( plataforma, 1));
            }
        }
    }

    private boolean verificarScene6(float x, float y) {
        Sprite sprite = btnDerecha.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }




    /*private void leerBack() {
        Gdx.app.log("LeerBack", "Probando back ");
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;

            if (verificarBack(x, y) == true) {
                Gdx.app.log("LeerBack", "Regresando al menu principal ");
                plataforma.setScreen(new Menu(plataforma));
            }
        }
    }

    private boolean verificarBack(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }*/

    private void leerBack1() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBack1(x, y) == true) {
                estadoJuego =GameState.SCENE0;

            }
        }
    }

    private boolean verificarBack1(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerBack2() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBack2(x, y) == true) {
                estadoJuego =GameState.SCENE1;

            }
        }
    }

    private boolean verificarBack2(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerBack3() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBack3(x, y) == true) {
                estadoJuego =GameState.SCENE2;

            }
        }
    }

    private boolean verificarBack3(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }
    private void leerBack4() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBack4(x, y) == true) {
                estadoJuego =GameState.SCENE3;

            }
        }
    }

    private boolean verificarBack4(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }
    private void leerBack5() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBack5(x, y) == true) {
                estadoJuego =GameState.SCENE4;

            }
        }
    }

    private boolean verificarBack5(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerBack6() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarBack6(x, y) == true) {
                estadoJuego =GameState.SCENE5;

            }
        }
    }

    private boolean verificarBack6(float x, float y) {
        Sprite sprite = btnIzquierda.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSkip() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSkip1() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip1(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip1(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSkip2() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip2(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip2(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSkip3() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip3(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip3(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSkip4() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip4(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip4(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSkip5() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip5(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip5(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }



    private void leerSkip6() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            escenas.unproject(coordenadas);
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSkip6(x, y) == true) {
                plataforma.setScreen(new Menu(plataforma));

            }
        }
    }

    private boolean verificarSkip6(float x, float y) {
        Sprite sprite = btnSkip.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
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
        AssetManager assetManager = plataforma.getAssetManager();
        assetManager.unload("comic1.png");
        assetManager.unload("comic2.png");
        assetManager.unload("comic3.png");
        assetManager.unload("comic4.png");
        assetManager.unload("comic5.png");
        assetManager.unload("comic6.png");
        assetManager.unload("comic7.png");
        assetManager.clear();

    }

    public enum GameState{
        SCENE0,
        SCENE1,
        SCENE2,
        SCENE3,
        SCENE4,
        SCENE5,
        SCENE6
    }
}

