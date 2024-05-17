package survivorstrail_castleconundrums.Itens;

public class BotaoVermelho extends Itens {

    public static boolean pressionado = false;

    public BotaoVermelho(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height);

        setSprites(Itens.BOTAO_VERMELHO_DISABLE, Itens.BOTAO_ENABLE);
    }

   
}
