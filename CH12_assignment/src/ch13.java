import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.*;



class GamblingThread extends Thread
{
	boolean gambling = false;
	
	JLabel card[];	// 카드 레이블
	JLabel result;	// 결과 레이블
	
	public GamblingThread(JLabel car[],JLabel res)
	{
		card = car;		// 매개 변수로 받은 레이블을 담음
		result = res;
	}
	
	synchronized public void waitForGambling()
	{
		if(!gambling)	// 도박을 시작하자는 신호가 오기 전까지 대기
			try {this.wait();}
			catch(InterruptedException e) {return;}
	}
	
	
	synchronized public void startGambling()
	{
		gambling = true;	// 도박 => true
		this.notify();		// 기다리는 스레드를 깨움
	}
	
	// 카드가 모두 일치한지 확인하는 메소드
	public boolean chk_win(int[] res)
	{
		if(res[0] == res[1] && res[1] == res[2])
			return true;	
		else 
			return false;
	}
	
	public void run()
	{
		while(true)
		{
			waitForGambling(); // 대기
			Random rand = new Random();
			
			int tmp;
			int card_result[] = new int[3];
			
			// 카드에 숫자
			for(int i = 0; i < 3; i++)
			{
				
				// 0 ~ 4 사이 랜덤 정수
				tmp = rand.nextInt(5);
				card_result[i] = tmp;
				card[i].setText(Integer.toString(tmp));
				
				try {
					sleep(200); // 200ms 간격으로 진행
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					return;
				}
			}
			
			gambling = false;// 다음 클릭 까지 false
			
			if(chk_win(card_result))
			{
				result.setText("        축하합니다!!");
			}
			else
			{
				result.setText("        아쉽군요 ");
			}			
			
		}
	}
}

class Display extends JPanel
{
	GamblingThread game;
	JLabel card[];
	JLabel result;
	
	
	public Display(JLabel card[], JLabel result, GamblingThread g)
	{
		JPanel cardLayout;
		
		game = g;
		this.card = card;
		this.result = result;
		

		Font f = new Font("돋움",Font.ITALIC, 45); // 카드 폰트
		Font resultF = new Font("돋움", Font.BOLD,25); // 결과 폰트
		
		setLayout(null);
		
		// 클릭 시 게임 시작
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				game.startGambling();
			}
		});
		
		
		// 카드 외형 생성
		for(int i = 0; i < 3; i++)
		{
			cardLayout = new JPanel();
			card[i].setFont(f);
			card[i].setForeground(Color.YELLOW);
			cardLayout.setBackground(Color.magenta); // 카드 외형 설정
			cardLayout.setSize(100,70);
			cardLayout.setLocation(110*(i+1), 80);
			cardLayout.add(card[i]);		
			
			add(cardLayout);
			
		}
		
		
		result.setFont(resultF);
		add(result);		
		result.setSize(500,50);
		result.setLocation(50,200);
		
	}
}



public class ch13 extends JFrame{

	public ch13()
	{
		super("스레드를 가진 갬블링");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼을 누르면 꺼짐
		JLabel card[] = new JLabel[3];
		JLabel result = new JLabel("마우스를 클릭할 때 마다 게임합니다.");
		
		int i = 0;
		
		while(i<3)
			card[i++] = new JLabel("0");
		
		GamblingThread game = new GamblingThread(card, result);
		
		game.start();
		
		setContentPane(new Display(card, result, game));
		
		setSize(600, 300);
		setVisible(true);
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ch13();
	}

}
