package main;

/*
 * Developer : 정현석
 * 
 * Project Name : Bounce_Ball_Game
 * Class : check_end, crash, KeyAdmin, Draw, Game_frame, Main, Background, bar, bullet, bulletManager, player_ball
 * 외부 라이브러리 : X
 * 
 * [설명]
 * JFrame을 상속받은 Game_frame클래스에 Runnable 인터페이스를 가져와서 스레드화를 해서 run()함수를 실행시키게 했다. 
 * run()함수 내에는 게임 내 객체들을 처리하는 모든 함수가 들어가 있고 repaint()를 통해 Draw클래스의 update(g)를 지속적으로 호출시켜 객체들을 버퍼에 그렸다가 frame에 버퍼이미지를 그리도록 했다.
 * 키 입력도 Game_frame에 KeyListener를 상속시켜 구현했고 키 입력으로 처리되야할 각 클래스의 멤버 변수 값은 KeyAdmin 클래스에서 처리했다.
 * 게임 시작 시 spacebar를 누르면 게임 진행 화면으로 넘어가고 공의 이동은 배경화면의 제일 아래부분이 그려질 때 공이 frame의 절반만큼 올라가고 그 후 배경화면의 제일 윗부분이 frame이 출력 되기 전까지 
 * 배경화면의 y좌표가 증가하면서 배경화면을 움직여 상대적으로 공이 움직여보이게 했다. bar 객체 같은 경우도 배경과 같이 움직여야 하므로 배경화면의 y좌표가 변경될 때 똑같이 bar의 y좌표도 변하게 했다.
 * 배경화면의 가장 윗부분이 frame에 출력되면 공이 다시 움직일 수 있게 하여 공이  frame 윗부분에 도달하게 되면 게임엔딩 화면이 나오도록 했다.
 * 
 */
 
public class Main {
	public static void main(String[] args) {
		// 전체적인 프레임과 Gui그리기 , 충돌여부 확인을 하는 스레드를 실행시키기 위해 Game_frame() 생성자 호출
		Game_frame gf = new Game_frame();
	}
}