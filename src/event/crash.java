package event;

import java.util.ArrayList;

import key.KeyAdmin;
import main.Game_frame;
import map.Background;
import map.bar;
import map.bullet;
import map.bulletManager;
import map.player_ball;

// ���� bar, ���� bullet, bullet�� ȭ���� �浹�� Ȯ���ϰ� �׿� ���� ��,bar,bullet ��ü�� ���¸� ��Ÿ���� ������� ���� �ٲ��ִ� Ŭ���� crash
public class crash  {
	private static int bar_index = -1; // crashed bar's index : �浹�� bar�� �ε��� ��
	private static int bullet_index = -1; // crashed bullet's index : �浹�� bullet�� �ش�Ǵ� ArrayList<Bullet>�� �ε��� ��
	public static int activity_cnt = 0; // * ���� bar�� ���� �Ǵ� �Ʒ��鿡 �浹�� ��� �ѹ��� ���� �浹���¸� ��Ÿ���� �������(ball_crash_status)�� �ٲٰ� ���ִ� ����
										// ** activity_cnt�� 0�̸� ���� �浹���� ��������� �ٲٰ� 1�̸� ���� �״�� ���� ���¸� �����ϰ� �ȴ�.
	
	public crash() {
	}
	
// Check_overlap : ball, Bar_img(0~19) - return crashed bar's index
	public synchronized static int overlapBallBar() {
		// * ���� �����¿� ��ǥ�� �� bar�� �����¿� ��ǥ�� ���ؼ� if���� ���ǿ� �����ϸ� �浹�ߴٰ� �ν��ϰ� �浹�� bar�� �ε��� ���� ��ȯ�ϴ� �Լ�
		// ** ���� bar�� �浹���� �ʾҴٰ� �ǴܵǸ� -1�� ��ȯ�Ѵ�.
		for(int i =0;i<bar.Bar_img.length;i++) {
			if(player_ball.b_posx >= bar.bar_posX[i] - player_ball.Ball_img.getWidth(null) &&
					player_ball.b_posx <= bar.bar_right_posX[i] &&
					player_ball.b_right_posx >= bar.bar_posX[i] &&
					player_ball.b_right_posx <= bar.bar_right_posX[i] + player_ball.Ball_img.getWidth(null) &&
					player_ball.b_posy >= bar.bar_posY[i] - player_ball.Ball_img.getHeight(null) && 
					player_ball.b_posy <= bar.bar_down_posY[i] &&
					player_ball.b_down_posy >= bar.bar_posY[i] &&
					player_ball.b_down_posy <= bar.bar_down_posY[i] + player_ball.Ball_img.getHeight(null)) {
				return i;
			}
		}
		return -1;
	}
// Check_overlap : ball, bullet - return crashed bullet's index
	public synchronized static int overlapBallBullet(ArrayList<bullet> a) {
		// * ���� �����¿� ��ǥ�� �� bullet�� �����¿� ��ǥ�� ���ؼ� if���� ���ǿ� �����ϸ� �浹�ߴٰ� �ν��ϰ� �浹�� bullet�� �ش�Ǵ� ArrayList<Bullet>�� �ε��� ���� ��ȯ�ϴ� �Լ�
		// ** ���� bullet�� �浹���� �ʾҴٰ� �ǴܵǸ� -1�� ��ȯ�Ѵ�.
		for(int i=0;i<a.size();i++) {
			if(player_ball.b_posx >= a.get(i).bullet_x - player_ball.Ball_img.getWidth(null) &&
				player_ball.b_posx <= a.get(i).bullet_right_x &&
				player_ball.b_right_posx >= a.get(i).bullet_x &&
				player_ball.b_right_posx <= a.get(i).bullet_right_x + player_ball.Ball_img.getWidth(null) &&
				player_ball.b_posy >= a.get(i).bullet_y - player_ball.Ball_img.getHeight(null) &&
				player_ball.b_posy <= a.get(i).bullet_down_y &&
				player_ball.b_down_posy >= a.get(i).bullet_y &&
				player_ball.b_down_posy <= a.get(i).bullet_down_y + player_ball.Ball_img.getHeight(null)){
				return i;
			}
		}
		return -1;
	}
	
// Event when overlap occurs : ball, Bar_img(0~19) - �浹�� bar�� �鿡 ���� ���� ���¸� ��Ÿ���� player_ball(��)�� ��� ������ ����
	public synchronized static void crashEventBallBar() {
		if((bar_index = overlapBallBar())!=-1) { // ���� bar�� �浹�� ���
			//----------------------crash 1 : bar ���ʸ�� ���� �浹
			if(player_ball.ball_action_x==1 &&
			   player_ball.b_posx < bar.bar_posX[bar_index] &&
			   bar.bar_posX[bar_index] <= player_ball.b_right_posx &&
			   bar.bar_posY[bar_index] <= player_ball.b_posy &&
			   bar.bar_down_posY[bar_index] >= player_ball.b_down_posy) 
			   {
				 player_ball.ball_action_x = -1; // ���� x�� ��������� �������� �ٲ۴�.
			   }
			//----------------------crash 2 : bar ����� ���� �浹
			else if(bar.bar_posX[bar_index] < player_ball.b_posx &&
					player_ball.b_right_posx < bar.bar_right_posX[bar_index] &&
					bar.bar_posY[bar_index] < player_ball.b_down_posy &&
					player_ball.b_posy < bar.bar_posY[bar_index]
					)
			{
				if(activity_cnt==0) player_ball.ball_crash_status = 2; // ���� �浹���¸� ��Ÿ���� �������� �ٲ��༭ ���� bar���� �״�� �ö�� �ְ� ���ش�.
				activity_cnt=1; // * ���� bar�� ����� ������ ���� ��� ball_crash_status���� 2�� �ٲٸ� spaceŰ�Է��� �ص� ���� ��ǥ�� ������ �����Ƿ�
								// ** �� ���� ball_crash_status���� �ٲٰ� ���ֱ� ���ؼ� activity_cnt���� 1�� �������.
			}
			//---------------------crash 3 : bar �����ʸ�� ���� �浹
			else if(player_ball.ball_action_x==-1 &&
				    player_ball.b_posx <= bar.bar_right_posX[bar_index] &&
				    bar.bar_right_posX[bar_index] < player_ball.b_right_posx &&
				    bar.bar_posY[bar_index] <= player_ball.b_posy &&
					bar.bar_down_posY[bar_index] >= player_ball.b_down_posy) 
					{
						player_ball.ball_action_x = 1; // ���� x�� ��������� ���������� �ٲ۴�.
					}
			//---------------------crash 4 : bar �Ʒ���� ���� �浹
			else if(bar.bar_posX[bar_index] < player_ball.b_right_posx &&
					player_ball.b_right_posx < bar.bar_right_posX[bar_index] + player_ball.Ball_img.getWidth(null) &&
					bar.bar_posX[bar_index]-player_ball.Ball_img.getWidth(null) < player_ball.b_posx &&
					player_ball.b_posx < bar.bar_right_posX[bar_index] &&
					player_ball.b_posy > bar.bar_posY[bar_index] && 
					player_ball.b_posy < bar.bar_down_posY[bar_index] &&
					player_ball.b_down_posy> bar.bar_down_posY[bar_index]) 
			{  
				if(activity_cnt==0) player_ball.ball_crash_status = 4; // ���� �浹���¸� ��Ÿ���� �������� �ٲ��༭ ���� bar�� �Ʒ��鿡 �浹���� �� ���� �ӵ����� �� ������ ƨ�ܳ����� ���ش�.
				activity_cnt=1;	// ���� crash2�� ������ ����
			}
		}
		else {
			//----------------------���� ��ġ�� ground�� �ƴϰ� bar�� �浹������ ���� ����
			// * Ex ) ����  bar������ ���ʳ��� �����ؼ� bar�������� ��� �� ���� bar���� �������� �ϴ� �Լ�
			if(player_ball.ball_crash_status==2 && player_ball.b_down_posy != Background.ground_posY)
			{
				player_ball.ball_action_x=0; // ���� ���������� �������� ���ش�
				player_ball.ball_crash_status=0; // ���� �浹 ���¸� �ٽ� ó�� ������ ������( 0: ���� � �ٸ� ��ü�� �ѵ����� ���� ���)
				player_ball.ball_air_status = 1; // ���� ���߿� �ִ� ���¶�� �˷��� �� �Ǵ� ���ȭ���� ��ǥ�� �ٲٸ鼭 ���� �̵��ϰ� �����.
				player_ball.velocity = 0;	// ��ӵ� ���Ŀ��� �ʱ� �߷°��ӵ� �ݴ�� �ۿ��ϴ� �ӵ��� ���� ����� ���� ���� �ö󰬴ٰ� �Ʒ��� �����ʰ� �ٷ� �ٴ��� ���ؼ� �����̰� �ߴ�.
				activity_cnt=0; // ���� �浹�� �Ͼ ��츦 ����� activity_cnt���� �ٽ� 0���� ����� ���� �������� �߰��� bar�� �����̳� �Ʒ��鿡 �ε����� ��� ���� �浹 ���¸� �ٲ� �� �ְ� �ߴ�.
				KeyAdmin.time_1 = System.currentTimeMillis()-500; // ���� y��ǥ�� �ð��� ������ �ϴ� ��ӵ� �����̱� ������ �� �ð��� �����ϱ� ���� �ʱ� �������� ������ ���� �ð��� time_1�� �����Ѵ�.
			}
		}
	}
	
	// crashEventBulletBorder : bullet�� frame�� ������ ���� �����ϰ� �Ǹ� bullet�� ������ ��Ÿ���� ���� bullet_dir�� ���� 1�� �ٲ㼭 bullet�� ��������� �ٲٴ� �Լ� 
	public synchronized static void crashEventBulletBorder (ArrayList<bullet> a) {
		for(int i=0;i<a.size();i++) {
			if(a.get(i).bullet_x+a.get(i).Bullet_img.getWidth(null) >= Game_frame.f_width) { // bullet�� ���� ������ x��ǥ�� frame�� width���� Ŀ���� ���
				a.get(i).bullet_dir=1;	// bullet�� �������� �ٲ��ش�.
			}
		}
	}
	
	public synchronized static void crashEventBallBullet() {
		// �Ѿ� ��ü �� ���� �浹�ϴ� ��ü�� �߰��ϸ� �ش� �ε����� bullet_index�� ���� �� �Ʒ��� �����Ѵ�.
		if((bullet_index=overlapBallBullet(bulletManager.Bullet)) != -1) { // ���� bullet��ü�� �浹���� ��
			
			// ���� ���߿� ���� ���� ��
			if(player_ball.ball_air_status==0) {
						// ���� bullet�� ���ʿ��� �浹�� ���
						if(player_ball.b_posx < bulletManager.Bullet.get(bullet_index).bullet_x) {
							if(player_ball.b_right_posx >= bulletManager.Bullet.get(bullet_index).bullet_x) {
								player_ball.p_x -= 5; // ���� x��ǥ�� �������� 5�̵����� bullet�� ���� ���� �������� ƨ�ܳ��� ���̰� �ߴ�.
							}
						}
						// ���� bullet�� �����ʿ��� �浹�� ���
						else if(player_ball.b_posx > bulletManager.Bullet.get(bullet_index).bullet_x) {
							if(player_ball.b_posx <= bulletManager.Bullet.get(bullet_index).bullet_right_x) {
								player_ball.p_x += 5; // ���� x��ǥ�� ���������� 5�̵����� bullet�� ���� ���� ���������� ƨ�ܳ��� ���̰� �ߴ�.
							}
						}
			}
			
			// ���� ���߿� ���� ��
			else if(player_ball.ball_air_status == 1) {
				// 1)���� ������ �Ʒ��� ���ϴ� �Ѿ˰� �ε����� �� 
				if(bulletManager.Bullet.get(bullet_index).bullet_dir == 0) {
					// 1-1)���� �������� �ö󰡰� �������� ���
					if(player_ball.ball_action_x == 0) {
						player_ball.velocity = 0;
						player_ball.ball_action_x = 1;
					}
					// 1-2)���� ������ �������� �̵��ϴ� ������ �˵��� ������ ��
					else if(player_ball.ball_action_x == 1) {
						// 1-2-1)���� �Ѿ��� ���ʿ��� �浹�� ���
						if(player_ball.b_posx < bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.velocity = 0;
						}
						// 1-2-2)���� �Ѿ��� �����ʿ��� �浹�� ���
						else if(player_ball.b_posx > bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.ball_action_x = 2; 
							// -> �Ѿ��� �ӵ��� �� �޾Ƽ� ���� ������ ������ �ӷ��� ����
						}
						
					}
					// 1-3)���� ���� �������� �̵��ϴ� ������ �˵��� ������ ��
					else if(player_ball.ball_action_x == -1) {
						player_ball.ball_action_x = -player_ball.ball_action_x; 
						// -> �ݴ������ �Ѿ˰� �浹�߱� ������ ���� ���� ������ �¿��� ��� �ٲ�
						player_ball.velocity = 0;
						// -> ���� �Ѿ˰� �浹 �� �� �̻� ������ �˵��� �� �������� �̵����� ���ϰ� �� ������ �Ʒ��� ���ϰ� �ϱ� ���� �ӵ��� ������
					}
				}
				// 2)���� ���� �Ʒ��� ���ϴ� �Ѿ˰� �ε����� �� 
				else if(bulletManager.Bullet.get(bullet_index).bullet_dir == 1) {
					// 2-1)���� �������� �ö󰡰� �������� ���
					if(player_ball.ball_action_x == 0) {
						player_ball.velocity = 0;
						player_ball.ball_action_x = -1;
					}
					// 2-2)���� ������ �������� �̵��ϴ� ������ �˵��� ������ ��
					else if(player_ball.ball_action_x == 1) {
						player_ball.ball_action_x = -player_ball.ball_action_x;
						// -> �ݴ������ �Ѿ˰� �浹�߱� ������ ���� ���� ������ �¿��� ��� �ٲ�
						player_ball.velocity = 0;
						// -> ���� �Ѿ˰� �浹 �� �� �̻� ������ �˵��� �� �������� �̵����� ���ϰ� �� ������ �Ʒ��� ���ϰ� �ϱ� ���� �ӵ��� ������
					}
					// 2-3)���� ���� �������� �̵��ϴ� ������ �˵��� ������ ��
					else if(player_ball.ball_action_x == -1) {
						// 2-3-1)���� �Ѿ��� ���ʿ��� �浹�� ���
						if(player_ball.b_posx < bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.ball_action_x = -2;
						}
						// 2-3-2)���� �Ѿ��� �����ʿ��� �浹�� ���
						else if(player_ball.b_posx > bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.velocity = 0;
							// -> ���� �Ѿ˰� �浹 �� �� �̻� ������ �˵��� �� �������� �̵����� ���ϰ� �� ������ �Ʒ��� ���ϰ� �ϱ� ���� �ӵ��� ������
						}
					}
				}
			}
		}
	}
}
