g++ -c -fPIC -I/home/radek/Downloads/jdk-11.0.7/include -I/home/radek/Downloads/jdk-11.0.7/include/linux Library.cpp -o Library.o
g++ -shared -fPIC -o libnative.so Library.o -lc
