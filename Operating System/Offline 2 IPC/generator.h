#ifndef GENERATOR_H
#define GENERATOR_H

#include <random>
using std::default_random_engine;
using std::poisson_distribution;

class Generator
{
private:
	default_random_engine generator;
	poisson_distribution<int> distribution;
public:
	Generator(double mean);
	int generate();
	~Generator();
};


#endif // GENERATOR_H