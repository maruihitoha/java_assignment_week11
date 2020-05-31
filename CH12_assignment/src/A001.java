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
	ImageIcon bg = new ImageIcon("0001.jpg");		// 배경사진
	ImageIcon apple = new ImageIcon("002.jpg");		// 사과사진
	Image bgImage = bg.getImage();
	Image appleImage = apple.getImage();
	
	
	
	public A001()
	{
		super("이미지 그리기 연습");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new PCPanel());
		setSize(1000,1000);
		setVisible(true);
	}
	
	class PCPanel extends JPanel
	{
		
		boolean H_S_flag = true;		// hide and show
		JLabel appleL = new JLabel(apple); // 사과 사진 레이블

		
		Point before_point, after_point, point; // 차례대로 마우스 드래그 전 포인트, 드래그 후 포인트, 받아온 컴포넌트 포인트
		int cx = 100, cy = 100; // 초록원 초기 위치
		
		public PCPanel()
		{
			
			ButtonGroup grouprd = new ButtonGroup(); // 라디오 버튼 그룹
			
			JButton H_S = new JButton("Hide/Show");
			JRadioButton ex = new JRadioButton("drag apple");
			JRadioButton ex2 = new JRadioButton("drag circle");
			
			
			appleL.setLocation(800, 800); // 사과 위치
			
			H_S.setLocation(0, 10);	// hide and show 위치
			H_S.setSize(100,70);	// 버튼 사이즈
			
			
			add(H_S);
			
			grouprd.add(ex);		// 라디오버튼 1 사과
			grouprd.add(ex2);		// 라디오버튼 2 초록 원
			
			this.add(ex);			// 버튼1 추가
			this.add(ex2);			// 버튼2 추가
									
			add(appleL);			// 사과 사진 추가
			
			
			H_S.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					H_S_flag = !H_S_flag; // hide and show flag, 누를 때마다 바뀌도록
					PCPanel.this.repaint(); // 플래그에 따라 배경 설정
				}
				
			});
			
			
			
			// 사과 사진이 눌렸을 때
			appleL.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e)
				{
					if(ex.isSelected()) // 사과 버튼이 체크 됐는지
					{
						before_point = e.getPoint();
					}
				}
				
			});
			
			// 사과 사진이 드래그 됐을 때
			appleL.addMouseMotionListener(new MouseMotionAdapter() {				
				
				public void mouseDragged(MouseEvent e)
				{
					if(ex.isSelected())  // 사과 버튼이 체크 됐는지
					{
						
						after_point = e.getPoint();
						Component cp = (JComponent)(e.getSource()); // 드래그 되는 위치로
						point = cp.getLocation();					// 좌표 저장
						
						// 사과를 해당 위치로 변경
						cp.setLocation(point.x + after_point.x - before_point.x ,
								point.y + after_point.y - before_point.y);
						cp.getParent().repaint();// paintcomponent 호출
					}
					
				}
				
			});
			
								
			
			
			
			addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent e)
	            {
	            	// 라디오 버튼이 체크가 됐고, 클릭한 곳의 위치가 원의 중심으로 부터 +- 원의 반지름 인지 체크.
	               if(ex2.isSelected() && 
	            		   (e.getX() <= cx +25 && e.getX() >= cx -25) 
	            		   && (e.getY() <= cy+25 && e.getY() >= cy-25) )
	               
	               { // 맞을 시 위치 재설정 후 리페인트
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
