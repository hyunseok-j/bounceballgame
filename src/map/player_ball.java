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

	public static int p_x=0;	// Ball_img의 상대적 x좌표 (이미지의 왼쪽 위를 기준)
	public static int p_y=0;	// Ball_img의 상대적 y좌표 (이미지의 왼쪽 위를 기준)
	public static int b_posx ;	// Ball_img의 실제 x좌표 (이미지의 왼쪽 위를 기준)
	public static int b_posy ;	// Ball_img의 실제 y좌표 (이미지의 왼쪽 위를 기준)
	public static int b_right_posx; // =  b_posx + ball.width : Ball_img의 가장 오른쪽 좌표
	public static int b_down_posy; // = b_posy + ball.height : Ball_img의 가장 아래쪽 좌표
	public static int velocity = 6; // 공의 y축 속력
	public static Image Ball_img; // 공 이미지를 저장할 Image객체
	public static int ball_air_status=0; // 공이 공중에 있는지 아닌지 여부 판단을 위한 변수 (0: bar나 ground위, 1: 허공)
	public static int ball_crash_status=0; // 공이 bar와 ground의 어느 면에 부딪힌 지를 나타내는 변수
	public static int ball_action_x=0; // 공의 x축 속도(방향+속력)
	public static final double g_accelartion = 0.00000980665; // 중력가속도(m / ms^2)  
	public player_ball() {
		init(); // 초기 공의 상태를 초기화한다
	}
	private void init() {
		Ball_img = new ImageIcon(Main.class.getResource("../image/ball.png")).getImage();
	}
	public synchronized  static void BallProcess() {
		b_posx = p_x+320-Ball_img.getWidth(null)/2; // 공의 x좌표  = x변화량(0) + 초기x좌표값[화면가운데]
		b_posy = p_y+750-Ball_img.getHeight(null); // 공의 y좌표 = y변화량(0) + 초기y좌표값[ground(750)바로위]
		b_right_posx = b_posx + Ball_img.getWidth(null); // 공의 가장 오른쪽 x좌표
		b_down_posy = b_posy + Ball_img.getHeight(null); // 공의 가장 아래쪽 y좌표
//------when user is playing game---------------------------------------
		if(Game_frame.game_status==1) {
			if(ball_air_status==1)  // 공이 공중에 있을 때 
			{ 	
				if(Background.by==-1623 || Background.by==0) { // 배경화면의 제일 아랫 부분과 제일 윗 부분일 때 공이 움직인다
					long time_2 = System.currentTimeMillis(); // 현재 시각
					int t = (int)((time_2-KeyAdmin.time_1)); // 현재 시각에서 공이 공중에 있기 시작한 시각을 빼서 공중에 얼마동안 있었는지 그 시간을 구해서 t에 저장한다
					p_y = p_y+(int)(g_accelartion*t*t/2)-velocity*t/1000; 
					/* 
					 * 나중 변위 = 처음 변위 + (g*t^2)/2 + velocity*t
					 *  velocity(속도:+,-가 변할 수 있다) , 위의 공식은 t가 sec단위
					 */
					
					// 바닥과 ball이 충돌했을 때
					if(p_y>0){
						p_y = 0;// 공이 바닥(ground)밑에 그려지지 않게 하기 위해 공의 y좌표 결정변수 값 수정
						KeyAdmin.time_1 = 0;
						ball_air_status = 0; // 공이 바닥에 있을 것이기 때문에 해당 변수를 0으로 설정해서 공이 공중에 있지 않다고 나타냈다
					}
					// ball이 bar의 윗면과 충돌했을 때
					if(player_ball.ball_crash_status == 2) {
						ball_air_status = 0; // bar의 윗면에 올라왔기 때문에 객체들의 y좌표를 변경하지 않기 위해 공이 공중에 있지 않다고 해당 변수를 통해 나타냈다
						KeyAdmin.time_1 = 0; // 공이 공중에 뜨기 시작했을 때의 시각을 나타내는 변수 값을 초기값으로 재설정
						p_y = bar.bar_posY[crash.overlapBallBar()]-750; 
						/*
						 * bar_posY[crash.overlapBallBar()] = 
						 * 		 b_posy(=p_y+750-Ball_img.getHeight(null)) + Ball_img.getHeigh(null) 을 써서 bar와 공의 y좌표를 조정했다
						*/
						
					}
					// ball이 bar의 아랫면과 충돌했을 때
					if(player_ball.ball_crash_status == 4) {
						velocity = 0; // velocity를 0으로 바꿔서 공이 아래로 향하게 보이도록 했다
						ball_crash_status=0; // 공이 bar의 아랫면에 부딪히자마자 bar에서 떨어지기 때문에 해당 변수를 초기값으로 설정해서 bar와 충돌하지 않고 있다고 나타냈다
						crash.activity_cnt =0;
					}
				}
				
				
				
				/*
				 * ball_action_x에 ball_air_status가 0일 때의 x축 방향으로 어디로 이동하고 있었는지 기억시켜서 
				 * 공이 공중에 있을 때 키에 의해서는 좌우 이동이 되지 않고 ball_action_x값에 따라 왼쪽 오른쪽으로 이동하게 했다.
				 */
				if((player_ball.p_x+320-player_ball.Ball_img.getWidth(null)/2)>=0) {
					// 공이 frame의 왼쪽 끝이 되기 전
					if(ball_action_x == -1 || ball_action_x == -2) p_x += ball_action_x;		
				}
				
				if((p_x+(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)/2+(Ball_img.getWidth(null))/2)<(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)) {
					// 공이 frame의 오른쪽 끝이 되기 전
					if(ball_action_x == 1 || ball_action_x == 2) p_x += ball_action_x;
				}
			}
		}
	}
}
