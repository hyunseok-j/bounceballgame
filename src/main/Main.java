package main;

/*
 * Developer : ������
 * 
 * Project Name : Bounce_Ball_Game
 * Class : check_end, crash, KeyAdmin, Draw, Game_frame, Main, Background, bar, bullet, bulletManager, player_ball
 * �ܺ� ���̺귯�� : X
 * 
 * [����]
 * JFrame�� ��ӹ��� Game_frameŬ������ Runnable �������̽��� �����ͼ� ������ȭ�� �ؼ� run()�Լ��� �����Ű�� �ߴ�. 
 * run()�Լ� ������ ���� �� ��ü���� ó���ϴ� ��� �Լ��� �� �ְ� repaint()�� ���� DrawŬ������ update(g)�� ���������� ȣ����� ��ü���� ���ۿ� �׷ȴٰ� frame�� �����̹����� �׸����� �ߴ�.
 * Ű �Էµ� Game_frame�� KeyListener�� ��ӽ��� �����߰� Ű �Է����� ó���Ǿ��� �� Ŭ������ ��� ���� ���� KeyAdmin Ŭ�������� ó���ߴ�.
 * ���� ���� �� spacebar�� ������ ���� ���� ȭ������ �Ѿ�� ���� �̵��� ���ȭ���� ���� �Ʒ��κ��� �׷��� �� ���� frame�� ���ݸ�ŭ �ö󰡰� �� �� ���ȭ���� ���� ���κ��� frame�� ��� �Ǳ� ������ 
 * ���ȭ���� y��ǥ�� �����ϸ鼭 ���ȭ���� ������ ��������� ���� ���������̰� �ߴ�. bar ��ü ���� ��쵵 ���� ���� �������� �ϹǷ� ���ȭ���� y��ǥ�� ����� �� �Ȱ��� bar�� y��ǥ�� ���ϰ� �ߴ�.
 * ���ȭ���� ���� ���κ��� frame�� ��µǸ� ���� �ٽ� ������ �� �ְ� �Ͽ� ����  frame ���κп� �����ϰ� �Ǹ� ���ӿ��� ȭ���� �������� �ߴ�.
 * 
 */
 
public class Main {
	public static void main(String[] args) {
		// ��ü���� �����Ӱ� Gui�׸��� , �浹���� Ȯ���� �ϴ� �����带 �����Ű�� ���� Game_frame() ������ ȣ��
		Game_frame gf = new Game_frame();
	}
}