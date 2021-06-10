package map;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

import event.crash;
import key.KeyAdmin;
import main.Draw;
import main.Game_frame;

public class bulletManager{
	private static int count; // bullet ����, ���� �ӵ��� �����ϱ� ���� ����
	public static int delta_y; // ���� �����ӿ� ���� bullet�� y��ǥ ��ȭ��
	public static ArrayList<bullet> Bullet = new ArrayList<bullet>(); // bullet��ü��ü�� ����,�����ϴ� ArrayList

	
	public bulletManager() {
		count=0;
	}
	// �Ѿ��� �����ϰ� �Ѿ� ��ü�� ��� ���� ���� �ʱ�ȭ�ϴ� create_bullet �Լ�
	public void create_bullet() {
		// ������ ���� ���� ��
		if(Game_frame.game_status==1) {
			// map�� ���� ����⿡ �ö��� �� (200,0)��ǥ�� �Ѿ� ��ü�� �����Ѵ�.
			if(Background.by>=0) { 
				Bullet.add(new bullet(200,0,0,System.currentTimeMillis()));
			}
		}
	}
	// �Ѿ��� ��ǥ�� ������� �̵���Ű�� move_bullet �Լ�
	public void move_bullet(ArrayList<bullet> a) {
		for(int i=0;i<a.size();i++) {
			if(a.get(i).bullet_dir == 0) { // bullet�� ���Ϸ� �̵��ؾ��ϴ� ���
				a.get(i).bullet_x+=1;	// bullet�� x��ǥ 1����
				a.get(i).bullet_y+=1;	// bullet�� y��ǥ 1����
			}
			else if(a.get(i).bullet_dir == 1) { // bullet�� ���Ϸ� �̵��ؾ��ϴ� ���
				a.get(i).bullet_x-=1; 	// bullet�� x��ǥ 1����
				a.get(i).bullet_y+=1; 	// bullet�� y��ǥ 1����
			}
		}
	}
	
	// modify_bulletPoint_relative() : ���� ��ǥ�� ���Կ� ���� bullet�� ��ǥ�� �̵����� ���� bullet�� ����� �Ÿ��� ������Ű�� ���� �Լ�
	public void modify_bulletPoint_relative(ArrayList<bullet> a) {
		long time_2 = System.currentTimeMillis(); // ���� �ð� ����
		int t = (int)((time_2-KeyAdmin.time_1)); // ���� �ð��� ���� ó�� ������ ���� �ð��� ���� �̵��ϴ� �ð��� ���Ѵ�
		delta_y = (int)(player_ball.g_accelartion*t*t/2)-player_ball.velocity*t/1000; // ��ӵ� ���Ŀ��� ��ġ��ȭ���� ���ϴ� �κ�
		
		for(int i=0;i<a.size();i++) {
			if(player_ball.ball_air_status==1)				
			{	// ���� ���߿� ���� ��
				if(Background.by < 0) { // ���� ���� ������ ������ ���� ���� �ƴ� ���
					a.get(i).bullet_y = a.get(i).bullet_y - delta_y; // bullet�� ���� �����Ӱ��� �ݴ�� �������� �ǹǷ� y��ȭ���� ����� 
				}
			}
			else if(player_ball.ball_air_status==0) {
				delta_y = 0;
				// ���� ���߿� ���� ���� �� bullet�� y��ǥ ��ȭ���� �������� �ʰ� �ߴ�
			}
		}
	}
	
	// remove_bullet() : ���� �ð����� �Ѿ� ��ü�� ������ �ð��� ���� �Ѿ˰�ü�� �����ߴ� �ð��� ���ϰ� ���� �ð��� ������ �Ѿ� ��ü�� �����ϴ� �Լ�
	public void remove_bullet(ArrayList<bullet> a) {
		for(int i=0;i<a.size();i++) {
			long post_time = System.currentTimeMillis(); // ���� �ð� post_time
			a.get(i).bullet_life_time = post_time - a.get(i).bullet_create_time; // �Ѿ� ��ü�� �����ߴ� �ð� bullet_life_time
			if(a.get(i).bullet_life_time >= 100000) { // 100�� �̻� ������ �Ѿ� ��ü�� ����
				a.remove(a.get(i));
			}
		}
			
	}
	
	// BulletProcess() : bullet�� ������, ����, ���Ÿ� ����ϴ� �Լ�
	public synchronized void BulletProcess() {
		count=(count+1)%50; // �� �Լ��� ����� ������ 1����, 0->1->...->49->0->1->...
		if(count==0) { // count�� 0�� ��
			create_bullet();  // bullet ��ü ����
			remove_bullet(Bullet); // ������ �ִ� bullet ��ü ����
			// ������ ��� ����Ǵٺ��� ArrayList���� bullet��ü ���� �����ϰ� �ȴ�
		}
		move_bullet(Bullet); // bullet�� �����̴� �Լ� ȣ��
		modify_bulletPoint_relative(Bullet);
		for(int i=0;i<Bullet.size();i++) { // bullet�� ���� ������ x��ǥ�� ���� �Ʒ��� y��ǥ ���� ���������� �ٲ���
			Bullet.get(i).bullet_right_x = Bullet.get(i).bullet_x + Bullet.get(i).Bullet_img.getWidth(null);
			Bullet.get(i).bullet_down_y = Bullet.get(i).bullet_y + Bullet.get(i).Bullet_img.getHeight(null);
		}
	}
}
