package es.mcg;

import java.util.Scanner;

public class JuegoEncuentraTesoro {
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        final int VACIO = 0;
        final int MINA = 1;
        final int TESORO = 2;
        final int INTENTO = 3;

        int x, y;
        System.out.print("Nº columnas: ");
        int b = sc.nextInt();
        System.out.print("Nº filas: ");
        int a = sc.nextInt();
        int[][] cuadrante;
        if(a < 4)
        {
            System.err.println("ERROR. La dimension horizontal del tablero es menor que 4");
            System.exit(2);
        }
        else if(b < 4)
        {
            System.err.println("ERROR. La dimension vertical del tablero es menor que 4");
            System.exit(2);
        }
        cuadrante = new int[b][a];

        for(x = 0; x < (b-1); x++)
        {
            for(y = 0; x < (a-1); y++)
            {
                cuadrante[x][y] = VACIO;
            }
        }
        int minaX, minaY;
        minaX = (int)Math.floor(Math.random() * a);
        minaY = (int)Math.floor(Math.random() * b);
        cuadrante[minaX][minaY] = MINA;

        int tesoroX, tesoroY;
        do
        {
            tesoroX = (int)Math.floor(Math.random() * a);
            tesoroY = (int)Math.floor(Math.random() * b);
        }while((minaX == tesoroX) && (minaY == tesoroY));
        cuadrante[tesoroX][tesoroY] = TESORO;

        System.out.println("************************");
        System.out.println("* ENCUENTRA EL TESORO! *");
        System.out.println("************************");

        boolean salir = false;
        String c = "";
        do
        {
            for(y = (a-1); y >= 0; y--)
            {
                System.out.print("|");
                for(x = 0; x < (b-1); x++)
                {
                    if(cuadrante[x][y] == INTENTO)
                    {
                        System.out.print("X ");
                    }
                    else
                    {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            System.out.println("\t-----------------");

            System.out.print("Coordenada X: ");
            x = sc.nextInt();
            System.out.print("Coordenada Y: ");
            y = sc.nextInt();

            switch(cuadrante[x][y])
            {
                case VACIO: cuadrante[x][y] = INTENTO; break;
                case MINA: {
                    System.out.println("Lo siento. Has perdido");
                    salir = true;
                }
                break;
                case TESORO: {
                    System.out.println("Felicidades. Has encontrado el tesoro");
                    salir = true;
                }
                break;
                default:
            }
        }while(!salir);

        for(y = (a-1); y >= 0; y--)
        {
            System.out.print(" ");
            for(x = 0; x < (b-1); x++)
            {
                switch(cuadrante[x][y])
                {
                    case VACIO: c = "  "; break;
                    case MINA: c = "B "; break;
                    case TESORO: c = "T "; break;
                    case INTENTO: c = "X "; break;
                    default:
                }
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("\t-----------------");
    }
}
