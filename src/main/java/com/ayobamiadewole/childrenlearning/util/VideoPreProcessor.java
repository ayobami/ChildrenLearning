/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayobamiadewole.childrenlearning.util;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
/**
 *
 * @author aa185298
 */
public class VideoPreProcessor implements DataSetPreProcessor {

    @Override
    public void preProcess(DataSet toPreProcess) {
        toPreProcess.getFeatures().divi(255);  
    }
}
