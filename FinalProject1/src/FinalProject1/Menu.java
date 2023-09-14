package FinalProject1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import cg2d.shapes.Arrow;

public class Menu extends JPanel implements KeyListener, ActionListener, Runnable{
	
	JButton jbutton1;	
	JButton jbutton2;
	
	Shape rect1 = null;
	Shape rect2 = null;
	Shape rect3 = null;
	Shape rect4 = null;
	Shape arrow = new Arrow(60, 10, 100, 25);
	Shape arrow1 = new Arrow(170, 10, 100, 25);
	Shape arrow2 = new Arrow(500, 500, 100, 25);
	float angleBlueShape = 0f;
	float angleBlackShape = 0f;
	float angleQuadShape = 0f;
	float angle = 0f;
	
	AffineTransform at = new AffineTransform();
	

	public Menu() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(500, 500));
		
		addKeyListener(this);
		setFocusable(true);
		
		//----- Adicionar Butão -----//
		jbutton1 = new JButton("Jogar");
		jbutton1.addActionListener(this);
		add(jbutton1);
		
		jbutton2 = new JButton("Process Image");
		jbutton2.addActionListener(this);
		add(jbutton2);
		
		Thread thread = new Thread(this);
		thread.start();
		
		URL tabuleiro = getClass()
				.getClassLoader()
				.getResource("images/RainhaPreta1.png");
		try{
			imagemRainha = ImageIO.read(tabuleiro);
		}catch (IOException ex) {
		     ex.printStackTrace();
		}
		
	}
	private static BufferedImage imagemRainha;
	private static BufferedImage a;
	public static void processRotate() {
		BufferedImageOp op = null;
		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.PI /4);
		op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage bi = op.filter(imagemRainha, null);
		a = bi;	
	}
	
	public static BufferedImage RGBToGray(BufferedImage rainha) {
		BufferedImage tabuleiroOut = new BufferedImage(rainha.getWidth(), rainha.getHeight(), rainha.getType());
		
		WritableRaster rasterTabuleiro = rainha.getRaster();
		WritableRaster rasterTabuleiroOut = tabuleiroOut.getRaster();
		//array de inteiros
		int[] rgba = new int[4];
		
		for(int x = 0; x < rainha.getWidth(); x++) {
			for(int y = 0; y < rainha.getHeight(); y++) {//converter de cor para um tom cinzento
				rasterTabuleiro.getPixel(x, y, rgba);
				int gray = (int)((rgba[0] + rgba[1] + rgba[2]) / 3f) ;
				rgba[0] = rgba[1] = rgba[2] = gray; 
				rasterTabuleiroOut.setPixel(x, y, rgba);
			}
		}
		return tabuleiroOut;		
	}
	public static void RGB2Gray() {
		RGBToGray(imagemRainha);
	}
	public void jogar(){
		FinalProject1.cardLayout.show(FinalProject1.mainPanel, "Jogo");
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
		
		if(e.getSource() == jbutton1) {
			jogar();
		}else if(option.equals("Process Image")) {
			menuImage();
		}
		else if(option.equals("Terminar Jogo")){
			System.exit(0);		
		}else if (option.equals("RGBToGray")) {
			RGB2Gray();
			repaint();			
		}else if (option.equals("Rodar")) {
			processRotate();
			repaint();			
		}
		
	}
	public static void menuImage() {
		FinalProject1.cardLayout.show(FinalProject1.mainPanel, "novo");
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		//-----desenhar primitivas e Stoke-----//
		GeneralPath pRect = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		g2.setColor(Color.orange);
		Stroke stroke3 = new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		g2.setStroke(stroke3);
		g2.drawRect(70, 70, 50, 50);
		g2.drawRect(90, 90, 50, 50);
		g2.drawRect(50, 50, 50, 50);
		
		Stroke stroke4 = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke4);
		g2.drawRect(700, 50, 50, 50);
		g2.drawRect(680, 70, 50, 50);
		g2.drawRect(660, 90, 50, 50);
		
		Stroke stroke5 = new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
		g2.setStroke(stroke5);
		g2.drawRect(50, 700, 50, 50);
		g2.drawRect(70, 680, 50, 50);
		g2.drawRect(90, 660, 50, 50);
		
		Stroke stroke6 = new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE);
		g2.setStroke(stroke6);
		g2.drawRect(700, 700, 50, 50);
		g2.drawRect(680, 680, 50, 50);
		g2.drawRect(660, 660, 50, 50);
		
		//-----Desenhar retangulo azul Animação-----//
		
		rect1 = new Rectangle2D.Double(250, 250, 50, 50);
		g2.setColor(Color.BLUE);
		at.setToTranslation(50, 50);
		at.rotate(Math.toRadians(angleBlueShape), 100, 100);
		rect1 = at.createTransformedShape(rect1);
		g2.fill(rect1);	
		
		//-----Desenhar retangulo vermelho Animação-----//
		
		rect4 = new Rectangle2D.Double(600, 600, 50, 50);
		g2.setColor(Color.BLUE);
		at.setToTranslation(50, 50);
		at.rotate(Math.toRadians(angleQuadShape), 400, 400);
		rect4 = at.createTransformedShape(rect4);
		g2.fill(rect4);	
		
		//-----GeneralPath wind_even_ood-----//
		GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		p.moveTo(200, 200);
		p.quadTo(250, 300, 150, 300);
		p.lineTo(600, 600);
		p.quadTo(400, 690, 700, 600);
		p.lineTo(200, 200);
		p.closePath();
		
		AffineTransform a1 = new AffineTransform();
		a1.rotate(Math.toRadians(angleBlueShape), 400, 400);
		rect3 = a1.createTransformedShape(p);
		
		//----Desenhar seta----//
		g2.setColor(Color.orange);
		g2.fill(arrow);
		g2.setColor(Color.cyan);
		g2.fill(arrow1);
		g2.drawOval(285, -7, 230, 50);
		
		AffineTransform seta = new AffineTransform();
		g2.rotate(Math.PI);
		seta.rotate(angle, 400, 400);
		g2.rotate(-Math.PI);
		arrow2 = seta.createTransformedShape(arrow2);
		g2.draw(arrow2);
		
		//-----Gradiente repetido-----//
		GradientPaint paint = new GradientPaint(250, 250, Color.black, 190, 190, Color.white, true);
		g2.setPaint(paint);
		
		g2.fill(rect3);
		
		
		//-----GeneralPath WIND_NON_ZERO-----//		
		
		GeneralPath p1 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		p1.moveTo(200, 200);
		p1.quadTo(250, 300, 150, 300);
		p1.lineTo(600, 600);
		p1.quadTo(400, 690, 700, 600);
		p1.lineTo(200, 200);
		p1.closePath();
			
		AffineTransform a2 = new AffineTransform();
		a2.rotate(Math.toRadians(angleBlackShape), 400, 400);
		rect2 = a2.createTransformedShape(p1);
		
		//-----Gradiente repetido-----//
		GradientPaint paint1 = new GradientPaint(650, 650, Color.blue, 190, 600, Color.white, true);
		g2.setPaint(paint1);
		g2.fill(rect2);
		
		//-----desenhar stroke-----//
		g2.setColor(Color.blue);
		Stroke stroke = new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
		g2.setStroke(stroke);
		//-----Definir dimensoes do relogio-----//
		g2.drawLine(50, 50, 50, 750);
		g2.drawLine(50, 750, 750, 750 );
		g2.setColor(Color.blue);
		Stroke stroke1 = new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		g2.setStroke(stroke1);
		g2.drawLine(750, 750, 750, 50);
		g2.drawLine(750, 50, 50, 50);
			
		g2.drawImage(imagemRainha, 340, 340, 100, 100, null);
		
		
	}

	@Override
	public void run() {
		while (true) {
			// Code to change the model
			angleBlueShape = (angleBlueShape + 1.0f) % 360;
			angleBlackShape = (angleBlackShape + 6.0f)% 360;
			angleQuadShape = (angleQuadShape - 1.0f) % 360;
			angle =((angle + 1.0f) % 360);
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
