
#include <bits/stdc++.h>
#include "Library.h"
#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
using namespace std;


std::pair<int,int> randomMove(int board[5][5])
{
	unsigned long j;
	srand( (unsigned)time(NULL));

	int x,y;
	while(true){
	x=rand() % 5;
	y=rand() % 5;
	if(board[x][y]==0)
	{
		break;
	}
	}
	
	
	return std::make_pair(x, y);
}

JNIEXPORT jintArray JNICALL Java_game_algorithm_CppInterface_makeRandom(JNIEnv *env, jobject thisObject, jintArray javaArray)
{

    jintArray result;
    result = env->NewIntArray(2);
 
 
    jint *array = env->GetIntArrayElements(javaArray, JNI_FALSE);
    int size = 5;
    int board[5][5];

    for (int i = 0; i < size; i++)
    {
	for(int j = 0; j < size; j++)
	{
		board[i][j] = array[i*size+j];
	}	

    }
	std::pair<int,int> coords = randomMove(board);
	
	jint fill[2];
	fill[0]=coords.first;
	fill[1]=coords.second;
	env->SetIntArrayRegion(result, 0, 2, fill);
	return result;
}


std::pair<int,int> firstMove(int board[5][5])
{
int size = 5;

	int x,y;
	for(int i=0; i<5;i++){
		for(int j=0; j<5;j++){
			if(board[i][j]==0){
				x = i;
				y = j;
				return std::make_pair(x, y);
			}
		}
	}
	
}

JNIEXPORT jintArray JNICALL Java_game_algorithm_CppInterface_makeFirst(JNIEnv *env, jobject thisObject, jintArray javaArray)
{

    jintArray result;
    result = env->NewIntArray(2);
 
 
    jint *array = env->GetIntArrayElements(javaArray, JNI_FALSE);
    int size = 5;
    int board[5][5];

    for (int i = 0; i < size; i++)
    {
	for(int j = 0; j < size; j++)
	{
		board[i][j] = array[i*size+j];
	}	

    }
    

	std::pair<int,int> coords = firstMove(board);
	
	jint fill[2];
	fill[0]=coords.first;
	fill[1]=coords.second;
	env->SetIntArrayRegion(result, 0, 2, fill);
	return result;
}

