package demo;

import java.util.Scanner;

public class Demo8 {
 public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	System.out.println("������ɼ���");
	int pr=sc.nextInt();
	if(pr>=90&&pr<=100) {
		System.out.println("ѧ���ĳɼ�Ϊ��A");
	}
	else if(pr>=80) {
		System.out.println("ѧ���ĳɼ�Ϊ��B");
	}else if(pr>=70) {
		System.out.println("ѧ���ĳɼ�Ϊ��C");
	}
	else if(pr>=60) {
		System.out.println("ѧ���ĳɼ�Ϊ��D");
	}
	else {
		System.out.println("ѧ���ĳɼ�Ϊ��E");
	}
}
}
