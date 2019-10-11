package com.springboot.demo.util;

import java.util.Scanner;

/**
 * 恩格码加密解密
 *
 * @author lixing
 */
public class Enigma {
    static public void main(String[] arg) {
        String P;
        String M;//明文
        int key1, key2, key3 = 0;
        //使用Scanner类定义对象
        Scanner in = new Scanner(System.in);
        System.out.println("请输入明文：");
        P = in.nextLine();
        System.out.println("请输入密钥" + "\n" + "请输入第一个密码轮的初始位置：");
        key1 = in.nextInt();
        System.out.println("请输入第二个密码轮的初始位置：");
        key2 = in.nextInt();
        System.out.println("请输入第三个密码轮的初始位置：");
        key3 = in.nextInt();
        P = P.toUpperCase();// 将明文转换成大写
        P = P.replaceAll("[^A-Z]", "");//去除所有非字母的字符
        Encrypt en = new Encrypt();
        Decrypt dec = new Decrypt();
        M = en.encrypt(key1, key2, key3, P);
        dec.decrypt(key1, key2, key3, M);
    }

}

class Encrypt {
    String encrypt(int key1, int key2, int key3, String str) {
        String m = "";
        char[] chara = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] firstCycle = {3, 8, 7, 5, 2, 9, 1, 10, 6, 4};
        int[] secondCycle = {8, 6, 2, 5, 4, 7, 3, 9, 1, 10};
        int[] thirdCycle = {4, 7, 8, 2, 3, 1, 10, 6, 5, 9};
        int counter1 = 10, counter2 = 10, counter3 = 10, i, j, k1, k2, k3;
        for (i = 0; i < str.length(); i++) {
            for (j = 0; j < 10; j++) {
                if (str.charAt(i) == chara[j]) {
                    break;
                }
            }
            if ((counter1 + j - key1) < 0) {
                j = 10 + j + counter1 - key1;
            } else {
                j = j + counter1 - key1;
                for (k1 = 0; k1 < 10; k1++) {
                    if (list[(j) % 10] == firstCycle[k1]) {
                        break;
                    }
                }
                k1 = 10 - counter1 + k1;
                if ((counter2 + k1 - key2) < 0) {
                    k1 = 10 + counter2 + k1 - key2;

                } else {
                    k1 = counter2 + k1 - key2;
                    for (k2 = 0; k2 < 10; k2++) {
                        if (list[(k1) % 10] == secondCycle[k2]) {
                            break;
                        }
                    }
                    k2 = 10 - counter2 + k2;
                    if ((counter3 + k2 - key3) < 0) {
                        k2 = 10 + counter3 + k2 - key3;
                    } else {
                        k2 = counter3 + k2 - key3;
                        for (k3 = 0; k3 < 10; k3++) {
                            if (list[(k2) % 10] == thirdCycle[k3]) {
                                break;
                            }
                        }
                        m += chara[(k3 + key3 + 10 - counter3) % 10];
                        counter3--;
                    }
                }


                if (counter3 == 0) {
                    counter3 = 10;
                    counter2--;
                    if (counter2 == 0) {
                        counter2 = 10;
                        counter1--;
                        if (counter1 == 0) {
                            counter1 = 10;
                        }

                    }

                }

            }
        }


        System.out.println("密文是：" + m);
        return m;


    }

}

class Decrypt {
    void decrypt(int key1, int key2, int key3, String str) {
        String m = "";
        char[] chara = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] firstCycle = {3, 8, 7, 5, 2, 9, 1, 10, 6, 4};
        int[] secondCycle = {8, 6, 2, 5, 4, 7, 3, 9, 1, 10};
        int[] thirdCycle = {4, 7, 8, 2, 3, 1, 10, 6, 5, 9};
        int counter1 = 10, counter2 = 10, counter3 = 10, i, j, k1, k2 = 0, k3;
        for (i = 0; i < str.length(); i++) {
            for (j = 0; j < 10; j++) {
                if (str.charAt(i) == chara[j]) {
                    break;
                }
            }
            if ((counter3 + j - key3) < 0) {
                j = 10 + j + counter3 - key3;

            } else {
                j = j + counter3 - key3;
                for (k1 = 0; k1 < 10; k1++) {
                    if (thirdCycle[(j) % 10] == list[k1]) {
                        break;
                    }
                }
                k1 = 10 - counter3 + k1;


            }


            k2 = 10 - counter2 + k2;
            k2 = counter1 + k2 + key2;
            for (k3 = 0; k3 < 10; k3++) {
                if (firstCycle[(k2) % 10] == list[k3]) {
                    break;
                }
            }
            m += chara[(k3 + key1 + 10 - counter1) % 10];
            counter3--;
            if (counter3 == 0) {
                counter3 = 10;
                counter2--;
                if (counter2 == 0) {
                    counter2 = 10;
                    counter1--;
                    if (counter1 == 0) {
                        counter1 = 10;
                    }

                }

            }

        }
        System.out.println("明文是：" + m);
    }

}
