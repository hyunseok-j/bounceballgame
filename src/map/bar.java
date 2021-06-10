package map;

import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.Draw;
import main.Game_frame;
import main.Main;

public class bar extends JPanel{

	public static Image[] Bar_img; // bar의 이미지
	public static int[] bar_posX; // bar의 x좌표
	public static int[] bar_posY; // bar의 y좌표
	public static int[] bar_right_posX; // bar의 가장 오른쪽 x좌표
	public static int[] bar_down_posY; // bar의 가장 아래쪽 y좌표
	public static int[] bar_first_posY; // 게임 처음 시작됐을 때 bar의 y좌표를 저장하는 변수
	public static int[] prev_bar_posY; // bar가 움직이기 전 자신의 y좌표
	public static int[] bar_dir= {0,0,0,0}; // 4개의 bar를 이동시키기 위해 방향값을 저장한 배열
	public static int bar_speed=1;	// 좌우로 움직이는 bar의 속도
	public static int[] index = {10,12,13,19}; // 움직이는 4개 bar의 index값
	public bar() {
		init(); // bar의 모든 멤버 변수 설정해주는 함수
	}
	// init() : 
	private void init() {
		Bar_img = new Image[20];
		bar_first_posY = new int[20];
		bar_posX = new int[20];
		bar_posY = new int[20];
		bar_right_posX = new int [20];
		bar_down_posY = new int [20];
		prev_bar_posY = new int [20];
		
		for(int i=0;i<Bar_img.length;i++) {
			// 1) bar의 이미지를 로드해서 Image객체 Bar_img에 넣었다
			Bar_img[i] = new ImageIcon(Main.class.getResource("../image/bar.jpg")).getImage();
			// 2) Bar_img를 index가 증가할 때마다 더 작은 범위를 잘라서 가져와서 다시 Bar_img에 저장하므로써 공이 게임의 위로 올라갈 때마다 bar의 좌우가 점점 좁아지게 만들었다
			Bar_img[i] = createImage(new FilteredImageSource(Bar_img[i].getSource(),new CropImageFilter(0, 0, 239-7*i, 28)));
			
			// 아래는 해당 변수들을 임의의 값으로 우선 초기화해준 것이다
			bar_right_posX[i]=0;
			bar_down_posY[i]=0;
		}
		for(int i=0,num=625;i<bar_posY.length;i++,num-=125) {
			// bar의 y좌표를 625부터 125간격으로 저장시키기 위한 반복문
			bar_posY[i]=num;
			
			if(i==15) { //  index 14와 15인 bar의 y좌표 값을 같게 맞추려고 했다
				num = num + 125;
				bar_posY[i]=bar_posY[14];
			}
			if(i==18) { //  index 17와 18인 bar의 y좌표 값을 같게 맞추려고 했다
				num = num + 125;
				bar_posY[i]=bar_posY[17];
			}
		}
		
		// 위의 반복문과 과정은 같으나 저장하는 변수가 bar_first_posY로 bar가 게임 시작했을 때 가진 y좌표를 저장해둔다
		for(int i=0,num=625;i<bar_first_posY.length;i++,num-=125) {
			bar_first_posY[i]=num;
			if(i==15) {
				num = num + 125;
				bar_first_posY[i]=bar_first_posY[14];
			}
			if(i==18) {
				num = num + 125;
				bar_first_posY[i]=bar_first_posY[17];
			}
		}
		
		// bar의 x좌표를 각각 개발자가 원하는 고정된 위치에 넣고 싶어서 bar객체 하나씩 x좌표를 설정해줬다
		bar_posX[0] = 100;
		bar_posX[1] = 320;
		bar_posX[2] = 100;
		bar_posX[3] = 240;
		bar_posX[4] = 420;
		bar_posX[5] = 50;
		bar_posX[6] = 10;
		bar_posX[7] = 160;
		bar_posX[8] = 470;
		bar_posX[9] = 500;
		bar_posX[10] = 480;
		bar_posX[11] = 480;
		bar_posX[12] = 320;
		bar_posX[13] = 100;
		bar_posX[14] = 50;
		bar_posX[15] = 250;
		bar_posX[16] = 320;
		bar_posX[17] = 90;
		bar_posX[18] = 500;
		bar_posX[19] = 150;	
	}
	
	// BarMove() : bar 중 좌우로 움직이는 bar의 x좌표 제어
	public static void BarMove() {
		for(int i=0;i<bar_dir.length;i++) {
			// 좌우로 움직이는 4개의 bar의 인덱스 값을 index[] 배열로 가져와 움직이는 bar만 바로 접근가능하게 함
			if(bar_dir[i]==0) { 
				// bar_dir == 0 : bar가 오른쪽으로 이동하게 한다
				bar_posX[index[i]] = bar_posX[index[i]] + bar_speed;
				if(bar_posX[index[i]]+Bar_img[index[i]].getWidth(null)>=Game_frame.f_width) { // 이 때 bar의 가장 오른쪽 좌표값이 frame의 오른쪽 끝과 닿았을 때 
					bar_dir[i]=1;	// bar가 움직이는 방향을 왼쪽으로 향하게 바꾼다
				}
			}
			else if(bar_dir[i]==1) {
				// bar_dir == 1 : bar가 왼쪽으로 이동하게 한다
				bar_posX[index[i]] = bar_posX[index[i]]-bar_speed; 
				if(bar_posX[index[i]]<=0) { // 이 때 bar의 가장 오른쪽 좌표값이 frame의 왼쪽 끝과 닿았을 때 
					bar_dir[i]=0; // bar가 움직이는 방향을 오른쪽으로 향하게 바꾼다
				}
		    }	
		}
	}
	
	// BarProcess() : bar의 y좌표를 결정하고 좌우로 이동하는 bar을 제어하는 BarMove()를 호출
	public static void BarProcess() {
		for(int i=0;i<bar_posY.length;i++) {
			
			prev_bar_posY[i] = bar_posY[i]; // bar가 이동하기 직전 bar의 y좌표를 prev_bar_posY에 저장
			
			if(player_ball.ball_air_status==1) bar_posY[i] = bar_posY[i]+Draw.base_y; 
						// * 공이 공중에 있을 때 배경화면과 bar는 y좌표를 똑같은 값을 이동하므로 
						// ** bar의 y좌표에 배경화면의 y변화량을 더해준다
			
			if(bar_posY[i]<bar_first_posY[i]) { // bar의 y좌표가 제일 처음 bar의 y좌표보다 작아지기 시작하는 순간일 때 (이 상황은 발생하면 안되므로)
				bar_posY[i]=bar_first_posY[i]; // bar의 y좌표를 제일 처음 bar의 y좌표로 바꾼다
			}
			if((bar_posY[i]-bar_first_posY[i])>=1623) bar_posY[i]=prev_bar_posY[i]; // bar의 y좌표가 총 이동한 거리가 배경화면의 길이보다 긴 경우
																					// 변화하기 이전의 bar y좌표로 돌아가게 했다
			
			bar_right_posX[i] = bar_posX[i] + Bar_img[i].getWidth(null); // 계속 bar의 가장 오른쪽 x좌표 값을 수정한다
		    bar_down_posY[i] = bar_posY[i] + Bar_img[i].getHeight(null); // 계속 bar의 가장 아래쪽 y좌표 값을 수정한다
		}
		BarMove(); // 특정 bar를 좌우로 움직이게 하는 함수 호출
	}
}
