package key;

import java.awt.Toolkit;

import event.crash;
import main.Draw;
import main.Game_frame;
import map.player_ball;

// ������� Ű�Է��� �����ϴ� Ŭ���� KeyAdmin
public class KeyAdmin {
	public static long time_1; // ���� �����̱� ������ �ð��� �����ϴ� ��������, ���� y��ǥ ���� ���ϸ� �ٸ� ��ü�� y��ǥ ���� ���ϹǷ� bullet,bar��ü�� time_1�� �̿��� �������� �����Ѵ�
	public KeyAdmin() {}
	
	public synchronized  static void KeyProcess() {
		// Ű �Է¹��� ���� ó���ϴ� �Լ�
		if(Game_frame.game_status==0) {
			// --------- 1) ������ ����ȭ���̰� ---------
			if(Game_frame.KeySpace==true) {
				// ----- 2) �����̽�Ű�� ��������  ---------
				Game_frame.game_status=1; // ���� ���� ���� ȭ������ �Ѿ�� �Ѵ�
			}
		}
		else if(Game_frame.game_status==1) {
			// ------- ���� ������ ���� ���� ��---------
			//---------LEFT�� �Էµ��� ��-------------------------------------------------------------------------
			if(Game_frame.KeyLeft==true) {
				if((player_ball.p_x+320-player_ball.Ball_img.getWidth(null)/2)>=0) { // ���� x��ǥ�� frame�� ���� ���� �Ѿ�� ������ (= ���� x��ǥ�� 0�̸��� ���� �ʴ´ٸ� )
					if(player_ball.ball_air_status == 0) { // ���� ������ ���� ���� ���� x�� �¿츦 Ű�Է¿� ���� ������ �� �ִ�
						player_ball.p_x-=1; // 	���� x��ǥ�� �������� 1�̵��ϰ� �Ѵ�
						player_ball.ball_action_x = -1; // ���� �����̽���Ű�� ���� ���߿� �ְ� �Ǵ� ��� �ٴ��̳� ground���� �Է¹��� �¿� ���۰��� ����ϰ� �Ѵ�. ������ ���� �������� �̵��߾��ٰ� ������״�
					}
				}
			}
			//---------RIGHT�� �Է��� ��-------------------------------------------------------------------------
			if(Game_frame.KeyRight==true) {
				if((player_ball.p_x+(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)/2+(player_ball.Ball_img.getWidth(null))/2)<(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)) {
					//  ���� x��ǥ�� frame�� ������ ���� �Ѿ�� ������ 
					if(player_ball.ball_air_status == 0) { // ���� ���߿� ���� �ʴٸ�
						player_ball.p_x+=1; // ���� x��ǥ�� ���������� 1�̵��ϰ� �Ѵ�
						player_ball.ball_action_x = 1; // ���� ���������� �̵��ϰ� �־��ٰ� �����״�. �� ���� 1�� �� �����̽��ٸ� ������ ���� ���߿��� ���������� �̵��ϸ鼭 �������� �׸��� �̵��Ѵ�
					}
				}
			}
			//---------NONE(Ű�Է��� ���� ���)--------------------------------------------------------------------------
			if(Game_frame.KeyRight==false && Game_frame.KeyLeft==false) {
				if(player_ball.ball_air_status == 0) player_ball.ball_action_x = 0; // ������ ���� 0�� ���¿��� �����̽��ٸ� �Է��ϸ� �¿� �̵����� ���� ���������� ���Ʒ��� �����δ�
			}
			//---------SPACE�� �Է��� ��-------------------------------------------------------------------------
			if(Game_frame.KeySpace==true) {
				if(player_ball.ball_air_status==0) {
					player_ball.ball_crash_status = 0; // ���� bar���� �浹�� ����� �浹 ���� ������ 0���� �ʱⰪ���� �ٽ� ����
					time_1 = System.currentTimeMillis(); // ���� �����̱� ������ �ð� �����ؼ� ����
					player_ball.ball_air_status = 1;	// ���� ���߿� �ִٴ� ���� �˸��� ���� ���� 0���� 1�� �ٲ��
					crash.activity_cnt =0;	// ���� bar���� �浹���� �� bar�� ����� �Ʒ��鿡 �ε��� ��� ���� �浹 ���� �������(ball_crash_status)���� ���� �� �ְ� ����� ���
					player_ball.velocity =6; // ���� bar�� �Ʒ���� �ε����� �� �ٷ� �ٴ��� ���ϰ� �ϱ� ���� velocity�� 0���� �ٲ���µ� �ٽ� ���� ó�� ���� 6���� �ٲ㼭 ���� �Ϲ����� ���������� �����̰� �Ѵ�
				}
			}
		}
		else if(Game_frame.game_status==2) {
			// ------- ���ӿ���ȭ���� ��---------
			if(Game_frame.KeySpace==true) { // �����̽��ٰ� �ԷµǸ�
				System.exit(1); // ������ �����Ų��
			}
		}
	}
}
