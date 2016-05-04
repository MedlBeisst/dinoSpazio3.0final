package mx.itesm.dinospazio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PantallaCargando3 implements Screen
{
    private final int nivel;
    private Plataforma plataforma;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Imagen cargando
    private Texture texturaCargando;
    private Sprite spriteCargando;

    // Textura Fondo
    private Texture texturaPantallaCargando;


    private AssetManager assetManager;  // Asset manager principal

    public PantallaCargando3(Plataforma plataforma, int nivel) {
        this.plataforma = plataforma;
        this.assetManager = plataforma.getAssetManager();
        this.nivel=nivel;
    }

    @Override
    public void show() {
        // Crea la cámara/vista
        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camara.update();
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA, camara);

        batch = new SpriteBatch();

        // Cargar recursos
        assetManager.load("cargando.png", Texture.class);
        assetManager.load("level3.png", Texture.class);
        assetManager.finishLoading();
        texturaCargando = assetManager.get("cargando.png");
        texturaPantallaCargando=assetManager.get("level3.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(Plataforma.ANCHO_CAMARA / 2 - spriteCargando.getWidth() / 2,
                Plataforma.ALTO_CAMARA / 2 - spriteCargando.getHeight() / 2);

        cargarRecursos();
    }

    // Carga los recursos a través del administrador de assets (siguiente pantalla)
    private void cargarRecursos() {

        // Carga los recursos de la siguiente pantalla (PantallaJuego)
        assetManager.load("mapaNivel3.tmx", TiledMap.class);  // Cargar info del mapa
        assetManager.load("caminar.png", Texture.class);    // Cargar imagen
        // Texturas de los botones
        assetManager.load("derecha.png", Texture.class);
        assetManager.load("izquierda.png", Texture.class);
        assetManager.load("arriba.png", Texture.class);
        // Boton pausa
        assetManager.load("btnPausa.png", Texture.class);
        assetManager.load("pantallaPausa.png", Texture.class);
        assetManager.load("continuar.png",Texture.class);
        assetManager.load("retroceder.png",Texture.class);
        //Sonido
        assetManager.load("sonido.png",Texture.class);
        assetManager.load("mute.png",Texture.class);
        // Fin del juego
        assetManager.load("YouLose.png", Texture.class);
        assetManager.load("YouWon.png", Texture.class);
        // Efecto al tomar la moneda
        assetManager.load("bite.mp3", Sound.class);
        assetManager.load("The Boo! You Suck! Sound Effect.wav", Sound.class);
        assetManager.load("musicaFondo.mp3", Sound.class);


    }

    @Override
    public void render(float delta) {

        // Actualizar carga
        actualizar();

        // Dibujar
        borrarPantalla();
        spriteCargando.setRotation(spriteCargando.getRotation() + 15);

        batch.setProjectionMatrix(camara.combined);

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();
        batch.draw(texturaPantallaCargando, 0, 0);
        spriteCargando.draw(batch);
        spriteCargando.setAlpha(.3f);
        batch.end();
    }

    private void actualizar() {

        if (assetManager.update()) {
            // Terminó la carga, cambiar de pantalla
            plataforma.setScreen(new Nivel3(plataforma));
        } else {
            // Aún no termina la carga de assets, leer el avance
            float avance = assetManager.getProgress()*100;
            Gdx.app.log("Cargando",avance+"%");
        }
    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0.42f, 0.55f, 1, 1);    // r, g, b, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
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
        texturaCargando.dispose();
        //texturaPantallaCargando.dispose();
        // Los assets de PantallaJuego se liberan en el método dispose de PantallaJuego
    }
}
