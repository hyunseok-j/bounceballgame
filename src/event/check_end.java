package event;

import main.Game_frame;
import map.player_ball;

// ������ �������� ���θ� üũ���ִ� Ŭ���� check_end
public class check_end{
	
	public check_end() {
	}

	public static void F_check_end() {
		if(player_ball.b_posy <= 0) {	// ���� y��ǥ�� �������� ���� ����(0)�� �Ѿ�� 
			Game_frame.game_status = 2;	// ���� ���¸� 2�� �ٲ� endingȭ���� ������ �Ѵ�.
		}
	}
}
