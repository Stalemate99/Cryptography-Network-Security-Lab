import java.util.Scanner;
public class Playfaircipher{
private static void menu() {
System.out.println("_____Play Fair Cipher_____");
System.out.println("1. Enter plain text");
System.out.println("2. Enter key text");
System.out.println("3. Print matrix");
System.out.println("4. Encrypt");
System.out.println("5. Decrypt");
System.out.println("6. Exit");
}
private static String removeRedundancy(String s) {
StringBuilder finalString = new StringBuilder();
String[] currString = new String[s.length()];
char[] tempString = s.toCharArray();
for (int i = 0 ; i < s.length() ; i++ ) {
currString[i] = Character.toString(tempString[i]);
}
for (int i = 0 ; i < s.length() ; i++ ) {
if(finalString.indexOf(currString[i]) == -1) {
finalString.append(currString[i]);
}
}
return finalString.toString();
}
private static char[][] generateMatrix(String s){
char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
String[] alphaString = new String[26];
for (int i = 0 ; i < 26 ; i++ ) {
alphaString[i] = Character.toString(alphabets[i]);
}
s = s.toLowerCase();
char[] tempString = s.toCharArray();
StringBuilder matrixString = new StringBuilder();
for (int i = 0 ; i < s.length() ; i++) {
matrixString.append(Character.toString(tempString[i]));
}
for ( int i = 0 ; i < 26 ; i++) {
if(matrixString.indexOf(alphaString[i]) == -1) {
matrixString.append(alphaString[i]);
}
}
matrixString = matrixString.deleteCharAt(matrixString.indexOf("j"));
char[] matrix = matrixString.toString().toCharArray();
char[][] finalMatrix = new char[5][5];
for (int i = 0 ; i < 5 ; i++) {
for (int j = 0 ; j < 5 ; j++) {
finalMatrix[i][j] = matrix[ i * 5 + j ];
}
}
return finalMatrix;
}
private static void printMatrix(char matrix[][]) {
for ( int i = 0 ; i < 5 ; i++ ){
for ( int j = 0 ; j < 5 ; j++ ){
System.out.print(matrix[i][j] + " ");
}
System.out.println("");
}
}
private static String[] deconstructPlainText(String s) {
StringBuilder tempString = new StringBuilder();
s = s.length() % 2 == 0 ? s : s + "x";
String[] splitString = s.split("(?<=\\G..)");
for ( int i = 0 ; i < s.length()/2 ; i++ ) {
char[] check = splitString[i].toCharArray();
if(check[0] == check[1]) {
tempString.append(Character.toString(check[0])+"x");
tempString.append(Character.toString(check[1])+"x");
}else {
tempString.append(splitString[i]);
}
}
return tempString.toString().split("(?<=\\G..)");
}
private static Integer[] returnIndex(char[][] matrix , char a ) {
Integer[] location = new Integer[2];
for ( int i = 0 ; i < 5 ; i++ ){
for ( int j = 0 ; j < 5 ; j++ ){
if( matrix[i][j] == a ) {
location[0] = i;
location[1] = j;
break;
}
}
}
return location;
}
private static String encryption( char [][] matrix , String[] pairs) {
StringBuilder encryptPairs = new StringBuilder();
Integer[] loc1;
Integer[] loc2;
for (String s : pairs) {
char[] items = s.toCharArray();
loc1 = returnIndex( matrix , items[0] );
loc2 = returnIndex( matrix , items[1] );
if( loc1[0] == loc2[0] ) {
loc1[1] = ( loc1[1] + 1 ) % 5;
loc2[1] = ( loc2[1] + 1 ) % 5;
}else if( loc1[1] == loc2[1] ) {
loc1[0] = ( loc1[0] + 1 ) % 5;
loc2[0] = ( loc2[0] + 1 ) % 5;
}else {
Integer temp = loc1[1];
loc1[1] = loc2[1];
loc2[1] = temp;
}
encryptPairs.append( Character.toString(matrix[loc1[0]][loc1[1]]) +
Character.toString(matrix[loc2[0]][loc2[1]]) );
}
return encryptPairs.toString();
}
private static String decryption( char[][] matrix , String[] pairs ) {
StringBuilder decryptPairs = new StringBuilder();
Integer[] loc1;
Integer[] loc2;
for (String s : pairs) {
char[] items = s.toCharArray();
loc1 = returnIndex( matrix , items[0] );
loc2 = returnIndex( matrix , items[1] );
if( loc1[0] == loc2[0] ) {
Integer temp = ( loc1[1] - 1 ) % 5 ;
loc1[1] = temp < 0 ? temp + 5 : temp;
temp = ( loc2[1] - 1 ) % 5 ;
loc2[1] = temp < 0 ? temp + 5 : temp;
}else if( loc1[1] == loc2[1] ) {
Integer temp = ( loc1[0] - 1 ) % 5;
loc1[0] = temp < 0 ? temp + 5 : temp;
temp = ( loc2[0] - 1 ) % 5;
loc2[0] = temp < 0 ? temp + 5 : temp;
}else {
Integer temp = loc1[1];
loc1[1] = loc2[1];
loc2[1] = temp;
}
decryptPairs.append( Character.toString(matrix[loc1[0]][loc1[1]]) +
Character.toString(matrix[loc2[0]][loc2[1]]) );
}
return decryptPairs.toString();
}
private static String finalChange(String s) {
String[] pairs = s.split("(?<=\\G..)");
StringBuilder finalDecrypt = new StringBuilder();
for ( String pair : pairs ) {
if(finalDecrypt.indexOf(pair) == -1) {
finalDecrypt.append(pair);
}else {
int tempIndex = finalDecrypt.indexOf(pair);
finalDecrypt.deleteCharAt(tempIndex);
finalDecrypt.deleteCharAt(tempIndex);
finalDecrypt.append( Character.toString(pair.toCharArray()[0])
+ Character.toString(pair.toCharArray()[0]) );
}
}
return finalDecrypt.toString();
}
public static void main(String[] args) {
int choice = 0;
Scanner inp = new Scanner(System.in);
String plainText = null, key = null;
char[][] matrix = null;
while( choice != 6 ) {
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
if(key.equals("")) {
System.out.println("Enter required parameters
for finding matrix.");
}else {
matrix =
generateMatrix(removeRedundancy(key));
}
printMatrix(matrix);
break;
}
case 4:{
if(plainText.equals("") || key.equals("") || matrix[0].length
== 0) {
System.out.println("Enter required parameters
for encrypting.");
}else {
System.out.println(encryption( matrix ,
deconstructPlainText(plainText)));
}
break;
}
case 5:{
if(plainText.equals("") || key.equals("") || matrix[0].length
== 0) {
System.out.println("Enter required parameters
for decrypting.");
}else {
System.out.println(finalChange(decryption(
matrix , deconstructPlainText(plainText))));
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
