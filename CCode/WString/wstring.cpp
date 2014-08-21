#include <iostream>
#include <cstdio>
#include <new>
#include <cstring>

using namespace std;

int Wstring();
int maxChar(int, int);

//char* testCharArray;
//static int* sMemoize;
char testString[10001];
int * counted;
int * hashIndex;
int * totalCharCount;
int * maxNowCount;
int * maxUptoCount;
int * nowCharCount;
int * totalReverseCharCount;

int main() {
    int n;
    int i;

    counted = new int[10000];
    hashIndex = new int[10000];

    scanf("%d", &n);
    for (i = 0; i < n; i++) {
//        cin >> testString;
        scanf("%s", testString);
        printf("%d\n", Wstring());
//        printf("%s\n", testString);
//        cout << testString.at(0) << endl;
    }
    delete [] counted;
    delete [] hashIndex;
    return 0;
} 

int Wstring() {

    int hashCount = 0;
    int i = 0;
    int j = 0;
    int k = 0;
    int index = 0;
    int len = 0;
    int val1 = 0;
    int val2 = 0;
    int val3 = 0;
    int val4 = 0;
    int maxCount = 0;
    int tempCount = 0;
    int max = 0;
    int tempdiff = 0;

//    len = testString.length();
    //        testCharArray = new (nothrow) char[len];
    //        strcpy(testCharArray, testString);
//    cout << "Wstring function is being executed" << endl;


//    cout << "hashIndex is being allocated" << endl;

    while (testString[i] != '\0') {
        if (testString[i] == '#') {
            hashIndex[j++] = i;
            hashCount++;
        }
        i++;
    }

    if (hashCount < 3) {
        return 0;
    }

    totalCharCount = new int[(hashCount + 1) * 26]();
    nowCharCount = new int[26]();
    maxNowCount = new int[hashCount + 1]();
    maxUptoCount = new int[hashCount + 1]();

    totalReverseCharCount = new int[hashCount]();


    len = i;
    j = 0;

    for (i = 0; i < hashCount + 1; i++) {
        memset(nowCharCount, 0, 26 * sizeof(int));
        if (i != 0) {
            j = hashIndex[i - 1] + 1;
            for (k = 0; k < 26; k++) {
                totalCharCount[i * 26 + k] += totalCharCount[(i - 1) * 26 + k];
            }
        }
        if (i == hashCount)
            index = len;
        else
            index = hashIndex[i];
        for (; j < index; j++) { 
            k = testString[j] - 'a';
            totalCharCount[i * 26 + k]++;
            nowCharCount[k]++;
//            if (totalCharCount[i * 26 + k] > maxUptoCount[i])
//                maxUptoCount[i] = totalCharCount[i * 26 + k];
            if (nowCharCount[k] > maxNowCount[i])
                maxNowCount[i] = nowCharCount[k];
        }
    }

    for (i = 0; i < hashCount; i++) {
        for (j = 0; j < 26; j++) {
            tempdiff = totalCharCount[hashCount * 26 + j] - totalCharCount[i * 26 + j];
            if (tempdiff > totalReverseCharCount[i])
                totalReverseCharCount[i] = tempdiff;
            if (totalCharCount[i * 26 + j] > maxUptoCount[i])
                maxUptoCount[i] = totalCharCount[i * 26 + j];
        }
    }

//    cout << "hashCount value: " << hashCount << endl;


    //        sMemoize = new (nothrow) int[len][len];

//    cout << "Going into the main computation loop" << endl;

    for (i = 0; i < hashCount - 2; i++) {
        if (hashIndex[i] + 1 == hashIndex[i + 1] || hashIndex[i + 1] + 1 == hashIndex[i + 2])
            continue;
//        val1 = maxChar(0, hashIndex[i] - 1);
//        printf("Real val1 = %d\tHashIndex = %d\n", val1, hashIndex[i]);
        val1 = maxUptoCount[i];
//        printf("val1 = %d\n", val1);
        if (val1 == 0)
            continue;
//        val2 = maxChar(hashIndex[i] + 1, hashIndex[i + 1] - 1);
//        printf("Real val2 = %d\n", val2);
        val2 = maxNowCount[i + 1];
//        printf("val2 = %d\n", val2);
        if (val2 == 0)
            continue;
//        val3 = maxChar(hashIndex[i + 1] + 1, hashIndex[i + 2] - 1);
//        printf("Real val3 = %d\n", val3);
        val3 = maxNowCount[i + 2];
//        printf("val3 = %d\n", val3);
        if (val3 == 0)
            continue;
//        val4 = maxChar(hashIndex[i + 2] + 1, len - 1);
//        printf("Real val4 = %d\n", val4);
        val4 = totalReverseCharCount[i + 2];
//        printf("val4 = %d\n", val4);
        if (val4 == 0)
            continue;
        tempCount = val1 + val2 + val3 + val4 + 3;
        if (tempCount > maxCount)
            maxCount = tempCount;
    }

    //        delete sMemoize;
//    delete [] hashIndex;
    delete [] nowCharCount; 
    delete [] totalCharCount;
    delete [] maxUptoCount;
    delete [] maxNowCount;
    delete [] totalReverseCharCount;
    return maxCount;
}

int maxChar(int i, int j) {
    int x, y;
    int temp = 0;
    int maxChar = 0;

    if (i > j)
        return 0;

//    cout << "maxChar function is being executed" << endl;

    if (i == j && testString[i] != '#') {
        //        sMemoize[i][i] = 1;
        return 1;
    }

    if (testString[j] != '#')
        maxChar = 1;

    //    if (sMemoize[i][j] != 0)
    //       return sMemoize[i][j];

    y = j - i + 1;
//    counted = new int[y];
    memset(counted, 0, y * sizeof(int));

    for (x = i; x < j; x++) {
        temp = 1;
        if (counted[x - i] == 1)
            continue;
        else if (testString[x] == '#')
            continue;
        for (y = x + 1; y <= j; y++) {
            if (testString[x] == testString[y]) {
                temp++;
                counted[y - i] = 1;
            }
        }
        if (temp > maxChar) {
            maxChar = temp;
            if (2 * maxChar >= y)
                break;
        }
    }

    //    sMemoize[i][j] = maxChar;

//    delete [] counted;
    return maxChar;
}
