package mx.itesm.dinospazio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego implements Screen
{



    public static final float ANCHO_MAPA =1280;   // Ancho del mapa en pixeles  2560
    public static final int TAM_CELDA = 16;
    // Referencia al objeto de tipo Game (tiene setScreen para cambiar de pantalla)
    private Plataforma plataforma;
    // La cámara y vista principal
    private OrthographicCamera camara;
    private Viewport vista;
    // Objeto para dibujar en la pantalla
    private SpriteBatch batch;
    // Opciones

    // MAPA
    private TiledMap mapa;      // Información del mapa en memoria
    private OrthogonalTiledMapRenderer rendererMapa;    // Objeto para dibujar el mapa
    // Personaje
    private Texture texturaPersonaje;       // Aquí cargamos la imagen marioSprite.png con varios frames
    private Personaje dino;
    // HUD. Los componentes en la pantalla que no se mueven
    private OrthographicCamera camaraHUD;   // Cámara fija
    // Botones izquierda/derecha
    private Texture texturaBtnIzquierda;
    private Boton btnIzquierda;
    private Texture texturaBtnDerecha;
    private Boton btnDerecha;
    // Botón saltar
    private Texture texturaSalto;
    private Boton btnSalto;

    // Estrellas recolectadas
    private int pizzaBuena;
    private Texto texto;
    private Sound sonidoBite;


    private Music musicaFondo;

    // Fin del juego, Gana o Pierde
    private Texture texturaGana;
    private Boton btnGana;

    private Texture texturaPierde;
    private Boton btnPierde;

    private Sound sonidoPierde;

    // Estados del juego
    private EstadosJuego estadoJuego;
    private int keycode;

    // Boton pausa

    private Texture Pausa;
    private Boton btnPausa;

    private Fondo menuPausa;
    private Texture texturaMenuPausa;

    // Seguir jugando
    private Texture continuar;
    private Boton btnContinuar;

    // Salir
    private Texture salir;
    private Boton btnSalir;

    // Sonido
    private Texture sonido;
    private Boton btnSonido;

    // Mute
    private Texture mute;
    private Boton btnMute;





    public PantallaJuego(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    /*
    Se ejecuta al mostrar este Screen como pantalla de la app
     */
    @Override
    public void show() {
        // Crea la cámara/vista
        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 4, Plataforma.ALTO_CAMARA / 4, 0);
        camara.update();
        //Aquí es donde se tiene el ancho y alto de la salida de la cámara pero
        //no sigue al personaje
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA/2, Plataforma.ALTO_CAMARA/2, camara);

        batch = new SpriteBatch();

        // Cámara para HUD
        camaraHUD = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camaraHUD.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camaraHUD.update();

        //cargarRecursos();
        crearObjetos();

        // Indicar el objeto que atiende los eventos de touch (entrada en general)
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

        estadoJuego = EstadosJuego.JUGANDO;

        // Texto
        texto = new Texto();
        musicaFondo= Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3"));
        musicaFondo.setLooping(true);   ///Infinito
        musicaFondo.play();

    }

    // LOS RECURSOS SE CARGAN AHORA EN PantallaCargando

    // Carga los recursos a través del administrador de assets
    private void cargarRecursos() {

        // Cargar las texturas/mapas
        AssetManager assetManager = plataforma.getAssetManager();   // Referencia al assetManager
        assetManager.load("mapaNivel1a.tmx", TiledMap.class);  // Cargar info del mapa    nivel 1
        assetManager.load("caminar.png", Texture.class);    // Cargar imagen

        // Texturas de los botones
        assetManager.load("derecha.png", Texture.class);
        assetManager.load("izquierda.png", Texture.class);
        assetManager.load("arriba.png", Texture.class);

        //Pausa
        assetManager.load("continuar.png",Texture.class);
        assetManager.load("btnPausa.png",Texture.class);
        assetManager.load("retroceder.png",Texture.class);
        assetManager.load("pantallaPausa.png",Texture.class);
        assetManager.load("sonido.png",Texture.class);
        assetManager.load("mute.png",Texture.class);



        // Fin del juego
        assetManager.load("YouWon.png", Texture.class);
        assetManager.load("YouLose.png", Texture.class);
        assetManager.load("musicaFondo.mp3", Sound.class);

        // Se bloquea hasta que cargue todos los recursos
        assetManager.finishLoading();
    }


    private void crearObjetos() {
        AssetManager assetManager = plataforma.getAssetManager();   // Referencia al assetManager
        // Carga el mapa en memoria

        mapa = assetManager.get("mapaNivel1a.tmx"); //nivel 1

        //mapa.getLayers().get(0).setVisible(false);    // Pueden ocultar una capa así

        // Crear el objeto que dibujará el mapa
        rendererMapa = new OrthogonalTiledMapRenderer(mapa,batch);
        rendererMapa.setView(camara);


        // Cargar frames
        texturaPersonaje = assetManager.get("caminar.png");

        // Crear el personaje
        dino = new Personaje(texturaPersonaje);

        // Posición inicial del personaje
        dino.getSprite().setPosition(Plataforma.ANCHO_CAMARA / 10, Plataforma.ALTO_CAMARA * 0.90f);

        // Crear los botones
        texturaBtnIzquierda = assetManager.get("izquierda.png");
        btnIzquierda = new Boton(texturaBtnIzquierda);
        btnIzquierda.setPosicion(TAM_CELDA, 5 * TAM_CELDA);
        btnIzquierda.setAlfa(0.7f); // Un poco de transparencia

        texturaBtnDerecha = assetManager.get("derecha.png");
        btnDerecha = new Boton(texturaBtnDerecha);
        btnDerecha.setPosicion(10* TAM_CELDA, 5 * TAM_CELDA);
        btnDerecha.setAlfa(0.7f); // Un poco de transparencia

        texturaSalto = assetManager.get("arriba.png");
        btnSalto = new Boton(texturaSalto);
        btnSalto.setPosicion(Plataforma.ANCHO_CAMARA - 5 * TAM_CELDA, 5 * TAM_CELDA);
        btnSalto.setAlfa(0.7f);

        // Gana
        texturaGana = assetManager.get("YouWon.png");
        btnGana = new Boton(texturaGana);
        btnGana.setPosicion(Plataforma.ANCHO_CAMARA / 2 - btnGana.getRectColision().width / 2,
                Plataforma.ALTO_CAMARA / 2 - btnGana.getRectColision().height / 2);
        btnGana.setAlfa(1.0f);

        /// Pierde
        texturaPierde = assetManager.get("YouLose.png");
        btnPierde = new Boton(texturaPierde);
        btnPierde.setPosicion(Plataforma.ANCHO_CAMARA / 2 - btnPierde.getRectColision().width / 2,
                Plataforma.ALTO_CAMARA / 2 - btnPierde.getRectColision().height / 2);
        btnPierde.setAlfa(1.0f);

        // Efecto moneda
        sonidoBite = assetManager.get("bite.mp3");
        sonidoPierde = assetManager.get("The Boo! You Suck! Sound Effect.wav");
        //Pausa
        Pausa = assetManager.get("btnPausa.png");
        btnPausa = new Boton (Pausa);
        btnPausa.setSize(100, 100);
        //btnPause.setPosicion(400, 600);
        btnPausa.setPosicion(1180,600);
        btnPausa.setAlfa(1f);



    }

    /*
    Dibuja TODOS los elementos del juego en la pantalla.
    Este método se está ejecutando muchas veces por segundo.
     */
    @Override
    public void render(float delta) { // delta es el tiempo entre frames (Gdx.graphics.getDeltaTime())

        if (estadoJuego!=EstadosJuego.PERDIO) {
            // Actualizar objetos en la pantalla
            moverPersonaje();
            actualizarCamara(); // Mover la cámara para que siga al personaje
        }

        // Dibujar
        borrarPantalla();
        leerPausa();


        batch.setProjectionMatrix(camara.combined);

        rendererMapa.setView(camara);
        rendererMapa.render();  // Dibuja el mapa

        // Entre begin-end dibujamos nuestros objetos en pantalla
        batch.begin();

        dino.render(batch);    // Dibuja el personaje


        batch.end();
        batch.setProjectionMatrix(camaraHUD.combined);
        if (estadoJuego !=EstadosJuego.PERDIO) {

            // Dibuja el HUD: cosas estáticas que lleva la pantalla


            batch.begin();
            btnIzquierda.render(batch);
            btnDerecha.render(batch);
            btnSalto.render(batch);
            texto.mostrarMensaje(batch, "Pizzas: " + (pizzaBuena) / 4, Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA * 0.97f);

            batch.end();
        }

        batch.begin();

        //Perdió
        if (estadoJuego==EstadosJuego.PERDIO) {
            btnPierde.render(batch);

        }

        // ¿Ya ganó?
        if (estadoJuego==EstadosJuego.GANO || pizzaBuena>=32) {
            btnGana.render(batch);
        }

        batch.end();


        // estado del juego
        if (estadoJuego==EstadosJuego.JUGANDO) {
            batch.setProjectionMatrix(camara.combined);
            batch.setProjectionMatrix(camaraHUD.combined);

            batch.begin();

            btnSalto.render(batch);
            btnIzquierda.render(batch);
            btnDerecha.render(batch);
            btnPausa.render(batch);
            estadoJuego = EstadosJuego.JUGANDO;

            batch.end();
        }

        if (estadoJuego == EstadosJuego.PAUSADO) {
            batch.begin();
            batch.setProjectionMatrix(camaraHUD.combined);
            AssetManager assetManager = plataforma.getAssetManager();
            texturaMenuPausa = assetManager.get("pantallaPausa.png");
            continuar = assetManager.get("continuar.png");
            salir = assetManager.get("retroceder.png");
            sonido = assetManager.get("sonido.png");
            mute = assetManager.get("mute.png");


            menuPausa = new Fondo(texturaMenuPausa);
            //menuPausa.setAlfa(0.7f);

            menuPausa.setSize(400, 400);
            //menuPausa.setSize(500, 600);
            menuPausa.setPosicion(440, 300);

            menuPausa.render(batch);

            btnContinuar = new Boton(continuar);
            btnContinuar.setSize(200, 100);
            btnContinuar.setPosicion(450, 400);
            btnContinuar.render(batch);

            btnSalir = new Boton(salir);
            btnSalir.setSize(200, 100);
            //btnSalir.setPosicion(10,100);
            btnSalir.setPosicion(600, 400);// 580,400
            btnSalir.render(batch);

            // Mute
            btnMute = new Boton(mute);
            btnMute.setSize(100, 100);
            btnMute.setPosicion(500, 500);
            btnMute.render(batch);

            //Sonido
            btnSonido = new Boton(sonido);
            btnSonido.setSize(100, 100);
            btnSonido.setPosicion(650,500);
            btnSonido.render(batch);


            LeerContinuar();
            LeerSalir();
            leerMute();
            leerSonido();

            batch.end();
        }



    }

    // Actualiza la posición de la cámara para que el personaje esté en el centro,
    // excepto cuando está en la primera y última parte del mundo
    private void actualizarCamara() {
        float posX = dino.getX();
        // Si está en la parte 'media'
        if (posX>=Plataforma.ANCHO_CAMARA/4 && posX<=ANCHO_MAPA-Plataforma.ANCHO_CAMARA/4) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX, camara.position.y, 0);
        } else if (posX>ANCHO_MAPA-Plataforma.ANCHO_CAMARA/4) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA-Plataforma.ANCHO_CAMARA/4, camara.position.y, 0);
        }
        camara.update();
    }

    /*
    Movimiento del personaje. SIMPLIFICAR LOGICA :(
     */
    private void moverPersonaje() {
        // Prueba caída libre inicial o movimiento horizontal
        switch (dino.getEstadoMovimiento()) {
            case INICIANDO:     // Mueve el personaje en Y hasta que se encuentre sobre un bloque
                // Los bloques en el mapa son de 16x16
                // Calcula la celda donde estaría después de moverlo
                int celdaX = (int)(dino.getX()/ TAM_CELDA);
                int celdaY = (int) ((dino.getY() + Personaje.VELOCIDAD_Y) / TAM_CELDA);
                // Recuperamos la celda en esta posición
                // La capa 0 es el fondo
                TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(1);
                TiledMapTileLayer.Cell celda = capa.getCell(celdaX, celdaY);
                // probar si la celda está ocupada
                if (celda==null) {
                    // Celda vacía, entonces el personaje puede avanzar
                    dino.caer();
                }  else if ( !esBuena(celda) ) {  // Las pizzaBuena no lo detienen :)
                    // Dejarlo sobre la celda que lo detiene
                    dino.setPosicion(dino.getX(), (celdaY + 1) * TAM_CELDA);
                    dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                }
                break;
            case MOV_DERECHA:       // Se mueve horizontal
            case MOV_IZQUIERDA:
                probarChoqueParedes();      // Prueba si debe moverse
                break;
        }

        // Prueba si debe caer por llegar a un espacio vacío
        if ( dino.getEstadoMovimiento()!= Personaje.EstadoMovimiento.INICIANDO
                && (dino.getEstadoSalto() != Personaje.EstadoSalto.SUBIENDO) ) {
            // Calcula la celda donde estaría después de moverlo
            int celdaX = (int) (dino.getX() / TAM_CELDA);
            int celdaY = (int) ((dino.getY() + Personaje.VELOCIDAD_Y) / TAM_CELDA);
            // Recuperamos la celda en esta posición
            // La capa 0 es el fondo
            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(1);
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(celdaX, celdaY);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(celdaX+1, celdaY);
            // probar si la celda está ocupada
            if ( (celdaAbajo==null && celdaDerecha==null) || esBuena(celdaAbajo) || esBuena(celdaDerecha) ) {
                // Celda vacía, entonces el personaje puede avanzar
                dino.caer();
                dino.setEstadoSalto(Personaje.EstadoSalto.CAIDA_LIBRE);
            } else {
                // Dejarlo sobre la celda que lo detiene
                dino.setPosicion(dino.getX(), (celdaY + 1) * TAM_CELDA);
                dino.setEstadoSalto(Personaje.EstadoSalto.EN_PISO);

                if ( esDorada(celdaAbajo) || esDorada(celdaDerecha) ||pizzaBuena>=32) {
                    // La encontró!!!!
                    Gdx.app.log("moverPersonaje()", "estadoGANAR");
                    estadoJuego = EstadosJuego.GANO;
                    btnIzquierda.setAlfa(0.2f);
                    btnDerecha.setAlfa(0.2f);
                    btnSalto.setAlfa(0.2f);
                }
            }
        }

        // Saltar
        switch (dino.getEstadoSalto()) {
            case SUBIENDO:
            case BAJANDO:
                dino.actualizarSalto();    // Actualizar posición en 'y'
                break;
        }
    }

    // Prueba si puede moverse a la izquierda o derecha
    private void probarChoqueParedes() {
        Personaje.EstadoMovimiento estado = dino.getEstadoMovimiento();
        // Quitar porque este método sólo se llama cuando se está moviendo
        if ( estado!= Personaje.EstadoMovimiento.MOV_DERECHA && estado!=Personaje.EstadoMovimiento.MOV_IZQUIERDA){
            return;
        }
        float px = dino.getX();    // Posición actual
        // Posición después de actualizar
        px = dino.getEstadoMovimiento()==Personaje.EstadoMovimiento.MOV_DERECHA? px+Personaje.VELOCIDAD_X:
                px-Personaje.VELOCIDAD_X;
        int celdaX = (int)(px/TAM_CELDA);   // Casilla del personaje en X
        if (dino.getEstadoMovimiento()== Personaje.EstadoMovimiento.MOV_DERECHA) {
            celdaX++;   // Casilla del lado derecho
        }
        int celdaY = (int)(dino.getY()/TAM_CELDA); // Casilla del personaje en Y
        TiledMapTileLayer capaPlataforma = (TiledMapTileLayer) mapa.getLayers().get(1);
        if ( capaPlataforma.getCell(celdaX,celdaY) != null || capaPlataforma.getCell(celdaX,celdaY+1) != null ) {
            // Colisionará, dejamos de moverlo


            if ( esBuena(capaPlataforma.getCell(celdaX, celdaY)) ) {
                // Borrar esta estrella y contabilizar
                capaPlataforma.setCell(celdaX,celdaY,null);
                pizzaBuena++;
                Gdx.app.log("ProbarChoqueParedes","pizzaBuena="+pizzaBuena);
                sonidoBite.play();



            } else if ( esBuena(capaPlataforma.getCell(celdaX, celdaY + 1)) ) {
                // Borrar esta estrella y contabilizar
                capaPlataforma.setCell(celdaX,celdaY+1,null);
                pizzaBuena++;
                Gdx.app.log("ProbarChoqueParedes","pizzaBuena="+pizzaBuena);
                sonidoBite.play();



            } else if ( esMala(capaPlataforma.getCell(celdaX, celdaY)) ) {
                long Id = sonidoPierde.play();
                sonidoPierde.setVolume(Id,1);

                estadoJuego = EstadosJuego.PERDIO;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {

                        //        plataforma.setScreen(new Menu(plataforma));
                    }
                }, 3);  // 3 segundos
            } else {
                dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
            }
        } else {
            dino.actualizar();
        }
    }

    // Verifica si esta casilla tiene una moneda
    private boolean esDorada(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");

        return "pizzaDorada".equals(propiedad);
    }

    // Verifica si esta casilla tiene una estrella (simplificar con la anterior)
    private boolean esBuena(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");
        return "pizzaBuena".equals(propiedad);
    }

    // Verifica si es pausa

   public void leerPausa() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camaraHUD.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificaPausa(x, y) == true) {
                Gdx.app.log("LeerEntrada", "Tap sobre el boton - Pausa");
                //this.prototipoGame.pause();
                estadoJuego = EstadosJuego.PAUSADO;


            }
        }
    }

    private boolean verificaPausa(float x, float y) {
        Sprite sprite = btnPausa.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }



    // Verificar continuar y exit




    private void LeerSalir() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camaraHUD.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarExit(x, y) == true) {
                Gdx.app.log("LeerExit", "Salir a menu principal");
                //Gdx.app.exit();
                plataforma.setScreen(new Menu(plataforma));
            }
        }
    }

    private boolean verificarExit(float x, float y) {
        Sprite sprite = btnSalir.getSprite();

        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void LeerContinuar() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camaraHUD.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarContinuar(x, y) == true) {
                //Gdx.app.log("LeerContinuar", "Continuar Juego");
                estadoJuego = EstadosJuego.JUGANDO;
                //Gdx.app.exit();
                //principal.setScreen(new OptionScreen(principal));
            }
        }
    }

    private boolean verificarContinuar(float x, float y) {
        Sprite sprite = btnContinuar.getSprite();

        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    private void leerMute() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarMute(x, y) == true) {
                musicaFondo.stop();
                sonidoBite.stop();
                sonidoPierde.stop();
            }
        }
    }


    private boolean verificarMute(float x, float y) {
        Sprite sprite = btnMute.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    private void leerSonido() {
        if (Gdx.input.justTouched()) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);//Traduce las coordenadas
            float x = coordenadas.x;
            float y = coordenadas.y;
            if (verificarSonido(x, y) == true) {
                musicaFondo.play();
            }
        }
    }


    private boolean verificarSonido(float x, float y) {
        Sprite sprite = btnSonido.getSprite();
        return x>=sprite.getX() && x<=sprite.getX() + sprite.getWidth() &&
                y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }


    // Verifica si esta casilla tiene un hongo (simplificar con las anteriores)
    private boolean esMala(TiledMapTileLayer.Cell celda) {
        if (celda==null) {
            return false;
        }
        Object propiedad = celda.getTile().getProperties().get("tipo");
        return "pizzaMala".equals(propiedad);
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
        dispose();
    }

    // Libera los assets
    @Override
    public void dispose() {
        // Los assets se liberan a través del assetManager
        AssetManager assetManager = plataforma.getAssetManager();
       /* assetManager.unload("caminar.png");
        assetManager.unload("derecha.png");
        assetManager.unload("izquierda.png");
        assetManager.unload("YouWon.png");
        assetManager.unload("YouLose.png");
        assetManager.unload("mapaNivel1.tmx");
        assetManager.unload("home.png");
        assetManager.unload("musicaFondo.mp3");*/



        Gdx.app.log("dispose()","liberando");
        assetManager.clear();
    }
    public enum EstadosJuego {
        GANO,
        JUGANDO,
        PAUSADO,
        PERDIO,
    }

    /*
    Clase utilizada para manejar los eventos de touch en la pantalla
     */
    public class ProcesadorEntrada extends InputAdapter {
        private Vector3 coordenadas = new Vector3();
        private float x, y;     // Las coordenadas en la pantalla

        /*
        Se ejecuta cuando el usuario PONE un dedo sobre la pantalla, los dos primeros parámetros
        son las coordenadas relativas a la pantalla física (0,0) en la esquina superior izquierda
        pointer - es el número de dedo que se pone en la pantalla, el primero es 0
        button - el botón del mouse
         */
        @Override
        public boolean keyDown(int keycode) {
            if(estadoJuego == EstadosJuego.JUGANDO){
                if (keycode == Input.Keys.RIGHT) {
                    dino.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
                }
                if (keycode == Input.Keys.LEFT) {
                    dino.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
                }
                if (keycode == Input.Keys.SPACE) {
                    dino.saltar();
                }
            }
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);
            if (estadoJuego == EstadosJuego.JUGANDO) {
                // Preguntar si las coordenadas están sobre el botón derecho

                if (btnDerecha.contiene(x, y) && dino.getEstadoMovimiento() != Personaje.EstadoMovimiento.INICIANDO) {
                    // Tocó el botón derecha, hacer que el personaje se mueva a la derecha
                    dino.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
                } else if (btnIzquierda.contiene(x, y) && dino.getEstadoMovimiento() != Personaje.EstadoMovimiento.INICIANDO) {
                    // Tocó el botón izquierda, hacer que el personaje se mueva a la izquierda
                    dino.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
                } else if (btnSalto.contiene(x, y)) {
                    // Tocó el botón saltar
                    dino.saltar();
                }
            }

                if (estadoJuego == EstadosJuego.PAUSADO) {
                    // Preguntar si las coordenadas están sobre el botón derecho

                    if (btnDerecha.contiene(x, y) && dino.getEstadoMovimiento() != Personaje.EstadoMovimiento.INICIANDO) {
                        // Tocó el botón derecha, hacer que el personaje se mueva a la derecha
                        dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    } else if (btnIzquierda.contiene(x, y) && dino.getEstadoMovimiento() != Personaje.EstadoMovimiento.INICIANDO) {
                        // Tocó el botón izquierda, hacer que el personaje se mueva a la izquierda
                        dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    } else if (btnSalto.contiene(x, y)) {
                        // Tocó el botón saltar
                        dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);

                    }else if (btnSonido.contiene(x,y)){
                        if (musicaFondo.isLooping()){
                            musicaFondo.play();
                        }
                    }else if (btnMute.contiene(x,y)){
                        if(musicaFondo.isPlaying()){
                            musicaFondo.stop();
                            sonidoBite.stop();
                            sonidoPierde.stop();

                        }
                    }
                }


             else if (estadoJuego == EstadosJuego.GANO || pizzaBuena >= 32) {
                if (btnGana.contiene(x, y)) {
                    if (musicaFondo.isPlaying()) {
                        musicaFondo.stop();
                    }
                    //carga las cosas de la pantalla cargando
                    //plataforma.setScreen(new PantallaCargando(plataforma, 2));

                    //plataforma.setScreen(new Nivel2(plataforma));
                    plataforma.setScreen(new PantallaCargando2(plataforma,2));

                }
            } else if (estadoJuego == EstadosJuego.PERDIO) {

                if (btnPierde.contiene(x, y)) {
                    if (musicaFondo.isPlaying()) {
                        musicaFondo.stop();
                    }

                    plataforma.setScreen(new Menu(plataforma));
                }


            }

            return true;    // Indica que ya procesó el evento
        }

        /*
        Se ejecuta cuando el usuario QUITA el dedo de la pantalla.
         */
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);
            // Preguntar si las coordenadas son de algún botón para DETENER el movimiento

            if (dino.getEstadoMovimiento() != Personaje.EstadoMovimiento.INICIANDO && (btnDerecha.contiene(x, y) || btnIzquierda.contiene(x, y))) {
                // Tocó el botón derecha, hacer que el personaje se mueva a la derecha
                dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
            }
            return true;    // Indica que ya procesó el evento
        }


        // Se ejecuta cuando el usuario MUEVE el dedo sobre la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            transformarCoordenadas(screenX, screenY);
            // Acaba de salir de las fechas (y no es el botón de salto)
            if (x < Plataforma.ANCHO_CAMARA / 2 && dino.getEstadoMovimiento() != Personaje.EstadoMovimiento.QUIETO) {
                if (!btnIzquierda.contiene(x, y) && !btnDerecha.contiene(x, y)) {
                    dino.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                }
            }
            return true;
        }


        private void transformarCoordenadas(int screenX, int screenY) {
            // Transformar las coordenadas de la pantalla física a la cámara HUD
            coordenadas.set(screenX, screenY, 0);
            camaraHUD.unproject(coordenadas);
            // Obtiene las coordenadas relativas a la pantalla virtual
            x = coordenadas.x;
            y = coordenadas.y;
        }
    }
}

