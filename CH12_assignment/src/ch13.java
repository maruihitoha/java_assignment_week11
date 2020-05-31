import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.*;



class GamblingThread extends Thread
{
	boolean gambling = false;
	
	JLabel card[];	// ī�� ���̺�
	JLabel result;	// ��� ���̺�
	
	public GamblingThread(JLabel car[],JLabel res)
	{
		card = car;		// �Ű� ������ ���� ���̺��� ����
		result = res;
	}
	
	synchronized public void waitForGambling()
	{
		if(!gambling)	// ������ �������ڴ� ��ȣ�� ���� ������ ���
			try {this.wait();}
			catch(InterruptedException e) {return;}
	}
	
	
	synchronized public void startGambling()
	{
		gambling = true;	// ���� => true
		this.notify();		// ��ٸ��� �����带 ����
	}
	
	// ī�尡 ��� ��ġ���� Ȯ���ϴ� �޼ҵ�
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
			waitForGambling(); // ���
			Random rand = new Random();
			
			int tmp;
			int card_result[] = new int[3];
			
			// ī�忡 ����
			for(int i = 0; i < 3; i++)
			{
				
				// 0 ~ 4 ���� ���� ����
				tmp = rand.nextInt(5);
				card_result[i] = tmp;
				card[i].setText(Integer.toString(tmp));
				
				try {
					sleep(200); // 200ms �������� ����
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					return;
				}
			}
			
			gambling = false;// ���� Ŭ�� ���� false
			
			if(chk_win(card_result))
			{
				result.setText("        �����մϴ�!!");
			}
			else
			{
				result.setText("        �ƽ����� ");
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
		

		Font f = new Font("����",Font.ITALIC, 45); // ī�� ��Ʈ
		Font resultF = new Font("����", Font.BOLD,25); // ��� ��Ʈ
		
		setLayout(null);
		
		// Ŭ�� �� ���� ����
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				game.startGambling();
			}
		});
		
		
		// ī�� ���� ����
		for(int i = 0; i < 3; i++)
		{
			cardLayout = new JPanel();
			card[i].setFont(f);
			card[i].setForeground(Color.YELLOW);
			cardLayout.setBackground(Color.magenta); // ī�� ���� ����
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
		super("�����带 ���� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x ��ư�� ������ ����
		JLabel card[] = new JLabel[3];
		JLabel result = new JLabel("���콺�� Ŭ���� �� ���� �����մϴ�.");
		
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
