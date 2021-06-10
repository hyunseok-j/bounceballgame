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

	public static Image[] Bar_img; // bar�� �̹���
	public static int[] bar_posX; // bar�� x��ǥ
	public static int[] bar_posY; // bar�� y��ǥ
	public static int[] bar_right_posX; // bar�� ���� ������ x��ǥ
	public static int[] bar_down_posY; // bar�� ���� �Ʒ��� y��ǥ
	public static int[] bar_first_posY; // ���� ó�� ���۵��� �� bar�� y��ǥ�� �����ϴ� ����
	public static int[] prev_bar_posY; // bar�� �����̱� �� �ڽ��� y��ǥ
	public static int[] bar_dir= {0,0,0,0}; // 4���� bar�� �̵���Ű�� ���� ���Ⱚ�� ������ �迭
	public static int bar_speed=1;	// �¿�� �����̴� bar�� �ӵ�
	public static int[] index = {10,12,13,19}; // �����̴� 4�� bar�� index��
	public bar() {
		init(); // bar�� ��� ��� ���� �������ִ� �Լ�
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
			// 1) bar�� �̹����� �ε��ؼ� Image��ü Bar_img�� �־���
			Bar_img[i] = new ImageIcon(Main.class.getResource("../image/bar.jpg")).getImage();
			// 2) Bar_img�� index�� ������ ������ �� ���� ������ �߶� �����ͼ� �ٽ� Bar_img�� �����ϹǷν� ���� ������ ���� �ö� ������ bar�� �¿찡 ���� �������� �������
			Bar_img[i] = createImage(new FilteredImageSource(Bar_img[i].getSource(),new CropImageFilter(0, 0, 239-7*i, 28)));
			
			// �Ʒ��� �ش� �������� ������ ������ �켱 �ʱ�ȭ���� ���̴�
			bar_right_posX[i]=0;
			bar_down_posY[i]=0;
		}
		for(int i=0,num=625;i<bar_posY.length;i++,num-=125) {
			// bar�� y��ǥ�� 625���� 125�������� �����Ű�� ���� �ݺ���
			bar_posY[i]=num;
			
			if(i==15) { //  index 14�� 15�� bar�� y��ǥ ���� ���� ���߷��� �ߴ�
				num = num + 125;
				bar_posY[i]=bar_posY[14];
			}
			if(i==18) { //  index 17�� 18�� bar�� y��ǥ ���� ���� ���߷��� �ߴ�
				num = num + 125;
				bar_posY[i]=bar_posY[17];
			}
		}
		
		// ���� �ݺ����� ������ ������ �����ϴ� ������ bar_first_posY�� bar�� ���� �������� �� ���� y��ǥ�� �����صд�
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
		
		// bar�� x��ǥ�� ���� �����ڰ� ���ϴ� ������ ��ġ�� �ְ� �; bar��ü �ϳ��� x��ǥ�� ���������
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
	
	// BarMove() : bar �� �¿�� �����̴� bar�� x��ǥ ����
	public static void BarMove() {
		for(int i=0;i<bar_dir.length;i++) {
			// �¿�� �����̴� 4���� bar�� �ε��� ���� index[] �迭�� ������ �����̴� bar�� �ٷ� ���ٰ����ϰ� ��
			if(bar_dir[i]==0) { 
				// bar_dir == 0 : bar�� ���������� �̵��ϰ� �Ѵ�
				bar_posX[index[i]] = bar_posX[index[i]] + bar_speed;
				if(bar_posX[index[i]]+Bar_img[index[i]].getWidth(null)>=Game_frame.f_width) { // �� �� bar�� ���� ������ ��ǥ���� frame�� ������ ���� ����� �� 
					bar_dir[i]=1;	// bar�� �����̴� ������ �������� ���ϰ� �ٲ۴�
				}
			}
			else if(bar_dir[i]==1) {
				// bar_dir == 1 : bar�� �������� �̵��ϰ� �Ѵ�
				bar_posX[index[i]] = bar_posX[index[i]]-bar_speed; 
				if(bar_posX[index[i]]<=0) { // �� �� bar�� ���� ������ ��ǥ���� frame�� ���� ���� ����� �� 
					bar_dir[i]=0; // bar�� �����̴� ������ ���������� ���ϰ� �ٲ۴�
				}
		    }	
		}
	}
	
	// BarProcess() : bar�� y��ǥ�� �����ϰ� �¿�� �̵��ϴ� bar�� �����ϴ� BarMove()�� ȣ��
	public static void BarProcess() {
		for(int i=0;i<bar_posY.length;i++) {
			
			prev_bar_posY[i] = bar_posY[i]; // bar�� �̵��ϱ� ���� bar�� y��ǥ�� prev_bar_posY�� ����
			
			if(player_ball.ball_air_status==1) bar_posY[i] = bar_posY[i]+Draw.base_y; 
						// * ���� ���߿� ���� �� ���ȭ��� bar�� y��ǥ�� �Ȱ��� ���� �̵��ϹǷ� 
						// ** bar�� y��ǥ�� ���ȭ���� y��ȭ���� �����ش�
			
			if(bar_posY[i]<bar_first_posY[i]) { // bar�� y��ǥ�� ���� ó�� bar�� y��ǥ���� �۾����� �����ϴ� ������ �� (�� ��Ȳ�� �߻��ϸ� �ȵǹǷ�)
				bar_posY[i]=bar_first_posY[i]; // bar�� y��ǥ�� ���� ó�� bar�� y��ǥ�� �ٲ۴�
			}
			if((bar_posY[i]-bar_first_posY[i])>=1623) bar_posY[i]=prev_bar_posY[i]; // bar�� y��ǥ�� �� �̵��� �Ÿ��� ���ȭ���� ���̺��� �� ���
																					// ��ȭ�ϱ� ������ bar y��ǥ�� ���ư��� �ߴ�
			
			bar_right_posX[i] = bar_posX[i] + Bar_img[i].getWidth(null); // ��� bar�� ���� ������ x��ǥ ���� �����Ѵ�
		    bar_down_posY[i] = bar_posY[i] + Bar_img[i].getHeight(null); // ��� bar�� ���� �Ʒ��� y��ǥ ���� �����Ѵ�
		}
		BarMove(); // Ư�� bar�� �¿�� �����̰� �ϴ� �Լ� ȣ��
	}
}
