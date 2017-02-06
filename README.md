# Neural Networks in Java

This repository contains code to generate multilayer perceptrons (Neural networks).
It also contains a test case consisting of hand-written digit recognition on the MNIST dataset.
Developed as a learning exercise with [Mathieu Bolduc](https://github.com/mathieubolduc).

To this date, the best accuracy obtained with this code on the MNIST dataset: **98.53%**.
This accuracy was obtained with the following configuration options:
```java
NetworkConfig config = new NetworkConfig();
config.epochs = 20;
config.batchSize = 10;
config.learningRate = 0.05;
config.regularizationLambda = 5.0;
config.momentumMu = 0.3;
config.costFunction = CostFunction.CROSS_ENTROPY;
config.activationFunction = ActivationFunction.SIGMOID;

Network n = new Network(new int[] {784, 1000, 500, 10}, config);
```

## Features
- Mini-batch gradient descent
- Momentum-based gradient descent
- L2 Regularization
- Sigmoid activation function
- Quadratic cost function
- Cross-entropy cost function
- Gaussian weight initialization (condensed)

### Planned features
- L1 Regularization
- Dropout
- *tanh* activation
- ReLU activation
- log-likelihood cost
- Softmax layer

## Running the code

In addition to the code in this repository, you will need a parent folder `res` containing the MNIST data set files, which can be downloaded [here](http://yann.lecun.com/exdb/mnist/).
Then simply run `mnist/Main.java`.

## The `NetworkConfig` object
You will need to instantiate a `NetworkConfig` object in order to pass the required configuration options to the neural network.
This object has several properties which can be used to vary the behaviour of the network:
- **epochs**: the number of times to go over the training data during the training phase
- **batchSize**: the number of data samples to evalute per batch before updating the weights of the network. Putting a value of 1 will make it so the network does not learn using mini-batch gradient descent.
- **learningRate**: the factor by which the network updates its weights. This is most likely the setting that will need to be tweaked the most to get optimal results.
- **regularizationLambda**: The L2 regularization factor. Putting a value of 0 means that there is no regularization.
- **momentumMu**: the momentum value for momentum-based gradient descent. Putting a value of 0 means the network will learning using regular gradient descent.
- **costFunction**: the cost function to use. Can currently be one of: `CostFunction.QUADRATIC` or `CostFunction.CROSS_ENTROPY`.
- **activationFunction**: the activation function to use. Can currently only be `ActivationFunction.SIGMOID`.
