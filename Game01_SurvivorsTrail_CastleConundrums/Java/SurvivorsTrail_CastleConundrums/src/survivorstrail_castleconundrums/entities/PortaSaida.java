package survivorstrail_castleconundrums.entities;

import survivorstrail_castleconundrums.main.Game;

public class PortaSaida extends Itens {

    public PortaSaida(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height);

        setSprites(Itens.PORTA_DISABLE, Itens.PORTA_ENABLE);
        EnableColision();
    }

    public void tick() {
        if (Game.porta_liberada) {
            Enable();
            DisableColision();
        } else {
            Disable();
            EnableColision();
        }
    }
}
