package main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import event.crash;
import key.KeyAdmin;
import map.Background;
import map.bar;
import map.bullet;
import map.bulletManager;
import map.player_ball;

public class Draw extends JPanel{
	public static Image buffImage; // 버퍼보내는 이미지 
	public static Graphics buffg; // 버퍼
	
	public static int cnt = 0;
	public static int gap = 0;
	public static int base_y = 0;
	
	static Background bg = new Background();
	static player_ball pb = new player_ball();
	static bar b = new bar();
	static bullet bl = new bullet();
	static bulletManager blm = new bulletManager();
	
	public Draw(){
		setVisible(true); // Draw 패널 화면에 보이게 해줬다
	}
	public void paint(Graphics g) {
		buffImage = createImage(Game_frame.f_width,Game_frame.f_height); // 초기 버퍼 이미지 생성 (화면 frame 사이즈에 맞춰서 생성시킴)
		buffg = buffImage.getGraphics();
		
		update(g);
	}
	public void update(Graphics g) { // draw관련 모든 함수 호출 
		draw_background();
		draw_player_ball();
		draw_bar();
		draw_bullet();
		// 모두 버퍼에 그린 다음
		g.drawImage(buffImage, 0, 0, this); // 버퍼 이미지를 화면에 그린다
	}
//background print---------------------------------------------------------------------------
	public synchronized  void draw_background() {
		Draw.buffg.clearRect(0, 0, Game_frame.f_width, Game_frame.f_height); // 화면 버퍼에 그려져있는 것들 전부 지우는 부분
//<< status : 0 game start
		if(Game_frame.game_status==0) {
			// 배경화면 y좌표가 점점 증가하면서 배경화면이 이동하게 했다
			if(Background.firsty<0) { // 배경화면의 제일 윗부분이 frame에 그려지기 전은
				// 배경 처음 위치 (x,y)=(0,-1623)
				Background.firsty+=1; // 배경화면의 y좌표를 1씩 증가시켜 이동시키고
			}else if(Background.firsty==0) { // 배경화면의 제일 윗부분이 frame에 그려지면
				Background.firsty=-1623; // 다시 배경화면이 제일 처음 그려진 위치로 돌아가게 했다
			}
			Draw.buffg.drawImage(Background.Background_img,0,Background.firsty,this); // 위의 과정을 거쳐 결정된 
			Draw.buffg.drawImage(Background.Presstxt_img,130,500,this); // space바를 입력하라는 이미지를 frame의 중앙 아래에 표시
			Draw.buffg.drawImage(Background.Title_img,70,300,this); // 게임 제목 이미지를 frame의 중앙 위에 표시
		}
//<< status : 1 game playing
		else if(Game_frame.game_status==1) {
//----------------------- 파란 바탕 화면 출력 ---------------------------------------------------------
			// 배경 맨 처음 위치 (x,y)=(0,-1623)
			if(player_ball.ball_air_status==1) // 공이 공중에 있을 때
			{
				if(player_ball.b_posy<=Game_frame.f_height/2 && 
				   player_ball.b_posy > Game_frame.f_height/2 - 10 &&
				   Background.by >= -1623 &&
				   Background.by <= 0) { // 공이 frame의 y축 중앙에 있고 frame이 배경화면의 일부로 꽉 채워질 때
					long time_2 = System.currentTimeMillis(); // 현재 시각
					int t = (int)((time_2-KeyAdmin.time_1)); // 현재 시각에서 공이 공중에 있기 시작한 시각을 빼서 공중에 얼마동안 있었는지 그 시간을 구해서 t에 저장한다
					base_y = -(int)(player_ball.g_accelartion*t*t/2)+player_ball.velocity*t/1000; 
					/* 
					 * 등가속도 공식으로 공은 frame 가운데에 고정되어 있는 채 배경화면이 원래 공이 이동해야하는 반대방향으로 이동해서
					 * 사용자는 공이 움직이는 것처럼 느낄 수 있게 했다 
					 */
					 
					Background.by = Background.by+base_y; // 나중 위치 = 처음 위치 + 변화량
					
					if(Background.by<=-1623){
						// 배경 이미지 중 가장 아래 부분이 frame에 출력될 때 위에 식 때문에 배경 화면 이외의 화면을 출력시키지 않기 위해 by 값 재조정
						Background.by=-1623;
					}
					if(player_ball.ball_crash_status==2) {
						player_ball.ball_air_status = 0; // bar의 윗면에 올라왔기 때문에 객체들의 y좌표를 변경하지 않기 위해 공이 공중에 있지 않다고 해당 변수를 통해 나타냈다
						crash.activity_cnt=0;
						player_ball.velocity = 6; // bar의 아랫면과 충돌한 후 bar의 윗면에 올라올 수도 있기 때문에 cash4일 때 velocity바꾼 것을 다시 원래 값으로 되돌렸다
						KeyAdmin.time_1 = 0; // 공이 공중에 뜨기 시작했을 때의 시각을 나타내는 변수 값을 초기값으로 재설정
						
						
						//(bar, ground, Background.by position) value 보정
						int check =crash.overlapBallBar();
						int p = bar.bar_posY[check];
						bar.bar_posY[check]=player_ball.b_posy+player_ball.Ball_img.getHeight(null);
						/*
						 *  위의 등가속도 공식이 원래는 실수로 다뤄져야하는데 frame내부 좌표가 int라서 int값을 공식의 변수에 넣다보니 오차가 생겨
						 *  공이미지와 bar이미지가 겹쳐서 화면에 나오는 경우가 생겨
						 *  이런 경우를 없애기 위해 공과 충돌한 bar의 y좌표를 공의 가장 아래 좌표와 같게 되도록하고
						 *  충돌된 bar외에 다른 bar도 서로간의 간격 125를 맞추기 위해 충돌한 bar가 이동한 만큼을 gap에 저장하고
						 *  다른 bar에도 그 값을 적용시켰다
						 *  배경 역시 bar와 같이 움직이므로 gap만큼을 by에 더해줬고
						 *  ground도 gap만큼을 ground의 y좌표에 더해줬다.
						 */
						
						gap = bar.bar_posY[check]-p;
						Background.by = Background.by + gap ;
						for(int i=0;i<bar.bar_posY.length;i++) {
							if(i!=check) {
								bar.bar_posY[i] = bar.bar_posY[i] + gap;
							}
						}
						Background.ground_posY = Background.ground_posY + gap;
						
					}
					// ball이 bar의 아랫면과 충돌했을 때
					if(player_ball.ball_crash_status == 4) {
						player_ball.velocity = 0; // velocity를 0으로 바꿔서 공이 아래로 향하게 보이도록 했다
						player_ball.ball_crash_status=0; // 공이 bar의 아랫면에 부딪히자마자 bar에서 떨어지기 때문에 해당 변수를 초기값으로 설정해서 bar와 충돌하지 않고 있다고 나타냈다
						crash.activity_cnt =0;
					}
					// 배경이미지 중 가장 위 부분이 frame에 출력될 때 base_y때문에 배경 이외의 화면을 출력되지 않기 위한 조건문
					if(Background.by>=0) {
						Background.by = 0; 
					}
				}
			}
			Draw.buffg.drawImage(Background.Background_img,0,Background.by,this);
//----------------------- 구름 출력  - 속도 제어  + 좌표 설정 --------------------------------------------
			for(int i=0;i<Background.cx.length;i++) {
				if(Background.cx[i]<1400) {
					Background.cx[i]+=3+i*1; // 구름마다 x좌표 이동속도를 다르게 해서 이동시킴
				}else {Background.cx[i]=0;} // x좌표가 frame에서 사라지면 다시 frame의 왼쪽에서 나오도록 좌표 조정
				Draw.buffg.drawImage(Background.Cloud_img[i],Background.cx[i],i*200,this); // 구름 이미지를 그린다
			}
//----------------------- 바닥 출력 --------------------------------------------------------------
				Background.prev_ground = Background.ground_posY; // ground(땅)가 이동하기 직전 ground의 y좌표를 prev_ground에 저장
			    if(player_ball.ball_air_status==1) Background.ground_posY = Background.ground_posY+base_y; 
			    // 공이 움직여야할 때 상대적으로 ground의 y좌표를 변화시켜 공이 움직이는 것처럼 보이게 한다
				if(Background.ground_posY<=750) {
					/*
					 * ground는 초기y좌표 값인 750보다 작아지면 안된다
					 * =>원래 위치에 그려지거나 아니면 frame의 아래로 이동하기 때문
					 * 그래서 초기값보다 ground의 y좌표가 작아지는 경우가 발생하면 ground의 y좌표를 초기값으로 바꿔주게 했다
					 */
					Background.ground_posY=750;
				}
				if((Background.ground_posY-750)>=1623) Background.ground_posY = Background.prev_ground;
				/*
				 * 모든 객체들의 총 이동거리는 배경화면의 y축 길이보다 길어지며 안되므로 
				 * ground가 y축 방향으로 배경화면의 y축 길이인 1623보다 길어지면 바로 이전 ground y좌표인 prev_ground를 ground의 y좌표가 될 수 있게 했다 
				 */
			Draw.buffg.drawImage(Background.Ground_img, 0, Background.ground_posY, this); // ground 이미지를 그린다
		}
//<< status : 2 game end
		else if(Game_frame.game_status==2) {
			Background.Background_img = new ImageIcon(Main.class.getResource("../image/end.png")).getImage(); // 배경이미지를 게임엔딩화면배경인 검은색 배경화면으로 바꾼다
			Draw.buffg.drawImage(Background.Background_img,0,0,this); // 배경이미지를 그린다
			Draw.buffg.drawImage(Background.Clear_img,80,350,this); // 게임 클리어 텍스트 이미지를 화면의 center에 표시되게 그린다
		}
	}
//player_ball print-----------------------------------------------------------------------------	
	public synchronized  void draw_player_ball() {
		if(Game_frame.game_status==1) {
			Draw.buffg.drawImage(player_ball.Ball_img,player_ball.b_posx,player_ball.b_posy,this); // 공의 좌표에 맞게 Draw 패널에 공 이미지를 그린다
			}
	}
//bar print-----------------------------------------------------------------------------	
	public synchronized  void draw_bar() {
		if(Game_frame.game_status==1) {// 게임 실행 중일 때
			// 20개의 bar 객체를 그린다
			Draw.buffg.drawImage(bar.Bar_img[0],bar.bar_posX[0],bar.bar_posY[0],this);// stage 1 : bar의 좌우 간격만 좁아진다
			Draw.buffg.drawImage(bar.Bar_img[1],bar.bar_posX[1],bar.bar_posY[1],this);
			Draw.buffg.drawImage(bar.Bar_img[2],bar.bar_posX[2],bar.bar_posY[2],this);
			Draw.buffg.drawImage(bar.Bar_img[3],bar.bar_posX[3],bar.bar_posY[3],this);
			Draw.buffg.drawImage(bar.Bar_img[4],bar.bar_posX[4],bar.bar_posY[4],this);
			Draw.buffg.drawImage(bar.Bar_img[5],bar.bar_posX[5],bar.bar_posY[5],this);
			Draw.buffg.drawImage(bar.Bar_img[6],bar.bar_posX[6],bar.bar_posY[6],this);
			Draw.buffg.drawImage(bar.Bar_img[7],bar.bar_posX[7],bar.bar_posY[7],this);// stage2 : bar의 좌우 간격이 좁아지고 좌우로 이동하는 bar가 존재
			Draw.buffg.drawImage(bar.Bar_img[8],bar.bar_posX[8],bar.bar_posY[8],this);
			Draw.buffg.drawImage(bar.Bar_img[9],bar.bar_posX[9],bar.bar_posY[9],this);
			Draw.buffg.drawImage(bar.Bar_img[10],bar.bar_posX[10],bar.bar_posY[10],this);
			Draw.buffg.drawImage(bar.Bar_img[11],bar.bar_posX[11],bar.bar_posY[11],this);
			Draw.buffg.drawImage(bar.Bar_img[12],bar.bar_posX[12],bar.bar_posY[12],this);
			Draw.buffg.drawImage(bar.Bar_img[13],bar.bar_posX[13],bar.bar_posY[13],this);
			Draw.buffg.drawImage(bar.Bar_img[14],bar.bar_posX[14],bar.bar_posY[14],this);// stage3 : 위의 상태는 유지하고 총알(bullet)이 나온다
			Draw.buffg.drawImage(bar.Bar_img[15],bar.bar_posX[15],bar.bar_posY[15],this);
			Draw.buffg.drawImage(bar.Bar_img[16],bar.bar_posX[16],bar.bar_posY[16],this);
			Draw.buffg.drawImage(bar.Bar_img[17],bar.bar_posX[17],bar.bar_posY[17],this);
			Draw.buffg.drawImage(bar.Bar_img[18],bar.bar_posX[18],bar.bar_posY[18],this);
			Draw.buffg.drawImage(bar.Bar_img[19],bar.bar_posX[19],bar.bar_posY[19],this);
		}
	}
//bullet print-----------------------------------------------------------------------------
	public synchronized  void draw_bullet() {
		if(Game_frame.game_status==1) { // 게임 실행 중일 때
			// ArrayList<Bullet> blm 에 있는 bullet 객체를 그린다
				for(int i=0;i<blm.Bullet.size();i++) {
					Draw.buffg.drawImage(blm.Bullet.get(i).Bullet_img,
							blm.Bullet.get(i).bullet_x,blm.Bullet.get(i).bullet_y,this);
				}
		}
	}

}
