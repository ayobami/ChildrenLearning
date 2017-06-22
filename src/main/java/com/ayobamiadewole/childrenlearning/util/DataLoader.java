/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayobamiadewole.childrenlearning.util;

import java.io.BufferedReader;
import org.datavec.api.conf.Configuration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVSequenceRecordReader;
import org.datavec.api.split.InputSplit;
import org.datavec.api.split.ListStringSplit;
import org.datavec.api.split.CollectionInputSplit;
import org.datavec.codec.reader.CodecRecordReader;
import org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.AsyncDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

/**
 *
 * @author aa185298
 */
public class DataLoader {

    public static final int V_WIDTH = 130;
    public static final int V_HEIGHT = 130;
    public static final int V_NFRAMES = 150;

    private DataSetIterator getDataSetIterator(String videoPath, String labelPath, int miniBatchSize) throws Exception {
        SequenceRecordReader featuresTrain = getFeaturesReader(videoPath);
        SequenceRecordReader labelsTrain = getLabelsReader(labelPath);
        SequenceRecordReaderDataSetIterator sequenceIter
                = new SequenceRecordReaderDataSetIterator(featuresTrain, labelsTrain, miniBatchSize, 4, false);
        sequenceIter.setPreProcessor(new VideoPreProcessor());
        return new AsyncDataSetIterator(sequenceIter, 1);
    }

    public SequenceRecordReader getFeaturesReader(String path) throws Exception {
        List<URI> uRIs = getEmotionVideosURIs(path);
        InputSplit inputSplit = new CollectionInputSplit(uRIs);
        Configuration configuration = new Configuration();
        configuration.set(CodecRecordReader.RAVEL, "true");
        configuration.set(CodecRecordReader.START_FRAME, "0");
        configuration.set(CodecRecordReader.TOTAL_FRAMES, String.valueOf(V_NFRAMES));
        configuration.set(CodecRecordReader.ROWS, String.valueOf(V_WIDTH));
        configuration.set(CodecRecordReader.COLUMNS, String.valueOf(V_HEIGHT));
        CodecRecordReader codecRecordReader = new CodecRecordReader();
        codecRecordReader.initialize(configuration, inputSplit);
        return codecRecordReader;
    }

    private SequenceRecordReader getLabelsReader(String path) throws Exception {
        List<List<String>> labels = getEmotionVideosLabels(path);
        InputSplit inputSplit = new ListStringSplit(labels);
        CSVSequenceRecordReader cSVSequenceRecordReader = new CSVSequenceRecordReader();
        cSVSequenceRecordReader.initialize(inputSplit);
        return cSVSequenceRecordReader;
    }

    public List<EmotionVideo> getEmotionVideos(String path) {
        List<EmotionVideo> emotionVideos = null;
        File file = new File(path);
        //check if path is a directory and contains video files
        if (file.isDirectory() && file.listFiles().length > 0) {
            emotionVideos = new ArrayList<>();
            File[] files = file.listFiles();
            EmotionVideo emotionVideo;
            for (File f : files) {
                emotionVideo = new EmotionVideo();
                emotionVideo.setFileName(f.getName());
                emotionVideo.setFilePath(f.getPath());
                emotionVideos.add(emotionVideo);
            }
        }
        return emotionVideos;
    }

    public List<URI> getEmotionVideosURIs(String path) {
        List<URI> uRIs = null;
        File file = new File(path);
        //check if path is a directory and contains video files
        if (file.isDirectory() && file.listFiles().length > 0) {
            uRIs = new ArrayList<>();
            File[] files = file.listFiles();
            for (File f : files) {
                uRIs.add(f.toURI());
            }
        }
        return uRIs;
    }

    public List<List<String>> getEmotionVideosLabels(String path) {
        List<List<String>> labels = null;
        File file = new File(path);
        if (file.exists()) {
            try {
                labels = new ArrayList<>();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                Iterator<String> iterators = bufferedReader.lines().iterator();
                while (iterators.hasNext()) {
                    String line = iterators.next();
                    if (line != null) {
                        List<String> splittedLabels = splitLineToList(line);
                        labels.add(splittedLabels);
                    }
                }
            } catch (FileNotFoundException ex) {

            }
        }
        return labels;
    }

    public List<String> splitLineToList(String line) {
        List<String> labels = null;
        if (line != null) {
            labels = new ArrayList();
            String[] lines = line.split("\\,");
            labels.addAll(Arrays.asList(lines));
        }
        return labels;
    }
}


