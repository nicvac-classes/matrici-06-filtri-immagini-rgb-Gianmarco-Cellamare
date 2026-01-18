import utils.ImageRGB;

public class Esercizio {

    public static void filtroRosso(int[][] R, int[][] G, int[][] B) {
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                G[i][j] = 0;
                B[i][j] = 0;
            }
        }
    }

    public static void filtroBW(int[][] R, int[][] G, int[][] B) {
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                int media = (R[i][j] + G[i][j] + B[i][j]) / 3;
                R[i][j] = media;
                G[i][j] = media;
                B[i][j] = media;
            }
        }
    }

    public static void filtroCentro(int[][] R, int[][] G, int[][] B) {
        int centroI = G.length / 2;
        int centroJ = G[0].length / 2;
        double distanzaMax = Math.sqrt(centroI * centroI + centroJ * centroJ);

        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                double di = Math.abs(i - centroI);
                double dj = Math.abs(j - centroJ);
                double distanza = Math.sqrt(di * di + dj * dj);
                double fattore = 1.0 - (distanza / distanzaMax);

                R[i][j] *= fattore;
                G[i][j] *= fattore;
                B[i][j] *= fattore;
            }
        }
    }

    public static void filtroGlitch(int[][] R, int[][] G, int[][] B) {
        int righe = R.length;
        int colonne = R[0].length;

        int[][] R_orig = new int[righe][colonne];
        int[][] B_orig = new int[righe][colonne];

        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                R_orig[i][j] = R[i][j];
                B_orig[i][j] = B[i][j];
                R[i][j] = 0;
                B[i][j] = 0;
            }
        }

        int spostamento = 10;

        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                int iR = i - spostamento;
                int jR = j - spostamento;
                int iB = i + spostamento;
                int jB = j + spostamento;

                if (iR >= 0 && jR >= 0)
                    R[iR][jR] = R_orig[i][j];

                if (iB < righe && jB < colonne)
                    B[iB][jB] = B_orig[i][j];
            }
        }
    }

    public static void filtroMare(int[][] R, int[][] G, int[][] B) {
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                if (!(R[i][j] < 127 && G[i][j] < 127 && B[i][j] >= 127)) {
                    R[i][j] = 0;
                    G[i][j] = 0;
                    B[i][j] = 0;
                }
            }
        }
    }

    public static void filtroSpiaggia(int[][] R, int[][] G, int[][] B) {
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                if (!(R[i][j] >= 127 && G[i][j] >= 127 && B[i][j] < 127)) {
                    R[i][j] = 0;
                    G[i][j] = 0;
                    B[i][j] = 0;
                }
            }
        }
    }

    public static void filtroVegetazione(int[][] R, int[][] G, int[][] B) {
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[0].length; j++) {
                if (!(R[i][j] < 100 && G[i][j] >= 100 && B[i][j] < 100)) {
                    R[i][j] = 0;
                    G[i][j] = 0;
                    B[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            String nomeFileIn = "faro";

            ImageRGB.RGB rgb = ImageRGB.extractRGB("immagini/" + nomeFileIn + ".png");
            int[][] R = rgb.R();
            int[][] G = rgb.G();
            int[][] B = rgb.B();

            System.out.println("Dimensione immagine: " + R.length + "x" + R[0].length);

            // SCEGLI UN FILTRO
            filtroGlitch(R, G, B);
            // filtroBW(R, G, B);
            // filtroRosso(R, G, B);

            ImageRGB.writeRGB(R, G, B, "output_" + nomeFileIn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
