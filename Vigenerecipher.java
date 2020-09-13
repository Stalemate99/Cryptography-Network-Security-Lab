import java.util.Scanner;
public class Vigenerecipher {
private static void menu() {
System.out.println("_____Vigenere Cipher_____");
System.out.println("1. Enter plain text");
System.out.println("2. Enter key text");
System.out.println("3. Print vigenere table");
System.out.println("4. Encrypt");
System.out.println("5. Decrypt");
System.out.println("6. Exit");
System.out.println("_________________________");
}
private static char[][] generateTable() {
char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
char[][] table = new char[26][26];
for ( int i = 0 ; i < 26 ; i++ ) {
for ( int j = 0 ; j < 26 ; j++ ) {
table[i][j] = alphabets[ ( i + j) % 26 ];
}
}
return table;
}
private static void printTable(char table[][]) {
for ( int i = 0 ; i < 26 ; i++ ){
for ( int j = 0 ; j < 26 ; j++ ){
System.out.print(table[i][j] + " ");
}
System.out.println("");
}
}
private static String generateKey( String plainText, String key ) {
char[] keyArray = key.toCharArray();
StringBuilder cipherKey = new StringBuilder();
for ( int i = 0 ; i < plainText.length() ; i++ ) {
cipherKey.append(Character.toString(keyArray[ i % key.length() ]));
}
return cipherKey.toString();
}
private static String encryption( String plainText, String cipherKey ) {
char[][] table = generateTable();
char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
StringBuilder alphaIndex = new StringBuilder();
for ( char c : alphabets ) {
alphaIndex.append(Character.toString(c));
}
char[] plainTextArray = plainText.toCharArray();
char[] cipherKeyArray = cipherKey.toCharArray();
StringBuilder encryptionText = new StringBuilder();
for ( int i = 0 ; i < plainText.length() ; i++ ) {
encryptionText.append( Character.toString(
table[alphaIndex.indexOf(Character.toString(plainTextArray[i]))][alphaIndex.indexOf(Character.toString(cipherKeyArray[i]))
]) );
}
return encryptionText.toString();
}
private static char returnTableIndex(char[][] table, Integer a, char b) {
int index = 0;
for (int i =0;i<26;i++){
if (table[a][i] == b){
index = i;
break;
}
}
return table[0][index];
}
private static String decryption( String plainText, String cipherKey ) {
char[][] table = generateTable();
char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
StringBuilder alphaIndex = new StringBuilder();
for ( char c : alphabets ) {
alphaIndex.append(Character.toString(c));
}
char[] plainTextArray = plainText.toCharArray();
char[] cipherKeyArray = cipherKey.toCharArray();
StringBuilder decryptionText = new StringBuilder();
for ( int i = 0 ; i < plainText.length() ; i++ ) {
decryptionText.append( Character.toString(returnTableIndex(table,
alphaIndex.indexOf(Character.toString(cipherKeyArray[i])), plainTextArray[i])) );
}
return decryptionText.toString();
}
public static void main(String[] args) {
Integer choice = 0;
Scanner inp = new Scanner(System.in);
String plainText = null, key = null;
while ( choice != 6 ) {
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
key = inp.next();
break;
}
case 3:{
printTable(generateTable());
break;
}
case 4:{
if(plainText.equals("") || key.equals("") ) {
System.out.println("Enter required parameters for encrypting.");
}else {
System.out.println(encryption( plainText , generateKey( plainText, key )));
}
break;
}
case 5:{
if(plainText.equals("") || key.equals("") ) {
System.out.println("Enter required parameters for decrypting.");
}else {
System.out.println(decryption(plainText, generateKey( plainText, key )));
}
break;
}
case 6:{
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
