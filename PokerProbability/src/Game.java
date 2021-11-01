import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ListModel;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTextArea;


public class Game {
	
	JList<String> sourceList ; // c'est pour pouvoir l'utiliser partout 
	
	/* 
	 * Override qq fonctions de base pour pouvoir drag and drop easy 
	 */
	
	class ListTransferHandler extends TransferHandler {
		  @Override
		  public int getSourceActions(JComponent c) {
		    return TransferHandler.COPY_OR_MOVE;
		  }
		  @Override
		  protected Transferable createTransferable(JComponent source) {
		    JList<String> sourceList = (JList<String>) source;
		    String data = sourceList.getSelectedValue();
		    Transferable t = new StringSelection(data);
		    return t;
		  }

		  // quand on lache 
		  @Override
		  protected void exportDone(JComponent source, Transferable data, int action) {
		    @SuppressWarnings("unchecked")
		    JList<String> sourceList = (JList<String>) source;
		    String movedItem = sourceList.getSelectedValue();
		    if (action == TransferHandler.MOVE) {
		      DefaultListModel<String> listModel = (DefaultListModel<String>) sourceList
		          .getModel();
		      listModel.removeElement(movedItem);
		    }
		  }
		  
		  // tant qu'on le tient  
		  @Override
		  public boolean canImport(TransferHandler.TransferSupport support) {
		    if (!support.isDrop()) {
		      return false;
		    }
		    return support.isDataFlavorSupported(DataFlavor.stringFlavor);
		  }
		  
		  // quand on lache au dessus d'une case 
		  @Override
		  public boolean importData(TransferHandler.TransferSupport support) {
		    if (!this.canImport(support)) {
		      return false;
		    }
		    Transferable t = support.getTransferable();
		    String data = null;
		    try {
		      data = (String) t.getTransferData(DataFlavor.stringFlavor);
		      if (data == null) {
		        return false;
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		      return false;
		    }
		    JList.DropLocation dropLocation = (JList.DropLocation) support
		        .getDropLocation();
		    int dropIndex = dropLocation.getIndex();
		    JList<String> targetList = (JList<String>) support.getComponent();
		    DefaultListModel<String> listModel = (DefaultListModel<String>) targetList
		        .getModel();
		    if (dropLocation.isInsert()) {
		      listModel.add(dropIndex, data);
		    } else {
		      listModel.set(dropIndex, data);
		    }
		    return true;
		  }
		}
	
	/*
	 * Test override pour les cartes seules (la diff c'est que si on lache et que y'a déjà un élément, l'élément repart a la liste des cartes dispo) 
	 */
	class ListTransferHandlerSolo extends TransferHandler {
		  @Override
		  public int getSourceActions(JComponent c) {
		    return TransferHandler.COPY_OR_MOVE;
		  }
		  @Override
		  protected Transferable createTransferable(JComponent source) {
		    JList<String> sourceList = (JList<String>) source;
		    String data = sourceList.getSelectedValue();
		    Transferable t = new StringSelection(data);
		    return t;
		  }

		  // quand on lache 
		  @Override
		  protected void exportDone(JComponent source, Transferable data, int action) {
		    @SuppressWarnings("unchecked")
		    JList<String> sourceList = (JList<String>) source;
		    String movedItem = sourceList.getSelectedValue();
		    if (action == TransferHandler.MOVE) {
		      DefaultListModel<String> listModel = (DefaultListModel<String>) sourceList
		          .getModel();
		      listModel.removeElement(movedItem);
		    }
		  }
		  
		  // tant qu'on le tient  
		  @Override
		  public boolean canImport(TransferHandler.TransferSupport support) {
		    if (!support.isDrop()) {
		      return false;
		    }
		    return support.isDataFlavorSupported(DataFlavor.stringFlavor);
		  }
		  
		  // quand on lache au dessus d'une case 
		  @Override
		  public boolean importData(TransferHandler.TransferSupport support) {
		    if (!this.canImport(support)) {
		      return false;
		    }
		    Transferable t = support.getTransferable();
		    String data = null;
		    try {
		      data = (String) t.getTransferData(DataFlavor.stringFlavor);
		      if (data == null) {
		        return false;
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		      return false;
		    }
		    JList.DropLocation dropLocation = (JList.DropLocation) support
		        .getDropLocation();
		    int dropIndex = dropLocation.getIndex();
		    JList<String> targetList = (JList<String>) support.getComponent();
		    System.out.println();
		    if (targetList.getModel().getSize()==0) {
			    DefaultListModel<String> listModel = (DefaultListModel<String>) targetList
			        .getModel();
			    if (dropLocation.isInsert()) {
			      listModel.add(dropIndex, data);
			    } else {
			      listModel.set(dropIndex, data);
			    }
		    }
		    else {
		    	DefaultListModel<String> listModel = (DefaultListModel<String>) targetList.getModel();
			    if (dropLocation.isInsert()) {
			      listModel.add(dropIndex, data);
			    } else {
			      listModel.set(dropIndex, data);
			    }
			    
			    ((DefaultListModel<String>) sourceList.getModel()).add(0,targetList.getModel().getElementAt(0) );// on remet l'ancienne carte dans le tas 
			    listModel.remove(dropIndex-1); // on fait disparaitre l'ancien
		    }
		    return true;
		  }
		}
	


    private JFrame frame;
    private JTable table;
    private JTextField probaAutres;
    private JTextArea probaMoi;
    private JTextArea probaOppo;
    private JTextField txtAutresJoueurs;
    private JTextField txtMesCartes;
    private JTextField txtCartesAdversaire;
    private JTextField txtProbaQuuneCarte;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Game window = new Game();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Game() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        /*
         * On initialise les éléments 
         */
        
        sourceList = new JList<>(new DefaultListModel<>());
        sourceList.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        sourceList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        sourceList.setVisibleRowCount(4);
        sourceList.setBackground(Color.LIGHT_GRAY);
        sourceList.setBounds(0, 0, 1000, 150);
        frame.getContentPane().add(sourceList);
        // pour faire en sorte que ca doit drag & dropable 
        sourceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sourceList.setDragEnabled(true);
        sourceList.setDropMode(DropMode.INSERT);
        sourceList.setTransferHandler(new ListTransferHandler());
        
        JList<String> destListOther = new JList<>(new DefaultListModel<>());
        destListOther.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        destListOther.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        destListOther.setVisibleRowCount(6);
        destListOther.setBackground(Color.CYAN);
        destListOther.setBounds(0, 500, 150, 166);
        frame.getContentPane().add(destListOther);
        destListOther.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        destListOther.setDragEnabled(true);
        destListOther.setDropMode(DropMode.INSERT);
        destListOther.setTransferHandler(new ListTransferHandler());
        
        JList<String> destListMe = new JList<>(new DefaultListModel<>());
        destListMe.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        destListMe.setBackground(Color.GREEN);
        destListMe.setBounds(350, 500, 150, 166);
        frame.getContentPane().add(destListMe);
        destListMe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        destListMe.setDragEnabled(true);
        destListMe.setDropMode(DropMode.INSERT);
        destListMe.setTransferHandler(new ListTransferHandler());
        
        JList<String> destListOpponent = new JList<>(new DefaultListModel<>());
        destListOpponent.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        destListOpponent.setBackground(Color.RED);
        destListOpponent.setBounds(700, 500, 150, 166);
        frame.getContentPane().add(destListOpponent);
        destListOpponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        destListOpponent.setDragEnabled(true);
        destListOpponent.setDropMode(DropMode.INSERT);
        destListOpponent.setTransferHandler(new ListTransferHandler());
        
        /*
         * Pour les 5 cartes au milieu 
         * !! ces emplacements peuvent contenir SEULEMENT une carte !! (donc faut coder ca trouduc) 
         */
        
        JList<String> carte1 = new JList<>(new DefaultListModel<>());
        carte1.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        carte1.setBackground(Color.YELLOW);
        carte1.setBounds(269, 299, 70, 70);
        frame.getContentPane().add(carte1);
        carte1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carte1.setDragEnabled(true);
        carte1.setDropMode(DropMode.INSERT);
        carte1.setTransferHandler(new ListTransferHandlerSolo());
        
        JList<String> carte2 = new JList<>(new DefaultListModel<>());
        carte2.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        carte2.setBackground(Color.YELLOW);
        carte2.setBounds(351, 299, 70, 70);
        frame.getContentPane().add(carte2);
        carte2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carte2.setDragEnabled(true);
        carte2.setDropMode(DropMode.INSERT);
        carte2.setTransferHandler(new ListTransferHandlerSolo());
        
        JList<String> carte3 = new JList<>(new DefaultListModel<>());
        carte3.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        carte3.setBackground(Color.YELLOW);
        carte3.setBounds(433, 299, 70, 70);
        frame.getContentPane().add(carte3);
        carte3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carte3.setDragEnabled(true);
        carte3.setDropMode(DropMode.INSERT);
        carte3.setTransferHandler(new ListTransferHandlerSolo());
        
        JList<String> carte4 = new JList<>(new DefaultListModel<>());
        carte4.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        carte4.setBackground(Color.ORANGE);
        carte4.setBounds(560, 299, 70, 70);
        frame.getContentPane().add(carte4);
        carte4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carte4.setDragEnabled(true);
        carte4.setDropMode(DropMode.INSERT);
        carte4.setTransferHandler(new ListTransferHandlerSolo());
        
        JList<String> carte5 = new JList<>(new DefaultListModel<>());
        carte5.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        carte5.setBackground(Color.PINK);
        carte5.setBounds(689, 299, 70, 70);
        frame.getContentPane().add(carte5);
        carte5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carte5.setDragEnabled(true);
        carte5.setDropMode(DropMode.INSERT);
        carte5.setTransferHandler(new ListTransferHandlerSolo());
        
        

        
        /*
         * Pour noter les probabilités et autres 
         */
        
        txtProbaQuuneCarte = new JTextField();
        txtProbaQuuneCarte.setText("Proba qu'une carte tombe : ");
        txtProbaQuuneCarte.setHorizontalAlignment(SwingConstants.CENTER);
        txtProbaQuuneCarte.setEditable(false);
        txtProbaQuuneCarte.setColumns(10);
        txtProbaQuuneCarte.setBackground(Color.LIGHT_GRAY);
        txtProbaQuuneCarte.setBounds(350, 160, 280, 29);
        frame.getContentPane().add(txtProbaQuuneCarte);
        
        probaAutres = new JTextField();
        probaAutres.setBackground(Color.CYAN);
        probaAutres.setEditable(false);
        probaAutres.setBounds(150, 500, 130, 166);
        frame.getContentPane().add(probaAutres);
  
        
        probaMoi = new JTextArea();
        probaMoi.setForeground(Color.BLACK);
        probaMoi.setText("oui\n2");
        probaMoi.setEditable(false);
        probaMoi.setColumns(1);
        probaMoi.setBackground(Color.GREEN);
        probaMoi.setBounds(512, 510, 124, 150);
        frame.getContentPane().add(probaMoi);
        
        probaOppo = new JTextArea();
        probaOppo.setText("non");
        probaOppo.setEditable(false);
        probaOppo.setColumns(1);
        probaOppo.setBackground(Color.RED);
        probaOppo.setBounds(856, 508, 124, 150);
        frame.getContentPane().add(probaOppo);
        
        /*
         * Juste pour expliquer les cartes de qui sont où 
         */
        
        txtAutresJoueurs = new JTextField();
        txtAutresJoueurs.setHorizontalAlignment(SwingConstants.CENTER);
        txtAutresJoueurs.setText("Cartes autres joueurs");
        txtAutresJoueurs.setEditable(false);
        txtAutresJoueurs.setColumns(10);
        txtAutresJoueurs.setBackground(Color.CYAN);
        txtAutresJoueurs.setBounds(0, 459, 280, 29);
        frame.getContentPane().add(txtAutresJoueurs);
        
        txtMesCartes = new JTextField();
        txtMesCartes.setText("Mes cartes");
        txtMesCartes.setHorizontalAlignment(SwingConstants.CENTER);
        txtMesCartes.setEditable(false);
        txtMesCartes.setColumns(10);
        txtMesCartes.setBackground(Color.GREEN);
        txtMesCartes.setBounds(350, 459, 280, 29);
        frame.getContentPane().add(txtMesCartes);
        
        txtCartesAdversaire = new JTextField();
        txtCartesAdversaire.setText("Cartes adversaire");
        txtCartesAdversaire.setHorizontalAlignment(SwingConstants.CENTER);
        txtCartesAdversaire.setEditable(false);
        txtCartesAdversaire.setColumns(10);
        txtCartesAdversaire.setBackground(Color.RED);
        txtCartesAdversaire.setBounds(700, 459, 280, 29);
        frame.getContentPane().add(txtCartesAdversaire);
       
        /*
         * Pour mettre toutes les cartes dans le truc 
         */
        
	    for (int j = 0;j<4;j++) {
	        char type = "♥◆♣♠".charAt(j); 
	        System.out.println(type);
	      for (int i = 0; i < 13; i++) {
	          i+=1;
	          String text = ""; 
	          if (i==1) {text = "A";}
	          else if (i==11) {text = "J";}
	          else if (i==12) {text = "Q";}
	          else if (i==13) {text = "K";}
	          else {text = Integer.toString(i);}
	          i-=1;
	        ((DefaultListModel<String>) sourceList.getModel()).add(i, text+type  );
	      }
	    }

        
        /*
         * Pour commencer a faire les calculs 
         */
        
        JButton btnNewButton = new JButton("Lancer calculs");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("Lancement calculs..");

        		// on calcule la proba qu'une carte tombe 
        		double probaCarte = 100.0/sourceList.getModel().getSize();
        		txtProbaQuuneCarte.setText("Proba qu'une carte tombe : "+Double.toString(probaCarte).substring(0,4));
        		
   
        		// on calcule la proba qu'un joueur ait une combinaison 
        		ArrayList<ListModel<String>> commun = new ArrayList<ListModel<String>>( 
        				Arrays.asList(carte1.getModel(), carte2.getModel(), carte3.getModel(), carte4.getModel(), carte5.getModel() ));        		
        		combinaisons comb_joueur = new combinaisons(destListMe.getModel(), commun );
        		
        		
        		System.out.println("Fin calculs..");
        	}
        });
        btnNewButton.setBounds(10, 161, 162, 81);
        frame.getContentPane().add(btnNewButton);
       

        
    }
}
