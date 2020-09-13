import java.util.*;
public class Railfencecipher {
private int key;
public Railfencecipher() {}
public Railfencecipher(int key) {
this.key = key;
}
public void setKey(int key) {
this.key = key;
}
public String encryptMessage(String message) {
String[] encrypted = new String[this.key];
int i = 0, d = 1, r = 0;
for(int j = 0; j < encrypted.length; j++) {
encrypted[j] = "";
}
while(i < message.length()) {
encrypted[r] += message.charAt(i);
if(r == this.key - 1) d = -1;
else if(r == 0) d = 1;
r += d; i++;
}
return String.join("", encrypted);
}
public String decryptMessage(String message) {
char[] decrypted = new char[message.length()];
int i = 0, g = 2 * this.key - 2;
for(int j = 0; j < this.key; j++) {
for(int k = j; k < message.length(); k += g) {
decrypted[k] = message.charAt(i);
i++;
if(i == message.length()) {
j = this.key;
break;
}
}
g -= 2;
if(g == 0) g = 2 * this.key - 2;
}
return String.valueOf(decrypted);
}
private static void menu() {
System.out.println("_____Rail Fence Cipher_____");
System.out.println("1. Enter plain text");
System.out.println("2. Enter key text");
System.out.println("3. Encrypt");
System.out.println("4. Decrypt");
System.out.println("5. Exit");
}
public static void main(String[] args) {
int choice = 0, key;
Scanner scanner = new Scanner(System.in);
String message = "";
Railfencecipher railFenceCipher = new Railfencecipher();
while(choice != 5) {
menu();
try {
choice = scanner.nextInt();
}catch(Exception E) {
System.out.println("Invalid option.");
continue;
}
switch(choice) {
case 1:{
System.out.println("Enter the message to encrypt: ");
message = scanner.next();
break;
}
case 2:{
System.out.println("Enter key: ");
key = scanner.nextInt();
railFenceCipher.setKey(key);
break;
}
case 3:{
System.out.println("The encrypted message is: ");
System.out.println(railFenceCipher.encryptMessage(message));
break;
}
case 4: {
System.out.println("Enter the message to decrypt: ");
message = scanner.next();
System.out.println("The decrypted message is: ");
System.out.println(railFenceCipher.decryptMessage(message));
break;
}
case 5:{
break;
}
default : {
System.out.println("Invalid Choice.");
break;
}
}
}
scanner.close();
}
}
