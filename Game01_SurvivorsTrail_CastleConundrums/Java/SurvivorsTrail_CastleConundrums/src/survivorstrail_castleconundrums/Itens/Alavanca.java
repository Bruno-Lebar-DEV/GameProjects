package survivorstrail_castleconundrums.Itens;

import survivorstrail_castleconundrums.main.Game;
import survivorstrail_castleconundrums.main.GameController;

public class Alavanca extends Itens {

    public Alavanca(double _x, double _y, int _width, int _height) {
        super(_x, _y, _width, _height);

        setSprites(Itens.ALAVANCA_DISABLE, Itens.ALAVANCA_ENABLE);
        
        isInteragivel = true;

        setIdAlavanca();

        if (GameController.DEV_DEBUG) {
            if (ID_RELATION != 0) {
                showMensagem = true;
                Mensagem = "" + ID_RELATION;
            }
        }
    }

    public void setIdAlavanca() {
        int newID = 1;

        for (int i = 0; i < Game.itens.size(); i++) {
            Itens item = Game.itens.get(i);
            if (item instanceof Alavanca) {
                if (item.ID_RELATION != 0)
                    newID++;
            }
        }

        ID_RELATION = newID;
    }

    public void tick() {
        if (ID_RELATION != 0) {
            for (int i = 0; i < Game.itens.size(); i++) {
                Itens item = Game.itens.get(i);
                if (item instanceof Plataforma) {
                    if (item.ID_RELATION == ID_RELATION) {
                        if (isAtivo()) {
                            item.Enable();
                        } else {
                            item.Disable();
                        }
                    }
                }
            }
        }
    }

}
