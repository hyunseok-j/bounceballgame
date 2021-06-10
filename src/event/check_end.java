package event;

import main.Game_frame;
import map.player_ball;

// 게임이 끝났는지 여부를 체크해주는 클래스 check_end
public class check_end{
	
	public check_end() {
	}

	public static void F_check_end() {
		if(player_ball.b_posy <= 0) {	// 공이 y좌표가 프레임의 가장 위쪽(0)을 넘어가면 
			Game_frame.game_status = 2;	// 게임 상태를 2로 바꿔 ending화면이 나오게 한다.
		}
	}
}
