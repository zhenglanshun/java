package demo;

import java.util.Scanner;

public class caidaxiao {
public static void main(String[] args) {
	int a=(int)(Math.random()*100)+1;
	Scanner sc=new Scanner(System.in);
	while(true) {
		System.out.println("������²����֣�");
		int b=sc.nextInt();
		if(b>a) {
			System.out.println("���ź�����µĴ���");
		}else if(b<a) {
			System.out.println("���ź�����µ�С��");
		}else {
			System.out.println("��ϲ��µĶ���");
			break;
		}
	}
}
}
