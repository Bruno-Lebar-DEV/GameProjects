package survivorstrail_castleconundrums.entities.itens;

import survivorstrail_castleconundrums.entities.Itens;
import survivorstrail_castleconundrums.main.Game;
import survivorstrail_castleconundrums.main.GameController;

public class Plataforma extends Itens {

    public Plataforma(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height);

        setSprites(Itens.PLATAFORMA_DISABLE, Itens.PLATAFORMA_ENABLE);

        setIdPlataforma();

        if (GameController.DEV_DEBUG) {
            if (ID_RELATION != 0) {
                showMensagem = true;
                Mensagem = "" + ID_RELATION;
            }
        }
    }

    public void setIdPlataforma() {
        int newID = 1;

        for (int i = 0; i < Game.itens.size(); i++) {
            Itens item = Game.itens.get(i);
            if (item instanceof Plataforma) {
                if (item.ID_RELATION != 0)
                    newID++;
            }
        }

        this.ID_RELATION = newID;
    }

    public void tick() {
        if (isAtivo())
            this.DisableColision();
        else
            this.EnableColision();
    }
}
