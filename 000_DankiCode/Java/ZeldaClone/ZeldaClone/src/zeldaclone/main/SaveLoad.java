package zeldaclone.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveLoad {

    public static final int MAX_SAVES = 5;
    public static final int NOT_SAVE = -1;

    public static int SAVE_ATUAL = NOT_SAVE;
    public static boolean EXIST_SAVE = false;
    public static String[][] LIST_SAVES;

    public SaveLoad() {
        String[][] listSaves = new String[MAX_SAVES][5];
        int line = 0;
        File file = new File("saves.txt");

        if (file.exists()) {
            try {
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("saves.txt"));

                try {
                    while ((singleLine = reader.readLine()) != null) {
                        String[] trans = singleLine.split("|");

                        // Posiciona no save correto
                        line = Integer.parseInt(trans[0]) - 1;

                        listSaves[line][0] = trans[0]; // Numero do Save (1 a MAX_SAVES)
                        listSaves[line][1] = trans[1]; // Nome do Save
                        listSaves[line][2] = trans[2]; // Nivel do Save
                        listSaves[line][3] = trans[3]; // Tempo do Save
                        listSaves[line][4] = trans[4]; // Encode do Save

                        EXIST_SAVE = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        LIST_SAVES = listSaves;
    }

    public static void saveGame() {
        int encode = 8;

        if (SAVE_ATUAL > NOT_SAVE) {
            encode = Integer.parseInt(LIST_SAVES[SAVE_ATUAL][4]);
        } else {
            // Escolha onde salvar
        }

        String[] opt1 = { "level", "life", "ammo", "weapon" };

        int weapon = 0;
        if (Game.player.weapon)
            weapon = 1;

        int[] opt2 = { Game.CUR_LEVEL, (int) Game.player.life, Game.player.ammos, weapon };

        aplySaveGame(opt1, opt2, encode);

        Menu.gameSalvo = true;
    }

    public static void aplySaveGame(String[] val1, int[] val2, int encode) {
        BufferedWriter write = null;
        try {
            String arquivo = "save" + SAVE_ATUAL + ".txt";
            write = new BufferedWriter(new FileWriter(arquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < val1.length; i++) {
            String current = val1[i];
            current += ":";

            char[] value = Double.toString(val2[i]).toCharArray();

            for (int n = 0; n < value.length; n++) {
                value[n] += encode;
                current += value[n];
            }

            try {
                write.write(current);
                if (i < val1.length - 1)
                    write.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            write.flush();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean loadGame() {
        String txtSave = getLoadGame(Integer.parseInt(LIST_SAVES[SAVE_ATUAL][4]));
        if (txtSave == null)
            return false;

        aplySaveLoad(txtSave);
        return true;
    }

    public static String getLoadGame(int encode) {
        String line = "";
        File file = new File("save.txt");

        if (file.exists()) {
            try {
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("save.txt"));

                try {
                    while ((singleLine = reader.readLine()) != null) {
                        String[] trans = singleLine.split(":");
                        char[] val = trans[1].toCharArray();
                        trans[1] = "";
                        for (int i = 0; i < val.length; i++) {
                            val[i] -= encode;
                            trans[1] += val[i];
                        }
                        line += trans[0];
                        line += ":";
                        line += trans[1];
                        line += "/";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return line;
    }

    public static void aplySaveLoad(String str) {
        String[] spl = str.split("/");

        for (int i = 0; i < spl.length; i++) {
            String[] spl2 = spl[i].split(":");
            switch (spl2[0]) {
                case "level":
                    Game.gameState = "NORMAL";
                    Game.CUR_LEVEL = (int) Double.parseDouble(spl2[1]);
                    Game.resetGame();
                    break;
                case "life":
                    Game.player.life = Double.parseDouble(spl2[1]);
                case "ammo":
                    Game.player.ammos = (int) Double.parseDouble(spl2[1]);
                default:
                    break;
            }

        }
    }

}
