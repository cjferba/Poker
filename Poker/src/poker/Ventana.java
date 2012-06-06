/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Carlosbasso
 */
public class Ventana extends JFrame {
    
     private javax.swing.JFrame jFrame1;
     private JButton jButton1;
     private JPanel panelReparto, panel2;
     private LinkedList<JLabel> jugNombres = new LinkedList<JLabel>();
     private LinkedList<JLabel> jugGanancias = new LinkedList<JLabel>();
     private JLabel lab1, lab2, lab3, lab4, lab5;
     private JLabel cartas[][];
    
     public LinkedList<String> JUG;
     protected Integer rondas,  ganancias;
     protected JuegoPoker juego;
     protected static int nJugadores, nGanancias, nRondas;
     
     
     public Ventana(){
        super("Juego de Póker - Carlos JEsus FErnandez BAsso");

        
        ganancias = nGanancias;
        rondas = nRondas;
        juego = new JuegoPoker(rondas,ganancias,JUG);
        initComponents();
    }
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        
        lab1 = new JLabel("Jugadores");
        lab2 = new JLabel("Ganancias");
        jButton1 = new JButton("Repartir");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //jButton1ActionReparto(evt);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        
        panelReparto=new JPanel(new GridBagLayout());

        gbc.gridx=0;
        gbc.gridy=0;
        panelReparto.add(lab1,gbc);
        gbc.gridx=1;
        gbc.gridy=0;
        panelReparto.add(lab2,gbc);
        
        for (int i=0; i<juego.jugadores.size(); i++) {
            jugNombres.add(new JLabel(juego.jugadores.get(i).nombre()));
            jugGanancias.add(new JLabel(Integer.toString(juego.jugadores.get(i).ganancias())));
            gbc.gridx=0;
            gbc.gridy=i+1;
            panelReparto.add(jugNombres.get(i),gbc);
            gbc.gridx=1;
            gbc.gridy=i+1;
            panelReparto.add(jugGanancias.get(i),gbc);
        }
        
        gbc.gridx=1;
        gbc.gridy=juego.jugadores.size()+1;
        panelReparto.add(jButton1,gbc);
        panelReparto.setVisible(true);
      
        add(panelReparto);
        pack();
        this.setVisible(true);
    }
}
