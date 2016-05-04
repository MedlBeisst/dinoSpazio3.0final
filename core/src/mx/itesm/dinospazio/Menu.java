package mx.itesm.dinospazio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu implements Screen
{
    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)
    private Plataforma plataforma;

    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;

    // Objeto para dibujar en la pantalla
    private SpriteBatch batch;

    // Fondo
    private Texture texturaMenu;



    // Opciones
    private Texture texturaPlay;
    private Texture texturaAbout;
    private Texture texturaSalir;//agregado
    private Texture texturaHistoria; // agregado
    private Boton btnPlay;
    private Boton btnAbout;
    private Boton btnSalir;//agregado
    private Boton btnHistoria;//agregado

    //Audio Menu
    //private Music MusicMenu;

    //Boton Mute
    //private  Texture mute;
    //private Boton btnMute;

    // Boton sonido
    //private Texture sonido;
    //private Boton btnSonido;

    public Menu(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    /*
    Se ejecuta al mostrar este Screen como pantalla de la app
     */
    @Override
    public void show() {

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        // Crea la cámara/vista
        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camara.update();
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA, camara);

        cargarRecursos();
        crearObjetos();
        batch = new SpriteBatch();







    }

    // Carga los recursos a través del administrador de assets
    private void cargarRecursos() {
        // Cargar las texturas/mapas
        AssetManager assetManager = plataforma.getAssetManager();// Referencia al assetManager
        //assetManager.load("musicaFondo.pm3", Music.class);
        assetManager.load("fondoMenuu.png", Texture.class);    // Cargar imagen
        assetManager.load("btnAbout.png", Texture.class);
        assetManager.load("btnPlay.png", Texture.class);
        //assetManager.load("sonido.png", Texture.class);//sonido
        //assetManager.load("mute.png", Texture.class);// Mute
        assetManager.load("btnStory.png", Texture.class);
        assetManager.load("exit2.png", Texture.class);// Boton salir



        // Se bloquea hasta que cargue todos los recursos
        assetManager.finishLoading();
    }

    private void crearObjetos() {
        AssetManager assetManager = plataforma.getAssetManager();   // Referencia al assetManager
        // Carga el mapa en memoria
        //MusicMenu = Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3"));
        //MusicMenu=assetManager.get("musicaFondo.mp3");
        texturaMenu = assetManager.get("fondoMenuu.png");
        texturaPlay = assetManager.get("btnPlay.png");
        texturaAbout = assetManager.get("btnAbout.png");
        texturaHistoria=assetManager.get("btnStory.png");// boton historia
        texturaSalir= assetManager.get("exit2.png");// boton salir
        //mute = assetManager.get("sonido.png");
//        sonido = assetManager.get("mute.png");

        //      MusicMenu.setLooping(true);
        //    MusicMenu.play();

        btnAbout = new Boton(texturaAbout);
        btnAbout.setPosicion(Plataforma.ANCHO_CAMARA / 2 - texturaAbout.getWidth() / 2,
                Plataforma.ALTO_CAMARA / 11 - texturaAbout.getHeight() / 2);

        btnAbout.setAlfa(0.7f);

        btnPlay = new Boton(texturaPlay);
        btnPlay.setPosicion(Plataforma.ANCHO_CAMARA / 2 - texturaPlay.getWidth() / 2,
                Plataforma.ALTO_CAMARA / 3 - texturaPlay.getHeight() / 2);
        btnPlay.setAlfa(0.7f);

        btnHistoria = new Boton(texturaHistoria);
        btnHistoria.setPosicion(Plataforma.ANCHO_CAMARA / 2 - texturaHistoria.getWidth() / 2,
                Plataforma.ALTO_CAMARA / 4 - texturaHistoria.getHeight() / 1);
        btnHistoria.setAlfa(0.7f);

        btnSalir = new Boton(texturaSalir);
        btnSalir.setPosicion(Plataforma.ANCHO_CAMARA / 16 - texturaSalir.getWidth() / 3,
                Plataforma.ALTO_CAMARA / 1 - texturaSalir.getHeight() / 1);
        btnSalir.setAlfa(0.7f);

        //Botón Mute
        // btnMute = new Boton(mute);
        //btnMute.setSize(100, 100);
        //btnMute.setPosicion(1170, 10);
        //Botón Unmute
        //btnSonido = new Boton(sonido);
        //btnSonido.setSize(100, 100);
        //btnSonido.setPosicion(1170, 10); //10/600


    }

    /*
    Dibuja TODOS los elementos del juego en la pantalla.
    Este método se está ejecutando muchas veces por segundo.
     */
    @Override
    public void render(float delta) { // delta es el tiempo entre frames (Gdx.graphics.getDeltaTime())

        // Dibujar
        borrarPantalla();
//        leerMute();

        batch.setProjectionMatrix(camara.combined);

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();
        /*if (musicaFondo1.isPlaying()){
            btnMute.render(batch);
        }
        else{
            btnSonido.render(batch);
        }*/
        batch.draw(texturaMenu, 0, 0);
        btnAbout.render(batch);
        btnPlay.render(batch);
        btnSalir.render(batch);
        btnHistoria.render(batch);
        //if (MusicMenu.isPlaying()){
        //  btnSonido.render(batch);
        //}
        //else{
        //  btnMute.render(batch);
        //}
        //musicaFondo1.render(batch);


        //efecto.draw(batch, delta);
        //efecto1.draw(batch, delta);
        batch.end();
    }

   /* private void leerMute() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarMute(x, y) == true) {

                if (MusicMenu.isPlaying()){
                    //Gdx.app.log("LeerMute", "Tap sobre el boton - Mute");
                    MusicMenu.pause();
                }
                else{
                    //Gdx.app.log("LeerUnmute", "Tap sobre el boton - Unmute");
                    MusicMenu.play();
                }
            }
        }
    }


    private boolean verificarMute(float x, float y) {
        Sprite sprite = btnMute.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }*/



    private void borrarPantalla() {
        Gdx.gl.glClearColor(0.42f, 0.55f, 1, 1);    // r, g, b, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        //if (MusicMenu.isPlaying()) {
        //  MusicMenu.setVolume(0);
        //MusicMenu.pause();
        //}
        //else{
        //if (MusicMenu.setVolume(0);){
        ///  MusicMenu.play();
        //}
        //MusicMenu.stop();

    }

    // Libera los assets
    @Override
    public void dispose() {
        // Los assets se liberan a través del assetManager
        AssetManager assetManager = plataforma.getAssetManager();
        assetManager.unload("fondoMenuu.png");
        assetManager.unload("btnPlay.png");
        assetManager.unload("btnAbout.png");
        assetManager.unload("btnStory.png");
        assetManager.unload("exit2.png");
        //assetManager.unload("musicaFondo.mp3");
        //assetManager.unload("sonido.png");
        //assetManager.unload("mute.png");

        assetManager.dispose();
        assetManager.clear();


    }

    /*
    Clase utilizada para manejar los eventos de touch en la pantalla
     */
    public class ProcesadorEntrada extends InputAdapter
    {
        private Vector3 coordenadas = new Vector3();
        private float x, y;     // Las coordenadas en la pantalla

        /*
        Se ejecuta cuando el usuario PONE un dedo sobre la pantalla, los dos primeros parámetros
        son las coordenadas relativas a la pantalla física (0,0) en la esquina superior izquierda
        pointer - es el número de dedo que se pone en la pantalla, el primero es 0
        button - el botón del mouse
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);


            return true;
        }

        /*
        Se ejecuta cuando el usuario QUITA el dedo de la pantalla.
         */
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);

            if (btnAbout.contiene(x,y)) {
                plataforma.setScreen(new AcercaDe(plataforma));
            }else
            if (btnPlay.contiene(x,y)) {
                plataforma.setScreen(new PantallaCargando(plataforma, 1 ));
            }else
            if (btnHistoria.contiene(x,y)) {
                plataforma.setScreen(new Historia(plataforma));

            }


            else if (btnSalir.contiene(x,y)){
                Gdx.app.exit();
            }

            return true;    // Indica que ya procesó el evento
        }


        // Se ejecuta cuando el usuario MUEVE el dedo sobre la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            transformarCoordenadas(screenX, screenY);

            return true;
        }


        private void transformarCoordenadas(int screenX, int screenY) {
            // Transformar las coordenadas de la pantalla física a la cámara HUD
            coordenadas.set(screenX, screenY, 0);
            camara.unproject(coordenadas);
            // Obtiene las coordenadas relativas a la pantalla virtual
            x = coordenadas.x;
            y = coordenadas.y;
        }
    }

}