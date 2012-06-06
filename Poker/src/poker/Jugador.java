package poker;
import java.util.*;
/**
 * @author Carlosbasso
 */
public abstract class Jugador {
    protected String nombre;
    protected int ganancias;
    protected LinkedList<Carta> carta = new LinkedList<Carta>();
    
    Jugador() {
        ganancias=0;
        nombre=null;
    };
    Jugador(String n,int g){
        this.inicializa(n,g);
    }
    public void inicializa(String n,int g){
        nombre=n;
        ganancias=g;
    };
    public String nombre(){
        return nombre;
    };
    public int ganancias(){
        return ganancias;
    };
    public boolean eliminado() {
        return ganancias<=0;
    };
    public void robar(Carta c){
        carta.add(c);
    };
    /**
     * @brief deja al jugador sin cartas
     * @author Carlos Jesus Fernandez Basso
     */
    public LinkedList<Carta> vaciarCartas() {
        LinkedList<Carta> aux = new LinkedList<Carta>();
        while (!carta.isEmpty()) {
            aux.add(carta.remove());
        }
        return aux;
    };
    /**
     * @brief 
     * @author Carlos Jesus Fernandez Basso
     */
    public abstract LinkedList<Carta> seleccionaCartas();
    public abstract int apostar();
    public abstract boolean seRetira();
    
   public LinkedList<Carta> descartar() {
        LinkedList<Carta> seleccion = new LinkedList<Carta>();
        LinkedList<Carta> descarte = new LinkedList<Carta>();
        
        seleccion = this.seleccionaCartas();
        for (int i=0; i<seleccion.size(); i++) {
            descarte.add(carta.remove(carta.indexOf(seleccion.get(i))));
        }
        return descarte;
    };
     public boolean perdedor() {
        return ganancias<=0;
    };
    /**
     *  Práctica Póker PDO
     * @brief Actualiza las ganancias del jugador.
     * @param g Ganancias a añadir a las ganancias actuales.
     * @author Carlos Jesus Fernandez Basso
     */
    public void ganancias(int g) {
        ganancias+=g;
    };
    
    
   
}
