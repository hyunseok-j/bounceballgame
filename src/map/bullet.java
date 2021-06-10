package map;

import java.awt.Image;

import javax.swing.ImageIcon;

import main.Main;

public class bullet {
	public int bullet_x; // �Ѿ��� x��ǥ
	public int bullet_y; // �Ѿ��� y��ǥ
	public int bullet_dir; // �Ѿ��� ������� -> 0: ��� , 1: ����, 2: ����
	public long bullet_create_time; // �Ѿ� ������ �ð��� �����ϴ� ����
	public long bullet_life_time; // �Ѿ˰�ü�� ���� ������ ������ �ð�
	public Image Bullet_img; // �Ѿ� �̹����� ���� Image ��ü
	public int bullet_right_x; // �Ѿ��� ���� ������ x��ǥ
	public int bullet_down_y; // �Ѿ��� ���� �Ʒ��� y��ǥ
	
	public bullet() {} // �Ѿ�(bullet) �⺻ ������
	public bullet(int x, int y, int dir, long time) { // * ���� ��� �Ѿ�(bullet) ������ : �Ѿ��� x,y��ǥ�� ����,���� �ð�,�̹����� ��������� �����Ű��
													  // ** bullet�� ���� �� ������ �ð��� ó�� 0���� �����Ѵ�.
		this.bullet_x = x;
		this.bullet_y = y;
		this.bullet_dir = dir;
		this.bullet_create_time = time;
		this.bullet_life_time = 0;
		this.Bullet_img = new ImageIcon(Main.class.getResource("../image/bullet.png")).getImage(); // bullet�� �̹����� �ε��ؼ� ����
	}
}