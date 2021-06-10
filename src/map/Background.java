package map;

import java.awt.Image;

import javax.swing.ImageIcon;

import main.Draw;
import main.Game_frame;
import main.Main;

public class Background{
	public static Image Background_img; // 배경이미지
	public static Image[] Cloud_img; // 구름이미지
	public static Image Ground_img; // 땅 이미지
	public static Image Presstxt_img; // 처음 space누르라고 표시하는 이미지
	public static Image Title_img; // 게임 제목 이미지
	public static Image Clear_img; // 게임 끝났음을 알리는 이미지
	public static int ground_posY=750; // 초기 땅의 y좌표
	public static int firsty=-1623; // 초기 배경의 y좌표
	public static int prev_by; // 이전 배경의 y좌표
	public static int prev_ground; // 이전 땅의 y좌표
	public static int by=-1623; // 배경의  실제y좌표
	public static int[] cx= {0,0,0}; // 구름의 속도
	static int cnt =0;
	
	// 생성자
	public Background(){
		if(cnt==0) { // 여러 곳에서 Background 생성자를 호출해도 함수 몸체 내용은 한 번만 실행되게 했다
			init();	
		}
		cnt++;
	}
	
	// Background::init() : Background 관련 img Load하는 함수
	private void init() {
		if(Game_frame.game_status==0||Game_frame.game_status==1) {
			Background_img = new ImageIcon(Main.class.getResource("../image/init_background.jpg")).getImage();
		}else if(Game_frame.game_status==2) {
			Background_img = new ImageIcon(Main.class.getResource("../image/end.png")).getImage();
		}
		Title_img = new ImageIcon(Main.class.getResource("../image/title.png")).getImage();
		Presstxt_img= new ImageIcon(Main.class.getResource("../image/pushspace.png")).getImage();
		Clear_img = new ImageIcon(Main.class.getResource("../image/clear.png")).getImage();
		
		Cloud_img = new Image[3];
		for(int i=0;i<Cloud_img.length;i++) {
			Cloud_img[i]= new ImageIcon(Main.class.getResource("../image/cloud_"+i+".png")).getImage();
		}
		
		Ground_img = new ImageIcon(Main.class.getResource("../image/ground_0.png")).getImage();
	}
}

