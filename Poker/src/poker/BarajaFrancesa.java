package poker;
import java.util.*;
/**
 *
 * @author Carlos Jesus Fernandez Basso
 */
public class BarajaFrancesa extends Baraja {
    //atributo de clase
    protected static LinkedList<Carta> barajainicial =new LinkedList<Carta>();
     
    /**
     *  Práctica Póker PDO
     * @brief Constructor por defecto.
     * @author Carlos Jesus Fernandez Basso
     */
    BarajaFrancesa() {
        this.inicializa();
    };
    /**
     *  Práctica Póker PDO
     * @brief Inicializa la BarajaInicial. Es un método de clase.
     * @author Carlos Jesus Fernandez Basso
     */
    public static void inicializaClase() {
        
        String palos[]={"Corazones","Tréboles","Rombos","Picas"};

        for (int i=0; i<4; i++) {
            
            for (int j=1; j<=13; j++) {
                barajainicial.add(new Carta(palos[i],j));
            }
        }
    };
    /**
     *  Práctica Póker PDO
     * @brief Inicializa una instancia de BarajaFrancesa.
     * @author Carlos Jesus Fernandez Basso
     */
    public void inicializa() {
        //Si no se ha inicializado la Clase, se procede a ello.
        if (!(barajainicial.size()>0)) {
            this.inicializaClase();
        }
        //Si no se ha inicializado la instancia, se procede a ello.
        if (!(cartas.size()>0)) {
            for (int i=0; i<barajainicial.size(); i++) {
                cartas.add(new Carta(barajainicial.get(i)));
            }
        }
    };
    
   
        /**
     * @brief Muestra por la salida estándar una lista de cartas.
     * @param c LinkedList<Carta> lista de cartas a mostrar.
     * @author Carlos jesus Fernandez Basso
     */
    public String muestraCartas(LinkedList<Carta> c) {
        int n_aux=0;
        String c_aux="";
        Carta c_aux2 = new Carta();
        String a=new String("");
        for (int i=0; i<c.size(); i++) {
            c_aux2 = c.get(i);
            n_aux = c_aux2.valor();
            //Comprobamos si es figura o no.
            if (n_aux>10 || n_aux==1) {
                switch(n_aux) {
                    case 1:
                        c_aux="As";
                        break;
                    case 11:
                        c_aux="J";
                        break;
                    case 12:
                        c_aux="Q";
                        break;
                    case 13:
                        c_aux="K";
                        break;
                }//EndSwitch
            }
            else {
                
                c_aux=""+n_aux;
            }
            if (i==(c.size()-1)) {
                a=a+"\t"+"\t"+c_aux+" de "+c_aux2.palo()+"."+"\n";
            }
            else {
                a=a+"\t"+"\t"+c_aux+" de "+c_aux2.palo()+", "+"\n";
            }
        }
        return a;
    };
}

