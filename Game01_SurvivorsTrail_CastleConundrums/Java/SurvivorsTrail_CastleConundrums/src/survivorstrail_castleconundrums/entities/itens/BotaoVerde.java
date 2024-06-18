package survivorstrail_castleconundrums.entities.itens;

import survivorstrail_castleconundrums.entities.Itens;

public class BotaoVerde extends Itens {

    public BotaoVerde(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height);

        setSprites(Itens.BOTAO_VERDE_DISABLE,Itens.BOTAO_ENABLE);
    }

    

}
