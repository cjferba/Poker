package poker;
import java.util.*;

/**
 *
 * @author Carlosbasso
 */
public  class JugadorPoker extends Jugador{
    /**
     * @brief Constructor con argumentos.
     * @author Carlos Jesus Fernandez Basso
     */
   
    JugadorPoker(int g,String nom) {
        this.inicializa(nom,g);
    };
    /**
     * @brief Constructor de copias.
     * @author Carlos Jesus Fernandez Basso
     */
    JugadorPoker(JugadorPoker jug) {
        this.inicializa(jug.nombre() , jug.ganancias());
        carta.addAll(jug.carta);
    };
    
    public int ganancias(){
        return ganancias;
    };
    
   public int apostar() {
        ganancias--;
        return 1;
    }
    
   public boolean seRetira() {
        String mano = this.mano();
        return (mano.equals("Pareja") || mano.equals("Carta Alta"));
    }
    /**
     * @brief Comprueba cuantas repeticiones de cartas hay en la mano del jugador.
     *  6=Poker, 4=Full, 3=trio, 2=Dobles Parejas, 1=Pareja, 0=Carta Alta
     * @return int Contador de repeticiones.
     * @author Carlos Jesus FErnandez Basso
     */
   protected int repeticiones() {
        int cont=0;
        for (int s=0; s<carta.size(); s++) {
            for (int j=0; j<carta.size(); j++) {
                if (s!=j) {
                    if (carta.get(s).valor() == carta.get(j).valor()) {
                        cont++;
                    }
                }
            }
        }
        
    return (cont/2);
    };
    
   protected void ordenar(){
        //Ordenación de las cartas
        Carta c;
        for (int i=0; i<(carta.size()-1); i++) {
            for (int j=i+1; j<carta.size(); j++) {
                if (carta.get(i).valor()>carta.get(j).valor()) {
                    c=new Carta(carta.remove(i));
                    carta.add(i,carta.remove(j-1));
                    carta.add(j,c);
                }
            }
        } 
       
    };
   
   protected boolean color() {
        boolean color=true;
        for (int i=0; i<(carta.size()-1); i++) {
            if (!(carta.get(i).palo().equals(carta.get(i+1).palo()))) {
                color=false;
            }
        }
        return color;
    };
     
   protected boolean escalera() {
       
        this.ordenar();
        //Comprobamos si hay escalera.
        boolean salida=true;
        for (int i=1; i<carta.size(); i++) {
            if (carta.get(i).valor()!=(carta.get(i-1).valor()+1)) {
                salida=false;
            }
        }
        return salida;
    };
    
   protected boolean escaleracolor(){
       
       return ((this.escalera()) && (this.color())); 
    };
    
   protected boolean poker(int r){
        return(r==6);
    };
   
   protected boolean full(int r){
        return(r==4);
    };
    
   protected boolean trio(int r){
        return(r==3);
    };
    
   protected boolean doblepareja(int r){
        return(r==2);
    };
    
   protected boolean pareja(int r){
        return(r==1);
    };
    
   protected String mano() {
        String salida="";
        //Contamos las repeticiones en la lista de cartas.
        int aux=this.repeticiones();
        //Escalera de Color
        if (this.escaleracolor()) {
            salida="Escalera de Color";
        }//Poker
        else if (this.poker(aux)) {
            salida="Poker";
        }//FullHouse
        else if (this.full(aux)) {
            salida="Full";
        }//Color
        else if (this.color()) {
            salida="Color";
        }//Escalera
        else if (this.escalera()) {
            salida="Escalera";
        }//Trío
        else if (this.trio(aux)) {
            salida="Trío";
        }//Dobles parejas
        else if (this.doblepareja(aux)) {
            salida="Dobles Parejas";
        }//Pareja
        else if (this.pareja(aux)) {
            salida="Pareja";
        }//Carta Alta
        else {
            salida="Carta Alta";
        }
        
        return salida;
    };
   
   
    protected boolean comun(Carta c) {
        boolean comun=false;
        int color=0;
        for (int i=0; i<carta.size(); i++) {
            if (!c.equals(carta.get(i))) {
                if (c.valor()==carta.get(i).valor()) {
                    comun=true;
                }
                if (c.palo().equals(carta.get(i).palo())) {
                    color++;
                }
            }
        }
        if (color>2) {
            comun=true;
        }
        
        return comun;
    };
   
   
   
   
    public LinkedList<Carta> seleccionaCartas() {
        LinkedList<Carta> seleccion = new LinkedList<Carta>();
        String mano = this.mano();
        
        if (!(mano.equals("Poker") || mano.equals("Escalera") || mano.equals("Escalera de Color")
                || mano.equals("Full") || mano.equals("Escalera"))) {
            
            if (mano.equals("Trío")||mano.equals("Dobles Parejas")||mano.equals("Pareja")) {
                for (int i=0; i<carta.size(); i++) {
                    if (!this.comun(carta.get(i)) && carta.get(i).valor!=1) {
                        seleccion.add(carta.get(i));
                    }
                }
            }
            //En caso de tener Carta Alta, seleccionamos todas las cartas que no son figuras.
            else if (mano.equals("Carta Alta")) {
                for (int i=0; i<carta.size(); i++) {
                    if (!(carta.get(i).valor()==1 || carta.get(i).valor()==11 || carta.get(i).valor()==12
                            || carta.get(i).valor()==13)) {
                        seleccion.add(carta.get(i));
                    }
                }
            }
        }

        return seleccion;
    };    
}
