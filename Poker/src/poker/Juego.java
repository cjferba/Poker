package poker;
import java.util.*;

/**
 *
 * @author Carlosbasso
 */

public abstract class Juego {
    protected BarajaFrancesa baraja = new BarajaFrancesa();
    protected LinkedList<JugadorPoker> jugadores = new LinkedList<JugadorPoker>();
    protected LinkedList<JugadorPoker> perdedores = new LinkedList<JugadorPoker>();
    protected LinkedList<JugadorPoker> plantados = new LinkedList<JugadorPoker>();
    protected int apuestas;
    
    Juego(){};
    
    Juego(int r,int ganancias,LinkedList<String> jugador) {
        this.inicializa(r,ganancias,jugador);
    };
    
    protected abstract void inicializa(int r,int ganancias,LinkedList<String> jugador) ;
}
