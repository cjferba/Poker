package poker;
import java.util.*;

/**
 * @author Carlos Jesus Fernandez Basso
 */
public class Carta {

    /**
     * @param argumentos
     */
    protected String palo;
    protected int valor;
    
   Carta(){
   
   };
   /**
     * @brief Constructor con paramentros.
     * @author Carlos JEsus Fernandez Basso
     */
   Carta(String C,int V){
    palo=C;
    valor=V;
    };
   /**
     * @brief Constructor de copias.
     * @author Carlos JEsus Fernandez Basso
     */
    Carta(Carta c) {
        palo=c.palo();
        valor=c.valor();
    };
    /**
     * @brief cambia el valor de una carta.
     * @author Carlos JEsus Fernandez Basso
     */
   void valor(int N){
   valor=N;
   };
   /**
     * @brief inserta un palo a una carta.
     * @author Carlos JEsus Fernandez Basso
     */
   void palo(String P){
   palo=P;
   };
   /**
     * @brief devuelve el valor de una carta.
     * @author Carlos JEsus Fernandez Basso
     */
   int valor(){
   return valor;
   };
   /**
     * @brief devuelve el valor d euna carta.
     * @author Carlos JEsus Fernandez Basso
     */
   String palo(){
   return palo;
   };
}
