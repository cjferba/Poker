package poker;
import java.util.*;

/**
 *
 * @author Carlos
 */
public abstract class Baraja{

 protected LinkedList<Carta> cartas = new LinkedList<Carta>();
 
    Baraja() {
        this.inicializa();
    };

    public abstract void inicializa();

    /**
     *  Práctica Póker PDO
     * @brief Baraja las cartas de la lista de cartas de forma aleatoria.
     * @author Carlos Jesus Fernandez Basso
     */
    public void barajar() {
        Collections.shuffle(cartas);
    };

    /**
     *  Práctica Póker PDO
     * @brief Extrae una carta del final de la lista de cartas y la devuelve.
     * @return Ultima carta de la lista.
     * @author Carlos Jesus Fernandez Basso
     */
    public Carta robar() {
        return cartas.removeLast();
    };
}
