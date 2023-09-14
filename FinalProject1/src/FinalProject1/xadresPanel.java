package FinalProject1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import cg2d.utils.Utils;

public class xadresPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Color color1 = new Color(0, 0, 0);
//	public static Color color2 = new Color(210, 65, 60);
	
	Shape peca = null;
	Shape peca1 = null;
	//-----cordenadas inicias da peça 1-----//
	int pecaX = 70;
	int pecaY = 70;
	
	int TpecaX = 0;
	int TpecaY = 0;
	int MpecaX = 0;
	int MpecaY = 0;
	//-----cordenadas inicias da peça 2-----//
	int peca1X = 110;
	int peca1Y = 110;
	
	int Tpeca1X = 0;
	int Tpeca1Y = 0;
	int Mpeca1X = 0;
	int Mpeca1Y = 0;
	
	//----- Coordenadas para inicializar as shapes do xadres-----//
	int Xh = 20;
	int Yh = 20;
	int Yv = 50;
	int Xv = 50;
	
	
	private BufferedImage imagemTabuleiro;
	
	BufferedImage imagemRainhaBranca = null;
	BufferedImage imagemRainhaPreta = null;
	
	JButton jbutton1;

	
	AffineTransform at = new AffineTransform();
	AffineTransform at1 = new AffineTransform();
	
	boolean selected = false;
	boolean collision = false;
	
	public xadresPanel() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(800, 800));
		
		addKeyListener(this);
		setFocusable(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);

		jbutton1 = new JButton("Voltar");
		jbutton1.addActionListener(this);
		add(jbutton1);
		
		//-----Buscar imagem da peça Rainha-----//
		imagemRainhaBranca = Utils.getImage(this, "images/RainhaBranca.png");
		imagemRainhaPreta = Utils.getImage(this, "images/RainhaPreta1.png");
		
		//-----imagem Tabuleiro
		URL tabuleiro = getClass()
				.getClassLoader()
				.getResource("images/TabuleiroReal.png");
		try{
			imagemTabuleiro = ImageIO.read(tabuleiro);
		}catch (IOException ex) {
		     ex.printStackTrace();
		}
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	
	public void menu() {
		FinalProject1.cardLayout.show(FinalProject1.mainPanel, "Inicial");
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(160, 160);
		
		
		g2.drawImage(imagemTabuleiro, 0, 0, 500, 500, null);
		
		
		
		Shape tabuleiroImg = new Rectangle2D.Double(0, 0, 500, 500);
		g2.setColor(Color.black);
		
		quadradosPretos(g2, color1);

		letters(g2);
		
		g2.translate(-160, -160);
		
		peca(g2); 
		peca1(g2); 
		
		if(collision == true) {
			peca1 = new Ellipse2D.Double(100, 100, 50, 50);
		}
		
		
		limites();
	
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		System.out.println("keyCode");
		switch(keyCode) {
		case KeyEvent.VK_LEFT:
			TpecaX = -1;
			TpecaY = 0;
			break;
		case KeyEvent.VK_RIGHT:
			TpecaX = 1;
			TpecaY = 0;
			break;
		case KeyEvent.VK_UP:
			TpecaX = 0;
			TpecaY = -1;
			break;
		case KeyEvent.VK_DOWN:
			TpecaX = 0;
			TpecaY = 1;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		TpecaX = 0;
		TpecaY = 0;
		
		Tpeca1X = 0;
		Tpeca1Y = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String option = e.getActionCommand();
		if("Voltar".equals(option)) {
			menu();	
		}
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {	
		if(selected) {
			System.out.println("ana");
			// ----- Mover peça ----- //
			
			pecaX = e.getX() - 165;
			pecaY = e.getY() - 165;
			//repaint();
			
			
			Point2D p = new Point2D.Double(110, 110);
			if(peca.contains(p))
				collision = true;
			else
				collision = false;
		}
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(peca.contains(e.getPoint())) {
			System.out.println("button");
			pecaX = e.getX() - 165;
			pecaY = e.getY() - 165;
			selected = true;
		}else {
			selected = false;
		}		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		selected = false;
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void peca (Graphics2D g2) {	
//		//-----cordenadas inicias da peça-----//
//		int pecaX = 70;
//		int pecaY = 70;
//		
//		int TpecaX = 0;
//		int TpecaY = 0;
//		int MpecaX = 0;
//		int MpecaY = 0;
		// ----- peca 1 ----- //
		peca = new Rectangle2D.Double(140, 140, 50, 50);
		// ----- Desenhar pec ----- //
		if(TpecaX != 0 || TpecaY != 0) {
			pecaX = pecaX + TpecaX;
			pecaY = pecaY + TpecaY;
		}else {
			pecaX = pecaX + MpecaX;
			pecaY = pecaY + MpecaY;
		}
		
		// ----- translação na Shape ----- //
		at.setToTranslation(pecaX, pecaY);
		peca = at.createTransformedShape(peca);
		
		// -----Usar TexturePaint na Shape peca----- //
		
		TexturePaint pecaPaint = new TexturePaint(imagemRainhaBranca, peca.getBounds2D());
		g2.setPaint(pecaPaint);
		g2.fill(peca);	
	}
	
	public void peca1(Graphics2D g2) {
		// -----peca 2----- //
		peca1 = new Rectangle2D.Double(200, 200, 50, 50);
		//-----Desenhar peca-----//
		if(Tpeca1X != 0 || Tpeca1Y != 0) {
			peca1X = peca1X + Tpeca1X;
			peca1Y = peca1Y + Tpeca1Y;
		}else {
			peca1X = peca1X + Mpeca1X;
			peca1Y = peca1Y + Mpeca1Y;
		}
		
		// ----- translação na Shape -----//
		at1.setToTranslation(peca1X, peca1Y);
		peca1 = at1.createTransformedShape(peca1);
		
		// ----- Usar TexturePaint na Shape peca ----- //
		
		TexturePaint pecaPaint1 = new TexturePaint(imagemRainhaPreta, peca1.getBounds2D());
		g2.setPaint(pecaPaint1);
		g2.fill(peca1);
	}
	private void limites() {
		
		//-----limites peca 1-----//
		if(pecaX - 25 < 70) {
			pecaX = 70;
			repaint();
		}
		if(pecaX + 25 > 445) {
			pecaX = 445 - 25;
			repaint();
		}
		
		if(pecaY - 25 < 70) {
			pecaY = 70;
			repaint();
		}
		if(pecaY + 25 > 445) {
			pecaY = 445 - 25;
			repaint();
		}		
	}
	
	
//	private void colisao(Rectangle2D peca1){
//		Point2D p = new Point2D.Double(peca1X, peca1Y);
//
//		if (peca.intersects(peca1)) {
//			peca1X = 900;
//			peca1Y = 900;
//			selected = false;
//		}
//	}
	
	public void letters(Graphics2D g2) {
		
		String[] AxisLabels = {"A", "B", "C", "D", "E", "F", "G", "H"};
		
		String[] AxisNumbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
		
		getFont();
		// ----- Fonts -----//
		Font axisLabelsFont = new Font("Verdana", Font.BOLD, 15);
		AffineTransform lt = new AffineTransform();
		lt.rotate(-Math.PI/2);
		
		FontRenderContext frc = g2.getFontRenderContext();
		
		// ----- ativar fonte -----//
		g2.setFont(axisLabelsFont); 
		
		//-----desenhar letras dos quadrados-----//
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
			//-----Desenhar letras no eixo dos x-----//
				// ----- com a fonte ativada, agora consigo determinar o espaço que a letra vai ocupar ----- //
				int labelWidth = (int) axisLabelsFont.getStringBounds(AxisLabels[i], frc).getWidth();
				int labelHeight = (int) axisLabelsFont.getStringBounds(AxisLabels[i], frc).getHeight();

				// ----- posicionar no inicio da shape -----//   
				//metade da shape para ficarmos no meio do quadrado, menos labelWidth / 2
				int XlabelX = (Yh + 30) + i * Yv + Yv / 2 - labelWidth / 2; 
				//ficarmos cá em baixo Yh + Yv
				int XlabelY = (Yh-50) + Yv + 5 + labelHeight;
				
				// ----- posicionar no inicio da shape -----// 
				//metade da shape para ficarmos no meio do quadrado, menos labelWidth / 2
				int YlabelX = (Xh + 30) + i * Xv + Xv / 2 - labelWidth / 2; //(Xh  +30) por causa da translação
				//ficarmos cá em baixo Yh + Yv
				int YlabelY = (Yh + 390) + Yv + 5 + labelHeight;
				
				
				g2.drawString(AxisLabels[i], XlabelX, XlabelY);
				g2.drawString(AxisLabels[i], YlabelX, YlabelY);
				
			// ----- Desenhar letras no eixo dos y ----- //
				int Ylabelw1 = (int) axisLabelsFont.getStringBounds(AxisLabels[j], frc).getWidth();
				int Ylabelh1 = (int) axisLabelsFont.getStringBounds(AxisLabels[j], frc).getHeight();
				
				
				int YLabelx1 = (Yv - 50 + 20) + Xh / 2 - Ylabelh1 / 2;
				int YLabely1 = (Yh + 20 + 20 + 40) +( j * Yv + Ylabelw1 - Yv / 2 );
				
				int Ylabelw2 = (int) axisLabelsFont.getStringBounds(AxisLabels[j], frc).getWidth();
				int Ylabelh2 = (int) axisLabelsFont.getStringBounds(AxisLabels[j], frc).getHeight();
				
				
				int YLabelx2 = (Yv + 400 + 10) + Xh / 2 - Ylabelh2 / 2;
				int YLabely2 = (Yh + 20 + 20 + 40) +( j * Yv + Ylabelw2 - Yv / 2 );
				
				g2.drawString(AxisNumbers[j], YLabelx1, YLabely1);
				g2.drawString(AxisNumbers[j], YLabelx2, YLabely2);
		
			}
		}	
	}
	
	public void quadradosPretos(Graphics2D g2, Color color1) {
		
		// ----- desenhar shape quadrado horizontais e verticais ----- //
		
		for(int i = 0; i < 8; i++) {	
			for(int j = 0; j < 8; j++) {
				Shape quadrado = new Rectangle(Xh + (i * 50), Yh + (j * 50), Xv, Yv);
				at.setToTranslation(30, 30);
				quadrado = at.createTransformedShape(quadrado);
				g2.draw(quadrado);		
				
				if(j%2 == 0 && i%2 != 0) {
					g2.setColor(FinalProject1.color1);
					g2.fill(quadrado);
				}else if(j%2 != 0 && i%2 == 0) {
					g2.setColor(FinalProject1.color1);
					g2.fill(quadrado);
				}
			}	
		}
	}
	@Override
	public void run() {
		while (true) {
			// Code to change the model
			repaint();
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}

	
