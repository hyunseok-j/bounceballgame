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
	public static Image buffImage; // ���ۺ����� �̹��� 
	public static Graphics buffg; // ����
	
	public static int cnt = 0;
	public static int gap = 0;
	public static int base_y = 0;
	
	static Background bg = new Background();
	static player_ball pb = new player_ball();
	static bar b = new bar();
	static bullet bl = new bullet();
	static bulletManager blm = new bulletManager();
	
	public Draw(){
		setVisible(true); // Draw �г� ȭ�鿡 ���̰� �����
	}
	public void paint(Graphics g) {
		buffImage = createImage(Game_frame.f_width,Game_frame.f_height); // �ʱ� ���� �̹��� ���� (ȭ�� frame ����� ���缭 ������Ŵ)
		buffg = buffImage.getGraphics();
		
		update(g);
	}
	public void update(Graphics g) { // draw���� ��� �Լ� ȣ�� 
		draw_background();
		draw_player_ball();
		draw_bar();
		draw_bullet();
		// ��� ���ۿ� �׸� ����
		g.drawImage(buffImage, 0, 0, this); // ���� �̹����� ȭ�鿡 �׸���
	}
//background print---------------------------------------------------------------------------
	public synchronized  void draw_background() {
		Draw.buffg.clearRect(0, 0, Game_frame.f_width, Game_frame.f_height); // ȭ�� ���ۿ� �׷����ִ� �͵� ���� ����� �κ�
//<< status : 0 game start
		if(Game_frame.game_status==0) {
			// ���ȭ�� y��ǥ�� ���� �����ϸ鼭 ���ȭ���� �̵��ϰ� �ߴ�
			if(Background.firsty<0) { // ���ȭ���� ���� ���κ��� frame�� �׷����� ����
				// ��� ó�� ��ġ (x,y)=(0,-1623)
				Background.firsty+=1; // ���ȭ���� y��ǥ�� 1�� �������� �̵���Ű��
			}else if(Background.firsty==0) { // ���ȭ���� ���� ���κ��� frame�� �׷�����
				Background.firsty=-1623; // �ٽ� ���ȭ���� ���� ó�� �׷��� ��ġ�� ���ư��� �ߴ�
			}
			Draw.buffg.drawImage(Background.Background_img,0,Background.firsty,this); // ���� ������ ���� ������ 
			Draw.buffg.drawImage(Background.Presstxt_img,130,500,this); // space�ٸ� �Է��϶�� �̹����� frame�� �߾� �Ʒ��� ǥ��
			Draw.buffg.drawImage(Background.Title_img,70,300,this); // ���� ���� �̹����� frame�� �߾� ���� ǥ��
		}
//<< status : 1 game playing
		else if(Game_frame.game_status==1) {
//----------------------- �Ķ� ���� ȭ�� ��� ---------------------------------------------------------
			// ��� �� ó�� ��ġ (x,y)=(0,-1623)
			if(player_ball.ball_air_status==1) // ���� ���߿� ���� ��
			{
				if(player_ball.b_posy<=Game_frame.f_height/2 && 
				   player_ball.b_posy > Game_frame.f_height/2 - 10 &&
				   Background.by >= -1623 &&
				   Background.by <= 0) { // ���� frame�� y�� �߾ӿ� �ְ� frame�� ���ȭ���� �Ϻη� �� ä���� ��
					long time_2 = System.currentTimeMillis(); // ���� �ð�
					int t = (int)((time_2-KeyAdmin.time_1)); // ���� �ð����� ���� ���߿� �ֱ� ������ �ð��� ���� ���߿� �󸶵��� �־����� �� �ð��� ���ؼ� t�� �����Ѵ�
					base_y = -(int)(player_ball.g_accelartion*t*t/2)+player_ball.velocity*t/1000; 
					/* 
					 * ��ӵ� �������� ���� frame ����� �����Ǿ� �ִ� ä ���ȭ���� ���� ���� �̵��ؾ��ϴ� �ݴ�������� �̵��ؼ�
					 * ����ڴ� ���� �����̴� ��ó�� ���� �� �ְ� �ߴ� 
					 */
					 
					Background.by = Background.by+base_y; // ���� ��ġ = ó�� ��ġ + ��ȭ��
					
					if(Background.by<=-1623){
						// ��� �̹��� �� ���� �Ʒ� �κ��� frame�� ��µ� �� ���� �� ������ ��� ȭ�� �̿��� ȭ���� ��½�Ű�� �ʱ� ���� by �� ������
						Background.by=-1623;
					}
					if(player_ball.ball_crash_status==2) {
						player_ball.ball_air_status = 0; // bar�� ���鿡 �ö�Ա� ������ ��ü���� y��ǥ�� �������� �ʱ� ���� ���� ���߿� ���� �ʴٰ� �ش� ������ ���� ��Ÿ�´�
						crash.activity_cnt=0;
						player_ball.velocity = 6; // bar�� �Ʒ���� �浹�� �� bar�� ���鿡 �ö�� ���� �ֱ� ������ cash4�� �� velocity�ٲ� ���� �ٽ� ���� ������ �ǵ��ȴ�
						KeyAdmin.time_1 = 0; // ���� ���߿� �߱� �������� ���� �ð��� ��Ÿ���� ���� ���� �ʱⰪ���� �缳��
						
						
						//(bar, ground, Background.by position) value ����
						int check =crash.overlapBallBar();
						int p = bar.bar_posY[check];
						bar.bar_posY[check]=player_ball.b_posy+player_ball.Ball_img.getHeight(null);
						/*
						 *  ���� ��ӵ� ������ ������ �Ǽ��� �ٷ������ϴµ� frame���� ��ǥ�� int�� int���� ������ ������ �ִٺ��� ������ ����
						 *  ���̹����� bar�̹����� ���ļ� ȭ�鿡 ������ ��찡 ����
						 *  �̷� ��츦 ���ֱ� ���� ���� �浹�� bar�� y��ǥ�� ���� ���� �Ʒ� ��ǥ�� ���� �ǵ����ϰ�
						 *  �浹�� bar�ܿ� �ٸ� bar�� ���ΰ��� ���� 125�� ���߱� ���� �浹�� bar�� �̵��� ��ŭ�� gap�� �����ϰ�
						 *  �ٸ� bar���� �� ���� ������״�
						 *  ��� ���� bar�� ���� �����̹Ƿ� gap��ŭ�� by�� �������
						 *  ground�� gap��ŭ�� ground�� y��ǥ�� �������.
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
					// ball�� bar�� �Ʒ���� �浹���� ��
					if(player_ball.ball_crash_status == 4) {
						player_ball.velocity = 0; // velocity�� 0���� �ٲ㼭 ���� �Ʒ��� ���ϰ� ���̵��� �ߴ�
						player_ball.ball_crash_status=0; // ���� bar�� �Ʒ��鿡 �ε����ڸ��� bar���� �������� ������ �ش� ������ �ʱⰪ���� �����ؼ� bar�� �浹���� �ʰ� �ִٰ� ��Ÿ�´�
						crash.activity_cnt =0;
					}
					// ����̹��� �� ���� �� �κ��� frame�� ��µ� �� base_y������ ��� �̿��� ȭ���� ��µ��� �ʱ� ���� ���ǹ�
					if(Background.by>=0) {
						Background.by = 0; 
					}
				}
			}
			Draw.buffg.drawImage(Background.Background_img,0,Background.by,this);
//----------------------- ���� ���  - �ӵ� ����  + ��ǥ ���� --------------------------------------------
			for(int i=0;i<Background.cx.length;i++) {
				if(Background.cx[i]<1400) {
					Background.cx[i]+=3+i*1; // �������� x��ǥ �̵��ӵ��� �ٸ��� �ؼ� �̵���Ŵ
				}else {Background.cx[i]=0;} // x��ǥ�� frame���� ������� �ٽ� frame�� ���ʿ��� �������� ��ǥ ����
				Draw.buffg.drawImage(Background.Cloud_img[i],Background.cx[i],i*200,this); // ���� �̹����� �׸���
			}
//----------------------- �ٴ� ��� --------------------------------------------------------------
				Background.prev_ground = Background.ground_posY; // ground(��)�� �̵��ϱ� ���� ground�� y��ǥ�� prev_ground�� ����
			    if(player_ball.ball_air_status==1) Background.ground_posY = Background.ground_posY+base_y; 
			    // ���� ���������� �� ��������� ground�� y��ǥ�� ��ȭ���� ���� �����̴� ��ó�� ���̰� �Ѵ�
				if(Background.ground_posY<=750) {
					/*
					 * ground�� �ʱ�y��ǥ ���� 750���� �۾����� �ȵȴ�
					 * =>���� ��ġ�� �׷����ų� �ƴϸ� frame�� �Ʒ��� �̵��ϱ� ����
					 * �׷��� �ʱⰪ���� ground�� y��ǥ�� �۾����� ��찡 �߻��ϸ� ground�� y��ǥ�� �ʱⰪ���� �ٲ��ְ� �ߴ�
					 */
					Background.ground_posY=750;
				}
				if((Background.ground_posY-750)>=1623) Background.ground_posY = Background.prev_ground;
				/*
				 * ��� ��ü���� �� �̵��Ÿ��� ���ȭ���� y�� ���̺��� ������� �ȵǹǷ� 
				 * ground�� y�� �������� ���ȭ���� y�� ������ 1623���� ������� �ٷ� ���� ground y��ǥ�� prev_ground�� ground�� y��ǥ�� �� �� �ְ� �ߴ� 
				 */
			Draw.buffg.drawImage(Background.Ground_img, 0, Background.ground_posY, this); // ground �̹����� �׸���
		}
//<< status : 2 game end
		else if(Game_frame.game_status==2) {
			Background.Background_img = new ImageIcon(Main.class.getResource("../image/end.png")).getImage(); // ����̹����� ���ӿ���ȭ������ ������ ���ȭ������ �ٲ۴�
			Draw.buffg.drawImage(Background.Background_img,0,0,this); // ����̹����� �׸���
			Draw.buffg.drawImage(Background.Clear_img,80,350,this); // ���� Ŭ���� �ؽ�Ʈ �̹����� ȭ���� center�� ǥ�õǰ� �׸���
		}
	}
//player_ball print-----------------------------------------------------------------------------	
	public synchronized  void draw_player_ball() {
		if(Game_frame.game_status==1) {
			Draw.buffg.drawImage(player_ball.Ball_img,player_ball.b_posx,player_ball.b_posy,this); // ���� ��ǥ�� �°� Draw �гο� �� �̹����� �׸���
			}
	}
//bar print-----------------------------------------------------------------------------	
	public synchronized  void draw_bar() {
		if(Game_frame.game_status==1) {// ���� ���� ���� ��
			// 20���� bar ��ü�� �׸���
			Draw.buffg.drawImage(bar.Bar_img[0],bar.bar_posX[0],bar.bar_posY[0],this);// stage 1 : bar�� �¿� ���ݸ� ��������
			Draw.buffg.drawImage(bar.Bar_img[1],bar.bar_posX[1],bar.bar_posY[1],this);
			Draw.buffg.drawImage(bar.Bar_img[2],bar.bar_posX[2],bar.bar_posY[2],this);
			Draw.buffg.drawImage(bar.Bar_img[3],bar.bar_posX[3],bar.bar_posY[3],this);
			Draw.buffg.drawImage(bar.Bar_img[4],bar.bar_posX[4],bar.bar_posY[4],this);
			Draw.buffg.drawImage(bar.Bar_img[5],bar.bar_posX[5],bar.bar_posY[5],this);
			Draw.buffg.drawImage(bar.Bar_img[6],bar.bar_posX[6],bar.bar_posY[6],this);
			Draw.buffg.drawImage(bar.Bar_img[7],bar.bar_posX[7],bar.bar_posY[7],this);// stage2 : bar�� �¿� ������ �������� �¿�� �̵��ϴ� bar�� ����
			Draw.buffg.drawImage(bar.Bar_img[8],bar.bar_posX[8],bar.bar_posY[8],this);
			Draw.buffg.drawImage(bar.Bar_img[9],bar.bar_posX[9],bar.bar_posY[9],this);
			Draw.buffg.drawImage(bar.Bar_img[10],bar.bar_posX[10],bar.bar_posY[10],this);
			Draw.buffg.drawImage(bar.Bar_img[11],bar.bar_posX[11],bar.bar_posY[11],this);
			Draw.buffg.drawImage(bar.Bar_img[12],bar.bar_posX[12],bar.bar_posY[12],this);
			Draw.buffg.drawImage(bar.Bar_img[13],bar.bar_posX[13],bar.bar_posY[13],this);
			Draw.buffg.drawImage(bar.Bar_img[14],bar.bar_posX[14],bar.bar_posY[14],this);// stage3 : ���� ���´� �����ϰ� �Ѿ�(bullet)�� ���´�
			Draw.buffg.drawImage(bar.Bar_img[15],bar.bar_posX[15],bar.bar_posY[15],this);
			Draw.buffg.drawImage(bar.Bar_img[16],bar.bar_posX[16],bar.bar_posY[16],this);
			Draw.buffg.drawImage(bar.Bar_img[17],bar.bar_posX[17],bar.bar_posY[17],this);
			Draw.buffg.drawImage(bar.Bar_img[18],bar.bar_posX[18],bar.bar_posY[18],this);
			Draw.buffg.drawImage(bar.Bar_img[19],bar.bar_posX[19],bar.bar_posY[19],this);
		}
	}
//bullet print-----------------------------------------------------------------------------
	public synchronized  void draw_bullet() {
		if(Game_frame.game_status==1) { // ���� ���� ���� ��
			// ArrayList<Bullet> blm �� �ִ� bullet ��ü�� �׸���
				for(int i=0;i<blm.Bullet.size();i++) {
					Draw.buffg.drawImage(blm.Bullet.get(i).Bullet_img,
							blm.Bullet.get(i).bullet_x,blm.Bullet.get(i).bullet_y,this);
				}
		}
	}

}
