package key;

import java.awt.Toolkit;

import event.crash;
import main.Draw;
import main.Game_frame;
import map.player_ball;

// 사용자의 키입력을 관리하는 클래스 KeyAdmin
public class KeyAdmin {
	public static long time_1; // 공이 움직이기 시작한 시각을 저장하는 정적변수, 공의 y좌표 값이 변하면 다른 객체의 y좌표 값도 변하므로 bullet,bar객체도 time_1을 이용해 움직임을 결정한다
	public KeyAdmin() {}
	
	public synchronized  static void KeyProcess() {
		// 키 입력받은 것을 처리하는 함수
		if(Game_frame.game_status==0) {
			// --------- 1) 게임이 시작화면이고 ---------
			if(Game_frame.KeySpace==true) {
				// ----- 2) 스페이스키가 눌렸으면  ---------
				Game_frame.game_status=1; // 다음 게임 진행 화면으로 넘어가게 한다
			}
		}
		else if(Game_frame.game_status==1) {
			// ------- 실제 게임이 실행 중일 때---------
			//---------LEFT가 입력됐을 때-------------------------------------------------------------------------
			if(Game_frame.KeyLeft==true) {
				if((player_ball.p_x+320-player_ball.Ball_img.getWidth(null)/2)>=0) { // 공의 x좌표가 frame의 왼쪽 끝을 넘어가지 않으면 (= 공의 x좌표가 0미만이 되지 않는다면 )
					if(player_ball.ball_air_status == 0) { // 공이 공중이 있지 않을 때만 x의 좌우를 키입력에 의해 조작할 수 있다
						player_ball.p_x-=1; // 	공의 x좌표를 왼쪽으로 1이동하게 한다
						player_ball.ball_action_x = -1; // 공이 스페이스바키로 인해 공중에 있게 되는 경우 바닥이나 ground에서 입력받은 좌우 조작값을 기억하게 한다. 지금의 경우는 왼쪽으로 이동했었다고 저장시켰다
					}
				}
			}
			//---------RIGHT가 입력됏을 때-------------------------------------------------------------------------
			if(Game_frame.KeyRight==true) {
				if((player_ball.p_x+(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)/2+(player_ball.Ball_img.getWidth(null))/2)<(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - Game_frame.f_width/2)) {
					//  공의 x좌표가 frame의 오른쪽 끝을 넘어가지 않으면 
					if(player_ball.ball_air_status == 0) { // 공이 공중에 있지 않다면
						player_ball.p_x+=1; // 공의 x좌표를 오른쪽으로 1이동하게 한다
						player_ball.ball_action_x = 1; // 공이 오른쪽으로 이동하고 있었다고 기억시켰다. 이 값이 1일 때 스페이스바를 누르면 공은 공중에서 오른쪽으로 이동하면서 포물선을 그리며 이동한다
					}
				}
			}
			//---------NONE(키입력이 없는 경우)--------------------------------------------------------------------------
			if(Game_frame.KeyRight==false && Game_frame.KeyLeft==false) {
				if(player_ball.ball_air_status == 0) player_ball.ball_action_x = 0; // 좌측의 값이 0인 상태에서 스페이스바를 입력하면 좌우 이동없이 공이 일직선으로 위아래로 움직인다
			}
			//---------SPACE가 입력됏을 때-------------------------------------------------------------------------
			if(Game_frame.KeySpace==true) {
				if(player_ball.ball_air_status==0) {
					player_ball.ball_crash_status = 0; // 다음 bar와의 충돌을 대비해 충돌 상태 변수를 0으로 초기값으로 다시 저장
					time_1 = System.currentTimeMillis(); // 공이 움직이기 시작한 시각 측정해서 저장
					player_ball.ball_air_status = 1;	// 공이 공중에 있다는 것을 알리기 위해 값을 0에서 1로 바꿨다
					crash.activity_cnt =0;	// 다음 bar와의 충돌했을 때 bar의 윗면과 아랫면에 부딪힌 경우 공의 충돌 상태 멤버변수(ball_crash_status)값이 변할 수 있게 만들어 줬다
					player_ball.velocity =6; // 공이 bar의 아랫면과 부딪혔을 때 바로 바닥을 향하게 하기 위해 velocity를 0으로 바꿨었는데 다시 원래 처음 값인 6으로 바꿔서 공이 일반적인 움직임으로 움직이게 한다
				}
			}
		}
		else if(Game_frame.game_status==2) {
			// ------- 게임엔딩화면일 때---------
			if(Game_frame.KeySpace==true) { // 스페이스바가 입력되면
				System.exit(1); // 게임을 종료시킨다
			}
		}
	}
}
