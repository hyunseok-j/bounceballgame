package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import event.check_end;
import event.crash;
import key.KeyAdmin;
import map.Background;
import map.bar;
import map.bulletManager;
import map.player_ball;

public class Game_frame extends JFrame implements KeyListener,Runnable{
	int by=0;
	public static int f_width=640; // frame�� �ʺ�
	public static int f_height=800; // frame�� ����
	public static int game_status = 0; // ���� ���¸� �˷��ִ� ���� ( 0: ���� �ʱ�ȭ��, 1: ���� ����ȭ��, 2: ���ӿ��� ȭ��)
	
	public static boolean KeyLeft = false; // ���� Ű �Է��� �ƴ����� ���θ� �����ϴ� ����
	public static boolean KeyRight = false; // ������ Ű �Է��� �ƴ����� ���θ� �����ϴ� ����
	public static boolean KeySpace = false; // �����̽��� Ű �Է��� �ƴ����� ���θ� �����ϴ� ����

	static Draw d = new Draw();
	static bulletManager blm = new bulletManager();
	
	Thread th;
	
	Game_frame(){
		init();
		start();
		
		setTitle("BounceBallGame"); // frame�� ����
		setSize(f_width,f_height); // frame�� ������
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // ���� ��ũ��(ȭ��)
		
		int f_xpos = (int)(screen.getWidth()/2 - f_width/2);
		int f_ypos = (int)(screen.getHeight()/2 - f_height/2);
		
		this.add(d);
		setLocation(f_xpos,f_ypos); // ȭ���� �߾ӿ� frame�� ��ġ�ϰ� �ߴ�
		setResizable(false); // frame�� ����� �ٲ� �� ���� �ߴ�
		setVisible(true); //  frame�� ȭ�鿡 ��Ÿ������ �ߴ�
	}
	public void init() {
	}
	
	// start() : KeyListener�� ���, ������ �Ҵ��ϰ� ���۽�Ŵ
	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �ݱ� ��ư�� ������ �ý����� ����ǰ� �ߴ�
		addKeyListener(this);	// KeyListenr ��� 
		
		th = new Thread(this); // ������ �Ҵ�
		th.start(); // ������ �������
	}
	
	// Ű�� �������� �� 
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT: // ����
				KeyLeft = true;
				break;
			case KeyEvent.VK_RIGHT: // ������
				KeyRight= true;
				break;
			case KeyEvent.VK_SPACE: // �����̽���
				KeySpace = true;
				break;				
		}
	}
	// Ű�� �����ٰ� �� ���
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: // ����
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT: // ������
			KeyRight = false;
			break;
		case KeyEvent.VK_SPACE: // �����̽���
			KeySpace = false;
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	
	public void run() {
		try {
			// ��� ������ ó���ϴ� �Լ��� �ش� run()���� ����
			while(true) {
				KeyAdmin.KeyProcess(); // Ű �Է��� ó���ϴ� �Լ�
				crash.crashEventBallBar(); // ���� ball�� �浹���� �� ó���ϴ� �Լ�
				bar.BarProcess(); // bar�� �������� ó���ϴ� �Լ�
				player_ball.BallProcess(); // ���� ������(��ǥ)�� �����ϴ� �Լ�
				crash.crashEventBulletBorder(bulletManager.Bullet); // bullet�� frame�� ������ ���� �浹�� ó���ϴ� �Լ�
				crash.crashEventBallBullet(); // ���� bullet�� �浹�� ó���ϴ� �Լ�
				blm.BulletProcess(); // bullet�� ������, ����, ���Ÿ� ����ϴ� �Լ�
				check_end.F_check_end(); // ������ ����ȭ������ �Ѿ���ϴ��� Ȯ���ϴ� �Լ�
				
				repaint(); // ��� frame�� ��ü���� �ٽ� �׸��� �Ѵ�
				Thread.sleep(5); // 5ms�� Thread�� ���ߵ��� �Ѵ�
			}
		}catch(Exception e) {}
	}
}
