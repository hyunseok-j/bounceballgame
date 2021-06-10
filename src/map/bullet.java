package map;

import java.awt.Image;

import javax.swing.ImageIcon;

import main.Main;

public class bullet {
	public int bullet_x; // 총알의 x좌표
	public int bullet_y; // 총알의 y좌표
	public int bullet_dir; // 총알의 진행방향 -> 0: 우상 , 1: 우하, 2: 좌하
	public long bullet_create_time; // 총알 생성된 시각을 저장하는 변수
	public long bullet_life_time; // 총알객체가 게임 내에서 존재한 시간
	public Image Bullet_img; // 총알 이미지를 담을 Image 객체
	public int bullet_right_x; // 총알의 가장 오른쪽 x좌표
	public int bullet_down_y; // 총알의 가장 아래쪽 y좌표
	
	public bullet() {} // 총알(bullet) 기본 생성자
	public bullet(int x, int y, int dir, long time) { // * 실제 사용 총알(bullet) 생성자 : 총알의 x,y좌표와 방향,생성 시각,이미지를 멤버변수에 저장시키고
													  // ** bullet이 게임 내 존재한 시간을 처음 0으로 저장한다.
		this.bullet_x = x;
		this.bullet_y = y;
		this.bullet_dir = dir;
		this.bullet_create_time = time;
		this.bullet_life_time = 0;
		this.Bullet_img = new ImageIcon(Main.class.getResource("../image/bullet.png")).getImage(); // bullet의 이미지를 로드해서 저장
	}
}