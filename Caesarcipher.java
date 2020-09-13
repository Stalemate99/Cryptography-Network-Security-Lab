import java.util.Scanner;
public class Caesarcipher {
public static void encryption(String text, int shift) {
String encryptText = new String();
encryptText = "";
for(int i = 0; i<text.length(); i++) {
if(Character.isUpperCase(text.charAt(i))) {
encryptText += (char)(((int)text.charAt(i) + shift - 65) % 26 +
65);
}else {
encryptText += (char)(((int)text.charAt(i) + shift - 97) % 26 +
97);
}
}
System.out.println(encryptText);
}
public static void decryption(String text, int shift) {
String decryptText = new String();
decryptText = "";
for(int i = 0; i<text.length(); i++) {
if(Character.isUpperCase(text.charAt(i))) {
decryptText += (char)(((int)text.charAt(i) - shift - 65) % 26 + 65);
}else {
decryptText += (char)(((int)text.charAt(i) - shift - 97) % 26 + 97);
}
}
System.out.println(decryptText);
}
private static void menu() {
System.out.println("_____Caesar Cipher_____");
System.out.println("1. Enter plain text");
System.out.println("2. Enter key");
System.out.println("3. Encrypt");
System.out.println("4. Decrypt");
System.out.println("5. Exit");
}
public static void main(String[] args) {
Scanner inp = new Scanner(System.in);
int choice = 0;
String plainText = "";
Integer key = 0;
while(choice != 5) {
menu();
try {
choice = inp.nextInt();
}catch(Exception E) {
System.out.println("Invalid option.");
continue;
}
switch(choice) {
case 1:{
plainText = inp.next();
break;
}
case 2:{
key = inp.nextInt();
break;
}
case 3:{
if(plainText.equals("") || key==0) {
System.out.println("Enter required parameters
for encrypting");
}else {
encryption(plainText, key);
}
break;
}
case 4:{
if(plainText.equals("") || key==0) {
System.out.println("Enter required parameters
for encrypting");
}else {
decryption(plainText, key);
}
break;
}
case 5:{
break;
}
default:{
System.out.println("Invalid option, try again.");
break;
}
}
}
inp.close();
System.exit(0);
}
}
