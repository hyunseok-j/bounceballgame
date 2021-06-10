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
	public static int f_width=640; // frame의 너비
	public static int f_height=800; // frame의 높이
	public static int game_status = 0; // 게임 상태를 알려주는 변수 ( 0: 게임 초기화면, 1: 게임 실행화면, 2: 게임엔딩 화면)
	
	public static boolean KeyLeft = false; // 왼쪽 키 입력이 됐는지를 여부를 저장하는 변수
	public static boolean KeyRight = false; // 오른쪽 키 입력이 됐는지를 여부를 저장하는 변수
	public static boolean KeySpace = false; // 스페이스바 키 입력이 됐는지를 여부를 저장하는 변수

	static Draw d = new Draw();
	static bulletManager blm = new bulletManager();
	
	Thread th;
	
	Game_frame(){
		init();
		start();
		
		setTitle("BounceBallGame"); // frame의 제목
		setSize(f_width,f_height); // frame의 사이즈
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // 현재 스크린(화면)
		
		int f_xpos = (int)(screen.getWidth()/2 - f_width/2);
		int f_ypos = (int)(screen.getHeight()/2 - f_height/2);
		
		this.add(d);
		setLocation(f_xpos,f_ypos); // 화면의 중앙에 frame이 위치하게 했다
		setResizable(false); // frame의 사이즈를 바꿀 수 없게 했다
		setVisible(true); //  frame이 화면에 나타나도록 했다
	}
	public void init() {
	}
	
	// start() : KeyListener을 등록, 스레드 할당하고 시작시킴
	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼을 눌러도 시스템이 종료되게 했다
		addKeyListener(this);	// KeyListenr 등록 
		
		th = new Thread(this); // 스레드 할당
		th.start(); // 스레드 실행시작
	}
	
	// 키가 눌러졌을 때 
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT: // 왼쪽
				KeyLeft = true;
				break;
			case KeyEvent.VK_RIGHT: // 오른쪽
				KeyRight= true;
				break;
			case KeyEvent.VK_SPACE: // 스페이스바
				KeySpace = true;
				break;				
		}
	}
	// 키를 눌렀다가 뗀 경우
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: // 왼쪽
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT: // 오른쪽
			KeyRight = false;
			break;
		case KeyEvent.VK_SPACE: // 스페이스바
			KeySpace = false;
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	
	public void run() {
		try {
			// 모든 게임을 처리하는 함수를 해당 run()에서 실행
			while(true) {
				KeyAdmin.KeyProcess(); // 키 입력을 처리하는 함수
				crash.crashEventBallBar(); // 공과 ball가 충돌했을 때 처리하는 함수
				bar.BarProcess(); // bar를 움직임을 처리하는 함수
				player_ball.BallProcess(); // 공의 움직임(좌표)를 결정하는 함수
				crash.crashEventBulletBorder(bulletManager.Bullet); // bullet과 frame의 오른쪽 끝의 충돌을 처리하는 함수
				crash.crashEventBallBullet(); // 공과 bullet의 충돌을 처리하는 함수
				blm.BulletProcess(); // bullet의 움직임, 생성, 제거를 담당하는 함수
				check_end.F_check_end(); // 게임이 엔딩화면으로 넘어가야하는지 확인하는 함수
				
				repaint(); // 계속 frame에 객체들을 다시 그리게 한다
				Thread.sleep(5); // 5ms간 Thread가 멈추도록 한다
			}
		}catch(Exception e) {}
	}
}
