package es.mcg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * <p>En este juego de un unico jugador consiste en encontrar el tesoro (T) en el cuadro MxM, donde M >= 4.
 * El juego contiene las siguientes posiciones:
 * <ul>
 *  <li>Tesoro (T): posicion unica en el juego, finaliza y el juego habra ganado.</li>
 *  <li>Bomba (B): existira una posicion unica en el juego, si lo encuentra, finaliza y el juego habra perdido.</li>
 *  <li>Nada (N): seran las posiciones restantes en el mapa y no seran significativas. El usuario podra seguir jugando
 * si da con una de ellas.</li>
 * </ul>
 * </p>
 * <p>Incialmente, el programa preguntara al usuario el numero de las dimensiones del tablero. Una vez recibido
 * el valor correcto, generara el tablero.</p>
 * <p>Una vez creado, empezara el juego, donde el usuario debera ir introduciendo la coordenada (fila, columna).
 * El juego finalizara solo cuando se encuentre el tesoro o la bomba.</p>
 * <p>Cuando acabe el juego, generara un tablero resultante donde se encontraba la bomba y el tesoro y los
 * intentos realizados, y los cargara en el fichero.</p>
 */
public class JuegoEncuentraTesoro {
    /**
     * Logger Manager
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Metodo principal del juego
     * @param args
     */
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        //Inicalizamos las constantes vacio, mina, tesoro e intento
        final int VACIO = 0;
        final int MINA = 1;
        final int TESORO = 2;
        final int INTENTO = 3;
        File file = null;
        FileWriter fw = null;
        PrintWriter pw = null;
        try 
        {
            //Creamos el fichero llamado 'ResultadoJuego.txt'
            file = new File("ResultadoJuego.txt");
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            //Imprimimos el titulo en el fichero del resultado del juego
            pw.println("Resultado del juego encuentra el tesoro\n");
            //Pedimos el numero de las dimensiones del juego
            int dimensiones = 0;
            while(dimensiones < 4)
            {
                System.out.println("Dime el numero de dimensiones");
                dimensiones = sc.nextInt();
                //Si el numero es menor de 4 volvemos a pedir el numero de dimensiones
                if(dimensiones < 4)
                {
                    System.out.println("Debe introducir un numero de dimensiones mayor que 4");
                }
                //Si el numero es mayor de 4 empieza a pintar el cuadro
            }
            int x, y;
            int[][] cuadrante = new int[dimensiones][dimensiones];
            //Pintamos las dimensiones
            for(x = 0; x < dimensiones; x++)
            {
                for(y = 0; y < dimensiones; y++)
                {
                    cuadrante[x][y] = VACIO;
                }
            }
            //Colocamos la mina
            int minaX = (int)Math.floor(Math.random()*dimensiones);
            int minaY = (int)Math.floor(Math.random()*dimensiones);
            cuadrante[minaX][minaY] = MINA;
            //Colocamos el tesoro
            int tesoroX, tesoroY;
            do
            {
                tesoroX = (int)Math.floor(Math.random()*dimensiones);
                tesoroY = (int)Math.floor(Math.random()*dimensiones);
            }while((minaX == tesoroX) && (minaY == tesoroY));
            cuadrante[tesoroX][tesoroY] = TESORO;
            //A partir de este codigo empieza el juego
            System.out.println("********************");
            System.out.println("* BUSCA EL TESORO! *");
            System.out.println("********************");

            boolean salir = false;
            String c = "";
            //Mientras que no haya tesoro o bomba el juego no se acaba
            do
            {
                for(y = (dimensiones-1); y >= 0; y--)
                {
                    System.out.print(y + " | ");
                    for(x = 0; x < dimensiones; x++)
                    {
                        if(cuadrante[x][y] == INTENTO)
                        {
                            System.out.print(" X ");
                        }
                        else
                        {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
                System.out.println("   ----------");
                System.out.print("   ");
                for(x = 0; x < dimensiones; x++)
                {
                    System.out.print(x+ " ");
                }
                System.out.println();

                System.out.print("Coordenada X: ");
                x = sc.nextInt();
                System.out.print("Coordenada Y: ");
                y = sc.nextInt();

                switch(cuadrante[x][y])
                {
                    //Si no hay nada se marca una X
                    case VACIO: cuadrante[x][y] = INTENTO; break;
                    //Si encuentra la mina, pierde la partida y finaliza el juego
                    case MINA: System.out.println("Lo siento, has perdido"); salir = true; break;
                    //Si encuentra el tesoro, gana la partida y finaliza el juego
                    case TESORO: System.out.println("Enhorabuena! Has encontrado el tesoro!"); salir = true; break;
                    default:
                }
            }while(!salir);
            //Al terminar la partida, pintara el cuadro del resultado donde estaba ubicada la bomba y el tesoro
            for(y = (dimensiones-1); y >= 0; y--)
            {
                System.out.print(y + "  ");
                pw.print(y + "  ");
                for(x = 0; x < dimensiones; x++)
                {
                    switch(cuadrante[x][y])
                    {
                        //En los cuadros no asignados no se pone nada
                        case VACIO: c = "  "; break;
                        //En el cuadro donde estaba la mina se pone una B
                        case MINA: c = " B "; break;
                        //En el cuadro donde estaba el tesoro se pone una T
                        case TESORO: c = " T "; break;
                        //En los cuadros asignados donde no habia nada cambiamos la X por la N
                        case INTENTO: c = " N "; break;
                        default:
                    }
                    //Pintamos el cuadro por la consola y lo guardamos en el fichero
                    System.out.print(c);
                    pw.print(c);
                }
                System.out.println();
                pw.println();
            }
            System.out.println("   ----------");
            pw.println("   ----------");
            System.out.print("   ");
            pw.print("   ");
            for(x = 0; x < dimensiones; x++)
            {
                System.out.print(x+ " ");
                pw.print(x + " ");
            }
            System.out.println();
            pw.println();
            //Guardamos los cambios en el fichero
            pw.flush();
            //Fin del programa
        } 
        catch (IOException ioException) 
        {
            ioException.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException aException)
        {
            //Logger que indica el error del numero fuera de las coordenadas del cuadro
            LOGGER.error("An error ocurred. The number entered is out of the coordinates of the frame");
            aException.printStackTrace();
        }
        finally
        {
            if(sc != null)
            {
                sc.close();
            }
            if(fw != null)
            {
                try 
                {
                    fw.close();    
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
