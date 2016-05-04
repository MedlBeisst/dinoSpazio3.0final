package mx.itesm.dinospazio;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Intro implements Screen {

    private final Plataforma principal;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    private Texture introScreenTexture;
    private Fondo introFondo;

    float time = 0;

    public Intro (Plataforma principal) {
        this.principal = principal;
    }

    @Override
    public void show () {


        camara = new OrthographicCamera(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA);
        camara.position.set(Plataforma.ANCHO_CAMARA / 2, Plataforma.ALTO_CAMARA / 2, 0);
        camara.update();
        vista = new StretchViewport(Plataforma.ANCHO_CAMARA, Plataforma.ALTO_CAMARA, camara);

        loadResources();
        createObjects();

        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);

    }

    private void loadResources() {
        AssetManager assetManager = principal.getAssetManager();
        assetManager.load("tec.png", Texture.class);
        assetManager.finishLoading();
    }

    private void createObjects() {
        AssetManager assetManager = principal.getAssetManager();
        introScreenTexture = assetManager.get("tec.png");
        introFondo= new Fondo(introScreenTexture);
        introFondo.setSize(1280, 720);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        introFondo.render(batch);
        batch.end();

        time += delta;
        if (time > 3f){
            Gdx.app.log("Intro Screen", "IntroScreen Appears");
            principal.setScreen(new Menu(principal));
            //}

        }

    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        AssetManager assetManager = principal.getAssetManager();
        assetManager.unload("tec.png");
    }
}
