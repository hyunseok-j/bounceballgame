package map;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import event.crash;
import key.KeyAdmin;
import main.Draw;
import main.Game_frame;
import main.Main;

public class player_ball{

	public static int p_x=0;	// Ball_img�� ����� x��ǥ (�̹����� ���� ���� ����)
	public static int p_y=0;	// Ball_img�� ����� y��ǥ (�̹����� ���� ���� ����)
	public static int b_posx ;	// Ball_img�� ���� x��ǥ (�̹����� ���� ���� ����)
	public static int b_posy ;	// Ball_img�� ���� y��ǥ (�̹����� ���� ���� ����)
	public static int b_right_posx; // =  b_posx + ball.width : Ball_img�� ���� ������ ��ǥ
	public static int b_down_posy; // = b_posy + ball.height : Ball_img�� ���� �Ʒ��� ��ǥ
	public static int velocity = 6; // ���� y�� �ӷ�
	public static Image Ball_img; // �� �̹����� ������ Image��ü
	public static int ball_air_status=0; // ���� ���߿� �ִ��� �ƴ��� ���� �Ǵ��� ���� ���� (0: bar�� ground��, 1: ���)
	public static int ball_crash_status=0; // ���� bar�� ground�� ��� �鿡 �ε��� ���� ��Ÿ���� ����
	public static int ball_action_x=0; // ���� x�� �ӵ�(����+�ӷ�)
	public static final double g_accelartion = 0.00000980665; // �߷°��ӵ�(m / ms^2)  
	public player_ball() {
		init(); // �ʱ� ���� ���¸� �ʱ�ȭ�Ѵ�
	}
	private void init() {
		Ball_img = new ImageIcon(Main.class.getResource("../image/ball.png")).getImage();
	}
	public synchronized  static void BallProcess() {
		b_posx = p_x+320-Ball_img.getWidth(null)/2; // ���� x��ǥ  = x��ȭ��(0) + �ʱ�x��ǥ��[ȭ�鰡�]
		b_posy = p_y+750-Ball_img.getHeight(null); // ���� y��ǥ = y��ȭ��(0) + �ʱ�y��ǥ��[ground(750)�ٷ���]
		b_right_posx = b_posx + Ball_img.getWidth(null); // ���� ���� ������ x��ǥ
		b_down_posy = b_posy + Ball_img.getHeight(null); // ���� ���� �Ʒ��� y��ǥ
//------when user is playing game---------------------------------------
		if(Game_frame.game_status==1) {
			if(ball_air_status==1)  // ���� ���߿� ���� �� 
			{ 	
				if(Background.by==-1623 || Background.by==0) { // ���ȭ���� ���� �Ʒ� �κа� ���� �� �κ��� �� ���� �����δ�
					long time_2 = System.currentTimeMillis(); // ���� �ð�
					int t = (int)((time_2-KeyAdmin.time_1)); // ���� �ð����� ���� ���߿� �ֱ� ������ �ð��� ���� ���߿� �󸶵��� �־����� �� �ð��� ���ؼ� t�� �����Ѵ�
					p_y = p_y+(int)(g_accelartion*t*t/2)-velocity*t/1000; 
					/* 
					 * ���� ���� = ó�� ���� + (g*t^2)/2 + velocity*t
					 *  velocity(�ӵ�:+,-�� ���� �� �ִ�) , ���� ������ t�� sec����
					 */
					
					// �ٴڰ� ball�� �浹���� ��
					if(p_y>0){
						p_y = 0;// ���� �ٴ�(ground)�ؿ� �׷����� �ʰ� �ϱ� ���� ���� y��ǥ �������� �� ����
						KeyAdmin.time_1 = 0;
						ball_air_status = 0; // ���� �ٴڿ� ���� ���̱� ������ �ش� ������ 0���� �����ؼ� ���� ���߿� ���� �ʴٰ� ��Ÿ�´�
					}
					// ball�� bar�� ����� �浹���� ��
					if(player_ball.ball_crash_status == 2) {
						ball_air_status = 0; // bar�� ���鿡 �ö�Ա� ������ ��ü���� y��ǥ�� �������� �ʱ� ���� ���� ���߿� ���� �ʴٰ� �ش� ������ ���� ��Ÿ�´�
						KeyAdmin.time_1 = 0; // ���� ���߿� �߱� �������� ���� �ð��� ��Ÿ���� ���� ���� �ʱⰪ���� �缳��
						p_y = bar.bar_posY[crash.overlapBallBar()]-750; 
						/*
						 * bar_posY[crash.overlapBallBar()] = 
						 * 		 b_posy(=p_y+750-Ball_img.getHeight(null)) + Ball_img.getHeigh(null) �� �Ἥ bar�� ���� y��ǥ�� �����ߴ�
						*/
						
					}
					// ball�� bar�� �Ʒ���� �浹���� ��
					if(player_ball.ball_crash_status == 4) {
						velocity = 0; // velocity�� 0���� �ٲ㼭 ���� �Ʒ��� ���ϰ� ���̵��� �ߴ�
						ball_crash_status=0; // ���� bar�� �Ʒ��鿡 �ε����ڸ��� bar���� �������� ������ �ش� ������ �ʱⰪ���� �����ؼ� bar�� �浹���� �ʰ� �ִٰ� ��Ÿ�´�
						crash.activity_cnt =0;
					}
				}
				
				
				
				/*
				 * ball_action_x�� ball_air_status�� 0�� ���� x�� �������� ���� �̵��ϰ� �־����� �����Ѽ� 
				 * ���� ���߿� ���� �� Ű�� ���ؼ��� �¿� �̵��� ���� �ʰ� ball_action_x���� ���� ���� ���������� �̵��ϰ� �ߴ�.
				 */
				if((player_ball.p_x+320-player_ball.Ball_img.getWidth(null)/2)>=0) {
					// ���� frame�� ���� ���� �Ǳ� ��
					if(ball_action_x == -1 || ball_action_x == -2) p_x += ball_action_x;		
				}
				
				if((p_x+(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)/2+(Ball_img.getWidth(null))/2)<(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)) {
					// ���� frame�� ������ ���� �Ǳ� ��
					if(ball_action_x == 1 || ball_action_x == 2) p_x += ball_action_x;
				}
			}
		}
	}
}
