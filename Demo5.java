package demo0103;

import java.util.Arrays;
import java.util.Scanner;

public class Demo5 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String ts="��ģ�������͡������֡�������������,"
				+ "1��Ӧ��ͣ�2��Ӧ�����֣�3��Ӧ������ѡ��";
		System.out.println(ts);
		int ss=sc.nextInt();
		if(ss==1) {
			//�û���������������������Ӳ�������
			//�ڿ���̨���һ���ַ���
			System.out.println("�������һ��������");
			//���û������ֵ��ֵ������a
			int a = sc.nextInt();
			System.out.println("������ڶ���������");
			//���û������ֵ��ֵ������b
			int b = sc.nextInt();
			//a+b�������ֵ�����ͱ���sum
			int sum=a+b;
			//�ڿ���̨���sum
			System.out.println("��ͽ��Ϊ��"+sum);
		}else if(ss==2) {
			//����һ��1-100֮�������
			int a=(int)(Math.random()*100)+1;
			while(true) {
				//������ʾ
				System.out.println("������²����֣�");
				//�ȴ����벢��ȡֵ
				int b=sc.nextInt();
				//�жϱ���a�ͱ���b�Ƿ����
				//boolean res=(a==b);
				if(b>a) {
					System.out.println("���ź�����µĴ���");
					
				}else if(b<a){
					System.out.println("���ź�����µ�С��");
				}else {
					System.out.println("��ϲ�㣬�¶���");
					break;
				}
			}
		}else if(ss==3) {
			//����һ���������鲢��̬��ʼ��
			int [] nums= {412,278,176,543,284,392,136,715};
			//int [] nums1= {1,2,3,4,5,6,7,8};
			//nums.length ��ȡ����ĳ���
			for(int j=0;j<nums.length-1;j++) {
				for(int i=0;i<nums.length-1;i++) {
					//�Ƚ��������±�Ϊi��i+1Ԫ�صĴ�С
					//����±�Ϊi��Ԫ�ش����±�Ϊi+1��Ԫ�أ�������Ԫ�ػ����������е�λ��
					if(nums[i]>nums[i+1]) {
						//���� i=1
						//nums[i]=278;
						//c=278
						int c=nums[i];
						//nums[i]=176;
						nums[i]=nums[i+1];
						//nums[i+1]=278;
						nums[i+1]=c;
					}
				}
			}
			System.out.println(Arrays.toString(nums));
		}				
	}
}
