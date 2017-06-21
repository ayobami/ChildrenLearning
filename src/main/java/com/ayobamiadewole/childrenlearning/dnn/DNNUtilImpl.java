/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayobamiadewole.childrenlearning.dnn;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

/**
 *
 * @author aa185298
 */
public class DNNUtilImpl {
    
    
    public void loadData(){
        MultiLayerConfiguration conf = 
	new NeuralNetConfiguration.Builder()
		.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
		.updater(Updater.NESTEROVS).momentum(0.9)
		.learningRate(learningRate)
		.list(
			new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes).activation("relu").build(),
			new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD).activation("softmax").nIn(numHiddenNodes).nOut(numOutputs).build()
		).backprop(true).build();
    }
}
