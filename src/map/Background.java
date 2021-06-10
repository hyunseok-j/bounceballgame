package map;

import java.awt.Image;

import javax.swing.ImageIcon;

import main.Draw;
import main.Game_frame;
import main.Main;

public class Background{
	public static Image Background_img; // ����̹���
	public static Image[] Cloud_img; // �����̹���
	public static Image Ground_img; // �� �̹���
	public static Image Presstxt_img; // ó�� space������� ǥ���ϴ� �̹���
	public static Image Title_img; // ���� ���� �̹���
	public static Image Clear_img; // ���� �������� �˸��� �̹���
	public static int ground_posY=750; // �ʱ� ���� y��ǥ
	public static int firsty=-1623; // �ʱ� ����� y��ǥ
	public static int prev_by; // ���� ����� y��ǥ
	public static int prev_ground; // ���� ���� y��ǥ
	public static int by=-1623; // �����  ����y��ǥ
	public static int[] cx= {0,0,0}; // ������ �ӵ�
	static int cnt =0;
	
	// ������
	public Background(){
		if(cnt==0) { // ���� ������ Background �����ڸ� ȣ���ص� �Լ� ��ü ������ �� ���� ����ǰ� �ߴ�
			init();	
		}
		cnt++;
	}
	
	// Background::init() : Background ���� img Load�ϴ� �Լ�
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

