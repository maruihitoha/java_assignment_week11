import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class A001 extends JFrame {
	ImageIcon bg = new ImageIcon("0001.jpg");		// ������
	ImageIcon apple = new ImageIcon("002.jpg");		// �������
	Image bgImage = bg.getImage();
	Image appleImage = apple.getImage();
	
	
	
	public A001()
	{
		super("�̹��� �׸��� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new PCPanel());
		setSize(1000,1000);
		setVisible(true);
	}
	
	class PCPanel extends JPanel
	{
		
		boolean H_S_flag = true;		// hide and show
		JLabel appleL = new JLabel(apple); // ��� ���� ���̺�

		
		Point before_point, after_point, point; // ���ʴ�� ���콺 �巡�� �� ����Ʈ, �巡�� �� ����Ʈ, �޾ƿ� ������Ʈ ����Ʈ
		int cx = 100, cy = 100; // �ʷϿ� �ʱ� ��ġ
		
		public PCPanel()
		{
			
			ButtonGroup grouprd = new ButtonGroup(); // ���� ��ư �׷�
			
			JButton H_S = new JButton("Hide/Show");
			JRadioButton ex = new JRadioButton("drag apple");
			JRadioButton ex2 = new JRadioButton("drag circle");
			
			
			appleL.setLocation(800, 800); // ��� ��ġ
			
			H_S.setLocation(0, 10);	// hide and show ��ġ
			H_S.setSize(100,70);	// ��ư ������
			
			
			add(H_S);
			
			grouprd.add(ex);		// ������ư 1 ���
			grouprd.add(ex2);		// ������ư 2 �ʷ� ��
			
			this.add(ex);			// ��ư1 �߰�
			this.add(ex2);			// ��ư2 �߰�
									
			add(appleL);			// ��� ���� �߰�
			
			
			H_S.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					H_S_flag = !H_S_flag; // hide and show flag, ���� ������ �ٲ��
					PCPanel.this.repaint(); // �÷��׿� ���� ��� ����
				}
				
			});
			
			
			
			// ��� ������ ������ ��
			appleL.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e)
				{
					if(ex.isSelected()) // ��� ��ư�� üũ �ƴ���
					{
						before_point = e.getPoint();
					}
				}
				
			});
			
			// ��� ������ �巡�� ���� ��
			appleL.addMouseMotionListener(new MouseMotionAdapter() {				
				
				public void mouseDragged(MouseEvent e)
				{
					if(ex.isSelected())  // ��� ��ư�� üũ �ƴ���
					{
						
						after_point = e.getPoint();
						Component cp = (JComponent)(e.getSource()); // �巡�� �Ǵ� ��ġ��
						point = cp.getLocation();					// ��ǥ ����
						
						// ����� �ش� ��ġ�� ����
						cp.setLocation(point.x + after_point.x - before_point.x ,
								point.y + after_point.y - before_point.y);
						cp.getParent().repaint();// paintcomponent ȣ��
					}
					
				}
				
			});
			
								
			
			
			
			addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent e)
	            {
	            	// ���� ��ư�� üũ�� �ư�, Ŭ���� ���� ��ġ�� ���� �߽����� ���� +- ���� ������ ���� üũ.
	               if(ex2.isSelected() && 
	            		   (e.getX() <= cx +25 && e.getX() >= cx -25) 
	            		   && (e.getY() <= cy+25 && e.getY() >= cy-25) )
	               
	               { // ���� �� ��ġ �缳�� �� ������Ʈ
	                	cx = e.getX();
	                	cy = e.getY();
	                	repaint();
	               }
	            }
	        });


			
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
	
			if(H_S_flag)
			{
				g.drawImage(bgImage,0,0,getWidth(),getHeight(),this);
			}
			g.setColor(Color.GREEN);
			g.fillOval(cx-25,cy-25,50,50);
		}
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new A001();
	}

}
