import java.lang.Math.*;
import java.util.Scanner;
public class Hillcipher {
private static void menu() {
System.out.println("_____Hill Cipher_____");
System.out.println("1. Enter key");
System.out.println("2. Enter plain text");
System.out.println("3. Encrypt");
System.out.println("4. Decrypt");
System.out.println("5. Exit");
System.out.println("---------------------");
}
private static String checkPlainText ( String plainText, int n ) {
return plainText.length() % n == 0 ?
plainText :
plainText + new String(new char[(n - plainText.length() % n)]).replace('\0',
plainText.charAt(plainText.length()-1));
}
private static int[][] alphaToNumericMatrix ( char[][] matrix, int row, int column ){
int[][] numericMatrix = new int[row][column];
StringBuilder alphaIndex = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
for ( int i = 0 ; i < row ; i++ ) {
for (int j = 0 ; j < column ; j++ ) {
numericMatrix[i][j] = alphaIndex.indexOf( Character.toString( matrix[i][j] ) );
}
}
return numericMatrix;
}
private static char[][] numericToAlphaMatrix ( int[][] matrix, int row, int column ){
char[][] alphaMatrix = new char[row][column];
StringBuilder alphaIndex = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
for ( int i = 0 ; i < row ; i++ ) {
for (int j = 0 ; j < column ; j++ ) {
alphaMatrix[i][j] = alphaIndex.charAt( matrix[i][j] );
}
}
return alphaMatrix;
}
private static String matrixToString ( char[][] matrix, int row, int column ) {
StringBuilder str = new StringBuilder();
for ( int j = 0 ; j < column ; j++ ) {
for (int i = 0 ; i < row ; i++ ) {
str.append( Character.toString( matrix[i][j] ) );
}
}
return str.toString();
}
private static char[][] plainTextToMatrix ( String finalPlainText, int n ) {
int entries = finalPlainText.length() / n;
char[][] plainTextMatrixCollection = new char[n][entries];
for ( int j = 0 ; j < entries ; j++ ) {
for ( int i = 0 ; i < n ; i++ ) {
plainTextMatrixCollection[i][j] = (char)finalPlainText.charAt( j * n + i );
}
}
return plainTextMatrixCollection;
}
private static int[][] matrixMultiply( int[][] keyMatrix, int[][] productMatrix, int n, int entries) {
int finalMatrix[][] = new int[n][entries];
for ( int i = 0 ; i < n ; i ++ ) {
for ( int j = 0 ; j < entries ; j++ ) {
for (int k = 0 ; k < n ; k++ ) {
finalMatrix[i][j] += keyMatrix[i][k] * productMatrix[k][j];
}
}
}
return finalMatrix;
}
static void getCofactor( int matrix[][], int temp[][], int p, int q, int n ) {
int i = 0, j = 0;
for (int row = 0; row < n; row++) {
for (int col = 0; col < n; col++) {
if (row != p && col != q) {
temp[i][j++] = matrix[row][col];
if (j == n - 1) {
j = 0;
i++;
}
}
}
}
}
static int getDeterminant( int matrix[][], int n, int N) {
int D = 0;
if (n == 1)
return matrix[0][0];
int temp[][] = new int[N][N];
int sign = 1;
for ( int i = 0; i < n; i++ ) {
getCofactor( matrix , temp, 0, i, n );
D += sign * matrix[0][i] * getDeterminant( temp , n - 1, N);
sign = -sign;
}
return D;
}
static int getInverseDeterminant( int d ) {
//Run through 25 and find which leaves 1, as d * d^-1 = 1 mod 26
for ( int i = 1 ; i < 26 ; i++ ) {
if ( ( d * i ) % 26 == 1 ) {
return i;
}
}
return d;
}
static int[][] getAdjointMatrix ( int[][] matrix , int n ) {
int adj[][] = new int[n][n];
int sign = 1;
int [][]temp = new int[n][n];
for (int i = 0; i < n; i++) {
for (int j = 0; j < n; j++) {
getCofactor( matrix , temp, i, j, n );
sign = ((i + j) % 2 == 0)? 1: -1;
adj[j][i] = (sign)*(getDeterminant(temp, n-1 , n));
}
}
return adj;
}
static int[][] getInverse ( int[][] matrix, int n) {
int determinant = getDeterminant( matrix, n, n );
int determinantInverse = getInverseDeterminant(determinant);
int[][] adjointMatrix = getAdjointMatrix( matrix , n );
for ( int i = 0 ; i < n ; i++ ) {
for ( int j = 0 ; j < n ; j++ ) {
adjointMatrix[i][j] *= determinantInverse;
}
}
return adjointMatrix;
}
public static void main(String[] args) {
Scanner inp = new Scanner(System.in);
int choice = 0;
String plainText = "";
int[][] plainTextMatrix = null;
int n = 0; // Size of key matrix
int[][] key = null;
int[][] cipherMatrix = null;
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
System.out.println("Enter the size of key matrix : ");
n = inp.nextInt();
System.out.println("Enter the key matrix : ");
key = new int[n][n];
for ( int i = 0 ; i < n ; i++ ) {
for ( int j = 0; j < n ; j++ ) {
key[i][j] = inp.nextInt();
}
}
if ( getDeterminant(key, n, n) == 0) {
System.out.println("Invalid key, Invese does not exist. Try again.");
}
break;
}
case 2:{
System.out.println(" Enter the plain text : (preferably under 10 letters) ");
plainText = inp.next();
plainText = checkPlainText(plainText, n);
plainTextMatrix = alphaToNumericMatrix( plainTextToMatrix(plainText, n) , n ,
plainText.length() / n );
break;
}
case 3:{
if ( plainText.equals("") ) {
System.out.println("Enter required parameters for encrypting");
}else {
System.out.println("Encrypting plain text... ");
cipherMatrix = matrixMultiply( key, plainTextMatrix, n, plainText.length() /
n );
char[][] temp = new char[n][plainText.length()/n];
for ( int j = 0 ; j < plainText.length() / n ; j++ ) {
for ( int i = 0; i < n ; i++ ) {
int tempNum = cipherMatrix[i][j] % 26;
cipherMatrix[i][j] = tempNum < 0 ? 26 + tempNum :
tempNum;
}
}
temp = numericToAlphaMatrix( cipherMatrix, n , plainText.length() / n );
System.out.println(matrixToString( temp , n , plainText.length() / n ));
}
break;
}
case 4:{
if ( plainText.equals("")) {
System.out.println("Enter required parameters for encrypting");
}else {
key = getInverse( key , n );
for( int i = 0 ; i < n ; i++ ){
for ( int j = 0 ; j < n ;j ++ ){
int temp = key[i][j] % 26;
key[i][j] = temp < 0 ? 26 + temp : temp;
}
System.out.println("");
}
plainTextMatrix = matrixMultiply(key , cipherMatrix, n, plainText.length() /
n );
for ( int j = 0 ; j < plainText.length() / n ; j++ ) {
for ( int i = 0; i < n ; i++ ) {
plainTextMatrix[i][j] %= 26;
}
}
System.out.println(matrixToString(numericToAlphaMatrix(plainTextMatrix, n, plainText.length() / n ), n , plainText.length()
/ n ));
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
