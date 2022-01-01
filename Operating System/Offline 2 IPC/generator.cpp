#include "generator.h"

Generator::Generator(double mean) : distribution(mean) {
}

Generator::~Generator() {
}


int Generator::generate() {
	return distribution(generator);
}
/*
int main()
{
	double lambda = 1/5.0;
	Generator generator(lambda);
	
	cout<<"lambda = "<<lambda<<endl;
	std::map<int, int> hist;
	int sample=100000;
	double sum=0;

    for(int n=0; n<sample; ++n) {
        int x= generator.generate();
		++hist[x];
		sum+=x;
    }
    for(auto p : hist) {
        std::cout << p.first << ' '
                  << std::string(p.second/100, '*') << '\n';
    }
	cout<<"min: "<<hist.begin()->first<<endl;
	cout<<"max: "<<hist.rbegin()->first<<endl;
	double average = sum/sample;
	cout<<"average: "<<average<<endl;

}
*/