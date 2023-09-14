package FinalProject1;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.WritableRaster;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;





public class FinalProject1 extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Color color1 = new Color(0, 0, 0);
	public static void main(String[] args) {
		JFrame frame = new FinalProject1();
		frame.setTitle("FinalProject1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		//------centrar o Frame na tela do pc------//
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
		
	static JPanel mainPanel;
	
	
	
	static CardLayout cardLayout;
	xadresPanel xadresPanel;
	Menu menu;
	
	PrinterJob pj;
	MyPanel mypanel;
	
	public FinalProject1(){
		
		//------Definir o meu painel Inicial------//
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		mypanel = new MyPanel();
		xadresPanel = new xadresPanel();
		menu = new Menu();
		
		//------Adicionar ao painel incial subpaineis------//
		mainPanel.add(menu, "Inicial");
		mainPanel.add(mypanel, "novo");
		mainPanel.add(xadresPanel, "Jogo");
		
		//------KeyListener para o menuPrincipal------//
		mainPanel.addKeyListener(menu);
		mainPanel.setFocusable(true);

		mainPanel.addMouseListener(xadresPanel);
		mainPanel.addMouseMotionListener(xadresPanel);		
		
		//------KeyListener para o xadresPanel------//
		mainPanel.addKeyListener(xadresPanel);
		mainPanel.setFocusable(true);
		
		mainPanel.addKeyListener(mypanel);
		mainPanel.setFocusable(true);
		
		add(mainPanel);
		
		
		//-----Adicionar menus-----//

		
		//Menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//-----Menu jogo----//
		JMenu menu = new JMenu("Jogo");
		menuBar.add(menu);

		//----Adicionar menu itens-----//
		JMenuItem menuItem = new JMenuItem("Começar Novo jogo"); 
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator(); // add separator between the itens
		
		menuItem = new JMenuItem("Terminar Jogo"); 
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//-----Menu Mudar Tabuleiro-----//
		menu = new JMenu("Mudar Tabuleiro");
	
		//-----Adicionar menu itens-----//
		menuItem = new JMenuItem("Print");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Rodar"); 
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator(); // add separator between the itens
		
		menuItem = new JMenuItem("RGBToGray"); 
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		JMenu menu1 = new JMenu("Mudar Cor");
		
		menuItem = new JMenuItem("Verde"); 
		menuItem.addActionListener(this);
		menu1.add(menuItem);
		
		menu1.addSeparator();
		
		menuItem = new JMenuItem("Vermelho"); 
		menuItem.addActionListener(this);
		menu1.add(menuItem);
		
		menuBar.add(menu1);
		menuBar.add(menu);
		
		//-----Imprimir-----//
		pj = PrinterJob.getPrinterJob();
		
		URL img1 = getClass().getClassLoader().getResource("images/TabuleiroReal"); 
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		//ação selecionada
		String option = e.getActionCommand();
		
		if(option.equals("Rodar")) {
			MyPanel.processRotate();
			repaint();
			pack();
			
		}else if (option.equals("Print")) {
			if (pj.printDialog()) {
				try {
					pj.print();
				} catch (PrinterException ex) {
					ex.printStackTrace();
				}
			}
		}else if (option.equals("RGBToGray")) {
			MyPanel.RGB2Gray();
			repaint();
			pack(); 
	
		}else if(option.equals("Terminar Jogo")){
			System.exit(0);		
		}else if("Verde".equals(option)) {
			color1 = new Color(10, 118, 29);
			repaint();
		}else if("Vermelho".equals(option)) {
			color1 = new Color(210, 65, 6);
			repaint();
			System.out.println("cor");
		}else if("Começar Novo jogo".equals(option)) {
			comecarNovo();
			System.out.println("cor");
		}
	}
	private void comecarNovo(){
		AffineTransform nova = new AffineTransform();
		
		nova.translate(70, 70);
		xadresPanel.peca = nova.createTransformedShape(xadresPanel.peca);	
	}
	public static void menuInicial() {
		cardLayout.show(FinalProject1.mainPanel, "Inicial");
	}

}

class MyPanel extends JPanel implements KeyListener, ActionListener{

	JButton jbutton1;
	
	private static BufferedImage a;
	private static BufferedImage imagem;
	
	public MyPanel() {
		setPreferredSize(new Dimension(400, 400));
		
		URL tabuleiro = getClass()
				.getClassLoader()
				.getResource("images/xadrez.jpg");
		try{
			imagem = ImageIO.read(tabuleiro);
		}catch (IOException ex) {
		     ex.printStackTrace();
		}
		
		jbutton1 = new JButton("Voltar");
		jbutton1.addActionListener(this);
		add(jbutton1);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;//converter o g para uma versão mais recente
		
		
		//-----Validar imagem -----//
		if(a == null) {
			//desenhar imagem
			g2.drawImage(imagem, 200, 200, 400, 400, null);
		}else {
			g2.drawImage(a, 200, 200, 400, 400, null);
		}
		
		
		//-----Cliping-----//
		g2.translate(300, 300);
		GeneralPath pathClip = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        pathClip.moveTo(300,300);
        pathClip.lineTo(350,450);
        pathClip.quadTo(470, 470, 490, 490);
        pathClip.lineTo(470,460);
        pathClip.quadTo(388, 245, 300, 300);
        pathClip.closePath();

        g2.setColor(Color.BLACK);

        g2.clip(pathClip);
        g2.fill(pathClip);

        g2.setFont(new Font("Serif", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("Test Clip",350,400);
        
        g2.translate(-300, -300);
		
	}
	//-----Processamento de Imagem
	public static void processRotate() {
		BufferedImageOp op = null;
		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.PI/4);
		op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage bi = op.filter(imagem, null);
		a = bi;	
	}
	//-----Criação de imagem-----//
	public static BufferedImage RGBToGray(BufferedImage image) {
			
		WritableRaster rasterTabuleiro = image.getRaster();
		WritableRaster rasterTabuleiroOut = image.getRaster();
		
		//array de inteiros
		int[] rgba = new int[4];
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {//converter de cor para um tom cinzento
				rasterTabuleiro.getPixel(x, y, rgba);
				int gray = (int)((rgba[0] + rgba[1] + rgba[2]) / 3f) ;
				rgba[0] = rgba[1] = rgba[2] = gray; 
				rasterTabuleiroOut.setPixel(x, y, rgba);
			}
		}
		return image;		
	}
	public static void RGB2Gray() {
		RGBToGray(imagem);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String option = e.getActionCommand();
		
		if("Voltar".equals(option)) {
			FinalProject1.menuInicial();
		}
		
	}
}	
