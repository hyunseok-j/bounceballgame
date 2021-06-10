package event;

import java.util.ArrayList;

import key.KeyAdmin;
import main.Game_frame;
import map.Background;
import map.bar;
import map.bullet;
import map.bulletManager;
import map.player_ball;

// 공과 bar, 공과 bullet, bullet과 화면의 충돌을 확인하고 그에 따라 공,bar,bullet 객체의 상태를 나타내는 멤버변수 값을 바꿔주는 클래스 crash
public class crash  {
	private static int bar_index = -1; // crashed bar's index : 충돌된 bar의 인덱스 값
	private static int bullet_index = -1; // crashed bullet's index : 충돌된 bullet에 해당되는 ArrayList<Bullet>의 인덱스 값
	public static int activity_cnt = 0; // * 공이 bar의 윗면 또는 아랫면에 충돌한 경우 한번만 공의 충돌상태를 나타내는 멤버변수(ball_crash_status)를 바꾸게 해주는 변수
										// ** activity_cnt가 0이면 공의 충돌상태 멤버변수를 바꾸고 1이면 공은 그대로 이전 상태를 유지하게 된다.
	
	public crash() {
	}
	
// Check_overlap : ball, Bar_img(0~19) - return crashed bar's index
	public synchronized static int overlapBallBar() {
		// * 공의 상하좌우 좌표와 각 bar의 상하좌우 좌표를 비교해서 if문의 조건에 만족하면 충돌했다고 인식하고 충돌된 bar의 인덱스 값을 반환하는 함수
		// ** 공이 bar와 충돌하지 않았다고 판단되면 -1을 반환한다.
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
		// * 공의 상하좌우 좌표와 각 bullet의 상하좌우 좌표를 비교해서 if문의 조건에 만족하면 충돌했다고 인식하고 충돌된 bullet에 해당되는 ArrayList<Bullet>의 인덱스 값을 반환하는 함수
		// ** 공이 bullet과 충돌하지 않았다고 판단되면 -1을 반환한다.
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
	
// Event when overlap occurs : ball, Bar_img(0~19) - 충돌한 bar의 면에 따라 공의 상태를 나타내는 player_ball(공)의 멤버 변수값 조정
	public synchronized static void crashEventBallBar() {
		if((bar_index = overlapBallBar())!=-1) { // 공이 bar와 충돌한 경우
			//----------------------crash 1 : bar 왼쪽면과 공이 충돌
			if(player_ball.ball_action_x==1 &&
			   player_ball.b_posx < bar.bar_posX[bar_index] &&
			   bar.bar_posX[bar_index] <= player_ball.b_right_posx &&
			   bar.bar_posY[bar_index] <= player_ball.b_posy &&
			   bar.bar_down_posY[bar_index] >= player_ball.b_down_posy) 
			   {
				 player_ball.ball_action_x = -1; // 공의 x축 진행방향을 왼쪽으로 바꾼다.
			   }
			//----------------------crash 2 : bar 윗면과 공이 충돌
			else if(bar.bar_posX[bar_index] < player_ball.b_posx &&
					player_ball.b_right_posx < bar.bar_right_posX[bar_index] &&
					bar.bar_posY[bar_index] < player_ball.b_down_posy &&
					player_ball.b_posy < bar.bar_posY[bar_index]
					)
			{
				if(activity_cnt==0) player_ball.ball_crash_status = 2; // 공의 충돌상태를 나타내는 변수값을 바꿔줘서 공이 bar위에 그대로 올라와 있게 해준다.
				activity_cnt=1; // * 공이 bar의 윗면과 만나는 동안 계속 ball_crash_status값을 2로 바꾸면 space키입력을 해도 공의 좌표가 변하지 않으므로
								// ** 한 번만 ball_crash_status값을 바꾸게 해주기 위해서 activity_cnt값을 1로 만들었다.
			}
			//---------------------crash 3 : bar 오른쪽면과 공이 충돌
			else if(player_ball.ball_action_x==-1 &&
				    player_ball.b_posx <= bar.bar_right_posX[bar_index] &&
				    bar.bar_right_posX[bar_index] < player_ball.b_right_posx &&
				    bar.bar_posY[bar_index] <= player_ball.b_posy &&
					bar.bar_down_posY[bar_index] >= player_ball.b_down_posy) 
					{
						player_ball.ball_action_x = 1; // 공의 x축 진행방향을 오른쪽으로 바꾼다.
					}
			//---------------------crash 4 : bar 아랫면과 공이 충돌
			else if(bar.bar_posX[bar_index] < player_ball.b_right_posx &&
					player_ball.b_right_posx < bar.bar_right_posX[bar_index] + player_ball.Ball_img.getWidth(null) &&
					bar.bar_posX[bar_index]-player_ball.Ball_img.getWidth(null) < player_ball.b_posx &&
					player_ball.b_posx < bar.bar_right_posX[bar_index] &&
					player_ball.b_posy > bar.bar_posY[bar_index] && 
					player_ball.b_posy < bar.bar_down_posY[bar_index] &&
					player_ball.b_down_posy> bar.bar_down_posY[bar_index]) 
			{  
				if(activity_cnt==0) player_ball.ball_crash_status = 4; // 공의 충돌상태를 나타내는 변수값을 바꿔줘서 공이 bar의 아랫면에 충돌했을 때 기존 속도보다 더 빠르게 튕겨나가게 해준다.
				activity_cnt=1;	// 위의 crash2와 동일한 이유
			}
		}
		else {
			//----------------------공의 위치가 ground도 아니고 bar와 충돌하지도 않은 상태
			// * Ex ) 공이  bar위에서 왼쪽끝에 도달해서 bar범위에서 벗어날 때 공이 bar에서 떨어지게 하는 함수
			if(player_ball.ball_crash_status==2 && player_ball.b_down_posy != Background.ground_posY)
			{
				player_ball.ball_action_x=0; // 공이 일직선으로 떨어지게 해준다
				player_ball.ball_crash_status=0; // 공의 충돌 상태를 다시 처음 값으로 돌린다( 0: 공이 어떤 다른 객체에 총돌하지 않은 경우)
				player_ball.ball_air_status = 1; // 공이 공중에 있는 상태라고 알려줘 공 또는 배경화면의 좌표를 바꾸면서 공이 이동하게 만든다.
				player_ball.velocity = 0;	// 등가속도 공식에서 초기 중력가속도 반대로 작용하는 속도가 없게 만들어 공이 위로 올라갔다가 아래로 가지않고 바로 바닥을 향해서 움직이게 했다.
				activity_cnt=0; // 다음 충돌이 일어날 경우를 대비해 activity_cnt값을 다시 0으로 만들어 공이 떨어지는 중간에 bar의 윗면이나 아랫면에 부딪히는 경우 공의 충돌 상태를 바꿀 수 있게 했다.
				KeyAdmin.time_1 = System.currentTimeMillis()-500; // 공의 y좌표는 시간을 변수로 하는 등가속도 공식이기 때문에 그 시간을 측정하기 위해 초기 떨어지기 시작할 때의 시각을 time_1에 저장한다.
			}
		}
	}
	
	// crashEventBulletBorder : bullet이 frame의 오른쪽 끝에 도달하게 되면 bullet의 방향을 나타내는 변수 bullet_dir의 값을 1로 바꿔서 bullet의 진행방향을 바꾸는 함수 
	public synchronized static void crashEventBulletBorder (ArrayList<bullet> a) {
		for(int i=0;i<a.size();i++) {
			if(a.get(i).bullet_x+a.get(i).Bullet_img.getWidth(null) >= Game_frame.f_width) { // bullet의 제일 오른쪽 x좌표가 frame의 width보다 커지는 경우
				a.get(i).bullet_dir=1;	// bullet의 진행뱡을 바꿔준다.
			}
		}
	}
	
	public synchronized static void crashEventBallBullet() {
		// 총알 객체 중 공과 충돌하는 객체를 발견하면 해당 인덱스를 bullet_index에 저장 후 아래를 실행한다.
		if((bullet_index=overlapBallBullet(bulletManager.Bullet)) != -1) { // 공이 bullet객체와 충돌했을 때
			
			// 공이 공중에 있지 않을 때
			if(player_ball.ball_air_status==0) {
						// 공이 bullet의 왼쪽에서 충돌한 경우
						if(player_ball.b_posx < bulletManager.Bullet.get(bullet_index).bullet_x) {
							if(player_ball.b_right_posx >= bulletManager.Bullet.get(bullet_index).bullet_x) {
								player_ball.p_x -= 5; // 공의 x좌표를 왼쪽으로 5이동시켜 bullet에 의해 공이 왼쪽으로 튕겨내게 보이게 했다.
							}
						}
						// 공이 bullet의 오른쪽에서 충돌한 경우
						else if(player_ball.b_posx > bulletManager.Bullet.get(bullet_index).bullet_x) {
							if(player_ball.b_posx <= bulletManager.Bullet.get(bullet_index).bullet_right_x) {
								player_ball.p_x += 5; // 공의 x좌표를 오른쪽으로 5이동시켜 bullet에 의해 공이 오른쪽으로 튕겨내게 보이게 했다.
							}
						}
			}
			
			// 공이 공중에 있을 때
			else if(player_ball.ball_air_status == 1) {
				// 1)공이 오른쪽 아래로 향하는 총알과 부딪혔을 때 
				if(bulletManager.Bullet.get(bullet_index).bullet_dir == 0) {
					// 1-1)공이 직선으로 올라가고 내려가는 경우
					if(player_ball.ball_action_x == 0) {
						player_ball.velocity = 0;
						player_ball.ball_action_x = 1;
					}
					// 1-2)공이 오른쪽 방향으로 이동하는 포물선 궤도로 움직일 때
					else if(player_ball.ball_action_x == 1) {
						// 1-2-1)공이 총알의 왼쪽에서 충돌한 경우
						if(player_ball.b_posx < bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.velocity = 0;
						}
						// 1-2-2)공이 총알의 오른쪽에서 충돌한 경우
						else if(player_ball.b_posx > bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.ball_action_x = 2; 
							// -> 총알의 속도를 더 받아서 공의 오른쪽 방향의 속력을 높임
						}
						
					}
					// 1-3)공이 왼쪽 방향으로 이동하는 포물선 궤도로 움직일 때
					else if(player_ball.ball_action_x == -1) {
						player_ball.ball_action_x = -player_ball.ball_action_x; 
						// -> 반대방향의 총알과 충돌했기 때문에 공의 진행 방향을 좌에서 우로 바꿈
						player_ball.velocity = 0;
						// -> 공이 총알과 충돌 후 더 이상 포물선 궤도의 위 방향으로 이동하지 못하고 더 빠르게 아래로 향하게 하기 위해 속도값 재조정
					}
				}
				// 2)공이 왼쪽 아래로 향하는 총알과 부딪혔을 때 
				else if(bulletManager.Bullet.get(bullet_index).bullet_dir == 1) {
					// 2-1)공이 직선으로 올라가고 내려가는 경우
					if(player_ball.ball_action_x == 0) {
						player_ball.velocity = 0;
						player_ball.ball_action_x = -1;
					}
					// 2-2)공이 오른쪽 방향으로 이동하는 포물선 궤도로 움직일 때
					else if(player_ball.ball_action_x == 1) {
						player_ball.ball_action_x = -player_ball.ball_action_x;
						// -> 반대방향의 총알과 충돌했기 때문에 공의 진행 방향을 좌에서 우로 바꿈
						player_ball.velocity = 0;
						// -> 공이 총알과 충돌 후 더 이상 포물선 궤도의 위 방향으로 이동하지 못하고 더 빠르게 아래로 향하게 하기 위해 속도값 재조정
					}
					// 2-3)공이 왼쪽 방향으로 이동하는 포물선 궤도로 움직일 때
					else if(player_ball.ball_action_x == -1) {
						// 2-3-1)공이 총알의 왼쪽에서 충돌한 경우
						if(player_ball.b_posx < bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.ball_action_x = -2;
						}
						// 2-3-2)공이 총알의 오른쪽에서 충돌한 경우
						else if(player_ball.b_posx > bulletManager.Bullet.get(bullet_index).bullet_x) {
							player_ball.velocity = 0;
							// -> 공이 총알과 충돌 후 더 이상 포물선 궤도의 위 방향으로 이동하지 못하고 더 빠르게 아래로 향하게 하기 위해 속도값 재조정
						}
					}
				}
			}
		}
	}
}
