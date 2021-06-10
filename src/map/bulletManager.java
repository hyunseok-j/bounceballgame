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
	private static int count; // bullet 생성, 제거 속도를 제어하기 위한 변수
	public static int delta_y; // 공의 움직임에 따른 bullet의 y좌표 변화량
	public static ArrayList<bullet> Bullet = new ArrayList<bullet>(); // bullet객체전체를 저장,관리하는 ArrayList

	
	public bulletManager() {
		count=0;
	}
	// 총알을 생성하고 총알 객체의 멤버 변수 값을 초기화하는 create_bullet 함수
	public void create_bullet() {
		// 게임이 진행 중일 때
		if(Game_frame.game_status==1) {
			// map의 제일 꼭대기에 올랐을 때 (200,0)좌표에 총알 객체를 생성한다.
			if(Background.by>=0) { 
				Bullet.add(new bullet(200,0,0,System.currentTimeMillis()));
			}
		}
	}
	// 총알의 좌표를 변경시켜 이동시키는 move_bullet 함수
	public void move_bullet(ArrayList<bullet> a) {
		for(int i=0;i<a.size();i++) {
			if(a.get(i).bullet_dir == 0) { // bullet이 우하로 이동해야하는 경우
				a.get(i).bullet_x+=1;	// bullet의 x좌표 1증가
				a.get(i).bullet_y+=1;	// bullet의 y좌표 1증가
			}
			else if(a.get(i).bullet_dir == 1) { // bullet이 좌하로 이동해야하는 경우
				a.get(i).bullet_x-=1; 	// bullet의 x좌표 1감소
				a.get(i).bullet_y+=1; 	// bullet의 y좌표 1증가
			}
		}
	}
	
	// modify_bulletPoint_relative() : 공의 좌표가 변함에 따라 bullet의 좌표를 이동시켜 공과 bullet의 상대적 거리를 유지시키기 위한 함수
	public void modify_bulletPoint_relative(ArrayList<bullet> a) {
		long time_2 = System.currentTimeMillis(); // 현재 시각 측정
		int t = (int)((time_2-KeyAdmin.time_1)); // 현재 시각과 공이 처음 움직일 때의 시각을 빼서 이동하는 시간을 구한다
		delta_y = (int)(player_ball.g_accelartion*t*t/2)-player_ball.velocity*t/1000; // 등가속도 공식에서 위치변화량을 뜻하는 부분
		
		for(int i=0;i<a.size();i++) {
			if(player_ball.ball_air_status==1)				
			{	// 공이 공중에 있을 때
				if(Background.by < 0) { // 게임 진행 정도가 마지막 제일 끝이 아닌 경우
					a.get(i).bullet_y = a.get(i).bullet_y - delta_y; // bullet은 공의 움직임과는 반대로 움직여야 되므로 y변화량을 빼줬다 
				}
			}
			else if(player_ball.ball_air_status==0) {
				delta_y = 0;
				// 공이 공중에 있지 않을 때 bullet의 y좌표 변화량이 존재하지 않게 했다
			}
		}
	}
	
	// remove_bullet() : 현재 시간에서 총알 객체가 생성된 시각을 빼서 총알객체가 존재했던 시간을 구하고 일정 시간이 지나면 총알 객체를 삭제하는 함수
	public void remove_bullet(ArrayList<bullet> a) {
		for(int i=0;i<a.size();i++) {
			long post_time = System.currentTimeMillis(); // 현재 시각 post_time
			a.get(i).bullet_life_time = post_time - a.get(i).bullet_create_time; // 총알 객체가 존재했던 시간 bullet_life_time
			if(a.get(i).bullet_life_time >= 100000) { // 100초 이상 존재한 총알 객체는 삭제
				a.remove(a.get(i));
			}
		}
			
	}
	
	// BulletProcess() : bullet의 움직임, 생성, 제거를 담당하는 함수
	public synchronized void BulletProcess() {
		count=(count+1)%50; // 이 함수가 실행될 때마다 1증가, 0->1->...->49->0->1->...
		if(count==0) { // count가 0일 때
			create_bullet();  // bullet 객체 생성
			remove_bullet(Bullet); // 이전에 있던 bullet 객체 삭제
			// 게임이 계속 진행되다보면 ArrayList안의 bullet객체 수가 일정하게 된다
		}
		move_bullet(Bullet); // bullet을 움직이는 함수 호출
		modify_bulletPoint_relative(Bullet);
		for(int i=0;i<Bullet.size();i++) { // bullet의 가장 오른쪽 x좌표와 가장 아래쪽 y좌표 값을 지속적으로 바꿔줌
			Bullet.get(i).bullet_right_x = Bullet.get(i).bullet_x + Bullet.get(i).Bullet_img.getWidth(null);
			Bullet.get(i).bullet_down_y = Bullet.get(i).bullet_y + Bullet.get(i).Bullet_img.getHeight(null);
		}
	}
}
